package client.gui;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

import common.dataobjects.Appointment;


public class Appointments extends JPanel{
	
	private JLabel headlineLabel;
	private JLabel dateLabel;
	private JLabel startDateLabel;
	private JLabel endDateLabel;
	
	private JCheckBox appointmentCheckBox;
	private JCheckBox meetingCheckBox;
	
	private JTextField startDateField;
	private JTextField endDateField;
		
	private JList list;
	private DefaultListModel listModel;
	
	private JButton toCalendarButton;
	
	private JScrollPane listScrollPane;
	
	public Appointments(){
		
		ArrayList<Appointment> appointmentArrayList = new ArrayList<Appointment>();
		
		headlineLabel = new JLabel("Mine Avtaler");
		dateLabel = new JLabel("Dato:");
		startDateLabel = new JLabel("Fra"); 	//DATEPICKER
		endDateLabel = new JLabel("Til");		//DATEPICKER
		
		appointmentCheckBox = new JCheckBox("Personlige Avtaler");
		meetingCheckBox = new JCheckBox("Møter");
		
		startDateField = new JTextField();
		endDateField = new JTextField();
		
		String[]listeTest = {"Hei","Hei","Hei","Hei","Hei","Hei","Hei","Hei","Hei","Hei","Hei","Hei","Hei","Hei","Hei","Hei","Hei","Hei"};
		listModel = new DefaultListModel();
		list = new JList(listeTest);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //list.setSelectedIndex(0);
        //Add listener
        //list.setVisibleRowCount(5);
        listScrollPane = new JScrollPane(list);
		
		toCalendarButton = new JButton("Til Kalender");
		
		
		add(headlineLabel);
		add(dateLabel);
		add(startDateLabel);
		add(endDateLabel);
		add(appointmentCheckBox);
		add(meetingCheckBox);
		add(startDateField);
		add(endDateField);
		add(toCalendarButton);
		add(listScrollPane);

		setLayout(null);
		resize();
		
	}
	
	public void resize(){
		headlineLabel.setBounds(GuiConstants.DISTANCE*27, GuiConstants.DISTANCE, 300, 40);
		headlineLabel.setFont(new Font(headlineLabel.getFont().getName(), 0, 30));
		
		dateLabel.setBounds(GuiConstants.DISTANCE + 50, headlineLabel.getY() + headlineLabel.getHeight() + GuiConstants.GROUP_DISTANCE, 70, 30);
		dateLabel.setFont(new Font(dateLabel.getFont().getName(), 0, 16));
		
		startDateLabel.setBounds(GuiConstants.DISTANCE + 50, dateLabel.getX() + dateLabel.getWidth(), 25, 35);
		startDateLabel.setFont(new Font(startDateLabel.getFont().getName(), 0, 16));
		
		startDateField.setBounds(startDateLabel.getX() + GuiConstants.GROUP_DISTANCE, startDateLabel.getY(), 160, 35);
		
		endDateLabel.setBounds(startDateField.getX() + startDateField.getWidth() + GuiConstants.DISTANCE, startDateField.getY(), 25, 30);
		endDateLabel.setFont(new Font(endDateLabel.getFont().getName(), 0, 16));
		
		endDateField.setBounds(endDateLabel.getX() + endDateLabel.getWidth() + GuiConstants.DISTANCE, endDateLabel.getY(), 160, 35);
		
		appointmentCheckBox.setBounds(startDateField.getX(), startDateLabel.getY() + startDateLabel.getHeight() + GuiConstants.DISTANCE*2, 130, 30);
		meetingCheckBox.setBounds(appointmentCheckBox.getX() + appointmentCheckBox.getWidth() + GuiConstants.DISTANCE, appointmentCheckBox.getY(), 130, 30);
		
		list.setBounds(appointmentCheckBox.getX(), appointmentCheckBox.getY() + appointmentCheckBox.getHeight() + GuiConstants.DISTANCE, 
		endDateField.getX() + startDateLabel.getX() , 200);
		
		listScrollPane.setBounds(appointmentCheckBox.getX(), appointmentCheckBox.getY() + appointmentCheckBox.getHeight() + GuiConstants.DISTANCE, 
		endDateField.getX() + startDateLabel.getX() , 200);
		
		toCalendarButton.setBounds(list.getX(), list.getY() + GuiConstants.DISTANCE + list.getHeight(), 160, 35);
		
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Appointments");
		Appointments appointments = new Appointments();
		frame.add(appointments);
		frame.getContentPane();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setSize(700, 700);
		frame.setVisible(true);
	}
	
}