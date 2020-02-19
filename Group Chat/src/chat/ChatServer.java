package chat;
//File : ChatServer.java
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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * This file used to create the text area to display the 
 * message using jafax application. This server file handle the all messages 
 * send from the client and response to other clients.
 * 
 * @author Gulani Senthuran
 *
 */
public class ChatServer extends Application {
	// Create textArea object
	private static TextArea displayMessage = new TextArea();
	private Label heading = new Label("Display Message");

	// Create a socket to connect to the server
    private static ServerSocket serverSocket;

    // Created hash map object to save the set of ids.
    private static final Map<String, Socket> clientSocketMap = new HashMap<>();
    private static final Map<String, Thread> threads = new HashMap<>();
    private static final Map<String, DataInputStream> inputStreamMap = new HashMap<>();
    private static final Map<String, DataOutputStream> outputStreamMap = new HashMap<>();

	// Application call start method
	public void start(Stage primaryStage) throws Exception {

		displayMessage.setEditable(false);
	    displayMessage.setPrefWidth(500);
	    displayMessage.setPrefHeight(500);

	    // Set label text color
	    heading.setTextFill(Color.WHITE);

	    // Create VBox object
	    VBox vbox = new VBox();
		vbox.setSpacing(20);
        vbox.setPadding(new Insets(20, 20, 20, 20));
        vbox.getChildren().add(heading);
        vbox.getChildren().add(displayMessage);
        vbox.setStyle("-fx-background-color: #000000;");

        // Set to the scene
		Scene scene = new Scene(vbox, 400, 600);
		// Set title
		primaryStage.setTitle("Server Panel");
		// Place the scene in the stage
		primaryStage.setScene(scene);
		// Display the stage
		primaryStage.show();

		startServer();
	}

	/**
	 * This method used to connect the server,
	 * Received the client request and send to the 
	 * response to all client 
	 */
	private void startServer() {
		try {
			// Create the socket to connect the server
			serverSocket = new ServerSocket(8001);
			// Create the main thread to to call run 
			// and create multi thread to accept each client 
			// request
			Thread mainThread = new Thread(new IncomeMessage());
			mainThread.start();
			displayMessage.appendText("Server is running......");
		} catch (Exception e) {
			System.out.println("Error Occuring due to connect the server!!");
			displayMessage.appendText("Error Occuring due to connect the server!!");
		}
	}

	// Runnable inner class
	static class IncomeMessage implements Runnable {

		@Override
		public void run() {
			try {
				int count = 1;
				while (true) {
					threads.put("thread" + count, new Thread(new ClientSocket(serverSocket.accept())));
					threads.get("thread" + count).start();
					count++;
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	// Create runnable inner class ClientSocket
	static class ClientSocket implements Runnable {

		private Socket socket;

		public ClientSocket(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			String clientId = null;
			try {
				// Create data input and output stream
				DataInputStream inputStream = new DataInputStream(socket.getInputStream());
				DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
				// read clientId from the client
				clientId = inputStream.readUTF();
				displayMessage.appendText("\nClient: " + clientId + " connected to the server....");

				// put values an keys to the hashmap
				clientSocketMap.put(clientId, this.socket);
				inputStreamMap.put(clientId, inputStream);
				outputStreamMap.put(clientId, outputStream);

				for (String key : clientSocketMap.keySet()) {
					if (clientId.equalsIgnoreCase(key)) {
						outputStreamMap.get(key).writeUTF("\nYou connect to the group chat!!!. \nAvailable members: " + clientSocketMap.keySet());
					} else {
						outputStreamMap.get(key).writeUTF("\n" + clientId + " connected to the group chat!!!. \nAvailable members: " + clientSocketMap.keySet());
					}
				}

				while (true) {
					String message = inputStreamMap.get(clientId).readUTF();
					for (String key : clientSocketMap.keySet()) {
						if (clientId.equalsIgnoreCase(key)) {
							outputStreamMap.get(key).writeUTF("\nYou : " + message);
						} else {
							outputStreamMap.get(key).writeUTF("\n" + clientId + " : " + message);
						}
					}
					// Display the message in server who send the message
					displayMessage.appendText("\n" + clientId + " send message : '" + message + "'");
				}
			} catch (Exception e) {
				String leftMessage = "\nClient : " + clientId + " is left from the group";
				displayMessage.appendText(leftMessage);
				// Remove clients from the hash map
				clientSocketMap.remove(clientId);
				inputStreamMap.remove(clientId);
				outputStreamMap.remove(clientId);

				for (String key : clientSocketMap.keySet()) {
					try {
						outputStreamMap.get(key).writeUTF(leftMessage);
					} catch (Exception ex) {
						System.out.println(ex.getMessage());
					}
				}
			} finally {
				try {
					socket.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
				
			}
		}
		
	}

	// Create main method
	public static void main(String[] args) {
		launch(args);
	}
}
