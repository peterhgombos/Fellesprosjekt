package client.connection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import client.gui.calendar.ColorPicker;

import common.dataobjects.Appointment;
import common.dataobjects.ComMessage;
import common.dataobjects.InternalCalendar;
import common.dataobjects.Meeting;
import common.dataobjects.Note;
import common.dataobjects.Person;
import common.dataobjects.Room;
import common.utilities.DateString;
import common.utilities.MessageType;

public class ServerData {
	
	private static InternalCalendar calendar;
	
	private static Connection connection;
	private static LinkedList<MessageListener> listeners = new LinkedList<MessageListener>();
	
	private static boolean connected = false;
	
	public static boolean isConnected(){
		return connected;
	}
	
	public static InternalCalendar getCalendar(){
		return calendar;
	}
	
	public static void connect() throws IOException {
		connection = new Connection();
		
		connection.connect();
		
		connected = true;
		
		calendar = new InternalCalendar();
	}
	
	
	public static void resetMeetingsAndAppointments(){
		calendar = new InternalCalendar();
	}
	
	public static void disconnect() {
		connection.disconnect();
		connected = false;
		calendar = null;
	}
	
	public static void requestLogin(String user, String password){
		connection.login(user, password);
	}
	public static void requestAppointmentsAndMeetings(Person p){
		connection.requestMeetingsAndAppointments(p);
	}
	
	public static void requestBookRoom(Meeting a){
		connection.requestBookRoom(a);
	}
	
	public static void requestAvailableRooms(Meeting meeting){
		connection.requestAvailableRooms(meeting);
	}
	
	public static void requestNewAppointment(Appointment a){
		connection.requestNewAppointment(a);
	}
	
	public static void requestNewMeeting(Meeting m){
		connection.requestNewMeeting(m);
	}
	
	public static void requestNewNote(Note n){
		connection.requestNewNote(n);
	}
	
	public static void requestSearchForPerson(String search){
		connection.requestSearchForPerson(search);
	}
	
	public static void requestSearchForNotes(String search){
		connection.requestSearchForNotes(search);
	}
	
	public static void requestAddAttendant(ArrayList<Person> invitees, Appointment appointment){
		connection.requestAddPersons(invitees, appointment);
	}
	
	public static void requestGetParticipants(Appointment app){
		connection.requestGetParticipnts(app);
	}
	
	public static void requestUpdateAnswers(HashMap<Person, Integer> persons, Appointment app){
		connection.requestUpdateAnswers(persons, app);
	}
	
	public static void requestAppointmensAndMeetingByDateFilter(Person person, DateString start, DateString end){
		connection.requestAppointmentsAndMeetingsByDateFilter(person, start, end);
	}
	
	
	
	@SuppressWarnings("unchecked")
	public static synchronized void receiveMessage(ComMessage message){
		
		String messageType = message.getType();
		
		if(messageType.equals(MessageType.RECEIVE_APPOINTMENTS)){
			Collection<Appointment> apps = (Collection<Appointment>)message.getData();
			calendar.addAppointments(apps);
		}
		else if(messageType.equals(MessageType.RECEIVE_MEETINGS)){
			Collection<Meeting> meets = (Collection<Meeting>)message.getData();
			calendar.addMeetings(meets);
		}
		else if(messageType.equals(MessageType.RECEIVE_NEW_APPOINTMENT)){
			Appointment app = (Appointment)message.getData();
			calendar.addAppointment(app);
		}
		else if(messageType.equals(MessageType.RECEIVE_NEW_MEETING)){
			Meeting app = (Meeting)message.getData();
			calendar.addMeeting(app);
		}
		Collection<MessageListener> clone = (Collection<MessageListener>)listeners.clone();
		for (MessageListener l : clone) {
			l.receiveMessage(message);
		}
	}
	
	public static void addMessageListener(MessageListener listener) {
		ServerData.listeners.add(listener);
	}
	public static void removeMessageListener(MessageListener listener) {
		ServerData.listeners.remove(listener);
	}
	
	public static void requestNotes(Person person, String filter){
		connection.requestNotes(person, filter);
	}
	
	public static void delteNotes(ArrayList<Note> notes){
		connection.delteNotes(notes);
	}
	
	public static void requestUpdateAppointment(Appointment app){
		connection.requestUpdateAppointment(app);
	}
	
	public static void requestUpdateMeeting(Meeting meet) {
		connection.requestUpdateMeeting(meet);
	}

	public static void deleteAppointment(Appointment app) {
		connection.deleteAppointment(app);
		
	}
	
	public static void getNewOldNotes(Person person){
		connection.requestOldNewNotes(person);
	}
	
	public static void markNoteAsRead(Person person, Note note) {
		connection.updateNoteAsReadForPerson(person, note);
	}
	
}
