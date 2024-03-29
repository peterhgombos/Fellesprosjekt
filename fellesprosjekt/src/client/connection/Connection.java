package client.connection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

import server.Server;
import client.authentication.Login;

import common.dataobjects.Appointment;
import common.dataobjects.ComMessage;
import common.dataobjects.Meeting;
import common.dataobjects.Note;
import common.dataobjects.Person;
import common.sendobjects.AppointmentInvites;
import common.utilities.DateString;
import common.utilities.MessageType;

public class Connection  {

	
	private Socket socket;
	private ServerWriter writer;
	private LinkedList<ConnectionListener> listeners;
	private BufferedReader reader;
	private FileReader filereader;

	public void connect() throws IOException {
		filereader = new FileReader("res/adress");
		reader = new BufferedReader(filereader);
		String adress = reader.readLine();
		reader.close();
		filereader.close();
		socket = new Socket();
		socket.connect(new InetSocketAddress(adress, Server.PORT));
		ServerReader serverReader = new ServerReader(socket, this);
		serverReader.start();
		writer = new ServerWriter(socket);
	}
	
	public void disconnect()  {
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
	}
	
	public void requestUpdateAnswers(int pid, int appid, int svar){
		System.out.println("client:" + pid + " " + appid + " " + svar);
		writer.send(new ComMessage(pid + ":" + svar + ":" + appid, MessageType.REQUEST_UPDATE_ANSWER));
		//writer.send(new ComMessage(appointment, MessageType.REQUEST_UPDATE_ANSWER));
	}
	
	public void requestBookRoom(Meeting app){
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

	public void requestAppointmentsAndMeetingsByDateFilter(Person person, DateString start, DateString end) {
		ComMessage cm = new ComMessage(person, MessageType.REQUEST_MEETINGS_AND_APPOINTMENTS_BY_DATE_FILTER);
		cm.setProperty("dstart", start.toString());
		cm.setProperty("dend", end.toString());
		writer.send(cm);
	}

	public void requestNotes(Person person, String filter) {
		ComMessage comm = new ComMessage(person, MessageType.REQUEST_NOTES);
		comm.setProperty("filter", filter);
		writer.send(comm);
		
	}

	public void delteNotes(ArrayList<Note> notes) {
		writer.send(new ComMessage(notes, MessageType.DELETE_NOTE));
	}

	public void requestUpdateAppointment(Appointment app){
		writer.send(new ComMessage(app, MessageType.REQUEST_UPDATE_APPOINTMENT));
	}
	public void requestUpdateMeeting(Meeting meet) {
		writer.send(new ComMessage(meet, MessageType.REQUEST_UPDATE_MEETING));
	}

	public void deleteAppointment(Appointment app) {
		writer.send(new ComMessage(app, MessageType.DELETE_APPOINTMENT));
	}
	
	public void requestOldNewNotes(Person person){
		writer.send(new ComMessage(person, MessageType.GET_OLD_NEW_NOTES));
	}
	
	public void updateNoteAsReadForPerson(Person p, Note n) {
		n.setPersonId(p.getId());
		writer.send(new ComMessage(n, MessageType.UPDATE_NOTE_AS_READ));
	}
	
	public void deleteParticipants(ArrayList<Person> a, Meeting m) {
		ComMessage message = new ComMessage(a, MessageType.DELETE_PARTICIPANTS);
		message.setProperty("id", ""+m.getId());
		writer.send(message);
	}
}
