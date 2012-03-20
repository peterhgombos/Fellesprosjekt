package gui;

import java.awt.BorderLayout;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

import dataobjects.Appointment;

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
	
	public Appointments(){
		
		ArrayList<Appointment> appointmentArrayList = new ArrayList<Appointment>();
		
		headlineLabel = new JLabel("Mine Avtaler");
		dateLabel = new JLabel("Dato");
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
        list.setSelectedIndex(0);
        //Add listener
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);
        
		
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
		add(list);

		setLayout(null);
		resize();
		
	}
	
	public void resize(){
		GuiConstants guiConstants = new GuiConstants();
		
		headlineLabel.setBounds(guiConstants.getDistance()*27, guiConstants.getDistance(), 300, 40);
		headlineLabel.setFont(new Font(headlineLabel.getFont().getName(), 0, 30));
		
		dateLabel.setBounds(guiConstants.getDistance() + 50, headlineLabel.getY() + headlineLabel.getHeight() + guiConstants.getGroupDistance(), 70, 30);
		dateLabel.setFont(new Font(dateLabel.getFont().getName(), 0, 16));
		
		startDateLabel.setBounds(guiConstants.getDistance() + 50, dateLabel.getX() + dateLabel.getWidth(), 25, 35);
		startDateLabel.setFont(new Font(startDateLabel.getFont().getName(), 0, 16));
		
		startDateField.setBounds(startDateLabel.getX() + guiConstants.getGroupDistance(), startDateLabel.getY(), 160, 35);
		
		endDateLabel.setBounds(startDateField.getX() + startDateField.getWidth() + guiConstants.getDistance(), startDateField.getY(), 25, 30);
		endDateLabel.setFont(new Font(endDateLabel.getFont().getName(), 0, 16));
		
		endDateField.setBounds(endDateLabel.getX() + endDateLabel.getWidth() + guiConstants.getDistance(), endDateLabel.getY(), 160, 35);
		
		appointmentCheckBox.setBounds(startDateField.getX(), startDateLabel.getY() + startDateLabel.getHeight(), 130, 30);
		meetingCheckBox.setBounds(appointmentCheckBox.getX() + appointmentCheckBox.getWidth() + guiConstants.getDistance(), appointmentCheckBox.getY(), 130, 30);
		
		list.setBounds(appointmentCheckBox.getX(), appointmentCheckBox.getY() + appointmentCheckBox.getHeight() + guiConstants.getDistance(), 
		endDateField.getX() + startDateLabel.getX() , 200);
		
		toCalendarButton.setBounds(list.getX(), list.getY() + guiConstants.getDistance() + list.getHeight(), 160, 35);
		
		
//		System.out.println("headline X: " + headlineLabel.getX() + " headline Y: " + headlineLabel.getY());
//		System.out.println("Datelabel X: " + dateLabel.getX() + " datelabel Y: " + dateLabel.getY());
//		System.out.println("StartDateLabel X: " + startDateLabel.getX() + " StartDateLabel Y: " + startDateLabel.getY());
//		System.out.println("StartDateField X: " + startDateField.getX() + " StartDateField Y: " + startDateField.getY());
//		System.out.println("endDateLabel X: " + endDateLabel.getX() + " endDateLabel Y: " + endDateLabel.getY());
//		System.out.println("endDateField X: " + endDateField.getX() + " endDateField Y: " + endDateField.getY());
//		
//		
//		System.out.println("Fra width: " + startDateLabel.getWidth() + " Til width: " + endDateLabel.getWidth());
//		System.out.println("DateField width: " + startDateField.getWidth() + " EndDateField width: " + endDateField.getWidth());
//		System.out.println("appointmentCheckBox X: " + appointmentCheckBox.getX() + " AppointmentCheckBox Y: " + appointmentCheckBox.getY());
//		System.out.println("meetingCheckBox X: " + meetingCheckBox.getX() + " meetingChecBox Y: " + meetingCheckBox.getY());
//		
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
