package test;

import java.io.IOException;
import java.net.SocketTimeoutException;

import no.ntnu.fp.net.co.Connection;
import no.ntnu.fp.net.co.FpServerSocket;
import no.ntnu.fp.net.co.FpSocket;
import no.ntnu.fp.net.co.ReceiveMessageWorker.MessageListener;

public class Server implements MessageListener{
	
	public static Console c = new Console("Server");
	
	public static void main(String[] args){
		
		Server server = new Server();
		server.run();
		
	}
	
	public void run() {
		FpServerSocket sSocket = new FpServerSocket(44065);
		
		try{
			while(true){
				FpSocket s = (FpSocket) sSocket.accept();
				c.writeline("Connected");
				s.addListener(this);
			}
			
		}catch(SocketTimeoutException e){
			c.writeline(e.getMessage());
			return;
		}catch(IOException e){
			c.writeline(e.getMessage());
			return;
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
