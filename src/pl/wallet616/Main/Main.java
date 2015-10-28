package pl.wallet616.Main;

import java.io.File;

public class Main {
	// Static data.
	public static final int slots = 32;
	public static final int archivemem = 10;
	public static boolean serverOpen = true;
	
	// Files.
	public static File mainFolder;
	public static File mainServerFolder;
	public static File mainErrorFolder;
	public static File mainLogFolder;
	public static File dataFile;
	public static String logFile;
	public static String errorLogFile;
	
	// List of connected users. UserKey : UserName : UserTime
	public static String[][] usersList = new String[slots][3];
	// List of the newest messages. ID : Time : UserName : Message
	public static String[][] archive = new String[archivemem][4];

	public static void main(String[] args) {
		
		DataSave.getPaths();
		Background.background.start();
		ConnectionHandler.connection.start();
	}

}
