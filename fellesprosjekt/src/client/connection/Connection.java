package client.connection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import server.Server;

import common.dataobjects.*;
import common.sendobjects.AnswerUpdates;
import common.sendobjects.AppointmentInvites;
import common.utilities.MessageType;

import client.Client;
import client.authentication.Login;

public class Connection  {

	
	private Socket socket;
	private ServerWriter writer;
	private LinkedList<ConnectionListener> listeners;

	public void connect() throws IOException {
		socket = new Socket();
		socket.connect(new InetSocketAddress("localhost", Server.PORT));
		ServerReader serverReader = new ServerReader(socket, this);
		serverReader.start();
		writer = new ServerWriter(socket);
	}
	
	public void disConnect()  {
		try{
			socket.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void addConnectionListener(ConnectionListener listener) {
		this.listeners.add(listener);
	}
	
	public void removeConnectionListener(ConnectionListener listener) {
		this.removeConnectionListener(listener);
	}
	
	public void requestNewAppointment(Appointment newAppointment){
		writer.send(new ComMessage(newAppointment, MessageType.REQUEST_NEW_APPOINTMENT));
	}
	
	public void requestNewMeeting(Meeting newMeeting){
		writer.send(new ComMessage(newMeeting, MessageType.REQUEST_NEW_MEETING));
		Client.console.writeline("NEW MEETING\nDeltagere: " + newMeeting.getParticipants().keySet().size());
	}
	
	public void requestUpdateAnswers(HashMap<Person, Integer> persons, Appointment appointment){
		AnswerUpdates send = new AnswerUpdates(persons, appointment);
		writer.send(new ComMessage(send, MessageType.REQUEST_UPDATE_ANSWER));
	}
	
	public void requestBookRoom(Appointment app){
		writer.send(new ComMessage(app, MessageType.REQUEST_BOOK_ROOM));
	}
	
	public void requestAvailableRooms(Meeting meeting){
		writer.send(new ComMessage(meeting, MessageType.REQUEST_ROOMS_AVAILABLE));
	}
	
	public synchronized void receiveMessage(ComMessage s) {
		try{
			ServerData.receiveMessage(s);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void login(String username, String password) {
		Login login = new Login(username, password);
		writer.send(new ComMessage(login, MessageType.REQUEST_LOGIN));
	}
	
	public void requestMeetingsAndAppointments(Person p) {
		writer.send(new ComMessage(p, MessageType.REQUEST_APPOINTMENTS_AND_MEETINGS));
	}
	
	public void requestNewNote(Note n){
		writer.send(new ComMessage(n, MessageType.REQUEST_NEW_NOTE));
	}
	
	public void requestSearchForPerson(String search){
		writer.send(new ComMessage(search, MessageType.REQUEST_SEARCH_PERSON));
	}
	
	public void requestMessages(Person p) {
		//TODO
	}
	
	public void requestAddPersons(ArrayList<Person> invitees, Appointment appointment){
		AppointmentInvites appInvites = new AppointmentInvites(invitees, appointment);
		writer.send(new ComMessage(appInvites, MessageType.REQUEST_ADD_ATTENDANT));
	}
	
	public void requestGetParticipnts(Appointment app){
		writer.send(new ComMessage(app, MessageType.REQUEST_PARTICIPANTS));		
	}

	public void requestAppointmentsAndMeetingsByDateFilter(Person person) {
		writer.send(new ComMessage(person, MessageType.REQUEST_MEETINGS_AND_APPOINTMENTS_BY_DATE_FILTER));
	}

	public void requestNotes(Person person, String filter) {
		ComMessage comm = new ComMessage(person, MessageType.REQUEST_NOTES);
		comm.setProperty("filter", filter);
		writer.send(comm);
		
	}

	public void delteNotes(ArrayList<Note> notes) {
		writer.send(new ComMessage(notes, MessageType.DELETE_NOTE));
	}

	public void requestSearchForNotes(String search) {
		writer.send(new ComMessage(search, MessageType.REQUEST_SEARCH_NOTES));
		
	}
	public void requestUpdateAppointment(Appointment app){
		writer.send(new ComMessage(app, MessageType.REQUEST_UPDATE_APPOINTMENT));
	}
}
