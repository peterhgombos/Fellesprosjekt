package client.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

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
	
	private JTextField dateInput;
	private JTextField timeInput;
	private JTextField placeInput;
	private JTextArea descriptionInput;
	private JScrollPane textAreaScrollPane;
	
	private JButton accpectButton; 		//Bare deltaker
	private JButton rejectButton;		//Bare deltaker
	private JButton toCalendarButton;	//Deltaker og leder
	private JButton editButton;			//Bare leder
	private JButton cancelButton;		//Bare leder
	
	private JList leaderList;
	private JList acceptedList;
	private JList deniedList;
	private JList notAnsweredList;
	private JTextField numberOfParticipantsField;
	private DefaultListModel leaderModel;
	private DefaultListModel acceptedModel;
	private DefaultListModel deniedModel;
	private DefaultListModel notAnsweredModel;
	private String numberOfParticipants;
	
	
	public AppointmentView() {
		
		//TESTER
		appointmentName = "TEST";
		isLeader = false;
		description = "Dette er en laaaaaaaaaaaaang beskrivelse til TEST!! <3 dette kommme efi fsei FJ ISE HFUDF DSF H SFH SFSHFIS HFISF HSIF SIFH SIF HSIGHSI H SI FHSIF HSIF HSIGH SIGH SIG HSIDHG SIGH SIDHG SIHDGISHG SIGHSIDGH SIDGH SDIGHSDIHG SIF GSIDGH SIDH GSIDHG SIDH GIDSGH ISDHG ISDG HSIDHG ISD HGSIDHG ISDH GISH NSIDG HSIG SIDG HSIGH SIG HSIGHSIHG SIDG HSIDG SD GSHDI GSIDHG ISDHG ISDHG ISDH GISH GISHG ISDGH IDSGH ISG HSIDG SIDGH IDS HGSID HGSID HGDSIHG SIGH SID HGISD HGISDHFGIDSHGISDUUUUUUU D  D D  D D D D D D D  DD FJIHSDHF I SDHIFUSDHFIUDH GUID FXICG DFGI FCIGDXIGUSTDGICFHGOD HGUDYFGOSHDGOUDSAZHFUIASGHISDFHGOUDFASHGIUAFD HGAFDHGOADSHGOASHGADSH FGIUASEHF USOe fausd g";
		date = "30.03.2012";
		time = "12:00-13:00";
		place = "NTNU";
		numberOfParticipants = "123";
		//TESTER
		
		headline = new JLabel(appointmentName);
		timeLabel = new JLabel("Tid");
		dateLabel = new JLabel("Dato");
		descriptionLabel = new JLabel("Beskrivelse");
		placeLabel = new JLabel("Sted");
		participantsLabel = new JLabel("Deltakere");
		meetingLeaderLabel = new JLabel("Møteleder");
		participateLabel = new JLabel("Deltar");
		notParticipateLabel = new JLabel("Deltar Ikke");
		notAnsweredLabel = new JLabel("Ikke Svar");
		
		dateInput = new JTextField(date);
		dateInput.setEditable(false);
		dateInput.setBackground(this.getBackground());
		timeInput = new JTextField(time);
		timeInput.setEditable(false);
		timeInput.setBackground(this.getBackground());
		placeInput = new JTextField(place);
		placeInput.setEditable(false);
		placeInput.setBackground(this.getBackground());
		descriptionInput = new JTextArea(description);
		descriptionInput.setEditable(false);
		descriptionInput.setBackground(this.getBackground());
		numberOfParticipantsField = new JTextField(numberOfParticipants);
		numberOfParticipantsField.setEditable(false);
		numberOfParticipantsField.setBackground(this.getBackground());
		
		
		accpectButton = new JButton("Godta");
		rejectButton = new JButton("Avslå");
		toCalendarButton = new JButton("Til Kalender");
		editButton = new JButton("Rediger");
		cancelButton = new JButton("Avlys");
		
		leaderList = new JList();
		acceptedList = new JList();
		deniedList = new JList();
		notAnsweredList = new JList();
		leaderModel = new DefaultListModel();
		acceptedModel = new DefaultListModel();
		deniedModel = new DefaultListModel();
		notAnsweredModel = new DefaultListModel();
		
		leaderList.setModel(leaderModel);
		leaderModel.addElement("leder");
		acceptedList.setModel(acceptedModel);
		acceptedModel.addElement("deltaker");
		acceptedModel.addElement("deltaker");
		deniedList.setModel(deniedModel);
		deniedModel.addElement("deltaker");
		deniedModel.addElement("deltaker");
		notAnsweredModel.addElement("deltaker");
		notAnsweredList.setModel(notAnsweredModel);
		textAreaScrollPane = new JScrollPane(descriptionInput);
		
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
		add(textAreaScrollPane);
		add(numberOfParticipantsField);
		add(leaderList);
		add(acceptedList);
		add(deniedList);
		add(notAnsweredList);
		
		setLayout(null);
		resize();
		
	}
	
	public void resize(){
		
		headline.setBounds(GuiConstants.DISTANCE*27, GuiConstants.DISTANCE, 300, 40);
		headline.setFont(new Font(headline.getFont().getName(),0,30));
		
		dateLabel.setBounds(GuiConstants.DISTANCE*5, headline.getHeight() + GuiConstants.DISTANCE*5, 100, 30);
		dateLabel.setFont(new Font(dateLabel.getFont().getName(),0,15));
		dateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		dateInput.setBounds(dateLabel.getX()+ dateLabel.getWidth() + GuiConstants.DISTANCE,
				dateLabel.getY(), 300, 30);
		dateInput.setFont(new Font(dateInput.getFont().getName(),0,15));
		
		participantsLabel.setBounds(dateInput.getX() + dateInput.getWidth() + GuiConstants.GROUP_DISTANCE *8, dateInput.getY(), 100, 30);
		participantsLabel.setFont(new Font(participantsLabel.getFont().getName(),0,18));
		
		numberOfParticipantsField.setBounds(participantsLabel.getX() + participantsLabel.getWidth() + GuiConstants.DISTANCE, participantsLabel.getY(), 
				40, 30);
		meetingLeaderLabel.setBounds(participantsLabel.getX(), participantsLabel.getY() + participantsLabel.getHeight() + GuiConstants.DISTANCE, 80, 30);
		meetingLeaderLabel.setFont(new Font(meetingLeaderLabel.getFont().getName(), 0, 15));
		
		leaderList.setBounds(meetingLeaderLabel.getX(), meetingLeaderLabel.getY() + meetingLeaderLabel.getHeight() + GuiConstants.DISTANCE, 150, 50);
		
		participateLabel.setBounds(leaderList.getX(), leaderList.getY() + leaderList.getHeight() + GuiConstants.GROUP_DISTANCE, 80, 30);
		participateLabel.setFont(new Font(participateLabel.getFont().getName(), 0, 15));
		
		acceptedList.setBounds(participateLabel.getX(), participateLabel.getY() + participateLabel.getHeight() + GuiConstants.DISTANCE, 150, 50);
		
		notParticipateLabel.setBounds(acceptedList.getX(), acceptedList.getY() + acceptedList.getHeight() + GuiConstants.GROUP_DISTANCE, 80, 30);
		notParticipateLabel.setFont(new Font(notParticipateLabel.getFont().getName(), 0, 15));
		
		deniedList.setBounds(notParticipateLabel.getX(), notParticipateLabel.getY() + notParticipateLabel.getHeight() + GuiConstants.DISTANCE, 150, 50);
		
		notAnsweredLabel.setBounds(deniedList.getX(), deniedList.getY() + deniedList.getHeight() + GuiConstants.GROUP_DISTANCE, 80, 30);
		notAnsweredLabel.setFont(new Font(notAnsweredLabel.getFont().getName(), 0, 15));
		
		notAnsweredList.setBounds(notAnsweredLabel.getX(), notAnsweredLabel.getY() + notAnsweredLabel.getHeight() + GuiConstants.DISTANCE, 150, 50);
		
		timeLabel.setBounds(dateLabel.getX(), dateLabel.getY() + dateLabel.getHeight() + GuiConstants.DISTANCE, 100, 30);
		timeLabel.setFont(new Font(timeLabel.getFont().getName(),0,15));
		timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		timeInput.setBounds(dateInput.getX(), dateLabel.getY() + dateLabel.getHeight() + GuiConstants.DISTANCE, 300, 30);
		timeInput.setFont(new Font(timeInput.getFont().getName(),0,15));
		
		descriptionLabel.setBounds(timeLabel.getX(), timeLabel.getY() + timeLabel.getHeight() + GuiConstants.GROUP_DISTANCE, 100, 30);
		descriptionLabel.setFont(new Font(descriptionLabel.getFont().getName(),0,15));
		descriptionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			
		descriptionInput.setLineWrap(true);
		textAreaScrollPane.setBounds(timeInput.getX(), timeInput.getY()+ timeInput.getHeight() + GuiConstants.GROUP_DISTANCE, 300, 100 );
		
		
		placeLabel.setBounds(descriptionLabel.getX(), descriptionLabel.getY() + textAreaScrollPane.getHeight() + GuiConstants.GROUP_DISTANCE, 100, 30);
		placeLabel.setFont(new Font(placeLabel.getFont().getName(),0,15));
		placeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		placeInput.setBounds(textAreaScrollPane.getX(), textAreaScrollPane.getY() + textAreaScrollPane.getHeight() + GuiConstants.GROUP_DISTANCE, 300, 30);
		placeInput.setFont(new Font(placeInput.getFont().getName(),0,15));
		
		if(isLeader){
			cancelButton.setBounds(placeInput.getX(), placeLabel.getY() + placeLabel.getHeight() + GuiConstants.GROUP_DISTANCE, 80, 35);
			editButton.setBounds(cancelButton.getX() + cancelButton.getWidth() + GuiConstants.DISTANCE, 
					placeLabel.getY() + placeLabel.getHeight() + GuiConstants.GROUP_DISTANCE, 80, 35);
			toCalendarButton.setBounds(editButton.getX() + editButton.getWidth() + GuiConstants.DISTANCE, 
					placeLabel.getY() + placeLabel.getHeight() + GuiConstants.GROUP_DISTANCE, 110, 35);
		}
		else{
			accpectButton.setBounds(placeInput.getX(), placeLabel.getY() + placeLabel.getHeight() + GuiConstants.GROUP_DISTANCE, 80, 35);
			rejectButton.setBounds(accpectButton.getX() + accpectButton.getWidth() + GuiConstants.DISTANCE, 
					placeLabel.getY() + placeLabel.getHeight() + GuiConstants.GROUP_DISTANCE, 80, 35);
			toCalendarButton.setBounds(rejectButton.getX() + rejectButton.getWidth() + GuiConstants.DISTANCE, 
					placeLabel.getY() + placeLabel.getHeight() + GuiConstants.GROUP_DISTANCE, 110, 35);
			
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
