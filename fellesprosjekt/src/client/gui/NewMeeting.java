package client.gui;

import java.awt.Font;


import javax.swing.ButtonGroup;
import javax.swing.JButton;
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

public class NewMeeting extends JPanel{
	
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
	private JTextField dateField; //DATEPICKER
	private JTextField placeField;
	private JTextArea descriptionArea;
	
	private JComboBox startTimeHoursField;
	private JComboBox endTimeHoursField;
	private JComboBox startTimeMinField;
	private JComboBox endTimeMinField;
	private JComboBox roomPicker;
	
	private JRadioButton bookMeetingroomRadioButton;
	private JRadioButton otherPlaceRadioButton;
	
	private JButton addParticipantsButton;
	private JButton saveButton;
	private JButton cancelButton;
	private ButtonGroup radioButtonGroup;

	public NewMeeting(){
		String[] min = {"00", "15", "30", "45"};
		String[] hours= {"00","01","02","03","04","05","06","07","08","09","10",
						"11","12","13","14","15","16","17","18","19","20","21","22","23"};

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
		dateField = new JTextField();
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
		
		addParticipantsButton = new JButton("Legg til/fjern");
		saveButton = new JButton("Lagre");
		cancelButton = new JButton("Avbryt");
		
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
		add(dateField);
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
		
		setLayout(null);
		resize();
		
	}
	
	public void resize(){
		GuiConstants guiConstants = new GuiConstants();
		
		headlineLabel.setBounds(guiConstants.getDistance()*27, guiConstants.getDistance(), 300, 40);
		headlineLabel.setFont(new Font(headlineLabel.getFont().getName(), 0, 30));
		
		titleLabel.setBounds(guiConstants.getDistance(), headlineLabel.getY() + headlineLabel.getHeight() + guiConstants.getGroupDistance(), 100, 25);
		titleLabel.setFont(new Font(headlineLabel.getFont().getName(), 0, 16));
		
		nameField.setBounds(guiConstants.getDistance()*2+ titleLabel.getWidth() + titleLabel.getX(), titleLabel.getY(), 160, 25);
		
		dateLabel.setBounds(titleLabel.getX(), titleLabel.getY() + titleLabel.getHeight() + guiConstants.getDistance(), 100, 25);
		dateLabel.setFont(new Font(dateLabel.getFont().getName(), 0, 16));
		
		dateField.setBounds(guiConstants.getDistance()*2 + titleLabel.getWidth() + titleLabel.getX(), dateLabel.getY(), 160, 25);
		
		startTimeLabel.setBounds(titleLabel.getX(), dateLabel.getY() + dateLabel.getHeight() + guiConstants.getDistance(), 100, 25);
		startTimeLabel.setFont(new Font(startTimeLabel.getFont().getName(), 0, 16));
		
		startTimeHoursField.setBounds(guiConstants.getDistance()*2+ titleLabel.getWidth() + titleLabel.getX() , startTimeLabel.getY(), 70, 30);
		startTimeMinField.setBounds(startTimeHoursField.getX() + 2 + startTimeHoursField.getWidth(), startTimeHoursField.getY(), 70, 30);
		
		endTimeLabel.setBounds(startTimeHoursField.getX() + startTimeMinField.getWidth() + guiConstants.getDistance(), startTimeHoursField.getY(), 100, 25);
		endTimeLabel.setFont(new Font(endTimeLabel.getFont().getName(), 0, 16));
		
		endTimeHoursField.setBounds(endTimeLabel.getX() + endTimeLabel.getWidth() + guiConstants.getDistance(), endTimeLabel.getY(), 70, 30);
		endTimeMinField.setBounds(endTimeHoursField.getX() + 2 + endTimeHoursField.getWidth(), endTimeHoursField.getY(), 70, 30);
		
		descriptionLabel.setBounds(titleLabel.getX(), startTimeLabel.getY() + startTimeLabel.getHeight() + guiConstants.getGroupDistance(), 100, 25);
		descriptionLabel.setFont(new Font(descriptionLabel.getFont().getName(), 0, 16));
		
		scrollPane.setBounds(guiConstants.getDistance()*2+ titleLabel.getWidth() + titleLabel.getX(), descriptionLabel.getY(), 200, 100);
		
		participantsLabel.setBounds(titleLabel.getX(), scrollPane.getY() + scrollPane.getHeight() + guiConstants.getGroupDistance(), 100, 25);
		participantsLabel.setFont(new Font(participantsLabel.getFont().getName(), 0, 16));
		
		addParticipantsButton.setBounds(guiConstants.getDistance()*2+ titleLabel.getWidth() + titleLabel.getX(), participantsLabel.getY(), 160, 35);
		
		placeLabel.setBounds(titleLabel.getX(), participantsLabel.getY() + participantsLabel.getHeight() + guiConstants.getGroupDistance(), 100, 25);
		placeLabel.setFont(new Font(placeLabel.getFont().getName(), 0, 16));
		
		bookMeetingroomRadioButton.setBounds(guiConstants.getDistance()*2+ titleLabel.getWidth() + titleLabel.getX(), placeLabel.getY(), 120, 25);
		roomPicker.setBounds(bookMeetingroomRadioButton.getX() + bookMeetingroomRadioButton.getWidth() + guiConstants.getDistance(), placeLabel.getY(), 80, 25);
		roomInformationLabel.setBounds(roomPicker.getX() + roomPicker.getWidth() + 10, placeLabel.getY(), 160, 25);
		
		otherPlaceRadioButton.setBounds(bookMeetingroomRadioButton.getX(), placeLabel.getY()+ guiConstants.getDistance() + 20, 120, 25);
		placeField.setBounds(otherPlaceRadioButton.getX() + otherPlaceRadioButton.getWidth() + guiConstants.getDistance(), placeLabel.getY() + guiConstants.getDistance() + 20 , 160, 25);
		
		saveButton.setBounds(otherPlaceRadioButton.getX(), otherPlaceRadioButton.getY() + otherPlaceRadioButton.getHeight() + guiConstants.getDistance(), 100, 25);
		saveButton.setFont(new Font(saveButton.getFont().getName(), 0, 14));
		
		cancelButton.setBounds(saveButton.getX() + saveButton.getWidth() + guiConstants.getDistance(), saveButton.getY(), 100, 25);
	
		}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("NewMeeting");
		NewMeeting meeting = new NewMeeting();
		frame.add(meeting);
		frame.getContentPane();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setSize(700, 700);
		frame.setVisible(true);
	}
	
	

}
