package dataobjects;

import java.sql.Date;

import client.connection.XMLElements;

public class Appointment {

	private int appointmentId; 
	private Person appointmentLeader;
	private String apponintmentTitle;
	private String apponintmentDescription;
	private Date startTime;
	private Date endTime;
	private String place;
	
	//M� sjekke start tid mot slutt tid
	public Appointment(Person leader, String aTitle, Date start, Date end) {
		this.appointmentLeader = leader;
		this.apponintmentTitle = aTitle;
		this.startTime = start;
		this.endTime = end;
	}
	
	
	public int getAppointmentId() {
		return appointmentId;
	}


	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}


	public void setAppointmentLeader(Person appointmentLeader) {
		this.appointmentLeader = appointmentLeader;
	}


	public String getApponintmentTitle() {
		return apponintmentTitle;
	}

	public void setApponintmentTitle(String apponintmentTitle) {
		this.apponintmentTitle = apponintmentTitle;
	}

	public String getApponintmentDescription() {
		return apponintmentDescription;
	}

	public void setApponintmentDescription(String apponintmentDescription) {
		this.apponintmentDescription = apponintmentDescription;
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
		return appointmentLeader;
	}
	
	public String toXML(){
		StringBuilder xml = new StringBuilder();
		xml.append(XMLElements.openXML(XMLElements.APPOINTMENT));
		xml.append(XMLElements.openXML(XMLElements.APPOINTMENT_ID) + getAppointmentId() + XMLElements.closeXML(XMLElements.APPOINTMENT_ID));
		
		return xml.toString();
	}
	
}
