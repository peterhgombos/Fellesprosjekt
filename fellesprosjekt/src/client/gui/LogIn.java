package client.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import client.Client;
import client.connection.MessageListener;
import common.utilities.MessageType;
import client.connection.ServerData;

import common.dataobjects.ComMessage;
import common.dataobjects.Person;

@SuppressWarnings("serial")
public class LogIn extends JPanel implements MessageListener, ActionListener{
	
	private JLabel nameLabel;
	private JLabel passwordLabel;
	
	private JTextField nameField;
	private JPasswordField passwordField;
	private JButton loginButton;
	
	private Client client;
	
	public LogIn(Client mainclient) {
		this.client = mainclient;
		
		setLayout(null);
		
		nameLabel = new JLabel("Navn");
		passwordLabel = new JLabel("Passord");
		
		nameField = new JTextField();
		passwordField = new JPasswordField();
		
		loginButton = new JButton();
		loginButton.setText("Logg Inn");
		
		add(nameLabel);
		add(passwordLabel);
		add(nameField);
		add(passwordField);
		add(loginButton);
		
		ServerData.addMessageListener(this);
		
		passwordField.addActionListener(this);
		nameField.addActionListener(this);
		loginButton.addActionListener(this);
		
		resize();
	}
	
	private void logIn(){
		ServerData.requestLogin(nameField.getText(), passwordField.getText());
	}

	private void resize() {
		nameLabel.setBounds(GuiConstants.DISTANCE*15, GuiConstants.DISTANCE*15, 90, 25);
		nameLabel.setFont(new Font(nameLabel.getFont().getName(), 0, 16));
		nameField.setBounds(nameLabel.getX() + nameLabel.getWidth() + GuiConstants.DISTANCE, nameLabel.getY(), 300, 40);
		
		passwordLabel.setBounds(nameLabel.getX(), GuiConstants.DISTANCE + nameField.getY() + nameField.getHeight(), nameLabel.getWidth(), nameLabel.getHeight());
		passwordLabel.setFont(new Font(nameLabel.getFont().getName(), 0, 16));
		passwordField.setBounds(nameField.getX(), passwordLabel.getY(), nameField.getWidth(), nameField.getHeight());
		
		loginButton.setBounds(nameField.getX(), GuiConstants.DISTANCE + passwordField.getY() + passwordField.getHeight(), 160, 35);
		loginButton.setBounds(nameField.getX() + (passwordField.getWidth() - loginButton.getWidth()), GuiConstants.DISTANCE + passwordField.getY() + passwordField.getHeight(), 160, 35);
	}

	@Override
	public void receiveMessage(ComMessage m){
		if(m.getType().equals(MessageType.RECEIVE_LOGIN)){
			Person user = (Person)m.getData();
			if(user == null){
				loginButton.setEnabled(true);
				Client.console.writeline("Ugyldig login");
				UserInformationMessages.showErrormessage("Ugyldig brukernavn eller passord");
				return;
			}
			Client.console.writeline("Logget inn som bruker " + user.getUsername());
			ServerData.removeMessageListener(this);
			client.loggedIn(user);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try{
			if(!ServerData.isConnected()){
				ServerData.connect();
			}
		}catch(IOException e1){
			Client.console.writeline("Kunne ikke koble til");
			UserInformationMessages.showErrormessage("Kunne ikke koble til");
			return;
		}
		logIn();
		loginButton.setEnabled(false);
	}
	
}
