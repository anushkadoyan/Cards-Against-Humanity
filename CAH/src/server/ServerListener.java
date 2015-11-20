package server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;


public class ServerListener extends Thread {

	private ServerSocket ss;
	private Vector<ServerClientCommunicator> sccVector;
	private Vector<ServerClientCommunicator> validscc;
	private GameController gc;
	public ServerListener(ServerSocket ss) {
		this.ss = ss;
		sccVector = new Vector<ServerClientCommunicator>();
		validscc = new Vector<ServerClientCommunicator>();
		gc = null;
	}
	
	public void removeServerClientCommunicator(ServerClientCommunicator scc) {
		sccVector.remove(scc);
	}
	public void addValidSCC(ServerClientCommunicator scc){
		validscc.add(scc);
	}
	public void removeValidSCC(ServerClientCommunicator scc){
		validscc.remove(scc);
	}
	
	public void run() {
		try {
			while(true) {
				Socket s = ss.accept();
				try {
					ServerClientCommunicator scc = new ServerClientCommunicator(s, this);
					scc.start();
					sccVector.add(scc);
				} catch (IOException ioe) {
					System.out.println("ioe in ServerListener.run() while(true): " + ioe.getMessage());
				}
			}
		} catch(BindException be) {
			System.out.println("be in ServerListener.run(): " + be.getMessage());
		}
		catch (IOException ioe) {
			System.out.println("ioe in ServerListener.run() initial try block: " + ioe.getMessage());
		} 
		finally {
			if (ss != null) {
				try {
					ss.close();
				} catch (IOException ioe) {
					System.out.println("ioe in ServerListener.run() finally block: " + ioe.getMessage());
				}
			}
		}
	}
}
