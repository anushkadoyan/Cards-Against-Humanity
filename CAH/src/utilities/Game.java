package utilities;

import java.util.Vector;

public class Game {
	private int ID;
	private Vector<Player> players;
	private int turn;
	private Player host;
	private Player judge;
	private Deck deck;
	
	public Game(Player host, Deck deck){
		this.ID = -1;
		this.host = host;
		this.deck = deck;
		turn=0;
	}
	
	public int getID(){
		return ID;
	}
	
	public void setID(int ID){
		this.ID = ID;
	}
}
