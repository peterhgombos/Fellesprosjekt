package client.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import client.connection.MessageListener;
import client.connection.ServerData;

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
	private JScrollPane scroll;
	private JScrollPane scrollSelectedEmployee;
	private CalendarPanel calendarpanel;
	
	private DefaultListModel employeeListModel;
	private DefaultListModel selectedEmployeeListModel;
	
	public SidePanel(CalendarPanel calendarPanel) {
		calendarpanel = calendarPanel;
			
		message = new JButton("Meldinger");
		message.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				calendarpanel.goToMessages();
				
			}
		});
		newAppointment = new JButton("Ny Avtale");
		newAppointment.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				calendarpanel.goToNewAppointment();
			}
		});
		newMeeting = new JButton("Nytt møte");
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
		
		addEmployee = new JButton("Legg Til");
		addEmployee.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Object[] personList = employeeList.getSelectedValues();
				for (Object object : personList) {
					selectedEmployeeListModel.addElement((Person)object);
					employeeListModel.removeElement(object);
				}
			}
		});
		
		employeeList = new JList(new DefaultListModel());
		employeeList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		employeeList.setBackground(Color.WHITE);
		employeeList.setLayout(null);
		
		scroll = new JScrollPane(employeeList);

		selectedEmployeeList = new JList(new DefaultListModel());
		selectedEmployeeList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		selectedEmployeeList.setCellRenderer(new EmployeeListCellRenderer());
		scrollSelectedEmployee = new JScrollPane(selectedEmployeeList);
		
		selectedEmployeeListModel = (DefaultListModel)selectedEmployeeList.getModel();
		employeeListModel = (DefaultListModel)employeeList.getModel();
		
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
		add(scroll);
		add(scrollSelectedEmployee);
	}
	
	public void resize(){
		message.setBounds(GuiConstants.DISTANCE, GuiConstants.DISTANCE, 160, 35);
		message.setFont(GuiConstants.BUTTON_FONT);
		
		newAppointment.setBounds(GuiConstants.DISTANCE, message.getY() + message.getHeight()+
				GuiConstants.GROUP_DISTANCE+5, 
				message.getWidth(), message.getHeight());
		newAppointment.setFont(GuiConstants.BUTTON_FONT);
		
		
		newMeeting.setBounds(GuiConstants.DISTANCE, newAppointment.getY() + GuiConstants.DISTANCE
				+ message.getHeight(), message.getWidth(), message.getHeight());
		newMeeting.setFont(GuiConstants.BUTTON_FONT);

		
		myAppointments.setBounds(GuiConstants.DISTANCE, newMeeting.getY() + GuiConstants.DISTANCE + message.getHeight(), message.getWidth(), message.getHeight());
		myAppointments.setFont(GuiConstants.BUTTON_FONT);
		
		
		employeesAppointments.setBounds(GuiConstants.DISTANCE, myAppointments.getY() + GuiConstants.DISTANCE + message.getHeight(), message.getWidth(), message.getHeight());
		employeesAppointments.setFont(GuiConstants.BUTTON_FONT);
		
		
		search.setBounds(GuiConstants.DISTANCE, employeesAppointments.getY() + GuiConstants.GROUP_DISTANCE-30 + message.getHeight(), message.getWidth(), message.getHeight());
		search.setText("Søk");
		search.addFocusListener(this);
		
		scroll.setBounds(GuiConstants.DISTANCE, search.getY() +2 + message.getHeight(), message.getWidth(), message.getHeight()*5);
		
		employeeList.setBounds(GuiConstants.DISTANCE, search.getY() + 2 + message.getHeight(), message.getWidth()-20, employeeList.getHeight());
		employeeList.setPreferredSize(employeeList.getSize());
				
		addEmployee.setBounds(GuiConstants.DISTANCE, scroll.getY() + GuiConstants.DISTANCE + scroll.getHeight(), message.getWidth()/2, message.getHeight());
		
		scrollSelectedEmployee.setBounds(GuiConstants.DISTANCE, addEmployee.getY() + GuiConstants.DISTANCE + message.getHeight(), message.getWidth(), message.getHeight()*4);

		
		logOut.setBounds(GuiConstants.DISTANCE, scrollSelectedEmployee.getY() + scrollSelectedEmployee.getHeight() + GuiConstants.DISTANCE, message.getWidth(), message.getHeight());
		logOut.setFont(GuiConstants.BUTTON_FONT);
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
	
	private void renderList(){
		
		int x = 5;
		int y = 5;
		int width = 30;
		int height = 30;
		
		for (int i = 0; i < employeeList.getModel().getSize(); i++) {
			JCheckBox checkBox = new JCheckBox();
			JLabel nameLabel = new JLabel();
			Person p = (Person)employeeList.getModel().getElementAt(i);
			nameLabel.setText(p.getFirstname() + " " + p.getSurname());
			checkBox.setBounds(x, y, width - 6, height);
			checkBox.setOpaque(false);
			nameLabel.setBounds(x+width, y, width, height);
			employeeList.add(checkBox);
			employeeList.add(nameLabel);
			y+=22;
			employeeList.setSize(employeeList.getWidth(), y);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void receiveMessage(ComMessage m) {
		if(m.getType().equals(MessageType.RECEIVE_SEARCH_PERSON)){
			employeeListModel.clear();
			Collection<Person> persons = (Collection<Person>)m.getData();
			for (Person person : persons) {
				employeeListModel.addElement(person);
			}
			renderList();
		}
	}
}