package pl.wallet616.Main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommandHandler extends Main{
	public static String commandInput(String[] message) {
		boolean userFound = false;
		int id = 0;
		
		// Checks if user is connected.
		if (checkIfNotNull(message[0])) {
			while (id < slots) {
				if (usersList[id][0] != null && usersList[id][0].equals(message[0])) {
					userFound = true;
					break;
				}
				id++;
			}
		}
		
		if (userFound) {
			if (message[1].equals("arch")) {
				usersList[id][2] = String.valueOf(System.currentTimeMillis());
				if (checkIfNumber(message[2])) {
					return arch(message[2]);
				}
			
			} else if (message[1].equals("say")) {
				if (checkIfNotNull(message[2]) && archiveAdd(usersList[id][1], message[2])) {
					return "1:1:1";
				} else {
					return "1:1:0";
				}
			}
		
		} else {
			// Tries to load him.
			if (message[1].equals("load")) {
				if (checkIfNotNull(message[0])) {
					if(DataRead.loadUser(message[0], false)) {
						return "1:3:1";
					} else if (DataSave.addData(message[0], message[2])) {
						return "1:3:1";
					} else {
						return "1:1:0";
					}
				}
			} else {
				return "1:3:0";
			}
		}
		
		return "1:0:0";
	}
	
	public static boolean archiveAdd(String userName, String message) {
		// Adding time.
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
		Date date = new Date();
		
		// Move content of archive to farther position.
		for (int i = archivemem - 2; i >= 0; i--) {
			if (archive[i][0] != null) {
				archive[i + 1][0] = archive[i][0];
				archive[i + 1][1] = archive[i][1];
				archive[i + 1][2] = archive[i][2];
				archive[i + 1][3] = archive[i][3];
			}
		}
		
		// Generate id.
		if (archive[0][0] != null) {
			archive[0][0] = String.valueOf(Long.parseLong(archive[0][0]) + 1);
		} else {
			archive[0][0] = "0";
		}
		
		// Assign new message to archive.
		archive[0][1] = String.valueOf(dateFormat.format(date));
		archive[0][2] = userName;
		archive[0][3] = message;
		DataSave.archiveSave(userName, message);
		
		return true;
	}
	
	// Archive reading.
	public static String arch(String id) {
		String repeat = "1:2:";
		String returnString = "";
		for (int i = archivemem - 1; i >= 0; i--) {
			if (archive[i][0] != null && Integer.parseInt(id) < Integer.parseInt(archive[i][0])) {
				returnString += ".ws1" + archive[i][0] + ".ws2" + archive[i][1] + ".ws2" + archive[i][2] + ".ws2" + archive[i][3];
			}
		}
		
		if (returnString.equals("")) {
			repeat += "0";
		} else {
			repeat += returnString.substring(4);
		}
		return repeat;
	}
	
	public static boolean checkIfNumber(String string) {
		if (string.replace("[0-9]+", "").length() == 0 && !string.equals("")) {
			return false;
		}
		return true;
	}
	
	public static boolean checkIfNotNull(String string) {
		if (!string.equals("")) {
			return true;
		}
		return false;
	}
}
