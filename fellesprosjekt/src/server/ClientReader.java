package server;

import java.io.BufferedReader;
import java.io.IOException;
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
	
	private void disconnect(){
		Console.writeline("Client has disconnected");
		try {
			reader.close();
			socket.close();
		} catch (IOException e) {
			//nothing they are allready disconnected
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
					disconnect();
					return;
				}
			} catch (IOException e) {
				disconnect();
				return;
			}
			
		}
	}
}
