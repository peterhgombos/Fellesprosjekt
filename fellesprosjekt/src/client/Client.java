package client;

import java.io.IOException;
import java.util.LinkedHashMap;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import client.connection.ServerData;
import client.gui.CalendarPanel;
import client.gui.LogIn;

import common.dataobjects.Person;
import common.utilities.Console;

public class Client {
	
	public static final Console console = new Console("Client");
	
	private JFrame frame;
	private Person user;
	
	private LogIn loginPanel;
	private CalendarPanel calendarpanel;
	
	public Client() {
		frame = new JFrame("Client");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(1096, 739);
		frame.setLocationRelativeTo(null);
		
		loginPanel = new LogIn(this);
		
		calendarpanel = new CalendarPanel(this);
		
		frame.add(loginPanel);
		frame.setVisible(true);
	}
	
	public JFrame getFrame (){
		return frame;
	}
	
	public void loggedIn(Person user){
		this.user = user;
		
		ServerData.requestAppointmentsAndMeetings(user);
		
		frame.remove(loginPanel);
		frame.add(calendarpanel);
		frame.repaint();
		frame.setVisible(true);
		
	}
	
	public Person getUser(){
		return user;
	}
	
	public static void main(String[] args) throws IOException {
		try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch(Exception e){}
		new Client();
	}
}
