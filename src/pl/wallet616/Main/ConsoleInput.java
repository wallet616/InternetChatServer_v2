package pl.wallet616.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleInput extends Main {
	
	// Console input listener. 
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	
	public static void console() {
		try {
			while (true) {
				String consoleInput = input.readLine();
				consoleInput = DataRead.clearText(consoleInput);
				
				if (consoleInput.equals("stop")) {
					Log.log("Server has been stopped.");
				} else if (consoleInput.equals("disable connection")) {
					Log.log("Server connection has been disabled.");
					serverOpen = false;
				} else if (consoleInput.equals("disable connection")) {
					Log.log("Server connection has been enabled.");
					serverOpen = true;
				} else if (consoleInput.startsWith("say ")) {
					CommandHandler.archiveAdd("Server", consoleInput.substring(4));
				} else if (consoleInput.startsWith("change ")) {
					String[] buff = consoleInput.substring(7).split(" ");
					DataSave.changeData(buff[0], buff[1], buff[2]);
				} else {
					Log.log("Command unreconised.");
				}
			}
		} catch (IOException e) {
			Log.error("Unable to read data from console.");
		}
	}
}
