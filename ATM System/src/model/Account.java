// Account.java
//Course Name : ITC508 - Object Modelling
//Assessment Item : Assignment 3
//Instructor Name : Dr Naveed Ali
//Date : 09 May 2018
//Due on : 25 May 2017
//Student Id : 11619843
//Student Name : Gulani Senthuran
package model;

/**
 * This class used to describe the object attributes to access the objects.
 * all attributes have setters methods an getters methods to access the attributes
 * 
 * @author Gulani Senthuran
 *
 */
public class Account {
	private int accountId;
	private int customerId;
	private String accountNo;
	private double balance;
	private int accountType;
	private boolean isValidAmount;
	private int transactionType;
	private double transferAmount;
	private String transferAccNo;
	private double transferAccAmount;

	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public int getAccountType() {
		return accountType;
	}
	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}
	public boolean isValidAmount() {
		return isValidAmount;
	}
	public void setValidAmount(boolean isValidAmount) {
		this.isValidAmount = isValidAmount;
	}
	public int getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(int transactionType) {
		this.transactionType = transactionType;
	}
	public double getTransferAmount() {
		return transferAmount;
	}
	public void setTransferAmount(double transferAmount) {
		this.transferAmount = transferAmount;
	}
	public double getTransferAccAmount() {
		return transferAccAmount;
	}
	public void setTransferAccAmount(double transferAccAmount) {
		this.transferAccAmount = transferAccAmount;
	}
	public String getTransferAccNo() {
		return transferAccNo;
	}
	public void setTransferAccNo(String transferAccNo) {
		this.transferAccNo = transferAccNo;
	}


}
