package task1;
//File : PopupPanels.java
//Course Name : ITC521 - Programming in Java 2
//Assessment Item : Assignment 2, Task 1
//Instructor Name : Recep Ulusoy
//Date : 14 September 2017
//Due on : 22 September 2017
//Student Id : 11619843
//Student Name : Gulani Senthuran

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * 
 * @author Gulani Senthuran
 *
 */
public class PopupPanels {

	// create button objects
	private Button insertBt = new Button("Insert");
	private Button cancelBt = new Button("Cancel");
	private Button searchBt = new Button("Search");
	private Button updateBt = new Button("Update");
	private Button searchExitBt = new Button("Search Exit Record");

	// Create the text fields in the GUI
	private TextField studentId = new TextField();
	private TextField studentName = new TextField();
	private TextField quizMark = new TextField();
	private TextField ass1Mark = new TextField();
	private TextField ass2Mark = new TextField();
	private TextField ass3Mark = new TextField();
	private TextField examMark = new TextField();
	private TextField results = new TextField();
	private TextField grade = new TextField();

	Stage primaryStage = new Stage();
	// Create error popup
	Alert alert = new Alert(AlertType.ERROR);
	Alert alertMessage = new Alert(AlertType.INFORMATION);

	// Create the table in the GUI
	private TableView<Student> studentTable = new TableView<Student>();
	private final ObservableList<Student> studentList = FXCollections.observableArrayList();

	/**
	 * This method used to insert the record into the table.
	 * Here create the popup insert panel with text field and button.
	 * and include the field validation
	 */
	public void insertRecord() {
		// Create Top grid on GUI
	    GridPane gridPaneTop = new GridPane();

	    // call addGrid method to ad the top grid to the panel
	    gridPaneTop = addGrid();

	    // Add button into grid
	    gridPaneTop.add(insertBt, 1, 7);
	    gridPaneTop.add(cancelBt, 2, 7);
	    GridPane.setHalignment(insertBt, HPos.RIGHT);
	    GridPane.setHalignment(cancelBt, HPos.RIGHT);

	    Scene scene = new Scene(gridPaneTop, 500, 400);
	    // Set title
	    primaryStage.setTitle("Insert Record");
	    // Place the scene in the stage
	    primaryStage.setScene(scene);
	    // Display the stage
	    primaryStage.show();

	    // Process events when Insert button click
	    insertBt.setOnAction(e -> insert());
	    cancelBt.setOnAction(e -> cancel(primaryStage));
	}

	/**
	 * This method used to create the top grid panel
	 * include with text fields
	 * 
	 * @return GridPane
	 */
	private GridPane addGrid() {
		// Create Top grid on GUI
	    GridPane gridPaneTop = new GridPane();
	    gridPaneTop.setHgap(20);
	    gridPaneTop.setVgap(20);
	    // Add the text field, label in to the grid
	    gridPaneTop.add(new Label("Student ID (must be 8 digits)"), 0, 0);
	    gridPaneTop.add(studentId, 1, 0);
	    gridPaneTop.add(new Label("Student Name"), 0, 1);
	    gridPaneTop.add(studentName, 1, 1);
	    gridPaneTop.add(new Label("Quiz Marks (enter 0-100)"), 0, 2);
	    gridPaneTop.add(quizMark, 1, 2);
	    gridPaneTop.add(new Label("Assignment 1 Marks (must be 8 digits)"), 0, 3);
	    gridPaneTop.add(ass1Mark, 1, 3);
	    gridPaneTop.add(new Label("Assignment 2 Marks (must be 8 digits)"), 0, 4);
	    gridPaneTop.add(ass2Mark, 1, 4);
	    gridPaneTop.add(new Label("Assignment 3 Marks (must be 8 digits)"), 0, 5);
	    gridPaneTop.add(ass3Mark, 1, 5);
	    gridPaneTop.add(new Label("Exam Marks (must be 8 digits)"), 0, 6);
	    gridPaneTop.add(examMark, 1, 6);

	    // Set properties alignment for label UI
	    gridPaneTop.setAlignment(Pos.CENTER);
		return gridPaneTop;
	}

