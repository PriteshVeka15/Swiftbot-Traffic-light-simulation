package assignment3;

import swiftbot.*;

public class MainClass {
	public static SwiftBotAPI swiftBot;

	/*This is initialising all the variables and making them ready to be updated.*/
	public static String common_colour = "";
	public static int common_colour_number = 0;
	public static int common_red = 0;
	public static int common_green = 0;
	public static int common_blue = 0;
	public static int traffic_light_encountered = 0;
	public static int total_execution_time = 0;
	static boolean stopRequested = false; // Flag to check if Button X was pressed

	public static void main(String[] args) {

		try {
			swiftBot = new SwiftBotAPI();
		} catch (Exception e) {
			/*Outputs a warning if I2C is disabled. This only needs to be turned on once,
			  so you won't need to worry about this problem again!*/
			System.out.println("\nI2C disabled!");
			System.out.println("Run the following command:");
			System.out.println("sudo raspi-config nonint do_i2c 0\n");
			System.exit(5);
		}
		MainFlow(); //Calls the main flow in which all the main program is made in.	

	}
	//This is the main method that runs the whole program.
	public static void MainFlow() {
		System.out.println("");
		System.out.println("");
		System.out.println("***************************************************************************************************************");
		System.out.println("***************************************************************************************************************");
		System.out.println("***************************************************************************************************************");
		System.out.println("\r\n"
				+ "$$$$$$$$\\                  $$$$$$\\   $$$$$$\\  $$\\                 $$\\ $$\\           $$\\        $$\\     \r\n"
				+ "\\__$$  __|                $$  __$$\\ $$  __$$\\ \\__|                $$ |\\__|          $$ |       $$ |    \r\n"
				+ "   $$ | $$$$$$\\  $$$$$$\\  $$ /  \\__|$$ /  \\__|$$\\  $$$$$$$\\       $$ |$$\\  $$$$$$\\  $$$$$$$\\ $$$$$$\\   \r\n"
				+ "   $$ |$$  __$$\\ \\____$$\\ $$$$\\     $$$$\\     $$ |$$  _____|      $$ |$$ |$$  __$$\\ $$  __$$\\\\_$$  _|  \r\n"
				+ "   $$ |$$ |  \\__|$$$$$$$ |$$  _|    $$  _|    $$ |$$ /            $$ |$$ |$$ /  $$ |$$ |  $$ | $$ |    \r\n"
				+ "   $$ |$$ |     $$  __$$ |$$ |      $$ |      $$ |$$ |            $$ |$$ |$$ |  $$ |$$ |  $$ | $$ |$$\\ \r\n"
				+ "   $$ |$$ |     \\$$$$$$$ |$$ |      $$ |      $$ |\\$$$$$$$\\       $$ |$$ |\\$$$$$$$ |$$ |  $$ | \\$$$$  |\r\n"
				+ "   \\__|\\__|      \\_______|\\__|      \\__|      \\__| \\_______|      \\__|\\__| \\____$$ |\\__|  \\__|  \\____/ \r\n"
				+ "                                                                          $$\\   $$ |                   \r\n"
				+ "                                                                          \\$$$$$$  |                   \r\n"
				+ "                                                                           \\______/                    \r\n"
				+ "");
		System.out.println("***************************************************************************************************************");
		System.out.println("***************************************************************************************************************");
		System.out.println("***************************************************************************************************************");
		System.out.println("");

		//After the title is displayed, the program waits 1 second before continuing.
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//This prompts the user to press button 'A' and when it is pressed, it runs the main section of the program.
		long start_time = System.currentTimeMillis(); //Starts the timer.
		System.out.println("Press 'A' to start.");
		System.out.println("");
		swiftBot.enableButton(Button.A, () -> {
			System.out.println("Button A Pressed.");

			runSwiftBot();

			/*While button 'X' is not pressed, it will loop through this set of code which allows it to do the different functions. 
			 This includes the methods that the robot has to call and the different classes it has to call in order to process images.*/
			while (stopRequested == false) {
				NormalFlow();
				double Distancefromlight = DFTL();
				System.out.println("///// Distance to traffic light:" + Distancefromlight + "cm" + "/////");

				/*If the distance is less than or equal to 20cm, it will detect the colour that is captured and call the class that
				captures the image and processes it. */
				if (Distancefromlight <= 20) {
					averagepixel.Determinecolour();
				}
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			/*This runs at the end of the loop when the user presses the 'X' button. It adds the total execution time of
			 * this method into the global variable which is displayed at the end with the execution log.*/
			long end_time = System.currentTimeMillis();
			double totaltimeseconds = (end_time-start_time)/1000.0;
			total_execution_time = (int) totaltimeseconds;

		});
	}
	
	/*The normal flow is the default mode the robot stays in until it detects a colour. */
	public static void NormalFlow() {
		int[] yellow = { 255, 255, 0 };
		swiftBot.fillUnderlights(yellow);
		swiftBot.startMove(40, 39);
		System.out.println();
		System.out.println("///// Initial speed = 12cm/s. /////");
	}

	/*Calculates the distance between the object and the robot.*/
	public static double DFTL() {
		double distanceToObject = 0;
		for (int i = 0; i < 1000; ++i)
			;
		{
			distanceToObject = swiftBot.useUltrasound();
			return distanceToObject;
		}
	}

	/*The robot runs this method when button 'X' is pressed.*/
	public static void runSwiftBot() {
		System.out.println("SwiftBot is now moving forward. Press Button X to stop.");

		// Enable Button X to set stopRequested to true
		swiftBot.enableButton(Button.X, () -> {
			System.out.println("");
			System.out.println("Button X pressed. Stopping SwiftBot.");
			stopRequested = true;
			// Stop the SwiftBot once X is pressed
			swiftBot.stopMove();
			System.out.println("SwiftBot has stopped.");
			System.out.println("");
			System.out.println("///// To view the execution log, press 'Y', else press 'X' again. /////");
			System.out.println("");
			ExecutionLog.Log_Buttons(); //Goes to class and runs the execution log steps.
		});
	}
}