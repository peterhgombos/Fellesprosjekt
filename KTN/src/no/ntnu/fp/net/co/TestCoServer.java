/*
 * Created on Oct 27, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package no.ntnu.fp.net.co;

import java.io.EOFException;
import java.io.IOException;

import no.ntnu.fp.net.admin.Log;
import no.ntnu.fp.net.co.Connection;

/**
 * Simplest possible test application, server part.
 *
 * @author seb, steinjak
 *
 */
public class TestCoServer {

	/**
	 * Empty.
	 */
	public TestCoServer() {
	}

	/**
	 * Program Entry Point.
	 */
	public static void main (String args[]){

		// Create log
		new Log();
		Log.setLogName("Server");

		// server connection instance, listen on port 5555
		Connection server = new ConnectionImpl(5555);
		// each new connection lives in its own instance
		try {
			Connection conn = server.accept();

			try {
					//receive five messages
					String m1 = conn.receive();
					Log.writeToLog("Message got through to server: " + m1, "TestServer");
					String m2 = conn.receive();
					Log.writeToLog("Message got through to server: " + m2, "TestServer");
					String m3 = conn.receive();
					Log.writeToLog("Message got through to server: " + m3, "TestServer");
					String m4 = conn.receive();
					Log.writeToLog("Message got through to server: " + m4, "TestServer");
					String m5 = conn.receive();
					Log.writeToLog("Message got through to server: " + m5, "TestServer");
					
					//send two messages
					conn.send("fra server 1");
					conn.send("fra server 2");
					
					//wait for close
					conn.receive();
					System.exit(0);
			} catch (EOFException e){
				Log.writeToLog("Got close request (EOFException), closing.", "TestServer");
				conn.close();
			}

			System.out.println("SERVER TEST FINISHED");
			Log.writeToLog("TEST SERVER FINISHED","TestServer");
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
}
