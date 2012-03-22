package client.gui;

import java.util.Calendar;

import javax.swing.table.AbstractTableModel;

import client.connection.ServerData;

import common.dataobjects.InternalCalendar;

@SuppressWarnings("serial")
public class CalModel extends AbstractTableModel{

	private int week;
	private int year;
	private InternalCalendar cal;
	
	public CalModel(){
		cal = ServerData.getCalendar();
		cal.getCalendar().setTimeInMillis(System.currentTimeMillis());
		this.year = cal.getCalendar().get(Calendar.YEAR);
		this.week = cal.getCalendar().get(Calendar.WEEK_OF_YEAR);
	}
	
	public int getYear(){
		return cal.getCalendar().get(Calendar.YEAR);
	}
	
	public int getWeek(){
		System.out.println(this.week);
		return cal.getCalendar().get(Calendar.WEEK_OF_YEAR);
	}
	
	public void setDate(){
		//TODO naviger til dato
	}
	public void setWeek(int weekNumber){
		cal = ServerData.getCalendar();
		cal.getCalendar().set(Calendar.WEEK_OF_YEAR, weekNumber);
		week = cal.getCalendar().get(Calendar.WEEK_OF_YEAR);
		
	}
	
	public void setYear(int year){
		cal = ServerData.getCalendar();
		cal.getCalendar().set(Calendar.YEAR, year);
		this.year = cal.getCalendar().get(Calendar.YEAR);
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
