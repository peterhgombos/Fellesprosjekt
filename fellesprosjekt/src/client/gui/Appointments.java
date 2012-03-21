package client.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.*;
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

import com.toedter.calendar.JDateChooser;


public class Appointments extends JPanel{
	
	private JDateChooser datepickerFromDate;
	private JDateChooser datepickerToDate;
	
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
	
	private CalendarPanel calendarpanel;
	
	
	public Appointments(CalendarPanel calendarPanel){
		
		//ArrayList<Appointment> appointmentArrayList = new ArrayList<Appointment>();
		calendarpanel = calendarPanel;
		
		datepickerFromDate = new JDateChooser();
		datepickerToDate = new JDateChooser();
		
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
		toCalendarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calendarpanel.goToCalender();
			}
		});
		
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
		add(datepickerFromDate);
		add(datepickerToDate);
		

		setLayout(null);
		resize();
		
	}
	
	public void resize(){
		
		headlineLabel.setBounds(GuiConstants.DISTANCE*27, GuiConstants.DISTANCE, GuiConstants.HEADLINE_LENGTH, GuiConstants.HEADLINE_HEIGTH);
		headlineLabel.setFont(new Font(headlineLabel.getFont().getName(), 0, 30));
		
		startDateLabel.setBounds(GuiConstants.DISTANCE*5, headlineLabel.getHeight() + GuiConstants.DISTANCE*5 , 25, GuiConstants.LABEL_HEIGTH);
		startDateLabel.setFont(new Font(startDateLabel.getFont().getName(), 0, 16));
		
		datepickerFromDate.setBounds(startDateLabel.getX() + startDateLabel.getWidth() + GuiConstants.DISTANCE, startDateLabel.getY(), 160, GuiConstants.TEXTFIELD_HEIGTH);
		
		endDateLabel.setBounds(datepickerFromDate.getX() + datepickerFromDate.getWidth() + GuiConstants.DISTANCE, datepickerFromDate.getY(), 25, GuiConstants.LABEL_HEIGTH);
		endDateLabel.setFont(new Font(endDateLabel.getFont().getName(), 0, 16));
		
		datepickerToDate.setBounds(endDateLabel.getX() + endDateLabel.getWidth() + GuiConstants.DISTANCE, endDateLabel.getY(), 160, GuiConstants.TEXTFIELD_HEIGTH);
				
		appointmentCheckBox.setBounds(datepickerFromDate.getX(), startDateLabel.getY() + startDateLabel.getHeight() + GuiConstants.GROUP_DISTANCE, 150, 30);
		meetingCheckBox.setBounds(appointmentCheckBox.getX() + appointmentCheckBox.getWidth() + GuiConstants.DISTANCE, appointmentCheckBox.getY(), 130, 30);
		
		list.setBounds(appointmentCheckBox.getX(), appointmentCheckBox.getY() + appointmentCheckBox.getHeight() + GuiConstants.DISTANCE, 
		datepickerToDate.getX() + startDateLabel.getX() + 25 , 200);
		
		listScrollPane.setBounds(appointmentCheckBox.getX(), appointmentCheckBox.getY() + appointmentCheckBox.getHeight() + GuiConstants.DISTANCE, 
		datepickerToDate.getX() + startDateLabel.getX() + 25 , 200);
		
		toCalendarButton.setBounds(list.getX(), list.getY() + GuiConstants.GROUP_DISTANCE + list.getHeight(), 140, GuiConstants.BUTTON_HEIGTH);
		
		System.out.println(datepickerToDate.getX() + " " + datepickerToDate.getWidth() + " " + list.getX() );
		
	}

}
