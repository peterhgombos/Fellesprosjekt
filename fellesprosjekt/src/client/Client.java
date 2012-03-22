package client;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import client.gui.CalendarPanel;
import client.gui.LogIn;

import common.dataobjects.Person;
import common.utilities.Console;

public class Client {
	
	public static final Console console = new Console("Client");
	
	private JFrame frame;
	private static Person user;
	
	private LogIn loginPanel;
	private CalendarPanel calendarpanel;
	
	public Client() {
		frame = new JFrame("Client");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(1200, 750);
		frame.setLocationRelativeTo(null);
		showLogin();
	}
	public void showLogin(){
		if(calendarpanel != null){
			frame.remove(calendarpanel);
		}
		user = null;
		loginPanel = new LogIn(this);
		frame.add(loginPanel);
		frame.setVisible(true);
		frame.repaint();
	}
	public void showCalPan(){
		if(loginPanel != null){
			frame.remove(loginPanel);
		}
		calendarpanel = new CalendarPanel(this);
		frame.add(calendarpanel);
		frame.setVisible(true);
		frame.repaint();
	}
	
	public JFrame getFrame (){
		return frame;
	}
	
	public void loggedIn(Person loggedin){
		user = loggedin;
		showCalPan();
	}
	
	public static Person getUser(){
		return user;
	}
	
	public static void main(String[] args) throws IOException {
		try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch(Exception e){}
		new Client();
	}
}
