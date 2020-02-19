package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Account;

public class Transfer extends Transaction {

	@Override
	public void accountBalance(Account accountDetails) {
		updateAccount(accountDetails);
		createTransaction(accountDetails);
	}
	/**
	 * This method used to serch specific account
	 * @param account
	 * @return
	 */
	public Account searchAccountNo(String transAccNo) {
		Account account = new Account();
		// database connection url, username, password
		String url="jdbc:mysql://localhost/atmcollin";
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
			StringBuffer query = new StringBuffer("SELECT * FROM accounts WHERE ");
		    if (transAccNo != null) {
		    	query.append("account_number = " + transAccNo);
		    }

		 // execute select SQL stetement
		 ResultSet resultSet = statement.executeQuery(query.toString());
		 if (resultSet.next()) {
			 account.setAccountId(resultSet.getInt("account_id"));
			 account.setCustomerId(resultSet.getInt("customer_id"));
			 account.setAccountNo(resultSet.getString("account_number"));
			 account.setBalance(resultSet.getDouble("balance"));
			 account.setAccountType(resultSet.getInt("account_type"));
			 
		 } else {
			 account = null;
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
		return account;
	}
}
