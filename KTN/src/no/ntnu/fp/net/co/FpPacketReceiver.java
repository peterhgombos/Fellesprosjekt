package no.ntnu.fp.net.co;

import no.ntnu.fp.net.cl.KtnDatagram;

public interface FpPacketReceiver {

	void receivePacket(KtnDatagram packet);
	
}
