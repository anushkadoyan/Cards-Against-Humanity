package utilities;

import java.util.Collections;
import java.util.Vector;

public class Deck {
	
	private String name;
	private Vector<Card> cards;
	private int ID;
	private int playerID;
	private Vector<Card> usedCards;
	
	public Deck(int id, String name){
		this.ID = id;
		this.name = name;
		cards = new Vector<Card>();
		usedCards = new Vector<Card>();
	}
	
	public Deck(String name){
		this.ID = -1;
		this.name = name;
		cards = new Vector<Card>();
	}
	
	public int getID(){
		return ID;
	}
	
	public String getName(){
		return name;
	}
	
	public void setID(int id) {
		this.ID = id;
	}
	
	public int getOwnerID(){
		return playerID;
	}
	
	public void setOwnerID(int id) {
		playerID = id;
	}
	
	public Vector<Card> getWhiteCards() {
		Vector<Card> whiteCards = new Vector<Card>();
		for( int i = 0; i < cards.size(); i++ ) {
			if(! cards.get(i).getBlackness() ) {
				whiteCards.add(cards.get(i));
			}
		}
		
		return whiteCards;
	}
	
	public Vector<Card> getBlackCards() {
		Vector<Card> blackCards = new Vector<Card>();
		for( int i = 0; i < cards.size(); i++ ) {
			if( cards.get(i).getBlackness() ) {
				blackCards.add(cards.get(i));
			}
		}
		
		return blackCards;
	}
	
	public void reshuffle() {
		for(Card c: usedCards){
			cards.add(c);
		}
		usedCards.clear();
		Collections.shuffle(cards);
	}
	
	public void setCards(Vector<Card> cards){
		this.cards = cards;
	}

	public Vector<Card> getCards(){
		return cards;
	}
	
	public Card useCard(){
		if(cards.size()==0){
			reshuffle();
		}
		Card temp = cards.remove(0);
		usedCards.add(temp);
		return temp;
	}
	//do NOT call ServerManager methods directly here. Load the deck from the ServerManager and set the cards from there.
}
