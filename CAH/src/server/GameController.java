package server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import utilities.Deck;
import utilities.Game;
import utilities.Player;

public class GameController extends Thread {
	private Game game;
	private Vector<ServerClientCommunicator> sccVector;
	public GameController(Game g) {
		game = ServerManager.getGame(g);
		sccVector = new Vector<ServerClientCommunicator>();
	}
	
	public void addServerClientCommunicator(ServerClientCommunicator scc){
		sccVector.add(scc);
	}
	public void removeServerClientCommunicator(ServerClientCommunicator scc) {
		sccVector.remove(scc);
	}
	
	public void run() {
		//start game, game logic here
	}
}