package client.gui.calendar;

import java.awt.Color;

public class ColorPicker {

	private static final Color[] farger = {new Color(0x66CC66), new Color(0x6666FF), new Color(0xFF6666), new Color(0xFFFCC33), new Color(0xCCCC66), new Color(0xFF9966), new Color(0x66CCFF) ,new Color(0xCCCC99), new Color(0xFFCCFF), new Color(0xCCCCFF)};
	private static int i = 0;
	
	public static Color nextColor(){
		if(i >= farger.length){
			i = 0;
		}
		return farger[i++];
	}
	public static void reset(){
		i = 0;
	}
	public static Color otherColor(){
		return new Color(0xFF0000);
	}

}
