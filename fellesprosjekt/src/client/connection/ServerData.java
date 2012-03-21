package client.connection;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import client.Client;

import common.dataobjects.Appointment;
import common.dataobjects.ComMessage;
import common.dataobjects.Inbox;
import common.dataobjects.InternalCalendar;
import common.dataobjects.Meeting;
import common.dataobjects.Message;
import common.dataobjects.Person;
import common.dataobjects.Room;


public class ServerData {
	
	private static InternalCalendar calendar;
	private static Inbox inbox;
	
	private static HashMap<Integer, Appointment> appointments;
	private static HashMap<Integer, Meeting> meetings;
	private static HashMap<Integer, Room> rooms;
	private static HashMap<Integer, Person> persons;
	private static HashMap<Integer, Message> messages;
	
	private static Connection connection;
	private static LinkedList<MessageListener> listeners = new LinkedList<MessageListener>();
	
	private static boolean connected = false;
	
	public static boolean isConnected(){
		return connected;
	}
	
	public static void connect() throws IOException {
		connection = new Connection();
		
		connection.connect();
		
		connected = true;
		
		appointments = new HashMap<Integer, Appointment>();
		meetings = new HashMap<Integer, Meeting>();
		rooms = new HashMap<Integer, Room>();
		persons = new HashMap<Integer, Person>();
		messages = new HashMap<Integer, Message>();
		
		calendar = new InternalCalendar();
	}
	
	public static void requestLogin(String user, String password){
		connection.login(user, password);
	}
	public static void requestAppointmentsAndMeetings(Person p){
		connection.requestMeetingsAndAppointments(p);
	}
	
	@SuppressWarnings("unchecked")
	public static synchronized void receiveMessage(ComMessage message){
		String messageType = message.getType();
		
		Client.console.writeline("received message: " + messageType);
		
		if(messageType.equals(MessageType.RECEIVE_APPOINTMENTS)){
			Collection<Appointment> apps = (Collection<Appointment>)message.getData();
			for(Appointment a: apps){
				appointments.put(a.getId(), a);
				persons.put(a.getLeader().getPersonID(), a.getLeader());
			}
			calendar.addAppointments(apps);
		}
		else if(messageType.equals(MessageType.RECEIVE_MEETINGS)){
			Collection<Meeting> meets = (Collection<Meeting>)message.getData();
			for(Meeting m: meets){
				meetings.put(m.getId(), m);
				persons.put(m.getLeader().getPersonID(), m.getLeader());
				for(Person p : m.getParticipants().keySet()){
					persons.put(p.getPersonID(), p);
				}
			}
			calendar.addMeetings(meets);
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
