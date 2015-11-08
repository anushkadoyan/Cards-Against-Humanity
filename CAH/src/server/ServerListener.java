package server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;


public class ServerListener extends Thread {

	private ServerSocket ss;
	private Vector<ServerClientCommunicator> sccVector;
	private Vector<GameController> gcVector;
	public ServerListener(ServerSocket ss) {
		this.ss = ss;
		sccVector = new Vector<ServerClientCommunicator>();
		gcVector = new Vector<GameController>();
	}
	
	public void removeServerClientCommunicator(ServerClientCommunicator scc) {
		sccVector.remove(scc);
	}
	
	public void run() {
		try {
			while(true) {
				Socket s = ss.accept();
				try {
					ServerClientCommunicator scc = new ServerClientCommunicator(s, this);
					if(scc.isValid()){
						scc.start();
						sccVector.add(scc);
					}
				} catch (IOException ioe) {
					//error
				}
			}
		} catch(BindException be) {
			//error
		}
		catch (IOException ioe) {
			//error
		} 
		finally {
			if (ss != null) {
				try {
					ss.close();
				} catch (IOException ioe) {
					//error
				}
			}
		}
	}
}
