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
		
		DataRead.loadUser("22d21sf2df", false);
		
		DataSave.changeData("22d21saf2df", "userName", "Proszek do robienia kart");
		
		usersList[0][2] = "" + System.currentTimeMillis();
	}

}
