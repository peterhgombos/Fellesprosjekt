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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


import com.toedter.calendar.JDateChooser;

import client.Client;
import client.connection.MessageListener;
import client.connection.ServerData;


import common.dataobjects.Appointment;
import common.dataobjects.ComMessage;
import common.dataobjects.Meeting;
import common.dataobjects.Person;
import common.dataobjects.Room;
import common.utilities.DateString;
import common.utilities.MessageType;


@SuppressWarnings("serial")
public class NewMeeting extends JPanel implements MessageListener{
	
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
	private AddRemoveParticipants addRemoveParticipants;
	
	private JRadioButton bookMeetingroomRadioButton;
	private JRadioButton otherPlaceRadioButton;
	
	private JButton addParticipantsButton;
	private JButton saveButton;
	private JButton cancelButton;
	private ButtonGroup radioButtonGroup;
	
	private CalendarPanel calendar;
	private HashMap<Person,Integer> participantsList;
	private NewMeeting thisNewMeeting;
	
	private Meeting meeting;
	private int numberOfParticipants;
	private int numberOfExternalparticipants;
	
	private Date defaultDate = new Date(System.currentTimeMillis());
	
	private boolean isInEdit = false;
	private int existingAppointmentId;
	
	public NewMeeting(CalendarPanel pal){
		init(pal);
	}
	
	public NewMeeting(CalendarPanel pal, Meeting meet){
		isInEdit=true;
		init(pal);
		
		
		headlineLabel.setText("Rediger: " + meet.getTitle());
		nameField.setText(meet.getTitle());
		
		startTimeHoursField.setSelectedIndex(meet.getStartTime().getHour());
		startTimeMinField.setSelectedIndex(meet.getStartTime().getMinute());
		endTimeHoursField.setSelectedIndex(meet.getEndTime().getHour());
		endTimeMinField.setSelectedIndex(meet.getEndTime().getMinute());
		
		descriptionArea.setText(meet.getDescription());
		placeField.setText(meet.getPlace());
		participantsList = meet.getParticipants();
		
		existingAppointmentId = meet.getId();
		
		numberOfParticipants = meet.getNumberOfParticipants();
		
	}
	
