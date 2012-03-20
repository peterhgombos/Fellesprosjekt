package utilities;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Console {
	
	private JTextArea textarea;
	private JFrame frame;
	
	public Console(){
		frame = new JFrame();
		frame.setMinimumSize(new Dimension(500, 600));
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
	
	public void setTitle(String s) {
		frame.setTitle(s);
	}
}//END
