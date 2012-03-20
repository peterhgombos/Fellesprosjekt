package gui;

import java.awt.Font;
import java.nio.channels.AcceptPendingException;

import javax.swing.*;

public class AppointmentView extends JPanel{
	
	//Variabler som får verdier fra databasen
	private String appointmentName;
	
	private JLabel headline;
	private JLabel dateLabel;
	private JLabel descriptionLabel;
	private JLabel placeLabel;
	private JLabel participantsLabel;
	private JLabel meetingLeaderLabel;
	private JLabel participateLabel;
	private JLabel notParticipateLabel;
	private JLabel notAnsweredLabel;
	
	private JButton accpectButton; 		//Bare deltaker
	private JButton rejectButton;		//Bare deltaker
	private JButton toCalendarButton;	//Deltaker og leder
	private JButton editButton;			//Bare leder
	private JButton cancelButton;		//Bare leder
	
	
	public AppointmentView() {
		
		//TESTER
		appointmentName = "TEST";
		
		
		headline = new JLabel(appointmentName);
		dateLabel = new JLabel("Dato");
		descriptionLabel = new JLabel("Beskrivelse");
		placeLabel = new JLabel("Sted");
		participantsLabel = new JLabel("Deltakere");
		meetingLeaderLabel = new JLabel("Møteleder");
		participateLabel = new JLabel("Deltar");
		notParticipateLabel = new JLabel("Deltar Ikke");
		notAnsweredLabel = new JLabel("Ikke Svart");
		
		accpectButton = new JButton("Godta");
		rejectButton = new JButton("Avslå");
		toCalendarButton = new JButton("Til Kalender");
		editButton = new JButton("Rediger");
		cancelButton = new JButton("Avbryt");
		
		add(headline);
		add(dateLabel);
		add(descriptionLabel);
		add(placeLabel);
		add(participantsLabel);
		add(meetingLeaderLabel);
		add(participateLabel);
		add(notAnsweredLabel);
		add(notParticipateLabel);
		add(accpectButton);
		add(rejectButton);
		add(toCalendarButton);
		add(editButton);
		add(cancelButton);
		
		setLayout(null);
		resize();
		
	}
	
	public void resize(){
		
		GuiConstants guiConstants = new GuiConstants();
		
		headline.setBounds(guiConstants.getDistance()*30, guiConstants.getDistance(), 400, 40);
		headline.setFont(new Font(headline.getFont().getName(),0,30));
		
		dateLabel.setBounds(guiConstants.getDistance()*5, headline.getHeight() + guiConstants.getDistance()*5, 100, 25);
		dateLabel.setFont(new Font(dateLabel.getFont().getName(),0,15));
		
		
		
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Avtale");
		AppointmentView appointmentView = new AppointmentView();
		frame.add(appointmentView);
		frame.getContentPane();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setSize(700, 700);
		frame.setVisible(true);
	}
}
