package no.ntnu.fp.net.co;

import gruppe27.test.Console;

import java.io.IOException;

public class TestCoServer {

	public static void main (String args[]){
		Console c = new Console("Server");
		Connection server = new ConnectionImpl(5555);
		
		try {
			for(int j = 0; j < 10; j++){
				
				Connection conn = server.accept();
				c.writeline("Connected");
				
				for(int i = 0; i < 1; i++){
					String s = conn.receive();
					c.writeline("Received:" + s);
				}
				
				for(int i = 0; i < 1; i++){
					String s = "Hi" + i + "!";
					conn.send(s);
					c.writeline("Sendt:" + s);
				}

				//wait for close
				conn.receive();
				c.writeline("Connection closed by remote client");
			}
		}
		catch (IOException e){
			c.writeline("ERROR " + e.getMessage());
			e.printStackTrace();
		}
	}
}
