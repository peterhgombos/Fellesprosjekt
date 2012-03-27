package common.dataobjects;

import java.io.Serializable;
import java.util.HashMap;

import common.utilities.DateString;

public class Meeting extends Appointment implements Serializable{

	private static final long serialVersionUID = -8326127541644962045L;

	public static final int SVAR_BLANK = 0;
	public static final int SVAR_OK = 1;
	public static final int SVAR_NEI = 2;

	private Room room;
	private HashMap<Person, Integer> participants;
	private int externalParticipants;

	public Meeting(int id, Person leader, Person owner, String title, String description, String place, Room room, DateString start, DateString end, HashMap<Person, Integer> participants, int externalParticipants){
		super(id, leader, owner, title, description, place, start, end);
		this.participants = participants;
		this.room = room;
		this.externalParticipants = externalParticipants;
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
	public HashMap<Integer, Integer> getANSWERSParticipants() {
		HashMap<Integer, Integer> newp = new HashMap<Integer, Integer>();
		for(Person p: participants.keySet()){
			newp.put(p.getPersonID(), participants.get(p));
		}
		return newp;
	}

	//Sjekke om deltakeren er med fra før?
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


	public int getNumberOfParticipants(){
		return participants.size() + externalParticipants;
	}

	public void changeCountExternalParticipants(int num){
		this.externalParticipants = num;
	}

}
