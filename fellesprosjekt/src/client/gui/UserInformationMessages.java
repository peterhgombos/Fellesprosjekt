package client.gui;

import javax.swing.JOptionPane;

public class UserInformationMessages  {
	
	//av typen; melding sendt
	public void showMessage(String message){
		JOptionPane.showMessageDialog(null, message);
	}
	
	//av typen; du har ikke skrevet inn passord
	public void showErrormessage(String message){
		JOptionPane.showMessageDialog(null, message);
	}
}
