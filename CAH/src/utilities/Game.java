package utilities;

import java.util.Vector;

public class Game {
	private int ID;
	private Vector<Player> players;
	private int turn;
	private Player host;
	private Player judge;
	private Deck deck;
	private int numMovesExpected;
	private Card lastWinningCard;
	
	public Game(Player host, Deck deck){
		this.ID = -1;
		this.host = host;
		this.deck = deck;
		turn=0;
		numMovesExpected = 0;
		judge = null;
		lastWinningCard = null;
	}
	
	public int getID(){
		return ID;
	}
	
	public void setID(int ID){
		this.ID = ID;
	}
	public Vector<Player> getPlayers(){
		return players;
	}
	public void addPlayer(Player p){
		players.add(p);
	}
	
	public void judgeSelection(Card c){
		lastWinningCard = c;
		turn++;
		Boolean foundJudge = false;
		for(Player p: players){
			if(p.getID()==judge.getID()){
				foundJudge = true;
			}
			else if(foundJudge){
				setJudge(p);
				foundJudge = false;
			}
			Vector<Card> temp = p.getAvailableCards();
			temp.add(deck.useCard());
			p.setAvailableCards(temp);
		}
		if(foundJudge){
			setJudge(players.get(0));
		}
		
	}
	public int getNumMovesExpected(){
		return numMovesExpected;
	}
	public Player getJudge(){
		return judge;
	}
	public void setJudge(Player p){
		judge = p;
	}
	public void startGame(){
		turn = 1;
		numMovesExpected = players.size() - 1;
		distributeCards();
	}
	private void distributeCards(){
		for(Player p: players){
			Vector<Card> result = new Vector<Card>();
			for(int i=0; i<10; i++){
				result.add(deck.useCard());
			}
			p.setAvailableCards(result);
		}
	}
	public Card getLastWinningCard(){
		return lastWinningCard;
	}
}
