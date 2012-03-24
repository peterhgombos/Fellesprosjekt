package client.gui;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import common.dataobjects.Note;

public class MessageRendrer implements ListCellRenderer{

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		
		JLabel label = new JLabel();
		Note note = (Note) value;
		label.setFont(new Font("Arial", 0, 12));
		if (!note.isRead()) {
			label.setFont(new Font("Arial",Font.BOLD, 12));
		}
		
		label.setText(note.getTitle());
		
			if (isSelected) {
				label.setBackground(list.getSelectionBackground());
				label.setForeground(list.getSelectionForeground());
			}
			else {
				label.setBackground(list.getBackground());
				label.setForeground(list.getForeground());
			}
			label.setOpaque(true);
		
		
		return label;
	}
	
	
}
