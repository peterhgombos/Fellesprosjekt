package client.gui;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;

import javax.swing.*;

import common.dataobjects.Meeting;
import common.dataobjects.Person;

@SuppressWarnings("serial")
public class AddRemoveParticipants extends JPanel implements FocusListener{
	
	private JLabel headline;
	private JLabel externalParticipantsLabel;
	private JTextField searchField;
	private JTextField externalParticipantsField;
	
	private DefaultListModel listmodel1;
	private DefaultListModel listmodel2;
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
	
	private NewMeeting newMeeting;
	
	
	public AddRemoveParticipants(CalendarPanel calendarPanel, NewMeeting meeting) {
		
		newMeeting = meeting;
		
		participants = new HashMap<Person, Integer>();
		calendarpanel = calendarPanel;
		
		headline = new JLabel("Legge Til/Fjerne Deltakere");
		searchField = new JTextField();
		
		externalParticipantsField = new JTextField();
		externalParticipantsLabel = new JLabel("Antall eksterne deltakere");
		frame = new JFrame();
		
		listmodel1 = new DefaultListModel();
		listmodel2 = new DefaultListModel();
		
		addedParticipantsList = new JList(listmodel1);
		addedParticipantsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		addedParticipantsList.setSelectedIndex(0);
		addedParticipantsList.setVisibleRowCount(10);
		
		employeeList = new JList(listmodel2);
		employeeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		employeeList.setSelectedIndex(0);
		employeeList.setVisibleRowCount(10);
		
		addedParticipantsListScroll = new JScrollPane(addedParticipantsList);
		employeeListScroll = new JScrollPane(employeeList);
		
		add = new JButton(">");
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//legge deltakere over til lista
			}
		});
		remove = new JButton("<");
		remove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Fjerne deltakre
			}
		});
		save = new JButton("Lagre");
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < addedParticipantsList.getModel().getSize() ; i++) {
					participants.put((Person)addedParticipantsList.getModel().getElementAt(i), Meeting.SVAR_BLANK);
				}
				newMeeting.addParticipants(participants);
				frame.dispose();
			}
		});
		cancel = new JButton("Avbryt");
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		
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
		searchField.setText("Søk");
		searchField.addFocusListener(this);
		
		employeeListScroll.setBounds(searchField.getX(), searchField.getY() + searchField.getHeight() + GuiConstants.DISTANCE, 210, 300);
		
		add.setBounds(employeeList.getX() + employeeList.getWidth() + GuiConstants.DISTANCE,
				employeeList.getY() + GuiConstants.DISTANCE*10, 50, 30);
		remove.setBounds(add.getX(), add.getY() + add.getHeight() + GuiConstants.DISTANCE, 50, 30);
		
		addedParticipantsListScroll.setBounds(add.getX() + add.getWidth() + GuiConstants.DISTANCE, employeeList.getY(), 210, 300);
		
		externalParticipantsField.setBounds(employeeList.getX(), employeeList.getY() + employeeList.getHeight() + GuiConstants.DISTANCE, 
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
	
	
}
