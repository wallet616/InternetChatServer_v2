package pl.wallet616.Main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DataRead extends Main {
	// Loading user by its key, or just checking if that user exist in data file.
	public static boolean loadUser(String userKey, boolean check) {
		boolean repeat = false;
		try {
			// Data to assign in loops.
			BufferedReader br = new BufferedReader(new FileReader(dataFile));
		    String line;
		    int currentID = 0;
		    boolean userKeyFound = false;
		    boolean isFreeToUse = true;
		    boolean freeSlotFound = false;
		    
		    if (!check) {
			    for (int i = 0; i < slots; i++) {
		    		if (usersList[i][0] == null) {
		    			currentID = i;
		    			freeSlotFound = true;
	    				break;
	    			}
		    	}
			    
				for (int i = 0; i < slots; i++) {
			    	if (usersList[i][0] != null && usersList[i][0].equals(userKey)) {
		    			isFreeToUse = false;
		    			break;
		    		}
			    }
		    }
		    
		    if (isFreeToUse) {
			    while ((line = br.readLine()) != null) {
			    	line = clearText(line);
			    	
			    	if (line.startsWith("UserKey: ") && userKeyFound) {
			    		break;
			    	}
			    	
			    	if (line.equals("UserKey: " + userKey)) {
			    		if (!check && freeSlotFound) {
			    			usersList[currentID][0] = line.substring(9);
			    		}
			    		repeat = true;
			    		userKeyFound = true;
			    	}
			    	if (line.startsWith("UserName: ") && userKeyFound) {
			    		if (!check && freeSlotFound) {
			    			usersList[currentID][1] = line.substring(10);
			    		}
			    	}
			    }
		    }
	
		    br.close();
		    
		    if (!check) {
			    if (repeat) {
			    	Log.log("User " + usersList[currentID][1] + " has connected.");
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
	
	public static boolean unloadUser(String userKey, boolean silent) {
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
		
		if (!silent) {
			if (repeat) {
				Log.log("User " + username + " has disconnected.");
			} else {
				Log.log("Unable to unload user.");
			}
		}
		return repeat;
	}
}
