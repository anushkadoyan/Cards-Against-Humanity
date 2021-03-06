package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import server.Database;

public class DBAccess {
	private static Database db = new Database();
	private static Connection conn = db.getConnection();

	private static void connect(){
//		try{
//			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?user=root&password=root");
//		}catch(SQLException e){
//			e.printStackTrace();
//		}
		db.connect();
		conn = db.getConnection();
	}
	private static void disconnect(){
//		try{
//			conn.close();
//		}catch(SQLException e){e.printStackTrace();}
		db.disconnect();
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
		System.out.println("Checking credentials");
		connect();
		PreparedStatement ps = conn.prepareStatement("SELECT id FROM Player_Table WHERE user_name = ? AND password = ?");
		ps.setString(1, user);
		ps.setString(2, password);
		ResultSet rs = ps.executeQuery();
		int id = -1;
		if(rs.next()){
			id=rs.getInt("id");
		}
		disconnect();
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
		PreparedStatement ps = conn.prepareStatement("SELECT deck_id FROM Players_Decks_Table WHERE player_id = ?");
		ps.setInt(1, playerID);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			result.add(rs.getInt("deck_id"));
		}
		disconnect();
		return result;
	}
	public static Deck getPlayerDeck(int deckID) throws SQLException{
		connect();
		PreparedStatement ps = conn.prepareStatement("SELECT deck_name FROM Decks_Table WHERE id = ?");
		ps.setInt(1, deckID);
		ResultSet rs = ps.executeQuery();
		String name = "";
		if(rs.next()){
			name=rs.getString("deck_name");
		}
		Deck deck = new Deck(deckID, name);
		deck.setCards(getDeckCards(deckID));
		disconnect();
		return deck;
	}
	public static Vector<Card> getDeckCards(int deckID) throws SQLException{
		Vector<Card> result = new Vector<Card>();
		connect();
		PreparedStatement ps = conn.prepareStatement("SELECT id, text, is_black FROM Cards_Table WHERE deck_id = ?");
		ps.setInt(1, deckID);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			Card c = new Card(rs.getInt("id"), rs.getString("text"), rs.getBoolean("is_black"));
			c.setDeckID(deckID);
			result.add(c);
		}
		disconnect();
		return result;
	}
	
	public static Player getFullPlayerInfo(int playerID) throws SQLException{
		//TODO: gets all player info, with cards, decks, etc.
		Vector<Deck> playerDecks = null;
		Player player = null;
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

		// Now that we have the cards and created our Player, assign them to the Player.
		// Should a null check be here?
		player.loadDecks(playerDecks);
		player.setID(playerID);
		return player;
	}
	
	public static void editCardDescription(int cardId, String txt) {
		String statement = "UPDATE Cards_Table set text = ? where id = ?";
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(statement);
			ps.setString(1, txt);
			ps.setInt(2, cardId);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static int addDeck(Deck d) throws SQLException{
		connect();
		PreparedStatement ps = conn.prepareStatement("INSERT INTO Decks_Table (deck_name) VALUES (?);", Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, d.getName());
		int affectedRows = ps.executeUpdate();
		disconnect();
		int deckID;
		if(affectedRows==0){
			throw new SQLException("Create deck failed, no rows affected.");
		}
		try(ResultSet generatedKeys = ps.getGeneratedKeys()){
			if(generatedKeys.next()){
				deckID = generatedKeys.getInt(1);
			}
			else{
				throw new SQLException("Create card failed, no ID obtained.");
			}
		}
		
		connect();
		PreparedStatement ps2 = conn.prepareStatement("INSERT INTO Players_Decks_Table (deck_id, player_id) VALUES (?, ?);");
		ps2.setInt(1, deckID);
		ps2.setInt(2,  d.getOwnerID());
		disconnect();
		return deckID;
	}
	public static void editDeck(Deck d){
		
	}
}
