package common.dataobjects;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashMap;

import common.utilities.DateString;

import xml.XMLElements;


public class Meeting extends Appointment implements Serializable{
	
	private static final long serialVersionUID = -8326127541644962045L;
	
	public static final int SVAR_BLANK = 0;
	public static final int SVAR_OK = 1;
	public static final int SVAR_NEI = 2;
	
	private Room room;
	//Integer er svaret fra person
	private HashMap<Person, Integer> participants;
	private int externalParticipants;
		
	public Meeting(int id, Person leader, String title, String description, String place, DateString start, DateString end, HashMap<Person, Integer> participants){
		super(id, leader, title, description, place, start, end);
		this.participants = participants;
	}

	public Room getRoom() {
		return room;
	}
	
	public void setRoom(Room room) {
		this.room = room;
	}
	
	public HashMap<Person, Integer> getParticipants() {
		return participants;
	}
	
	//Sjekke om deltakeren er med fra f�r?
	public void addParticipant(Person person, int answear) {
		participants.put(person, answear);
	}
	
	public void removeParticipants(Person person){
		participants.remove(person);
	}
	
	public void changeParticipantAnswer(Person person, int answear){
		participants.remove(person);
		participants.put(person, answear);
	}
	
	public int getParticipantsCount(){
		return participants.size() + externalParticipants;
	}
	
	public void changeCountExternalParticipants(int num){
		this.externalParticipants = num;
	}
	
	public String toXML(){
		StringBuilder xml = new StringBuilder();
		xml.append(XMLElements.openXML(XMLElements.MEETING));
		xml.append(XMLElements.openXML(XMLElements.MEETING_ID) + getId() + XMLElements.closeXML(XMLElements.MEETING_ID));
		xml.append(XMLElements.openXML(XMLElements.TITLE)+getTitle() + XMLElements.closeXML(XMLElements.TITLE));
		xml.append(XMLElements.openXML(XMLElements.LEADER) + getAppointmentLeader().toXML() + XMLElements.closeXML(XMLElements.LEADER));
		xml.append(XMLElements.openXML(XMLElements.STARTTIME) + getStartTime() + XMLElements.closeXML(XMLElements.STARTTIME));
		xml.append(XMLElements.openXML(XMLElements.ENDTIME) + getEndTime() + XMLElements.closeXML(XMLElements.ENDTIME));
		xml.append(XMLElements.openXML(XMLElements.ROOM) + getRoom() + XMLElements.closeXML(XMLElements.ROOM));
		xml.append(XMLElements.openXML(XMLElements.PLACE) + getPlace() + XMLElements.closeXML(XMLElements.PLACE));
		xml.append(XMLElements.openXML(XMLElements.PARTICIPANT));
		for(Person p : participants.keySet()) xml.append(p.toXML()); 
		xml.append(XMLElements.closeXML(XMLElements.PARTICIPANT));
		xml.append(XMLElements.closeXML(XMLElements.MEETING));
		return xml.toString();
	}
	
}
