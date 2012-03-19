package dataobjects;

import java.sql.Date;
import java.util.HashMap;

import client.connection.XMLElements;

public class Meeting extends Appointment{
	
	
	private Room room;
	//Integer er svaret fra person
	private HashMap<Person, Integer> participants;
	private int externalParticipants;
		
	public Meeting(int id, Person leader, String title, String description, Date start, Date end, HashMap<Person, Integer> participants){
		super(id, leader, title, description, start, end);
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
		xml.append(XMLElements.openXML(XMLElements.TITLE)+getApponintmentTitle() + XMLElements.closeXML(XMLElements.TITLE));
		xml.append(XMLElements.openXML(XMLElements.LEADER) + " " + XMLElements.closeXML(XMLElements.LEADER));
		xml.append(XMLElements.openXML(XMLElements.STARTTIME) + getStartTime() + XMLElements.closeXML(XMLElements.STARTTIME));
		xml.append(XMLElements.openXML(XMLElements.ENDTIME) + getEndTime() + XMLElements.closeXML(XMLElements.ENDTIME));
		xml.append(XMLElements.openXML(XMLElements.ROOM) + getRoom() + XMLElements.closeXML(XMLElements.ROOM));
		xml.append(XMLElements.openXML(XMLElements.PLACE) + getPlace() + XMLElements.closeXML(XMLElements.PLACE));
		xml.append(XMLElements.openXML(XMLElements.PARTICIPANT) + " " + XMLElements.closeXML(XMLElements.PARTICIPANT));
		xml.append(XMLElements.closeXML(XMLElements.MEETING));
		return xml.toString();
	}
	
}
