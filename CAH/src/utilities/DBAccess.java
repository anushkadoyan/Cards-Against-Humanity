package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
}
