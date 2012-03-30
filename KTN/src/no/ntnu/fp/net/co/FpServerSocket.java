package no.ntnu.fp.net.co;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Timer;

import test.K;
import test.Server;

import no.ntnu.fp.net.cl.ClException;
import no.ntnu.fp.net.cl.ClSocket;
import no.ntnu.fp.net.cl.KtnDatagram;
import no.ntnu.fp.net.cl.KtnDatagram.Flag;

public class FpServerSocket extends AbstractConnection{

	private static final int FIRSTPORT = 67891;
	private int nextport = FIRSTPORT;
	private static Map<Integer, Boolean> usedPorts = Collections.synchronizedMap(new HashMap<Integer, Boolean>());

	private ClSocket a2Socket; 

	private LinkedList<FpSocket> listeners;

	private int getNextPort(){
		usedPorts.put(nextport, true);
		return nextport++;
	}

	public FpServerSocket(int port) {
		this.state = State.CLOSED;
		this.myPort = port;
		this.myAddress = getIPv4Address();
		this.a2Socket = new ClSocket();
		listeners = new LinkedList<FpSocket>();
	}

	public void close(){
		for(FpSocket fps: listeners){
			fps.close();
		}
		this.state = State.CLOSED;
		listeners.clear();
		usedPorts.clear();
	}

	@Override
	public String receive() throws IOException{
		throw  new IOException("Server can't receive stuff!");
	}

	@Override
	public void send(String msg) throws ConnectException, IOException{
		throw  new IOException("Server can't send stuff!");
	}

	@Override
	protected boolean isValid(KtnDatagram packet){
		return false;
	}

	@Override
	public Connection accept() throws IOException, SocketTimeoutException, ClException {
		this.state = State.LISTEN;

		KtnDatagram receivedPacket = a2Socket.receive(myPort);
		while(receivedPacket.getFlag() != Flag.SYN){
			broadCast(receivedPacket);
			Server.c.writeline(""+receivedPacket.getFlag());
			receivedPacket = a2Socket.receive(myPort);
		}
		broadCast(receivedPacket);
		Server.c.writeline(""+receivedPacket.getFlag());

		remotePort = receivedPacket.getSrc_port();
		remoteAddress = receivedPacket.getSrc_addr();

		KtnDatagram synack = K.makePacket(Flag.SYN_ACK, remotePort, remoteAddress, myPort, myAddress, "", 0);
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new SendTimer(a2Socket, synack), 0, RETRANSMIT);

		KtnDatagram ack = a2Socket.receive(myPort);
		while(ack.getFlag() != Flag.ACK){
			broadCast(ack);
			Server.c.writeline(""+ack.getFlag());
			ack = receiveAck();
		}
		broadCast(ack);
		Server.c.writeline(""+ack.getFlag());

		timer.cancel();
		this.state = State.ESTABLISHED;
		FpSocket newSocket = new FpSocket(getNextPort());
		listeners.add(newSocket);
		return newSocket;
	}

	private void broadCast(KtnDatagram packet) throws IOException, ClException{
		for(FpPacketReceiver pr: listeners){
			pr.getA2Socket().send(packet);
		}
	}

	public void connect(String remoteAddress, int remotePort)throws IOException, SocketTimeoutException{
		throw new IOException("ServerSockets can't connect!");
	}

}
