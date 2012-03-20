package server;

import utilities.Console;

public class Main {
	
	public Main() {
		ServerConstants.console = new Console();
		ServerConstants.console.setTitle("Server");
		MessageReceiver server = new MessageReceiver();

		ConnectionReceiver connectionReceiver = new ConnectionReceiver(server);
		connectionReceiver.start();
	}
	
	
	public static void main(String[] args) {
		new Main();
	}

}
