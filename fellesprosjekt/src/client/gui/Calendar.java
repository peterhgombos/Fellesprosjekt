package client.gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import common.dataobjects.ComMessage;

import client.connection.MessageListener;
import common.utilities.MessageType;

@SuppressWarnings("serial")
public class Calendar extends JPanel implements MessageListener {

	private JTable table;
	private JScrollPane scrollPane;
	
	private String[] dayName = {"", "Mandag", "Tirsdag", "Onsag", "Torsdag", "Fredag", "Lørdag", "Søndag"};
	
	public Calendar(){
		setLayout(null);
		
		table = new JTable();
		
		CalRenderer renderer = new CalRenderer();
		table.setModel(new CalModel(2012, 11));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setRowHeight(60);
		table.setDefaultRenderer(Object.class, renderer);
		table.getColumnModel().getColumn(0).setMaxWidth(40);
		table.getColumnModel().getColumn(0).setHeaderValue(dayName[0]);
		for(int i = 1; i < table.getModel().getColumnCount(); i++){
			TableColumn col = table.getColumnModel().getColumn(i);
			col.setMinWidth(134);
			col.setHeaderValue(dayName[i]);
		}
		
		scrollPane = new JScrollPane(table);
	
		resize();
		
		add(scrollPane);
	}
	
	private void resize(){
		scrollPane.setBounds(0, 0, 1000, 650);
	}

	@Override
	public void receiveMessage(ComMessage m){
		if(m.getType().equals(MessageType.RECEIVE_APPOINTMENTS) || m.getType().equals(MessageType.RECEIVE_MEETINGS)){
			table.repaint();
		}
	}
	
}
