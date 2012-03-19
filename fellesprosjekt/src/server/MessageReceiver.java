package server;

import java.net.InetAddress;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import utilities.XmlUtilities;
import client.connection.MessageType;
import dataobjects.Appointment;
import dataobjects.Meeting;
import dataobjects.Person;


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

		//TODO metoder for alle messagetype
		if(messageType.equals(MessageType.REQUEST_APPOINTMENTS)){
			Element personidelement = rootElement.getFirstChildElement("Personid");
			int personid = Integer.parseInt(personidelement.getValue());

			try{
				ResultSet result;
				Collection<Appointment> appointments;
				ClientWriter writer = clients.get(ip);
				
				result = database.executeQuery(Queries.getMeetingsAsParticipant(personid)); //Get the meetings where the person is a participants		
				appointments = resultSetToMeeting(result);
				writer.send(buildAppointmentXML(appointments));

				result = database.executeQuery(Queries.getMeetingsAsLeader(personid)); //Get the meetings where the person is a participants		
				appointments = resultSetToMeeting(result);
				writer.send(buildAppointmentXML(appointments));

				result = database.executeQuery(Queries.getAppointmentsAsLeader(personid));	//Get appointments (no participants )for the relevant person 	
				appointments= resultSetToAppointment(result);
				writer.send(buildAppointmentXML(appointments));
				
			}catch(SQLException e){
				//TODO Better error handling
				e.printStackTrace();
			}
		}
	}
	
	private String buildAppointmentXML(Collection<Appointment> appointments){
		StringBuilder xml = new StringBuilder();
		xml.append(XmlUtilities.XMLHEADER);
		for(Appointment a : appointments){
			xml.append(a.toXML());
		}
		xml.append((char)0);
		return xml.toString();
	}

	private ArrayList<Appointment> resultSetToMeeting(ResultSet result){
		ArrayList<Appointment> returnthis = new ArrayList<Appointment>();
		try{
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

				returnthis.add(new Meeting(id, leader, title, description, start, end, participants));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return returnthis;
	}
	private ArrayList<Appointment> resultSetToAppointment(ResultSet result){
		ArrayList<Appointment> returnthis = new ArrayList<Appointment>();
		try{
			while(result.next()){
				int id = result.getInt(Database.COL_APPOINTMENTID);
				String title = result.getString(Database.COL_TITLE);
				String description = result.getString(Database.COL_DESCRIPTION);
				Date start = result.getDate(Database.COL_FROM);
				Date end = result.getDate(Database.COL_TO);

				ResultSet leaderRes = database.executeQuery(Queries.getLeaderForMeeting(id));
				Person leader = resultSetToPerson(leaderRes, true).keySet().iterator().next();

				returnthis.add(new Appointment(id, leader, title, description, start, end));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return returnthis;
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
