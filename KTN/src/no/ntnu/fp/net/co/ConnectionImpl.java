/*
 * Created on Oct 27, 2004
 */
package no.ntnu.fp.net.co;

import java.io.EOFException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import no.ntnu.fp.net.cl.ClException;
import no.ntnu.fp.net.cl.ClSocket;
import no.ntnu.fp.net.cl.KtnDatagram;
import no.ntnu.fp.net.cl.KtnDatagram.Flag;

/**
 * Implementation of the Connection-interface. <br>
 * <br>
 * This class implements the behaviour in the methods specified in the interface
 * {@link Connection} over the unreliable, connectionless network realised in
 * {@link ClSocket}. The base class, {@link AbstractConnection} implements some
 * of the functionality, leaving message passing and error handling to this
 * implementation.
 * 
 * @author Sebj�rn Birkeland and Stein Jakob Nordb�
 * @see no.ntnu.fp.net.co.Connection
 * @see no.ntnu.fp.net.cl.ClSocket
 */
public class ConnectionImpl extends AbstractConnection {

	/** Keeps track of the used ports for each server port. */
	private static Map<Integer, Boolean> usedPorts = Collections.synchronizedMap(new HashMap<Integer, Boolean>());

	private int randomPort(){
		int nextport;
		do{
			nextport = 1050 + (int)(Math.random()*63050);
		}while(usedPorts.containsKey(nextport));
		return nextport;
	}

	/**
	 * Initialise initial sequence number and setup state machine.
	 * 
	 * @param myPort
	 *            - the local port to associate with this connection
	 */
	public ConnectionImpl(int myPort) {
		usedPorts.put(myPort, true);
		this.myPort = myPort;
		this.myAddress = getIPv4Address();
	}

