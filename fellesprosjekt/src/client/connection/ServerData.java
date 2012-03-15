package client.connection;

import java.io.IOException;
import java.util.HashMap;

import nu.xom.Document;
import nu.xom.Element;
import dataobjects.Appointment;
import dataobjects.Calender;
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
	
	public static void receiveMessage(Document document){
		Element rootElement = document.getRootElement();
		Element typeElement = rootElement.getFirstChildElement(XMLElements.MESSAGE_TYPE);
		int type = Integer.parseInt(typeElement.getValue());
		
		if(type == MessageType.RECEIVE_LOGIN){
			
		}else if(type == MessageType.RECEIVE_APPOINTMENTS){
			
		}else if(type == MessageType.RECEIVE_MEETINGS){
			
		}
	}
	
}
