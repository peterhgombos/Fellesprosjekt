package server;

import java.net.InetAddress;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import server.database.Database;
import server.database.Queries;
import client.authentication.Login;

import common.dataobjects.Appointment;
import common.dataobjects.ComMessage;
import common.dataobjects.Meeting;
import common.dataobjects.Note;
import common.dataobjects.Person;
import common.dataobjects.Room;
import common.sendobjects.AnswerUpdates;
import common.sendobjects.AppointmentInvites;
import common.utilities.DateString;
import common.utilities.MessageType;

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
		else if(messageType.equals(MessageType.REQUEST_MEETINGS_AND_APPOINTMENTS_BY_DATE_FILTER)){
			meetingsandappointmentsbydate(message, clientWriter);
		}
		else if(messageType.equals(MessageType.REQUEST_NEW_APPOINTMENT)){
			newAppointment(message);
		}
		else if(messageType.equals(MessageType.REQUEST_NEW_MEETING)){
			newMeeting(message);
		}
		else if(messageType.equals(MessageType.REQUEST_NEW_NOTE)){
			Note createdNote = newNote(message);
			ComMessage sendNote = new ComMessage(createdNote, MessageType.RECEIVE_NEW_NOTE);
			clientWriter.send(sendNote);
		}
		else if(messageType.equals(MessageType.REQUEST_SEARCH_PERSON)){
			ArrayList<Person> filteredPersons = searchForPerson(message);
			ComMessage sendPersons = new ComMessage(filteredPersons, MessageType.RECEIVE_SEARCH_PERSON);
			clientWriter.send(sendPersons);
		}
		else if(messageType.equals(MessageType.REQUEST_ADD_ATTENDANT)){
			addAttendant(message);
		}
		else if(messageType.equals(MessageType.REQUEST_PARTICIPANTS)){
			ArrayList<Person> persons = searchForMeetingParticipants(message);
			ComMessage getParticipants = new ComMessage(persons, MessageType.RECEIVE_PARTICIPANTS);
			clientWriter.send(getParticipants);
		}
		else if(messageType.equals(MessageType.REQUEST_UPDATE_ANSWER)){
			AnswerUpdates answerUpdates = (AnswerUpdates) message.getData();
			HashMap<Person, Integer> personsWithAnwsers = answerUpdates.getPersonsWithAnswer();
			Appointment appointment = answerUpdates.getAppointment();
			for(Person p : personsWithAnwsers.keySet()){
				try{
					database.updateDB(Queries.updateAnswerToInvite(p.getPersonID(), appointment.getId(), personsWithAnwsers.get(p)));
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
		else if(messageType.equals(MessageType.REQUEST_UPDATE_APPOINTMENT)){
			Appointment a = (Appointment) message.getData();
			try{
				database.updateDB(Queries.updateAppointment(a.getId(), a.getTitle(), a.getDescription(), a.getStartTime(), a.getEndTime(), a.getPlace()));
			}
			catch(SQLException e){
				e.printStackTrace();
			}

		}
		else if(messageType.equals(MessageType.REQUEST_ROOMS_AVAILABLE)){
			ArrayList<Room> availableRooms = getAvaliableRooms(message);
			ComMessage rooms = new ComMessage(availableRooms, MessageType.RECEIVE_ROOM_AVAILABLE);
			clientWriter.send(rooms);
		}
		else if(messageType.equals(MessageType.REQUEST_BOOK_ROOM)){
			Meeting meeting = (Meeting) message.getData();
			try{
				database.updateDB(Queries.bookRoom(meeting.getId(),meeting.getRoom().getRomId()));
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		else if (messageType.equals(MessageType.REQUEST_NOTES)) {
			Person p = (Person)message.getData();
			try {
				ResultSet rs = database.executeQuery(Queries.getNotes(p.getPersonID(), message.getProperty("filter")));
				ArrayList<Note> result = resultSetToNotes(rs);
				clientWriter.send(new ComMessage(result, MessageType.RECEIVE_NOTES));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if (messageType.equals(MessageType.DELETE_NOTE)) {
			Server.console.writeline("delete notes");
			ArrayList<Note> n = (ArrayList<Note>)message.getData();
			for (Note note : n) {
				try {
					database.updateDB(Queries.deleteNote(note.getID()));
					Server.console.writeline("delete" + note.getTitle());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}			
		else if (messageType.equals(MessageType.REQUEST_SEARCH_NOTES)) {
			ArrayList<Note> n = searchForNotes(message);
			ComMessage sendNotes = new ComMessage(n, MessageType.RECEIVE_SEARCH_NOTES);
			clientWriter.send(sendNotes);
		}
	}

	private ArrayList<Note> resultSetToNotes(ResultSet rs){
		ArrayList<Note> notes = new ArrayList<Note>(); 
		try {
			while (rs.next()) {
				String title = rs.getString(Database.COL_TITLE);
				int varselID = rs.getInt(Database.COL_VARSELID);
				Timestamp timesend = rs.getTimestamp(Database.COL_TIMESEND);
				int appointmentID = rs.getInt(Database.COL_APPOINTMENTID);
				boolean hasRead = rs.getBoolean(Database.COL_HASREAD);
				Appointment appointment = resultSetToAppointment(database.executeQuery(Queries.getAppointmentById(appointmentID))).get(0);

				Note n = new Note(varselID, title, new DateString(timesend), appointment, hasRead);
				notes.add(n);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return notes;
	}

	private void meetingsandappointmentsbydate(ComMessage message, ClientWriter cw) {
		Person p = (Person)message.getData();
		int personid = p.getPersonID();

		try{
			ResultSet appointmentResult = database.executeQuery(Queries.getAppointments(personid)); //Get the appointments where the person is a participants	
			ResultSet meetingResult = database.executeQuery(Queries.getMeetings(personid)); //Get the meetings where the person is a participants	

			Collection<Appointment> appointments = resultSetToAppointment(appointmentResult);
			Collection<Meeting> meetings = resultSetToMeeting(meetingResult);


			ComMessage sendapp = new ComMessage(appointments, MessageType.RECEIVE_APPOINTMENTS_BY_DATE_FILTER);
			ComMessage sendmeet = new ComMessage(meetings, MessageType.RECEIVE_MEETINGS_BY_DATE_FILTER);

			cw.send(sendapp);
			cw.send(sendmeet);

		}catch(SQLException e){
			e.printStackTrace();
		}

	}


	private ArrayList<Person> searchForMeetingParticipants(ComMessage message){
		Appointment query = (Appointment) message.getData();
		try{
			ResultSet rs = database.executeQuery(Queries.getParticipantsForMeeting(query.getId()));
			ArrayList<Person> persons = resutlSetToPerson(rs);
			return persons;
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	private ArrayList<Room> getAvaliableRooms(ComMessage message){
		Meeting meeting = (Meeting) message.getData();
		try{
			ResultSet rs = database.executeQuery(Queries.getRoomsForTimeSlot(meeting.getStartTime(), meeting.getEndTime(), meeting.getCapacty()));
			return resultSetToRooms(rs);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
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
			ResultSet rs = database.executeQuery(Queries.getLastAppointment());

			Appointment appo = resultSetToAppointment(rs).get(0);
			ComMessage comMesNewApp = new ComMessage(appo, MessageType.RECEIVE_NEW_APPOINTMENT);
			database.updateDB(Queries.addPersonToAttend(appo.getLeader().getPersonID(), appo.getId()));
			sendToAll(comMesNewApp);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	private void newMeeting(ComMessage message){
		Meeting newMeet = (Meeting) message.getData();
		try{
			database.updateDB(Queries.createNewMeeting(newMeet.getTitle(), newMeet.getDescription(), newMeet.getStartTime(), newMeet.getEndTime(),newMeet.getPlace(), newMeet.getRoom() == null ? null : newMeet.getRoom().getRomId(), newMeet.getLeader().getPersonID()));
			ResultSet rs = database.executeQuery(Queries.getLastMeeting());

			Meeting meeti = resultSetToMeeting(rs).get(0);

			for(Person p : newMeet.getParticipants().keySet()){
				database.updateDB(Queries.addPersonToAttend(p.getPersonID(), meeti.getId()));
			}
			database.updateDB(Queries.addPersonToAttend(meeti.getLeader().getPersonID(), meeti.getId()));
			ComMessage comMesNewMeet = new ComMessage(meeti, MessageType.RECEIVE_NEW_MEETING);

			sendToAll(comMesNewMeet);
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

	private ArrayList<Person> searchForPerson(ComMessage message){
		String query = (String) message.getData();
		try{
			return resutlSetToPerson(database.executeQuery(Queries.getPersonsByFilter(query)));
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}

	private ArrayList<Note> searchForNotes(ComMessage message){
		String query = (String) message.getData();
		try {
			return resultSetToNotes(database.executeQuery(Queries.getNotesByFilter(query)));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private void addAttendant(ComMessage message){
		AppointmentInvites received = (AppointmentInvites) message.getData();
		ArrayList<Person> invited = received.getInvited();
		Appointment appointment = received.getAppointment();
		for(Person p : invited){
			try{
				database.updateDB(Queries.addPersonToAttend(p.getPersonID(), appointment.getId()));
			}
			catch(SQLException e){
				e.printStackTrace();
			}
		}
	}

	private ArrayList<Meeting> resultSetToMeeting(ResultSet result){
		ArrayList<Meeting> returnthis = new ArrayList<Meeting>();
		try{
			while(result.next()){
				int id = result.getInt(Database.COL_APPOINTMENTID);
				String title = result.getString(Database.COL_TITLE);
				String description = result.getString(Database.COL_DESCRIPTION);
				String place = result.getString(Database.COL_PLACE);
				DateString start = new DateString(result.getTimestamp(Database.COL_FROM));
				DateString end = new DateString(result.getTimestamp(Database.COL_TO));
				int external = result.getInt(Database.COL_EXTERNAL);

				ResultSet participantRes = database.executeQuery(Queries.getAnsFromParticipants(id));
				HashMap<Person, Integer> participants = resultSetToPersonWithAnswer(participantRes);

				Person leader = resutlSetToPerson(database.executeQuery(Queries.getLeaderForMeeting(id))).get(0);

				returnthis.add(new Meeting(id, leader, title, description, place, start, end, participants,external));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return returnthis;
	}

	private ArrayList<Room> resultSetToRooms(ResultSet rs){
		ArrayList<Room> returnThis = new ArrayList<Room>();
		try{
			while(rs.next()){
				String room = rs.getString(Database.COL_ROMID);
				int cap = rs.getInt(Database.COL_ROMKAPASITET);
				returnThis.add(new Room(room, cap));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return returnThis;
	}

	private ArrayList<Appointment> resultSetToAppointment(ResultSet result){
		ArrayList<Appointment> returnthis = new ArrayList<Appointment>();
		try{
			while(result.next()){
				int id = result.getInt(Database.COL_APPOINTMENTID);
				String title = result.getString(Database.COL_TITLE);
				String description = result.getString(Database.COL_DESCRIPTION);
				String place = result.getString(Database.COL_PLACE);
				DateString start = new DateString(result.getTimestamp(Database.COL_FROM));
				DateString end = new DateString(result.getTimestamp(Database.COL_TO));

				Person leader = resutlSetToPerson(database.executeQuery(Queries.getLeaderForMeeting(id))).get(0);

				returnthis.add(new Appointment(id, leader, title, description, place, start, end));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return returnthis;
	}

	private HashMap<Person, Integer> resultSetToPersonWithAnswer(ResultSet rs){
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

	private ArrayList<Person> resutlSetToPerson(ResultSet rs){
		ArrayList<Person> returnSet = new ArrayList<Person>();
		try{
			while(rs.next()){
				int id = rs.getInt(Database.COL_PERSONID);
				String fornavn = rs.getString(Database.COL_FORNAVN);
				String etternavn = rs.getString(Database.COL_ETTERNAVN);
				String epost = rs.getString(Database.COL_EPOST);
				String brukernavn = rs.getString(Database.COL_BRUKERNAVN);
				String tlf = rs.getString(Database.COL_TLF);

				returnSet.add(new Person(id, fornavn, etternavn, epost, brukernavn, tlf));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return returnSet;
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
		//		try{
			//			while(rs.next()){
		//				ResultSet appointmentResult = database.executeQuery(Queries.getAppointment(rs.getInt("AVTALE")));
		//				Appointment app = resultSetToAppointment(appointmentResult).get(0);
		//				return new Note(rs.getInt("AVTALEID"), rs.getString("TITTEL"), new DateString(rs.getDate("TIDSENDT")), app);
		//			}
		//		}catch(SQLException e){
		//			e.printStackTrace();
		//		}
		return null;
	}

	public synchronized void addClient(ClientWriter clientWriter){
		clients.put(clientWriter.getIP(), clientWriter);
	}

	public synchronized void sendToAll(ComMessage message){
		for (ClientWriter client : clients.values()) {
			client.send(message);
		}
	}

}