	private String getIPv4Address() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		}catch (UnknownHostException e) {
			return "127.0.0.1";
		}
	}

	/**
	 * Establish a connection to a remote location.
	 * 
	 * @param remoteAddress
	 *            - the remote IP-address to connect to
	 * @param remotePort
	 *            - the remote portnumber to connect to
	 * @throws IOException
	 *             If there's an I/O error.
	 * @throws java.net.SocketTimeoutException
	 *             If timeout expires before connection is completed.
	 * @see Connection#connect(InetAddress, int)
	 */
	public void connect(InetAddress remoteAddress, int remotePort) throws IOException, SocketTimeoutException {

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(state != State.CLOSED){
			throw new IOException("Attempted connect on connected socket");
		}
		this.remoteAddress = remoteAddress.getHostAddress();
		this.remotePort = remotePort;

		try{
			KtnDatagram syn = constructInternalPacket(Flag.SYN);
			state = State.SYN_SENT;
			KtnDatagram synack = sendPacketWithTimeout(syn);
			this.remotePort = synack.getSrc_port();

			sendAck(synack, false);
			state = State.ESTABLISHED;
			return;

		}catch(IOException e){

			internalClose();
			throw new IOException("Could not connect!");
		}

	}

	/**
	 * Listen for, and accept, incoming connections.
	 * 
	 * @return A new ConnectionImpl-object representing the new connection.
	 * @see Connection#accept()
	 */
	public Connection accept() throws IOException, SocketTimeoutException {
		if(state != State.CLOSED){
			throw new IOException("Attempted accept on connected socket");
		}
		//waits for incoming connections

		while(true){
			state = State.LISTEN;

			KtnDatagram syn = null;

			syn = receivePacket(true);
			while(!isValid(syn)){
				syn = receivePacket(true);
			}

			//when i new connection is received this method creates an instance of Conimpl and lets that instance handle the rest of the connection
			ConnectionImpl c = new ConnectionImpl(randomPort());
			c.state = State.SYN_RCVD;
			c.remotePort = syn.getSrc_port();
			c.remoteAddress = syn.getSrc_addr();
			c.sendAck(syn, true);

			KtnDatagram synAck = c.receiveAck();
			if(synAck != null){
				c.state = State.ESTABLISHED;
				state = State.CLOSED;
				return c;
			}
		}
	}

	/**
	 * Send a message from the application.
	 * 
	 * @param msg
	 *            - the String to be sent.
	 * @throws ConnectException
	 *             If no connection exists.
	 * @throws IOException
	 *             If no ACK was received.
	 * @see AbstractConnection#sendDataPacketWithRetransmit(KtnDatagram)
	 * @see no.ntnu.fp.net.co.Connection#send(String)
	 */
	public void send(String msg) throws ConnectException, IOException {
		if(state != State.ESTABLISHED){
			throw new IOException("Attempted send on disconnected socket");
		}

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}

		KtnDatagram tosend = constructDataPacket(msg);
		lastDataPacketSent = tosend;
		KtnDatagram ack = null;
		int again = 3;
		while(!isValid(ack)){
			if(ack == null){
				if(again > 0){
					try{
						ack = sendDataPacketWithRetransmit(tosend);
					}catch(EOFException eof){
						remoteClose();
						return;
					}
					again--;
				}else{
					internalClose();
					throw new IOException("Connection lost");
				}
			}else{
				again = 3;
				ack = sendDataPacketWithRetransmit(tosend);
			}
		}
	}

	/**
	 * Wait for incoming data.
	 * 
	 * @return The received data's payload as a String.
	 * @see Connection#receive()
	 * @see AbstractConnection#receivePacket(boolean)
	 * @see AbstractConnection#sendAck(KtnDatagram, boolean)
	 */
	public String receive() throws ConnectException, IOException {
		if(state != State.ESTABLISHED){
			throw new IOException("Attempted receive on disconnected socket");
		}
		
		KtnDatagram packet = null;
		try{
			do{
				packet = receivePacket(false);
				if(lastValidPacketReceived != null && packet.getSeq_nr() == lastValidPacketReceived.getSeq_nr()){
					sendAck(packet, false);
				}
			}while(!isValid(packet));
		}catch(EOFException e){
			remoteClose();
			return null;
		}

		sendAck(packet, false);
		lastValidPacketReceived = packet;
		return packet.getPayload().toString();
	}

	private KtnDatagram sendPacketWithTimeout(KtnDatagram packet) throws IOException {
		int maxAttempts = 5;
		int attempts = 0;
		while(attempts < maxAttempts){
			try{
				simplySendPacket(packet);
			}catch(ClException e){
				//prøver igjen
			}
			KtnDatagram ack = null;
			try{
				ack = receiveAck();
			}catch(EOFException e){
				e.printStackTrace();
				remoteClose();
			}

			if(ack == null || !isValid(ack)){
				attempts++;
			}else{
				return ack;
			}
		}
		throw new IOException("Failed to send packet, connection timed out");
	}


	public void remoteClose() throws IOException{
		try {
			state = State.FIN_WAIT_2;

			KtnDatagram finack = constructInternalPacket(Flag.ACK);
			while(true){
				try{
					simplySendPacket(finack);
				}catch (SocketException se) {
					//Ignore
				}
				catch(ClException e){
					//Ignore
				}

				KtnDatagram fin = constructInternalPacket(Flag.FIN);
				Thread.sleep(100);
				KtnDatagram lastAck = sendPacketWithTimeout(fin); //Wait for the last ack before close
				//Will do active close if receives ack. Times out to passive close otherwise
				if(lastAck.getFlag() != Flag.FIN){
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		internalClose();
	}

	/**
	 * Close the connection.
	 * 
	 * @see Connection#close()
	 */
	public void close() throws IOException {
		try{Thread.sleep(100);}catch(InterruptedException e){}
		try {
			if(state != State.ESTABLISHED){
				throw new IOException();
			}

			KtnDatagram thisfin = constructInternalPacket(Flag.FIN);
			state = State.FIN_WAIT_1;

			KtnDatagram finack;
			KtnDatagram remotefin = null;
			while(true){
				
				try {
					finack = sendPacketWithTimeout(thisfin);
					if (isValid(finack)&& finack.getFlag() == Flag.ACK) {
						System.err.println("****************************************************** FINACK received as expected");
						state = State.FIN_WAIT_2;
						break;
					}
					else if(isValid(finack) && finack.getFlag() == Flag.FIN){
						//have received FIN when waiting for ack, but it's okay because FIN implies ACK. Just skips a step.
						System.err.println("****************************************** FIN received when FINACK expected");
						remotefin = finack;
						state = State.LAST_ACK;
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			if (state == State.FIN_WAIT_2) {
				while(true){
					//Waits for FIN from other side
					while(!isValid(remotefin)){
						remotefin = receivePacket(true);
					}

					sendAck(remotefin, false); //Will send this ack every time the while loop runs.
					state = State.CLOSE_WAIT;
					remotefin = receiveAck(); //Will continue to listen for FIN's until none are received. This is in case the other side never received it's ack, and to increase chance of active disconnect
					if(remotefin == null){
						//Breaks while loop after TIMEOUT time
						break;
					}
				}
			} else if (state == State.LAST_ACK) {
				while (true) {
					//Repeats the behavior that's over, except the waiting for FIN part.
					sendAck(remotefin, false);
					state = State.CLOSE_WAIT;
					remotefin = receiveAck();
					if(remotefin == null){
						break;
					}
				}
			}


			internalClose();
		} catch (Exception e) {
			e.printStackTrace();
		}
		internalClose();
	}

	private void internalClose(){
		usedPorts.remove(myPort);
		state = State.CLOSED;
		myPort = -1;
		myAddress = null;
		remotePort = -1;
		remoteAddress = null;
		lastDataPacketSent = null;
		lastValidPacketReceived = null;
	}

	/**
	 * Test a packet for transmission errors. This function should only called
	 * with data or ACK packets in the ESTABLISHED state.
	 * 
	 * @param packet
	 *            Packet to test.
	 * @return true if packet is free of errors, false otherwise.
	 */
	protected boolean isValid(KtnDatagram packet) {
		if(packet == null){
			System.err.println("*******************************************NULLPAKKE");
			return false;
		}

		if(packet.calculateChecksum() != packet.getChecksum()){
			System.err.println("*******************************************INVALID CHECKSUM");
			return false;
		}

		if(packet.getFlag() == Flag.NONE && packet.getPayload() == null){
			System.err.println("*******************************************NO DATA IN DATA PACKET");
			return false;
		}

		if(state == State.LISTEN && packet.getFlag() != Flag.SYN){
			return false;
		}

		if(lastDataPacketSent != null && state != State.FIN_WAIT_1 && state != State.FIN_WAIT_2 && packet.getFlag() == Flag.ACK && packet.getAck() != lastDataPacketSent.getSeq_nr()){
			System.err.println("*******************************************WRONG ACKSEQ");
			return false;
		}

		if(lastValidPacketReceived != null && packet.getFlag() == Flag.NONE && packet.getSeq_nr() <= lastValidPacketReceived.getSeq_nr()){
			System.err.println("*******************************************OUT OF ORDER");
			return false;
		}


		return true;
	}
}