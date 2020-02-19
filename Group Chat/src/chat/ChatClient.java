package chat;
//File : ChatClient.java
//Course Name : ITC521 - Programming in Java 2
//Assessment Item : Assignment 3
//Instructor Name : Recep Ulusoy
//Date : 04 October 2017
//Due on : 06 October 22017
//Student Id : 11619843
//Student Name : Gulani Senthuran

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * This file used to create the jafax application to chat with multiple clients
 * simultaniouly. Every messages from clients sen to the main common server
 * and send all other clients
 * 
 * @author Gulani Senthuran
 *
 */
public class ChatClient extends Application {
	
	static String[] name;

	// Create the text fields in the GUI
	private TextField message = new TextField();
	// create string to set the command line argument
	private static String clientName;

	// Create TextArea object
	private static final TextArea displayMessage = new TextArea();
	private static final Object[] String = null;
	private Label heading = new Label("Display Message");
	private Label messageLabel = new Label("Message: ");

	// Create the button object
	private Button send = new Button("Send");
	private Button exit = new Button("Exit Chat");

	// Create a socket to connect to the server
    private static Socket socket;
    // Create ObjectOutputStream
    private static DataOutputStream outputToServer;
    // Create DataInputStream
    private static DataInputStream inputFromServer;

    Alert alertMessage = new Alert(AlertType.INFORMATION);

	// Application start method
	public void start(Stage primaryStage) throws Exception {
		VBox rootPane = new VBox();
		// Create the grid object
		GridPane gridPaneTop = new GridPane();
		gridPaneTop.setHgap(15);
		gridPaneTop.setVgap(15);

	    // Add the text field, label in to the grid
	    gridPaneTop.add(messageLabel, 0, 0);
	    gridPaneTop.add(message, 1, 0);
	    message.setPrefWidth(300);

	    displayMessage.setEditable(false);
	    displayMessage.setPrefWidth(500);
	    displayMessage.setPrefHeight(400);

	    // Set fill text color for label
	    heading.setTextFill(Color.WHITE);
	    messageLabel.setTextFill(Color.WHITE);
	    // set button text color
	    send.setTextFill(Color.BLACK);
	    exit.setTextFill(Color.BLACK);

	    // Add send button into the grid pane
	    gridPaneTop.add(send, 2, 0);
	    // Set properties alignment for label UI
	    gridPaneTop.setAlignment(Pos.BOTTOM_CENTER);
	    GridPane.setHalignment(send, HPos.RIGHT);

	    // Add exit button into the grid pane
	    gridPaneTop.add(exit, 3, 0);
	    // Set properties alignment for label UI
	    gridPaneTop.setAlignment(Pos.BOTTOM_CENTER);
	    GridPane.setHalignment(send, HPos.RIGHT);

	    // Create VBox object
		VBox vbox = new VBox();
		vbox.setSpacing(20);
        vbox.setPadding(new Insets(20, 20, 20, 20));
        vbox.getChildren().addAll(heading,displayMessage, gridPaneTop);
        /*vbox.getChildren().add(heading);
        vbox.getChildren().add(displayMessage);
        vbox.getChildren().add(gridPaneTop);*/
        vbox.setStyle("-fx-background-color: #000000;");
        rootPane.getChildren().addAll(vbox);
		Scene scene = new Scene(rootPane, 600, 400, Color.BLACK);
		// Set title
		primaryStage.setTitle("Client: " + clientName);
		// Place the scene in the stage
		primaryStage.setScene(scene);
		// Display the stage
		primaryStage.show();

		Thread incomeServer = new Thread(new IncomeServer(clientName));
		incomeServer.start();
		// Process events when send button click
		send.setOnAction(e -> send());

		exit.setOnAction(e -> exitChat(primaryStage));
	}

	/**
	 * This method used to exit the person
	 * from the chat group
	 * 
	 * @param primaryStage
	 */
	private void exitChat(Stage primaryStage) {
		System.exit(0);
	}

	// Create inner runnable class
	static class IncomeServer implements Runnable {

		private String clientName;

		public IncomeServer(String clientName) {
			this.clientName = clientName;
		}

		@Override
		public void run() {
			try {
				// Create a socket to connect to the server
		        socket = new Socket("localhost", 8001);
		        //displayMessage.appendText("You send request to connect to the server");

		        // Create object output stream to send student object to server
		        outputToServer = new DataOutputStream(socket.getOutputStream());

		        // Create data input stream to receive data from server
		        inputFromServer = new DataInputStream(socket.getInputStream());

		        // Send the clientId object to the server
		        outputToServer.writeUTF(this.clientName);

		        // Message received from server and write it to the display text area
		        while (true) {
		        	String inputMessage = inputFromServer.readUTF();
		        	appendTextArea(inputMessage);
		        }
			} catch (Exception e) {
				System.out.println("Server Connection Failier!!!");
				System.exit(0);
			} finally {
				try {
					socket.close();
					outputToServer.flush();
					inputFromServer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		private void appendTextArea(String inputMessage) {
			Platform.runLater(() -> {
		        displayMessage.appendText(inputMessage);
		    });
		}
	}

	/**
	 * This method used to send the messages to the other 
	 * clients through the server
	 */
	private void send() {
		try {
			// Check user enter the some message
			if (!message.getText().isEmpty()) {
				// send message to the server
				outputToServer.writeUTF(message.getText());
				// Clear the message text field
				message.clear();
			} else {
				alertMessage.setTitle("Message");
				alertMessage.setHeaderText("You have a message!!");
				alertMessage.setContentText("Please enter some message to chat!!");
				alertMessage.showAndWait();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	// Main method
	public static void main (String[] args) {
		/*
		 * Scanner in = new Scanner(System. in);
		 * System.out.println("Enter the client name: "); args[0] = in.next();
		 */
		clientName = args[0];
		launch(args);
	}
}
