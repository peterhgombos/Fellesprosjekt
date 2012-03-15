package client.connection;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServerWriter {


	private Socket socket;
	private BufferedWriter writer;

	public ServerWriter(Socket socket) {
		this.socket = socket;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			Connection.console.writeline(e.getMessage());
		}
	}

	
	public void send(String s) {
		try {
			writer.write(s);
			writer.newLine();
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
			Connection.console.writeline(e.getMessage());
		}
	}
}
