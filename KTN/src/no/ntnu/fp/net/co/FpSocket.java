package no.ntnu.fp.net.co;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.Timer;

import test.Client;
import test.K;

import no.ntnu.fp.net.cl.ClException;
import no.ntnu.fp.net.cl.ClSocket;
import no.ntnu.fp.net.cl.KtnDatagram;
import no.ntnu.fp.net.cl.KtnDatagram.Flag;
import no.ntnu.fp.net.co.ReceiveMessageWorker.MessageListener;

public class FpSocket extends AbstractConnection implements FpPacketReceiver{

	
	ClSocket a2Socket;
	LinkedList<MessageListener> listeners;
	
	public FpSocket(int port){
		this.myPort = port;
		this.myAddress = "localhost";
		this.a2Socket = new ClSocket();
		listeners = new LinkedList<ReceiveMessageWorker.MessageListener>();
	}
	
	public void close(){

	}

	public String receive(){
		return "";
	}

	public void connect(String remoteAddress, int remotePort) throws IOException, SocketTimeoutException {
		//If state is not closed, no connect
		if(this.state != State.CLOSED) return;

		this.remotePort = remotePort;
		this.remoteAddress = remoteAddress;
		
		//Send SYN with timer until SYNACK is received.
		KtnDatagram syn = constructInternalPacket(Flag.SYN);
		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new SendTimer(a2Socket, syn), 0, RETRANSMIT);
		//TODO Make connection timeout/stop after a given number of attempts
		this.state = State.SYN_SENT;
		KtnDatagram synack = a2Socket.receive(myPort);
		while(synack.getFlag() != Flag.SYN_ACK){
			Client.c.writeline(""+synack.getFlag());
			synack = a2Socket.receive(myPort);
		}
		timer.cancel();

		//Send ACK after receiving SYNACK
		KtnDatagram ack = K.makePacket(Flag.ACK, remotePort, remoteAddress, myPort, myAddress, "", 0);
		try {
			simplySendPacket(ack);
		} catch (ClException e) {
			e.printStackTrace();
		}
		this.state = State.ESTABLISHED;
		return;
	}

	@Override
	public void send(String msg) throws ConnectException, IOException{
		if(this.state != State.ESTABLISHED) throw new ConnectException();
		KtnDatagram packet = K.makePacket(Flag.NONE, remotePort, remoteAddress, myPort, myAddress, msg, 0);
		
		sendDataPacketWithRetransmit(packet);
	}

	@Override
	protected boolean isValid(KtnDatagram packet){
		if (packet.calculateChecksum() == packet.getChecksum()) {
			return true;
		}
		Client.c.writeline("Calculated: " + packet.calculateChecksum() + " Reference: " + packet.getChecksum());
		return false;
	}

	@Override
	public Connection accept() throws IOException, SocketTimeoutException{
		throw new IOException("Sockets can't accept!");
	}
	
	public void addListener(MessageListener l) {
		listeners.add(l);
	}
	
	public void removeListener(MessageListener l) {
		listeners.remove(l);
	}

	@Override
	public void receivePacket(KtnDatagram packet){
		if (isValid(packet) && packet.getDest_addr() == myAddress && packet.getDest_port() == myPort) {
			if (packet.getFlag() == Flag.NONE) {
				for (MessageListener l : listeners) {
					l.messageReceived(packet.getPayload().toString());
				}
			} else if (packet.getFlag() == Flag.ACK) {
				if (this.state == State.ESTABLISHED) {
					//A previously sent packet has obviously been ack'd. Deal with it appropriately
				} else if (this.state == State.FIN_WAIT_1) {		//FIN_WAIT_1 should be waiting for a FIN or FINACK
					//Goto FIN_WAIT_2 (Waiting for a FIN)
				} else {
					//Something obviously went wrong. Do something about it
				}
				//TODO
			} else if (packet.getFlag() == Flag.FIN) {
				if (this.state == State.ESTABLISHED) {
					//Send FINACK - Go to LAST_ACK
				} else if (this.state == State.FIN_WAIT_1) {
					//Send FINACK
					//Goto CLOSE_WAIT
				} else if (this.state == State.FIN_WAIT_2) {
					//Send FINACK
					//Goto CLOSE_WAIT
				} else {
					//Something obviously went wrong. Do something about it
				}
				//TODO
			}
		}
		
	}

}
