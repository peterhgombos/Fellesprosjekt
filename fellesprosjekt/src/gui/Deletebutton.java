package gui;

import javax.swing.ImageIcon;
import javax.swing.JButton;


public class Deletebutton extends JButton{
	final static ImageIcon icon = new ImageIcon("res/X-symbol.png");
	
	public Deletebutton() {
		this.setIcon(icon);
	}

}