package client;

import java.net.Socket;
import java.util.Vector;

import utilities.Card;
import utilities.Deck;
import utilities.Player;

public class PlayerManager {
	private static ServerCommunicator sc = null;
	private static Player player = null;

	public static Card createCard(Card c){
		
		//TODO
		return new Card(null, null);
	}
	public static void editCard(Card c){
		//TODO
	}
	public static Player login(Player p){
		//TODO
		return new Player(null, null);
	}
	public static Deck createDeck(Deck d, Player p){
		//TODO
		return new Deck(null);
	}
	public static void editDeck(Deck d){
		//TODO
	}
	public static void selectCard(Card c){
		//TODO
	}
	public Vector<Player> getScores(){
		return new Vector<Player>();
		//TODO
	}
<<<<<<< HEAD
	public static Deck getDeck(int deckID){//get
		//TODO
		return new Deck(null);
		
=======
	public static void setServerCommunicator(ServerCommunicator sc2){
		sc = sc2;
>>>>>>> 2fa0c81ba1f5e20bffa3217406b836c21b1e6262
	}
}
