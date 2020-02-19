package task1;
//File : GradeProcessing.java
//Course Name : ITC521 - Programming in Java 2
//Assessment Item : Assignment 2, Task 1
//Instructor Name : Recep Ulusoy
//Date : 14 September 2017
//Due on : 22 September 2017
//Student Id : 11619843
//Student Name : Gulani Senthuran

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * This file used to implement the GradeProcess for the students. It contains Javafx application and 
 * data base connection to processing the records. He re this application able to
 * insert the records, search the records, and update the records accordingly.
 * 
 * @author Gulani Senthuran
 *
 */
public class GradeProcessing extends Application{

	// Create Button in the GUI
	private Button insertButton = new Button("Insert Record");
	private Button searchButton = new Button("Search Record");
	private Button updateButton = new Button("Update Record");
	private Button quit = new Button("Quit");

	Alert alertBox = new Alert(AlertType.INFORMATION);

	@Override
	public void start(Stage primaryStage) throws Exception {
		alertBox.setTitle("Message");
        alertBox.setHeaderText("You have a message!!");
        alertBox.setContentText("Successfully created the table!!");
        alertBox.showAndWait();
		// Create UI
	    GridPane gridPane = new GridPane();
	    gridPane.setHgap(15);
	    gridPane.setVgap(25);

	    // Add buttons and label into grid
	    gridPane.add(insertButton, 0, 0);
	    gridPane.add(new Label("Click button to inser the record in to the table"), 1, 0);
	    gridPane.add(searchButton, 0, 1);
	    gridPane.add(new Label("Click button to search the record from the table"), 1, 1);
	    gridPane.add(updateButton, 0, 2);
	    gridPane.add(new Label("Click button to upate the record to the table"), 1, 2);
	    gridPane.add(quit, 0, 3);
	    gridPane.add(new Label("Terminate the program"), 1, 3);
	    // Set properties for UI
	    gridPane.setAlignment(Pos.CENTER);

	    Scene scene = new Scene(gridPane, 500, 300);
	    // Set title
	    primaryStage.setTitle("Grade Processing Demo");
	    // Place the scene in the stage
	    primaryStage.setScene(scene);
	    // Display the stage
	    primaryStage.show();

	    //Create PoppPanel object
	    PopupPanels popupPanel = new PopupPanels();
	    // Process events when Insert Record button click
	    insertButton.setOnAction(e -> popupPanel.insertRecord());
	    searchButton.setOnAction(e -> popupPanel.searchRecord());
	    updateButton.setOnAction(e -> popupPanel.upateRecord());
	    quit.setOnAction(e -> cancel(primaryStage));
	}

	/**
	 * This method used to close the Grade Processing panel when 
	 * click the cancel button
	 * 
	 * @param primaryStage
	 */
	private void cancel(Stage primaryStage) {
		primaryStage.close();
	}

	// Call the main method
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		ResultSet resultSet = null;
		String url="jdbc:mysql://localhost/gradeprocessing";
		String userName="root";
		String password="root";
		// Load the JDBC driver
	    Class.forName("com.mysql.jdbc.Driver");
	    // Establish a connection
	    Connection connection = DriverManager.getConnection(url, userName, password);
	    // Create a statement
	    Statement statement = connection.createStatement();
	    // Create a meta data
	    DatabaseMetaData metadata = connection.getMetaData();
	    // Return results tables name with ITC521
	    resultSet = metadata.getTables(null, null, "ITC521", null);
	    if(!resultSet.next()) {
	    	// Create a table in the gradeprocessing Schema
	    	// Execute a statement for creating table
	        statement.executeUpdate("CREATE TABLE ITC521 (StudentID VARCHAR(8), StudentName VARCHAR(50), Quiz INT(3), Assignment1 INT(3), "
	        		+ "Assignment2 INT(3), Assignment3 INT(3), Exam INT(3), Results FLOAT(5,2), Grade CHAR(4))");
	    }
	    statement.close();
	    connection.close();
	    launch(args);
	}
}
