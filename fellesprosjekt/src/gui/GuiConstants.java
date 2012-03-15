package gui;

import java.awt.Font;

import javax.swing.JButton;

public class GuiConstants {

	private Font jButtonFont;
	private int distance;
	private int groupDistance;
	
	
	public GuiConstants() {
		JButton b = new JButton();
		jButtonFont = new Font(b.getFont().getName(), 
				b.getFont().getStyle(), 14 );
		
		distance = 10;
		groupDistance = 40;
	}

	

	public int getDistance() {
		return distance;
	}



	public int getGroupDistance() {
		return groupDistance;
	}



	public Font getJButtonFont() {
		return jButtonFont;
	}
	
}
