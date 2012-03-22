package client.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.toedter.calendar.JDateChooser;

import client.Client;
import client.connection.ServerData;

import common.dataobjects.Appointment;
import common.dataobjects.Meeting;
import common.dataobjects.Note;
import common.dataobjects.Person;
import common.utilities.DateString;

import server.Server;

public class NewMeeting extends JPanel{
	
	private JDateChooser datepicker;
	private JDateChooser datepickerDays;
	private JCheckBox severalDays;
	private JLabel severalDaysLabel;
	
	private JLabel headlineLabel;
	private JLabel titleLabel;
	private JLabel dateLabel;
	private JLabel startTimeLabel;
	private JLabel endTimeLabel;
	private JLabel descriptionLabel;
	private JLabel participantsLabel;
	private JLabel placeLabel;
	private JLabel roomInformationLabel;
	
	private JScrollPane scrollPane;
	
	private JTextField nameField; 
	private JTextField placeField;
	private JTextArea descriptionArea;
	
	private JComboBox startTimeHoursField;
	private JComboBox endTimeHoursField;
	private JComboBox startTimeMinField;
	private JComboBox endTimeMinField;
	private JComboBox roomPicker;
	private AddRemoveParticipants addRemovep;
	
	private JRadioButton bookMeetingroomRadioButton;
	private JRadioButton otherPlaceRadioButton;
	
	private JButton addParticipantsButton;
	private JButton saveButton;
	private JButton cancelButton;
	private ButtonGroup radioButtonGroup;
	
	private CalendarPanel calendar;
	private HashMap<Person,Integer> participantsList;
	private NewMeeting thisNewMeeting;
	
	

	public NewMeeting(CalendarPanel calendarPanel){
		
		String[] min = {"00", "15", "30", "45"};
		String[] hours= {"00","01","02","03","04","05","06","07","08","09","10",
						"11","12","13","14","15","16","17","18","19","20","21","22","23"};
		
		thisNewMeeting = this;
		
		participantsList = new HashMap<Person, Integer>();
		calendar = calendarPanel;
		headlineLabel = new JLabel("Nytt Møte");
		titleLabel = new JLabel("Tittel", SwingConstants.RIGHT);
		dateLabel = new JLabel("Dato", SwingConstants.RIGHT);
		startTimeLabel = new JLabel("Fra", SwingConstants.RIGHT);
		endTimeLabel = new JLabel("Til", SwingConstants.RIGHT);
		descriptionLabel = new JLabel("Beskrivelse", SwingConstants.RIGHT);
		placeLabel = new JLabel("Sted", SwingConstants.RIGHT);
		roomInformationLabel =  new JLabel("(min 2 deltakere)");
		participantsLabel = new JLabel("Deltakere", SwingConstants.RIGHT);
		
		nameField = new JTextField();
		placeField = new JTextField();
		descriptionArea = new JTextArea();
		descriptionArea.setLineWrap(true);
		
		bookMeetingroomRadioButton = new JRadioButton();
		otherPlaceRadioButton = new JRadioButton();
		bookMeetingroomRadioButton.setText("Book møterom");
		otherPlaceRadioButton.setText("Annet sted");
		radioButtonGroup = new ButtonGroup();
		radioButtonGroup.add(bookMeetingroomRadioButton);
		radioButtonGroup.add(otherPlaceRadioButton);
		
		datepicker = new JDateChooser();
		datepicker.setMinSelectableDate(new Date(System.currentTimeMillis()));
		datepickerDays = new JDateChooser();
		datepickerDays.setVisible(false);
		datepickerDays.setMinSelectableDate(new Date(System.currentTimeMillis()));
		severalDays = new JCheckBox();
		severalDaysLabel = new JLabel("Flere dager");
		severalDays.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (e.getStateChange() == e.SELECTED) {
					datepickerDays.setVisible(true);
					datepickerDays.setEnabled(true);


				}
				if (e.getStateChange() == e.DESELECTED){

					//TODO remember to ensure end-date is not linked to datepickerDays when disabled/invisible
					datepickerDays.setVisible(false);
					datepickerDays.setEnabled(false);
				}
			}
		});
		
		bookMeetingroomRadioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				roomPicker.setEditable(true);
				placeField.setEditable(false);
			}
		});
		otherPlaceRadioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				roomPicker.setEditable(false);
				placeField.setEditable(true);
			}
		});
		
		addParticipantsButton = new JButton("Legg til/fjern");
		addParticipantsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addRemovep = new AddRemoveParticipants(calendar,thisNewMeeting);
			}
		});
		saveButton = new JButton("Lagre");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String title = nameField.getText();
				
				String dateStart = datepicker.getJCalendar().getCalendar().get(Calendar.YEAR) + "-" + (datepicker.getJCalendar().getCalendar().get(Calendar.MONTH) + 1) + "-" + datepicker.getJCalendar().getCalendar().get(Calendar.DAY_OF_MONTH);;
				String dateEnd;
				if(datepickerDays.isEnabled()){
					dateEnd = datepickerDays.getJCalendar().getCalendar().get(Calendar.YEAR) + "-" + (datepickerDays.getJCalendar().getCalendar().get(Calendar.MONTH) + 1) + "-" + datepickerDays.getJCalendar().getCalendar().get(Calendar.DAY_OF_MONTH); ;
				}
				else{
					dateEnd = dateStart;
				}
				
				//TODO: Feilmeldinger for dato
				
				String timeStart = startTimeHoursField.getSelectedItem() + ":" + startTimeMinField.getSelectedItem();
				String timeEnd = endTimeHoursField.getSelectedItem() + ":" + endTimeMinField.getSelectedItem();
				
				String description = descriptionArea.getText();
				
				String place = "";
				if(bookMeetingroomRadioButton.isSelected()){
					//place = roomPicker.getSelectedItem();
				}
				else if (otherPlaceRadioButton.isSelected()){
					place = placeField.getText();
				}
				
				if(title.trim().equals("")){
					UserInformationMessages.showErrormessage("Du må lage en tittel");
				}
