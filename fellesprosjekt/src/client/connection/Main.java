package client.connection;

import java.io.IOException;

import dataobjects.ComMessage;
import dataobjects.InternalCalendar;
import dataobjects.Person;

public class Main {
	
	public static void main(String[] args) throws IOException {
		new Main();
	}
	
	public Main() {
		ServerData.initialise();
		ServerData.addMessageListener(new TestListener());
		ServerData.connection.requestMeetingsAndAppointments(new Person(123, "", "", "", "", ""));
	}

	private class TestListener implements MessageListener {
		public void receiveMessage(ComMessage m) {
			ServerData.calendar = new InternalCalendar(ServerData.appointments.values(), ServerData.meetings.values());
			System.out.println();
		}
		
	}
	

}
