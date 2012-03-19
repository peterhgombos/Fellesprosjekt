package dataobjects;

import java.sql.Date;

import client.connection.XMLElements;

public class Appointment {

	private int id; 
	private Person leader;
	private String title;
	private String description;
	private Date startTime;
	private Date endTime;
	private String place;
	
	//M� sjekke start tid mot slutt tid
	public Appointment(int id, Person leader, String title, String description, Date start, Date end) {
		this.id = id;
		this.leader = leader;
		this.title = title;
		this.description = description;
		this.startTime = start;
		this.endTime = end;
	}
	
	
	public int getId() {
		return id;
	}


	public void setId(int appointmentId) {
		this.id = appointmentId;
	}


	public void setAppointmentLeader(Person appointmentLeader) {
		this.leader = appointmentLeader;
	}


	public String getApponintmentTitle() {
		return title;
	}

	public void setApponintmentTitle(String apponintmentTitle) {
		this.title = apponintmentTitle;
	}

	public String getApponintmentDescription() {
		return description;
	}

	public void setApponintmentDescription(String apponintmentDescription) {
		this.description = apponintmentDescription;
	}

	public Date getStartTime() {
		return startTime;
	}

	//Sjekke at det er lovlig starttid
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}
	
	//Sjekke at det er lovlig sluttid
	public void setEndTime(Date endTime) {
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
		xml.append(XMLElements.openXML(XMLElements.TITLE) + getApponintmentTitle() + XMLElements.closeXML(XMLElements.TITLE));
		xml.append(XMLElements.openXML(XMLElements.DESCRIPTION) + getApponintmentDescription() + XMLElements.closeXML(XMLElements.DESCRIPTION));
		xml.append(XMLElements.openXML(XMLElements.STARTTIME) + getStartTime() + XMLElements.closeXML(XMLElements.STARTTIME));
		xml.append(XMLElements.openXML(XMLElements.ENDTIME) + getEndTime() + XMLElements.closeXML(XMLElements.ENDTIME));
		xml.append(XMLElements.openXML(XMLElements.PLACE) + getPlace() + XMLElements.closeXML(XMLElements.PLACE));
		xml.append(XMLElements.closeXML(XMLElements.APPOINTMENT));
		return xml.toString();
	}
	
}
