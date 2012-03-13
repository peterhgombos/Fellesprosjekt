package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientReader extends Thread{
	private BufferedReader reader;
	private Socket socket;
	private MessageReceiver server;
	
	public ClientReader(Socket socket, MessageReceiver server) {
		this.server = server;
		this.socket = socket;
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			Console.writeline(e.getMessage());
		}
		
		
	}
	
	@Override
	public void run() {
		while(true){
			try {
				String message = reader.readLine();
				if(message != null){
					server.receiveMessage(message);
				}
				else {
					Console.writeline("Client has disconnected");
					return;
				}
			} catch (IOException e) {
				Console.writeline("Client has disconnected");
				return;
			}
			
		}
	}
}
