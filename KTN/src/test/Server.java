package test;

import java.io.IOException;
import java.net.SocketTimeoutException;

import no.ntnu.fp.net.co.FpServerSocket;
import no.ntnu.fp.net.co.FpSocket;

public class Server {
	
	public static Console c = new Console("Server");
	
	public static void main(String[] args){
		
		FpServerSocket sSocket = new FpServerSocket(44065);
		
		try{
			FpSocket s = (FpSocket)sSocket.accept();
		}catch(SocketTimeoutException e){
			c.writeline(e.getMessage());
			return;
		}catch(IOException e){
			c.writeline(e.getMessage());
			return;
		}
		c.writeline("Connected");
	}
	
}
