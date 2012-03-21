package client.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

public class UserInformationMessages {
	
	private JButton okButton;
	private JLabel messageLabel;
	
	public UserInformationMessages(String message) {
		
		messageLabel = new JLabel();
		messageLabel.setText(message);
		
		okButton = new JButton();
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//tilbake til kalender, eller annet sted avhenger av tye melding
				//feil, og varsler
			}
		});
		
		resize();
	}
	
	public void resize(){
		messageLabel.setBounds(GuiConstants.DISTANCE*5, GuiConstants.GROUP_DISTANCE*5, 200, GuiConstants.LABEL_HEIGTH);
		messageLabel.setFont(new Font(messageLabel.getFont().getName(), 0, 16));
		
		okButton.setBounds(messageLabel.getX(), messageLabel.getY() + messageLabel.getHeight() + GuiConstants.DISTANCE, 140, GuiConstants.BUTTON_HEIGTH);
		
	}
}
