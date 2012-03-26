package client.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import client.Client;
import client.connection.ServerData;

import com.toedter.calendar.JDateChooser;
import common.dataobjects.Appointment;
import common.utilities.DateString;

@SuppressWarnings("serial")
public class NewAppointment extends JPanel{
	
	private JDateChooser datepicker;
	private JDateChooser endDPicker;
	private JCheckBox severalDays;
	private JLabel severalDaysLabel;
	
	private JLabel headlineLabel;
	private JLabel titleLabel;
	private JLabel dateLabel;
	private JLabel startTimeLabel;
	private JLabel endTimeLabel;
	private JLabel descriptionLabel;
	private JLabel placeLabel;
	private JLabel roomInformationLabel;
	
	private JScrollPane scrollPane;
	
	private JTextField nameField; 
	private JTextField placeField;
	private JTextArea descriptionArea;
	
	private JComboBox startTimeHoursField;
	private JComboBox endTimeHoursField;
	private JComboBox startTimeMinField;
	private JComboBox endTimeMinField;
	
	private JButton saveButton;
	private JButton cancelButton;
	private CalendarPanel calendar;
	private boolean isInEdit=false;
	private int existingAppointmentId; 
	
	private Date defaultDate = new Date(System.currentTimeMillis());

	public NewAppointment(CalendarPanel pal){
		init(pal);
	}
	private void init(CalendarPanel calendarPanel){
		String[] min = {"00", "15", "30", "45"};
		String[] hours= {"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
		
		
		headlineLabel = new JLabel("Ny Avtale");
		titleLabel = new JLabel("Tittel", SwingConstants.RIGHT);
		dateLabel = new JLabel("Dato", SwingConstants.RIGHT);
		startTimeLabel = new JLabel("Fra", SwingConstants.RIGHT);
		endTimeLabel = new JLabel("Til", SwingConstants.RIGHT);
		descriptionLabel = new JLabel("Beskrivelse", SwingConstants.RIGHT);
		placeLabel = new JLabel("Sted", SwingConstants.RIGHT);
		roomInformationLabel =  new JLabel("(min 2 deltakere)", SwingConstants.RIGHT);
		calendar = calendarPanel;		
		
		nameField = new JTextField();
		datepicker = new JDateChooser();
		datepicker.setDate(defaultDate);
		datepicker.setMinSelectableDate(new Date(System.currentTimeMillis()));
		endDPicker = new JDateChooser();
		endDPicker.setDate(datepicker.getDate());
		endDPicker.setVisible(false);
		endDPicker.setMinSelectableDate(new Date(System.currentTimeMillis()));
		severalDaysLabel = new JLabel("Flere dager");
		severalDays = new JCheckBox();
		severalDays.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					endDPicker.setVisible(true);
					endDPicker.setEnabled(true);
				}
				if (e.getStateChange() == ItemEvent.DESELECTED){
					//TODO remember to ensure end-date is not linked to datepickerDays when disabled/invisible
					endDPicker.setVisible(false);
					endDPicker.setEnabled(false);
				}
			}
		});
		placeField = new JTextField();
		
		descriptionArea = new JTextArea();
		descriptionArea.setLineWrap(true);
		
		saveButton = new JButton("Lagre");
		saveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String title = nameField.getText();
				
				String dateStart = datepicker.getJCalendar().getCalendar().get(Calendar.YEAR) + "-" + (datepicker.getJCalendar().getCalendar().get(Calendar.MONTH) + 1) + "-" + datepicker.getJCalendar().getCalendar().get(Calendar.DAY_OF_MONTH); 
				String dateEnd;
				
				if (endDPicker.isEnabled()) {
					dateEnd = endDPicker.getJCalendar().getCalendar().get(Calendar.YEAR) + "-" + (endDPicker.getJCalendar().getCalendar().get(Calendar.MONTH) + 1) + "-" + endDPicker.getJCalendar().getCalendar().get(Calendar.DAY_OF_MONTH); 
				} else {
					dateEnd = dateStart;
					
				}
				
				String timeStart = startTimeHoursField.getSelectedItem() + ":" + startTimeMinField.getSelectedItem() + ":0";
				String timeEnd = endTimeHoursField.getSelectedItem() + ":" + endTimeMinField.getSelectedItem() + ":0";
				
				String description = descriptionArea.getText();
				
				String place = placeField.getText();
				
				if(title.trim().equals("")){
					UserInformationMessages.showErrormessage("Du må lage en tittel");
					return;
				}

				
				if(isInEdit){
					Appointment a = new Appointment(existingAppointmentId, Client.getUser(), title, description, place, new DateString(dateStart + " " + timeStart), new DateString(dateEnd + " " + timeEnd));
					ServerData.requestUpdateAppointment(a);
				}
				
				Appointment a = new Appointment(-1, Client.getUser(), title, description, place, new DateString(dateStart + " " + timeStart), new DateString(dateEnd + " " + timeEnd));
				ServerData.requestNewAppointment(a);
			
				
				calendar.goToCalender();
			}
		});
		
		cancelButton = new JButton("Avbryt");
		cancelButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				calendar.goToCalender();
			}
		});
		
		startTimeHoursField = new JComboBox(hours);
		endTimeHoursField = new JComboBox(hours);
		startTimeMinField = new JComboBox(min);
		endTimeMinField = new JComboBox(min);
		
		scrollPane = new JScrollPane(descriptionArea);
		
		setLayout(null);
		resize();
		
		add(scrollPane);
		add(headlineLabel);
		add(titleLabel);
		add(dateLabel);
		add(startTimeLabel);
		add(endTimeLabel);
		add(descriptionLabel);
		add(placeLabel);
		add(roomInformationLabel);
		add(nameField);
		add(placeField);
		add(saveButton);
		add(cancelButton);
		add(startTimeHoursField);
		add(startTimeMinField);
		add(endTimeHoursField);
		add(endTimeMinField);
		add(datepicker);
		add(severalDays);
		add(severalDaysLabel);
		add(endDPicker);
		
		Client.getFrame().resize(GuiConstants.FRAME_WIDTH+1, GuiConstants.FRAME_HEIGTH+1);
		Client.getFrame().resize(GuiConstants.FRAME_WIDTH, GuiConstants.FRAME_HEIGTH);
		
		
		//This is for testing:
		datepicker.addPropertyChangeListener(new PropertyChangeListener() {
			
			public void propertyChange(PropertyChangeEvent evt) {
				firePropertyChange("date", evt.getOldValue(), evt.getNewValue());
			}
		});
		//testing over
	}
	
	public NewAppointment(CalendarPanel pal, Appointment app){
		init(pal);
		
		isInEdit=true;
		
		headlineLabel.setText("Rediger: " + app.getTitle());
		nameField.setText(app.getTitle());
		
		startTimeHoursField.setSelectedIndex(app.getStartTime().getHour());
		startTimeMinField.setSelectedIndex(app.getStartTime().getMinute());
		endTimeHoursField.setSelectedIndex(app.getEndTime().getHour());
		endTimeMinField.setSelectedIndex(app.getEndTime().getMinute());
		
		descriptionArea.setText(app.getDescription());
		placeField.setText(app.getPlace());
		
		existingAppointmentId = app.getId();
		
	}
	
	public void resize(){
		
		headlineLabel.setBounds(GuiConstants.HEADLINE_X, GuiConstants.HEADLINE_Y, GuiConstants.HEADLINE_WIDTH, GuiConstants.HEADLINE_HEIGTH);
		headlineLabel.setFont(GuiConstants.FONT_30);
		
		titleLabel.setBounds(GuiConstants.DISTANCE, headlineLabel.getY() + headlineLabel.getHeight() + GuiConstants.GROUP_DISTANCE, 100, GuiConstants.LABEL_HEIGTH);
		titleLabel.setFont(new Font(headlineLabel.getFont().getName(), 0, 16));
		
		nameField.setBounds(GuiConstants.DISTANCE*2+ titleLabel.getWidth() + titleLabel.getX(), titleLabel.getY(), 190, GuiConstants.TEXTFIELD_HEIGTH);
				
		dateLabel.setBounds(titleLabel.getX(), titleLabel.getY() + titleLabel.getHeight() + GuiConstants.DISTANCE, 100, GuiConstants.LABEL_HEIGTH);
		dateLabel.setFont(new Font(dateLabel.getFont().getName(), 0, 16));
		
		datepicker.setBounds(GuiConstants.DISTANCE*2 + dateLabel.getWidth() + dateLabel.getX(), dateLabel.getY(), 190, GuiConstants.TEXTFIELD_HEIGTH);
		
		severalDays.setBounds(datepicker.getX(), dateLabel.getY() + dateLabel.getHeight() + GuiConstants.GROUP_DISTANCE, 23, 23);
		
		severalDaysLabel.setBounds(severalDays.getX() + severalDays.getWidth() + GuiConstants.DISTANCE, severalDays.getY(), 200, GuiConstants.LABEL_HEIGTH);
		
		
		endDPicker.setBounds(datepicker.getX() + datepicker.getWidth() + GuiConstants.DISTANCE, datepicker.getY(), 190, GuiConstants.TEXTFIELD_HEIGTH);
		
		startTimeLabel.setBounds(titleLabel.getX(), severalDays.getY() + severalDays.getHeight() + GuiConstants.GROUP_DISTANCE, 100, GuiConstants.LABEL_HEIGTH);
		startTimeLabel.setFont(new Font(startTimeLabel.getFont().getName(), 0, 16));
		
		startTimeHoursField.setBounds(GuiConstants.DISTANCE*2+ startTimeLabel.getWidth() + startTimeLabel.getX() , startTimeLabel.getY(), 70, GuiConstants.TEXTFIELD_HEIGTH);
		startTimeMinField.setBounds(startTimeHoursField.getX() + 2 + startTimeHoursField.getWidth(), startTimeHoursField.getY(), 70, GuiConstants.TEXTFIELD_HEIGTH);
		
		endTimeLabel.setBounds(startTimeHoursField.getX() + startTimeMinField.getWidth() + GuiConstants.DISTANCE, startTimeHoursField.getY(), 100, GuiConstants.LABEL_HEIGTH);
		endTimeLabel.setFont(new Font(endTimeLabel.getFont().getName(), 0, 16));
		
		endTimeHoursField.setBounds(endTimeLabel.getX() + endTimeLabel.getWidth() + GuiConstants.DISTANCE, endTimeLabel.getY(), 70, GuiConstants.TEXTFIELD_HEIGTH);
		endTimeMinField.setBounds(endTimeHoursField.getX() + 2 + endTimeHoursField.getWidth(), endTimeHoursField.getY(), 70, GuiConstants.TEXTFIELD_HEIGTH);
		
		descriptionLabel.setBounds(titleLabel.getX(), startTimeLabel.getY() + startTimeLabel.getHeight() + GuiConstants.GROUP_DISTANCE, 100, GuiConstants.LABEL_HEIGTH);
		descriptionLabel.setFont(new Font(descriptionLabel.getFont().getName(), 0, 16));
		
		scrollPane.setBounds(GuiConstants.DISTANCE*2+ titleLabel.getWidth() + titleLabel.getX(), descriptionLabel.getY(),  
				(endTimeMinField.getX() + endTimeMinField.getWidth())-startTimeHoursField.getX(), 100);
		
		placeLabel.setBounds(titleLabel.getX(), scrollPane.getY() + scrollPane.getHeight() + GuiConstants.GROUP_DISTANCE, 100, GuiConstants.LABEL_HEIGTH);
		placeLabel.setFont(new Font(placeLabel.getFont().getName(), 0, 16));
		
		placeField.setBounds(scrollPane.getX(), placeLabel.getY(), 190, GuiConstants.TEXTFIELD_HEIGTH);
		
		saveButton.setBounds(placeField.getX(), placeField.getY() + placeField.getHeight() + GuiConstants.GROUP_DISTANCE, 100, GuiConstants.BUTTON_HEIGTH);
		saveButton.setFont(new Font(saveButton.getFont().getName(), 0, 14));
		
		cancelButton.setBounds(saveButton.getX() + saveButton.getWidth() + GuiConstants.DISTANCE, saveButton.getY(), 100, GuiConstants.BUTTON_HEIGTH);
		cancelButton.setFont(new Font(cancelButton.getFont().getName(), 0, 14));
	}
}
