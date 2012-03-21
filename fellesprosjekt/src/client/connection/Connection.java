package client.connection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.LinkedList;

import server.Server;

import common.dataobjects.*;
import common.utilities.MessageType;

import client.authentication.Login;

public class Connection  {

	
	private Socket socket;
	private ServerWriter writer;
	private LinkedList<ConnectionListener> listeners;

	public void connect() throws IOException {
		socket = new Socket();
		socket.connect(new InetSocketAddress("localhost", Server.PORT));
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
	
	public void requestNewAppointment(Appointment newAppointment){
		writer.send(new ComMessage(newAppointment, MessageType.REQUEST_NEW_APPOINTMENT));
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
		System.out.println(p);
		writer.send(new ComMessage(p, MessageType.REQUEST_APPOINTMENTS_AND_MEETINGS));
	}
	
	public void requestNewNote(Note n){
		writer.send(new ComMessage(n, MessageType.REQUEST_NEW_NOTE));
	}
	
	public void requestMessages(Person p) {
		//TODO
	}
	
	

}
