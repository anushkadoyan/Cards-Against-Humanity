package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class DBAccess {
	private static Connection conn = null;
	private static void connect(){
		try{
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/factory?user=root&password=root");
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	private static void disconnect(){
		try{
			conn.close();
		}catch(SQLException e){e.printStackTrace();}
	}
	public DBAccess() throws SQLException{
		conn = DriverManager.getConnection(Constants.databaseURL, Constants.databaseUsername, Constants.databasePassword);
	}
	
	public static int addCard(String cardDescription, Boolean isBlack, int deckID) throws SQLException{
		connect();
		PreparedStatement ps = conn.prepareStatement("INSERT INTO Cards_Table (text, is_black, deck_id) VALUES (?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, cardDescription);
		ps.setBoolean(2, isBlack);
		ps.setInt(3, deckID);
		int affectedRows = ps.executeUpdate();
		disconnect();
		if(affectedRows==0){
			throw new SQLException("Create card failed, no rows affected.");
		}
		try(ResultSet generatedKeys = ps.getGeneratedKeys()){
			if(generatedKeys.next()){
				return generatedKeys.getInt(1);
			}
			else{
				throw new SQLException("Create card failed, no ID obtained.");
			}
		}
	}
	public static int checkCredentials(String user, String password) throws SQLException{
		connect();
		PreparedStatement ps = conn.prepareStatement("SELECT id FROM Player_Table WHERE user_name = ? AND password = ?");
		ps.setString(1, user);
		ps.setString(2, password);
		ResultSet rs = ps.executeQuery();
		disconnect();
		int id = -1;
		if(rs.next()){
			id=rs.getInt("ID");
		}
		return id;
	}
	public static Vector<Deck> getPlayerDecks(int playerID) throws SQLException{
		Vector<Deck> result = new Vector<Deck>();
		Vector<Integer> deckIDs = getPlayerDeckIDs(playerID);
		for(Integer deckId:deckIDs){
			result.add(getPlayerDeck(deckId));
		}
		return result;
	}
	public static Vector<Integer> getPlayerDeckIDs(int playerID) throws SQLException{
		Vector<Integer> result = new Vector<Integer>();
		connect();
		PreparedStatement ps = conn.prepareStatement("SELECT deck_id FROM Player_Decks_Table WHERE player_id = ?");
		ps.setInt(1, playerID);
		ResultSet rs = ps.executeQuery();
		disconnect();
		while(rs.next()){
			result.add(rs.getInt("deck_id"));
		}
		return result;
	}
	public static Deck getPlayerDeck(int deckID) throws SQLException{
		connect();
		PreparedStatement ps = conn.prepareStatement("SELECT deck_name FROM Decks_Table WHERE id = ?");
		ps.setInt(1, deckID);
		ResultSet rs = ps.executeQuery();
		disconnect();
		String name = "";
		if(rs.next()){
			name=rs.getString("deck_name");
		}
		Deck deck = new Deck(deckID, name);
		deck.setCards(getDeckCards(deckID));
		return deck;
	}
	public static Vector<Card> getDeckCards(int deckID) throws SQLException{
		Vector<Card> result = new Vector<Card>();
		connect();
		PreparedStatement ps = conn.prepareStatement("SELECT id, text, is_black FROM Cards_Table WHERE deck_id = ?");
		ps.setInt(1, deckID);
		ResultSet rs = ps.executeQuery();
		disconnect();
		while(rs.next()){
			Card c = new Card(rs.getInt("id"), rs.getString("text"), rs.getBoolean("is_black"));
			c.setDeckID(deckID);
			result.add(c);
		}
		return result;
	}
	public static Player getFullPlayerInfo(int playerID){
		//TODO: gets all player info, with cards, decks, etc.
		Vector<Deck> playerDecks = null;
		Player player = null;
		try {
			playerDecks = getPlayerDecks(playerID); // Gets player decks.
			for( int i = 0; i < playerDecks.size(); i++ ) {
				playerDecks.get(i).setCards(getDeckCards(playerDecks.get(i).getID())); // Sets deck cards for each deck.
			}
			connect();
			String statement = "SELECT * from Player_Table WHERE id = " + playerID;
			PreparedStatement ps = conn.prepareStatement(statement);
			ResultSet rs = ps.executeQuery();
			player = new Player(rs.getString("user_name"), rs.getString("password"));
			disconnect();
		} catch (SQLException e) {
			System.out.println("Error establishing database connection in DBAccess.getFullPlayerInfo(): " + e.getMessage());
		}
		
		// Now that we have the cards and created our Player, assign them to the Player.
		// Should a null check be here?
		player.loadDecks(playerDecks);
		player.setID(playerID);
		return player;
	}
}
