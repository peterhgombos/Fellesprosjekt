package server;

public class Main {
	
	public Main() {
		Console.open();
		
		MessageReceiver server = new MessageReceiver();

		ConnectionReceiver connectionReceiver = new ConnectionReceiver(server);
		connectionReceiver.start();
		
	}
	
	
	
	public static void main(String[] args) {
		new Main();
	}

}
