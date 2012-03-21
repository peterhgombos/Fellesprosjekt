package client.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;

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
	
	private JButton add;
	private JButton remove;
	private JButton save;
	private JButton cancel;
	
	private CalendarPanel calendarpanel;

	
	public AddRemoveParticipants(CalendarPanel calendarPanel) {
		calendarpanel = calendarPanel;
		
		headline = new JLabel("Legge Til/Fjerne Deltakere");
		searchField = new JTextField();
		
		externalParticipantsField = new JTextField();
		externalParticipantsLabel = new JLabel("Antall eksterne deltakere");
		
		listmodel1 = new DefaultListModel();
		listmodel2 = new DefaultListModel();
		addedParticipantsList = new JList(listmodel1);
		employeeList = new JList(listmodel2);
		addedParticipantsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		addedParticipantsList.setSelectedIndex(0);
		addedParticipantsList.setVisibleRowCount(10);
		JScrollPane addedParticipantsListScroll = new JScrollPane(addedParticipantsList);
		employeeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		employeeList.setSelectedIndex(0);
		employeeList.setVisibleRowCount(10);
		JScrollPane employeeListScroll = new JScrollPane(employeeList);
		
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
				calendarpanel.goToNewMeeting();
			}
		});
		cancel = new JButton("Avbryt");
		cancel..addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calendarpanel.goToNewMeeting();
			}
		});
		
		add(headline);
		add(searchField);
		add(externalParticipantsField);
		add(externalParticipantsLabel);
		add(addedParticipantsList);
		add(employeeList);
		add(add);
		add(remove);
		add(save);
		add(cancel);
		
		setLayout(null);
		resize();
	}
	
	public void resize(){
		
		headline.setBounds(GuiConstants.DISTANCE*15, GuiConstants.DISTANCE, 400, 40);
		headline.setFont(new Font(headline.getFont().getName(), 0, 30));
		
		searchField.setBounds(GuiConstants.DISTANCE*5, headline.getHeight() + GuiConstants.DISTANCE*5, 210, 35);
		searchField.setText("Søk");
		searchField.addFocusListener(this);
		
		employeeList.setBounds(searchField.getX(), searchField.getY() + searchField.getHeight() + GuiConstants.DISTANCE, 210, 300);
		
		add.setBounds(employeeList.getX() + employeeList.getWidth() + GuiConstants.DISTANCE,
				employeeList.getY() + GuiConstants.DISTANCE*10, 50, 30);
		remove.setBounds(add.getX(), add.getY() + add.getHeight() + GuiConstants.DISTANCE, 50, 30);
		
		addedParticipantsList.setBounds(add.getX() + add.getWidth() + GuiConstants.DISTANCE, employeeList.getY(), 210, 300);
		
		externalParticipantsField.setBounds(employeeList.getX(), employeeList.getY() + employeeList.getHeight() + GuiConstants.DISTANCE, 
				60, 35);
		
		externalParticipantsLabel.setBounds(externalParticipantsField.getX() + externalParticipantsField.getWidth() + GuiConstants.DISTANCE,
				externalParticipantsField.getY(), 160, 35);
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
