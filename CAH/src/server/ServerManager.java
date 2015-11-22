package server;

import java.sql.SQLException;

import utilities.Card;
import utilities.DBAccess;
import utilities.Deck;
import utilities.Game;
import utilities.Player;

public class ServerManager {
	private static int guestIndex = 1;
	private static int gameIndex = 1;
	
	public static Player VerifyCredentials(Player p){//receives player with no id
		if(p.getName().equals("Guest")){
			guestIndex++;
			return new Player("Guest"+guestIndex, "");
		}
		int result = -1;
		try{
			result = DBAccess.checkCredentials(p.getName(), p.getPassword());
		}
		catch(SQLException sqle){
			result = -2;//sqlconnection error
		}
		p.setID(result);
		if(result>0){
			p=loadPlayerInfo(p);
		}
		return p;
	}
	
	public static Card AddCard(Card c) throws SQLException{//receives card with no id
		c.setID(DBAccess.addCard(c.getDesc(), c.getBlackness(), c.getDeckID()));
		return c;
	}
	
	public static Game getGame(Game g){
		g.setID(gameIndex);
		gameIndex++;
		return g;
	}
	
	public static Player loadPlayerInfo(Player p){
		try {
			p.loadDecks(DBAccess.getPlayerDecks(p.getID()));
			// Make sure to load other info too.
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return p;
	}
	
	public static void addDeck(Deck d){
		try {
			DBAccess.addDeck(d);
		} catch (SQLException e) {
			//error
		}
	}
	public static void editDeck(Deck d){
		DBAccess.editDeck(d);
	}
	public static void editCard(Card c){
		DBAccess.editCardDescription(c.getID(), c.getDesc());
	}
}
