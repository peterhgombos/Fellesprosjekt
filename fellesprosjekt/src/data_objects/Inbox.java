package data_objects;

import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;


public class Inbox implements ListModel{
	
	private ArrayList<Message> messages;
	private LinkedList<ListDataListener> listeners;
	private ArrayList<Message> displayedMessages;
	
	
	public Inbox (){
		messages = new ArrayList<Message>();
		listeners = new LinkedList<ListDataListener>();
		displayedMessages = new ArrayList<Message>();
	}
	
	
	public void addMessage(Message message){
		messages.add(message);
	}
	
	public void removeMessage(Message message){
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