package server;

import java.io.IOException;
import java.net.ServerSocket;

import client.CAHClient;

public class Server {
	private ServerSocket ss;
	private ServerListener serverListener;
	private Database db;
	
	public Server() {
		System.out.println("Creating a server.");
		createDatabase();
		try{
			ss = new ServerSocket(6789);
		}
		catch(IOException ioe){
			System.out.println("Error initializing server: " + ioe.getMessage());
		}
		serverListener = new ServerListener(ss);
		serverListener.start();
	}
	private void createDatabase() {
		db = new Database();
//		db.drop();
	}

	public static void main(String [] args) {
		new Server();
	}
}
