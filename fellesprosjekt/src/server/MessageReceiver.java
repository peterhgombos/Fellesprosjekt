package server;

import java.net.InetAddress;
import java.util.HashMap;

import server.constants.ServerConstants;

public class MessageReceiver {
	
	private HashMap<InetAddress, ClientWriter> clients;
	private Database database;
	
	public MessageReceiver() {
		clients = new HashMap<InetAddress, ClientWriter>();
		database = new Database();
	}
	
	public synchronized void receiveMessage(String message){
		ServerConstants.console.writeline(message);
		sendToAll(message);
	}
	
	public synchronized void addClient(ClientWriter clientWriter){
		clients.put(clientWriter.getIP(), clientWriter);
	}
	
	public synchronized void sendToAll(String message){
		ServerConstants.console.writeline("send to all");
		for (ClientWriter client : clients.values()) {
			ServerConstants.console.writeline("send to: " + client.getIP());
			client.send(message);
		}
	}
}
