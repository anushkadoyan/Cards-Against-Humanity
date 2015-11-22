package server;

import java.io.IOException;
import java.net.ServerSocket;

import client.CAHClient;

public class Server {
	private ServerSocket ss;
	private ServerListener serverListener;
	public Server() {
		try{
			ss = new ServerSocket(6789);
		}
		catch(IOException ioe){
			System.out.println("Error initializing server: " + ioe.getMessage());
		}
		serverListener = new ServerListener(ss);
		serverListener.start();
	}
	public static void main(String [] args) {
		new Server();
		new CAHClient();
	}
}
