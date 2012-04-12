package test;

import gruppe27.FpServerSocket;
import gruppe27.FpSocket;

import java.io.IOException;
import java.net.SocketTimeoutException;

import no.ntnu.fp.net.cl.ClException;

public class Server {
	
	public static Console c = new Console("Server");
	
	public static void main(String[] args){
		
		Server server = new Server();
		server.run();
	}
	
	public void run() {
		FpServerSocket serverSocket = new FpServerSocket(44065);
		
		try{
			while(true){
				readThread leser = new readThread(serverSocket.accept());
				c.writeline("got connection");
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
				
				c.writeline(socket.receive());
				Thread.sleep(10);
				socket.send("her er svar");
				
			
			}catch(IOException e){
				e.printStackTrace();
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			return;
		}
	}
	
}
