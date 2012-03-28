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
	private HashMap<Integer, Integer> answers;
	private HashMap<Integer, Person> members;
	private int externalParticipants;

	public Meeting(int id, Person leader, Person owner, String title, String description, String place, Room room, DateString start, DateString end, HashMap<Person, Integer> participants, int externalParticipants){
		super(id, leader, owner, title, description, place, start, end);
		this.room = room;
		this.externalParticipants = externalParticipants;
		
		answers = new HashMap<Integer, Integer>();
		members = new HashMap<Integer, Person>();
		
		for(Person p: participants.keySet()){
			answers.put(p.getId(), participants.get(p));
			members.put(p.getId(), p);
		}
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public HashMap<Integer, Person> getParticipants() {
		return members;
	}
	public HashMap<Integer, Integer> getAnswers(){
		return answers;
	}
	
	public void addParticipant(Person person, int answear) {
		members.put(person.getId(), person);
		answers.put(person.getId(), answear);
	}

	public void removeParticipants(Person person){
		members.remove(person.getId());
		answers.remove(person.getId());
	}
	public void removeParticipant(int id){
		members.remove(id);
		answers.remove(id);
	}

	public void changeParticipantAnswer(Person person, int answear){
		answers.put(person.getId(), answear);
	}

	public int getNumberOfParticipants(){
		return members.values().size() + externalParticipants;
	}

	public void changeCountExternalParticipants(int num){
		this.externalParticipants = num;
	}

}
