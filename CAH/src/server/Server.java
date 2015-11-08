package server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
	private ServerSocket ss;
	private ServerListener serverListener;
	public Server() {
		try{
			ss = new ServerSocket(6788);
		}
		catch(IOException ioe){
			//TODO
		}
		serverListener = new ServerListener(ss);
		serverListener.start();
	}
	public static void main(String [] args) {
		new Server();
	}
}