package pl.wallet616.Main;

public class CommandHandler extends Main{
	public static boolean commandInput(String[] message) {
		boolean repeat = false;
		int id = 0;
		
		breakpoint:
        	while (id < slots) {
				if (usersList[id][0] != null && usersList[id][0].equals(message[0])) {
					usersList[id][2] = String.valueOf(System.currentTimeMillis());
					repeat = true;
					break breakpoint;
				}
				id++;
		    }
		
		if (repeat) {
			if (message[1].equals("say")) {
				Log.log(message[2]);
			}
		} else {
			Log.log(message[2]);
		}
		
		return repeat;
	}
}
