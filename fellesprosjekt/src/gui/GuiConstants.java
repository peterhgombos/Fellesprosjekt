package gui;

import java.awt.Font;

import javax.swing.JButton;

public class GuiConstants {

	private Font JButtonFont;
	private int JButtonDistance;
	private int JButtonGroupDistance;
	
	
	public GuiConstants() {
		JButton b = new JButton();
		JButtonFont = new Font(b.getFont().getName(), 
				b.getFont().getStyle(), 14 );
		
		JButtonDistance = 10;
		JButtonGroupDistance = 40;
	}

	

	public int getJButtonDistance() {
		return JButtonDistance;
	}



	public int getJButtonGroupDistance() {
		return JButtonGroupDistance;
	}



	public Font getJButtonFont() {
		return JButtonFont;
	}
	
}
