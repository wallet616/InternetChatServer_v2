package pl.wallet616.Main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log extends Main{
	// Creating log.
	public static void log(String message) {
		DateFormat dateFormat = new SimpleDateFormat("[HH:mm:ss] ");
		Date date = new Date();
		
		// Display log in the console.
		System.out.println(dateFormat.format(date) + "> System info: " + message);
	}
	
	// Creating error log.
	public static void error(String error) {
		DateFormat fileDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat dateFormat = new SimpleDateFormat("[HH:mm:ss] ");
		Date date = new Date();
		
		try {
			// Creating file to save log.
			File errorFile = new File(errorLogFile + fileDateFormat.format(date) + ".txt");
			if (!errorFile.exists()) {
				errorFile.createNewFile();
			}
			
			// Adding new data at the end of the line. 
			BufferedWriter bw = new BufferedWriter(new FileWriter(errorFile, true));
			bw.append(dateFormat.format(date) + error + "\n");
		    bw.close();
		    
		    // DIsplay log in the console.
		    System.out.println(dateFormat.format(date) + "> System error: " + error);
	    
		} catch (IOException e) {
			System.out.println(dateFormat.format(date) + "> System error: Unable to save errorlog.");
		}
	}
}
