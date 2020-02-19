package task2Mock;
//File : TrafficLightDemo.java
//Course Name : ITC521 - Programming in Java 2
//Assessment Item : Assignment 2, Task 2
//Instructor Name : Recep Ulusoy
//Date : 09 September 2017
//Due on : 22 September 2017
//Student Id : 11619843
//Student Name : Gulani Senthuran

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * This trafficLightDemo class used to demonstrating for traffic signal concept.
 * Using this GUI program able to stop the light signals and progress the 
 * Green Yellow and Re signals. Using Javafx application this GUI is developed
 * This file has used multi threading function using javafx application.
 * 
 * @author Gulani Senthuran
 *
 */
public class TrafficLightPanel extends Application{

	// Create the text fields in the GUI
	private TextField green = new TextField("3");
	private TextField yellow = new TextField("3");
	private TextField red = new TextField("3");

	// Create the button fields in the GUI
	private Button start = new Button("Start");
	private Button stop = new Button("Stop");

	// Create circle object
	Circle greenCircle = new Circle();
	Circle yellowCircle = new Circle();
	Circle redCircle = new Circle();
	/**
	 * This method used to start the application
	 */
	@Override
	public void start(Stage primaryStage) {

		// Create border pane object
		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(80, 80, 80, 80));
		// Place the notes in the pane
		borderPane.setTop(getHbox());
		borderPane.setBottom(getGrid());

		// Create the scene object and place it into the stage
        Scene scene = new Scene(borderPane, 500, 500);
		// Set the name of the stage
        primaryStage.setTitle("Traffic Light Demo");
        primaryStage.setScene(scene);
		// Display the stage
        primaryStage.show();

		// Process events when student Marks button click
		start.setOnAction(e -> startButton());
		// Process events when average button click
		stop.setOnAction(e -> stopAction());
	}

	/**
	 * This method used to create hBox Pane with three colors of circle.
	 * 
	 * @return HBox
	 */
	public HBox getHbox() {
		// Create horizontal box
        HBox hPane = new HBox(15);
		// Create space between each margin
		hPane.setPadding(new Insets(30,30,30,30));
		// Card position set to center
		hPane.setAlignment(Pos.CENTER);

		// Set the initial color for an radius for circle
		greenCircle.setRadius(40);
		greenCircle.setFill(Color.GREEN);

		yellowCircle.setRadius(40);
		yellowCircle.setFill(Color.SILVER);

		redCircle.setRadius(40);
		redCircle.setFill(Color.SILVER);

		// Add circles into hBox
		hPane.getChildren().addAll(greenCircle, yellowCircle, redCircle);
		hPane.setStyle("-fx-background-color: #000000;" + "-fx-background-radius: 30;");
		hPane.setSpacing(15);

		return hPane;
	}
	/**
	 * This method used to process when start button clicked.
	 * Create three threads for green, yellow, red lights
	 * and start it sequentially.
	 * 
	 */
	private void startButton() {
		Thread threadGreen = new Thread(new TrafficLight(Integer.parseInt(green.getText()), greenCircle, yellowCircle, redCircle), "Green");
		Thread threadYellow = new Thread(new TrafficLight(Integer.parseInt(yellow.getText()), greenCircle, yellowCircle, redCircle), "Yellow");
		Thread threadRed = new Thread(new TrafficLight(Integer.parseInt(red.getText()), greenCircle, yellowCircle, redCircle), "Red");

		// Start the threads and join it
		threadGreen.start();
		try {
			threadGreen.join();
		} catch (Exception e) {
			System.out.println("** RuntimeException from main");
		}
		threadYellow.start();
		try {
			threadYellow.join();
		} catch (Exception e) {
			System.out.println("** RuntimeException from main");
		}
		threadRed.start();
		try {
			threadRed.join();
		} catch (Exception e) {
			System.out.println("** RuntimeException from main");
		}
	}

	/**
	 * This method used to implement the process when stop button 
	 * is pressed. Light should be paused.
	 */
	@SuppressWarnings("deprecation")
	public void stopAction() {
		Thread.currentThread().suspend();
	}
	/**
	 * This method used to create bottom grid with text field an button
	 * 
	 * @return GridPane
	 */
	private GridPane getGrid() {
		GridPane gridPane = new GridPane();
		gridPane.setHgap(15);
		gridPane.setVgap(15);

		// Add the text field, label in to the grid
		gridPane.add(new Label("Green"), 0, 0);
		gridPane.add(green, 1, 0);
		green.setMaxWidth(50.0);

		gridPane.add(new Label("Yellow"), 0, 1);
		gridPane.add(yellow, 1, 1);
		yellow.setMaxWidth(50.0);

		gridPane.add(new Label("Green"), 0, 2);
		gridPane.add(red, 1, 2);
		red.setMaxWidth(50.0);

		gridPane.add(start, 4, 2);
		gridPane.add(stop, 5, 2);

		return gridPane;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
