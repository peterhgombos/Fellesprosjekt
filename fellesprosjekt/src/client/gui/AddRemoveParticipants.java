package client.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

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

import client.Client;
import client.connection.MessageListener;
import client.connection.ServerData;

import common.dataobjects.Appointment;
import common.dataobjects.ComMessage;
import common.dataobjects.Meeting;
import common.dataobjects.Person;
import common.utilities.MessageType;

@SuppressWarnings("serial")
public class AddRemoveParticipants extends JPanel implements FocusListener, MessageListener{
	
	private JLabel headline;
	private JLabel externalParticipantsLabel;
	private JTextField searchField;
	private JTextField externalParticipantsField;
	
	private JList addedParticipantsList;
	private JList employeeList;
	
	private JScrollPane employeeListScroll;
	private JScrollPane addedParticipantsListScroll;
	
	private JButton add;
	private JButton remove;
	private JButton save;
	private JButton cancel;
	private JFrame frame;
	
	private CalendarPanel calendarpanel;
	private HashMap<Person, Integer> participants;
	private ArrayList<Person> newInvited;
	
	private NewMeeting newMeeting;
	private DefaultListModel addedParticipantsListmodel;
	private DefaultListModel employeeListModel;
	
	private AddRemoveParticipants thisAddRemoveParticipants;
	private ArrayList<Person> removedParticipants;
	
