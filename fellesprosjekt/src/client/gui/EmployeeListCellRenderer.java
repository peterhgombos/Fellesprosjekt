package client.gui;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import common.dataobjects.Person;

public class EmployeeListCellRenderer implements ListCellRenderer{

	DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
	
	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		
		ImageIcon i = new ImageIcon("res/kryss.gif");
		JLabel label = new JLabel();
		label.setIcon(i);
		Person person = (Person)value;
		label.setText(person.getFirstname() + " " + person.getSurname());
		
		
		return label;
	}
}
