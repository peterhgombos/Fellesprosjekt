package common.dataobjects;

import java.sql.Date;

public class Message {
	
	private int id;
	private String title;
	private Date timeAdded;
	private String text;
	private Appointment appointment;
	
	public Message (String title, Date timeAdded, int id, String text, Appointment appointment){
		this.title = title;
		this.timeAdded = timeAdded;
		this.id = id;
		this.text = text;
		this.appointment = appointment;
	}
	
	public String getText() {
		return text;
	}
	
	public Appointment getAppointment() {
		return appointment;
	}
	
	public int getID(){
		return id;
	}

	public String getTitle() {
		return title;
	}

	public Date getTimeAdded() {
		return timeAdded;
	}
	
}
