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
		if(sidePanel == null){
		sidePanel = new SidePanel(this);
		sidePanel.setBounds(0, 0, 180, 700);
		add(sidePanel);
		}
		removeAllComponents();
		
		calendarPanel = new Calendar();
		calendarPanel.setBounds(sidePanel.getX() + sidePanel.getWidth(), 0, 900, 700);
		
		ServerData.addMessageListener(calendarPanel);
		
		
		
		add(calendarPanel);
		
	}
	
	public void goToMessages(){
		removeAllComponents();
		
		messagePanel = new Message(this);
		messagePanel.setBounds(sidePanel.getX() + sidePanel.getWidth(), 0, 900, 700);
		

		add(messagePanel);
		
		frame.repaint();
		
	}
	
	public void goToNewAppointment(){

		removeAllComponents();
		newAppointmentPanel = new NewAppointment(this);
		newAppointmentPanel.setBounds(sidePanel.getX() + sidePanel.getWidth(), 0, 900, 700);
		
		
		add(newAppointmentPanel);
		frame.repaint();
	}
	
	public void goToNewMeeting(){

		removeAllComponents();
		newMeetingPanel = new NewMeeting(this);
		newMeetingPanel.setBounds(sidePanel.getX()+ sidePanel.getWidth(), 0, 900, 700);
		
		
		add(newMeetingPanel);
		frame.repaint();
	}
	
	public void goToMyAppointments(){

		removeAllComponents();
		myAppointments = new Appointments(this);
		myAppointments.setBounds(sidePanel.getX() + sidePanel.getWidth(), 0, 900, 700);
		

		add(myAppointments);
		frame.repaint();
	}
	
	public void logout(){

		removeAll();
		client.showLogin();

	}
	
	public void removeAllComponents(){
		if(calendarPanel != null){
			remove(calendarPanel);
		}
		if(messagePanel != null){
			remove(messagePanel);
		}
		if(newAppointmentPanel!= null){
			remove(newAppointmentPanel);
		}
		if(newMeetingPanel != null){
			remove(newMeetingPanel);
		}
		if(myAppointments != null){
			remove(myAppointments);
		}
	}
	
	
	
	
}
