package assignment3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import swiftbot.Button;

public class ExecutionLog {
	private static String Log = "Trafficlightexecutionlog.txt";
	

	public static void main(String[] args) {

	}
	
	//Enables all the buttons and allows each button to have a certain function.
	public static void Log_Buttons () {
		MainClass.swiftBot.disableAllButtons(); //Disables all buttons so they can have new functions attached.
		
		MainClass.swiftBot.enableButton(Button.X, () -> {
			SavingTheLog(); //Saves the execution log.
			System.exit(0); //Exits the program.
			
		});
		
		MainClass.swiftBot.enableButton(Button.Y, () -> {
			execution_log(); //Displays the execution log.
			SavingTheLog(); //Saves the execution log.
			System.exit(0); //Exits the program.
		});
	}
	
	public static void execution_log () {
		MainClass.swiftBot.disableAllButtons(); //Disables all buttons so no button is accidentally pressed.
		LightUpArray(); //Runs the array of the different colours encountered.
		
		//Displays the different information that was stored while the program was running.
		System.out.println("Most common colour = " +MainClass.common_colour);
		System.out.println("Number of times the common colour was encountered = " +MainClass.common_colour_number);
		System.out.println("Number of traffic lights encountered = "+MainClass.traffic_light_encountered);
		System.out.println("Total execution time of program = " +MainClass.total_execution_time);
	}
	
	public static void LightUpArray () {
		ArrayList<Integer> list = LightExecution.ExeLights; //Calls the array from LightExecution.
		
		int[] Red = {255,0,0};
		int[] Green = {0,255,0};
		int[] Blue= {0,0,255};

		//Takes the values stored in the array and displays the lights encountered.
		for (int i=0; i < list.size(); i++) {
			if (list.get(i) == 1) {
				MainClass.swiftBot.fillUnderlights(Red);
			}
			else if (list.get(i) == 2) {
				MainClass.swiftBot.fillUnderlights(Green);
			}
			else if (list.get(i) == 3) {
				MainClass.swiftBot.fillUnderlights(Blue);
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void SavingTheLog () {
		
		//Boolean flags made to check if any traffic lights were encountered.
		boolean RedAppending = MainClass.common_red !=0;
		boolean GreenAppending = MainClass.common_green !=0;
		boolean BlueAppending = MainClass.common_blue !=0;
		
		//Creates a new file and adds new information onto it if the file exists or is it has to create a new file.
		try (FileWriter LogFile = new FileWriter(Log, RedAppending && GreenAppending && BlueAppending);
				BufferedWriter addlog = new BufferedWriter(LogFile)) {
			if (!RedAppending && GreenAppending && BlueAppending) {
				addlog.write("Traffic light run information Log\n"); //Creates a header for the text file.
			}
			
			//Adds the information that is stored onto the text file.
			addlog.write("///// Most common colour = " + MainClass.common_colour + "/////" + "\n");
			addlog.write("///// Number of times the common colour was encountered =" +MainClass.common_colour_number++ + "/////" + "\n" );
			addlog.write("///// Number of traffic lights encountered = " + MainClass.traffic_light_encountered++ + "\n");
			addlog.write("Total execution time of program =" +MainClass.total_execution_time++ + "seconds /////" + "\n");
			addlog.flush();
			System.out.println("");
			/*Displays where the text file is located in the robot.*/
			System.out.println("///// The log file is located at: " + new File(Log).getAbsolutePath() + "/////");
		} catch (IOException e) { //Handles any errors.
			System.out.println("Error: Details were not saved sucessfully");
		}
	}
}
