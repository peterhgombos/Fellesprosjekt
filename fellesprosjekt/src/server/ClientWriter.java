package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import common.dataobjects.ComMessage;

public class ClientWriter{
	
	private Socket socket;
	private ObjectOutputStream writer;
	private MessageReceiver messageReceiver;
	public int id = -1;
	
	/**
	 * en klasse som skriver til en klient, identifiserers vha Ip 
	 * @param socket
	 */
	public ClientWriter(MessageReceiver r, Socket socket) {
		this.socket = socket;
		this.messageReceiver = r;
		
		try {
			writer = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			Server.console.writeline(e.getMessage());
		}
	}
	
	public void send(ComMessage message){
		try{
			writer.writeObject(message);
			writer.flush();
		}catch(IOException e){
			messageReceiver.removeClient(socket.getInetAddress());
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
