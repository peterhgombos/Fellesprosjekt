package server;

import server.constants.ServerConstants;
import utilities.Console;

public class Main {
	
	public Main() {
		ServerConstants.console = new Console();
		
		MessageReceiver server = new MessageReceiver();

		ConnectionReceiver connectionReceiver = new ConnectionReceiver(server);
		connectionReceiver.start();
		
	}
	
	public static void main(String[] args) {
		new Main();
	}

}
