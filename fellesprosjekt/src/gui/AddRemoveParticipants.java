package gui;

import java.awt.Font;

import javax.swing.*;

public class AddRemoveParticipants extends JPanel{
	
	JLabel headline;
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
		
		listmodel1 = new DefaultListModel();
		listmodel2 = new DefaultListModel();
		addedParticipantsList = new JList(listmodel1);
		employeeList = new JList(listmodel2);
		
		add = new JButton(">");
		remove = new JButton("<");
		save = new JButton("Lagre");
		cancel = new JButton("Avbryt");
		
		add(headline);
		add(searchField);
		add(externalParticipantsField);
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
		GuiConstants guiConstants = new GuiConstants();
		
		headline.setBounds(guiConstants.getDistance()*15, guiConstants.getDistance(), 400, 40);
		headline.setFont(new Font(headline.getFont().getName(), 0, 30));
		
		searchField.setBounds(x, y, width, height)
		
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Legge til/fjerne deltakere");
		AddRemoveParticipants addRemoveParticipants = new AddRemoveParticipants();
		frame.add(addRemoveParticipants);
		frame.getContentPane();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setSize(700, 700);
		frame.setVisible(true);
		
	}
	
}
