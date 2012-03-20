package client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.annotation.processing.Messager;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import common.dataobjects.ComMessage;
import common.dataobjects.Person;

import client.Client;
import client.connection.MessageListener;
import client.connection.MessageType;
import client.connection.ServerData;

@SuppressWarnings("serial")
public class LogIn extends JPanel implements MessageListener {
	
	private JLabel nameLabel;
	private JLabel passwordLabel;
	
	private JTextField nameField;
	private JTextField passwordField;
	private JButton loginButton;
	
	private Client client;
	
	public LogIn(Client mainclient) {
		this.client = mainclient;
		
		setLayout(null);
		
		nameLabel = new JLabel("Navn");
		passwordLabel = new JLabel("Passord");
		
		nameField = new JTextField();
		passwordField = new JTextField();
		
		loginButton = new JButton();
		loginButton.setText("Logg Inn");
		
		add(nameLabel);
		add(passwordLabel);
		add(nameField);
		add(passwordField);
		add(loginButton);
		
		
		ServerData.addMessageListener(this);
		
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				try{
					if(!ServerData.isConnected()){
						ServerData.connect();
					}
				}catch(IOException e1){
					Client.console.writeline("kunne ikke koble til");
					//TODO feilmelding kunne ikke koble til
					return;
				}
				logIn();
				loginButton.setEnabled(false);
			}
		});
		
		resize();
	}
	
	private void logIn(){
		ServerData.requestLogin(nameField.getText(), passwordField.getText());
	}

	private void resize() {
		nameLabel.setBounds(GuiConstants.DISTANCE*15, GuiConstants.DISTANCE*15, 90, 25);
		nameField.setBounds(nameLabel.getX() + nameLabel.getWidth() + GuiConstants.DISTANCE, nameLabel.getY(), 300, 40);
		
		passwordLabel.setBounds(nameLabel.getX(), GuiConstants.DISTANCE + nameField.getY() + nameField.getHeight(), nameLabel.getWidth(), nameLabel.getHeight());
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
				Client.console.writeline("ugyldig login");
				//TODO feilmelding ugyldig login
				return;
			}
			Client.console.writeline("logget inn som bruker " + user.getUsername());
			ServerData.removeMessageListener(this);
			client.setUser(user);
		}
	}
	
}
