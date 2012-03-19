package gui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

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
	
	private JComboBox<Integer> startTimeHoursField;
	private JComboBox<Integer> endTimeHoursField;
	private JComboBox<Integer> startTimeMinField;
	private JComboBox<Integer> endTimeMinField;
	private JComboBox<String> roomPicker;
	
	private JRadioButton bookMeetingroomRadioButton;
	private JRadioButton otherPlaceRadioButton;
	
	private JButton addParticipantsButton;
	private JButton saveButton;
	private JButton cancelButton;

	public NewMeeting(){
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
		
		startTimeHoursField = new JComboBox<Integer>();
		endTimeHoursField = new JComboBox<Integer>();
		startTimeMinField = new JComboBox<Integer>();
		endTimeMinField = new JComboBox<Integer>();
		
		bookMeetingroomRadioButton = new JRadioButton();
		otherPlaceRadioButton = new JRadioButton();
		
		addParticipantsButton = new JButton("Legg til/fjern");
		saveButton = new JButton("Lagre");
		cancelButton = new JButton("Avbryt");
		
		
	}
	

}
