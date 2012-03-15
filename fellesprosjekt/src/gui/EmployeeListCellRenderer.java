package gui;

import java.awt.*;
import javax.swing.*;

public class EmployeeListCellRenderer extends JLabel implements ListCellRenderer{

	DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
	
	private JButton x;
	
	public EmployeeListCellRenderer() {
		x = new JButton();
	}
	
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		
		x.setIcon(new ImageIcon("Bilder/female.png"));
		
		return this;
	}
}
