package client.connection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.LinkedList;

import client.authentication.Login;

import utilities.Console;
import dataobjects.Appointment;
import dataobjects.ComMessage;
import dataobjects.Meeting;
import dataobjects.Person;

public class Connection  {

	public static Console console;
	
	private Socket socket;
	private ServerWriter writer;
	private LinkedList<ConnectionListener> listeners;

	public void connect() throws IOException {
		console = new Console();
		socket = new Socket();
		socket.connect(new InetSocketAddress("localhost", server.ServerConstants.PORT));
		ServerReader serverReader = new ServerReader(socket, this);
		serverReader.start();
		writer = new ServerWriter(socket);
	}
	
	public void addConnectionListener(ConnectionListener listener) {
		this.listeners.add(listener);
	}
	
	public void removeConnectionListener(ConnectionListener listener) {
		this.removeConnectionListener(listener);
	}
	
	
	public synchronized void receiveMessage(ComMessage s) {
		try{
			ServerData.receiveMessage(s);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	public void requestPersonLoggedIn(String username) {
		//TODO
	}
	
	public void login(String username, String password) {
		Login login = new Login(username, password);
		writer.send(new ComMessage(login, MessageType.REQUEST_LOGIN));
	}
	
	
	public void requestMeetingsAndAppointments(Person p) {
		System.out.println(p);
		writer.send(new ComMessage(p, MessageType.REQUEST_APPOINTMENTS_AND_MEETINGS));
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
	

}
