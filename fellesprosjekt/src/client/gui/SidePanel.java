package client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collection;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import client.Client;
import client.connection.MessageListener;
import client.connection.ServerData;
import client.gui.calendar.ColorPicker;

import common.dataobjects.ComMessage;
import common.dataobjects.Person;
import common.utilities.MessageType;

@SuppressWarnings("serial")
public class SidePanel extends JPanel implements FocusListener, MessageListener{
	
	private JButton message;
	private JButton newAppointment;
	private JButton newMeeting;
	private JButton myAppointments;
	private JButton employeesAppointments;
	private JButton logOut;
	private JButton addEmployee;
	
	private JList employeeList;
	private JList selectedEmployeeList;
	
	private JTextField search;
	private JScrollPane employeeListScroll;
	private JScrollPane scrollSelectedEmployee;
	private CalendarPanel calendarpanel;
	
	private DefaultListModel employeeListModel;
	private DefaultListModel selectedEmployeeListModel;
	
	//private SidePanel thisSidepanel;
	
	public SidePanel(CalendarPanel calendarPanel) {
		calendarpanel = calendarPanel;
		//thisSidepanel = this;
		
		message = new JButton("Meldinger");
		message.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				calendarpanel.goToMessages();
				message.setForeground(Color.BLACK);
				message.setFont(new Font(message.getFont().getName(), 0, message.getFont().getSize()));
				
			}
		});
		newAppointment = new JButton("Ny Avtale");
		newAppointment.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				calendarpanel.goToNewAppointment();
			}
		});
		newMeeting = new JButton("Nytt Møte");
		newMeeting.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				calendarpanel.goToNewMeeting();
			}
		});
		myAppointments = new JButton("Mine Avtaler");
		myAppointments.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				calendarpanel.goToMyAppointments();
			}
		});
		employeesAppointments = new JButton("Ansattes Avtaler");
		employeesAppointments.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// calendarPanel
				
			}
		});
		logOut = new JButton("Logg Ut");
		logOut.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				calendarpanel.logout();
			}
		});
		
		addEmployee = new JButton("Legg Til Kalender");
		addEmployee.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Object[] personList = employeeList.getSelectedValues();
				if(personList == null){
					return;
				}
				for (Object object : personList) {
					if(object != null){
						selectedEmployeeListModel.addElement((Person)object);
						employeeListModel.removeElement((Person)object);
					}
				}
				
				ColorPicker.reset();
				ServerData.getCalendar().reset();
				ServerData.resetMeetingsAndAppointments();
				ServerData.requestAppointmentsAndMeetings(Client.getUser());
				for(int i = 0; i < selectedEmployeeListModel.size(); i++){
					ServerData.requestAppointmentsAndMeetings(((Person)selectedEmployeeListModel.getElementAt(i)));
				}
			}
		});
		
		employeeList = new JList(new DefaultListModel());
		employeeList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		employeeListScroll = new JScrollPane(employeeList);

		selectedEmployeeList = new JList(new DefaultListModel());
		selectedEmployeeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		selectedEmployeeList.setCellRenderer(new EmployeeListCellRenderer());
		scrollSelectedEmployee = new JScrollPane(selectedEmployeeList);
		
		selectedEmployeeListModel = (DefaultListModel)selectedEmployeeList.getModel();
		employeeListModel = (DefaultListModel)employeeList.getModel();
		
		
		selectedEmployeeList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Person person = (Person)selectedEmployeeList.getSelectedValue();
				if(person != null){
					selectedEmployeeListModel.removeElement(person);
					employeeListModel.addElement(person);					
					
					ColorPicker.reset();
					ServerData.getCalendar().reset();
					ServerData.resetMeetingsAndAppointments();
					ServerData.requestAppointmentsAndMeetings(Client.getUser());
					for(int i = 0; i < selectedEmployeeListModel.size(); i++){
						ServerData.requestAppointmentsAndMeetings(((Person)selectedEmployeeListModel.getElementAt(i)));
					}
				}
			}
		});
		
		search = new JTextField();
		search.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				ServerData.requestSearchForPerson(search.getText());
			}
			public void keyPressed(KeyEvent e) {}
		});
		
		ServerData.addMessageListener(this);
		ServerData.requestSearchForPerson("");
		
		setLayout(null);
		resize();

		add(message);
		add(newAppointment);
		add(newMeeting);
		add(myAppointments);
		add(logOut);
		add(addEmployee);
		add(search);
		add(employeeListScroll);
		add(scrollSelectedEmployee);
	}
	
	public void resize(){
		message.setBounds(GuiConstants.DISTANCE, GuiConstants.DISTANCE, 160, 35);
		message.setFont(GuiConstants.FONT_14);
		
		newAppointment.setBounds(GuiConstants.DISTANCE, message.getY() + message.getHeight()+
				GuiConstants.GROUP_DISTANCE+5, 
				message.getWidth(), message.getHeight());
		newAppointment.setFont(GuiConstants.FONT_14);
		
		
		newMeeting.setBounds(GuiConstants.DISTANCE, newAppointment.getY() + GuiConstants.DISTANCE
				+ message.getHeight(), message.getWidth(), message.getHeight());
		newMeeting.setFont(GuiConstants.FONT_14);

		
		myAppointments.setBounds(GuiConstants.DISTANCE, newMeeting.getY() + GuiConstants.DISTANCE + message.getHeight(), message.getWidth(), message.getHeight());
		myAppointments.setFont(GuiConstants.FONT_14);
		
		
		employeesAppointments.setBounds(GuiConstants.DISTANCE, myAppointments.getY() + GuiConstants.DISTANCE + message.getHeight(), message.getWidth(), message.getHeight());
		employeesAppointments.setFont(GuiConstants.FONT_14);
		
		
		search.setBounds(GuiConstants.DISTANCE, employeesAppointments.getY() + message.getHeight(), message.getWidth(), message.getHeight());
		search.setText("Søk");
		search.addFocusListener(this);
		
		employeeListScroll.setBounds(GuiConstants.DISTANCE, search.getY() +2 + message.getHeight(), message.getWidth(), message.getHeight()*5);
		
		employeeList.setBounds(GuiConstants.DISTANCE, search.getY() + 2 + message.getHeight(), message.getWidth()-20, employeeList.getHeight());
		employeeList.setPreferredSize(employeeList.getSize());
				
		addEmployee.setBounds(GuiConstants.DISTANCE, employeeListScroll.getY() + GuiConstants.DISTANCE + employeeListScroll.getHeight(), message.getWidth(), message.getHeight());
		
		scrollSelectedEmployee.setBounds(GuiConstants.DISTANCE, addEmployee.getY() + GuiConstants.DISTANCE + message.getHeight(), message.getWidth(), message.getHeight()*4);
		
		logOut.setBounds(GuiConstants.DISTANCE, scrollSelectedEmployee.getY() + scrollSelectedEmployee.getHeight() + GuiConstants.DISTANCE, message.getWidth(), message.getHeight());
		logOut.setFont(GuiConstants.FONT_14);
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		if (search.getText().equals("Søk")) {
			search.setText("");
		}
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		if (!search.getText().equals("Søk")) {
			search.setText("Søk");
		}
	}
	
	private boolean personExistInSelectedEmployeeList(Person p){
		for (int i = 0; i < selectedEmployeeList.getModel().getSize(); i++) {
			Person person = (Person)selectedEmployeeListModel.getElementAt(i);
			if (p.getPersonID() == person.getPersonID()){
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void receiveMessage(ComMessage m) {
		if(m.getType().equals(MessageType.RECEIVE_SEARCH_PERSON)){
			employeeListModel.clear();
			Collection<Person> persons = (Collection<Person>)m.getData();
			for (Person person : persons) {
				if (!personExistInSelectedEmployeeList(person) && person.getPersonID() != Client.getUser().getPersonID()){
					employeeListModel.addElement(person);
				}
			}
		}
		else if (m.getType().equals(MessageType.WARNING)) {
			System.out.println("WARNING");
			message.setFont(new Font(message.getFont().getName(), Font.BOLD, message.getFont().getSize()));
			message.setForeground(Color.RED);
		}
	}
}
