package pl.wallet616.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
					serverOpen = false;
				} else if (consoleInput.startsWith("say ")) {
					CommandHandler.archiveAdd("Server", consoleInput.substring(4));
				} else if (consoleInput.startsWith("change ")) {
					String[] buff = consoleInput.substring(7).split(" ");
					DataSave.changeData(buff[0], buff[1], buff[2]);
				} else if (consoleInput.startsWith("list")) {
					int j = 0;
					System.out.println("#==============# Users List #==============#");
					for (int i = 0; i < usersList.length; i++) {
						if (usersList[i][0] != null) {
							j++;
							System.out.println("# " + j + ". " + usersList[i][1]);
						}
					}
					if (j == 0) {
						System.out.println("# No users online.");
					}
					DateFormat dateF = new SimpleDateFormat("[HH:mm:ss]");
					System.out.println("#==============# " + dateF.format(new Date()) + " #==============#");
				} else {
					Log.log("Command unreconised.");
				}
			}
		} catch (IOException e) {
			Log.error("Unable to read data from console.");
		}
	}
}