	private void init(CalendarPanel calendarPanel){
		
		String[] min = {"00", "15", "30", "45"};
		String[] hours= {"00","01","02","03","04","05","06","07","08","09","10", "11","12","13","14","15","16","17","18","19","20","21","22","23"};
		
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
		datepicker.setDate(defaultDate);
		datepicker.setMinSelectableDate(new Date(System.currentTimeMillis()));
		datepickerDays = new JDateChooser();
		datepickerDays.setDate(defaultDate);
		datepickerDays.setVisible(false);
		datepickerDays.setMinSelectableDate(new Date(System.currentTimeMillis()));
		severalDays = new JCheckBox();
		severalDaysLabel = new JLabel("Flere dager");
		
		startTimeHoursField = new JComboBox(hours);
		endTimeHoursField = new JComboBox(hours);
		startTimeMinField = new JComboBox(min);
		endTimeMinField = new JComboBox(min);
		roomPicker = new JComboBox();
		roomPicker.setEnabled(false);
		
		endTimeHoursField.setSelectedIndex(1);
		
		ServerData.addMessageListener(this);
		
		severalDays.addItemListener(new ItemListener() {
		

			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					datepickerDays.setVisible(true);
					datepickerDays.setEnabled(true);
				}
				if (e.getStateChange() == ItemEvent.DESELECTED){
					//TODO remember to ensure end-date is not linked to datepickerDays when disabled/invisible
					datepickerDays.setVisible(false);
					datepickerDays.setEnabled(false);
				}
			}
		});
		
		endTimeHoursField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String timeEnd = "";
				
				if(Integer.parseInt(""+endTimeHoursField.getSelectedItem()) >= Integer.parseInt(""+startTimeHoursField.getSelectedItem()) || isInEdit){
					timeEnd = endTimeHoursField.getSelectedItem() + ":" + endTimeMinField.getSelectedItem() + ":0";
				}
				else {
					endTimeHoursField.setSelectedIndex(0);
					UserInformationMessages.showErrormessage("Du kan ikke sette avtaler som går bakover i tid");
					return;
				}
				
			}
		});
		
		endTimeMinField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String timeEnd = "";
				if((Integer.parseInt(""+endTimeHoursField.getSelectedItem()) == Integer.parseInt(""+startTimeHoursField.getSelectedItem())) && (Integer.parseInt(""+startTimeMinField.getSelectedItem())> Integer.parseInt(""+ endTimeMinField.getSelectedItem())) || !isInEdit){
					UserInformationMessages.showErrormessage("Du kan ikke sette avtaler som går bakover i tid");
					return;
				}
				else{

					timeEnd = endTimeHoursField.getSelectedItem() + ":" + endTimeMinField.getSelectedItem() + ":0";

				}
				
			}
		});
		
		startTimeHoursField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String timeEnd = "";
				
				if(Integer.parseInt(""+endTimeHoursField.getSelectedItem()) >= Integer.parseInt(""+startTimeHoursField.getSelectedItem()) || isInEdit){
					timeEnd = endTimeHoursField.getSelectedItem() + ":" + endTimeMinField.getSelectedItem() + ":0";
				}
				else {
					endTimeHoursField.setSelectedIndex(0);
					UserInformationMessages.showErrormessage("Du kan ikke sette avtaler som går bakover i tid");
					return;
				}
				
			}
		});
		
		startTimeMinField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String timeEnd = "";
				if((Integer.parseInt(""+endTimeHoursField.getSelectedItem()) == Integer.parseInt(""+startTimeHoursField.getSelectedItem())) && (Integer.parseInt(""+startTimeMinField.getSelectedItem()) > Integer.parseInt(""+ endTimeMinField.getSelectedItem())) || !isInEdit){
					UserInformationMessages.showErrormessage("Du kan ikke sette avtaler som går bakover i tid");
					return;
				}
				else {
					timeEnd = endTimeHoursField.getSelectedItem() + ":" + endTimeMinField.getSelectedItem() + ":0";
				}
				
			}
		});
		
		bookMeetingroomRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				roomPicker.setEnabled(true);
				placeField.setText("");
				String dateStart = datepicker.getJCalendar().getCalendar().get(Calendar.YEAR) + "-" + (datepicker.getJCalendar().getCalendar().get(Calendar.MONTH) + 1) + "-" + datepicker.getJCalendar().getCalendar().get(Calendar.DAY_OF_MONTH);;
				String dateEnd;
				if(datepickerDays.isEnabled()  && datepickerDays.isVisible()){
					dateEnd = datepickerDays.getJCalendar().getCalendar().get(Calendar.YEAR) + "-" + (datepickerDays.getJCalendar().getCalendar().get(Calendar.MONTH) + 1) + "-" + datepickerDays.getJCalendar().getCalendar().get(Calendar.DAY_OF_MONTH); ;
				}
				else{
					dateEnd = dateStart;
				}
				String timeStart = startTimeHoursField.getSelectedItem() + ":" + startTimeMinField.getSelectedItem() + ":0";
				String timeEnd = endTimeHoursField.getSelectedItem() + ":" + endTimeMinField.getSelectedItem() + ":0";
				ServerData.requestAvailableRooms(new Meeting(-1, null, null, null, null, null, new DateString(dateStart + " " + timeStart), new DateString(dateEnd + " " + timeEnd), participantsList, numberOfParticipants));
			}
		});
		otherPlaceRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				roomPicker.setEnabled(false);
				roomPicker.setSelectedIndex(-1);
				placeField.setEnabled(true);
				
			}
		});
		
		addParticipantsButton = new JButton("Legg til/fjern");
		addParticipantsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addRemoveParticipants = new AddRemoveParticipants(calendar, thisNewMeeting);
			}
		});
		saveButton = new JButton("Lagre");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String title = nameField.getText();
				
				String dateStart = datepicker.getJCalendar().getCalendar().get(Calendar.YEAR) + "-" + (datepicker.getJCalendar().getCalendar().get(Calendar.MONTH) + 1) + "-" + datepicker.getJCalendar().getCalendar().get(Calendar.DAY_OF_MONTH);;
				String dateEnd;
				if(datepickerDays.isEnabled()  && datepickerDays.isVisible()){
					dateEnd = datepickerDays.getJCalendar().getCalendar().get(Calendar.YEAR) + "-" + (datepickerDays.getJCalendar().getCalendar().get(Calendar.MONTH) + 1) + "-" + datepickerDays.getJCalendar().getCalendar().get(Calendar.DAY_OF_MONTH); ;
				}
				else{
					dateEnd = dateStart;
				}
				
				String timeStart = startTimeHoursField.getSelectedItem() + ":" + startTimeMinField.getSelectedItem() + ":0";
				String timeEnd = endTimeHoursField.getSelectedItem() + ":" + endTimeMinField.getSelectedItem() + ":0";
				String description = descriptionArea.getText();
				
				String place = "";
				Room nyttrom = null;
				
				if(bookMeetingroomRadioButton.isSelected()){
					nyttrom = ((common.dataobjects.Room)roomPicker.getSelectedItem());
					place = nyttrom.getRomId();
				}
				else if (otherPlaceRadioButton.isSelected()){
					place = placeField.getText();
				}
				
				if(title.trim().equals("")){
					UserInformationMessages.showErrormessage("Du må lage en tittel");
					return;
				}else if(bookMeetingroomRadioButton.isSelected() && numberOfParticipants<1 ){
					UserInformationMessages.showErrormessage("Det må være minst 2 deltakere for å booke et møterom");
					return;
				}
				
				if(isInEdit){
					Meeting a = new Meeting(existingAppointmentId, Client.getUser(), title, description, place, nyttrom, new DateString(dateStart + " " + timeStart), new DateString(dateEnd + " " + timeEnd), participantsList, numberOfExternalparticipants);
					ServerData.requestUpdateMeeting(a);
					calendar.goToCalender();
					return;
				}
				
				if (numberOfParticipants < 1) {
					UserInformationMessages.showErrormessage("Du må legge til minst en deltaker");
					return;
				}
				
				Meeting m = new Meeting(-1, Client.getUser(),title, description, place, nyttrom, new DateString(dateStart + " " + timeStart), new DateString(dateEnd + " " + timeEnd), participantsList, numberOfParticipants);
				ServerData.requestNewMeeting(m);
				ServerData.removeMessageListener(thisNewMeeting);
				
				calendar.goToCalender();
			}
		});
		cancelButton = new JButton("Avbryt");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calendar.goToCalender();
				ServerData.removeMessageListener(thisNewMeeting);
			}
		});
		

		
		scrollPane = new JScrollPane(descriptionArea);
		
		setLayout(null);
		resize();
		
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
		
		Client.getFrame().resize(GuiConstants.FRAME_WIDTH+1, GuiConstants.FRAME_HEIGTH+1);
		Client.getFrame().resize(GuiConstants.FRAME_WIDTH, GuiConstants.FRAME_HEIGTH);
		
	}
	
	public void resize(){
		headlineLabel.setBounds(GuiConstants.HEADLINE_X, GuiConstants.HEADLINE_Y, GuiConstants.HEADLINE_WIDTH, GuiConstants.HEADLINE_HEIGTH);
		headlineLabel.setFont(GuiConstants.FONT_30);
		
		titleLabel.setBounds(GuiConstants.DISTANCE, headlineLabel.getY() + headlineLabel.getHeight() + GuiConstants.GROUP_DISTANCE, 100, GuiConstants.LABEL_HEIGTH);
		titleLabel.setFont(new Font(headlineLabel.getFont().getName(), 0, 16));
		
		nameField.setBounds(GuiConstants.DISTANCE*2+ titleLabel.getWidth() + titleLabel.getX(), titleLabel.getY(), 190, GuiConstants.TEXTFIELD_HEIGTH);
		
		dateLabel.setBounds(titleLabel.getX(), titleLabel.getY() + titleLabel.getHeight() + GuiConstants.DISTANCE, 100, GuiConstants.LABEL_HEIGTH);
		dateLabel.setFont(new Font(dateLabel.getFont().getName(), 0, 16));
		
		datepicker.setBounds(GuiConstants.DISTANCE*2 + titleLabel.getWidth() + titleLabel.getX(), dateLabel.getY(), 190, GuiConstants.TEXTFIELD_HEIGTH);
		
		severalDays.setBounds(datepicker.getX(), dateLabel.getY() + dateLabel.getHeight() + GuiConstants.GROUP_DISTANCE, 23, 23);

		severalDaysLabel.setBounds(severalDays.getX() + severalDays.getWidth() + GuiConstants.DISTANCE, severalDays.getY(), 200, GuiConstants.LABEL_HEIGTH);
		
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
		roomPicker.setBounds(bookMeetingroomRadioButton.getX() + bookMeetingroomRadioButton.getWidth() + GuiConstants.DISTANCE, placeLabel.getY(), 160, 25);
		roomInformationLabel.setBounds(roomPicker.getX() + roomPicker.getWidth() + 10, placeLabel.getY(), 160, 25);
		
		otherPlaceRadioButton.setBounds(bookMeetingroomRadioButton.getX(), placeLabel.getY()+ GuiConstants.DISTANCE + 20, 120, 25);
		placeField.setBounds(otherPlaceRadioButton.getX() + otherPlaceRadioButton.getWidth() + GuiConstants.DISTANCE, placeLabel.getY() + GuiConstants.DISTANCE + 20 , 160, GuiConstants.TEXTFIELD_HEIGTH);
		
		saveButton.setBounds(otherPlaceRadioButton.getX(), otherPlaceRadioButton.getY() + otherPlaceRadioButton.getHeight() + GuiConstants.DISTANCE, 100, GuiConstants.BUTTON_HEIGTH);
		saveButton.setFont(new Font(saveButton.getFont().getName(), 0, 14));
		
		cancelButton.setBounds(saveButton.getX() + saveButton.getWidth() + GuiConstants.DISTANCE, saveButton.getY(), 100, GuiConstants.BUTTON_HEIGTH);
		}
	
	public void addParticipants(HashMap<Person,Integer> p){
		for(Person person : p.keySet()){
			participantsList.put(person, p.get(person));
		}
	}
	public HashMap<Person, Integer> getParticipantList(){
		return participantsList;
	}
	public Meeting getMeeting(){
		return meeting;
	}
	
	public void setNumberOfParticipants(int externalParticipants){
		numberOfExternalparticipants = externalParticipants;
		numberOfParticipants = externalParticipants + participantsList.values().size();
	}
	
	public int getNumberOfExternalParticipants(){
		return numberOfExternalparticipants;
	}
	@Override
	public void receiveMessage(ComMessage m) {
		
		if(m.getType().equals(MessageType.RECEIVE_ROOM_AVAILABLE)){
			roomPicker.removeAllItems();
			ArrayList<Room> rooms = (ArrayList<Room>) m.getData();
			for(Room r : rooms){
				roomPicker.addItem(r);
			}
		}
	}
}
