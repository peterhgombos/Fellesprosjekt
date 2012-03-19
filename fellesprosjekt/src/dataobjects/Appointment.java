package dataobjects;

import java.sql.Date;

import utilities.XMLElements;


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
	
	public Appointment() {
		
	}
	
	public int getId() {
		return id;
	}


	public void setId(int appointmentId) {
		this.id = appointmentId;
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
	
	//TODO Sjekke at det er lovlig sluttid
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
	
}
