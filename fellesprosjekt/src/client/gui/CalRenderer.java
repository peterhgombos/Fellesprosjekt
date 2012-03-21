package client.gui;

import java.awt.Component;
import java.text.DecimalFormat;
import java.util.Collection;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import client.connection.MessageListener;
import client.connection.ServerData;

import common.dataobjects.Appointment;
import common.dataobjects.ComMessage;
import common.dataobjects.InternalCalendar;

@SuppressWarnings("serial")
public class CalRenderer extends DefaultTableCellRenderer {

	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
		
//		if(row == 0 && column != 0){
//			return dayrenderer(table, column);
//		}
		if(column == 0){
			return hourrenderer(row);
		}
		
		InternalCalendar calendar = ServerData.getCalendar();
		
		CalModel model = (CalModel)table.getModel();
		
		Collection<Appointment> result = calendar.getAppointments(model.getYear(), model.getWeek(), column - 1, row);
		
		JPanel panel = new JPanel(null);
		
		JLabel lab = new JLabel("test: " + (result == null ? "" : result.size()));
		panel.add(lab);
		lab.setBounds(0, 0, 70, 40);
		
		return lab;
		

//		JLabel tittel;
//		JLabel fra;
//		JLabel til;
//
//		if(value == null){
//			tittel = new JLabel("");
//			fra = new JLabel("");
//			til = new JLabel("");
//		}else{
//			App app = (App)value;
//
//			tittel = new JLabel(app.getTitle());
//			fra = new JLabel("fra: " + app.getFra() + "." + app.getFra());
//			til = new JLabel("til: " + app.getFra() + "." + app.getFra());
//		}
//
//		JPanel panel = new JPanel();
//		panel.setLayout(null);
//
//		panel.add(tittel);
//		panel.add(fra);
//		panel.add(til);
//
//		tittel.setBounds(2, 2, 50, 10);
//		fra.setBounds(2, tittel.getY() + tittel.getHeight() + 2, 50, 10);
//		til.setBounds(2, fra.getY() + fra.getHeight() + 2, 50, 10);
//
//		panel.setBounds(0, 0, 50, til.getY() +til.getHeight() +2);
//		panel.setPreferredSize(new Dimension(50, til.getY() +til.getHeight() + 2));
//
//		if (hasFocus) {
//			panel.setBackground(table.getSelectionBackground());
//			panel.setForeground(table.getSelectionForeground());
//
//			tittel.setBackground(table.getSelectionBackground());
//			tittel.setForeground(table.getSelectionForeground());
//
//			fra.setBackground(table.getSelectionBackground());
//			fra.setForeground(table.getSelectionForeground());
//
//			til.setBackground(table.getSelectionBackground());
//			til.setForeground(table.getSelectionForeground());
//			
//		}else {
//			panel.setBackground(table.getBackground());
//			panel.setForeground(table.getForeground());
//
//			tittel.setBackground(table.getBackground());
//			tittel.setForeground(table.getForeground());
//
//			fra.setBackground(table.getBackground());
//			fra.setForeground(table.getForeground());
//
//			til.setBackground(table.getBackground());
//			til.setForeground(table.getForeground());
//		}
//
//		tittel.setOpaque(true);
//		fra.setOpaque(true);
//		til.setOpaque(true);
//
//		return panel;
	}
	
	public JLabel hourrenderer(int row){
		JLabel label = new JLabel();
		label.setOpaque(true);
		
		DecimalFormat format = new DecimalFormat("00");
		
		label.setText(format.format(row) + ".00");
		
		return label;
	}

	public JLabel dayrenderer(JTable table, int column){
		JLabel label = new JLabel();
		label.setOpaque(true);

		if(column == 1){
			label.setText("Mandag");
		}else if(column == 2){
			label.setText("Tirsdag");
		}else if(column == 3){
			label.setText("Onsdag");
		}else if(column == 4){
			label.setText("Torsdag");
		}else if(column == 5){
			label.setText("Fredag");
		}else if(column == 6){
			label.setText("Lørdag");
		}else if(column == 7){
			label.setText("Søndag");
		}
		
		return label;
	}

}
