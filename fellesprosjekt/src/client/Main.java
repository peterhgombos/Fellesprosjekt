package client;

import java.io.IOException;

import client.connection.MessageListener;
import client.connection.MessageType;
import client.connection.ServerData;

import common.dataobjects.ComMessage;
import common.dataobjects.Person;
import common.utilities.Console;


public class Main {
	
	public static Console c ;
	
	public static void main(String[] args) throws IOException {
		new Main();
	}
	
	public Main() {
		c = new Console();
		c.setTitle("Client");
		ServerData.initialise();
		ServerData.addMessageListener(new TestListener());
		Person p = new Person(123, "", "", "", "", "");
		
		ServerData.requestLogin("martedl", "ntnu");
		
		
	}

	private class TestListener implements MessageListener {
		public void receiveMessage(ComMessage m) {
			System.out.println();
			c.writeline("recmess");
			if (m.getType().equals(MessageType.RECEIVE_LOGIN)) {
				c.writeline(""+m.getData());
			}
		}
		
	}
	

}
