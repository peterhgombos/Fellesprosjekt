/*
 * Created on Oct 27, 2004
 */
package no.ntnu.fp.net.co;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import no.ntnu.fp.net.cl.ClException;
import no.ntnu.fp.net.cl.ClSocket;
import no.ntnu.fp.net.cl.KtnDatagram;
import no.ntnu.fp.net.cl.KtnDatagram.Flag;
import no.ntnu.fp.net.co.ReceiveConnectionWorker.ConnectionListener;

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

	private ClSocket clientSocket;
	private ConnectionListener connListener; //TODO Initialize


	@SuppressWarnings("serial")
	class NotImplementedException extends RuntimeException {}

	/** Keeps track of the used ports for each server port. */
	private static Map<Integer, Boolean> usedPorts = Collections.synchronizedMap(new HashMap<Integer, Boolean>());

	/**
	 * Initialise initial sequence number and setup state machine.
	 * 
	 * @param myPort
	 *            - the local port to associate with this connection
	 */
	public ConnectionImpl(int myPort) {
		this.state = State.CLOSED;
		this.myPort = myPort;
		this.myAddress = getIPv4Address();


		throw new NotImplementedException();
	}

	private String getIPv4Address() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		}
		catch (UnknownHostException e) {
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
	 * @throws ClException 
	 * @see Connection#connect(InetAddress, int)
	 */
	public void connect(InetAddress remoteAddress, int remotePort) throws IOException, SocketTimeoutException {
		KtnDatagram syn = constructInternalPacket(Flag.SYN);
		this.remotePort = remotePort;
		this.remoteAddress = remoteAddress.getHostAddress();
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new SendTimer(new ClSocket(), syn), 0, RETRANSMIT);
		KtnDatagram synack; 
		do{
			synack = receiveAck();
		}while(synack.getFlag() != Flag.SYN_ACK);
		timer.cancel();
		KtnDatagram ack = constructInternalPacket(Flag.ACK);
		try {
			simplySendPacket(ack);
		} catch (ClException e) {
			e.printStackTrace();
		}
		return;
	}

	/**
	 * Listen for, and accept, incoming connections.
	 * 
	 * @return A new ConnectionImpl-object representing the new connection.
	 * @see Connection#accept()
	 */
	public Connection accept() throws IOException, SocketTimeoutException {
		connListener = new ConnectionListener() {
			public void connectionReceived(Connection connection){
				
			}
		};
		
		ReceiveConnectionWorker receiveConn = new ReceiveConnectionWorker(this, connListener); //??
		receiveConn.run();
		
		
		
		return this;
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
		throw new NotImplementedException();
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
		ReceiveMessageWorker msgWorker = new ReceiveMessageWorker(this); //??
		throw new NotImplementedException();
	}

	/**
	 * Close the connection.
	 * 
	 * @see Connection#close()
	 */
	public void close() throws IOException {


		//Finally
		this.state = State.CLOSED;
		throw new NotImplementedException();
	}

	protected KtnDatagram constructDataPacket(String payload) {
		KtnDatagram packet = super.constructDataPacket(payload);
		packet.setChecksum(packet.calculateChecksum());
		return packet;

	}

	protected KtnDatagram constructInternalPacket(Flag flag) {
		KtnDatagram packet = super.constructInternalPacket(flag);
		packet.setChecksum(packet.calculateChecksum());
		return packet;

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
		if (packet.calculateChecksum() == packet.getChecksum()) {
			return true;
		}
		return false;
		//throw new NotImplementedException();
	}

}
