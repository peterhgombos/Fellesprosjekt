package common.dataobjects;

import java.sql.Date;

public class Note {
	
	private int id;
	private String title;
	private Date timeAdded;
	private Appointment appointment;
	
	public Note (String title, Appointment appointment){
		this.title = title;
		this.appointment = appointment;
	}
	
	public Note(int id, String title, Date timeAdded, Appointment appointment){
		this.id = id;
		this.title = title;
		this.timeAdded = timeAdded;
		this.appointment = appointment;
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
