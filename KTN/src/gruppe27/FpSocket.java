package gruppe27;


import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import no.ntnu.fp.net.cl.ClException;
import no.ntnu.fp.net.cl.ClSocket;
import no.ntnu.fp.net.cl.KtnDatagram;
import no.ntnu.fp.net.cl.KtnDatagram.Flag;
import no.ntnu.fp.net.co.SendTimer;

public class FpSocket {

	protected enum State {
		CLOSED, SYN_SENT, SYN_RCVD, ESTABLISHED, FIN_WAIT_1, FIN_WAIT_2, TIME_WAIT, CLOSE_WAIT, LAST_ACK, FIN_RCVD, FIN_SENT, FINACK_SENT
	}

	//<HAXORZ>
	private Object waitObject = new Object();
	private KtnDatagram serverSideNextDatagram = null;
	//</HAXORZ>

	private boolean serverSide = false;
	private ClSocket a2Socket;

	private int myPort;
	private String myAddress;

	private int remotePort;
	private String remoteAddress;

	private State state;

	private int sendSeq = 0;
	private int receiveSeq = -1;

	private boolean isReceiving = false;

	public FpSocket(int port){
		state = State.CLOSED;
		init(port);
	}

	public FpSocket(int port, String remoteAddr, int remotePort, boolean serverSide){
		this.serverSide = serverSide;
		this.state = State.ESTABLISHED;
		this.remoteAddress = remoteAddr;
		this.remotePort = remotePort;
		init(port);
	}

	private void init(int port){
		this.myPort = port;
		this.myAddress = Util.getIPv4Address();
		this.a2Socket = new ClSocket();
	}

	public void close() throws IOException{
		//TODO
	}
	private void internalClose() {
		state = State.CLOSED;
	}

	private void remoteClose(KtnDatagram initialFin) throws IOException{

		
	}

	public String receive() throws IOException {
		if(state != State.ESTABLISHED){
			throw new IOException();
		}

		while(true){
			isReceiving = true;
			KtnDatagram packet = getNextValidPackage();
			isReceiving = false;

			if(state == State.ESTABLISHED){
				if (packet.getFlag() == Flag.NONE) {
					sendACK(packet.getSeq_nr());
					if(receiveSeq < packet.getSeq_nr()){
						receiveSeq = packet.getSeq_nr();
						return packet.getPayload().toString();
					}
				}
				else if(packet.getFlag() == Flag.FIN){
					state = State.FIN_RCVD;
					sendACK(packet.getSeq_nr());
				}
				else if(packet.getFlag() == Flag.SYN_ACK){
					sendACK(0);
				}
			}
			else if(state == State.FIN_RCVD){
				
				if(packet.getFlag() == Flag.FIN){
					state = State.FIN_RCVD;
					sendACK(packet.getSeq_nr());
				}
				else if(packet.getFlag() == Flag.ACK){
					
//					KtnDatagram fin = Util.makePacket(Flag.FIN, remotePort, remoteAddress, myPort, myAddress, "", sendSeq);
//
//					Timer timer = new Timer();
//					timer.scheduleAtFixedRate(new sendWithCounter(fin, 5), 0, Util.RETRANSMIT);
//
//					Thread t = new readAckInThread();
//					t.start();
//
//					synchronized(waitObject){
//						try{
//							waitObject.wait();
//						}catch(InterruptedException e){
//							e.printStackTrace();
//						}
//					}
//
//					timer.cancel();
//					state = State.CLOSED;
					
					
					
				}
			}
		}
	}

	public void connect(String remoteAddress, int remotePort) throws IOException {
		if(state != State.CLOSED){
			throw new IOException();
		}

		this.remotePort = remotePort;
		this.remoteAddress = remoteAddress.equalsIgnoreCase("localhost") ? Util.getIPv4Address() : remoteAddress;

		KtnDatagram syn = Util.makePacket(Flag.SYN, remotePort, remoteAddress, myPort, myAddress, "", 0);

		Timer syntimer = new Timer();
		syntimer.scheduleAtFixedRate(new SendTimer(a2Socket, syn), 0, Util.RETRANSMIT);
		//TODO ikke prøv evig mange ganger
		
		KtnDatagram synack = nextPacket();
		while(synack.getFlag() != Flag.SYN_ACK || synack.getChecksum() != synack.calculateChecksum()){
			synack = nextPacket();
		}
		syntimer.cancel();

		sendACK(0);
		state = State.ESTABLISHED;
	}

	private void sendACK(int seqToAck) throws IOException{
		KtnDatagram ack = Util.makeAckPack(false, remotePort, remoteAddress, myPort, myAddress, seqToAck);
		try{
			a2Socket.send(ack);
		}catch(ClException e){
			throw new IOException();
		}
	}

	public void send(String msg) throws IOException{
		if(this.state != State.ESTABLISHED){
			throw new IOException();
		}

		KtnDatagram packet = Util.makePacket(Flag.NONE, remotePort, remoteAddress, myPort, myAddress, msg, sendSeq);

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new SendTimer(a2Socket, packet), 0, Util.RETRANSMIT);

		isReceiving = true;
		KtnDatagram ackpack = getNextValidPackage();
		while(ackpack.getFlag() != Flag.ACK || ackpack.getAck() != sendSeq){
			ackpack = getNextValidPackage();
		}
		isReceiving = false;

		timer.cancel();
		sendSeq++;
	}

	private KtnDatagram getNextValidPackage() throws IOException{
		KtnDatagram packet = nextPacket();
		while(!(Util.isValid(packet) && packet.getSrc_addr().equals(remoteAddress) && packet.getSrc_port() == remotePort)){
			packet = nextPacket();
		}
		return packet;
	}

	public void receivePacket(KtnDatagram packet){
		if(isReceiving){
			serverSideNextDatagram = packet;
			synchronized(waitObject){
				waitObject.notifyAll();
			}
		}
	}

	private KtnDatagram nextPacket() throws IOException {
		if(serverSide){
			try{
				synchronized (waitObject) {
					waitObject.wait();
					return serverSideNextDatagram;
				}
			}catch(InterruptedException e){
				e.printStackTrace();
				return null;
			}
		}else{
			return a2Socket.receive(myPort);
		}

	}

	private class readAckInThread extends Thread{
		public void run(){
			try{
				KtnDatagram finack = getNextValidPackage();
				while(finack.getFlag() != Flag.ACK || finack.getSeq_nr() == sendSeq){
					finack = getNextValidPackage();
					waitObject.notifyAll();
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}

	private class sendWithCounter extends TimerTask{
		int attempts = 0;
		int limit;
		KtnDatagram packet;
		public sendWithCounter(KtnDatagram packet, int limit){
			this.packet = packet;
			this.limit = limit;
		}
		public void run(){
			try{
				a2Socket.send(packet);
			}catch(IOException e){
				e.printStackTrace();
			}catch(ClException e){
				e.printStackTrace();
			}
			attempts++;
			if(attempts > limit){
				waitObject.notifyAll();
			}
		}
	}
}
