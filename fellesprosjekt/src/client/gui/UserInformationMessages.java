package client.gui;

import javax.swing.JOptionPane;

public class UserInformationMessages  {
	
	//av typen; melding sendt
	public static void showMessage(String message){
		JOptionPane.showMessageDialog(null, message);
	}
	
	public static void showWarningMessage(String message){
		JOptionPane.showMessageDialog(null, message,"OBS!",JOptionPane.WARNING_MESSAGE);
	}
	
	//av typen; du har ikke skrevet inn passord
	public static void showErrormessage(String message){
		JOptionPane.showMessageDialog(null, message,"FEIL!",JOptionPane.ERROR_MESSAGE);
	}
}