	/**
	 * This method used to calculate the results and grade for
	 * the marks then insert the values to the table
	 * when the Student Mark button click
	 */
	private void insert() {
		// define hasError to check existing error
		boolean hasError = false;
		// set error message for the error pop-up
		alert.setTitle("Error Dialog");
		alert.setHeaderText("You have an Error!!");

		// Check whether if there any blank field
		if (studentId.getText().isEmpty() || studentName.getText().isEmpty() ||
				quizMark.getText().isEmpty() || ass1Mark.getText().isEmpty() ||
				ass2Mark.getText().isEmpty() || examMark.getText().isEmpty()) {
			alert.setContentText("Please do not keep blank!!!");
			alert.showAndWait();
		} else {
			// Get the values from the text fields
			String stuId = studentId.getText();
			String stuName = studentName.getText();
			try {
				int quiz = Integer.parseInt(quizMark.getText());
				int assignment1 = Integer.parseInt(ass1Mark.getText());
				int assignment2 = Integer.parseInt(ass2Mark.getText());
				int assignment3 = Integer.parseInt(ass3Mark.getText());
				int exam = Integer.parseInt(examMark.getText());
				// Create the Student object for the setting values and validation
				Student student = new Student();
				student.setStudentID(stuId);
				student.setStudentName(stuName);
				student.setQuizMark(quiz);
				student.setAss1Mark(assignment1);
				student.setAss2Mark(assignment2);
				student.setAss3Mark(assignment3);
				student.setExamMark(exam);

				// Call the validation methods to validate the values
				if (!student.validateId(stuId)) {
					alert.setContentText("Invalid StudentID!!!Must be 8 Digit Numbers");
					hasError = true;
				}
				// Validate quiz marks
				if (!student.validateMark(quiz)) {
					alert.setContentText("Invalid Quiz Marks!!!Enter (0 - 100)");
					hasError = true;
				}
				// Validate assignment1 marks
				if (!student.validateMark(assignment1)) {
					alert.setContentText("Invalid Assignment1 Marks!!!Enter (0 - 100)");
					hasError = true;
				}
				// Validate assignment2 marks
				if (!student.validateMark(assignment2)) {
					alert.setContentText("Invalid Assignment2 Marks!!!Enter (0 - 100)");
					hasError = true;
				}
				// Validate assignment3 marks
				if (!student.validateMark(assignment3)) {
					alert.setContentText("Invalid Assignment2 Marks!!!Enter (0 - 100)");
					hasError = true;
				}
				// Validate exam marks
				if (!student.validateMark(exam)) {
					alert.setContentText("Invalid Exam Marks!!!Enter (0 - 100)");
					hasError = true;
				}
				if (hasError) {
					alert.showAndWait();
				} else {
					Student studentExit = new Student();
					// Check the student is already exit or not
					studentExit = student.getSearchRecords(student);
					if (studentExit != null) {
						alert.setContentText("Student has already existing!!!");
						alert.showAndWait();
					} else {
						float result = student.calculateTotal();
						// Set results and grade properties to the student object
						student.setResult(result);
						student.setGrade(student.calculateGrade(result));
						student.insertRecordTable();
						alertMessage.setTitle("Message");
						alertMessage.setHeaderText("You have a message!!");
						alertMessage.setContentText("Successfully Insert Records!!");
						alertMessage.showAndWait();
						ClearTextField();
					}
				}
			} catch (NumberFormatException nfe) {
				alert.setContentText("Invalid Marks....Enter Integer Value!!!");
				alert.showAndWait();
			}
		}
	}

	/**
	 * This method use clear all text field after table view
	 */
	private void ClearTextField() {
		studentId.clear();
		studentName.clear();
		quizMark.clear();
		ass1Mark.clear();
		ass2Mark.clear();
		ass3Mark.clear();
		examMark.clear();
	}

