package pl.wallet616.Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DataRead extends Main{
	private static File folder;
	private static File folder2;
	private static File file;
	private static final String OS = System.getProperty("os.name");
	
	public static boolean loadUser(String userKey, boolean check) {
		boolean repeat = false;
		try {
			if (OS.startsWith("Win")) {
				folder = new File(System.getenv("APPDATA") + "/wallet616");
				folder2 = new File(System.getenv("APPDATA") + "/wallet616/server");
				file = new File(System.getenv("APPDATA") + "/wallet616/server/data.dat");
			} else {
				folder = new File("/home/wallet616");
				folder = new File("/home/wallet616/server");
				file = new File("/home/wallet616/server/data.dat");
			}
			
			if (!folder.exists()) {
				folder.mkdirs();
			}
			if (!folder2.exists()) {
				folder2.mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}
		    
			// Data to assign in loops.
			BufferedReader br = new BufferedReader(new FileReader(file));
		    String line;
		    int currentID = 0;
		    boolean userKeyFound = false;
		    boolean isFreeToUse = true;
		    
		    if (!check) {
			    loopbreak:
			    for (int i = 0; i < slots; i++) {
		    		if (usersList[i][0] == null) {
		    			currentID = i;
	    				break loopbreak;
	    			}
		    	}
			    loopbreak:
				for (int i = 0; i < slots; i++) {
			    	if (usersList[i][0] != null && usersList[i][0].equals(userKey)) {
		    			isFreeToUse = false;
		    			break loopbreak;
		    		}
			    }
		    }
		    
		    if (isFreeToUse) {
		    	repeat = true;
		    	
			    loopbreak:
			    while ((line = br.readLine()) != null) {
			    	line = clearText(line);
			    	
			    	if (line.startsWith("UserKey: ") && userKeyFound) {
			    		break loopbreak;
			    	}
			    	
			    	if (line.equals("UserKey: " + userKey)) {
			    		if (!check) {
			    			usersList[currentID][0] = line.substring(9);
			    		}
			    		userKeyFound = true;
			    	}
			    	if (line.startsWith("UserName: ") && userKeyFound) {
			    		if (!check) {
			    			usersList[currentID][1] = line.substring(10);
			    		}
			    	}
			    }
		    }
	
		    br.close();
		    
		    if (!check) {
			    if (repeat) {
			    	Log.log("User " + usersList[currentID][1] + " has been loaded to list.");
			    } else if (!isFreeToUse) {
			    	Log.log("User " + usersList[currentID][1] + " has not been loaded, already at users list.");
			    } else {
			    	Log.log("User has not been loaded.");
			    }
		    }
	    
		} catch (IOException e) {
			Log.error("Unable to load users list.");
		}
		return repeat;
	}
	
	public static String clearText(String message) {
		try 
		{
			message = message.replaceAll("\\s+", " ");
			
			if ((message.substring(0, 1)).equals(" ")) {
				message = message.substring(1);
			}
			if ((message.substring((message.length() - 1), message.length())).equals(" ")) {
				message = message.substring(0, (message.length() - 1));
			}
		}
		catch (Exception e)
	    {
			Log.error("Unable to clear text.");
		}
		return message;
	}
	
	public static boolean unloadUser(String userKey) {
		boolean repeat = false;
		String username = "";
		loopbreak:
		for (int i = 0; i < slots; i++) {
		    if (usersList[i][0] != null && usersList[i][0].equals(userKey)) {
		    	usersList[i][0] = null;
		    	username = usersList[i][1];
		    	usersList[i][1] = null;
		    	usersList[i][2] = null;
		    	repeat = true;
	    		break loopbreak;
	    	}
		}
		
		if (repeat) {
			Log.log("User " + username + " has been unloaded.");
		} else {
			Log.log("Unable to unload user.");
		}
		return repeat;
	}
}
