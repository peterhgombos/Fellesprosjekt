package gruppe27;


import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Timer;

import no.ntnu.fp.net.cl.ClException;
import no.ntnu.fp.net.cl.ClSocket;
import no.ntnu.fp.net.cl.KtnDatagram;
import no.ntnu.fp.net.cl.KtnDatagram.Flag;
import no.ntnu.fp.net.co.SendTimer;
import test.Client;

public class FpSocket {

	protected enum State {
		CLOSED, LISTEN, SYN_SENT, SYN_RCVD, ESTABLISHED, FIN_WAIT_1, FIN_WAIT_2, TIME_WAIT, CLOSE_WAIT, LAST_ACK
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

	private int seqCounter = 0;

	private boolean isReceiving = false;

	public FpSocket(int port){
		init(port);
	}

	public FpSocket(int port, boolean serverSide){
		this.serverSide = serverSide;
		this.state = State.ESTABLISHED;
		init(port);
	}

	private void init(int port){
		this.myPort = port;
		this.myAddress = "localhost";
		this.a2Socket = new ClSocket();
	}

	public void close() throws IOException{
		//TODO
	}

	public String receive() throws IOException {

		isReceiving = true;

		KtnDatagram packet = null;
		packet = nextPacket();

		try{
			sendACK(packet);
		}catch(ClException e){
			e.printStackTrace();
		}
		
		System.out.println("sendt ack");

		isReceiving = false;
		return ""+packet.getPayload();

		//System.out.println(packet.getPayload().toString());

		//		if (isValid(packet) && packet.getSrc_addr() == remoteAddress) {
		//			if (packet.getFlag() == Flag.NONE) {
		//				try{
		//					sendACK(packet.getSeq_nr());
		//					System.out.println("sendt ack");
		//				}catch(IOException e){
		//					e.printStackTrace();
		//				}catch(ClException e){
		//					e.printStackTrace();
		//				}
		//				return ""+packet.getPayload();
		//			}
		//		}

		//		if (isValid(packet) && packet.getDest_addr() == myAddress && packet.getDest_port() == myPort) {
		//			
		//			if (packet.getFlag() == Flag.NONE) {
		//
		//				return packet.getPayload().toString();
		//
		//			} else if (packet.getFlag() == Flag.ACK) {
		//				
		//				if (state == State.ESTABLISHED) {
		//					//A previously sent packet has obviously been ack'd. Deal with it appropriately
		//				} else if (state == State.FIN_WAIT_1) {		//FIN_WAIT_1 should be waiting for a FIN or FINACK
		//					//Goto FIN_WAIT_2 (Waiting for a FIN)
		//				} else {
		//					//Something obviously went wrong. Do something about it
		//				}
		//				
		//			} else if (packet.getFlag() == Flag.FIN) {
		//				
		//				if (state == State.ESTABLISHED) {
		//					//Send FINACK - Go to LAST_ACK
		//				}else if (state == State.FIN_WAIT_1) {
		//					//Send FINACK
		//					//Goto CLOSE_WAIT
		//				}else if (state == State.FIN_WAIT_2) {
		//					//Send FINACK
		//					//Goto CLOSE_WAIT
		//				}else {
		//					//Something obviously went wrong. Do something about it
		//				}
		//				
		//			}
		//		}
		//NOE VELDIG GALT
		//		return null;
	}

	public void connect(String remoteAddress, int remotePort) throws IOException, SocketTimeoutException {
		//If state is not closed, no connect
		if(this.state != State.CLOSED){
			throw new IOException();
		}

		this.remotePort = remotePort;
		this.remoteAddress = remoteAddress;

		//Send SYN with timer until SYNACK is received.
		KtnDatagram syn = Util.makePacket(Flag.SYN, remotePort, remoteAddress, myPort, myAddress, "", 0);

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new SendTimer(a2Socket, syn), 0, Util.RETRANSMIT);
		//TODO Make connection timeout/stop after a given number of attempts
		this.state = State.SYN_SENT;
		KtnDatagram synack = nextPacket();
		while(synack.getFlag() != Flag.SYN_ACK){
			Client.c.writeline(""+synack.getFlag());
			synack = nextPacket();
		}
		timer.cancel();

		//Send ACK after receiving SYNACK
		KtnDatagram ack = Util.makeAckPack(remotePort, remoteAddress, myPort, myAddress, 0);
		try {
			a2Socket.send(ack);
		} catch (ClException e) {
			e.printStackTrace();
		}
		this.state = State.ESTABLISHED;
	}

	private void sendACK(KtnDatagram packetToAck) throws IOException, ClException{
		remoteAddress = packetToAck.getSrc_addr();
		remotePort = packetToAck.getSrc_port();
		KtnDatagram ack = Util.makeAckPack(remotePort, remoteAddress, myPort, myAddress, packetToAck.getSeq_nr());
		a2Socket.send(ack);
	}

	public void send(String msg) throws IOException{
		if(this.state != State.ESTABLISHED){
			throw new IOException();
		}
		KtnDatagram packet = Util.makePacket(Flag.NONE, remotePort, remoteAddress, myPort, myAddress, msg, seqCounter);

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new SendTimer(a2Socket, packet), 0, Util.RETRANSMIT);
		KtnDatagram ackpack = nextPacket();
		while(ackpack.getFlag() != Flag.ACK || ackpack.getSeq_nr() != seqCounter){
			ackpack = nextPacket();
			Client.c.writeline("ACK");
		}
		timer.cancel();
		seqCounter++;
	}

	public void receivePacket(KtnDatagram packet){
		if(isReceiving){
			serverSideNextDatagram = packet;
			synchronized(waitObject){
				waitObject.notifyAll();
			}
		}
	}
	private KtnDatagram nextPacket() throws IOException{
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
}
