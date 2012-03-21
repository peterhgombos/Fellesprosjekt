package run;

import java.io.IOException;

public class RunServerAndClient {
	
	public static void main(String[] args) {
		Thread s = new Thread(new Runnable() {
			
			public void run() {
				server.Server.main(null);
			}
		});
		Thread c = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					client.Client.main(null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		s.start();
		c.start();
	}
}
