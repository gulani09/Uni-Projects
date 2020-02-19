//File : Application.java
//Course Name : ITC508 - Object Modelling
//Assessment Item : Assignment 3
//Instructor Name : Dr Naveed Ali
//Date : 09 May 2018
//Due on : 25 May 2017
//Student Id : 11619843
//Student Name : Gulani Senthuran

package view;

import java.io.File;

import controller.Balance;
import controller.CardController;
import controller.Transfer;
import controller.Withdrawal;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.Account;
import model.Card;
/**
 * This file used to implement the the transaction for the ATM. It contains Javafx application and 
 * data base connection to processing the records. He re this application able to do the 
 * Withdrawal, deposit, transfer, balance check.
 * 
 * @author Gulani Senthuran
 *
 */
public class ApplicationATM extends Application {

	Stage primaryApp = new Stage();
	private Label welcome = new Label("Welcome to Collin's ATM");

	// Create the text fields in the GUI
	private final PasswordField pin = new PasswordField();
	private TextField trnsferAccountNo = new TextField();

	private Button enter = new Button("ENTER");
	private Button ok = new Button("OK");
	private Button confirm = new Button("CONFIRM");

	private Label messageLabel = new Label("Enter the pin code and Press Enter ");
	private Label success = new Label("Your Transaction Successfully Completed");
	private Label accBalance = new Label("Your Account Balance is :\n$");
	private Label balance =  new Label();
	private Label enterAccountNo = new Label("Enter the account number, \nwhich you want to transfer money: ");

	private String transferAcc1 = "";
	private String transferAcc2 = "";
	ImageView imv = new ImageView();

	private Label copyRight = new Label("Copyright © 2018. All rights reserved.");

	// Create error popup
	Alert alert = new Alert(AlertType.ERROR);
	Alert alertMessage = new Alert(AlertType.INFORMATION);

