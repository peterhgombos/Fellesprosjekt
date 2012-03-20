package client;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import client.gui.LogIn;

import common.dataobjects.Person;
import common.utilities.Console;

public class Client {
	
	public static final Console console = new Console("Client");
	
	private JFrame frame;
	private Person user;
	
	public Client() {
		frame = new JFrame("Client");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(1000, 700);
		
		frame.add(new LogIn(this));
		frame.setVisible(true);
	}
	
	public void setUser(Person user){
		this.user = user;
	}
	
	public Person getUser(){
		return user;
	}
	
	public static void main(String[] args) throws IOException {
		try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch(Exception e){}
		new Client();
	}
}
