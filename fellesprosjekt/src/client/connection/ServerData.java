package client.connection;

import java.util.ArrayList;

import dataobjects.*;

public class ServerData {
	
	private static ArrayList<Appointment> appointments;
	private static Calender calender;
	private static Inbox inbox;
	private static ArrayList<Meeting> meetings;
	private static ArrayList<Room> rooms;
	private static ArrayList<Person> persons;
	private static ArrayList<Message> messages;
	
	public static void initialise() {
		appointments = new ArrayList<Appointment>();
		meetings = new ArrayList<Meeting>();
		rooms = new ArrayList<Room>();
		persons = new ArrayList<Person>();
		messages = new ArrayList<Message>();
		
	}
}