	// Application start method
	public void start(Stage primaryStage) throws Exception {
		VBox rootPane = new VBox();

		// Create hbox for the header of the application
		HBox hBox = new HBox();
		hBox = heading();

		// Create the grid object
		GridPane gridPaneMiddle = new GridPane();
		gridPaneMiddle.setHgap(15);
		gridPaneMiddle.setVgap(15);

	    // Add the text field, label in to the grid
		gridPaneMiddle.add(messageLabel, 0, 0);
	    messageLabel.setTextFill(Color.DARKRED);
	    messageLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 25));

		gridPaneMiddle.add(pin, 0, 1);
		pin.setPrefHeight(50);
		pin.setPrefWidth(70);
		pin.setAlignment(Pos.CENTER);
		pin.setFont(Font.font("Times New Roman", FontWeight.BOLD, 40));
	    // Add exit button into the grid pane
	    gridPaneMiddle.add(enter, 0, 2);
	    enter.setPrefSize(150, 50);
	    enter.setStyle("-fx-background-color: #8B0000;");
	    enter.setTextFill(Color.WHITE);
	    enter.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));

	    // Set properties alignment for label UI
	    gridPaneMiddle.setAlignment(Pos.BOTTOM_CENTER);
	    GridPane.setHalignment(enter, HPos.RIGHT);

	    GridPane gridPaneBottom = new GridPane();
	    gridPaneBottom.setHgap(15);
	    gridPaneBottom.setVgap(15);
	    // Add the text field, label in to the grid
	    gridPaneBottom.add(copyRight, 0, 0);
	    // Set properties alignment for label UI
	    gridPaneBottom.setAlignment(Pos.BOTTOM_CENTER);

	    // Create VBox object
		VBox vbox = createVBox();
		
        vbox.getChildren().addAll(gridPaneMiddle);

        rootPane.getChildren().addAll(hBox, vbox, gridPaneBottom);
		Scene scene = new Scene(rootPane, 750, 550, Color.BLACK);
		// Set title
		primaryStage.setTitle("Collin's ATM");
		// Place the scene in the stage
		primaryStage.setScene(scene);
		// Display the stage
		primaryStage.show();

		// Process events when send button click
		enter.setOnAction(e -> pinValidation(pin.getText(), primaryStage));
	}

	/**
	 * This method used to create middle background 
	 * @return
	 */
	private VBox createVBox() {
		VBox vbox = new VBox();
		vbox.setPrefHeight(450);
		vbox.setPrefWidth(650);
		vbox.setSpacing(20);
        vbox.setPadding(new Insets(20, 20, 20, 20));
        vbox.setStyle("-fx-background-color: #FFFFFF;");
		return vbox;
	}

	private HBox heading() {
		HBox box = new HBox();
		box.setPrefHeight(10);
		box.setPrefWidth(650);
		box.setSpacing(20);
		box.setPadding(new Insets(10, 10, 10, 10));
		box.setStyle("-fx-background-color: #8B0000;");

		// insert bank logo
		Image image = new Image(new File("logo.png").toURI().toString());
        imv.setImage(image);
	    // Set fill text color for label
	    welcome.setTextFill(Color.WHITE);
	    welcome.setFont(Font.font("Times New Roman", FontWeight.BOLD, 35));
	    welcome.setAlignment(Pos.CENTER);
	    box.getChildren().addAll(imv, welcome);
		return box;
	}

	/**
	 * This method used to validate the pin number
	 * @param primaryStage2 
	 * 
	 * @param pin
	 */
	private void pinValidation(String pinNo, Stage primaryStage) {
		boolean hasError = false;
		Card card = null;
		try {
			// convert password to integer
			int pinId = Integer.parseInt(pinNo);
			CardController cardController = new CardController();
			// validate the pin id from cardController
			card = cardController.pinValidation(pinId);
			if (card == null) {
				hasError = true;
			}
		} catch (NumberFormatException nfe) {
			hasError = true;
		}

		// if there is any error in the pin id display the error
		if (hasError) {
			alert.setContentText("Invalid Pin Number!! Please Enter 4 Digit Pin");
			alert.showAndWait();
			pin.clear();
		} else {
			createAccountPopup(card, primaryStage);
		}
	}

	/**
	 * Create a popup panel for a account selection
	 * @param card 
	 * @param primaryStage 
	 */
	private void createAccountPopup(Card card, Stage primaryStage) {

		// Create message
		Label message  = new Label("Select the Account Type:");

		// Create button
		Button saveings = new Button("Savings Account");
		Button credit = new Button("Credit Account");
		VBox rootPane = new VBox();

		// Create hbox for the header of the application
		HBox hBox = new HBox();
		hBox = heading();

		// Create the grid object
		GridPane gridPaneMiddle = new GridPane();
		gridPaneMiddle.setHgap(15);
		gridPaneMiddle.setVgap(15);

	    // Add the text field, label in to the grid
		gridPaneMiddle.add(message, 0, 0);
		message.setTextFill(Color.DARKRED);
		message.setFont(Font.font("Times New Roman", FontWeight.BOLD, 25));

	    // Add exit button into the grid pane
	    gridPaneMiddle.add(saveings, 1, 2);
	    saveings.setPrefSize(250, 50);
	    saveings.setStyle("-fx-background-color: #8B0000;");
	    saveings.setTextFill(Color.WHITE);
	    saveings.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));

	    gridPaneMiddle.add(credit, 1, 3);
	    credit.setPrefSize(250, 50);
	    credit.setStyle("-fx-background-color: #8B0000;");
	    credit.setTextFill(Color.WHITE);
	    credit.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));

	    // Set properties alignment for label UI
	    gridPaneMiddle.setAlignment(Pos.BOTTOM_CENTER);

	    GridPane gridPaneBottom = new GridPane();
	    gridPaneBottom.setHgap(15);
	    gridPaneBottom.setVgap(15);
	    // Add the text field, label in to the grid
	    gridPaneBottom.add(copyRight, 0, 0);
	    // Set properties alignment for label UI
	    gridPaneBottom.setAlignment(Pos.BOTTOM_CENTER);

	    // Create VBox object
		VBox vbox = createVBox();
		
        vbox.getChildren().addAll(gridPaneMiddle);

        rootPane.getChildren().addAll(hBox, vbox, gridPaneBottom);
		Scene scene = new Scene(rootPane, 750, 550, Color.BLACK);
		// Set title
		primaryStage.setTitle("Collin's ATM");
		// Place the scene in the stage
		primaryStage.setScene(scene);
		// Display the stage
		primaryStage.show();

		// Click savings account
		saveings.setOnAction(e -> createTransactionPopup(card, primaryStage, "SAVINGS"));
		credit.setOnAction(e -> createTransactionPopup(card, primaryStage, "CREDIT"));
	}

	/**
	 * Create t
	 * @param card
	 * @param primaryStage
	 * @param accType 
	 */
	private void createTransactionPopup(Card card, Stage primaryStage, String accType) {
		Account account = new Account();
		account.setCustomerId(card.getCustomerId());
		if (accType.equalsIgnoreCase("SAVINGS")) {
			account.setAccountType(1);
		} else {
			account.setAccountType(2);
		}

		// Create transaction popup
		Label message  = new Label("Select the Transaction Type:");

		// Create button
		Button withdrawal = new Button("Withdrawal");
		Button deposit = new Button("Deposit");
		Button transfer = new Button("Transfer");
		Button checkBalance = new Button("Balance Inquiry");
		VBox rootPane = new VBox();

		// Create hbox for the header of the application
		HBox hBox = new HBox();
		hBox = heading();

		// Create the grid object
		GridPane gridPaneMiddle = new GridPane();
		gridPaneMiddle.setHgap(15);
		gridPaneMiddle.setVgap(15);

	    // Add the text field, label in to the grid
		gridPaneMiddle.add(message, 0, 0);
		message.setTextFill(Color.DARKRED);
		message.setFont(Font.font("Times New Roman", FontWeight.BOLD, 25));

	    // Add withdrawal button into the grid pane
	    gridPaneMiddle.add(withdrawal, 1, 2);
	    withdrawal.setPrefSize(250, 50);
	    withdrawal.setStyle("-fx-background-color: #8B0000;");
	    withdrawal.setTextFill(Color.WHITE);
	    withdrawal.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
	    GridPane.setHalignment(withdrawal, HPos.RIGHT);

	    // Add deposit button into the gridpane
	    gridPaneMiddle.add(deposit, 1, 3);
	    deposit.setPrefSize(250, 50);
	    deposit.setStyle("-fx-background-color: #8B0000;");
	    deposit.setTextFill(Color.WHITE);
	    deposit.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
	    GridPane.setHalignment(deposit, HPos.RIGHT);

	    // Add transfer button into the gridpane
	    gridPaneMiddle.add(transfer, 1, 4);
	    transfer.setPrefSize(250, 50);
	    transfer.setStyle("-fx-background-color: #8B0000;");
	    transfer.setTextFill(Color.WHITE);
	    transfer.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
	    GridPane.setHalignment(transfer, HPos.RIGHT);

	    // Add transfer button into the gridpane
	    gridPaneMiddle.add(checkBalance, 1, 5);
	    checkBalance.setPrefSize(250, 50);
	    checkBalance.setStyle("-fx-background-color: #8B0000;");
	    checkBalance.setTextFill(Color.WHITE);
	    checkBalance.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
	    GridPane.setHalignment(checkBalance, HPos.RIGHT);

	    // Set properties alignment for label UI
	    gridPaneMiddle.setAlignment(Pos.BOTTOM_CENTER);

	    GridPane gridPaneBottom = new GridPane();
	    gridPaneBottom.setHgap(15);
	    gridPaneBottom.setVgap(15);
	    // Add the text field, label in to the grid
	    gridPaneBottom.add(copyRight, 0, 0);
	    // Set properties alignment for label UI
	    gridPaneBottom.setAlignment(Pos.BOTTOM_CENTER);

	    // Create VBox object
		VBox vbox = createVBox();

        vbox.getChildren().addAll(gridPaneMiddle);

        rootPane.getChildren().addAll(hBox, vbox, gridPaneBottom);
		Scene scene = new Scene(rootPane, 750, 550, Color.BLACK);
		// Set title
		primaryStage.setTitle("Collin's ATM");
		// Place the scene in the stage
		primaryStage.setScene(scene);
		// Display the stage
		primaryStage.show();

		// button click functionality
		// Click savings account
		withdrawal.setOnAction(e -> createWithdrawalDepositPopup(account, null, primaryStage, withdrawal.getText()));
		deposit.setOnAction(e -> createWithdrawalDepositPopup(account, null, primaryStage, deposit.getText()));
		transfer.setOnAction(e -> createTransferPopup(account, primaryStage, transfer.getText()));
		checkBalance.setOnAction(e -> createBalancePopup(account, primaryStage));
	}

	/**
	 * This method used to display the account balance to the user
	 * 
	 * @param account
	 * @param primaryStage
	 * @param transactionType
	 */
	private void createBalancePopup(Account account, Stage primaryStage) {
		Balance balance = new Balance();
		Account accountDetails = balance.retriveAccount(account);
		balanceDisplay(accountDetails, primaryStage);
	}

	/**
	 * This method use to display the balance the in the popup panel.
	 * 
	 * @param accountDetails
	 */
	private void balanceDisplay(Account accountDetails, Stage primaryStage) {
		VBox rootPane = new VBox();

		// Create hbox for the header of the application
		HBox hBox = new HBox();
		hBox = heading();

		// Create the grid object
		GridPane gridPaneMiddle = new GridPane();
		gridPaneMiddle.setHgap(15);
		gridPaneMiddle.setVgap(15);

	    // Add the text field, label in to the grid
		gridPaneMiddle.add(accBalance, 0, 0);
		accBalance.setTextFill(Color.DARKRED);
		accBalance.setFont(Font.font("Times New Roman", FontWeight.BOLD, 25));

		balance.setText(accountDetails.getBalance()+"");
		gridPaneMiddle.add(balance, 0, 1);
		balance.setTextFill(Color.DARKRED);
		balance.setFont(Font.font("Times New Roman", FontWeight.BOLD, 25));

	    gridPaneMiddle.add(ok, 1, 2);
	    ok.setPrefSize(150, 50);
	    ok.setStyle("-fx-background-color: #8B0000;");
	    ok.setTextFill(Color.WHITE);
	    ok.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));

	    // Set properties alignment for label UI
	    gridPaneMiddle.setAlignment(Pos.BOTTOM_CENTER);
	    GridPane.setHalignment(ok, HPos.RIGHT);

	    GridPane gridPaneBottom = new GridPane();
	    gridPaneBottom.setHgap(15);
	    gridPaneBottom.setVgap(15);
	    // Add the text field, label in to the grid
	    gridPaneBottom.add(copyRight, 0, 0);
	    // Set properties alignment for label UI
	    gridPaneBottom.setAlignment(Pos.BOTTOM_CENTER);

	    // Create VBox object
		VBox vbox = createVBox();
		
        vbox.getChildren().addAll(gridPaneMiddle);

        rootPane.getChildren().addAll(hBox, vbox, gridPaneBottom);
		Scene scene = new Scene(rootPane, 750, 550, Color.BLACK);
		// Set title
		primaryStage.setTitle("Collin's ATM");
		// Place the scene in the stage
		primaryStage.setScene(scene);
		// Display the stage
		primaryStage.show();

		// button function
		ok.setOnAction(e -> reloadPage(primaryStage));
	}

	private void createTransferPopup(Account account, Stage primaryStage, String transactionType) {
		VBox rootPane = new VBox();

		// Create hbox for the header of the application
		HBox hBox = new HBox();
		hBox = heading();

		// Create the grid object
		GridPane gridPaneMiddle = new GridPane();
		gridPaneMiddle.setHgap(15);
		gridPaneMiddle.setVgap(15);

	    // Add the text field, label in to the grid
		gridPaneMiddle.add(enterAccountNo, 0, 0);
		enterAccountNo.setTextFill(Color.DARKRED);
		enterAccountNo.setFont(Font.font("Times New Roman", FontWeight.BOLD, 25));

		gridPaneMiddle.add(trnsferAccountNo, 0, 1);
		trnsferAccountNo.setFont(Font.font("Times New Roman", FontWeight.BOLD, 25));

	    gridPaneMiddle.add(confirm, 1, 2);
	    confirm.setPrefSize(150, 50);
	    confirm.setStyle("-fx-background-color: #8B0000;");
	    confirm.setTextFill(Color.WHITE);
	    confirm.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));

	    // Set properties alignment for label UI
	    gridPaneMiddle.setAlignment(Pos.BOTTOM_CENTER);
	    GridPane.setHalignment(ok, HPos.RIGHT);

	    GridPane gridPaneBottom = new GridPane();
	    gridPaneBottom.setHgap(15);
	    gridPaneBottom.setVgap(15);
	    // Add the text field, label in to the grid
	    gridPaneBottom.add(copyRight, 0, 0);
	    // Set properties alignment for label UI
	    gridPaneBottom.setAlignment(Pos.BOTTOM_CENTER);

	    // Create VBox object
		VBox vbox = createVBox();
		
        vbox.getChildren().addAll(gridPaneMiddle);

        rootPane.getChildren().addAll(hBox, vbox, gridPaneBottom);
		Scene scene = new Scene(rootPane, 750, 550, Color.BLACK);
		// Set title
		primaryStage.setTitle("Collin's ATM");
		// Place the scene in the stage
		primaryStage.setScene(scene);
		// Display the stage
		primaryStage.show();

		// buttion click action
		confirm.setOnAction(e -> createTransfer1(account, primaryStage, transactionType, trnsferAccountNo.getText()));
	}

	private void createTransfer1(Account account, Stage primaryStage, String transactionType, String transAccNo1) {
		trnsferAccountNo.clear();
		if (!transAccNo1.isEmpty()) {
			transferAcc1 = transAccNo1;
			trnsferAccountNo.clear();
			createTransferPopup2(account, primaryStage, transactionType);
		} else {
			alert.setContentText("Please Enter Transfer Account Number:");
			alert.showAndWait();
		}
	}

	private void createTransferPopup2(Account account, Stage primaryStage, String transactionType) {
		VBox rootPane = new VBox();

		// Create hbox for the header of the application
		HBox hBox = new HBox();
		hBox = heading();

		// Create the grid object
		GridPane gridPaneMiddle = new GridPane();
		gridPaneMiddle.setHgap(15);
		gridPaneMiddle.setVgap(15);

	    // Add the text field, label in to the grid
		gridPaneMiddle.add(enterAccountNo, 0, 0);
		enterAccountNo.setTextFill(Color.DARKRED);
		enterAccountNo.setFont(Font.font("Times New Roman", FontWeight.BOLD, 25));

		gridPaneMiddle.add(trnsferAccountNo, 0, 1);
		trnsferAccountNo.setFont(Font.font("Times New Roman", FontWeight.BOLD, 25));

	    gridPaneMiddle.add(confirm, 1, 2);
	    confirm.setPrefSize(150, 50);
	    confirm.setStyle("-fx-background-color: #8B0000;");
	    confirm.setTextFill(Color.WHITE);
	    confirm.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));

	    // Set properties alignment for label UI
	    gridPaneMiddle.setAlignment(Pos.BOTTOM_CENTER);
	    GridPane.setHalignment(ok, HPos.RIGHT);

	    GridPane gridPaneBottom = new GridPane();
	    gridPaneBottom.setHgap(15);
	    gridPaneBottom.setVgap(15);
	    // Add the text field, label in to the grid
	    gridPaneBottom.add(copyRight, 0, 0);
	    // Set properties alignment for label UI
	    gridPaneBottom.setAlignment(Pos.BOTTOM_CENTER);

	    // Create VBox object
		VBox vbox = createVBox();
		
        vbox.getChildren().addAll(gridPaneMiddle);

        rootPane.getChildren().addAll(hBox, vbox, gridPaneBottom);
		Scene scene = new Scene(rootPane, 750, 550, Color.BLACK);
		// Set title
		primaryStage.setTitle("Collin's ATM");
		// Place the scene in the stage
		primaryStage.setScene(scene);
		// Display the stage
		primaryStage.show();

		// buttion click action
		confirm.setOnAction(e -> createTransfer2(account, primaryStage, transactionType, trnsferAccountNo.getText()));
	}

	private void createTransfer2(Account account, Stage primaryStage, String transactionType, String transAccNo2) {
		trnsferAccountNo.clear();
		Account transferAccount = null;
		if (!transAccNo2.isEmpty()) {
			transferAcc2 = transAccNo2;
			// Check the two transaction account number
			if (transferAcc1.equals(transferAcc2)) {
				Transfer transfer = new Transfer();
				transferAccount = transfer.searchAccountNo(transferAcc1);
				if (transferAccount != null) {
					createWithdrawalDepositPopup(account, transferAccount, primaryStage, transactionType);
				} else {
					alert.setContentText("Transfer Account Number is incorrect!!\nNo Customer in this Account!!");
					alert.showAndWait();
					createTransferPopup(transferAccount, primaryStage, transactionType);
				}
			} else {
				alert.setContentText("Transfer Account Number is incorrect!!\nPlease Enter Correct Number!!");
				alert.showAndWait();
				createTransferPopup(transferAccount, primaryStage, transactionType);
			}
		} else {
			alert.setContentText("Please Enter Transfer Account Number:");
			alert.showAndWait();
		}
	}

	/**
	 * This method used to create withdrawal popup
	 * 
	 * @param account
	 * @param primaryStage
	 * @param string
	 */
	private void createWithdrawalDepositPopup(Account account,  Account  transferAccount, Stage primaryStage, String transactionType) {

		// Create transaction popup
		Label message  = new Label("Please Select or \nEnter the Amount:\n$");

		TextField amount = new TextField();
		// Create button
		Button twenty = new Button("$20");
		Button fifty = new Button("$50");
		Button hundred = new Button("$100");
		Button twoHundred = new Button("$200");
		Button enter = new Button("ENTER");

		VBox rootPane = new VBox();

		// Create hbox for the header of the application
		HBox hBox = new HBox();
		hBox = heading();

		// Create the grid object
		GridPane gridPaneMiddle = new GridPane();
		gridPaneMiddle.setHgap(15);
		gridPaneMiddle.setVgap(15);

	    // Add the text field, label in to the grid
		gridPaneMiddle.add(message, 1, 0);
		message.setTextFill(Color.DARKRED);
		message.setFont(Font.font("Times New Roman", FontWeight.BOLD, 25));

		gridPaneMiddle.add(amount, 1, 1);
		amount.setPrefWidth(80);
		amount.setPrefHeight(50);
		amount.setAlignment(Pos.CENTER);
		amount.setFont(Font.font("Times New Roman", FontWeight.BOLD, 35));
	    // Add twenty button into the grid pane
	    gridPaneMiddle.add(twenty, 0, 1);
	    twenty.setPrefSize(150, 50);
	    twenty.setStyle("-fx-background-color: #8B0000;");
	    twenty.setTextFill(Color.WHITE);
	    twenty.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
	    GridPane.setHalignment(twenty, HPos.RIGHT);

	    // Add fifty button into the gridpane
	    gridPaneMiddle.add(fifty, 0, 2);
	    fifty.setPrefSize(150, 50);
	    fifty.setStyle("-fx-background-color: #8B0000;");
	    fifty.setTextFill(Color.WHITE);
	    fifty.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
	    GridPane.setHalignment(fifty, HPos.RIGHT);

	    // Add hundred button into the gridpane
	    gridPaneMiddle.add(hundred, 0, 3);
	    hundred.setPrefSize(150, 50);
	    hundred.setStyle("-fx-background-color: #8B0000;");
	    hundred.setTextFill(Color.WHITE);
	    hundred.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
	    GridPane.setHalignment(hundred, HPos.RIGHT);

	    // Add twoHundred button into the gridpane
	    gridPaneMiddle.add(twoHundred, 0, 4);
	    twoHundred.setPrefSize(150, 50);
	    twoHundred.setStyle("-fx-background-color: #8B0000;");
	    twoHundred.setTextFill(Color.WHITE);
	    twoHundred.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
	    GridPane.setHalignment(twoHundred, HPos.RIGHT);

	    // Add enter button into the gridpane
	    gridPaneMiddle.add(enter, 2, 3);
	    enter.setPrefSize(200, 50);
	    enter.setStyle("-fx-background-color: #8B0000;");
	    enter.setTextFill(Color.WHITE);
	    enter.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
	    GridPane.setHalignment(enter, HPos.RIGHT);

	    // Set properties alignment for label UI
	    gridPaneMiddle.setAlignment(Pos.BOTTOM_CENTER);

	    GridPane gridPaneBottom = new GridPane();
	    gridPaneBottom.setHgap(15);
	    gridPaneBottom.setVgap(15);
	    // Add the text field, label in to the grid
	    gridPaneBottom.add(copyRight, 0, 0);
	    // Set properties alignment for label UI
	    gridPaneBottom.setAlignment(Pos.BOTTOM_CENTER);

	    // Create VBox object
		VBox vbox = createVBox();

        vbox.getChildren().addAll(gridPaneMiddle);

        rootPane.getChildren().addAll(hBox, vbox, gridPaneBottom);
		Scene scene = new Scene(rootPane, 750, 550, Color.BLACK);
		// Set title
		primaryStage.setTitle("Collin's ATM");
		// Place the scene in the stage
		primaryStage.setScene(scene);
		// Display the stage
		primaryStage.show();

		if (transactionType.equalsIgnoreCase("Withdrawal") || transactionType.equalsIgnoreCase("Deposit")) {
			// button function
			twenty.setOnAction(e -> transaction(account, primaryStage, "20", transactionType));
			fifty.setOnAction(e -> transaction(account, primaryStage, "50", transactionType));
			hundred.setOnAction(e -> transaction(account, primaryStage, "100", transactionType));
			twoHundred.setOnAction(e -> transaction(account, primaryStage, "200", transactionType));
			enter.setOnAction(e -> emptyCheck(account, null, primaryStage, amount.getText(), transactionType));
		} else {
			// button function
			twenty.setOnAction(e -> transferAccount(account, transferAccount, primaryStage, "20", transactionType));
			fifty.setOnAction(e -> transferAccount(account, transferAccount, primaryStage, "50", transactionType));
			hundred.setOnAction(e -> transferAccount(account, transferAccount, primaryStage, "100", transactionType));
			twoHundred.setOnAction(e -> transferAccount(account, transferAccount, primaryStage, "200", transactionType));
			enter.setOnAction(e -> emptyCheck(account, transferAccount, primaryStage, amount.getText(), transactionType));
		}


	}

	/**
	 * This method used to transfer the amount to the other account
	 * 
	 * @param account
	 * @param transferAccount
	 * @param primaryStage
	 * @param text
	 * @param transactionType
	 */
	private void transferAccount(Account account, Account  transferAccountDetails, Stage primaryStage, String text, String transactionType) {
		try {
			double cashAmount = Double.parseDouble(text);
			Transfer transfer = new Transfer();
			Account accountDetails = transfer.retriveAccount(account);
			// Validate cashAmount
			if (accountDetails != null) {
				accountDetails.setTransactionType(3);
				transferAccountDetails.setTransactionType(3);
				if (cashAmount <= accountDetails.getBalance()) {
					// update account table for sender record
					double accountBalance = accountDetails.getBalance() - cashAmount;
					accountDetails.setTransferAmount(-cashAmount);
					accountDetails.setBalance(accountBalance);
					transfer.accountBalance(accountDetails);

					// update account for receiver record
					double transferAccountBalance = transferAccountDetails.getBalance() + cashAmount;
					transferAccountDetails.setTransferAmount(cashAmount);
					transferAccountDetails.setBalance(transferAccountBalance);
					transfer.accountBalance(transferAccountDetails);

					// success full message display
					successTransaction(primaryStage);
				} else {
					alert.setContentText("You do not have enough balance");
					alert.showAndWait();
				}
			} else {
				alert.setContentText("Customer incorrect!!");
				alert.showAndWait();
			}
		} catch (NumberFormatException e) {
			alert.setContentText("Please enter the Correct Amount");
			alert.showAndWait();
		}
	}

	private void emptyCheck(Account account, Account  transferAccount, Stage primaryStage, String amount, String transactionType) {
		if (transactionType.equalsIgnoreCase("Withdrawal") || transactionType.equalsIgnoreCase("Deposit")) {
			if (!amount.isEmpty()) {
				transaction(account, primaryStage, amount, transactionType);
			} else {
				alert.setContentText("Please enter the Amount or Select the Amount");
				alert.showAndWait();
			}
		}else {
			if (!amount.isEmpty()) {
				transferAccount(account, transferAccount, primaryStage, amount, transactionType);
			} else {
				alert.setContentText("Please enter the Amount or Select the Amount");
				alert.showAndWait();
			}
		}

	}

	private void transaction(Account account, Stage primaryStage, String text, String transactionType) {
		try {
			double cashAmount = Double.parseDouble(text);
			Withdrawal withdrawal = new Withdrawal();
			Account accountDetails = withdrawal.retriveAccount(account);
			// Validate cashAmount
			if (accountDetails != null) {
				if (transactionType.equalsIgnoreCase("Withdrawal")) {
					accountDetails.setTransactionType(1);
					if (cashAmount <= accountDetails.getBalance()) {
						double accountBalance = accountDetails.getBalance() - cashAmount;
						accountDetails.setTransferAmount(cashAmount);
						accountDetails.setBalance(accountBalance);
						withdrawal.accountBalance(accountDetails);
						successTransaction(primaryStage);
					} else {
						alert.setContentText("You do not have enough balance");
						alert.showAndWait();
					}
				} else if (transactionType.equalsIgnoreCase("Deposit")) {
					accountDetails.setTransactionType(2);
					double accountBalance = accountDetails.getBalance() + cashAmount;
					accountDetails.setTransferAmount(cashAmount);
					accountDetails.setBalance(accountBalance);
					withdrawal.accountBalance(accountDetails);
					successTransaction(primaryStage);
				}
			} else {
				alert.setContentText("Customer incorrect!!");
				alert.showAndWait();
			}
		} catch (NumberFormatException e) {
			alert.setContentText("Please enter the Correct Amount");
			alert.showAndWait();
		}
	}

	/**
	 * This method created for display the success transaction
	 * @param primaryStage
	 */
	private void successTransaction(Stage primaryStage) {
		VBox rootPane = new VBox();

		// Create hbox for the header of the application
		HBox hBox = new HBox();
		hBox = heading();

		// Create the grid object
		GridPane gridPaneMiddle = new GridPane();
		gridPaneMiddle.setHgap(15);
		gridPaneMiddle.setVgap(15);

	    // Add the text field, label in to the grid
		gridPaneMiddle.add(success, 0, 1);
		success.setTextFill(Color.DARKRED);
		success.setFont(Font.font("Times New Roman", FontWeight.BOLD, 25));

	    gridPaneMiddle.add(ok, 1, 2);
	    ok.setPrefSize(150, 50);
	    ok.setStyle("-fx-background-color: #8B0000;");
	    ok.setTextFill(Color.WHITE);
	    ok.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));

	    // Set properties alignment for label UI
	    gridPaneMiddle.setAlignment(Pos.BOTTOM_CENTER);
	    GridPane.setHalignment(ok, HPos.RIGHT);

	    GridPane gridPaneBottom = new GridPane();
	    gridPaneBottom.setHgap(15);
	    gridPaneBottom.setVgap(15);
	    // Add the text field, label in to the grid
	    gridPaneBottom.add(copyRight, 0, 0);
	    // Set properties alignment for label UI
	    gridPaneBottom.setAlignment(Pos.BOTTOM_CENTER);

	    // Create VBox object
		VBox vbox = createVBox();
		
        vbox.getChildren().addAll(gridPaneMiddle);

        rootPane.getChildren().addAll(hBox, vbox, gridPaneBottom);
		Scene scene = new Scene(rootPane, 750, 550, Color.BLACK);
		// Set title
		primaryStage.setTitle("Collin's ATM");
		// Place the scene in the stage
		primaryStage.setScene(scene);
		// Display the stage
		primaryStage.show();

		// button function
		ok.setOnAction(e -> reloadPage(primaryStage));
	}

	/**
	 * This method used to reload the first page
	 * @param primaryStage 
	 */
	private void reloadPage(Stage primaryStage) {
		try {
			primaryStage.close();
			pin.clear();
			trnsferAccountNo.clear();
			start(primaryApp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Main method
	 * @param args
	 */
	public static void main (String [] args) {
		launch(args);
	}
	
}
