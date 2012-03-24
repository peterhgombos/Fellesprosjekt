package common.dataobjects;

import java.io.Serializable;

public class Room implements Serializable {

	private static final long serialVersionUID = -6176491067195878044L;

	private String room;
	private int capasity;

	public Room(String room, int capasity){
		this.capasity = capasity;
		this.room = room;
	}

	public int getCapasity(){
		return capasity;	
	}

	public String getRomId(){
		return room;
	}
}
