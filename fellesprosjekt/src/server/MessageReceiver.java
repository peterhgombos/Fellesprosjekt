package server;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.ParsingException;
import nu.xom.ValidityException;


public class MessageReceiver {
	
	private HashMap<InetAddress, ClientWriter> clients;
	private Database database;
	
	public MessageReceiver() {
		clients = new HashMap<InetAddress, ClientWriter>();
		database = new Database();
		
		receiveMessage("<?xml version='1.0' encoding='UTF-8'?><Tull><Ting></Ting></Tull>");
	}
	
	public synchronized void receiveMessage(String message){
		ServerConstants.console.writeline(message);

		Builder parser = new Builder(false);
		Document doc = null;
		try{
			doc = parser.build(message, "");
		}catch(Exception e){
			e.printStackTrace();
		}
		Element rootElement = doc.getRootElement();
		System.out.println(rootElement.getLocalName());
		
		
		
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
