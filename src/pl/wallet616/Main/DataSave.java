package pl.wallet616.Main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DataSave extends Main{
	private static File file;
	private static final String OS = System.getProperty("os.name");
	
	public static boolean addData(String userKey, String userName) {
		boolean repeat = false;
		try {
			// Sprawdzenie systemu operacyjnego.
			if (OS.startsWith("Win")) {
				file = new File(System.getenv("APPDATA") + "/wallet616/data.dat");
			} else {
				file = new File("/home/wallet616/data.dat");
			}
			
			if (!DataRead.loadUser(userKey, true)) {
				BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
				bw.append("UserKey: " + DataRead.clearText(userKey) + "\n");
				bw.append("	UserName: " + DataRead.clearText(userName) + "\n");
			    bw.close();
			    repeat = true;
			    
			    Log.log("User has been added to data file.");
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
			if (OS.startsWith("Win")) {
				file = new File(System.getenv("APPDATA") + "/wallet616/data.dat");
			} else {
				file = new File("/home/wallet616/data.dat");
			}
			
			BufferedReader br = new BufferedReader(new FileReader(file));
		    String line;
		    String newString = "";
		    boolean foundKey = false;
		    
		    if (!(position.equals("userKey") && DataRead.loadUser(DataRead.clearText(data), true))) {
			    while ((line = br.readLine()) != null)
			    {
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
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write(newString);
		    bw.close();
		    
		    if (repeat) {
		    	Log.log("Data has been changed in data file.");
		    	DataRead.loadUser(userKey, false);
		    } else {
		    	Log.log("Data has not been changed.");
		    }
		    
	    
		} catch (IOException e) {
			Log.error("Unable to add change data in data file.");
		}
		return repeat;
	}
}
