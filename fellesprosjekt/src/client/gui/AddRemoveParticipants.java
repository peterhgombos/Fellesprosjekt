package client.gui;

import java.awt.Font;

import javax.swing.*;

@SuppressWarnings("serial")
public class AddRemoveParticipants extends JPanel{
	
	
	//TODO: lage listcellRender!!
	//TODO: lage Person generics i Jlistene
	
	JLabel headline;
	JLabel externalParticipantsLabel;
	JTextField searchField;
	JTextField externalParticipantsField;
	
	DefaultListModel listmodel1;
	DefaultListModel listmodel2;
	JList addedParticipantsList;
	JList employeeList;
	
	JButton add;
	JButton remove;
	JButton save;
	JButton cancel;	

	
	public AddRemoveParticipants() {
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
		remove = new JButton("<");
		save = new JButton("Lagre");
		cancel = new JButton("Avbryt");
		
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
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Legge til/fjerne deltakere");
		AddRemoveParticipants addRemoveParticipants = new AddRemoveParticipants();
		frame.add(addRemoveParticipants);
		frame.getContentPane();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setSize(650, 600);
		frame.setVisible(true);
	}
	
}
