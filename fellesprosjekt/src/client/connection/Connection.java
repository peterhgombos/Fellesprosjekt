package client.connection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.LinkedList;

import nu.xom.ParsingException;
import nu.xom.ValidityException;

import utilities.Console;
import dataobjects.*;

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
	   	nu.xom.Builder parser = new nu.xom.Builder(false);
	   	//TODO create doc properly
    	nu.xom.Document doc;
		try {
			doc = parser.build(s, "");
		} catch (ValidityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParsingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ServerData.receiveMessage(doc);

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
