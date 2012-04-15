package gruppe27.test;

import gruppe27.FpServerSocket;
import gruppe27.FpSocket;

import java.io.IOException;
import java.net.SocketTimeoutException;

import no.ntnu.fp.net.cl.ClException;

public class Server {
	
	public static Console c = new Console("Server");
	
	public static void main(String[] args){
		new Server();
	}
	
	public Server(){
		FpServerSocket serverSocket = new FpServerSocket(44065);
		
		try{
			while(true){
				c.writeline("Listening...");
				readThread leser = new readThread(serverSocket.accept());
				c.writeline("Connection Established");
				leser.start();
			}
		}catch(SocketTimeoutException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}catch(ClException e){
			e.printStackTrace();
		}
	}
	
	class readThread extends Thread {
		private FpSocket socket;
		
		public readThread(FpSocket s){
			socket = s;
		}
		
		public void run(){
			try{
				c.writeline("Receiving message...");
				c.writeline(socket.receive());
				c.writeline("Sending message...");
				socket.send("her er svar");
				c.writeline("Receiving message...");
				c.writeline(socket.receive());
				c.writeline("DONE");
			}catch(IOException e){
				c.writeline("ERROR:" + e.getMessage());
			}
			return;
		}
	}
}