	/**
	 * This method used to search the records from the database table
	 * by student id or other fields with field validation
	 * 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void searchRecord() {
		// Create Top grid on GUI
	    GridPane gridPaneTop = new GridPane();

	    // call addGrid method to ad the top grid to the panel
	    gridPaneTop = addGrid();

	    gridPaneTop.add(new Label("Results"), 0, 7);
	    gridPaneTop.add(results, 1, 7);
	    gridPaneTop.add(new Label("Grade"), 0, 8);
	    gridPaneTop.add(grade, 1, 8);
	    // Add button into grid
	    gridPaneTop.add(searchBt, 1, 9);
	    gridPaneTop.add(cancelBt, 2, 9);
	    GridPane.setHalignment(insertBt, HPos.RIGHT);
	    GridPane.setHalignment(cancelBt, HPos.RIGHT);

        // Create the table column ID and set properties
	    TableColumn idCol = new TableColumn("ID");
	    idCol.prefWidthProperty().bind(studentTable.widthProperty().divide(7));
	    idCol.setCellValueFactory(new PropertyValueFactory<Student, String>("studentID"));

	    // Create the table column Name and set properties
	    studentTable.setEditable(false);
	    TableColumn nameCol = new TableColumn("Name");
	    nameCol.prefWidthProperty().bind(studentTable.widthProperty().divide(5));
	    nameCol.setCellValueFactory(new PropertyValueFactory<Student, String>("studentName"));

        // Create the table column Quiz Marks and set properties
	    TableColumn quizCol = new TableColumn("Quiz Marks");
	    quizCol.prefWidthProperty().bind(studentTable.widthProperty().divide(8));
	    quizCol.setCellValueFactory(new PropertyValueFactory<Student, String>("quizMark"));

        // Create the table column A1 and set properties
	    TableColumn a1Col = new TableColumn("A1");
	    a1Col.prefWidthProperty().bind(studentTable.widthProperty().divide(14));
	    a1Col.setCellValueFactory(new PropertyValueFactory<Student, String>("ass1Mark"));

        // Create the table column A2 and set properties
	    TableColumn a2Col = new TableColumn("A2");
	    a2Col.prefWidthProperty().bind(studentTable.widthProperty().divide(14));
	    a2Col.setCellValueFactory(new PropertyValueFactory<Student, String>("ass2Mark"));

	    // Create the table column A2 and set properties
	    TableColumn a3Col = new TableColumn("A3");
	    a3Col.prefWidthProperty().bind(studentTable.widthProperty().divide(14));
	    a3Col.setCellValueFactory(new PropertyValueFactory<Student, String>("ass3Mark"));

        // Create the table column Exam and set properties
	    TableColumn examCol = new TableColumn("Exam");
	    examCol.prefWidthProperty().bind(studentTable.widthProperty().divide(9));
	    examCol.setCellValueFactory(new PropertyValueFactory<Student, String>("examMark"));

        // Create the table column Results and set properties
	    TableColumn resultsCol = new TableColumn("Results");
	    resultsCol.prefWidthProperty().bind(studentTable.widthProperty().divide(9));
	    resultsCol.setCellValueFactory(new PropertyValueFactory<Student, String>("result"));

        // Create the table column Grade and set properties
	    TableColumn gradeCol = new TableColumn("Grade");
	    gradeCol.prefWidthProperty().bind(studentTable.widthProperty().divide(9));
	    gradeCol.setCellValueFactory(new PropertyValueFactory<Student, String>("grade"));

	    // Add list to the table
	    studentTable.setItems(studentList);
	    studentTable.getColumns().addAll(idCol, nameCol, quizCol, a1Col, a2Col, a3Col, examCol, resultsCol, gradeCol);

	    // Set all top grid, table an bottom grid to the VBox
 		final VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(gridPaneTop, studentTable);

	    Scene scene = new Scene(vbox, 700, 700);
	    // Set title
	    primaryStage.setTitle("Search Record");
	    // Place the scene in the stage
	    primaryStage.setScene(scene);
	    // Display the stage
	    primaryStage.show();

	    searchBt.setOnAction(e -> search());

	    cancelBt.setOnAction(e -> cancel(primaryStage));
	}

	/**
	 * This method used to cancel the panel when
	 * click the cancel button
	 * 
	 * @param primaryStage
	 */
	private void cancel(Stage primaryStage) {
		ClearTextField();
		primaryStage.close();
	}

