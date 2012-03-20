package client.gui;

import java.awt.Font;
import java.nio.channels.AcceptPendingException;
import java.sql.Date;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

public class AppointmentView extends JPanel{
	
	//Variabler som får verdier fra databasen
	private String appointmentName;
	private String description;
	private boolean isLeader;
	private String date;
	private String time;
	private String place;
	//
	
	private JLabel headline;
	private JLabel dateLabel;
	private JLabel timeLabel;
	private JLabel descriptionLabel;
	private JLabel placeLabel;
	private JLabel participantsLabel;
	private JLabel meetingLeaderLabel;
	private JLabel participateLabel;
	private JLabel notParticipateLabel;
	private JLabel notAnsweredLabel;
	
	private JLabel dateInput;
	private JLabel timeInput;
	private JLabel placeInput;
	private JTextArea descriptionInput;
	private JScrollPane textAreaScrollPane;
	
	private JButton accpectButton; 		//Bare deltaker
	private JButton rejectButton;		//Bare deltaker
	private JButton toCalendarButton;	//Deltaker og leder
	private JButton editButton;			//Bare leder
	private JButton cancelButton;		//Bare leder
	
	
	public AppointmentView() {
		
		//TESTER
		appointmentName = "TEST";
		isLeader = false;
		description = "Dette er en laaaaaaaaaaaaang beskrivelse til TEST!! <3 dette kommme efi fsei FJ ISE HFUDF DSF H SFH SFSHFIS HFISF HSIF SIFH SIF HSIGHSI H SI FHSIF HSIF HSIGH SIGH SIG HSIDHG SIGH SIDHG SIHDGISHG SIGHSIDGH SIDGH SDIGHSDIHG SIF GSIDGH SIDH GSIDHG SIDH GIDSGH ISDHG ISDG HSIDHG ISD HGSIDHG ISDH GISH NSIDG HSIG SIDG HSIGH SIG HSIGHSIHG SIDG HSIDG SD GSHDI GSIDHG ISDHG ISDHG ISDH GISH GISHG ISDGH IDSGH ISG HSIDG SIDGH IDS HGSID HGSID HGDSIHG SIGH SID HGISD HGISDHFGIDSHGISDUUUUUUU D  D D  D D D D D D D  DD FJIHSDHF I SDHIFUSDHFIUDH GUID FXICG DFGI FCIGDXIGUSTDGICFHGOD HGUDYFGOSHDGOUDSAZHFUIASGHISDFHGOUDFASHGIUAFD HGAFDHGOADSHGOASHGADSH FGIUASEHF USOe fausd g";
		date = "30.03.2012";
		time = "12:00-13:00";
		place = "NTNU";
		//TESTER
		
		headline = new JLabel(appointmentName);
		timeLabel = new JLabel("Tid:");
		dateLabel = new JLabel("Dato:");
		descriptionLabel = new JLabel("Beskrivelse:");
		placeLabel = new JLabel("Sted:");
		participantsLabel = new JLabel("Deltakere:");
		meetingLeaderLabel = new JLabel("Møteleder:");
		participateLabel = new JLabel("Deltar:");
		notParticipateLabel = new JLabel("Deltar Ikke:");
		notAnsweredLabel = new JLabel("Ikke Svart:");
		
		dateInput = new JLabel(date);
		timeInput = new JLabel(time);
		placeInput = new JLabel(place);
		descriptionInput = new JTextArea(description);
		textAreaScrollPane = new JScrollPane(descriptionInput);
		
		accpectButton = new JButton("Godta");
		rejectButton = new JButton("Avslå");
		toCalendarButton = new JButton("Til Kalender");
		editButton = new JButton("Rediger");
		cancelButton = new JButton("Avlys");
		
		add(headline);
		add(dateLabel);
		add(timeLabel);
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
		add(dateInput);
		add(timeInput);
		add(placeInput);
		add(descriptionInput);
		add(textAreaScrollPane);
		
		setLayout(null);
		resize();
		
	}
	
