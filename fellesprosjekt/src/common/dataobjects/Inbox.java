package common.dataobjects;

import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;


public class Inbox implements ListModel{
	
	private ArrayList<Note> messages;
	private LinkedList<ListDataListener> listeners;
	private ArrayList<Note> displayedMessages;
	
	
	public Inbox (){
		messages = new ArrayList<Note>();
		listeners = new LinkedList<ListDataListener>();
		displayedMessages = new ArrayList<Note>();
	}
	
	
	public void addMessage(Note message){
		messages.add(message);
	}
	
	public void removeMessage(Note message){
		messages.remove(message);
	}
		
	@Override
	public void addListDataListener(ListDataListener l) {		
		listeners.add(l);
	}

	@Override
	public Object getElementAt(int index) {
		return displayedMessages.get(index);
	}

	@Override
	public int getSize() {
		return displayedMessages.size();
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}
}