package client;

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
	public static void setServerCommunicator(ServerCommunicator sc2){
		sc = sc2;
	}
	
	// Accessors for player members.
	public String getPlayerUsername() {
		return player.getName();
	}
	
	public String getPlayerPassword() {
		return player.getPassword();
	}
	
	public int getPlayerID() {
		return player.getID();
	}
	
	public boolean isGuest() {
		return player.isGuest();
	}
	
	public Vector<Deck> getDecks() {
		return player.getPlayerDecks();
	}
}
