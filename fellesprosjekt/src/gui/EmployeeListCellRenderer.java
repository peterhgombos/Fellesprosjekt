package gui;

import java.awt.*;
import javax.swing.*;

public class EmployeeListCellRenderer extends JLabel implements ListCellRenderer{

	DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
	
	private JButton x;
	

	
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		ImageIcon i = new ImageIcon("res/kryss.gif");
		setIcon(i);
		setText("hei");
		
		
		if (isSelected) {
			setText("");
			setIcon(null);
		}
		
		return this;
	}
}
