package server;


public class ServerCLI {
	
	public static final int PORT = 4536;
	
	public ServerCLI() {
		MessageReceiver server = new MessageReceiver();

		ConnectionReceiver connectionReceiver = new ConnectionReceiver(server);
		connectionReceiver.start();
	}
	
	
	public static void main(String[] args) {
		new ServerCLI();
	}

}
