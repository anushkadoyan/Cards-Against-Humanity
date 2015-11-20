package client;

import java.net.Socket;
import java.util.Vector;

import utilities.Card;
import utilities.Deck;
import utilities.Player;

public class PlayerManager {
	private static Socket socket;
	public static void setSocket(Socket s){
		socket = s;
	}
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
	public static Vector<Player> getScores(){
		return new Vector<Player>();
		//TODO
	}
	public static Deck getDeck(int deckID){//get
		//TODO
		return new Deck(null);
		
	}
}
