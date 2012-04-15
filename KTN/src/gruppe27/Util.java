package gruppe27;

import java.net.InetAddress;
import java.net.UnknownHostException;

import no.ntnu.fp.net.cl.KtnDatagram;
import no.ntnu.fp.net.cl.KtnDatagram.Flag;

public class Util {
	
	public static final int RETRANSMIT = 5000;
	
	public static KtnDatagram makePacket(Flag flag, int remotePort, String remoteAddress, int srcPort, String srcAddress, String data, int seq) {
		if(flag == Flag.ACK){
			throw new RuntimeException("ikke sett ACK her");
		}
		KtnDatagram packet = new KtnDatagram();
		packet.setPayload(data);
		packet.setDest_port(remotePort);
		packet.setDest_addr(remoteAddress);
		packet.setSrc_addr(srcAddress);
		packet.setSrc_port(srcPort);
		packet.setFlag(flag);
		packet.setSeq_nr(seq);
		packet.setAck(0);
		packet.setPayload(data);
		
		packet.setChecksum(packet.calculateChecksum());
		return packet;
	}
	
	public static KtnDatagram makeAckPack(boolean syn, int remotePort, String remoteAddress, int srcPort, String srcAddress, int ack) {
		KtnDatagram packet = new KtnDatagram();
		packet.setDest_port(remotePort);
		packet.setDest_addr(remoteAddress);
		packet.setSrc_addr(srcAddress);
		packet.setSrc_port(srcPort);
		packet.setSeq_nr(0);
		packet.setAck(ack);
		packet.setFlag(syn ? Flag.SYN_ACK : Flag.ACK);
		
		packet.setChecksum(packet.calculateChecksum());
		return packet;
	}
	
	public static String getIPv4Address() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		}
		catch (UnknownHostException e) {
			return "127.0.0.1";
		}
	}
	
	public static boolean isValid(KtnDatagram packet){
		if (packet.calculateChecksum() == packet.getChecksum()) {
			return true;
		}
		return false;
	}
	
}
