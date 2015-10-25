package pl.wallet616.Main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataSave extends Main {
	// Default paths to files.
	public static void getPaths() {
		String os = System.getProperty("os.name");
		
		if (os.startsWith("Win")) {
			mainFolder = new File(System.getenv("APPDATA") + "/wallet616");
			mainServerFolder = new File(System.getenv("APPDATA") + "/wallet616/server");
			mainErrorFolder = new File(System.getenv("APPDATA") + "/wallet616/server/error");
			mainLogFolder = new File(System.getenv("APPDATA") + "/wallet616/server/log");
			dataFile = new File(System.getenv("APPDATA") + "/wallet616/server/data.dat");
			logFile = System.getenv("APPDATA") + "/wallet616/server/log/";
			errorLogFile = System.getenv("APPDATA") + "/wallet616/server/error/";
		} else {
			mainFolder = new File("/home/wallet616");
			mainServerFolder = new File("/home/wallet616/server");
			mainErrorFolder = new File("/home/wallet616/server/error");
			mainLogFolder = new File("/home/wallet616/server/log");
			dataFile = new File("/home/wallet616/server/data.dat");
			logFile = "/home/wallet616/server/log/";
			errorLogFile = "/home/wallet616/server/error/";
		}
		
		try {
			if (!mainFolder.exists()) {
				mainFolder.mkdirs();
			}
			if (!mainServerFolder.exists()) {
				mainServerFolder.mkdirs();
			}
			if (!mainLogFolder.exists()) {
				mainLogFolder.mkdirs();
			}
			if (!dataFile.exists()) {
				dataFile.createNewFile();
			}
		} catch (IOException e) {
			Log.error("Unable to create necesery files and folders");
		}
	}
	
	public static boolean addData(String userKey, String userName) {
		boolean repeat = false;
		try {
			// Check if the userKey is already occurred, by trying to load again.
			if (!DataRead.loadUser(userKey, true)) {
				BufferedWriter bw = new BufferedWriter(new FileWriter(dataFile, true));
				bw.append("UserKey: " + DataRead.clearText(userKey) + "\n");
				bw.append("	UserName: " + DataRead.clearText(userName) + "\n");
			    bw.close();
			    repeat = true;
			    
			    Log.log("User " + userName + " has been added to data file.");
			    DataRead.loadUser(userKey, false);
			} else {
				Log.log("User has not been added to data file, user key already in use.");
			}
	    
		} catch (IOException e) {
			Log.error("Unable to add data to data file.");
		}
		return repeat;
	}
	
	public static boolean changeData(String userKey, String position, String data) {
		boolean repeat = false;
		try {
			// Prepare variables to change data values.
			BufferedReader br = new BufferedReader(new FileReader(dataFile));
		    String line;
		    String newString = "";
		    boolean foundKey = false;
		    
		    // Check if user wants to change userKey that is already occurred. 
		    if (!(position.equals("userKey") && DataRead.loadUser(DataRead.clearText(data), true))) {
			    while ((line = br.readLine()) != null)
			    {
			    	// Finds right position to change data.
			    	if (line.substring(9).startsWith(userKey)) {
			    		foundKey = true;
			    	}
			    	if (foundKey) {
			    		if (position.equals("userKey") && DataRead.clearText(line).startsWith("UserKey")) {
			    			newString += "UserKey: " + DataRead.clearText(data) + "\n";
			    			repeat = true;
			    			foundKey = false;
			    		} else if (position.equals("userName") && DataRead.clearText(line).startsWith("UserName")) {
			    			newString += "	UserName: " + DataRead.clearText(data) + "\n";
				    		repeat = true;
				    		foundKey = false;
			    		} else {
				    		newString += line + "\n";
				    	}
			    	} else {
			    		newString += line + "\n";
			    	}
			    }
		    }
		    br.close();
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(dataFile));
			bw.write(newString);
		    bw.close();
		    
		    if (repeat) {
		    	Log.log("Data has been changed in data file.");
		    	DataRead.unloadUser(userKey, true);
		    	DataRead.loadUser(userKey, false);
		    } else {
		    	Log.log("Data has not been changed.");
		    }
		    
	    
		} catch (IOException e) {
			Log.error("Unable to add change data in data file.");
		}
		return repeat;
	}
	
	public static boolean archiveSave(String userName, String message) {
		DateFormat fileDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat dateFormat = new SimpleDateFormat("[HH:mm:ss]");
		Date date = new Date();
		
		try {
			// Creating file to save log.
			File log = new File(logFile + fileDateFormat.format(date) + ".txt");
			if (!log.exists()) {
				log.createNewFile();
			}
			
			// Dodanie nowych danych na koncu linii. 
			BufferedWriter bw = new BufferedWriter(new FileWriter(log, true));
			bw.append(dateFormat.format(date) + "[" + userName + "] " + message + "\n");
		    bw.close();
		    
		    System.out.println(dateFormat.format(date) + "[" + userName + "] " + message);
	    
		} catch (IOException e) {
			Log.error("System error: Unable to save errorlog.");
		}
		return true;
	}
}
