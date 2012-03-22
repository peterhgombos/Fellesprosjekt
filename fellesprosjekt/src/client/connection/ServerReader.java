package client.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Collection;

import common.dataobjects.Appointment;
import common.dataobjects.ComMessage;
import common.utilities.MessageType;


public class ServerReader extends Thread {
	
	private Socket socket;
	private ObjectInputStream reader;
	private Connection connection;
	
	public ServerReader(Socket socket, Connection connection) {
		this.socket = socket;
		this.connection = connection;
		try {
			reader = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			//Connection.console.writeline(e.getMessage());
		}
	}
	
	private void disconnect(){
		//Connection.console.writeline("Client has disconnected");
		try {
			reader.close();
			socket.close();
		} catch (IOException e) {
			//ingenting, hvis det skjer noe her f�r det bare v�re, de er allerede frakoblet
			//egentlig kan man ikke annta dette, det kan skje skumle feil men det kan bli komplisert
		}
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				ComMessage message = (ComMessage)reader.readObject();
				if (message == null) {
					disconnect();
					return;
				}
				
				if(message.getType().equals(MessageType.RECEIVE_APPOINTMENTS)){
					for(Appointment a : (Collection<Appointment>)(message .getData())){
						System.out.println("RAWWWWWWWWWWW: " + a.getTitle());
					}
				}
				
				connection.receiveMessage(message);
			}catch (SocketException e) {
				e.printStackTrace();
				disconnect();
				return;
			}catch (IOException e) {
				e.printStackTrace();
				disconnect();
				return;
			}catch(ClassNotFoundException e){
				e.printStackTrace();
				disconnect();
				return;
			}
		}
	}
}
