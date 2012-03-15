package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import server.constants.ServerConstants;

public class ConnectionReceiver extends Thread{

	private ServerSocket serverSocket;
	private MessageReceiver server;
	
	public ConnectionReceiver(MessageReceiver server) {
		this.server = server;
	}

	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(ServerConstants.PORT);
		} catch (IOException e) {
			Console.writeline(e.getMessage());
		}
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				
				ClientWriter clientWriter = new ClientWriter(socket);
				server.addClient(clientWriter);				
				ClientReader clientReader = new ClientReader(socket, server);
				clientReader.start();
				
			} catch (IOException e) {
				Console.writeline(e.getMessage());
				
			}
			
		}
		
		
	}
}
