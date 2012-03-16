package server;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import utilities.XmlUtilities;

import client.connection.MessageType;

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
		
		receiveMessage(null, "<?xml version='1.0' encoding='UTF-8'?><" + 
		MessageType.REQUEST_APPOINTMENTS + ">" +
		"<Personid>123</Personid>"	+	
		"</" + MessageType.REQUEST_APPOINTMENTS + ">");
	}
	
	public synchronized void receiveMessage(InetAddress ip, String message){
		ServerConstants.console.writeline(message);

		Builder parser = new Builder(false);
		Document doc = null;
		try{
			doc = parser.build(message, "");
		}catch(Exception e){
			e.printStackTrace();
		}
		Element rootElement = doc.getRootElement();
		
		String messageType = rootElement.getLocalName();
		
		if(messageType.equals(MessageType.REQUEST_APPOINTMENTS)){
			int personid = Integer.parseInt(rootElement.getAttributeValue("Personid"));
			
			ResultSet result = null;
			try{
				result = database.executeQuery(Queries.getAppointmentsAsLeader(personid));
				XmlUtilities.appointmentResultSet(result);
			}catch(SQLException e){
				e.printStackTrace();
			}
			
			
			
			
		}
		
		
		
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
