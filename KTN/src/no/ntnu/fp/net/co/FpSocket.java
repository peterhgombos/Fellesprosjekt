package no.ntnu.fp.net.co;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Timer;

import test.Client;
import test.K;

import no.ntnu.fp.net.cl.ClException;
import no.ntnu.fp.net.cl.ClSocket;
import no.ntnu.fp.net.cl.KtnDatagram;
import no.ntnu.fp.net.cl.KtnDatagram.Flag;

public class FpSocket extends AbstractConnection implements FpPacketReceiver{

	
	ClSocket a2Socket;
	
	public FpSocket(int port){
		this.myPort = port;
		this.myAddress = "localhost";
		this.a2Socket = new ClSocket();
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Connection accept() throws IOException, SocketTimeoutException{
		throw new IOException("Sockets can't accept!");
	}

	@Override
	public void receivePacket(KtnDatagram packet){
		// TODO Auto-generated method stub
		
	}

}
