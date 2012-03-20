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

public class NewAppointment extends JPanel{
	
	private JLabel headlineLabel;
	private JLabel titleLabel;
	private JLabel dateLabel;
	private JLabel startTimeLabel;
	private JLabel endTimeLabel;
	private JLabel descriptionLabel;
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
	
	private JButton saveButton;
	private JButton cancelButton;

	public NewAppointment(){
		String[] min = {"00", "15", "30", "45"};
		String[] hours= {"00","01","02","03","04","05","06","07","08","09","10",
						"11","12","13","14","15","16","17","18","19","20","21","22","23"};
		
		headlineLabel = new JLabel("Ny Avtale");
		titleLabel = new JLabel("Tittel", SwingConstants.RIGHT);
		dateLabel = new JLabel("Dato", SwingConstants.RIGHT);
		startTimeLabel = new JLabel("Fra", SwingConstants.RIGHT);
		endTimeLabel = new JLabel("Til", SwingConstants.RIGHT);
		descriptionLabel = new JLabel("Beskrivelse", SwingConstants.RIGHT);
		placeLabel = new JLabel("Sted", SwingConstants.RIGHT);
		roomInformationLabel =  new JLabel("(min 2 deltakere)", SwingConstants.RIGHT);
				
		
		nameField = new JTextField();
		dateField = new JTextField();
		placeField = new JTextField();
		
		descriptionArea = new JTextArea();
		descriptionArea.setLineWrap(true);
		
		saveButton = new JButton("Lagre");
		cancelButton = new JButton("Avbryt");
		
		startTimeHoursField = new JComboBox(hours);
		endTimeHoursField = new JComboBox(hours);
		startTimeMinField = new JComboBox(min);
		endTimeMinField = new JComboBox(min);
		
		scrollPane = new JScrollPane(descriptionArea);
		
		add(scrollPane);
		add(headlineLabel);
		add(titleLabel);
		add(dateLabel);
		add(startTimeLabel);
		add(endTimeLabel);
		add(descriptionLabel);
		add(placeLabel);
		add(roomInformationLabel);
		add(nameField);
		add(dateField);
		add(placeField);
	//	add(descriptionArea);
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
		
		placeLabel.setBounds(titleLabel.getX(), scrollPane.getY() + scrollPane.getHeight() + guiConstants.getGroupDistance(), 100, 25);
		placeLabel.setFont(new Font(placeLabel.getFont().getName(), 0, 16));
		
		placeField.setBounds(placeLabel.getX() + placeLabel.getWidth() + guiConstants.getDistance(), placeLabel.getY(), 160, 25);
		
		saveButton.setBounds(placeField.getX(), placeField.getY() + placeField.getHeight() + guiConstants.getDistance(), 100, 25);
		saveButton.setFont(new Font(saveButton.getFont().getName(), 0, 14));
		
		cancelButton.setBounds(saveButton.getX() + saveButton.getWidth() + guiConstants.getDistance(), saveButton.getY(), 100, 25);
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("NewMeeting");
		NewAppointment appointment = new NewAppointment();
		frame.add(appointment);
		frame.getContentPane();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setSize(700, 700);
		frame.setVisible(true);
	}
	
	
	
}
