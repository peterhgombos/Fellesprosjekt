package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import server.constants.ServerConstants;
import utilities.Console;

public class ClientReader extends Thread {
	
	private BufferedReader reader;
	private Socket socket;
	private MessageReceiver server;
	
	/**
	 * ClientReader er en tråd som kontinuerlig leser fra en klient og sender meldingene vidre til en sentral server
	 * @param socket - socketen som 
	 * @param server - en messagereciever som leseren sender meldinger til
	 */
	public ClientReader(Socket socket, MessageReceiver server) {
		this.server = server;
		this.socket = socket;
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			ServerConstants.console.writeline(e.getMessage());
		}
	}
	
	/**
	 * metoden kobler fra klienten
	 */
	private void disconnect(){
		ServerConstants.console.writeline("Client has disconnected");
		try {
			reader.close();
			socket.close();
		} catch (IOException e) {
			//ingenting, hvis det skjer noe her får det bare være, de er allerede frakoblet
			//egentlig kan man ikke annta dette, det kan skje skumle feil men det kan bli komplisert
		}
	}
	
	/**
	 * run-metoden, leser meldinger fra klienten og sender dem til message-recieveren
	 */
	public void run() {
		while(true){
			try {
				String message = reader.readLine();
				if(message == null){
					disconnect();
					return;
				}
				server.receiveMessage(message);
			} catch (IOException e) {
				disconnect();
				return;
			}
		}
	}
	
}//END
