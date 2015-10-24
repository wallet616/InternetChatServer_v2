package pl.wallet616.Main;

public class CommandHandler extends Main{
	public static String commandInput(String[] message) {
		String repeat = "1";
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
				// Tutaj dorobic odczyt wiadomosci o ile takie istnieja.
			} else if (message[1].equals("say")) {
				Log.log(message[2]);
				repeat += ":1:1";
			}
		} else {
			if (message[1].equals("load")) {
				if(DataRead.loadUser(message[0], false)) {
					repeat += ":1:1";
				} else if (DataSave.addData(message[0], message[2])) {
					repeat += ":1:1";
				} else {
					repeat += ":1:0";
				}
			} else {
				repeat += ":0:0";
			}
		}
		return repeat;
	}
}
