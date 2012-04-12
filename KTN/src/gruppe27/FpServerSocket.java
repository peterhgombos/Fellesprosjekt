package gruppe27;


import gruppe27.test.Server;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Timer;

import no.ntnu.fp.net.cl.ClException;
import no.ntnu.fp.net.cl.ClSocket;
import no.ntnu.fp.net.cl.KtnDatagram;
import no.ntnu.fp.net.cl.KtnDatagram.Flag;
import no.ntnu.fp.net.co.SendTimer;

public class FpServerSocket {

	private static final int FIRSTPORT = 6789;
	private int nextport = FIRSTPORT;
	private static Map<Integer, Boolean> usedPorts = Collections.synchronizedMap(new HashMap<Integer, Boolean>());

	private ClSocket a2Socket; 

	private LinkedList<FpSocket> listeners;
	
	private int myPort;
	private String myAddress;
	
	private int remotePort;
	private String remoteAddress;
	
	private int getNextPort(){
		usedPorts.put(nextport, true);
		return nextport++;
	}

	public FpServerSocket(int port) {
		this.myPort = port;
		this.myAddress = Util.getIPv4Address();
		this.a2Socket = new ClSocket();
		listeners = new LinkedList<FpSocket>();
	}

	public void close(){
		for(FpSocket fps: listeners){
			try{
				fps.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		listeners.clear();
		usedPorts.clear();
	}

	private KtnDatagram nextPacket() throws IOException{
		KtnDatagram packet = a2Socket.receive(myPort);
		for(FpSocket l: listeners){
			l.receivePacket(packet);
		}
		
		Server.c.writeline("flag: " + packet.getFlag() + " data: " + packet.getPayload());
		
		return packet;
	}
	
	public FpSocket accept() throws IOException, SocketTimeoutException, ClException {
		KtnDatagram receivedPacket = nextPacket();
		while(receivedPacket.getFlag() != Flag.SYN){
			receivedPacket = nextPacket();
		}

		remotePort = receivedPacket.getSrc_port();
		remoteAddress = receivedPacket.getSrc_addr();

		KtnDatagram synack = Util.makePacket(Flag.SYN_ACK, remotePort, remoteAddress, myPort, myAddress, "", 0);
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new SendTimer(a2Socket, synack), 0, Util.RETRANSMIT);

		KtnDatagram ack = nextPacket();
		while(ack.getFlag() != Flag.ACK){
			ack = nextPacket();
		}

		timer.cancel();
		FpSocket newSocket = new FpSocket(getNextPort(), true);
		listeners.add(newSocket);
		return newSocket;
	}

}
