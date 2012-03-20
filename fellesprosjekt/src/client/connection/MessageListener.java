package client.connection;

import dataobjects.ComMessage;

public interface MessageListener {
	
	public void receiveMessage(ComMessage m);

}
