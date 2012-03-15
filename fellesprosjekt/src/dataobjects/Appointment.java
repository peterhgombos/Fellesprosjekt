package dataobjects;

import java.sql.Date;

public class Appointment {

	private String apponintmentTitle;
	private String apponintmentDescription;
	private Date startTime;
	private Date endTime;
	private String place;
	
	//Må sjekke start tid mot slutt tid
	public Appointment(String aTitle, Date start, Date end) {
		this.apponintmentTitle = aTitle;
		this.startTime = start;
		this.endTime = end;
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
	
}
