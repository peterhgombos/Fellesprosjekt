package gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class LogIn extends JPanel{
	
	private JLabel nameLabel;
	private JLabel passwordLabel;
	
	private JTextField nameField;
	private JTextField passwordField;
	private JButton loginButton;
	
	public LogIn() {
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
		
		setLayout(null);
		resize();
	}

	private void resize() {
		GuiConstants guiConstants = new GuiConstants();
		
		nameLabel.setBounds(guiConstants.getDistance()*15, guiConstants.getDistance()*15, 90, 25);
		nameField.setBounds(nameLabel.getX() + nameLabel.getWidth() + guiConstants.getDistance(), nameLabel.getY(), 300, 40);
		
		passwordLabel.setBounds(nameLabel.getX(), guiConstants.getDistance() + nameField.getY() + nameField.getHeight(), nameLabel.getWidth(), nameLabel.getHeight());
		passwordField.setBounds(nameField.getX(), passwordLabel.getY(), nameField.getWidth(), nameField.getHeight());
		
		loginButton.setBounds(nameField.getX(), guiConstants.getDistance() + passwordField.getY() + passwordField.getHeight(), 160, 35);
		loginButton.setBounds(nameField.getX() + (passwordField.getWidth() - loginButton.getWidth()), guiConstants.getDistance() + passwordField.getY() + passwordField.getHeight(), 160, 35);
		
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("NewMeeting");
		LogIn logIn = new LogIn();
		frame.add(logIn);
		frame.getContentPane();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setSize(700, 500);
		frame.setVisible(true);
	}
}
