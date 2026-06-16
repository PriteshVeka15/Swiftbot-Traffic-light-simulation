package assignment3;

import java.util.ArrayList;

public class LightExecution {
	
	//This creates an array that stores the different colours that are encountered.
	public static ArrayList<Integer> ExeLights = new ArrayList<>();

	public static void main(String[] args) {

	}

	//If the robot detects a red light:
	public static void Red() {
		//Starts the timer to see how long the red method runs for.
		long redstart = System.currentTimeMillis();
		int[] Stop = {255,0,0}; //Stores the RGB value for red.
		MainClass.traffic_light_encountered++; //Increments the traffic light encountered variable by 1.
		MainClass.common_red++; //Increments the common red variable by 1.
		ExeLights.add(1); // Add red light identifier (1) to the list of executed lights.
		MainClass.swiftBot.fillUnderlights(Stop);
		MainClass.swiftBot.stopMove();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//Calculates the duration that the red light ran for and adds it to the total execution time.
		long redend = System.currentTimeMillis();
		double totalredtime = (redstart-redend)/1000.0;
		MainClass.total_execution_time += totalredtime;
		
		/*Identifies the common colour, if red is the most common colour then the common colour variable
		 * changes to red and the number of times it is encountered increments by 1.*/
		if (MainClass.common_red > MainClass.common_green && MainClass.common_red >MainClass.common_blue) {
			MainClass.common_colour_number = MainClass.common_red;
			MainClass.common_colour = "Red.";
		}
	}

	//The method that runs if the green light is encountered.
	public static void  Green() {
		long greenstart = System.currentTimeMillis();
		int[] Go = {0,255,0}; //RGB value for green.
		MainClass.traffic_light_encountered++; //Increments the traffic light encountered variable by 1.
		MainClass.common_green++; //Increments the common green variable by 1.
		ExeLights.add(2); // Add green light identifier (2) to the list of executed lights.
		MainClass.swiftBot.fillUnderlights(Go);
		MainClass.swiftBot.startMove(80, 79); //Double the initial speed.
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//Calculates the duration of green light and adds it on the total execution time.
		long greenend = System.currentTimeMillis();
		double totalgreentime = (greenstart-greenend)/1000.0;
		MainClass.total_execution_time += totalgreentime;
		
		/*Identifies the common colour, if green is the most common colour then the common colour variable
		 * changes to green and the number of times it was encountered increments by 1.*/
		if (MainClass.common_green > MainClass.common_red && MainClass.common_green >MainClass.common_blue) {
			MainClass.common_colour_number = MainClass.common_green;
			MainClass.common_colour = "Green.";
		}
	}

	public static void Blue() {
		long bluestart = System.currentTimeMillis();
		MainClass.traffic_light_encountered++;
		MainClass.common_blue++;
		ExeLights.add(3); // Add blue light identifier (3) to the list of executed lights.
		MainClass.swiftBot.stopMove();
		int[] Yield = {0,0,255};
		int[] off = { 0, 0, 0 };

		for (int i = 0; i < 5; i++) { //Makes the under-lights blink 5 times before executing the blue light function.
			MainClass.swiftBot.fillUnderlights(Yield);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			MainClass.swiftBot.fillUnderlights(off);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		//Turns 90 degrees right.
		MainClass.swiftBot.move(0, 25, 2000);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//Moves forward.
		MainClass.swiftBot.move(20, 20, 1000);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		MainClass.swiftBot.stopMove();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//Moves back at same speed it moved forward.
		MainClass.swiftBot.move(-20, -20, 1000);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//Turns right 90 degrees backwards.
		MainClass.swiftBot.move(0, -25, 2000);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		MainClass.swiftBot.stopMove();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//Calculate the duration of the blue light and adds it onto the total execution time.
		long blueend = System.currentTimeMillis();
		double totalbluetime = (bluestart-blueend)/1000.0;
		MainClass.total_execution_time += totalbluetime;
		
		/*Identifies the common colour, if blue is the most common colour then the common colour variable
		 * changes to blue and the number of times it was encountered increments by 1.*/
		if (MainClass.common_blue > MainClass.common_red && MainClass.common_blue >MainClass.common_green) {
			MainClass.common_colour_number = MainClass.common_blue;
			MainClass.common_colour = "Blue.";
		}
	}

}