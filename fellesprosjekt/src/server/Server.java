package server;

import common.utilities.Console;

public class Server {
	
	public static final int PORT = 4536;
	public static Console console = new Console("Server");
	
	public Server() {
		MessageReceiver server = new MessageReceiver();

		ConnectionReceiver connectionReceiver = new ConnectionReceiver(server);
		connectionReceiver.start();
	}
	
	
	public static void main(String[] args) {
		new Server();
	}

}
