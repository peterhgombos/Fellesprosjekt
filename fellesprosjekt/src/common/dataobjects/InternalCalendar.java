package common.dataobjects;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.TimeZone;

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
		
		System.out.println("addtocal" + app.getTitle());
		
		calendar.set(app.getStartTime().getYear(), app.getStartTime().getMonth() -1, app.getStartTime().getDay(), app.getStartTime().getHour(), 0);
		int year = calendar.get(Calendar.YEAR);
		int week = calendar.get(Calendar.WEEK_OF_YEAR);
		int day = dayToWeekDay(calendar.get(Calendar.DAY_OF_WEEK));
		int hourInWeek = day * 24 + calendar.get(Calendar.HOUR_OF_DAY);
		
		calendar.set(app.getEndTime().getYear(), app.getEndTime().getMonth() -1, app.getEndTime().getDay(), app.getEndTime().getHour(), 0);
		int eDay = dayToWeekDay(calendar.get(Calendar.DAY_OF_WEEK));
		int eHourInWeek = eDay * 24 + calendar.get(Calendar.HOUR_OF_DAY);
		
		HashMap<Integer, ArrayList<Appointment>[]> tempyear = apps.get(year);
		if(tempyear == null){
			tempyear = new HashMap<Integer, ArrayList<Appointment>[]>();
			apps.put(year, tempyear);
		}
		
		ArrayList<Appointment>[] arr = tempyear.get(week);
		if(arr == null){
			arr = (ArrayList<Appointment>[]) new ArrayList[24*7];
			tempyear.put(week, arr);
		}else {
			tempyear.put(week, arr);
		}
		System.out.println(hourInWeek + " " + eHourInWeek);
		for(int i = hourInWeek; i < eHourInWeek; i++){
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
	
	public boolean startsInHour(Appointment a, int year, int week, int hour){
		calendar.set(a.getStartTime().getYear(), a.getStartTime().getMonth() -1, a.getStartTime().getDay(), a.getStartTime().getHour(), 0);
		int cyear = calendar.get(Calendar.YEAR);
		int cweek = calendar.get(Calendar.WEEK_OF_YEAR);
		int cday = dayToWeekDay(calendar.get(Calendar.DAY_OF_WEEK));
		int chourInWeek = cday * 24 + calendar.get(Calendar.HOUR_OF_DAY);
		
		System.out.println(hour);
		System.out.println(chourInWeek);
		
		if(year == cyear && week == cweek && hour == chourInWeek){
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

	public GregorianCalendar getCalendar() {
		return calendar;
	}
}