//				TODO Datoene
//				else if(){
//					
//				}
				else if(bookMeetingroomRadioButton.isSelected() && participantsList.size()<2){
					UserInformationMessages.showErrormessage("Det må være minst 2 deltakere for å booke et møterom");
				}
				
				Meeting m = new Meeting(-1, Client.getUser(),title, description, place, new DateString(dateStart + " " + timeStart), new DateString(dateEnd + " " + timeEnd), participantsList);
				//ServerData.requestNewMeeting(m);;
				
				calendar.goToCalender();
				
			}
		});
		cancelButton = new JButton("Avbryt");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calendar.goToCalender();
			}
		});
		
		startTimeHoursField = new JComboBox(hours);
		endTimeHoursField = new JComboBox(hours);
		startTimeMinField = new JComboBox(min);
		endTimeMinField = new JComboBox(min);
		roomPicker = new JComboBox();
		
		scrollPane = new JScrollPane(descriptionArea);
		
		add(headlineLabel);
		add(titleLabel);
		add(dateLabel);
		add(startTimeLabel);
		add(endTimeLabel);
		add(descriptionLabel);
		add(participantsLabel);
		add(placeLabel);
		add(roomInformationLabel);
		add(nameField);
		add(roomPicker);
		add(placeField);
		add(scrollPane);
		add(bookMeetingroomRadioButton);
		add(otherPlaceRadioButton);
		add(addParticipantsButton);
		add(saveButton);
		add(cancelButton);
		add(startTimeHoursField);
		add(startTimeMinField);
		add(endTimeHoursField);
		add(endTimeMinField);
		add(datepicker);
		add(datepickerDays);
		add(severalDays);
		add(severalDaysLabel);
		setLayout(null);
		resize();
		
	}
	
	public void resize(){
		headlineLabel.setBounds(GuiConstants.DISTANCE*27, GuiConstants.GROUP_DISTANCE, GuiConstants.HEADLINE_LENGTH, GuiConstants.HEADLINE_HEIGTH);
		headlineLabel.setFont(new Font(headlineLabel.getFont().getName(), 0, 30));
		
		titleLabel.setBounds(GuiConstants.DISTANCE, headlineLabel.getY() + headlineLabel.getHeight() + GuiConstants.GROUP_DISTANCE, 100, GuiConstants.LABEL_HEIGTH);
		titleLabel.setFont(new Font(headlineLabel.getFont().getName(), 0, 16));
		
		nameField.setBounds(GuiConstants.DISTANCE*2+ titleLabel.getWidth() + titleLabel.getX(), titleLabel.getY(), 190, GuiConstants.TEXTFIELD_HEIGTH);
		
		dateLabel.setBounds(titleLabel.getX(), titleLabel.getY() + titleLabel.getHeight() + GuiConstants.DISTANCE, 100, GuiConstants.LABEL_HEIGTH);
		dateLabel.setFont(new Font(dateLabel.getFont().getName(), 0, 16));
		
		datepicker.setBounds(GuiConstants.DISTANCE*2 + titleLabel.getWidth() + titleLabel.getX(), dateLabel.getY(), 190, GuiConstants.TEXTFIELD_HEIGTH);
		
		severalDays.setBounds(datepicker.getX(), dateLabel.getY() + dateLabel.HEIGHT + GuiConstants.GROUP_DISTANCE, 20, 20);

		severalDaysLabel.setBounds(severalDays.getX() + severalDays.getWidth() + GuiConstants.DISTANCE, severalDays.getY(), 200, GuiConstants.LABEL_HEIGTH);
		severalDaysLabel.setFont(new Font(severalDaysLabel.getFont().getName(),0 ,10));

		datepickerDays.setBounds(datepicker.getX() + datepicker.getWidth() + GuiConstants.DISTANCE, datepicker.getY(), 190, GuiConstants.TEXTFIELD_HEIGTH);

		
		startTimeLabel.setBounds(titleLabel.getX(), severalDays.getY() + severalDays.getHeight() + GuiConstants.GROUP_DISTANCE, 100, GuiConstants.LABEL_HEIGTH);
		startTimeLabel.setFont(new Font(startTimeLabel.getFont().getName(), 0, 16));
		
		startTimeHoursField.setBounds(GuiConstants.DISTANCE*2+ titleLabel.getWidth() + titleLabel.getX() , startTimeLabel.getY(), 70, GuiConstants.TEXTFIELD_HEIGTH);
		startTimeMinField.setBounds(startTimeHoursField.getX() + 2 + startTimeHoursField.getWidth(), startTimeHoursField.getY(), 70, GuiConstants.TEXTFIELD_HEIGTH);
		
		endTimeLabel.setBounds(startTimeHoursField.getX() + startTimeMinField.getWidth() + GuiConstants.DISTANCE, startTimeHoursField.getY(), 100, GuiConstants.LABEL_HEIGTH);
		endTimeLabel.setFont(new Font(endTimeLabel.getFont().getName(), 0, 16));
		
		endTimeHoursField.setBounds(endTimeLabel.getX() + endTimeLabel.getWidth() + GuiConstants.DISTANCE, endTimeLabel.getY(), 70, GuiConstants.TEXTFIELD_HEIGTH);
		endTimeMinField.setBounds(endTimeHoursField.getX() + 2 + endTimeHoursField.getWidth(), endTimeHoursField.getY(), 70, GuiConstants.TEXTFIELD_HEIGTH);
		
		descriptionLabel.setBounds(titleLabel.getX(), startTimeLabel.getY() + startTimeLabel.getHeight() + GuiConstants.GROUP_DISTANCE, 100, GuiConstants.LABEL_HEIGTH);
		descriptionLabel.setFont(new Font(descriptionLabel.getFont().getName(), 0, 16));
		
		scrollPane.setBounds(GuiConstants.DISTANCE*2+ titleLabel.getWidth() + titleLabel.getX(), descriptionLabel.getY(), (endTimeMinField.getX() + endTimeMinField.getWidth())-startTimeHoursField.getX(), 100);
		
		participantsLabel.setBounds(titleLabel.getX(), scrollPane.getY() + scrollPane.getHeight() + GuiConstants.GROUP_DISTANCE, 100, GuiConstants.LABEL_HEIGTH);
		participantsLabel.setFont(new Font(participantsLabel.getFont().getName(), 0, 16));
		
		addParticipantsButton.setBounds(GuiConstants.DISTANCE*2+ titleLabel.getWidth() + titleLabel.getX(), participantsLabel.getY(), 160, 35);
		
		placeLabel.setBounds(titleLabel.getX(), participantsLabel.getY() + participantsLabel.getHeight() + GuiConstants.GROUP_DISTANCE, 100, GuiConstants.LABEL_HEIGTH);
		placeLabel.setFont(new Font(placeLabel.getFont().getName(), 0, 16));
		
		bookMeetingroomRadioButton.setBounds(GuiConstants.DISTANCE*2+ titleLabel.getWidth() + titleLabel.getX(), placeLabel.getY(), 120, 25);
		roomPicker.setBounds(bookMeetingroomRadioButton.getX() + bookMeetingroomRadioButton.getWidth() + GuiConstants.DISTANCE, placeLabel.getY(), 80, 25);
		roomInformationLabel.setBounds(roomPicker.getX() + roomPicker.getWidth() + 10, placeLabel.getY(), 160, 25);
		
		otherPlaceRadioButton.setBounds(bookMeetingroomRadioButton.getX(), placeLabel.getY()+ GuiConstants.DISTANCE + 20, 120, 25);
		placeField.setBounds(otherPlaceRadioButton.getX() + otherPlaceRadioButton.getWidth() + GuiConstants.DISTANCE, placeLabel.getY() + GuiConstants.DISTANCE + 20 , 160, GuiConstants.TEXTFIELD_HEIGTH);
		
		saveButton.setBounds(otherPlaceRadioButton.getX(), otherPlaceRadioButton.getY() + otherPlaceRadioButton.getHeight() + GuiConstants.DISTANCE, 100, GuiConstants.BUTTON_HEIGTH);
		saveButton.setFont(new Font(saveButton.getFont().getName(), 0, 14));
		
		cancelButton.setBounds(saveButton.getX() + saveButton.getWidth() + GuiConstants.DISTANCE, saveButton.getY(), 100, GuiConstants.BUTTON_HEIGTH);
	
		}
	
	public void addParticipants(HashMap<Person,Integer> p){
		participantsList = p;
	}
}
