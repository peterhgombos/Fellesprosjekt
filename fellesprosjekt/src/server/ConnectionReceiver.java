package server;

import java.io.IOException;
import java.net.ServerSocket;

import server.constants.ServerConstants;

public class ConnectionReceiver extends Thread{

	private ServerSocket serverSocket;

	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(ServerConstants.PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
