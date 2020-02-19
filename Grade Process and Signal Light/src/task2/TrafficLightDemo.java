package task2;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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
public class TrafficLightDemo extends Application {
	// Create the text fields in the GUI
	private TextField green = new TextField("3");
	private TextField yellow = new TextField("3");
	private TextField red = new TextField("3");

	// Create the button fields in the GUI
	private Button start = new Button("Start");
	private Button stop = new Button("Stop");

	/**
	 * This method used to start the application
	 */
	@Override
	public void start(Stage primaryStage) {

		// Create SignalLight object
		SignalLight signalLight = new SignalLight();
		// Create border pane object
		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(80, 80, 80, 80));
		// Place the notes in the pane
		borderPane.setTop(signalLight.getHbox());
		borderPane.setBottom(getGrid());

		// Create the scene object and place it into the stage
        Scene scene = new Scene(borderPane, 500, 500);
		// Set the name of the stage
        primaryStage.setTitle("Traffic Light Demo");
        primaryStage.setScene(scene);
		// Display the stage
        primaryStage.show();

		// Process events when student Marks button click
		start.setOnAction(e -> startButton(signalLight));
		// Process events when average button click
		stop.setOnAction(e -> signalLight.stopAction());
	}

	/**
	 * This method used to process when start button clicked.
	 * the values from text fields retrieved an set.
	 * 
	 * @param signalLight
	 */
	private void startButton(SignalLight signalLight) {
		// Check whether the text field is empty,
		// if empty set 0 time to field
		if (green.getText().isEmpty()) {
			signalLight.setGreenTime(3);
		} else {
			signalLight.setGreenTime(Integer.parseInt(green.getText()));
		}

		if (yellow.getText().isEmpty()) {
			signalLight.setYellowTime(3);
		} else {
			signalLight.setYellowTime(Integer.parseInt(yellow.getText()));
		}

		if (red.getText().isEmpty()) {
			signalLight.setRedTime(3);
		} else {
			signalLight.setRedTime(Integer.parseInt(red.getText()));
		}

		signalLight.startAction();
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
