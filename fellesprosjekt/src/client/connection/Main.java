package client.connection;

import java.io.IOException;

import utilities.Console;
import dataobjects.ComMessage;
import dataobjects.Person;

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
