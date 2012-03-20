package client.connection;

import common.dataobjects.ComMessage;

public interface MessageListener {
	
	public void receiveMessage(ComMessage m);

}
