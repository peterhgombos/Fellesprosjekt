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
		reset();
	}
	public void reset(){
		calendar = new GregorianCalendar();
		calendar.setTimeZone(TimeZone.getDefault());
		calendar.setMinimalDaysInFirstWeek(4);

		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTimeInMillis(System.currentTimeMillis());

		apps = new HashMap<Integer, HashMap<Integer,ArrayList<Appointment>[]>>();
	}


	@SuppressWarnings("unchecked")
	private void addToCal(Appointment app){

		calendar.set(app.getStartTime().getYear(), app.getStartTime().getMonth() -1, app.getStartTime().getDay(), app.getStartTime().getHour(), 0);
		int year = calendar.get(Calendar.YEAR);
		int week = calendar.get(Calendar.WEEK_OF_YEAR);
		int day = dayToWeekDay(calendar.get(Calendar.DAY_OF_WEEK));
		int hourInWeek = day * 24 + calendar.get(Calendar.HOUR_OF_DAY);

		calendar.set(app.getEndTime().getYear(), app.getEndTime().getMonth() -1, app.getEndTime().getDay(), app.getEndTime().getHour(), app.getEndTime().getMinute());
		int eYear = calendar.get(Calendar.YEAR);
		int eDay = dayToWeekDay(calendar.get(Calendar.DAY_OF_WEEK));
		int eWeek = calendar.get(Calendar.WEEK_OF_YEAR);
		int eHourInWeek = eDay * 24 + calendar.get(Calendar.HOUR_OF_DAY);
		
		int eMin = calendar.get(Calendar.MINUTE);
		if(eMin > 15){
			eHourInWeek++;
		}

		
		int weeks = 1;
		weeks += eWeek - week;
		
		for (int j = 0; j < weeks; j++) {
			int start = j == 0 ? hourInWeek : 0;
			int slutt = j == (weeks -1) ? eHourInWeek: 24*7;

			calendar.set(Calendar.WEEK_OF_YEAR, week + j);

			int curryear = calendar.get(Calendar.YEAR);
			HashMap<Integer, ArrayList<Appointment>[]> tempyear = apps.get(curryear);
			if(tempyear == null){
				tempyear = new HashMap<Integer, ArrayList<Appointment>[]>();
				apps.put(curryear, tempyear);
			}

			int currweek =  calendar.get(Calendar.WEEK_OF_YEAR);
			ArrayList<Appointment>[] arr = tempyear.get(currweek);
			if(arr == null){
				arr = (ArrayList<Appointment>[]) new ArrayList[24*7];
				tempyear.put(currweek, arr);
			}else {
				tempyear.put(currweek, arr);
			}


			for(int i = start; i < slutt; i++) {
				if(arr[i] == null){
					arr[i] = new ArrayList<Appointment>(1);
				}
				arr[i].add(app);
			}
		}
	}

	public int dayToWeekDay(int day){
		if(day == Calendar.SATURDAY){
			return 5;
		}else if(day == Calendar.SUNDAY){
			return 6;
		}
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

		if(year == cyear && week == cweek && hour == chourInWeek){
			return true;
		}
		return false;
	}

	public String getDateForDay(int year, int week, int day){
		int currentday = 1;
		if(day == 1 ){
			currentday = Calendar.MONDAY;
		}else if(day == 2 ){
			currentday = Calendar.TUESDAY;
		}else if(day == 3 ){
			currentday = Calendar.WEDNESDAY;
		}else if(day == 4 ){
			currentday = Calendar.THURSDAY;
		}else if(day == 5 ){
			currentday = Calendar.FRIDAY;
		}else if(day == 6 ){
			currentday = Calendar.SATURDAY;
		}else if(day == 7 ){
			currentday = Calendar.SUNDAY;
		}

		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.WEEK_OF_YEAR, week);
		calendar.set(Calendar.DAY_OF_WEEK, currentday);

		return " " +calendar.get(Calendar.DAY_OF_MONTH)+"."+ (calendar.get(Calendar.MONTH)+1);
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
		for(Meeting a: meetings){
			addToCal(a);
		}
	}

	public GregorianCalendar getCalendar() {
		return calendar;
	}
}
