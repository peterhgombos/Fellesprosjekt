package test;

import gruppe27.FpSocket;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;


public class Client {
	
	public static Console c = new Console("Client");
	
	public static void main(String[] args) throws InterruptedException{
		Client client = new Client();
		client.run();
	}
		
	public void run() throws InterruptedException{
		
		FpSocket socket = new FpSocket(32759);
		
		try{
			socket.connect("localhost", 44065);
			c.writeline("Connected");
		}catch(SocketTimeoutException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		Thread.sleep(100);
		
		try{
			c.writeline("sending message");
			socket.send("dette er en melding fra klient til server");
			c.writeline("received ACK");
			c.writeline("receiving message");
			c.writeline(socket.receive());
			c.writeline("YAY");
		}catch(ConnectException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
}
