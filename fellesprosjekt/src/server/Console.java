package server;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Console {
	
	private static JFrame frame;
	private static JTextArea textarea;
	private static JScrollPane scrollpane;
	
	public static void open(){
		frame = new JFrame();
		frame.setTitle("ArenaServer");
		frame.setMinimumSize(new Dimension(500, 600));
		
		frame.setLayout(new GridLayout(1,1));
		
		textarea = new JTextArea();
		textarea.setEditable(false);
		
		scrollpane = new JScrollPane(textarea);
		frame.add(scrollpane);
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.addWindowListener(new WindowListener() {
			public void windowClosing(WindowEvent arg0){
				System.exit(0);
			}
			public void windowOpened(WindowEvent arg0){}
			public void windowIconified(WindowEvent arg0){}
			public void windowDeiconified(WindowEvent arg0){}
			public void windowDeactivated(WindowEvent arg0){}
			public void windowClosed(WindowEvent arg0){}
			public void windowActivated(WindowEvent arg0){}
		});
	}
	public static synchronized void writeline(String s){
		if(textarea != null){
			textarea.append(s+"\n");
		}
	}
	public static synchronized void writechar(char c){
		if(textarea != null){
			textarea.append(""+c);
		}
	}
}//END
