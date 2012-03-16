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
		//TODO change hardcodes below
		receiveMessage(null, "<?xml version='1.0' encoding='UTF-8'?><" + 
		MessageType.REQUEST_APPOINTMENTS + ">" +
		"<Personid>1234</Personid>"	+										//Hardcoded personid 1234. Change to relative
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
		
		
		//TODO methods for each messagetype
		if(messageType.equals(MessageType.REQUEST_APPOINTMENTS)){
			Element personidelement = rootElement.getFirstChildElement("Personid");
			int personid = Integer.parseInt(personidelement.getValue());
			
			ResultSet result = null;
			try{
				result = database.executeQuery(Queries.getAppointmentsAsPartcipant(personid));		
				XmlUtilities.appointmentAsParticipant(result);
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
