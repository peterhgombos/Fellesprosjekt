package client.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import client.Client;
import client.connection.ServerData;

@SuppressWarnings("serial")
public class CalendarPanel extends JPanel {

	private Client client;
	
	private SidePanel sidePanel;
	private Calendar calendarPanel;
	private Message messagePanel;
	private NewAppointment newAppointmentPanel;
	private NewMeeting newMeetingPanel;
	private Appointments myAppointments;
	private LogIn login;
	
	private JFrame frame;
	
	public CalendarPanel(Client client){
		this.client = client;
		this.frame = client.getFrame();
		
		setLayout(null);
		
		goToCalender();
	}
	
	public void goToCalender(){
		sidePanel = new SidePanel(this);
		sidePanel.setBounds(0, 0, 180, 700);
		
		calendarPanel = new Calendar();
		calendarPanel.setBounds(sidePanel.getX() + sidePanel.getWidth(), 0, 900, 700);
		
		ServerData.addMessageListener(calendarPanel);
		
		removeAll();
		add(sidePanel);
		add(calendarPanel);
	}
	
	public void goToMessages(){
		
		sidePanel = new SidePanel(this);
		sidePanel.setBounds(0, 0, 180, 700);
		
		messagePanel = new Message(this);
		messagePanel.setBounds(sidePanel.getX() + sidePanel.getWidth(), 0, 900, 700);
		
		removeAll();
		add(sidePanel);
		add(messagePanel);
		
		frame.repaint();
		
	}
	
	public void goToNewAppointment(){
		sidePanel = new SidePanel(this);
		sidePanel.setBounds(0, 0, 180, 700);
		
		newAppointmentPanel = new NewAppointment();
		newAppointmentPanel.setBounds(sidePanel.getX() + sidePanel.getWidth(), 0, 900, 700);
		
		removeAll();
		add(sidePanel);
		add(newAppointmentPanel);
		frame.repaint();
	}
	
	public void goToNewMeeting(){
		sidePanel = new SidePanel(this);
		sidePanel.setBounds(0, 0, 180, 700);
		
		newMeetingPanel = new NewMeeting();
		newMeetingPanel.setBounds(sidePanel.getX()+ sidePanel.getWidth(), 0, 900, 700);
		
		removeAll();
		add(sidePanel);
		add(newMeetingPanel);
		frame.repaint();
	}
	
	public void goToMyAppointments(){
		sidePanel = new SidePanel(this);
		sidePanel.setBounds(0, 0, 180, 700);
		
		myAppointments = new Appointments();
		myAppointments.setBounds(sidePanel.getX() + sidePanel.getWidth(), 0, 900, 700);
		
		removeAll();
		add(sidePanel);
		add(myAppointments);
		frame.repaint();
	}
	
	public void logout(){

		removeAll();
		client.showLogin();

	}
	
	
	
	
}
