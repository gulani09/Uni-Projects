package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

import model.Account;

public abstract class Transaction {

	public void updateAccount(Account account) {
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
					StringBuffer query = new StringBuffer("UPDATE accounts SET ");
					if (account.getBalance() >= 0) {
				    	query.append("balance = " + account.getBalance());
				    }				    
				    // Remove last ' AND ' letters from string query
				    //query.delete(query.length() - 2, query.length() - 0);
				    query.append(" WHERE account_id = " + account.getAccountId());
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

	public void createTransaction(Account account) {
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
	    PreparedStatement statement = null;
		try {
			connection = DriverManager.getConnection(url, userName, password);

			// Sql insert query
		    String query = "INSERT INTO transactions (customer_id, account, amount, transaction_type, transaction_date) values ("
		    				+ "?,?,?,?,?)";
		    statement = (PreparedStatement) connection.prepareStatement(query);
		    // set the values
		    statement.setInt(1, account.getCustomerId());
		    statement.setInt(2, account.getAccountId());
		    statement.setDouble(3, account.getTransferAmount());
		    statement.setInt(4, account.getTransactionType());
		    statement.setTimestamp(5, new Timestamp(new Date().getTime()));

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

	public Account retriveAccount(Account account) {
		Account accountDetails = null;
		accountDetails = searchAccount(account);
		return accountDetails;
	}

	/**
	 * This method used to serch specific account
	 * @param account
	 * @return
	 */
	private Account searchAccount(Account account) {
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
		    if (account.getCustomerId() > 0) {
		    	query.append("customer_id = " + account.getCustomerId() + " AND ");
		    }
		    if (account.getAccountType() > 0) {
		    	query.append("account_type = " + account.getAccountType());
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
	public abstract void accountBalance(Account accountDetails);
}
