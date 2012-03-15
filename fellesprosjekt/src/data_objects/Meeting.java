package data_objects;

import java.util.HashMap;

public class Meeting extends Appointment{
	
	//Integer er svaret fra person
	private Room room;
	private HashMap<Person, Integer> participants;
	
	public Room getRoom() {
		return room;
	}
	
	public void setRoom(Room room) {
		this.room = room;
	}
	
	public HashMap<Person, Integer> getParticipants() {
		return participants;
	}
	
	public void addParticipant(Person person, int answear) {
		participants.put(person, answear);
	}
	
	public void removeParticipants(Person person){
		participants.remove(person);
	}
	
	
	
	
}
