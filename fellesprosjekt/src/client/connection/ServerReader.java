package client.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import dataobjects.ComMessage;

public class ServerReader extends Thread {
	
	private Socket socket;
	private ObjectInputStream reader;
	private Connection connection;
	
	public ServerReader(Socket socket, Connection connection) {
		this.socket = socket;
		this.connection = connection;
		try {
			reader = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			//Connection.console.writeline(e.getMessage());
		}
	}
	
	private void disconnect(){
		//Connection.console.writeline("Client has disconnected");
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
				ComMessage message = (ComMessage)reader.readObject();
				if (message == null) {
					disconnect();
					return;
				}
				
				connection.receiveMessage(message);
			} catch (IOException e) {
				e.printStackTrace();
				disconnect();
				return;
			}catch(ClassNotFoundException e){
				e.printStackTrace();
				disconnect();
				return;
			}
		}
	}
}