	/**
	 * This method used to validate the fields and connection to the 
	 * database and implement to display the records to the table view.
	 */
	private void search() {
		// Initially clear the list to display
		studentList.clear();
		// define hasError to check existing error
		boolean hasError = false;
		Student student = new Student();
		// Check student id Fields empty or not
		if (!studentId.getText().isEmpty()) {
			student.setStudentID(studentId.getText());
			// Call the validation methods to validate the values
			if (!student.validateId(studentId.getText())) {
				alert.setContentText("Invalid StudentID!!!Must be 8 Digit Numbers");
				hasError = true;
			}
		}
		// Check student name field is empty
		if (!studentName.getText().isEmpty()) {
			student.setStudentName(studentName.getText());
		}
		// Check quiz marks field is empty
		if (!quizMark.getText().isEmpty()) {
			try {
				student.setQuizMark(Integer.parseInt(quizMark.getText()));
				// Validate quiz marks
				if (!student.validateMark(Integer.parseInt(quizMark.getText()))) {
					alert.setContentText("Invalid Quiz Marks!!!Enter (0 - 100)");
					hasError = true;
				}
			} catch (NumberFormatException nfe) {
				alert.setContentText("Invalid Marks....Enter Integer Value!!!");
				hasError = true;
			}
		} else {
			student.setQuizMark(-1);
		}
		// Check ass1 marks field is empty
		if (!ass1Mark.getText().isEmpty()) {
			try {
				student.setAss1Mark(Integer.parseInt(ass1Mark.getText()));
				// Validate assignment1 marks
				if (!student.validateMark(Integer.parseInt(ass1Mark.getText()))) {
					alert.setContentText("Invalid Assignment1 Marks!!!Enter (0 - 100)");
					hasError = true;
				}
			} catch (NumberFormatException nfe) {
				alert.setContentText("Invalid Assignment1 Marks....Enter Integer Value!!!");
				hasError = true;
			}
		} else {
			student.setAss1Mark(-1);
		}
		// Check ass2 marks field is empty
		if (!ass2Mark.getText().isEmpty()) {
			try {
				student.setAss2Mark(Integer.parseInt(ass2Mark.getText()));
				// Validate assignment2 marks
				if (!student.validateMark(Integer.parseInt(ass2Mark.getText()))) {
					alert.setContentText("Invalid Assignment2 Marks!!!Enter (0 - 100)");
					hasError = true;
				}
			} catch (NumberFormatException nfe) {
				alert.setContentText("Invalid Assignment2 Marks....Enter Integer Value!!!");
				hasError = true;
			}
		} else {
			student.setAss2Mark(-1);
		}
		// Check ass3 marks field is empty
		if (!ass3Mark.getText().isEmpty()) {
			try {
				student.setAss3Mark(Integer.parseInt(ass3Mark.getText()));
				// Validate assignment3 marks
				if (!student.validateMark(Integer.parseInt(ass3Mark.getText()))) {
					alert.setContentText("Invalid Assignment3 Marks!!!Enter (0 - 100)");
					hasError = true;
				}
			} catch (NumberFormatException nfe) {
				alert.setContentText("Invalid Assignment3 Marks....Enter Integer Value!!!");
				hasError = true;
			}
		} else {
			student.setAss3Mark(-1);
		}
		// Check exam marks field empty
		if (!examMark.getText().isEmpty()) {
			try {
				student.setExamMark(Integer.parseInt(examMark.getText()));
				// Validate exam marks
				if (!student.validateMark(Integer.parseInt(examMark.getText()))) {
					alert.setContentText("Invalid Exam Marks!!!Enter (0 - 100)");
					hasError = true;
				}
			} catch (NumberFormatException nfe) {
				alert.setContentText("Invalid Exam Marks....Enter Integer Value!!!");
				hasError = true;
			}
		} else {
			student.setExamMark(-1);
		}
		// Check result field is empty
		if (!results.getText().isEmpty()) {
			try {
				student.setResult(Float.parseFloat(results.getText()));
				// Validate results
				if (!student.validateMark((int)Float.parseFloat(results.getText()))) {
					alert.setContentText("Invalid Results !!!Enter (0 - 100)");
					hasError = true;
				}
			} catch (NumberFormatException nfe) {
				alert.setContentText("Invalid results....Enter Integer Value!!!");
				hasError = true;
			}
		} else {
			student.setResult(-1);
		}
		// check grade field is empty
		if (!grade.getText().isEmpty()) {
			student.setGrade(grade.getText());
			// Validate grade
			if (!student.validateGrade(grade.getText())) {
				alert.setContentText("Invalid Grade !!!Enter HD, D, C, PASS or FAIL");
				hasError = true;
			}
		}

		// Display the error message
		if (hasError) {
			alert.showAndWait();
		} else {
			ArrayList<Student> studentRecordList = new ArrayList<Student>();
			studentRecordList = student.addSearchList(student);
			if (!studentRecordList.isEmpty()) {
				for (int i = 0; i < studentRecordList.size(); i++) {
					studentList.add(studentRecordList.get(i));
				}
			} else {
				alertMessage.setTitle("Message");
				alertMessage.setHeaderText("You have a message!!");
				alertMessage.setContentText("No Records Find to Display!!");
				alertMessage.showAndWait();
			}
			// After click the search button clear the fields
			clearSearchFields();
		}
	}

