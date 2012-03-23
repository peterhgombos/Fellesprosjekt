package common.dataobjects;

import java.util.ArrayList;

import client.connection.ServerData;

import common.utilities.DateString;

public class Room {
	
	private String room;
	private int capasity;
	
	public Room(String room, int capasity){
		this.capasity = capasity;
		this.room = room;
	}
	
//	public boolean isAvailable(){
//		return true;	
//	}
	
	public void bookRoom(DateString startTime, DateString endTime){
		//ServerData.
	}
	
	public String getRomId(){
		return room;
	}
}
