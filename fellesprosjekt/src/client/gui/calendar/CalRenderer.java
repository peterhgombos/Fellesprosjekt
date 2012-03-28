package client.gui.calendar;

import java.awt.Color;
import java.awt.Component;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import client.Client;
import client.connection.ServerData;

import common.dataobjects.Appointment;
import common.dataobjects.Meeting;
import common.dataobjects.Person;

@SuppressWarnings("serial")
public class CalRenderer extends DefaultTableCellRenderer {

	DecimalFormat format = new DecimalFormat("00");
	
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
			int bredde = table.getColumnModel().getColumn(column).getWidth() / result.size();
			int i = 0;
			for(int j = 0; j < result.size(); j++){
				
				Appointment a = result.get(j);
				
				JPanel innerpanel = new JPanel(null);
				
				innerpanel.setBackground(a.getColor());
				innerpanel.setBounds(bredde * i, 0, bredde , table.getRowHeight());
				panel.add(innerpanel);
				
				if(row == 0 || ServerData.getCalendar().startsInHour(a, model.getYear(), model.getWeek(), ((column - 1) * 24) + row)){
					
					JLabel title = new JLabel(" " + a.getTitle());
					
					JLabel stime = new JLabel(" Fra: " + format.format(a.getStartTime().getHour()) + ":" + format.format(a.getStartTime().getMinute()));
					JLabel etime = new JLabel(" Til: " + format.format(a.getEndTime().getHour()) + ":" + format.format(a.getEndTime().getMinute()));
					JLabel minfo = null;
					JLabel owner = null;
					
					if(a.getowner().getId() != Client.getUser().getId()){
						owner = new JLabel("  "  + a.getowner().getUsername());
						a.setColor(ColorPicker.otherColor());
						innerpanel.setBackground(a.getColor());
					}
					
					title.setBounds(0, 3, bredde , 14);
					stime.setBounds(0, title.getY() + title.getHeight(), bredde, 14);
					etime.setBounds(0, stime.getY() + stime.getHeight(), bredde, 14);
					
					if(a instanceof Meeting){
						Meeting m = (Meeting)a;
						int deltakere = 0;
						int ja = 0;
						for(Person per : m.getParticipants().values()){
							if(m.getAnswers().get(per.getId()) == Meeting.SVAR_OK){
								ja++;
								deltakere++;
							}else{
								deltakere++;
							}
						}
						minfo = new JLabel(" Kommer: " + ja + "/" + (deltakere-1));
						minfo.setBounds(0, etime.getY() + etime.getHeight(), bredde, 14);
					}
					
					title.setBackground(innerpanel.getBackground());
					stime.setBackground(innerpanel.getBackground());
					etime.setBackground(innerpanel.getBackground());
					
					
					innerpanel.add(title);
					innerpanel.add(stime);
					innerpanel.add(etime);
					if(minfo != null){
						innerpanel.add(minfo);
						minfo.setBackground(innerpanel.getBackground());
					}
					if(owner != null){
						if(minfo == null){
							owner.setBounds(0, etime.getY() + etime.getHeight(), bredde, 14);
						}else{
							owner.setBounds(0, minfo.getY() + minfo.getHeight(), bredde, 14);
						}
						owner.setBackground(innerpanel.getBackground());
						innerpanel.add(owner);
					}
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
