package gruppe27;


import gruppe27.test.Client;

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
		CLOSED, LISTEN, SYN_SENT, SYN_RCVD, ESTABLISHED, FIN_WAIT_1, FIN_WAIT_2, TIME_WAIT, CLOSE_WAIT, LAST_ACK
	}

	//<HAXORZ>
	private Object closeWaiter = new Object();
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
		this.state = State.CLOSED;
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
		this.myAddress = "localhost";
		this.a2Socket = new ClSocket();
	}

	public void close() throws IOException{
		//TODO
	}

	private void remoteClose(KtnDatagram initialFin) throws IOException{
		state = State.FIN_WAIT_1;

		try{
			sendACK(initialFin.getSeq_nr());
		}catch(ClException e){
			throw new IOException();
		}

		KtnDatagram fin = Util.makePacket(Flag.FIN, remotePort, remoteAddress, myPort, myAddress, "", seqCounter);

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new sendWithCounter(fin, 5), 0, Util.RETRANSMIT);
		
		Thread t = new readAckInThread();
		t.start();
		
		synchronized(closeWaiter){
			try{
				closeWaiter.wait();
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		
		timer.cancel();
		state = State.CLOSED;
	}

	private KtnDatagram getNextValidPackage() throws IOException{
		KtnDatagram packet = nextPacket();
		while(!(Util.isValid(packet) && packet.getSrc_addr().equals(remoteAddress) && packet.getSrc_port() == remotePort)){
			packet = nextPacket();
		}
		return packet;
	}

	public String receive() throws IOException {

		isReceiving = true;

		KtnDatagram packet = getNextValidPackage();

		isReceiving = false;

		if (packet.getFlag() == Flag.NONE) {
			try{
				sendACK(packet.getSeq_nr());
				return ""+packet.getPayload();
			}catch(ClException e){
				throw new IOException();
			}
		}else if(packet.getFlag() == Flag.FIN){
			remoteClose(packet);
			return null;
		}else {
			throw new IOException();
		}
	}

	public void connect(String remoteAddress, int remotePort) throws IOException {
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
			throw new IOException();
		}
		this.state = State.ESTABLISHED;
	}

	private void sendACK(int seqToAck) throws IOException, ClException{
		KtnDatagram ack = Util.makeAckPack(remotePort, remoteAddress, myPort, myAddress, seqToAck);
		a2Socket.send(ack);
	}

	public void send(String msg) throws IOException{
		if(this.state != State.ESTABLISHED){
			throw new IOException();
		}
		KtnDatagram packet = Util.makePacket(Flag.NONE, remotePort, remoteAddress, myPort, myAddress, msg, seqCounter);

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new SendTimer(a2Socket, packet), 0, Util.RETRANSMIT);

		isReceiving = true;
		KtnDatagram ackpack = getNextValidPackage();
		while(ackpack.getFlag() != Flag.ACK || ackpack.getAck() != seqCounter){
			ackpack = getNextValidPackage();
		}
		timer.cancel();
		isReceiving = false;
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
				while(finack.getFlag() != Flag.ACK || finack.getSeq_nr() == seqCounter){
					finack = getNextValidPackage();
					closeWaiter.notifyAll();
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
				closeWaiter.notifyAll();
			}
		}
	}
}
