package server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import utilities.Card;
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
	public void sendGameState(){
		try{
			for(ServerClientCommunicator scc:sccVector){
				scc.sendGameState(game);
			}
		}
		catch(IOException ioe){
			//error
		}
	}
	
	public void addPlayer(Player p){
		game.addPlayer(p);
	}
	
	public Vector<Player> getPlayers(){
		return game.getPlayers();
	}
	
	public void run() {
		while(true){
			if(game.getJudge()==null){
				game.setJudge(sccVector.get(0).getPlayer());
				startGame();
			}
			if(game.getNumMovesExpected()>0){
				//not all players have finished selecting
			}
			else{
				
			}
		}
	}
	
	public Game getGame(){
		return game;
	}
	
	public void sendMessage(String text){
		try{
			for(ServerClientCommunicator scc:sccVector){
				scc.sendChatMessage(text);
			}
		}
		catch(IOException ioe){
			//error
		}
	}
	
	public void selectCard(int playerID, Card c){
		//todo: 
	}
	
	public void startGame(){
		game.startGame();;
	}
	
}