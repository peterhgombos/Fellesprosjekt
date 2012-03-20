package client.gui;
import App;
import CalModel;

import javax.swing.JPanel;
import javax.swing.JTable;


@SuppressWarnings("serial")
public class Calendar extends JPanel{

	
	public Calendar(){
		
		App[][] hendelser = new App[25][8];
		
		hendelser[6][6] = new App();
		hendelser[1][1] = new App();
		hendelser[2][2] = new App();
		hendelser[3][3] = new App();
		hendelser[4][4] = new App();
		hendelser[5][5] = new App();
		
		this.setModel(new CalModel(hendelser));
		this.setDefaultRenderer(Object.class, new CalRenderer());
		
		for(int i = 0; i < 24; i++){
			this.setRowHeight(i, 40);  
		}
		
		for(int i = 0; i < 7; i++){
			this.getColumnModel().getColumn(i).setWidth(50); 
		}
		 
		
	}

}
