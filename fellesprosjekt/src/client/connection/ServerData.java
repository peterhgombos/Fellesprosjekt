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
	
	static HashMap<Integer, Appointment> appointments;
	static HashMap<Integer, Meeting> meetings;
	private static HashMap<Integer, Room> rooms;
	private static HashMap<Integer, Person> persons;
	private static HashMap<Integer, Message> messages;
	
	static Connection connection;
	static LinkedList<MessageListener> listeners;
	
	
	public static void initialize() {
		connection = new Connection();
		try{
			connection.connect();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		listeners = new LinkedList<MessageListener>();
		appointments = new HashMap<Integer, Appointment>();
		meetings = new HashMap<Integer, Meeting>();
		rooms = new HashMap<Integer, Room>();
		persons = new HashMap<Integer, Person>();
		messages = new HashMap<Integer, Message>();
		
		calendar = new InternalCalendar();
	}
	
	public static void requestLogin(String user, String password){
		connection.login("martedl", "ntnu");
	}
	public static void requestAppointmentsAndMeetings(Person p){
		connection.requestMeetingsAndAppointments(p);
	}
	
	
	@SuppressWarnings("unchecked")
	public static void receiveMessage(ComMessage message){
		String messageType = message.getType();
		
		Client.console.writeline(messageType);
		
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
		
		
		for (MessageListener l : listeners) {
			l.receiveMessage(message);
		}
	}
	
	public static void addMessageListener(MessageListener listener) {
		ServerData.listeners.add(listener);
	}
	
	
}
