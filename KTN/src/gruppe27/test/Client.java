package gruppe27.test;

import gruppe27.FpSocket;

import java.io.IOException;


public class Client {
	
	public static Console c = new Console("Client");
	
	public static void main(String[] args) throws InterruptedException{
		new Client();
	}
	
	public Client() throws InterruptedException{
		
		FpSocket socket = new FpSocket(32759);
		
		try{
			c.writeline("Connecting...");
			socket.connect("localhost", 44065);
			c.writeline("Connected");
		}catch(IOException e){
			e.printStackTrace();
		}
		
		Thread.sleep(100);
		
		try{
			c.writeline("Sending message...");
			socket.send("dette er en melding");
			c.writeline("Receiving message...");
			c.writeline(socket.receive());
			c.writeline("Sending message...");
			socket.send("enda en melding");
			c.writeline("DONE");
		}catch(IOException e){
			c.writeline("ERROR:" + e.getMessage());
		}
	}
}
