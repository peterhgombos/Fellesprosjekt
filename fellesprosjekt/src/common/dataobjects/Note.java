package common.dataobjects;

import java.io.Serializable;


import common.utilities.DateString;

public class Note implements Serializable{

	private static final long serialVersionUID = 1775497913240870440L;
	private int personId;
	private int id;
	private String title;
	private DateString timeAdded;
	private Meeting appointment;
	private boolean hasRead;
	
	public Note (String title, Meeting appointment){
		this.title = title;
		this.appointment = appointment;
	}
	
	public Note(int id, String title, DateString timeAdded, Meeting appointment, boolean hasRead){
		this.id = id;
		this.title = title;
		this.timeAdded = timeAdded;
		this.appointment = appointment;
		this.hasRead = hasRead;
	}
	
	public Meeting getAppointment() {
		return appointment;
	}
	
	public boolean isRead(){
		return hasRead;
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
	
	@Override
	public String toString() {
		return this.title;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}
}
