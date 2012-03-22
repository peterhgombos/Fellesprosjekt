package client.gui;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class CalModel extends AbstractTableModel{

	private int week;
	private int year;
	
	public CalModel(int year, int week){
		this.year = year;
		this.week = week;
	}
	
	public int getYear(){
		return year;
	}
	
	public int getWeek(){
		return week;
	}
	
	public void setDate(){
		//TODO naviger til dato
	}
	public void setWeek(int weekNumber){
		week = weekNumber;
	}
	
	public void setYear(int year){
		this.year = year;
	}
	
	public void nextWeek(){
		week++;
	}
	public void lastWeek(){
		week--;
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
