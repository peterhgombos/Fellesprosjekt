package gui;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Sidepanel extends JPanel{
	
	private JButton message;
	private JButton newAppointment;
	private JButton newMeeting;
	private JButton myAppointments;
	private JButton employeesAppointments;
	private JButton logOut;
	private GuiConstants guiConstants;
	private JList employeeList;
	private JList selectedEmployeeList;
	
	public Sidepanel() {
		message = new JButton("Meldinger");
		newAppointment = new JButton("Ny Avtale");
		newMeeting = new JButton("Nytt møte");
		myAppointments = new JButton("Mine Avtaler");
		employeesAppointments = new JButton("Ansattes Avtaler");
		logOut = new JButton("Logg Ut");
		guiConstants = new GuiConstants();
		employeeList = new JList();
		selectedEmployeeList = new JList();
		
		
		setLayout(null);
		resize();
		
		add(message);
		add(newAppointment);
		add(newMeeting);
		add(myAppointments);
		add(employeesAppointments);
		add(logOut);
	}
	
	public void resize(){
		message.setBounds(guiConstants.getJButtonDistance(), guiConstants.getJButtonDistance(), 160, 35);
		message.setFont(guiConstants.getJButtonFont());
		
		newAppointment.setBounds(guiConstants.getJButtonDistance(), message.getY() + message.getHeight()+
				guiConstants.getJButtonGroupDistance(), 
				message.getWidth(), message.getHeight());
		newAppointment.setFont(guiConstants.getJButtonFont());
		
		
		newMeeting.setBounds(guiConstants.getJButtonDistance(), newAppointment.getY() + guiConstants.getJButtonDistance()
				+ message.getHeight(), message.getWidth(), message.getHeight());
		newMeeting.setFont(guiConstants.getJButtonFont());

		
		myAppointments.setBounds(guiConstants.getJButtonDistance(), newMeeting.getY() + guiConstants.getJButtonDistance() + message.getHeight(), message.getWidth(), message.getHeight());
		myAppointments.setFont(guiConstants.getJButtonFont());
		
		
		employeesAppointments.setBounds(guiConstants.getJButtonDistance(), myAppointments.getY() + guiConstants.getJButtonDistance() + message.getHeight(), message.getWidth(), message.getHeight());
		employeesAppointments.setFont(guiConstants.getJButtonFont());
		
		
		logOut.setBounds(guiConstants.getJButtonDistance(), employeesAppointments.getY() + guiConstants.getJButtonGroupDistance() + message.getHeight(), message.getWidth(), message.getHeight());
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
}
