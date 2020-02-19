package task2Mock;
//File : SignalLight.java
//Course Name : ITC521 - Programming in Java 2
//Assessment Item : Assignment 2, Task 2
//Instructor Name : Recep Ulusoy
//Date : 10 September 2017
//Due on : 22 September 2017
//Student Id : 11619843
//Student Name : Gulani Senthuran

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * This trafficLightDemo class used to demonstrating for traffic signal concept.
 * Using this GUI program able to stop the light signals and progress the 
 * Green Yellow and Re signals. Using Javafx application this GUI is developed
 * This file has used multi threading function using javafx application.
 * 
 * @author Gulani Senthuran
 *
 */
public class TrafficLight implements Runnable{

	// Create circle object
	private Circle greenCircle;
	private Circle yellowCircle;
	private Circle redCircle;

	// sleeping time
	private int colorTime;

	// Default Constructor
	public TrafficLight() {
		// Default
	}

	// Constructor with four arguments
	public TrafficLight(int colorTime, Circle greenCircle, Circle yellowCircle, Circle redCircle) {
		this.colorTime = colorTime;
		this.greenCircle = greenCircle;
		this.yellowCircle = yellowCircle;
		this.redCircle = redCircle;
	}

	/**
	 * This method used to set green color change and
	 * other two colors are not change
	 * 
	 */
	private void setGreenColor() {
		greenCircle.setFill(Color.GREEN);
		yellowCircle.setFill(Color.SILVER);
		redCircle.setFill(Color.SILVER);
	}

	/**
	 * This method used to set yellow color change and
	 * other two colors are not change
	 */
	private void setRedColor() {
		greenCircle.setFill(Color.SILVER);
		yellowCircle.setFill(Color.SILVER);
		redCircle.setFill(Color.RED);
	}

	/**
	 * This method used to set red color change and
	 * other two colors are not change
	 */
	private void setYellowColor() {
		greenCircle.setFill(Color.SILVER);
		yellowCircle.setFill(Color.YELLOW);
		redCircle.setFill(Color.SILVER);
	}

	/**
	 * This method is run method when thread start it will invoked
	 */
	@Override
	public void run() {
		if (Thread.currentThread().getName().equals("Green")) {
			System.out.println("change light color");
			setGreenColor();
			try {
				Thread.sleep(colorTime * 1000);
			} catch (InterruptedException e) {
				e.getMessage();
			}
			for (int i = colorTime -1 ; i >= 0; i--) {
				System.out.println("now light: 1 after " + i + "seconds will change color");
			}
		}

		if (Thread.currentThread().getName().equals("Yellow")) {
			System.out.println("change light color");
			setYellowColor();
			try {
				Thread.sleep(colorTime * 1000);
			} catch (InterruptedException e) {
				e.getMessage();
			}
			for (int i = colorTime -1 ; i >= 0; i--) {
				System.out.println("now light: 2 after " + i + "seconds will change color");
			}
		}

		if (Thread.currentThread().getName().equals("Red")) {
			System.out.println("change light color");
			setRedColor();
			try {
				Thread.sleep(colorTime * 1000);
			} catch (InterruptedException e) {
				e.getMessage();
			}
			for (int i = colorTime -1 ; i >= 0; i--) {
				System.out.println("now light: 3 after " + i + "seconds will change color");
			}
		}
	}

}
