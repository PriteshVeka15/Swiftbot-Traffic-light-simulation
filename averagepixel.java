package assignment3;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import swiftbot.*;
import javax.imageio.ImageIO;

public class averagepixel {

	public static void main(String[] args) {
		Determinecolour();
	}
	
	/*This method captures the image and processes it.*/
	public static void Determinecolour() {
		long cameratime = System.currentTimeMillis();

		/* Takes picture of specified size. Then saves it as a bitmap. */
		BufferedImage image = MainClass.swiftBot.takeStill(ImageSize.SQUARE_48x48);
		try {
			ImageIO.write(image, "bmp", new File("/data/home/pi/colourImage.bmp"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		String fn = "colourImage.bmp"; // Calls the image saved.
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(fn));
		} catch (IOException e) {
			e.printStackTrace();
		}
		int totalred = 0;
		int totalgreen = 0;
		int totalblue = 0;
		int pixelcount = 0;

		for (int x = 0; x < img.getWidth(); ++x) {
			for (int y = 0; y < img.getHeight(); ++y) {
				int p = img.getRGB(y, x);

				/*This performs a right binary shift to isolate the red, green and blue values and a mask is applied
				 *to only keep the lower 8 bits.*/
				int r = (p >> 16) & 0xFF;
				int g = (p >> 8) & 0xFF;
				int b = p & 0xFF;

				// total red = total red + r;
				totalred += r;
				// total green = total green  + g;
				totalgreen += g;
				// total blue = total blue + b;
				totalblue += b;

				++pixelcount;
			}

		}
		int avgr = totalred / pixelcount;
		int avgg = totalgreen / pixelcount;
		int avgb = totalblue / pixelcount;

		//Calculates the distance from red, green and blue
		int Dred = euclidsdistance(avgr, avgg, avgb, 255, 0, 0);
		int Dgreen = euclidsdistance(avgr, avgg, avgb, 0, 255, 0);
		int Dblue = euclidsdistance(avgr, avgg, avgb, 0, 0, 255);
		getcolour(Dred, Dgreen, Dblue);
		
		//Adds the time taken to process the image to the total execution time.
		long endcameratime = System.currentTimeMillis();
		double totalcameratime = (endcameratime-cameratime)/1000.0;
		MainClass.total_execution_time += totalcameratime;

	}

	//This method allows to see how far the calculated value is from the true value of red, green and blue.
	public static int euclidsdistance(int r1, int g1, int b1, int r2, int g2, int b2) {
		double D = 0;

		double red = Math.pow(r1 - r2, 2);
		double green = Math.pow(g1 - g2, 2);
		double blue = Math.pow(b1 - b2, 2);
		D = Math.sqrt(red + blue + green);

		return (int) D;

	}

	//Takes the calculated distance of each colour and compares them to see which colour has the strongest intensity.
	public static String getcolour(int Dred, int Dgreen, int Dblue) {
		if (Dred < Dgreen && Dred < Dblue) {
			System.out.println("///// Colour encountered = Red /////");
			LightExecution.Red();

			if (valid_colour(Dred) == false) {

			}
		} else if (Dgreen < Dred && Dgreen < Dblue) {
			System.out.println("///// Colour encountered = Green /////");
			LightExecution.Green();;
			if (valid_colour(Dgreen) == false) {

			}
		} else {
			System.out.println("///// Colour encountered = Blue /////");
			LightExecution.Blue();;
			if (valid_colour(Dblue) == false) {

			}
		}

		DFTL();

		return ("");

	}
	
	//If the calculated euclids distance is lower than 170, then it will execute the appropriate action.
	public static Boolean valid_colour(int colourdistance) {
		if (colourdistance < 170) {
			return true;
		} else {
			return false;
		}
	}

	//Calculated the distance from the robot to the traffic light.
	public static double DFTL() {
		double distanceToObject = 0;
		for (int i = 0; i < 1000; ++i);
		{
			distanceToObject = MainClass.swiftBot.useUltrasound();
			return distanceToObject;
		}
	}

}