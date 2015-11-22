package client;

import java.sql.SQLException;
import java.util.Vector;

import utilities.Card;
import utilities.DBAccess;
import utilities.Deck;
import utilities.Player;

public class PlayerManager {
	private static ServerCommunicator sc = null;
	private static Player player = null;

	public static Card createCard(Card c) throws SQLException{	
		c.setID(DBAccess.addCard(c.getDesc(), c.getBlackness(), c.getDeckID()));
		return c;
	}


<<<<<<< HEAD

	public static void editCard(int cardID, String cardText) throws SQLException{

=======
	public static void editCard(int cardID, String cardText) throws SQLException{
>>>>>>> ac97f66b66cec09fd45aa62eb89bb1d84c28cec7
		//TODO
		DBAccess.editCardDescription(cardID, cardText);
	}
	
	public static Player login(Player p) throws SQLException{
		//TODO
		if( DBAccess.checkCredentials(p.getName(), p.getPassword()) < 0 ) {
			//login failed.
			return null;
		}
		else {
			p.setID(DBAccess.checkCredentials(p.getName(), p.getPassword()));
		}
		return p;
	}
	
	public static Deck createDeck(Deck d) throws SQLException{
		d.setCards(DBAccess.getDeckCards(d.getID()));
		return d;
	}
	
	
	/*
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
	*/

		
	public static void setServerCommunicator(ServerCommunicator sc2){
		sc = sc2;
	}

	public static Deck getDeck(int deckID) throws SQLException{
		return DBAccess.getPlayerDeck(deckID);
	}
		
	public static void setPlayer(Player p) {
		player = p;
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
