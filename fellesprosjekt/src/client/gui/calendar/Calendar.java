package client.gui.calendar;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;

import client.connection.MessageListener;
import client.connection.ServerData;
import client.gui.CalendarPanel;
import client.gui.GuiConstants;

import common.dataobjects.ComMessage;
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

	
	public void weekChanged(){
		for(int i = 1; i < table.getModel().getColumnCount(); i++){
			table.getColumnModel().getColumn(i).setHeaderValue(dayName[i] + "" +  ServerData.getCalendar().getDateForDay(calModel.getYear(), calModel.getWeek(), i));
		}
	}
	
	public Calendar(CalendarPanel panel){
		setLayout(null);

		ServerData.getCalendar().getCalendar().setTimeInMillis(System.currentTimeMillis());

		calModel = new CalModel(this);
		weekLabel = new JLabel("Uke");
		lastWeek = new JButton("<");
		nextWeek = new JButton(">");
		weekNumberField = new JTextField(""+calModel.getWeek());
		yearField = new JTextField(""+calModel.getYear());

		table = new JTable();

		CalRenderer renderer = new CalRenderer();
		table.setGridColor(new Color(0x888888));
		table.setModel(calModel);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setRowHeight(75);
		table.setDefaultRenderer(Object.class, renderer);
		table.getColumnModel().getColumn(0).setMaxWidth(45);
		table.getColumnModel().getColumn(0).setHeaderValue(dayName[0]);
		for(int i = 1; i < table.getModel().getColumnCount(); i++){
			TableColumn col = table.getColumnModel().getColumn(i);
			col.setMinWidth(134);
		}

		lastWeek.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				calModel.lastWeek();
				weekNumberField.setText(""+calModel.getWeek());
				yearField.setText("" + calModel.getYear());
				repaint();
			}
		});

		nextWeek.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				calModel.nextWeek();
				weekNumberField.setText("" + calModel.getWeek());
				yearField.setText("" + calModel.getYear());
				repaint();
			}
		});

		weekNumberField.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if(!weekNumberField.getText().equals((""+calModel.getWeek()))){
					calModel.setWeek(Integer.parseInt(weekNumberField.getText()));
					repaint();
				}
			}
		});

		yearField.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
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
		
		scrollPane.getViewport().setViewPosition(new Point(0,table.getRowHeight()*7));		
		weekChanged();
	}

	private void resize(){
		lastWeek.setBounds(GuiConstants.DISTANCE*30, GuiConstants.DISTANCE, 50, GuiConstants.BUTTON_HEIGTH);
		lastWeek.setFont(GuiConstants.FONT_14);
		weekLabel.setBounds(lastWeek.getX() + lastWeek.getWidth() + GuiConstants.DISTANCE, lastWeek.getY() + 5, 40, GuiConstants.LABEL_HEIGTH);
		weekLabel.setFont(GuiConstants.FONT_14);
		weekNumberField.setBounds(weekLabel.getX() + weekLabel.getWidth() + GuiConstants.DISTANCE, weekLabel.getY(), 50, GuiConstants.TEXTFIELD_HEIGTH);
		weekNumberField.setFont(GuiConstants.FONT_14);
		yearField.setBounds(weekNumberField.getX() + weekNumberField.getWidth() + GuiConstants.DISTANCE, weekNumberField.getY(), 70, GuiConstants.TEXTFIELD_HEIGTH);
		yearField.setFont(GuiConstants.FONT_14);
		nextWeek.setBounds(yearField.getX() + yearField.getWidth() + GuiConstants.DISTANCE, lastWeek.getY(), 50, GuiConstants.BUTTON_HEIGTH);
		nextWeek.setFont(GuiConstants.FONT_14);
		scrollPane.setBounds(0, lastWeek.getHeight() + lastWeek.getY() + GuiConstants.GROUP_DISTANCE + 5, 1002, 620);
	}

	@Override
	public void receiveMessage(ComMessage m){
		if(m.getType().equals(MessageType.RECEIVE_APPOINTMENTS) || m.getType().equals(MessageType.RECEIVE_MEETINGS)){
			table.repaint();
		}
	}
}