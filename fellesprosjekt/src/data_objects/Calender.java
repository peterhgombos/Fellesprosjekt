package data_objects;

import java.util.ArrayList;

public class Calender {
	
	private ArrayList<Appointment> appointments;
	private ArrayList<Meeting> meetings;
	private int weeknumber;
	
	public Calender(ArrayList<Appointment> appointments, ArrayList<Meeting> meetings, int weeknumber) {
		this.appointments = appointments;
		this.meetings = meetings;
		this.weeknumber = weeknumber;
	}
	
	public int nextWeek(){
		return weeknumber+1;
	}
	
	public int lastWeek(){
		return weeknumber-1;
	}
	
	public void addAppointments(Appointment appointment){
		appointments.add(appointment);
	}
	
	public void addMeetings(Meeting meeting){
		meetings.add(meeting);
	}
}
