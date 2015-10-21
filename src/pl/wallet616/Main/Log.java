package pl.wallet616.Main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log extends Main{
	private static File folder;
	private static File folder2;
	private static File fileLog;
	
	// Creating log.
	public static void log(String message) {
		DateFormat dateFormat = new SimpleDateFormat("[HH:mm:ss] ");
		Date date = new Date();
		System.out.println(dateFormat.format(date) + "System info: " + message);
	}
	
	// Creating error log.
	public static void error(String error) {
		DateFormat fileDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat dateFormat = new SimpleDateFormat("[HH:mm:ss] ");
		Date date = new Date();
		
		try {
			if (System.getProperty("os.name").startsWith("Win")) {
				folder = new File(System.getenv("APPDATA") + "/wallet616/server");
				folder2 = new File(System.getenv("APPDATA") + "/wallet616/server/error");
				fileLog = new File(System.getenv("APPDATA") + "/wallet616/server/error/" + fileDateFormat.format(date) + ".txt");
			} else {
				folder = new File("/home/wallet616/server");
				folder2 = new File("/home/wallet616/server/error");
				fileLog = new File("home/wallet616/server/error/" + fileDateFormat.format(date) + ".txt");
			}
			
			if (!folder.exists()) {
				folder.mkdirs();
			}
			if (!folder2.exists()) {
				folder2.mkdirs();
			}
			if (!fileLog.exists()) {
				fileLog.createNewFile();
			}
			
			// Dodanie nowych danych na koncu linii. 
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileLog, true));
			bw.append(dateFormat.format(date) + error + "\n");
		    bw.close();
		    
		    System.out.println(dateFormat.format(date) + "System error: " + error);
	    
		} catch (IOException e) {
			System.out.println(dateFormat.format(date) + "System error: Unable to save errorlog.");
		}
	}
}
