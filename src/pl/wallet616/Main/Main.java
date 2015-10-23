package pl.wallet616.Main;

public class Main {
	// Static data.
	public static final int slots = 32;
	public static boolean serverOpen = true;
	
	// Connected users list.
	public static String[][] usersList = new String[slots][3];

	public static void main(String[] args) {
		Background.background.start();
		ConnectionHandler.connection.start();
	}

}
