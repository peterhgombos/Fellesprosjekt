package client.gui.calendar;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import client.connection.ServerData;
import client.gui.CalendarPanel;

import common.dataobjects.Appointment;

@SuppressWarnings("serial")
public class CalRenderer extends DefaultTableCellRenderer {

	DecimalFormat format = new DecimalFormat("00");
	CalendarPanel panel;
	
	public ArrayList<JButton> getButtons;
	
	private class TitleAction implements ActionListener{
		private Appointment appointment;
		public TitleAction(Appointment app){
			appointment = app;
		}
		public void actionPerformed(ActionEvent e){
			System.out.println("YESSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSs");
			//panel.goToAppointmentView(appointment);
		}
	}
	
	public CalRenderer(CalendarPanel panel){
		this.panel = panel;
		getButtons = new ArrayList<JButton>();
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
		
		if(column == 0){
			return hourrenderer(row);
		}
		
		CalModel model = (CalModel)table.getModel();
		
		ArrayList<Appointment> result = ServerData.getCalendar().getAppointments(model.getYear(), model.getWeek(), column - 1, row);
		
		JPanel panel = new JPanel(null);
		panel.setBackground(new Color(0xFFFFFF));
		if(result != null){
			int bredde = 134 / result.size();
			int i = 0;
			ArrayList<Appointment> clone = (ArrayList<Appointment>)result.clone();
			for(Appointment a : clone){
				
				JPanel innerpanel = new JPanel(null);
				
				innerpanel.setBackground(a.getColor());
				innerpanel.setBounds(bredde * i, 0, bredde , 60);
				panel.add(innerpanel);
				
				if(row == 0 || ServerData.getCalendar().startsInHour(a, model.getYear(), model.getWeek(), ((column - 1) * 24) + row)){
					
					JButton title = new JButton(" " + a.getTitle());
					title.addActionListener(new TitleAction(a));
					getButtons.add(title);
					
					JLabel stime = new JLabel(" Fra: " + format.format(a.getStartTime().getHour()) + ":" + format.format(a.getStartTime().getMinute()));
					JLabel etime = new JLabel(" Til: " + format.format(a.getEndTime().getHour()) + ":" + format.format(a.getEndTime().getMinute()));
					
					title.setBounds(0, 3, bredde , 14);
					stime.setBounds(0, title.getY() + title.getHeight(), bredde, 14);
					etime.setBounds(0, stime.getY() + stime.getHeight(), bredde, 14);
					
					title.setBackground(innerpanel.getBackground());
					stime.setBackground(innerpanel.getBackground());
					etime.setBackground(innerpanel.getBackground());
					
					innerpanel.add(title);
					innerpanel.add(stime);
					innerpanel.add(etime);
				}
				i++;
			}
		}
		return panel;
	}
	
	public JLabel hourrenderer(int row){
		JLabel label = new JLabel(" " + format.format(row) + ".00");
		label.setOpaque(true);
		return label;
	}
}