	public void resize(){
		
		GuiConstants guiConstants = new GuiConstants();
		
		headline.setBounds(guiConstants.getDistance()*30, guiConstants.getDistance(), 400, 40);
		headline.setFont(new Font(headline.getFont().getName(),0,30));
		
		dateLabel.setBounds(guiConstants.getDistance()*5, headline.getHeight() + guiConstants.getDistance()*5, 80, 30);
		dateLabel.setFont(new Font(dateLabel.getFont().getName(),0,15));
		dateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		dateInput.setBounds(dateLabel.getX()+ dateLabel.getWidth() + guiConstants.getDistance(),
				dateLabel.getY(), 300, 30);
		dateInput.setFont(new Font(dateInput.getFont().getName(),0,15));
		
		timeLabel.setBounds(dateLabel.getX(), dateLabel.getY() + dateLabel.getHeight() + guiConstants.getDistance(), 80, 30);
		timeLabel.setFont(new Font(timeLabel.getFont().getName(),0,15));
		timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		timeInput.setBounds(dateInput.getX(), dateLabel.getY() + dateLabel.getHeight() + guiConstants.getDistance(), 300, 30);
		timeInput.setFont(new Font(timeInput.getFont().getName(),0,15));
		
		descriptionLabel.setBounds(timeLabel.getX(), timeLabel.getY() + timeLabel.getHeight() + guiConstants.getDistance(), 80, 30);
		descriptionLabel.setFont(new Font(descriptionLabel.getFont().getName(),0,15));
		descriptionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		//TODO: Må legge til scrollbar		
		descriptionInput.setBounds(timeInput.getX(),timeLabel.getY() + timeLabel.getHeight() + guiConstants.getDistance() , 290, 150 );
		descriptionInput.setLineWrap(true);
		textAreaScrollPane.setBounds(timeInput.getX(),timeLabel.getY() + timeLabel.getHeight() + guiConstants.getDistance() , 290, 150 );
		
		
		placeLabel.setBounds(timeLabel.getX(), descriptionInput.getY() + descriptionInput.getHeight() + guiConstants.getDistance(), 80, 30);
		placeLabel.setFont(new Font(placeLabel.getFont().getName(),0,15));
		placeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		placeInput.setBounds(timeInput.getX(), descriptionInput.getY() + descriptionInput.getHeight() + guiConstants.getDistance(), 300, 30);
		placeInput.setFont(new Font(placeInput.getFont().getName(),0,15));
		
		if(isLeader){
			cancelButton.setBounds(placeInput.getX(), placeLabel.getY() + placeLabel.getHeight() + guiConstants.getDistance(), 80, 35);
			editButton.setBounds(cancelButton.getX() + cancelButton.getWidth() + guiConstants.getDistance(), 
					placeLabel.getY() + placeLabel.getHeight() + guiConstants.getDistance(), 80, 35);
			toCalendarButton.setBounds(editButton.getX() + editButton.getWidth() + guiConstants.getDistance(), 
					placeLabel.getY() + placeLabel.getHeight() + guiConstants.getDistance(), 110, 35);
		}
		else{
			rejectButton.setBounds(placeInput.getX(), placeLabel.getY() + placeLabel.getHeight() + guiConstants.getDistance(), 80, 35);
			accpectButton.setBounds(rejectButton.getX() + rejectButton.getWidth() + guiConstants.getDistance(), 
					placeLabel.getY() + placeLabel.getHeight() + guiConstants.getDistance(), 80, 35);
			toCalendarButton.setBounds(accpectButton.getX() + accpectButton.getWidth() + guiConstants.getDistance(), 
					placeLabel.getY() + placeLabel.getHeight() + guiConstants.getDistance(), 110, 35);
			
		}
		
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Avtale");
		AppointmentView appointmentView = new AppointmentView();
		frame.add(appointmentView);
		frame.getContentPane();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setSize(1000, 700);
		frame.setVisible(true);
	}
}
