package pl.wallet616.Main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommandHandler extends Main{
	public static String commandInput(String[] message) {
		String repeat = "1:";
		boolean userFound = false;
		int id = 0;
		
		breakpoint:
        	while (id < slots) {
				if (usersList[id][0] != null && usersList[id][0].equals(message[0])) {
					userFound = true;
					break breakpoint;
				}
				id++;
		    }
		
		if (userFound) {
			if (message[1].equals("arch")) {
				usersList[id][2] = String.valueOf(System.currentTimeMillis());
				String returnString = "";
				breakpoint:
				for (int i = 0; i < archivemem; i++) {
					if (archive[i][0] != null && Integer.parseInt(message[2]) < Integer.parseInt(archive[i][0])) {
						returnString += "<?:?>" + archive[i][0] + "<#:#>" + archive[i][1] + "<#:#>" + archive[i][2] + "<#:#>" + archive[i][3];
					} else {
						break breakpoint;
					}
				}
				if (returnString.equals("")) {
					repeat += "2:0";
				} else {
					repeat += "2:" + returnString.substring(5);
				}
				
			} else if (message[1].equals("say")) {
				archiveAdd(usersList[id][1], message[2]);
				repeat += "1:1";
				
			}
		} else {
			if (message[1].equals("load")) {
				if(DataRead.loadUser(message[0], false)) {
					repeat += "3:1";
					
				} else if (DataSave.addData(message[0], message[2])) {
					repeat += "3:1";
					
				} else {
					repeat += "0:0";
					
				}
			} else {
				repeat += "3:0";
			}
		}
		return repeat;
	}
	
	public static boolean archiveAdd(String userName, String message) {
		// Adding time.
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
		Date date = new Date();
		
		// Move content of archive to farther position.
		for (int i = 0; i < archivemem - 1; i++) {
			if (archive[i][0] != null) {
				archive[i + 1][0] = archive[i][0];
				archive[i + 1][1] = archive[i][1];
				archive[i + 1][2] = archive[i][2];
				archive[i + 1][3] = archive[i][3];
			} else {
				break;
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
}
