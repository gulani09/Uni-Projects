//File : Withdrawal.java
//Course Name : ITC508 - Object Modelling
//Assessment Item : Assignment 3
//Instructor Name : Dr Naveed Ali
//Date : 09 May 2018
//Due on : 25 May 2017
//Student Id : 11619843
//Student Name : Gulani Senthuran

package controller;

import model.Account;

/**
 * This file used to do the withdrawal process and this is 
 * subclass of the transaction abstract class
 * 
 * @author senthuran
 *
 */
public class Withdrawal extends Transaction {

	@Override
	public void accountBalance(Account accountDetails) {
		updateAccount(accountDetails);
		createTransaction(accountDetails);
	}

}
