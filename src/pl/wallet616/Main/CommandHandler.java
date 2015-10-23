package pl.wallet616.Main;

public class CommandHandler extends Main{
	public static boolean commandInput(String[] message) {
		boolean repeat = false;
		int id = 0;
		
		breakpoint:
        	while (id < slots) {
				if (usersList[id][0] != null && usersList[id][0].equals(message[0])) {
					repeat = true;
					break breakpoint;
				}
				id++;
		    }
		
		if (repeat) {
			if (message[1].equals("status")) {
				usersList[id][2] = String.valueOf(System.currentTimeMillis());
			} else if (message[1].equals("say")) {
				Log.log(message[2]);
			}
		} else {
			if (message[1].equals("load")) {
				if(DataRead.loadUser(message[0], false)) {
					repeat = true;
				} else if (DataSave.addData(message[0], message[2])) {
					repeat = true;
				}
			}
		}
		return repeat;
	}
}