	/**
	 * This method used to After click the search button clear the fields
	 */
	private void clearSearchFields() {
		ClearTextField();
		results.clear();
		grade.clear();
	}

	/**
	 * This method used to update the exiting records
	 * using key value student ID
	 */
	public void upateRecord() {
		// Create Top grid on GUI
	    GridPane gridPaneTop = new GridPane();

	    // call addGrid method to ad the top grid to the panel
	    gridPaneTop = addGrid();

	    // add search button for search exiting records
	    gridPaneTop.add(searchExitBt, 2, 0);
	    // Add button into grid
	    gridPaneTop.add(updateBt, 1, 7);
	    gridPaneTop.add(cancelBt, 2, 7);
	    GridPane.setHalignment(updateBt, HPos.RIGHT);
	    GridPane.setHalignment(cancelBt, HPos.RIGHT);
	    GridPane.setHalignment(searchExitBt, HPos.RIGHT);

	    // set editable false to other fields until serch the records
	    studentName.setEditable(false);
	    quizMark.setEditable(false);
	    ass1Mark.setEditable(false);
	    ass2Mark.setEditable(false);
	    ass3Mark.setEditable(false);
	    examMark.setEditable(false);

	    Scene scene = new Scene(gridPaneTop, 600, 500);
	    // Set title
	    primaryStage.setTitle("Update Record");
	    // Place the scene in the stage
	    primaryStage.setScene(scene);
	    // Display the stage
	    primaryStage.show();

	    // Process events when Insert button click
	    searchExitBt.setOnAction(e -> searchExitRecord());
	    cancelBt.setOnAction(e -> cancel(primaryStage));
	}

	/**
	 * This method used to search record using student id
	 * and display the all record details into the grid
	 * for update
	 */
	private void searchExitRecord() {
		// set error message for the error pop-up
		alert.setTitle("Error Dialog");
		alert.setHeaderText("You have an Error!!");

		// Check whether if there any blank field
		if (studentId.getText().isEmpty()) {
			alert.setContentText("Please enter the StudentId!!!");
			alert.showAndWait();
		} else {
			// Create the Student object for the setting values and validation
			Student student = new Student();
			student.setStudentID(studentId.getText());
			// Call the validation methods to validate the studentId
			// Whether have 8 numeric digit
			if (!student.validateId(studentId.getText())) {
				alert.setContentText("Invalid StudentID!!!Must be 8 Digit Numbers");
				alert.showAndWait();
			} else {
				student = student.getSearchRecords(student);
				// If query returns no records set the message
				if (student == null) {
					alertMessage.setTitle("Message");
					alertMessage.setHeaderText("You have a message!!");
					alertMessage.setContentText("No Records Find to Display!!");
					alertMessage.showAndWait();
				} else {
					// set editable true to other fields after search the records
				    studentName.setEditable(true);
				    quizMark.setEditable(true);
				    ass1Mark.setEditable(true);
				    ass2Mark.setEditable(true);
				    ass3Mark.setEditable(true);
				    examMark.setEditable(true);

				    // set all values to the text fields
				    studentName.setText(student.getStudentName());
				    quizMark.setText(String.valueOf(student.getQuizMark()));
				    ass1Mark.setText(String.valueOf(student.getAss1Mark()));
				    ass2Mark.setText(String.valueOf(student.getAss2Mark()));
				    ass3Mark.setText(String.valueOf(student.getAss3Mark()));
				    examMark.setText(String.valueOf(student.getExamMark()));

				    setEditValues(student);
				}
			}
		}
	}

