package common.dataobjects;

import java.awt.Color;
import java.io.Serializable;

import xml.XMLElements;
import client.gui.calendar.ColorPicker;

import common.utilities.DateString;

public class Appointment implements Serializable, Comparable<Appointment> {

	private static final long serialVersionUID = 3662988846703010000L;

	private int id; 
	private Person leader;
	private Person owner;
	private String title;
	private String description;
	private DateString startTime;
	private DateString endTime;
	private String place;
	
	private Color color;

	public Appointment(int id, Person leader, Person owner, String title, String description, String place, DateString start, DateString end) throws RuntimeException {
		this.id = id;
		this.leader = leader;
		this.title = title;
		this.description = description;
		this.startTime = start;
		this.endTime = end;
		this.place = place;
		this.owner = owner;
		color = ColorPicker.nextColor();
	}

	public Person getowner(){
		return owner;
	}
	
	public void setColor(Color c){
		color = c;
	}
	
	public Appointment() {
		
	}

	public int getId() {
		return id;
	}


	public void setId(int appointmentId) {
		this.id = appointmentId;
	}


	public DateString getStartTime() {
		return startTime;
	}

	public void setStartTime(DateString startTime) {
		this.startTime = startTime;
	}

	public DateString getEndTime() {
		return endTime;
	}

	public void setEndTime(DateString endTime) {
		this.endTime = endTime;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Person getAppointmentLeader() {
		return leader;
	}

	// makes XML for appointment class
	public String toXML(){
		StringBuilder xml = new StringBuilder();
		xml.append(XMLElements.openXML(XMLElements.APPOINTMENT));
		xml.append(XMLElements.openXML(XMLElements.APPOINTMENT_ID) + getId() + XMLElements.closeXML(XMLElements.APPOINTMENT_ID));
		xml.append(XMLElements.openXML(XMLElements.LEADER) + getAppointmentLeader().toXML() + XMLElements.closeXML(XMLElements.LEADER));
		xml.append(XMLElements.openXML(XMLElements.TITLE) + getTitle() + XMLElements.closeXML(XMLElements.TITLE));
		xml.append(XMLElements.openXML(XMLElements.DESCRIPTION) + getDescription() + XMLElements.closeXML(XMLElements.DESCRIPTION));
		xml.append(XMLElements.openXML(XMLElements.STARTTIME) + getStartTime() + XMLElements.closeXML(XMLElements.STARTTIME));
		xml.append(XMLElements.openXML(XMLElements.ENDTIME) + getEndTime() + XMLElements.closeXML(XMLElements.ENDTIME));
		xml.append(XMLElements.openXML(XMLElements.PLACE) + getPlace() + XMLElements.closeXML(XMLElements.PLACE));
		xml.append(XMLElements.closeXML(XMLElements.APPOINTMENT));
		return xml.toString();
	}


	public Person getLeader() {
		return leader;
	}


	public void setLeader(Person leader) {
		this.leader = leader;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Color getColor(){
		return color;
	}

	@Override
	public int compareTo(Appointment o) {
		return startTime.toString().compareTo(((Appointment)o).startTime.toString());
	}
	
	public String toString(){
		String start = getStartTime().getDay() +"."+ getStartTime().getMonth() + "." + getStartTime().getYear() 
				+ "  kl: " + getStartTime().getHour() +":"+ getStartTime().getMinute();
		String end = getEndTime().getDay() +"."+ getEndTime().getMonth() + "." + getEndTime().getYear() 
				+ "  kl: " + getEndTime().getHour() +":"+ getEndTime().getMinute();
		
		return getTitle() + "   " + start + " - " +end;
	}

}
