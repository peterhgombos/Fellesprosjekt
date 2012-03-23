package common.dataobjects;

import java.sql.Date;

import common.utilities.DateString;

public class Note {
	
	private int id;
	private String title;
	private DateString timeAdded;
	private Appointment appointment;
	
	public Note (String title, Appointment appointment){
		this.title = title;
		this.appointment = appointment;
	}
	
	public Note(int id, String title, DateString timeAdded, Appointment appointment){
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

	public DateString getTimeAdded() {
		return timeAdded;
	}
}
