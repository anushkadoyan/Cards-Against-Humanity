package client;

import java.util.Vector;

import utilities.Card;
import utilities.Deck;
import utilities.Player;

public class PlayerManager {
	private static ServerCommunicator sc = null;
	private static Player player = null;

<<<<<<< HEAD
	public static Card createCard(Card c){	
		// Receives a card with no ID.
		// Return a card with an ID to the client.
		//TODO
		return new Card(null, null);
=======
	public static void createCard(Card c){		
		
>>>>>>> a473dca4746909ea8860982a4670eb7599b3a05f
	}
	public static void editCard(Card c){
		//TODO
	}
	public static Player login(Player p){
		//TODO
		return new Player(null, null);
	}
	public static Deck createDeck(Deck d){
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
//<<<<<<< HEAD
=======
>>>>>>> a473dca4746909ea8860982a4670eb7599b3a05f
	public static Deck getDeck(int deckID){//get
		//TODO
		return new Deck(null);
	}
		
<<<<<<< HEAD
//=======
	public static void setServerCommunicator(ServerCommunicator sc2){
		sc = sc2;
//>>>>>>> 2fa0c81ba1f5e20bffa3217406b836c21b1e6262
=======
	public static void setServerCommunicator(ServerCommunicator sc2){
		sc = sc2;
>>>>>>> a473dca4746909ea8860982a4670eb7599b3a05f
	}
	
	// Accessors for player members.
	public static String getPlayerUsername() {
		return player.getName();
	}
	
	public static String getPlayerPassword() {
		return player.getPassword();
	}
	
	public static int getPlayerID() {
		return player.getID();
	}
	
	public static boolean isGuest() {
		return player.isGuest();
	}
	
	public static Vector<Deck> getDecks() {
		return player.getPlayerDecks();
	}
}
