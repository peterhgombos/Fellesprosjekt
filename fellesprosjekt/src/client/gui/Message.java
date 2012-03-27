package client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import client.Client;
import client.connection.MessageListener;
import client.connection.ServerData;

import common.dataobjects.ComMessage;
import common.dataobjects.Note;
import common.utilities.MessageType;

@SuppressWarnings("serial")
public class Message extends JPanel implements FocusListener, MessageListener{

	private JLabel headLine;

	private JCheckBox all;

	private JTextField searchfield;

	private JButton showbutton;
	private JButton delete;
	private JButton toCalender;

	private JList messageList;
	private DefaultListModel messageModel;
	private JScrollPane scroll;

	private CalendarPanel calendar;
	private ArrayList<Note> notes;
	private MessageRendrer rendrer;

	public Message(CalendarPanel calendarPanel) {
		calendar = calendarPanel;

		headLine = new JLabel("Meldinger");
		notes = new ArrayList<Note>();
		rendrer = new MessageRendrer();

		all = new JCheckBox();
		all.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e){
				if(e.getStateChange() == ItemEvent.DESELECTED){
					messageList.removeSelectionInterval(0, messageModel.getSize()-1);
				}else{
					int[] indexes = new int[messageModel.getSize()];
					for(int i = 0; i < indexes.length; i++){
						indexes[i] = i;
					}
					messageList.setSelectedIndices(indexes);
				}
			}
		});
		searchfield = new JTextField("Søk");

		searchfield.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				ServerData.requestNotes(Client.getUser(), searchfield.getText());
			}
			public void keyPressed(KeyEvent e) {}
		});

		messageList = new JList();
		messageModel = new DefaultListModel();
		messageList.setModel(messageModel);
		messageList.setCellRenderer(rendrer);
		messageList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		scroll = new JScrollPane(messageList);

		delete = new JButton("Slett");
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				if(messageList.getSelectedValues().length > 0){
					ArrayList<Note> alnotes = new ArrayList<Note>();
					for (int i = 0; i < messageList.getSelectedValues().length; i++) {
						alnotes.add((Note) messageList.getModel().getElementAt(i));
					}
					ServerData.delteNotes(alnotes);

					for (int i = 0; i < alnotes.size(); i++) {
						messageModel.removeElement(alnotes.get(i));
					}
				}
			}
		});

		showbutton = new JButton("Vis");
		showbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				if(messageList.getSelectedValues().length != 1){
					UserInformationMessages.showWarningMessage("Du kan bare vise en melding om gangen.");
					return;
				}
				if(((Note)messageList.getSelectedValue()).getAppointment() != null){
					calendar.goToAppointmentView(((Note)messageList.getSelectedValue()).getAppointment());
				}
				ServerData.markNoteAsRead(Client.getUser(), (Note)messageList.getSelectedValue());
				ServerData.requestNotes(Client.getUser(), searchfield.getText());
			}
		});

		toCalender = new JButton("Til Kalender");
		toCalender.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				calendar.goToCalender();
			}
		});

		setLayout(null);
		resize();

		add(headLine);
		add(all);
		add(searchfield);

		add(scroll);

		add(toCalender);
		add(delete);
		add(showbutton);

		ServerData.addMessageListener(this);
		ServerData.requestNotes(Client.getUser(), "");
	}

	public void resize(){
		headLine.setBounds(GuiConstants.HEADLINE_X, GuiConstants.HEADLINE_Y, GuiConstants.HEADLINE_WIDTH, GuiConstants.HEADLINE_HEIGTH);
		headLine.setFont(GuiConstants.FONT_30);

		all.setBounds(headLine.getX(), headLine.getY() + headLine.getHeight() + GuiConstants.GROUP_DISTANCE, (int)all.getPreferredSize().getWidth(), (int)all.getPreferredSize().getHeight());

		searchfield.setBounds(all.getX() + all.getWidth() + GuiConstants.DISTANCE, all.getY(), 470 - all.getWidth() - GuiConstants.DISTANCE, 25);
		searchfield.setFont(GuiConstants.FONT_14);
		searchfield.addFocusListener(this);

		scroll.setBounds(headLine.getX(), all.getY() + searchfield.getHeight() + GuiConstants.DISTANCE, 470, 250);

		messageList.setBounds(headLine.getX(), scroll.getY(), scroll.getWidth() - 5, messageList.getHeight());
		messageList.setPreferredSize(messageList.getSize());

		showbutton.setBounds(headLine.getX(), scroll.getY() + scroll.getHeight() + GuiConstants.DISTANCE, GuiConstants.BUTTON_WIDTH, GuiConstants.BUTTON_HEIGTH);
		showbutton.setFont(GuiConstants.FONT_14);

		delete.setBounds(showbutton.getX() + showbutton.getWidth() + GuiConstants.DISTANCE, showbutton.getY(),  GuiConstants.BUTTON_WIDTH, GuiConstants.BUTTON_HEIGTH);
		delete.setFont(GuiConstants.FONT_14);

		toCalender.setBounds(delete.getX() + delete.getWidth() + GuiConstants.DISTANCE, showbutton.getY(), GuiConstants.BUTTON_WIDTH, GuiConstants.BUTTON_HEIGTH);
		toCalender.setFont(GuiConstants.FONT_14);
	}

	@Override
	public void focusGained(FocusEvent e) {
		if (searchfield.getText().equals("Søk")) {
			searchfield.setText("");
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		if (!searchfield.getText().equals("Søk")) {
			searchfield.setText("Søk");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void receiveMessage(ComMessage m) {
		if(m.getType().equals(MessageType.RECEIVE_NOTES)){
			messageModel.clear();
			for (Note note : (Collection<Note>)m.getData()) {
				messageModel.addElement(note);
			}
		}
	}

}
