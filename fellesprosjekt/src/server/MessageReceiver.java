package server;


public class MessageReceiver {
	
	public MessageReceiver() {
		
	}
	
	public synchronized void receiveMessage(String message){
		Console.writeline(message);
	}
}
