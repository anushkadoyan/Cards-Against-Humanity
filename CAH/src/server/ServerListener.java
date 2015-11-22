package server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import utilities.Game;
import utilities.Player;


public class ServerListener extends Thread {

	private ServerSocket ss;
	private Vector<ServerClientCommunicator> sccVector;
	private Vector<ServerClientCommunicator> validscc;
	private Vector<GameController> gcVector;
	public ServerListener(ServerSocket ss) {
		this.ss = ss;
		sccVector = new Vector<ServerClientCommunicator>();
		validscc = new Vector<ServerClientCommunicator>();
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
	public void addGame(Game g){
		GameController gc = new GameController(g);
		gcVector.add(gc);
	}
	public void removeGame(Game g){
		for(GameController gc: gcVector){
			if(gc.getGame().getID()==g.getID()){
				gcVector.remove(gc);
				break;
			}
		}
	}
	public void joinGame(Player p, int gameID){
		for(GameController gc: gcVector){
			if(gc.getGame().getID()==gameID){
				gc.addPlayer(p);
				break;
			}
		}
	}
	public void sendGameInfo(int gameID){
		try{
			for(GameController gc: gcVector){
				if(gc.getGame().getID()==gameID){
					Vector<Player> players = gc.getPlayers();
					for(Player p:players){
						for(ServerClientCommunicator scc: sccVector){
							if(scc.getPlayer().getID()==p.getID()){
								scc.sendGameState(gc.getGame());
								break;
							}
						}
						//error
					}
					break;
				}
			}
		}
		catch(IOException ioe){
			//error
		}
	}
	public ServerClientCommunicator getSCC(int playerID){
		for(ServerClientCommunicator scc:sccVector){
			if(scc.getPlayer().getID()==playerID){
				return scc;
			}
		}
		return null;
	}
}
