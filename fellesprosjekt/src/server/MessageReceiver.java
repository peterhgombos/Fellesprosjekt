package server;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import utilities.XmlUtilities;

import client.connection.MessageType;
import dataobjects.Meeting;
import dataobjects.Person;

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
			
			
			
			try{
				ResultSet result = database.executeQuery(Queries.getMeetingsAsParticipant(personid)); //Get the meetings where the person is a participants		
				
				while(result.next()){
					int id = result.getInt(Database.COL_APPOINTMENTID);
					String title = result.getString(Database.COL_TITLE);
					String description = result.getString(Database.COL_DESCRIPTION);
					Date start = result.getDate(Database.COL_FROM);
					Date end = result.getDate(Database.COL_TO);
					
					
					ResultSet leaderRes = database.executeQuery(Queries.getLeaderForMeeting(id));
					Person leader = resultSetToPerson(leaderRes, true).keySet().iterator().next();
					
					ResultSet participantRes = database.executeQuery(Queries.getParticipantsForMeeting(id));
					HashMap<Person, Integer> participants = resultSetToPerson(participantRes, false);
					
					Meeting meeting = new Meeting(id, leader, title, description, start, end, participants);
				}
			}catch(SQLException e){
				//TODO Better error handling
				e.printStackTrace();
			}
			
			try{
				ResultSet result = database.executeQuery(Queries.getMeetingsAsLeader(personid)); //Get the meetings where the person is a participants		

				int id = result.getInt(Database.COL_APPOINTMENTID);
				String title = result.getString(Database.COL_TITLE);
				String description = result.getString(Database.COL_DESCRIPTION);
				Date start = result.getDate(Database.COL_FROM);
				Date end = result.getDate(Database.COL_TO);
				
				ResultSet leaderRes = database.executeQuery(Queries.getLeaderForMeeting(id));
				Person leader = resultSetToPerson(leaderRes, true).keySet().iterator().next();
				
				ResultSet participantRes = database.executeQuery(Queries.getParticipantsForMeeting(id));
				HashMap<Person, Integer> participants = resultSetToPerson(participantRes, false);
				
				Meeting meeting = new Meeting(id, leader, title, description, start, end, participants);
				
			}catch(SQLException e){
				//TODO Better error handling
				e.printStackTrace();
			}
			
			try{
				ResultSet result = database.executeQuery(Queries.getAppointmentsAsLeader(personid));	//Get appointments (no participants )for the relevant person 	

			}catch(SQLException e){
				//TODO Better error handling
				e.printStackTrace();
			}
			
		}
		
	}
	private HashMap<Person, Integer> resultSetToPerson(ResultSet rs, boolean leder){
		HashMap<Person, Integer> returnthis = new HashMap<Person, Integer>();
		try{
			while(rs.next()){
				int id = rs.getInt(Database.COL_PERSONID);
				String fornavn = rs.getString(Database.COL_FORNAVN);
				String etternavn = rs.getString(Database.COL_ETTERNAVN);
				String epost = rs.getString(Database.COL_EPOST);
				String brukernavn = rs.getString(Database.COL_BRUKERNAVN);
				String tlf = rs.getString(Database.COL_TLF);
				
				int svar = leder ? Meeting.SVAR_OK : rs.getInt(Database.COL_ANSWER);
				
				returnthis.put(new Person(id, fornavn, etternavn, epost, brukernavn, tlf), svar);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return returnthis;
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
