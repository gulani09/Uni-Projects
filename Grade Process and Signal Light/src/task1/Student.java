//File : GradeProcessing.java
//Course Name : ITC521 - Programming in Java 2
//Assessment Item : Assignment 2, Task1
//Instructor Name : Recep Ulusoy
//Date : 14 September 2017
//Due on : 22 Septembe 2017
//Student Id : 11619843
//Student Name : Gulani Senthuran

package task1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * This file used to implement the Student class and test the grade processing for the students.
 * This file contains student properties and its setters and getters.
 * averageMarks method calculate the average marks among the students and studentsMark method 
 * calculate the students results and grade.
 * 
 * @author Gulani Senthuran
 *
 */
public class Student {

	private String studentID;
	private String studentName;
	private int quizMark;
	private int ass1Mark;
	private int ass2Mark;
	private int ass3Mark;
	private int examMark;
	private float result;
	private String grade;
	private boolean isError;

	// Default constructor
	public Student() {
		
	}

	/**
	 * 
	 * @return
	 */
	public String getStudentID() {
		return studentID;
	}

	/**
	 * 
	 * @param studentID
	 */
	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	/**
	 * 
	 * @return
	 */
	public String getStudentName() {
		return studentName;
	}

	/**
	 * 
	 * @param studentName
	 */
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	/**
	 * 
	 * @return
	 */
	public int getQuizMark() {
		return quizMark;
	}

	/**
	 * 
	 * @param quizMark
	 */
	public void setQuizMark(int quizMark) {
		this.quizMark = quizMark;
	}

	/**
	 * 
	 * @return
	 */
	public int getAss1Mark() {
		return ass1Mark;
	}

	/**
	 * 
	 * @param ass1Mark
	 */
	public void setAss1Mark(int ass1Mark) {
		this.ass1Mark = ass1Mark;
	}

	/**
	 * 
	 * @return
	 */
	public int getAss2Mark() {
		return ass2Mark;
	}

	/**
	 * 
	 * @param ass2Mark
	 */
	public void setAss2Mark(int ass2Mark) {
		this.ass2Mark = ass2Mark;
	}

	/**
	 * 
	 * @return
	 */
	public int getAss3Mark() {
		return ass3Mark;
	}

	/**
	 * 
	 * @param ass3Mark
	 */
	public void setAss3Mark(int ass3Mark) {
		this.ass3Mark = ass3Mark;
	}

	/**
	 * 
	 * @return
	 */
	public int getExamMark() {
		return examMark;
	}

	/**
	 * 
	 * @param examMark
	 */
	public void setExamMark(int examMark) {
		this.examMark = examMark;
	}

	/**
	 * 
	 * @return
	 */
	public float getResult() {
		return result;
	}

	/**
	 * 
	 * @param result
	 */
	public void setResult(float result) {
		this.result = result;
	}

	/**
	 * 
	 * @return
	 */
	public String getGrade() {
		return grade;
	}

	/**
	 * 
	 * @param grade
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isError() {
		return isError;
	}

	/**
	 * 
	 * @param isError
	 */
	public void setError(boolean isError) {
		this.isError = isError;
	}

	/**
	 * This method used to validate the studentId
	 * 
	 * @param ass2Mark2
	 * @return boolean
	 */
	public boolean validateMark(int marks) {
		boolean isValid = false;
		// Check marks is integer and it is between 0-100
		if (marks >= 0 && marks <= 100) {
			isValid = true;
		}
		return isValid;
	}

	/**
	 * This method used to validate the all marks
	 * 
	 * @param studentID
	 * @return boolean
	 */
	public boolean validateId(String studentID) {
		String numericRegex = "\\d+";
		boolean isValid = false;
		// Check student id with 8 character an all digit
		if (studentID != null && studentID.length() == 8) {
			for (int i = 0; i < studentID.length(); i++) {
				if (studentID.matches(numericRegex)) {
					isValid = true;
				}
			}
		}
		return isValid;
	}

	/**
	 * This method used to calculate the all final results for students
	 * 
	 * @return float
	 */
	public float calculateTotal() {
		return (float) ((quizMark * 0.05) + (ass1Mark * 0.15) + (ass2Mark * 0.20) + (ass3Mark * 0.10) +(examMark * 0.5));
	}

