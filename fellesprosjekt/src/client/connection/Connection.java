package client.connection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.LinkedList;

import nu.xom.Builder;
import nu.xom.Document;
import utilities.Console;
import dataobjects.Appointment;
import dataobjects.Meeting;
import dataobjects.Person;

public class Connection  {

	private static Socket socket;
	public static Console console;
	public static ServerWriter writer;
	LinkedList<ConnectionListener> listeners;

	public static void connect() throws IOException {
		console = new Console();
		socket = new Socket();
		socket.connect(new InetSocketAddress("localhost", server.ServerConstants.PORT));
		ServerReader serverReader = new ServerReader(socket);
		serverReader.start();
		writer = new ServerWriter(socket);
		writer.send("melding");
	}
	
	public void addConnectionListener(ConnectionListener listener) {
		this.listeners.add(listener);
	}
	
	public void removeConnectionListener(ConnectionListener listener) {
		this.removeConnectionListener(listener);
	}
	
	
	public static void receiveMessage(String s) {
		try{
			Builder parser = new Builder(false);
			Document doc = parser.build(s);
			ServerData.receiveMessage(doc);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void requestPersonLoggedIn(String username) {
		//TODO
	}
	
	public void login(String username, String password) {
		//TODO
	}
	
	public void requestAppointments(Person p) {
		//TODO
	}
	
	public void requestMeetings(Person p) {
		//TODO
	}
	
	public void requestLeader(Appointment a) {
		//TODO
	}
	
	public void requestParticipants(Meeting m) {
		//TODO
	}
	
	public void requestMessages(Person p) {
		//TODO
	}
	
	public void requestRoom(Meeting m) {
		//TODO
	}
	
	public static void main(String[] args) throws IOException {
		Connection.connect();
	}

}
