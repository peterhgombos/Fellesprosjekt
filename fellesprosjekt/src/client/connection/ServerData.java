package client.connection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import client.Client;

import common.dataobjects.Appointment;
import common.dataobjects.ComMessage;
import common.dataobjects.Inbox;
import common.dataobjects.InternalCalendar;
import common.dataobjects.Meeting;
import common.dataobjects.Note;
import common.dataobjects.Person;
import common.dataobjects.Room;
import common.utilities.MessageType;


public class ServerData {
	
	private static InternalCalendar calendar;
	private static Inbox inbox;
	
	private static HashMap<Integer, Appointment> appointments;
	private static HashMap<Integer, Meeting> meetings;
	private static HashMap<Integer, Room> rooms;
	private static HashMap<Integer, Person> persons;
	private static HashMap<Integer, Note> messages;
	
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
		
		appointments = new HashMap<Integer, Appointment>();
		meetings = new HashMap<Integer, Meeting>();
		rooms = new HashMap<Integer, Room>();
		persons = new HashMap<Integer, Person>();
		messages = new HashMap<Integer, Note>();
		
		calendar = new InternalCalendar();
	}
	
	public static void requestLogin(String user, String password){
		connection.login(user, password);
	}
	public static void requestAppointmentsAndMeetings(Person p){
		connection.requestMeetingsAndAppointments(p);
	}
	
	public static void requestNewAppointment(Appointment a){
		connection.requestNewAppointment(a);
	}
	
	public static void requestNewNote(Note n){
		connection.requestNewNote(n);
	}
	
	public static void requestSearchForPerson(String search){
		connection.requestSearchForPerson(search);
	}
	
	public static void requestAddAttendant(ArrayList<Person> invitees, Appointment appointment){
		connection.requestAddPersons(invitees, appointment);
	}
	
	public static void requestGetParticipants(Appointment app){
		connection.requestGetParticipnts(app);
	}
	
	@SuppressWarnings("unchecked")
	public static synchronized void receiveMessage(ComMessage message){
		String messageType = message.getType();
		
		Client.console.writeline("received message: " + messageType);
		
		if(messageType.equals(MessageType.RECEIVE_APPOINTMENTS)){
			System.out.println("lkjlkjlkjlkjlkjlkjlkjlkjlkjlkjlkjlkjlkjlkjlkjlkjlkjlkjlkjlkjlkj");
			Collection<Appointment> apps = (Collection<Appointment>)message.getData();
			for(Appointment a: apps){
				System.out.println("MESSAGERECEIVER APPS: " + a.getTitle());
				appointments.put(a.getId(), a);
				persons.put(a.getLeader().getPersonID(), a.getLeader());
			}
			System.out.println("" + apps.size());
			calendar.addAppointments(apps);
		}
		else if(messageType.equals(MessageType.RECEIVE_MEETINGS)){
			Collection<Meeting> meets = (Collection<Meeting>)message.getData();
			for(Meeting m: meets){
				System.out.println("MESSAGERECEIVER MØTE" + m.getTitle());
				meetings.put(m.getId(), m);
				persons.put(m.getLeader().getPersonID(), m.getLeader());
				for(Person p : m.getParticipants().keySet()){
					persons.put(p.getPersonID(), p);
				}
			}
			calendar.addMeetings(meets);
		}
		else if(messageType.equals(MessageType.RECEIVE_NEW_APPOINTMENT)){
			Appointment app = (Appointment)message.getData();
			appointments.put(app.getId(), app);
			persons.put(app.getLeader().getPersonID(), app.getLeader());
			calendar.addAppointment(app);
		}
		else if(messageType.equals(MessageType.RECEIVE_NEW_NOTE)){
			Note note = (Note)message.getData();
			//TODO what to do with this note?
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
	
	
}
