package client.connection;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import dataobjects.Appointment;
import dataobjects.Calender;
import dataobjects.ComMessage;
import dataobjects.Inbox;
import dataobjects.Meeting;
import dataobjects.Message;
import dataobjects.Person;
import dataobjects.Room;

public class ServerData {
	
	private static Calender calender;
	private static Inbox inbox;
	
	private static HashMap<Integer, Appointment> appointments;
	private static HashMap<Integer, Meeting> meetings;
	private static HashMap<Integer, Room> rooms;
	private static HashMap<Integer, Person> persons;
	private static HashMap<Integer, Message> messages;
	
	private static Connection connection;
	
	public static void main(String[] args) throws IOException {
		initialise();
		
		connection.requestMeetingsAndAppointments(new Person(123, "", "", "", "", ""));
	}
	
	public static void initialise() {
		connection = new Connection();
		try{
			connection.connect();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		appointments = new HashMap<Integer, Appointment>();
		meetings = new HashMap<Integer, Meeting>();
		rooms = new HashMap<Integer, Room>();
		persons = new HashMap<Integer, Person>();
		messages = new HashMap<Integer, Message>();
	}
	
	@SuppressWarnings("unchecked")
	public static void receiveMessage(ComMessage message){
		String messageType = message.getType();
		
		if(messageType.equals(MessageType.RECEIVE_APPOINTMENTS)){
			Collection<Appointment> apps = (Collection<Appointment>)message.getData();
			for(Appointment a: apps){
				appointments.put(a.getId(), a);
				persons.put(a.getLeader().getPersonID(), a.getLeader());
			}
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
		}
	}
}
