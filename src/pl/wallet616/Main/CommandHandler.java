package pl.wallet616.Main;

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
				// Archive read here. "1:2"here"
				
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
					repeat += "3:0";
					
				}
			} else {
				repeat += "0:0";
				
			}
		}
		return repeat;
	}
	
	public static boolean archiveAdd(String userName, String message) {
		// Move content of archive to farther position.
		for (int i = 0; i < archivemem; i++) {
			archive[i][1] = archive[i + 1][1];
		}
		
		// Generate id.
		if (archive[1][0] != null) {
			archive[0][0] = String.valueOf(Long.getLong(archive[1][0]) + 1);
		} else {
			archive[0][0] = "0";
		}
		
		// Assign new message to archive.
		archive[0][1] = message;
		DataSave.archiveSave(userName, message);
		
		return true;
	}
}
