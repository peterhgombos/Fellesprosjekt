package gruppe27;

import java.io.IOException;

import no.ntnu.fp.net.cl.ClException;
import no.ntnu.fp.net.cl.ClSocket;
import no.ntnu.fp.net.cl.KtnDatagram;
import no.ntnu.fp.net.cl.KtnDatagram.Flag;

public class Exper {
	public static void main(String[] args) throws IOException, ClException, InterruptedException{
		
		Thread t = new Thread(){
			public void run() {
				ClSocket s2 = new ClSocket();
				try{
					int i = 0;
					while(true){
						KtnDatagram d = s2.receive(6666);
						System.out.println(d.getPayload().toString());
						i++;
						if(i == 3){
							return;
						}
					}
				}catch(IOException e){
					e.printStackTrace();
				}
			};
		};
		t.start();
		
		ClSocket s1 = new ClSocket();
		s1.send(Util.makePacket(Flag.NONE, 6666, "localhost", 6665, "localhost", "hei", 0));
		Thread.sleep(2000);
		
		ClSocket s2 = new ClSocket();
		s2.send(Util.makePacket(Flag.NONE, 6666, "localhost", 6665, "localhost", "hei", 0));
		Thread.sleep(2000);
		
		ClSocket s3 = new ClSocket();
		s3.send(Util.makePacket(Flag.NONE, 6666, "localhost", 6665, "localhost", "hei", 0));
		Thread.sleep(2000);
	}
}
