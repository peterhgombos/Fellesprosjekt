package client.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import client.Client;
import client.connection.ServerData;

@SuppressWarnings("serial")
public class CalendarPanel extends JPanel {

	private Client client;
	
	private SidePanel sidePanel;
	private Calendar calendarPanel;
	
	private JFrame frame;
	
	public CalendarPanel(Client client){
		this.client = client;
		this.frame = client.getFrame();
		
		setLayout(null);
		
		sidePanel = new SidePanel();
		sidePanel.setBounds(0, 0, 180, 700);
		
		calendarPanel = new Calendar();
		calendarPanel.setBounds(sidePanel.getX() + sidePanel.getWidth(), 0, 900, 700);
		
		ServerData.addMessageListener(calendarPanel);
		
		add(sidePanel);
		add(calendarPanel);
	}
	
}
