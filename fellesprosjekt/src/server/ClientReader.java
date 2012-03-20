package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import dataobjects.ComMessage;

public class ClientReader extends Thread {
	
	private ObjectInputStream reader;
	private Socket socket;
	private MessageReceiver server;
	
	/**
	 * ClientReader er en tr�d som kontinuerlig leser fra en klient og sender meldingene vidre til en sentral server
	 * @param socket - socketen som 
	 * @param server - en messagereciever som leseren sender meldinger til
	 */
	public ClientReader(Socket socket, MessageReceiver server) {
		this.server = server;
		this.socket = socket;
		try {
			reader = new ObjectInputStream(socket.getInputStream());
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
			//ingenting, hvis det skjer noe her f�r det bare v�re, de er allerede frakoblet
			//egentlig kan man ikke annta dette, det kan skje skumle feil men det kan bli komplisert
		}
	}
	
	/**
	 * run-metoden, leser meldinger fra klienten og sender dem til message-recieveren
	 */
	public void run() {
		while(true){
			try {
				ComMessage message = (ComMessage)reader.readObject();
				if(message == null){
					disconnect();
					return;
				}
				server.receiveMessage(socket.getInetAddress(), message);
			} catch (IOException e) {
				disconnect();
				return;
			}catch(ClassNotFoundException e){
				disconnect();
				return;
			}
		}
	}
	
}//END
