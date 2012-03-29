package client.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import client.Client;
import client.connection.ServerData;

import common.dataobjects.Appointment;
import common.dataobjects.Meeting;
import common.utilities.DateString;

@SuppressWarnings("serial")
public class AppointmentView extends JPanel{

	//Variabler som får verdier fra databasen
	private String appointmentName;
	private String description;
	private String date;
	private String time;
	private String place;

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
	private DefaultListModel leaderListModel;
	private DefaultListModel acceptedListModel;
	private DefaultListModel deniedListModel;
	private DefaultListModel notAnsweredListModel;
	private String numberOfParticipants;
	private CalendarPanel calendarpanel;

	private Appointment appointment;

	public AppointmentView(CalendarPanel calendarPanel, Appointment app) {
		appointment = app;
		calendarpanel = calendarPanel;
		if(app instanceof Meeting){
			System.out.println("constructor: " + ((Meeting)app).getAnswers());
		}


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

		accpectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Meeting m = (Meeting)appointment;
				m.changeParticipantAnswer(Client.getUser(), Meeting.SVAR_OK);
				ServerData.requestUpdateAnswers(Client.getUser().getId(), m.getId(), Meeting.SVAR_OK);
				//ServerData.getNewOldNotes(Client.getUser());
				accpectButton.setEnabled(false);
				rejectButton.setEnabled(true);
				setComponents();
				//calendarpanel.goToCalender();
			}
		});
		rejectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Meeting m = (Meeting)appointment;
				m.changeParticipantAnswer(Client.getUser(), Meeting.SVAR_NEI);
				ServerData.requestUpdateAnswers(Client.getUser().getId(), m.getId(), Meeting.SVAR_NEI);
				//ServerData.getNewOldNotes(Client.getUser());
				accpectButton.setEnabled(true);
				rejectButton.setEnabled(false);
				setComponents();
				//calendarpanel.goToCalender();
			}
		});

		toCalendarButton = new JButton("Til Kalender");
		toCalendarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calendarpanel.goToCalender();
			}
		});
		editButton = new JButton("Rediger");
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (appointment instanceof Meeting) {
					calendarpanel.goToEditMeeting((Meeting)appointment);
				} else {
					calendarpanel.goToEditAppointment(appointment);
				}

			}
		});
		cancelButton = new JButton("Avlys");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (UserInformationMessages.showConfirmationMessage("Vil du slette avtalen?")) {
					ServerData.deleteAppointment(appointment);
					calendarpanel.goToMyAppointments();
				}
			}
		});

		leaderList = new JList(new DefaultListModel());
		acceptedList = new JList(new DefaultListModel());
		deniedList = new JList(new DefaultListModel());
		notAnsweredList = new JList(new DefaultListModel());

		leaderListModel = (DefaultListModel)leaderList.getModel();
		acceptedListModel = (DefaultListModel)acceptedList.getModel();
		deniedListModel = (DefaultListModel)deniedList.getModel();
		notAnsweredListModel = (DefaultListModel)notAnsweredList.getModel();


		acceptedList.setModel(acceptedListModel);
		deniedList.setModel(deniedListModel);
		notAnsweredList.setModel(notAnsweredListModel);
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
		add(leaderList);
		add(acceptedList);
		add(deniedList);
		add(notAnsweredList);

		setLayout(null);
		resize();
		Client.getFrame().setSize(GuiConstants.FRAME_WIDTH+1, GuiConstants.FRAME_HEIGTH+1);
		Client.getFrame().setSize(GuiConstants.FRAME_WIDTH, GuiConstants.FRAME_HEIGTH);

	}

	private void setComponents(){
		DateString start = appointment.getStartTime();
		DateString end = appointment.getEndTime();

		headline.setText(appointment.getTitle());
		dateInput.setText(start.getDay() + "." + start.getMonth() + "." + start.getYear() + (end == start? "":" - " + end.getDay() + "." + end.getMonth() + "." + end.getYear()));
		timeInput.setText(start.getHour() + ":" + start.getMinute() + " - " + end.getHour() + ":" + end.getMinute());
		descriptionInput.setText(appointment.getDescription());

		if(appointment instanceof Meeting){
			Meeting m = (Meeting)appointment;

			leaderListModel.clear();
			notAnsweredListModel.clear();
			acceptedListModel.clear();
			deniedListModel.clear();


			leaderListModel.addElement(m.getLeader());

			for (Integer pid : m.getAnswers().keySet()) {
				if(pid != m.getLeader().getId()){
					if(m.getAnswers().get(pid) == Meeting.SVAR_BLANK){
						notAnsweredListModel.addElement(m.getParticipants().get(pid));
					}
					else if(m.getAnswers().get(pid) == Meeting.SVAR_OK){
						acceptedListModel.addElement(m.getParticipants().get(pid));
					}
					else{
						deniedListModel.addElement(m.getParticipants().get(pid));
					}
				}
			}

			placeInput.setText(m.getPlace());
		}
		else{
			placeInput.setText(appointment.getPlace());
			notAnsweredList.setVisible(false);
			deniedList.setVisible(false);
			acceptedList.setVisible(false);
			leaderList.setVisible(false);
			participantsLabel.setVisible(false);
			participateLabel.setVisible(false);
			notAnsweredLabel.setVisible(false);
			meetingLeaderLabel.setVisible(false);
			notParticipateLabel.setVisible(false);
		}
	}

	public void resize(){
		setComponents();

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

		if(appointment.getLeader().getId() == Client.getUser().getId()){
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
			if(appointment instanceof Meeting){

				Meeting m = (Meeting)appointment;

				if(m.getAnswers().get(Client.getUser().getId()) == Meeting.SVAR_BLANK){
					accpectButton.setEnabled(true);
					rejectButton.setEnabled(true);
				}
				else if (m.getAnswers().get(Client.getUser().getId()) == Meeting.SVAR_OK){
					accpectButton.setEnabled(false);
					rejectButton.setEnabled(true);
				}
				else{
					accpectButton.setEnabled(true);
					rejectButton.setEnabled(false);
				}
			}

			else{
				cancelButton.setBounds(placeInput.getX(), placeLabel.getY() + placeLabel.getHeight() + GuiConstants.GROUP_DISTANCE, 80, 35);
				cancelButton.setText("Slett");
				editButton.setBounds(cancelButton.getX() + cancelButton.getWidth() + GuiConstants.DISTANCE , cancelButton.getY(), 80, 35);
				toCalendarButton.setBounds(editButton.getX() + editButton.getWidth() + GuiConstants.DISTANCE, editButton.getY(), 110, 35);
			}	
		}

	}}
