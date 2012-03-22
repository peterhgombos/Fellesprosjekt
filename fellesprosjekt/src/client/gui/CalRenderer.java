package client.gui;

import java.awt.Color;
import java.awt.Component;
import java.text.DecimalFormat;
import java.util.Collection;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import client.connection.ServerData;

import common.dataobjects.Appointment;
import common.dataobjects.InternalCalendar;

@SuppressWarnings("serial")
public class CalRenderer extends DefaultTableCellRenderer {

	DecimalFormat format = new DecimalFormat("00");
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
		
		if(column == 0){
			return hourrenderer(row);
		}
		
		InternalCalendar calendar = ServerData.getCalendar();
		
		CalModel model = (CalModel)table.getModel();
		
		Collection<Appointment> result = calendar.getAppointments(model.getYear(), model.getWeek(), column - 1, row);
		
		JPanel panel = new JPanel(null);
		panel.setBackground(new Color(0xFFFFFF));
		if(result != null){
			int bredde = 134 / result.size();
			int i = 0;
			for(Appointment a : result){
				
				JPanel innerpanel = new JPanel(null);
				
				innerpanel.setBackground(a.getColor());
				innerpanel.setBounds(bredde * i, 0, bredde , 60);
				panel.add(innerpanel);
				
				if(calendar.startsInHour(a, model.getYear(), model.getWeek(), ((column - 1) * 24) + row)){
					
					JLabel title = new JLabel(" " + a.getTitle());
					JLabel stime = new JLabel(" " + format.format(a.getStartTime().getHour()) + ":" + format.format(a.getStartTime().getMinute()));
					JLabel etime = new JLabel(" " + format.format(a.getEndTime().getHour()) + ":" + format.format(a.getEndTime().getMinute()));
					
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
		JLabel label = new JLabel();
		label.setOpaque(true);
		
		label.setText(" " + format.format(row) + ".00");
		
		return label;
	}
}
