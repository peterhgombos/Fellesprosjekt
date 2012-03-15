package client.connection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;

import utilities.Console;

public class Connection {

	private static Socket socket;
	public static Console console;
	public static ServerWriter writer;

	public static void connect() throws IOException {
		console = new Console();
		socket = new Socket();
		socket.connect(new InetSocketAddress("localhost", server.ServerConstants.PORT));
		ServerReader serverReader = new ServerReader(socket);
		serverReader.start();
		writer = new ServerWriter(socket);
		writer.send("melding");
	}
	
	public static void main(String[] args) throws IOException {
		Connection.connect();
	}
}
