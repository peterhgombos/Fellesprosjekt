package server;

import java.net.InetAddress;
import java.util.HashMap;


public class MessageReceiver {
	
	private HashMap<InetAddress, ClientWriter> clients;
	private Database database;
	
	public MessageReceiver() {
		clients = new HashMap<InetAddress, ClientWriter>();
		database = new Database();
	}
	
	public synchronized void receiveMessage(String message){
		Console.writeline(message);
		sendToAll(message);
	}
	
	public synchronized void addClient(ClientWriter clientWriter){
		clients.put(clientWriter.getIP(), clientWriter);
	}
	
	public synchronized void sendToAll(String message){
		Console.writeline("send to all");
		for (ClientWriter client : clients.values()) {
			Console.writeline("send to: "+client.getIP());
			client.send(message);
		}
	}
}
