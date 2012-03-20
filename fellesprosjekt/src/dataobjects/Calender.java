package dataobjects;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class Calender {
	
	private int weeknumber;
	
	private GregorianCalendar calendar = new GregorianCalendar();
	
	private HashMap<Integer, HashMap<Integer, ArrayList<Appointment>[]>> apps;
	
	@SuppressWarnings("unchecked")
	public Calender(ArrayList<Appointment> appointments, ArrayList<Meeting> meetings, int weeknumber) {
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		
		this.weeknumber = weeknumber;
		
		apps = new HashMap<Integer, HashMap<Integer,ArrayList<Appointment>[]>>();
		
		for(Appointment app: appointments){
			addToCal(app);
		}
		for(Meeting mee: meetings){
			addToCal(mee);
		}
		calendar.setTimeInMillis(System.currentTimeMillis());
	}
	
	private void addToCal(Appointment app){
		calendar.setTime(new Date(app.getStartTime().getTime()));
		int year = calendar.get(Calendar.YEAR);
		int week = calendar.get(Calendar.WEEK_OF_YEAR);
		int day = dayToWeekDay(calendar.get(Calendar.DAY_OF_WEEK));
		int hourInWeek = day * 24 + calendar.get(Calendar.HOUR);
		
		calendar.setTime(new Date(app.getEndTime().getTime()));
		int eDay = dayToWeekDay(calendar.get(Calendar.DAY_OF_WEEK));
		int eHourInWeek = eDay * 24 + calendar.get(Calendar.HOUR);
		int duration = eHourInWeek - hourInWeek;
		
		HashMap<Integer, ArrayList<Appointment>[]> tempyear = apps.get(year);
		if(tempyear == null){
			tempyear = new HashMap<Integer, ArrayList<Appointment>[]>();
		}
		
		ArrayList<Appointment>[] arr = tempyear.get(week);
		if(arr == null){
			arr = (ArrayList<Appointment>[]) new ArrayList[24*7];
			tempyear.put(week, arr);
		}
		for(int i = hourInWeek; i < hourInWeek + duration; i++){
			if(arr[i] == null){
				arr[i] = new ArrayList<Appointment>(1);
			}
			arr[i].add(app);
		}
	}
	
	public int dayToWeekDay(int day){
		return day - 2;
	}
	
	public ArrayList<Appointment> getAppointments(int year, int week, int day, int hour){
		HashMap<Integer, ArrayList<Appointment>[]> tempyear = apps.get(year);
		if(tempyear == null){
			return null;
		}
		ArrayList<Appointment>[] arr = tempyear.get(week);
		if(arr == null){
			return null;
		}
		return arr[day*24 + hour];
	}
	public boolean startsInHour(Appointment a, int hour){
		calendar.setTime(new Date(a.getStartTime().getTime()));
		if(hour == calendar.get(Calendar.HOUR_OF_DAY)){
			return true;
		}
		return false;
	}
	
	public int nextWeek(){
		return weeknumber+1;
	}
	
	public int lastWeek(){
		return weeknumber-1;
	}
	
	public void addAppointment(Appointment appointment){
		addToCal(appointment);
	}
	
	public void addMeeting(Meeting meeting){
		addToCal(meeting);
	}
}
