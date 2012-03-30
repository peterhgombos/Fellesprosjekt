package test;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import no.ntnu.fp.net.co.Connection;
import no.ntnu.fp.net.co.FpSocket;
import no.ntnu.fp.net.co.ReceiveMessageWorker.MessageListener;

public class Client implements MessageListener{
	
	public static Console c = new Console("Client");
	
	public static void main(String[] args){
		Client client = new Client();
		client.run();
	}
		
	public void run(){
		
		FpSocket socket = new FpSocket(32759);
		socket.addListener(this);
		
		try{
			socket.connect("localhost", 44065);
		}catch(SocketTimeoutException e){
			c.writeline(e.getMessage());
			return;
		}catch(IOException e){
			c.writeline(e.getMessage());
			return;
		}
		c.writeline("Connected");
		
		try{
			socket.send("Test");
		}catch(ConnectException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void messageReceived(String message) {		
		c.writeline(message);
	}

	@Override
	public void connectionClosed(Connection conn) {
		c.writeline("Closed");
	}
	
}
