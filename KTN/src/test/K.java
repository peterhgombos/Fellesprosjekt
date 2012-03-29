package test;

import no.ntnu.fp.net.cl.KtnDatagram;
import no.ntnu.fp.net.cl.KtnDatagram.Flag;

public class K {
	
	public static KtnDatagram makePacket(Flag flag, int remotePort, String remoteAddress, int srcPort, String srcAddress, String data, int seq) {
		KtnDatagram packet = new KtnDatagram();
		packet.setDest_port(remotePort);
		packet.setDest_addr(remoteAddress);
		packet.setSrc_addr(srcAddress);
		packet.setSrc_port(srcPort);
		packet.setFlag(flag);
		packet.setSeq_nr(seq);
		packet.setPayload(data);
		packet.setAck(-1);
		
		packet.setChecksum(packet.calculateChecksum());
		return packet;
	}
	public static KtnDatagram makeAckPack(Flag flag, int remotePort, String remoteAddress, int srcPort, String srcAddress, int ack, int seq) {
		KtnDatagram packet = new KtnDatagram();
		packet.setDest_port(remotePort);
		packet.setDest_addr(remoteAddress);
		packet.setSrc_addr(srcAddress);
		packet.setSrc_port(srcPort);
		packet.setFlag(flag);
		packet.setSeq_nr(seq);
		packet.setAck(ack);
		packet.setPayload("");
		
		packet.setChecksum(packet.calculateChecksum());
		return packet;
	}
	
}
