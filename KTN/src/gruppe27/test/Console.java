package gruppe27.test;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class Console {
	
	public static final int WHITEBLACK = 0;
	public static final int BLACKWHITE = 1;
	public static final int BLACKGREEN = 2;
	
	private JTextArea textarea;
	private JFrame frame;
	
	public Console(){
		init();
	}
	
	public Console(String title){
		init();
		frame.setTitle(title);
	}
	
	public Console(String title, int style){
		init();
		frame.setTitle(title);
		setStyle(style);
	}
	
	private void setStyle(int style){
		if(style == WHITEBLACK){
			textarea.setBackground(new Color(0xFFFFFF));
			textarea.setForeground(new Color(0x000000));
		}else if(style == BLACKWHITE){
			textarea.setBackground(new Color(0x000000));
			textarea.setForeground(new Color(0xFFFFFF));
		}else if(style == BLACKGREEN){
			textarea.setBackground(new Color(0x000000));
			textarea.setForeground(new Color(0x00FF00));
		}
	}
	
	private void init(){
		try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch(Exception e){}
		frame = new JFrame();
		frame.setAlwaysOnTop(true);
		frame.setMinimumSize(new Dimension(300, 400));
		frame.setLayout(new GridLayout(1,1));
		
		textarea = new JTextArea();
		textarea.setEditable(false);
		
		JScrollPane scrollpane = new JScrollPane(textarea);
		frame.add(scrollpane);	
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public synchronized void writeline(String s){
		if(textarea != null){
			textarea.append(s + "\n");
			textarea.setCaretPosition(textarea.getText().length()-1);
		}
	}
	
}//END
