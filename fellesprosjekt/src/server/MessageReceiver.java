package server;

import java.net.InetAddress;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import server.database.Database;
import server.database.Queries;

import common.dataobjects.Appointment;
import common.dataobjects.ComMessage;
import common.dataobjects.Meeting;
import common.dataobjects.Note;
import common.dataobjects.Person;
import common.utilities.DateString;
import common.utilities.MessageType;

import client.authentication.Login;

public class MessageReceiver {

	private HashMap<InetAddress, ClientWriter> clients;
	private Database database;

	public MessageReceiver() {
		clients = new HashMap<InetAddress, ClientWriter>();
		database = new Database();
		database.connect();
	}

	public synchronized void receiveMessage(InetAddress ip, ComMessage message){
		String messageType = message.getType();
		ClientWriter clientWriter = clients.get(ip);

		if(messageType.equals(MessageType.REQUEST_APPOINTMENTS_AND_MEETINGS)){

			Person p = (Person)message.getData();
			int personid = p.getPersonID();

			try{
				ResultSet appointmentResult = database.executeQuery(Queries.getAppointments(personid)); //Get the appointments where the person is a participants	
				ResultSet meetingResult = database.executeQuery(Queries.getMeetings(personid)); //Get the meetings where the person is a participants	

				Collection<Appointment> appointments = resultSetToAppointment(appointmentResult);
				Collection<Meeting> meetings = resultSetToMeeting(meetingResult);

				for(Meeting m: meetings){
					Server.console.writeline(m.getTitle());
					Server.console.writeline(m.getAppointmentLeader().getFirstname());
					for(Person pp : m.getParticipants().keySet()){
						Server.console.writeline("  " + pp.getFirstname());
					}
				}

				ComMessage sendapp = new ComMessage(appointments, MessageType.RECEIVE_APPOINTMENTS);
				ComMessage sendmeet = new ComMessage(meetings, MessageType.RECEIVE_MEETINGS);

				clientWriter.send(sendapp);
				clientWriter.send(sendmeet);

			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		else if(messageType.equals(MessageType.REQUEST_LOGIN)){
			Person authenticatedPerson = requestLogin(message);
			ComMessage sendLogin = new ComMessage(authenticatedPerson, MessageType.RECEIVE_LOGIN);
			clientWriter.send(sendLogin);
		}
		else if(messageType.equals(MessageType.REQUEST_NEW_APPOINTMENT)){
			newAppointment(message);
		}
		else if(messageType.equals(MessageType.REQUEST_NEW_NOTE)){
			Note createdNote = newNote(message);
			ComMessage sendNote = new ComMessage(createdNote, MessageType.RECEIVE_NEW_NOTE);
			clientWriter.send(sendNote);
		}
	}

	private Person requestLogin(ComMessage message){
		Login login = (Login) message.getData();
		try{
			ResultSet personResult = database.executeQuery(Queries.loginAuthentication(login.getUserName(), login.getPasswordHash()));

			return resultSetToLoginPerson(personResult);

		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	
	private void newAppointment(ComMessage message){
		Appointment newApp = (Appointment) message.getData();
		try{
			database.updateDB(Queries.createNewAppointment(newApp.getTitle(), newApp.getDescription(), newApp.getStartTime(), newApp.getEndTime(),newApp.getPlace(), newApp.getLeader().getPersonID()));
			ComMessage comMesNewApp = new ComMessage(newApp, MessageType.RECEIVE_NEW_APPOINTMENT);
			sendToAll(comMesNewApp);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	private Note newNote(ComMessage message){
		Note newNote = (Note) message.getData();
		try{
			database.updateDB(Queries.newNote(newNote.getTitle(), newNote.getAppointment().getId()));
			ResultSet newDbNote = database.executeQuery(Queries.getLastNote());
			return resultSetToSingleNote(newDbNote);
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	private ArrayList<Meeting> resultSetToMeeting(ResultSet result){
		ArrayList<Meeting> returnthis = new ArrayList<Meeting>();
		try{
			while(result.next()){
				int id = result.getInt(Database.COL_APPOINTMENTID);
				int leaderid = result.getInt(Database.COL_LEADER);
				String title = result.getString(Database.COL_TITLE);
				String description = result.getString(Database.COL_DESCRIPTION);
				String place = result.getString(Database.COL_PLACE);
				DateString start = new DateString(result.getString(Database.COL_FROM));
				DateString end = new DateString(result.getString(Database.COL_TO));

				ResultSet participantRes = database.executeQuery(Queries.getParticipantsForMeeting(id));
				HashMap<Person, Integer> participants = resultSetToPerson(participantRes);
				Person leader = null;
				for(Person p : participants.keySet()){
					if(p.getPersonID() == leaderid){
						leader = p;
					}
				}

				returnthis.add(new Meeting(id, leader, title, description, place, start, end, participants));
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
				String place = result.getString(Database.COL_PLACE);
				DateString start = new DateString(result.getString(Database.COL_FROM));
				DateString end = new DateString(result.getString(Database.COL_TO));

				ResultSet participantRes = database.executeQuery(Queries.getParticipantsForMeeting(id));
				Person leader = resultSetToPerson(participantRes).keySet().iterator().next();

				returnthis.add(new Appointment(id, leader, title, description, place, start, end));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return returnthis;
	}

	private HashMap<Person, Integer> resultSetToPerson(ResultSet rs){
		HashMap<Person, Integer> returnthis = new HashMap<Person, Integer>();
		try{
			while(rs.next()){
				int id = rs.getInt(Database.COL_PERSONID);
				String fornavn = rs.getString(Database.COL_FORNAVN);
				String etternavn = rs.getString(Database.COL_ETTERNAVN);
				String epost = rs.getString(Database.COL_EPOST);
				String brukernavn = rs.getString(Database.COL_BRUKERNAVN);
				String tlf = rs.getString(Database.COL_TLF);
				int svar = rs.getInt(Database.COL_ANSWER);

				returnthis.put(new Person(id, fornavn, etternavn, epost, brukernavn, tlf), svar);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return returnthis;
	}

	private Person resultSetToLoginPerson(ResultSet rs){
		try{
			while(rs.next()){
				int id = rs.getInt(Database.COL_PERSONID);
				String fornavn = rs.getString(Database.COL_FORNAVN);
				String etternavn = rs.getString(Database.COL_ETTERNAVN);
				String epost = rs.getString(Database.COL_EPOST);
				String brukernavn = rs.getString(Database.COL_BRUKERNAVN);
				String tlf = rs.getString(Database.COL_TLF);
				return new Person(id, fornavn, etternavn, epost, brukernavn, tlf);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	
	private Note resultSetToSingleNote(ResultSet rs){
		try{
			while(rs.next()){
				ResultSet appointmentResult = database.executeQuery(Queries.getAppointment(rs.getInt("AVTALE")));
				Appointment app = resultSetToAppointment(appointmentResult).get(0);
				return new Note(rs.getInt("AVTALEID"), rs.getString("TITTEL"), rs.getDate("TIDSENDT"), app);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	public synchronized void addClient(ClientWriter clientWriter){
		clients.put(clientWriter.getIP(), clientWriter);
	}

	public synchronized void sendToAll(ComMessage message){
		Server.console.writeline("send to all");
		for (ClientWriter client : clients.values()) {
			Server.console.writeline("send to: " + client.getIP());
			client.send(message);
		}
	}
}
