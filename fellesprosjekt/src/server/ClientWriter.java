package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import dataobjects.ComMessage;


public class ClientWriter{
	
	
	private Socket socket;
	private ObjectOutputStream writer;
	
	/**
	 * en klasse som skriver til en klient, identifiserers vha Ip 
	 * @param socket
	 */
	public ClientWriter(Socket socket) {
		this.socket = socket;
		
		try {
			writer = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			ServerConstants.console.writeline(e.getMessage());
		}
	}
	
	public void send(ComMessage message){
		try{
			writer.writeObject(message);
			writer.flush();
		}catch(IOException e){
			//TODO
			e.printStackTrace();
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
