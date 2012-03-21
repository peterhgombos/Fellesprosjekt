package common.dataobjects;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.TimeZone;

import client.Client;

public class InternalCalendar {
	
	private GregorianCalendar calendar;
	
	
	private HashMap<Integer, HashMap<Integer, ArrayList<Appointment>[]>> apps;
	
	public InternalCalendar() {
		calendar = new GregorianCalendar();
		calendar.setTimeZone(TimeZone.getDefault());
		calendar.setMinimalDaysInFirstWeek(4);
		
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTimeInMillis(System.currentTimeMillis());
		
		apps = new HashMap<Integer, HashMap<Integer,ArrayList<Appointment>[]>>();
		
		
	}
	
	@SuppressWarnings("unchecked")
	private void addToCal(Appointment app){
		Client.console.writeline("addtocal");
		
		calendar.setTime(new Date(app.getStartTime().getTime()));
		int year = calendar.get(Calendar.YEAR);
		int week = calendar.get(Calendar.WEEK_OF_YEAR);
		int day = dayToWeekDay(calendar.get(Calendar.DAY_OF_WEEK));
		int hourInWeek = day * 24 + calendar.get(Calendar.HOUR);
		
		Client.console.writeline(year + " " + week + " " + day + " " + hourInWeek) ;
		
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
			Client.console.writeline(app.getTitle() + " " + year + " " + week);
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
	
	public void addAppointment(Appointment appointment){
		addToCal(appointment);
	}
	
	public void addMeeting(Meeting meeting){
		addToCal(meeting);
	}
	public void addAppointments(Collection<Appointment> appointments){
		for(Appointment a: appointments){
			addToCal(a);
		}
	}
	public void addMeetings(Collection<Meeting> meetings){
		for(Appointment a: meetings){
			addToCal(a);
		}
	}
}
