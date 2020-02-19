package task2;
//File : SignalLight.java
//Course Name : ITC521 - Programming in Java 2
//Assessment Item : Assignment 2, Task 2
//Instructor Name : Recep Ulusoy
//Date : 10 September 2017
//Due on : 22 September 2017
//Student Id : 11619843
//Student Name : Gulani Senthuran

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * 
 * @author Gulani Senthuran
 *
 */
public class SignalLight extends Pane implements Runnable{

	private int greenTime;
	private int yellowTime;
	private int redTime;

	// Create circle object
	Circle greenCircle;
	Circle yellowCircle;
	Circle redCircle;

	private int colorTime;
	private Circle circle;

	// Default Constructor
	public SignalLight() {
		// Default constructor
	}

	public SignalLight(int colorTime, Circle greenCircle, Circle yellowCircle, Circle redCircle) {
		this.colorTime = colorTime;
		this.greenCircle = greenCircle;
		this.yellowCircle = yellowCircle;
		this.redCircle = redCircle;
	}
	/**
	 * Get the assigned green time
	 * @return
	 */
	public int getGreenTime() {
		return greenTime;
	}

	/**
	 * Set the green time
	 * @param greenTime
	 */
	public void setGreenTime(int greenTime) {
		this.greenTime = greenTime;
	}

	/**
	 * Get the assigned yellow time
	 * @return
	 */
	public int getYellowTime() {
		return yellowTime;
	}

	/**
	 * Set the yellow time
	 * @param yellowTime
	 */
	public void setYellowTime(int yellowTime) {
		this.yellowTime = yellowTime;
	}

	/**
	 * Get the assigned red time
	 * @return
	 */
	public int getRedTime() {
		return redTime;
	}

	/**
	 * Set the red time
	 * @param redTime
	 */
	public void setRedTime(int redTime) {
		this.redTime = redTime;
	}
	/*Thread threadGreen = new Thread(() -> {
				try {
					while (true) {
						Platform.runLater(() -> setGreenColor());
				        Thread.sleep(this.getGreenTime() * 1000);
					}

				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			});
			

	Thread threadYellow = new Thread(() -> {
				try {
					while(true) {
						Platform.runLater(() -> setYellowColor());
				        Thread.sleep(this.getYellowTime() * 1000);
					}

				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			});
			

	Thread threadRed = new Thread(() -> {
				try {
					while (true) {
						Platform.runLater(() -> setRedColor());
				        Thread.sleep(this.getRedTime() * 1000);
					}

				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			});*/

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
	 * This method used to implement the process when start button 
	 * is pressed.
	 * 
	 * @param greenCircle
	 * @param yellowCircle
	 * @param redCircle
	 */
	@SuppressWarnings("deprecation")
	public void startAction() {

		Thread threadGreen = new Thread(new SignalLight(this.getGreenTime(), greenCircle, yellowCircle, redCircle), "Green");
		Thread threadYellow = new Thread(new SignalLight(this.getYellowTime(), greenCircle, yellowCircle, redCircle), "Yellow");
		Thread threadRed = new Thread(new SignalLight(this.getRedTime(), greenCircle, yellowCircle, redCircle), "Red");

		if (threadGreen.isAlive() || threadYellow.isAlive() || threadRed.isAlive()) {
			Thread.currentThread().resume();
		} else {
			threadGreen.start();
			try {
				threadGreen.join();
			} catch (Exception e) {
				// TODO: handle exception
			}
			threadYellow.start();
			try {
				threadYellow.join();
			} catch (Exception e) {
				// TODO: handle exception
			}
			threadRed.start();
			try {
				threadRed.join();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	/**
	 * This method used to implement the process when stop button 
	 * is pressed. Light should be paused.
	 */
	@SuppressWarnings("deprecation")
	public void stopAction() {
		Thread.currentThread().suspend();
	}

	/**
	 * This method used to create hBox Pane with three colors of circle.
	 * 
	 * @return HBox
	 */
	public HBox getHbox() {
		// Create horizontal box
        HBox hPane = new HBox(15);
		// Create space between each margin
		hPane.setPadding(new Insets(30,30,30,30));
		// Card position set to center
		hPane.setAlignment(Pos.CENTER);
		greenCircle = new Circle();
		yellowCircle = new Circle();
		redCircle = new Circle();
		// Set the initial color for an radius for circle
		greenCircle.setRadius(40);
		greenCircle.setFill(Color.GREEN);

		yellowCircle.setRadius(40);
		yellowCircle.setFill(Color.SILVER);

		redCircle.setRadius(40);
		redCircle.setFill(Color.SILVER);

		// Add circles into hBox
		hPane.getChildren().addAll(greenCircle, yellowCircle, redCircle);
		hPane.setStyle("-fx-background-color: #000000;" + "-fx-background-radius: 30;");
		hPane.setSpacing(15);

		return hPane;
	}

	@Override
	public void run() {
		if (Thread.currentThread().getName().equals("Green")) {
			System.out.println("change light color");
			setGreenColor();
			try {
				Thread.sleep(colorTime * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
				// TODO Auto-generated catch block
				e.printStackTrace();
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (int i = colorTime -1 ; i >= 0; i--) {
				System.out.println("now light: 3 after " + i + "seconds will change color");
			}
		}
	}

}
