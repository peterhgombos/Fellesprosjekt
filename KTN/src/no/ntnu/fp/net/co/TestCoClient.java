package no.ntnu.fp.net.co;

import gruppe27.test.Console;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;

public class TestCoClient {

	public static void main (String args[]){
		
		Console c = new Console("Klient");
		
		try {
			for(int j = 0; j < 10; j++){
				Connection conn = new ConnectionImpl(4001);
				conn.connect(InetAddress.getLocalHost(), 5555);

				c.writeline("Connected");

				for(int i = 0; i < 1; i++){
					String s = "Hi" + i + "!";
					conn.send(s);
					c.writeline("Sendt:" + s);
				}
				
				for(int i = 0; i < 1; i++){
					String s = conn.receive();
					c.writeline("Received:" + s);
				}

				try{Thread.sleep(10);}catch(InterruptedException e){}
				
				// write a message in the log and close the connection
				conn.close();
				c.writeline("Closed the connection");
			}
		}
		catch (ConnectException e){
			c.writeline("ERROR " + e.getMessage());
			e.printStackTrace();
		}
		catch (IOException e){
			c.writeline("ERROR " + e.getMessage());
			e.printStackTrace();
		}
	}

}
