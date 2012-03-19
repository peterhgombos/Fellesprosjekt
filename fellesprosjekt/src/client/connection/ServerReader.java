package client.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

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
				String xml = readXML();
				if (xml == null) {
					disconnect();
					return;
				}
				
				Connection.receiveMessage(xml);
				
			} catch (IOException e) {
				e.printStackTrace();
				disconnect();
				return;
			}
		}
	}
	private String readXML() throws IOException{
		StringBuilder builder = new StringBuilder();
		char end = 1;
		while (end != 0) {
			if (end == -1) {
				return null;
			}
			end = (char) reader.read();
			builder.append(end);
		}
		
		return builder.toString();
		
	}
}
