package utilities;

import java.util.Vector;

public class Player {
	private int ID;
	private String name;
	private String password;
	private Boolean guest;
	private Vector<Deck> decks;
	public Player(String username, String pwd){
		name = username;
		password = pwd;
		ID=0;
		guest = name.startsWith("Guest");
	}
	
	public String getName(){
		return name;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setID(int id){
		ID=id;
	}
	
	public int getID(){
		return ID;
	}
	
	public void setAsGuest(Boolean guestStatus){
		guest = guestStatus;
	}
	
	public Boolean isGuest(){
		return guest;
	}
	
	public void loadDecks(Vector<Deck> loadedDecks){
		decks = loadedDecks;
	}
	
	public Vector<Deck> getPlayerDecks() {
		return decks;
	}
}
