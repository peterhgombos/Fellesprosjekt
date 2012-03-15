package client.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.Socket;

import server.ServerConstants;

public class ServerReader extends Thread {
	
	private Socket socket;
	private BufferedReader reader;
	
	public ServerReader(Socket socket) {
		this.socket = socket;
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			Connection.console.writeline(e.getMessage());
		}
	}
	
	private void disconnect(){
		Connection.console.writeline("Client has disconnected");
		try {
			reader.close();
			socket.close();
		} catch (IOException e) {
			//ingenting, hvis det skjer noe her f�r det bare v�re, de er allerede frakoblet
			//egentlig kan man ikke annta dette, det kan skje skumle feil men det kan bli komplisert
		}
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				String message = reader.readLine();
				if (message == null) {
					disconnect();
					return;
				}
				
				Connection.console.writeline(message);
				
			} catch (IOException e) {
				e.printStackTrace();
				disconnect();
				return;
			}
		}
	}
}
