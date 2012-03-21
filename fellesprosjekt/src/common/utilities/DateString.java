package common.utilities;

import java.io.Serializable;

public class DateString implements Serializable {
	
	private static final long serialVersionUID = 3547507439592196795L;
	
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	private int second;

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	public int getHour() {
		return hour;
	}

	public int getMinute() {
		return minute;
	}

	public int getSecond() {
		return second;
	}

	public DateString(String s) {
		String[] datetime = s.split(" ");
		
		String[] date = datetime[0].split("-");
		String[] time = datetime[1].split(":");
		
		year = Integer.parseInt(date[0]);
		month = Integer.parseInt(date[1]);
		day = Integer.parseInt(date[2]);
		
		System.out.println(year + " " + month + " " + day);
		
		hour = Integer.parseInt(time[0]);
		minute = Integer.parseInt(time[1]);
		second = Integer.parseInt(time[2]);
		
		System.out.println("new datestring: " + hour + " " + minute + " " + second);
	}
	
	public boolean after(DateString s) {
		if (this.year > s.year && this.month > s.month && this.day > s.day && this.hour > s.hour && this.minute > s.minute) {
			return true;
		}
		return false;
	}
	
	public String toString() {
		return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
	}

}
