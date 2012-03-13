package server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ClientWriter{
	
	
	private Socket socket;
	private BufferedWriter writer;
	
	public ClientWriter(Socket socket) {
		this.socket = socket;
		
		try {
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			Console.writeline(e.getMessage());
		}
	}
	
	public void send(String message) {
		try {
			writer.write(message);
			writer.newLine();
			writer.flush();
		} catch (IOException e) {
			Console.writeline(e.getMessage());
		}
	}
	
	public InetAddress getIP(){
		return socket.getInetAddress();
	}
	
}
