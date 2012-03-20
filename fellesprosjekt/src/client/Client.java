package client;

import java.io.IOException;

import client.connection.MessageListener;
import client.connection.MessageType;
import client.connection.ServerData;

import common.dataobjects.ComMessage;
import common.dataobjects.Person;
import common.utilities.Console;


public class Client {
	
	public static Console console = new Console("Client");
	
	public static void main(String[] args) throws IOException {
		new Client();
	}
	
	public Client() {
		ServerData.initialize();
		ServerData.addMessageListener(new TestListener());
		Person p = new Person(123, "", "", "", "", "");
		
		ServerData.requestLogin("martedl", "ntnu");
		
	}

	private class TestListener implements MessageListener {
		public void receiveMessage(ComMessage m) {
			console.writeline("recmess");
			if (m.getType().equals(MessageType.RECEIVE_LOGIN)) {
				console.writeline(""+m.getData());
			}
		}
		
	}
	

}