	public AddRemoveParticipants(CalendarPanel calendarPanel, NewMeeting meeting) {
		
		CalendarPanel.setSearchInSidePanelTrue();
		thisAddRemoveParticipants = this;
		newMeeting = meeting;
		
		participants = new HashMap<Person, Integer>();
		calendarpanel = calendarPanel;
		
		headline = new JLabel("Legge Til/Fjerne Deltakere");
		
		searchField = new JTextField();
		searchField.setText("Søk");
		searchField.addFocusListener(this);
		searchField.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				ServerData.requestSearchForPerson(searchField.getText());
			}
			public void keyPressed(KeyEvent e) {}
		});
		
		newInvited = new ArrayList<Person>(); 
		removedParticipants = new ArrayList<Person>();
		externalParticipantsField = new JTextField(""+newMeeting.getNumberOfExternalParticipants());
		externalParticipantsLabel = new JLabel("Antall eksterne deltakere");
		frame = new JFrame();
		
		
		addedParticipantsList = new JList(new DefaultListModel());
		addedParticipantsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		addedParticipantsList.setSelectedIndex(0);
		addedParticipantsList.setVisibleRowCount(10);
		
		
		employeeList = new JList(new DefaultListModel());
		employeeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		employeeList.setSelectedIndex(0);
		employeeList.setVisibleRowCount(10);
		
		
		addedParticipantsListScroll = new JScrollPane(addedParticipantsList);
		employeeListScroll = new JScrollPane(employeeList);
		
		addedParticipantsListmodel  = (DefaultListModel) addedParticipantsList.getModel();
		for (Person p : newMeeting.getParticipantList().keySet()) {
			if(p.getId() != Client.getUser().getId())
				addedParticipantsListmodel.addElement(p);
		}
		employeeListModel = (DefaultListModel) employeeList.getModel();
		
		
		add = new JButton(">");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Person person = (Person)employeeList.getSelectedValue();
				if(person != null){
					//If clause removes person from list of removed participants if applicable
					if (removedParticipants.contains(person)) {
						removedParticipants.remove(person);
					}
					addedParticipantsListmodel.addElement(person);
					newInvited.add(person);
					employeeListModel.removeElement(person);
				}
			}
		});
		
		remove = new JButton("<");
		remove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Person person = (Person)addedParticipantsList.getSelectedValue();
				if(person != null){
					employeeListModel.addElement(person);
					addedParticipantsListmodel.removeElement(person);
					removedParticipants.add(person); //Adds person to the list of persons removed
					if(newInvited.contains(person)){
						newInvited.remove(person);
					}
				}
			}
		});
		
		save = new JButton("Lagre");
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < newInvited.size() ; i++) {
					participants.put(newInvited.get(i), Meeting.SVAR_BLANK);
				}
				newMeeting.addParticipants(participants);
				newMeeting.setRemovedParticipants(removedParticipants); //Tells the meeting which (if any) participants have been removed
				frame.dispose();
				ServerData.removeMessageListener(thisAddRemoveParticipants);
				newMeeting.setNumberOfParticipants(Integer.parseInt(externalParticipantsField.getText()));
				CalendarPanel.setSearchInSidePanelFalse();
			}
		});
		
		cancel = new JButton("Avbryt");
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ServerData.removeMessageListener(thisAddRemoveParticipants);
				frame.dispose();
				CalendarPanel.setSearchInSidePanelFalse();
			}
		});
		
		
		ServerData.addMessageListener(this);
		if(newMeeting.getMeeting() != null){
			ServerData.requestGetParticipants(newMeeting.getMeeting());
		}
		ServerData.requestSearchForPerson("");
		
				
		add(headline);
		add(searchField);
		add(externalParticipantsField);
		add(externalParticipantsLabel);
		add(addedParticipantsListScroll);
		add(employeeListScroll);
		add(add);
		add(remove);
		add(save);
		add(cancel);
		
		setLayout(null);
		resize();
		
		frame.add(this);
		frame.setVisible(true);
		frame.setSize(700, 700);
	}
	
	public void resize(){
		
		headline.setBounds(GuiConstants.DISTANCE*15, GuiConstants.DISTANCE, 400, 40);
		headline.setFont(new Font(headline.getFont().getName(), 0, 30));
		
		searchField.setBounds(GuiConstants.DISTANCE*5, headline.getHeight() + GuiConstants.DISTANCE*5, 210, 35);
		
		employeeListScroll.setBounds(searchField.getX(), searchField.getY() + searchField.getHeight() + GuiConstants.DISTANCE, 210, 300);
		
		add.setBounds(employeeListScroll.getX() + employeeListScroll.getWidth() + GuiConstants.DISTANCE,
				employeeListScroll.getY() + GuiConstants.DISTANCE*10, 50, 30);
		remove.setBounds(add.getX(), add.getY() + add.getHeight() + GuiConstants.DISTANCE, 50, 30);
		
		addedParticipantsListScroll.setBounds(add.getX() + add.getWidth() + GuiConstants.DISTANCE, employeeListScroll.getY(), 210, 300);
		
		externalParticipantsField.setBounds(employeeListScroll.getX(), employeeListScroll.getY() + employeeListScroll.getHeight() + GuiConstants.DISTANCE, 
				60, 35);
		
		externalParticipantsLabel.setBounds(externalParticipantsField.getX() + externalParticipantsField.getWidth() + GuiConstants.DISTANCE,
				externalParticipantsField.getY(), 180, 35);
		externalParticipantsLabel.setFont(new Font(externalParticipantsLabel.getFont().getName(), 0, 15));
		
		save.setBounds(externalParticipantsField.getX(), externalParticipantsField.getY() + externalParticipantsField.getHeight() + GuiConstants.DISTANCE,
				100, 30);
		cancel.setBounds(save.getX() + save.getWidth() + GuiConstants.DISTANCE, save.getY(), 100, 30);
		
	}
	
	@Override
	public void focusGained(FocusEvent arg0) {
		if (searchField.getText().equals("Søk")) {
			searchField.setText("");
		}
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		if (!searchField.getText().equals("Søk")) {
			searchField.setText("Søk");
		}	
	}

	private boolean personExistInAddedParticipantsList(Person p){
		for (int i = 0; i < addedParticipantsList.getModel().getSize(); i++) {
			Person person = (Person)addedParticipantsListmodel.getElementAt(i);
			if (p.getId() == person.getId()){
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
				if(!personExistInAddedParticipantsList(person) && person.getId() != Client.getUser().getId()){
					employeeListModel.addElement(person);
				}
			}
		}
		else if(m.getType().equals(MessageType.RECEIVE_PARTICIPANTS)){
			addedParticipantsListmodel.clear();
			ArrayList<Person> persons = (ArrayList<Person>) m.getData();
			for(Person p : persons){
				if(p.getId() != Client.getUser().getId()){
					addedParticipantsListmodel.addElement(p);
				}
			}
		}
	}
	
}
