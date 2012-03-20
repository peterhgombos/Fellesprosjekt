package client.connection;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import dataobjects.ComMessage;

public class ServerWriter {

	private Socket socket;
	private ObjectOutputStream writer;

	public ServerWriter(Socket socket) {
		this.socket = socket;
		try {
			writer = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			Connection.console.writeline(e.getMessage());
		}
	}
	
	public void send(ComMessage m) {
		try {
			writer.writeObject(m);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
			Connection.console.writeline(e.getMessage());
		}
	}
}
