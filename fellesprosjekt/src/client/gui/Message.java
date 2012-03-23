package client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


import common.dataobjects.ComMessage;
import common.dataobjects.Note;
import common.utilities.MessageType;

import client.connection.MessageListener;

@SuppressWarnings("serial")
public class Message extends JPanel implements FocusListener, MessageListener{

	private JCheckBox all;
	private JTextField searchfield;
	private JButton delete;
	private JButton toCalender;
	private JLabel headLine;
	private JPanel messageList;
	private JScrollPane scroll;
	private int x;
	private int y;
	private int width;
	private int height;
	private JCheckBox checkBox;
	private JLabel nameLabel;
	private ArrayList<JCheckBox> boxList;
	private CalendarPanel calendar;
	private ArrayList<Note> notesList;

	public Message(CalendarPanel calendarPanel) {
		notesList = new ArrayList<Note>();
		x = 5;
		y = 5;
		width = 30;
		height = 30;
		checkBox = new JCheckBox();
		boxList = new ArrayList<JCheckBox>();
		nameLabel = new JLabel();
		calendar = calendarPanel;
		 
		
		all = new JCheckBox();
		all.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(all.isSelected()){
					for (int i=0; i< boxList.size();i++){
						boxList.get(i).setSelected(true);					
					}
				}
				else{
					for (int i=0; i< boxList.size();i++){
						boxList.get(i).setSelected(false);					
					}
				}
				
			}
		});
		
		searchfield = new JTextField("Søk");
		delete = new JButton("Slett");
		toCalender = new JButton("Til Kalender");
		toCalender.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				calendar.goToCalender();
				
			}
		});
		headLine = new JLabel("Meldinger");
		messageList = new JPanel();
		scroll = new JScrollPane(messageList);
		
		
		add(all);
		add(searchfield);
		add(toCalender);
		add(delete);
		add(headLine);
		add(scroll);
		messageList.setLayout(null);
		messageList.setBackground(Color.WHITE);
		setLayout(null);
		resize();
		
	}
	
	public void resize(){
		
		headLine.setBounds(GuiConstants.DISTANCE*27, GuiConstants.DISTANCE, 300, 40);
		headLine.setFont(new Font(headLine.getFont().getName(), 0, 30));
		
		all.setBounds(GuiConstants.DISTANCE+60, headLine.getY() + headLine.getHeight() + GuiConstants.GROUP_DISTANCE, 25, 25);
		
		searchfield.setBounds(all.getY() + GuiConstants.GROUP_DISTANCE, all.getY(), 383, 25);
		searchfield.setFont(GuiConstants.BUTTON_FONT);
		searchfield.setText("Søk");
		searchfield.addFocusListener(this);
		
		scroll.setBounds(GuiConstants.DISTANCE+55, all.getY() + searchfield.getHeight() + GuiConstants.DISTANCE, 435, 250);
		
		for (int i = 0; i < notesList.size(); i++) {
			checkBox = new JCheckBox();
			nameLabel = new JLabel();
			nameLabel.setText(notesList.get(i).getTitle());
			checkBox.setBounds(x, y, width-8, height);
			nameLabel.setBounds(x+width, y, width, height);
			messageList.add(checkBox);
			boxList.add(i,checkBox);
			messageList.add(nameLabel);
			y+=22;
			messageList.setSize(messageList.getWidth(), y);
		}
		messageList.setBounds(GuiConstants.DISTANCE+55, all.getY() + searchfield.getHeight() + GuiConstants.DISTANCE, 325, messageList.getHeight());
		messageList.setPreferredSize(messageList.getSize());
		
		delete.setBounds(GuiConstants.DISTANCE + 50, scroll.getY() + scroll.getHeight() + GuiConstants.DISTANCE,  150, 35);
		delete.setFont(GuiConstants.BUTTON_FONT);
		
		toCalender.setBounds(delete.getWidth() + GuiConstants.DISTANCE + delete.getX(), delete.getY(), 150, 35);
	}

	
	

	@Override
	public void focusGained(FocusEvent arg0) {
		if (searchfield.getText().equals("Søk")) {
			searchfield.setText("");
		}
		
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		if (!searchfield.getText().equals("Søk")) {
			searchfield.setText("Søk");
		}
		
	}

	@Override
	public void receiveMessage(ComMessage m) {
		if(m.getType().equals(MessageType.RECEIVE_NOTE)){
			Collection<Note> notes = (Collection<Note>)m.getData();
			
			for (Note note : notes) {
				notesList.add(note);
			}
		}
		
	}

}
