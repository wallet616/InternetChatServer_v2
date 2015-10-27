package pl.wallet616.Main;

import java.io.File;

public class Main {
	// Static data.
	public static final int slots = 32;
	public static final int archivemem = 32;
	public static boolean serverOpen = true;
	public static File mainFolder;
	public static File mainServerFolder;
	public static File mainErrorFolder;
	public static File mainLogFolder;
	public static File dataFile;
	public static String logFile;
	public static String errorLogFile;
	
	// Connected users list.
	public static String[][] usersList = new String[slots][3];
	public static String[][] archive = new String[archivemem][3];

	public static void main(String[] args) {
		archive[0][0] = "0";
		
		DataSave.getPaths();
		Background.background.start();
		ConnectionHandler.connection.start();
	}

}
