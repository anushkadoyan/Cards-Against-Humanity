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
	
	public static Card AddCard(Card c, int deckID){//receives card with no id
		return c;
		//TODO: see verifyCredentials(), pass primitives only to DBAccess, get ID, return card with id.
	}
	
	public static Game getGame(Game g){
		g.setID(gameIndex);
		gameIndex++;
		return g;
	}
	public static Player loadPlayerInfo(Player p){
		/*try {
			p.loadDecks(DBAccess.getPlayerDecks(p.getID()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		return p;
	}
}
