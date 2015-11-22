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
	
	private Vector<Card> availableCards;
	
	public Player(String username, String pwd){
		name = username;
		password = pwd;
		ID=0;
		isJudge = false;
		guest = name.startsWith("Guest");
		availableCards = new Vector<Card>();
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
	
	public void setAvailableCards(Vector<Card> temp){
		availableCards = temp;
	}
	public Vector<Card> getAvailableCards(){
		return availableCards;
	}
}
