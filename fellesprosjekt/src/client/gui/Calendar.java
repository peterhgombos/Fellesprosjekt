package client.gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import common.dataobjects.ComMessage;

import client.connection.MessageListener;
import client.connection.MessageType;

@SuppressWarnings("serial")
public class Calendar extends JPanel implements MessageListener {

	private JTable header;
	private JTable table;
	private JScrollPane scrollPane;
	
	public Calendar(){
		setLayout(null);
		
		//header = new JTable();
		table = new JTable();
		
		CalRenderer renderer = new CalRenderer();
		table.setModel(new CalModel(2012, 11));
		
		table.setRowHeight(40);
		table.setDefaultRenderer(Object.class, renderer);
		for(int i = 0; i < table.getModel().getColumnCount(); i++){
			TableColumn col = table.getColumnModel().getColumn(i);
			col.setWidth(80);
		}
		scrollPane = new JScrollPane(table);
		
		
		resize();
		
		add(scrollPane);
	}
	
	private void resize(){
		scrollPane.setBounds(0, 0, 900, 650);
	}

	@Override
	public void receiveMessage(ComMessage m){
		if(m.getType().equals(MessageType.RECEIVE_APPOINTMENTS) || m.getType().equals(MessageType.RECEIVE_MEETINGS)){
			table.repaint();
		}
	}
	
}
