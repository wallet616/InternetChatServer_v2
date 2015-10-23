package pl.wallet616.Main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionHandler extends Main {
	public static Thread connection = new Thread() {
		// Creating handler of incoming connections.
		private final static int port = 616;
		private Socket socket;

		public void run() {
			try {
				ServerSocket serverSocket = new ServerSocket(port);
				Log.log("Server created at port " + port + ".");
				while (serverOpen) {
					// Reading the message from the client
					socket = serverSocket.accept();
	                InputStream is = socket.getInputStream();
	                InputStreamReader isr = new InputStreamReader(is);
	                BufferedReader br = new BufferedReader(isr);
	                String commandInput = br.readLine();
	                
	                // Formatting received message
	                String returnMessage = "";
	                String[] buffer = new String[3];
	                Boolean vaildCommand = false;
	                
	                try 
	    			{
	                	if ((commandInput.length() - commandInput.replaceAll(":", "").length()) >= 2)
	    				{
	                		buffer = commandInput.split(":", 3);
	                		vaildCommand = true;
	    				}
	    			}
	    			catch (Exception e)
	    		    {
	    				Log.log("Invaild message format received.");
	    			}
	                
	                
	                if (vaildCommand) {
	                	if (CommandHandler.commandInput(buffer)) {
	                		// To remove!! //
	                		Log.log(buffer[0]);
	                		Log.log(buffer[1]);
	                		Log.log(buffer[2]);
	                		// To remove!! //
	                		returnMessage = "1:1:1";
	                	} else {
	                		returnMessage = "1:1:0";
	                	}
	                } else {
	                	returnMessage = "1:0:0";
	                }
	                
	                //Sending the response back to the client.
	                OutputStream os = socket.getOutputStream();
	                OutputStreamWriter osw = new OutputStreamWriter(os);
	                BufferedWriter bw = new BufferedWriter(osw);
	                bw.write(returnMessage + "\n");
	                bw.flush();
				}
				
				try {
					serverSocket.close();
					Log.log("Server closed.");
				} catch (IOException e) {
					e.printStackTrace();
					Log.error("Unable to close the server.");
				}
			} catch (IOException e) {
				e.printStackTrace();
				Log.error("Unable to create the server.");
			}
		}
	};
}
