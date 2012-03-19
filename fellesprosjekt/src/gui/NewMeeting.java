package gui;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
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
	
	private JTextField nameField; 
	private JTextField dateField; //DATEPICKER
	private JTextField placeField;
	private JTextArea descriptionArea;
	
	private JComboBox<String> startTimeHoursField;
	private JComboBox<String> endTimeHoursField;
	private JComboBox<String> startTimeMinField;
	private JComboBox<String> endTimeMinField;
	private JComboBox<String> roomPicker;
	
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
		titleLabel = new JLabel("Tittel");
		dateLabel = new JLabel("Dato");
		startTimeLabel = new JLabel("Fra");
		endTimeLabel = new JLabel("Til");
		descriptionLabel = new JLabel("Beskrivelse");
		participantsLabel = new JLabel("Deltakere");
		placeLabel = new JLabel("Sted");
		roomInformationLabel =  new JLabel("(min 2 deltakere)");
		
		nameField = new JTextField();
		dateField = new JTextField();
		placeField = new JTextField();
		descriptionArea = new JTextArea();
		
		
		bookMeetingroomRadioButton = new JRadioButton();
		otherPlaceRadioButton = new JRadioButton();
		radioButtonGroup = new ButtonGroup();
		radioButtonGroup.add(bookMeetingroomRadioButton);
		radioButtonGroup.add(otherPlaceRadioButton);
		
		addParticipantsButton = new JButton("Legg til/fjern");
		saveButton = new JButton("Lagre");
		cancelButton = new JButton("Avbryt");
		
		startTimeHoursField = new JComboBox<String>(hours);
		endTimeHoursField = new JComboBox<String>(min);
		startTimeMinField = new JComboBox<String>(hours);
		endTimeMinField = new JComboBox<String>(min);
		
		add(headlineLabel);
		add(titleLabel);
//		add(dateLabel);
//		add(startTimeLabel);
//		add(endTimeLabel);
//		add(descriptionLabel);
//		add(participantsLabel);
//		add(placeLabel);
//		add(roomInformationLabel);
//		add(nameField);
//		add(dateField);
//		add(placeField);
//		add(descriptionArea);
//		add(bookMeetingroomRadioButton);
//		add(otherPlaceRadioButton);
//		add(addParticipantsButton);
//		add(saveButton);
//		add(cancelButton);
//		add(startTimeHoursField);
//		add(startTimeMinField);
//		add(endTimeHoursField);
//		add(endTimeMinField);
		
		resize();
		
	}
	
	public void resize(){
		GuiConstants guiConstants = new GuiConstants();
		
		headlineLabel.setBounds(guiConstants.getDistance()*5, guiConstants.getDistance()*5, 100, 100);
		headlineLabel.setFont(new Font(headlineLabel.getFont().getName(), 0, 30));
		

		titleLabel.setBounds(guiConstants.getDistance(), headlineLabel.getY() + headlineLabel.getHeight() + guiConstants.getGroupDistance(), 100, 25);
		

		System.out.println("gui dis: " + guiConstants.getDistance());
		System.out.println("headline x: " + headlineLabel.getX());
		System.out.println("headline y:" + headlineLabel.getY());
		System.out.println("Y: " + headlineLabel.getY() + headlineLabel.getHeight() + guiConstants.getGroupDistance());
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