	/**
	 * This method used set the edited values to the student
	 * object
	 */
	private void setEditValues(Student originalStudent) {
		Student student = new Student();

		student.setStudentID(studentId.getText());
		student.setResult(-1);
		student.setGrade(null);

		// call update method when update button clicked
	    updateBt.setOnAction(e -> update(student, originalStudent));
	}

	/**
	 * This method used to update the table with edited value
	 * 
	 * @param student
	 */
	private void update(Student newStudent, Student originalStudent) {
		boolean hasError = false;

		// Check original fields equals to eite fields
		if (!originalStudent.getStudentName().equalsIgnoreCase(studentName.getText())) {
			newStudent.setStudentName(studentName.getText());
		}
		// Check number format for int marks and parse to string
		try {
			int quiz = Integer.parseInt(quizMark.getText());
			int assignment1 = Integer.parseInt(ass1Mark.getText());
			int assignment2 = Integer.parseInt(ass2Mark.getText());
			int assignment3 = Integer.parseInt(ass3Mark.getText());
			int exam = Integer.parseInt(examMark.getText());

			if (originalStudent.getQuizMark() != quiz) {
				newStudent.setQuizMark(quiz);
				// Validate quiz marks
				if (!originalStudent.validateMark(quiz)) {
					alert.setContentText("Invalid Quiz Marks!!!Enter (0 - 100)");
					hasError = true;
				}
			} else {
				newStudent.setQuizMark(-1);
			}

			if (originalStudent.getAss1Mark() != assignment1) {
				newStudent.setAss1Mark(assignment1);
				// Validate quiz marks
				if (!originalStudent.validateMark(assignment1)) {
					alert.setContentText("Invalid Assignment1 Marks!!!Enter (0 - 100)");
					hasError = true;
				}
			} else {
				newStudent.setAss1Mark(-1);
			}

			if (originalStudent.getAss2Mark() != assignment2) {
				newStudent.setAss2Mark(assignment2);
				// Validate quiz marks
				if (!originalStudent.validateMark(assignment2)) {
					alert.setContentText("Invalid Assignment2 Marks!!!Enter (0 - 100)");
					hasError = true;
				}
			} else {
				newStudent.setAss2Mark(-1);
			}

			if (originalStudent.getAss3Mark() != assignment3) {
				newStudent.setAss3Mark(assignment3);
				// Validate quiz marks
				if (!originalStudent.validateMark(assignment3)) {
					alert.setContentText("Invalid Assignment3 Marks!!!Enter (0 - 100)");
					hasError = true;
				}
			} else {
				newStudent.setAss3Mark(-1);
			}

			if (originalStudent.getExamMark() != exam) {
				newStudent.setExamMark(exam);
				// Validate quiz marks
				if (!originalStudent.validateMark(exam)) {
					alert.setContentText("Invalid Exam Marks!!!Enter (0 - 100)");
					hasError = true;
				}
			} else {
				newStudent.setExamMark(-1);
			}

			// show the error message if have validation error
			// if not update the process
			if (hasError) {
				alert.showAndWait();
			} else {
				Student studentCal = new Student();
				if (newStudent.getQuizMark() >= 0 || newStudent.getAss1Mark() >= 0 ||
						newStudent.getAss2Mark() >= 0 || newStudent.getAss3Mark() >= 0 ||
								newStudent.getExamMark() >= 0) {
					studentCal.setQuizMark(Integer.parseInt(quizMark.getText()));
					studentCal.setAss1Mark(Integer.parseInt(ass1Mark.getText()));
					studentCal.setAss2Mark(Integer.parseInt(ass2Mark.getText()));
					studentCal.setAss3Mark(Integer.parseInt(ass3Mark.getText()));
					studentCal.setExamMark(Integer.parseInt(examMark.getText()));

					// Calculate the results
					float result = studentCal.calculateTotal();
					// Set results and grade properties to the student object
					newStudent.setResult(result);
					newStudent.setGrade(studentCal.calculateGrade(result));
				}
				newStudent.updateTable(newStudent);
				ClearTextField();
			}
		} catch (NumberFormatException nfe) {
			alert.setContentText("Invalid Marks....Enter Integer Value!!!");
			alert.showAndWait();
		}
	}
}
