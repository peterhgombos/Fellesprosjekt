package gui;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class Sidepanel extends JPanel implements FocusListener{
	
	private JButton message;
	private JButton newAppointment;
	private JButton newMeeting;
	private JButton myAppointments;
	private JButton employeesAppointments;
	private JButton logOut;
	private GuiConstants guiConstants;
	private JButton addEmployee;
	private JList employeeList;
	private JList selectedEmployeeList;
	private JTextField search;
	
	public Sidepanel() {
		message = new JButton("Meldinger");
		newAppointment = new JButton("Ny Avtale");
		newMeeting = new JButton("Nytt m�te");
		myAppointments = new JButton("Mine Avtaler");
		employeesAppointments = new JButton("Ansattes Avtaler");
		logOut = new JButton("Logg Ut");
		
		addEmployee = new JButton("Legg Til");
		guiConstants = new GuiConstants();
		employeeList = new JList();
		selectedEmployeeList = new JList();
		search = new JTextField();
		setLayout(null);
		resize();
		
		add(message);
		add(newAppointment);
		add(newMeeting);
		add(myAppointments);
		add(employeesAppointments);
		add(logOut);
		add(addEmployee);
		add(employeeList);
		add(selectedEmployeeList);
		add(search);
	}
	
	public void resize(){
		message.setBounds(guiConstants.getDistance(), guiConstants.getDistance(), 160, 35);
		message.setFont(guiConstants.getJButtonFont());
		
		newAppointment.setBounds(guiConstants.getDistance(), message.getY() + message.getHeight()+
				guiConstants.getGroupDistance(), 
				message.getWidth(), message.getHeight());
		newAppointment.setFont(guiConstants.getJButtonFont());
		
		
		newMeeting.setBounds(guiConstants.getDistance(), newAppointment.getY() + guiConstants.getDistance()
				+ message.getHeight(), message.getWidth(), message.getHeight());
		newMeeting.setFont(guiConstants.getJButtonFont());

		
		myAppointments.setBounds(guiConstants.getDistance(), newMeeting.getY() + guiConstants.getDistance() + message.getHeight(), message.getWidth(), message.getHeight());
		myAppointments.setFont(guiConstants.getJButtonFont());
		
		
		employeesAppointments.setBounds(guiConstants.getDistance(), myAppointments.getY() + guiConstants.getDistance() + message.getHeight(), message.getWidth(), message.getHeight());
		employeesAppointments.setFont(guiConstants.getJButtonFont());
		
		
		search.setBounds(guiConstants.getDistance(), employeesAppointments.getY() + guiConstants.getGroupDistance() + message.getHeight(), message.getWidth(), message.getHeight());
		search.setText("Søk");
		search.addFocusListener(this);
		
		
		employeeList.setBounds(guiConstants.getDistance(), search.getY() + 2 + message.getHeight(), message.getWidth(), message.getHeight());
		
				
		addEmployee.setBounds(guiConstants.getDistance(), employeeList.getY() + guiConstants.getDistance() + employeeList.getHeight(), message.getWidth()/2, message.getHeight());
		
		
		selectedEmployeeList.setBounds(guiConstants.getDistance(), addEmployee.getY() + guiConstants.getDistance() + message.getHeight(), message.getWidth(), message.getHeight());
		
		
		logOut.setBounds(guiConstants.getDistance(), selectedEmployeeList.getY() + guiConstants.getGroupDistance() + message.getHeight(), message.getWidth(), message.getHeight());
		logOut.setFont(guiConstants.getJButtonFont());
	}
	
	
	public static void main(String[] args) {
		JFrame test = new JFrame();
		Sidepanel s = new Sidepanel();
		test.add(s);
		
		test.getContentPane();
		test.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		test.pack();
		test.setSize(700, 700);
		test.setVisible(true);
	}



	@Override
	public void focusGained(FocusEvent arg0) {
		if (search.getText().equals("Søk")) {
			search.setText("");
		}
		
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		if (!search.getText().equals("Søk")) {
			search.setText("Søk");
		}
		
	}
}