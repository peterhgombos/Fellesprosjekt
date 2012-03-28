package test;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import no.ntnu.fp.net.co.FpSocket;

public class Client {
	
	public static Console c = new Console("Client");
	
	public static void main(String[] args){
		
		FpSocket socket = new FpSocket(32759);
		
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
	
}
