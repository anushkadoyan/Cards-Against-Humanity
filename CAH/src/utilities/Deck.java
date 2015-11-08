package utilities;

import java.util.Vector;

public class Deck {
	private String name;
	private Vector<Card> cards;
	private int ID;
	public Deck(String name){
		this.ID = -1;
		this.name = name;
	}
	public int getID(){
		return ID;
	}
	//TODO: methods to get/set id, get white/black cards, reshuffle, etc.
	//do NOT call ServerManager methods directly here. Load the deck from the ServerManager and set the cards from there.
}
