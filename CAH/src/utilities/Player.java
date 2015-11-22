package utilities;

import java.io.Serializable;
import java.util.Vector;

public class Player implements Serializable {
	private static final long serialVersionUID = 1L;
	private int ID;
	private String name;
	private String password;
	private Boolean guest;
	private Boolean isJudge;
	private Vector<Deck> decks;
	
	public Player(String username, String pwd){
		name = username;
		password = pwd;
		ID=0;
		isJudge = false;
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
	
	public void addDeck(Deck deck) {
		decks.addElement(deck);
	}
	
	public void loadDecks(Vector<Deck> loadedDecks){
		decks = loadedDecks;
	}
	
	public Vector<Deck> getPlayerDecks() {
		return decks;
	}
}