	/**
	 * This method used to calculate the grade according to the results
	 * 
	 * @param results
	 * @return String
	 */
	public String calculateGrade(float results) {
		String grade = null;
		if (results >= 85) {
			grade = "HD";
		} else if (results >= 75) {
			grade = "DI";
		} else if (results >= 65) {
			grade = "CR";
		} else if (results >= 50) {
			grade = "PASS";
		} else {
			grade = "FAIL";
		}
		return grade;
	}

	/**
	 * This method used to connect the database and insert the records 
	 * in to the ITC521 table
	 */
	public void insertRecordTable() {
		String url="jdbc:mysql://localhost/gradeprocessing";
		String userName="root";
		String password="root";
		// Load the JDBC driver
	    try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	    // Establish a connection
	    Connection connection = null;
	    PreparedStatement statement = null;
		try {
			connection = DriverManager.getConnection(url, userName, password);

			// Sql insert query
		    String query = "INSERT INTO ITC521 (StudentID, StudentName, Quiz, Assignment1, Assignment2, Assignment3, Exam, Results, Grade) values ("
		    				+ "?,?,?,?,?,?,?,?,?)";
		    statement = (PreparedStatement) connection.prepareStatement(query);
		    // set the values
		    statement.setString(1, this.getStudentID());
		    statement.setString(2, this.getStudentName());
		    statement.setInt(3, this.getQuizMark());
		    statement.setInt(4, this.getAss1Mark());
		    statement.setInt(5, this.getAss2Mark());
		    statement.setInt(6, this.getAss3Mark());
		    statement.setInt(7, this.getExamMark());
		    statement.setFloat(8, this.getResult());
		    statement.setString(9, this.getGrade());

		    // Execute the query
		    statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method used to validate the grade include 
	 * (HD, D, C, PASS, FAIL)
	 * 
	 * @param grade
	 * @return boolean
	 */
	public boolean validateGrade(String grade) {
		if (grade.equalsIgnoreCase("HD") || grade.equalsIgnoreCase("D") ||
			grade.equalsIgnoreCase("C") || grade.equalsIgnoreCase("PASS") ||
			grade.equalsIgnoreCase("FAIL")) {
			return true;
		}
		return false;
	}

	/**
	 * This method use to connect database and retrieve the specific 
	 * records based on the request and add to the
	 * student list
	 * 
	 * @param student
	 * @return ArrayList<Student>
	 */
	public ArrayList<Student> addSearchList(Student student) {
		// Create student array list
		ArrayList<Student> studentRecordList = new ArrayList<Student>();

		// database connection url, username, password
		String url="jdbc:mysql://localhost/gradeprocessing";
		String userName="root";
		String password="root";
		// Load the JDBC driver
	    try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	    // Establish a connection
	    Connection connection = null;
	    Statement statement = null;
		try {
			connection = DriverManager.getConnection(url, userName, password);
			statement = connection.createStatement();
			// Sql select query
			StringBuffer query = new StringBuffer("SELECT * FROM ITC521 WHERE ");
		    if (student.getStudentID() != null) {
		    	query.append("StudentID = '" + student.getStudentID() + "' AND ");
		    }
		    if (student.getStudentName() != null) {
		    	query.append("StudentName = '" + student.getStudentName() + "' AND ");
		    }
		    if (student.getQuizMark() >= 0) {
		    	query.append("Quiz = " + student.getQuizMark() + " AND ");
		    }
		    if (student.getAss1Mark() >= 0) {
		    	query.append("Assignment1 = " + student.getAss1Mark() + " AND ");
		    }
		    if (student.getAss2Mark() >= 0) {
		    	query.append("Assignment2 = " + student.getAss2Mark() + " AND ");
		    }
		    if (student.getAss3Mark() >= 0) {
		    	query.append("Assignment3 = " + student.getAss3Mark() + " AND ");
		    }
		    if (student.getExamMark() >= 0) {
		    	query.append("Exam = " + student.getExamMark() + " AND ");
		    }
		    if (student.getResult() >= 0) {
		    	query.append("Results = " + student.getResult() + " AND ");
		    }
		    if (student.getGrade() != null) {
		    	query.append("Grade = '" + student.getGrade() + "' AND ");
		    }
		    // Remove last ' AND ' letters from string query
		    query.delete(query.length() - 5, query.length() - 0);

			// execute select SQL stetement
			ResultSet resultSet = statement.executeQuery(query.toString());

			// Insert all retrieved records into student array list
			while (resultSet.next()) {
				Student studentRecords = new Student();
				studentRecords.setStudentID(resultSet.getString("studentID"));
				studentRecords.setStudentName(resultSet.getString("StudentName"));
				studentRecords.setQuizMark(resultSet.getInt("Quiz"));
				studentRecords.setAss1Mark(resultSet.getInt("Assignment1"));
				studentRecords.setAss2Mark(resultSet.getInt("Assignment2"));
				studentRecords.setAss3Mark(resultSet.getInt("Assignment3"));
				studentRecords.setExamMark(resultSet.getInt("Exam"));
				studentRecords.setResult(resultSet.getFloat("Results"));
				studentRecords.setGrade(resultSet.getString("Grade"));

				// ad student object to list
				studentRecordList.add(studentRecords);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return studentRecordList;
	}

	/**
	 * This method used to connect the database and retrieve the records
	 * using student id
	 * 
	 * @param student
	 * @return Student
	 */
	public Student getSearchRecords(Student student) {
		// database connection url, username, password
		String url="jdbc:mysql://localhost/gradeprocessing";
		String userName="root";
		String password="root";
		// Load the JDBC driver
	    try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	    // Establish a connection
	    Connection connection = null;
	    Statement statement = null;
	    try {
	    	connection = DriverManager.getConnection(url, userName, password);
			statement = connection.createStatement();
			// Sql select query
			StringBuffer query = new StringBuffer("SELECT * FROM ITC521 WHERE ");
		    if (student.getStudentID() != null) {
		    	query.append("StudentID = '" + student.getStudentID() + "'");
		    }

		 // execute select SQL stetement
		 ResultSet resultSet = statement.executeQuery(query.toString());
		 if (resultSet.next()) {
			 student.setStudentName(resultSet.getString("StudentName"));
			 student.setQuizMark(resultSet.getInt("Quiz"));
			 student.setAss1Mark(resultSet.getInt("Assignment1"));
			 student.setAss2Mark(resultSet.getInt("Assignment2"));
			 student.setAss3Mark(resultSet.getInt("Assignment3"));
			 student.setExamMark(resultSet.getInt("Exam"));
		 } else {
			 student = null;
		 }
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return student;
	}

	/**
	 * This method used to connect the database and create the query
	 * for the update the table with the edited value
	 * 
	 * @param student
	 */
	public void updateTable(Student student) {
		// database connection url, username, password
		String url="jdbc:mysql://localhost/gradeprocessing";
		String userName="root";
		String password="root";
		// Load the JDBC driver
	    try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	    // Establish a connection
	    Connection connection = null;
	    Statement statement = null;
	    try {
	    	connection = DriverManager.getConnection(url, userName, password);
			statement = connection.createStatement();
			// Sql select query
			StringBuffer query = new StringBuffer("UPDATE ITC521 SET ");
			if (student.getStudentName() != null) {
		    	query.append("StudentName = '" + student.getStudentName() + "', ");
		    }
		    if (student.getQuizMark() >= 0) {
		    	query.append("Quiz = " + student.getQuizMark() + ", ");
		    }
		    if (student.getAss1Mark() >= 0) {
		    	query.append("Assignment1 = " + student.getAss1Mark() + ", ");
		    }
		    if (student.getAss2Mark() >= 0) {
		    	query.append("Assignment2 = " + student.getAss2Mark() + ", ");
		    }
		    if (student.getAss3Mark() >= 0) {
		    	query.append("Assignment3 = " + student.getAss3Mark() + ", ");
		    }
		    if (student.getExamMark() >= 0) {
		    	query.append("Exam = " + student.getExamMark() + ", ");
		    }
		    if (student.getResult() >= 0) {
		    	query.append("Results = " + student.getResult() + ", ");
		    }
		    if (student.getGrade() != null) {
		    	query.append("Grade = '" + student.getGrade() + "', ");
		    }
		    // Remove last ' AND ' letters from string query
		    query.delete(query.length() - 2, query.length() - 0);
		    query.append(" WHERE StudentID = '" + student.getStudentID() + "'");
		    // Execute the query
		    statement.executeUpdate(query.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
