package server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

import server.constants.ServerConstants;

public class ClientWriter{
	
	
	private Socket socket;
	private BufferedWriter writer;
	
	/**
	 * en klasse som skriver til en klient, identifiserers vha Ip 
	 * @param socket
	 */
	public ClientWriter(Socket socket) {
		this.socket = socket;
		
		try {
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			ServerConstants.console.writeline(e.getMessage());
		}
	}
	
	/**
	 * sender en melding som en tekststreng til klienten
	 * @param message
	 */
	public void send(String message) {
		try {
			writer.write(message);
			writer.newLine();
			writer.flush();
		} catch (IOException e) {
			ServerConstants.console.writeline(e.getMessage());
		}
	}
	
	/**
	 * klientens ipadresse
	 * @return  IP
	 */
	public InetAddress getIP(){
		return socket.getInetAddress();
	}
	
}
