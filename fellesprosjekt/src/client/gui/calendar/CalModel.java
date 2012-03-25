package client.gui.calendar;

import java.util.Calendar;

import javax.swing.table.AbstractTableModel;

import client.connection.ServerData;

import common.dataobjects.InternalCalendar;

@SuppressWarnings("serial")
public class CalModel extends AbstractTableModel{

	private int week;
	private int year;
	private InternalCalendar cal;
	private client.gui.calendar.Calendar listener;
	
	public CalModel(client.gui.calendar.Calendar calendar){
		listener = calendar;
		cal = ServerData.getCalendar();
		this.year = cal.getCalendar().get(Calendar.YEAR);
		this.week = cal.getCalendar().get(Calendar.WEEK_OF_YEAR);
	}
	
	public int getYear(){
		return year;
	}
	
	public int getWeek(){
		return week;
	}

	public void setWeek(int weekNumber){
		cal = ServerData.getCalendar();
		cal.getCalendar().set(Calendar.WEEK_OF_YEAR, weekNumber);
		week = cal.getCalendar().get(Calendar.WEEK_OF_YEAR);
		listener.weekChanged();
	}
	
	public void setYear(int year){
		cal = ServerData.getCalendar();
		cal.getCalendar().set(Calendar.YEAR, year);
		this.year = cal.getCalendar().get(Calendar.YEAR);
		listener.weekChanged();
	}
	
	public void nextWeek(){
		InternalCalendar cal;
		cal = ServerData.getCalendar();
		cal.getCalendar().set(Calendar.YEAR, year);
		
		if(week < (cal.getCalendar().getActualMaximum(Calendar.WEEK_OF_YEAR))){
			cal.getCalendar().set(Calendar.WEEK_OF_YEAR, week++);
		}
		
		else{
			week = 1;
			cal.getCalendar().set(Calendar.YEAR, year++);
			cal.getCalendar().set(Calendar.WEEK_OF_YEAR, week);
			
		}
		listener.weekChanged();
	}
	public void lastWeek(){

		cal = ServerData.getCalendar();
		cal.getCalendar().set(Calendar.YEAR, year);
		if(week > 1 && week <= (cal.getCalendar().getActualMaximum(Calendar.WEEK_OF_YEAR))){
			cal.getCalendar().set(Calendar.WEEK_OF_YEAR, week--);
		
		}
		else if(week == 1){
			year = cal.getCalendar().get(Calendar.YEAR) -1;
			cal.getCalendar().set(Calendar.YEAR, year);
			
			week = cal.getCalendar().getActualMaximum(Calendar.WEEK_OF_YEAR);
			cal.getCalendar().set(Calendar.WEEK_OF_YEAR, week);	
		}
		listener.weekChanged();
	}

	@Override
	public int getColumnCount(){
		return 8;
	}

	@Override
	public int getRowCount(){
		return 24;
	}

	@Override
	public Object getValueAt(int y, int x){
		return "";
	}

}
