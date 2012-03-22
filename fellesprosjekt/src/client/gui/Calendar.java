package client.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;

import common.dataobjects.ComMessage;

import client.connection.MessageListener;
import client.connection.*;
import common.utilities.MessageType;

@SuppressWarnings("serial")
public class Calendar extends JPanel implements MessageListener {

	private JTable table;
	private JScrollPane scrollPane;
	private JLabel weekLabel;
	private JButton lastWeek;
	private JButton nextWeek;
	private JTextField weekNumberField;
	private JTextField yearField;
	private CalModel calModel;
	
	private String[] dayName = {"", "Mandag", "Tirsdag", "Onsag", "Torsdag", "Fredag", "Lørdag", "Søndag"};
	
	public Calendar(){
		setLayout(null);
		
		ServerData.getCalendar().getCalendar().setTimeInMillis(System.currentTimeMillis());
		calModel = new CalModel();
		weekLabel = new JLabel("Uke");
		lastWeek = new JButton("<");
		nextWeek = new JButton(">");
		weekNumberField = new JTextField(""+calModel.getWeek());
		yearField = new JTextField(""+calModel.getYear());
		
		table = new JTable();
		
		CalRenderer renderer = new CalRenderer();
		table.setModel(calModel);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setRowHeight(60);
		table.setDefaultRenderer(Object.class, renderer);
		table.getColumnModel().getColumn(0).setMaxWidth(45);
		table.getColumnModel().getColumn(0).setHeaderValue(dayName[0]);
		for(int i = 1; i < table.getModel().getColumnCount(); i++){
			TableColumn col = table.getColumnModel().getColumn(i);
			col.setMinWidth(134);
			col.setHeaderValue(dayName[i]);
		}
		
		lastWeek.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				calModel.lastWeek();
				weekNumberField.setText(""+calModel.getWeek());
				yearField.setText("" + calModel.getYear());
				repaint();
				
			}
		});
		
		nextWeek.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				calModel.nextWeek();
				weekNumberField.setText("" + calModel.getWeek());
				yearField.setText("" + calModel.getYear());
				repaint();
				
			}
		});
		
		weekNumberField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!weekNumberField.getText().equals((""+calModel.getWeek()))){
					calModel.setWeek(Integer.parseInt(weekNumberField.getText()));
					repaint();
				}
				
			}
		});
		
		yearField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!yearField.getText().equals(("" + calModel.getYear()))) {
					calModel.setYear(Integer.parseInt(yearField.getText()));
					repaint();
				}
				
			}
		});
		
		scrollPane = new JScrollPane(table);
	
		resize();
		
		add(lastWeek);
		add(scrollPane);
		add(weekLabel);
		add(weekNumberField);
		add(yearField);
		add(nextWeek);
		nextWeek.setFont(new Font("Arial", 0, 14));
		System.out.println(nextWeek.getFont().getName());
	}
	
	private void resize(){
		lastWeek.setBounds(GuiConstants.DISTANCE*30, GuiConstants.DISTANCE, 35, GuiConstants.BUTTON_HEIGTH);
		lastWeek.setFont(GuiConstants.BUTTON_FONT);
		weekLabel.setBounds(lastWeek.getX() + lastWeek.getWidth() + GuiConstants.DISTANCE, lastWeek.getY(), 40, GuiConstants.LABEL_HEIGTH);
		weekLabel.setFont(GuiConstants.BUTTON_FONT);
		weekNumberField.setBounds(weekLabel.getX() + weekLabel.getWidth() + GuiConstants.DISTANCE, weekLabel.getY(), 50, GuiConstants.TEXTFIELD_HEIGTH);
		weekNumberField.setFont(GuiConstants.BUTTON_FONT);
		yearField.setBounds(weekNumberField.getX() + weekNumberField.getWidth() + GuiConstants.DISTANCE, weekNumberField.getY(), 70, GuiConstants.TEXTFIELD_HEIGTH);
		yearField.setFont(GuiConstants.BUTTON_FONT);
		nextWeek.setBounds(yearField.getX() + yearField.getWidth() + GuiConstants.DISTANCE + weekLabel.getWidth(), yearField.getY(), 35, GuiConstants.BUTTON_HEIGTH);
		nextWeek.setFont(GuiConstants.BUTTON_FONT);
		scrollPane.setBounds(0, lastWeek.getHeight() + lastWeek.getY() + GuiConstants.GROUP_DISTANCE, 1000, 650);
	}

	@Override
	public void receiveMessage(ComMessage m){
		if(m.getType().equals(MessageType.RECEIVE_APPOINTMENTS) || m.getType().equals(MessageType.RECEIVE_MEETINGS)){
			table.repaint();
		}
	}
	
}
