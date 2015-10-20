package pl.wallet616.Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DataRead extends Main{
	private static File folder;
	private static File file;
	private static final String OS = System.getProperty("os.name");
	
	public static void loadUsers() {
		try {
			if (OS.startsWith("Win")) {
				folder = new File(System.getenv("APPDATA") + "/wallet616");
				file = new File(System.getenv("APPDATA") + "/wallet616/data.dat");
			} else {
				folder = new File("/home/wallet616");
				file = new File("/home/wallet616/data.dat");
			}
			
			if (!folder.exists()) {
				folder.mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}
			
			BufferedReader br = new BufferedReader(new FileReader(file));
		    
		    String line;
		    
		    int currentID = -1;
		    
		    while ((line = br.readLine()) != null) {
		    	line = clearText(line);
		    	
		    	if (line.startsWith("UserKey: ")) {
		    		currentID++;
		    		usersList[currentID][0] = line.substring(9);
		    	}
		    	
		    	loopbreak:
		    	if (line.startsWith("UserName: ")) {
		    		usersList[currentID][1] = line.substring(10);
		    		break loopbreak;
		    	}
		    }
	
		    br.close();
		    
		    Log.log("Users list has been loaded.");
	    
		} catch (IOException e) {
			Log.error("Unable to load users list.");
		}
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
}
