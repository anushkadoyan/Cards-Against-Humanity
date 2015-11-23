package utilities;

public class Card {
	private String description;
	private Boolean isBlack;
	private int ID;
	private int deckID;
	private Player user;
	
	public Card(int id, String desc, Boolean black){
		ID = id;
		description = desc;
		isBlack = black;
		user = null;
	}
	public Card(String desc, Boolean black){
		ID = -1;
		description = desc;
		isBlack = black;
		user = null;
	}
	
	public String getDesc(){
		return description;
	}
	
	public Boolean getBlackness(){
		return isBlack;
	}
	
	public Integer getID(){
		return ID;
	}
	
	public void setID(int i) {
		this.ID = i;
	}
	
	public void setDeckID(int i){
		this.deckID = i;
	}
	
	public int getDeckID() {
		return deckID;
	}
	
	public void setDesc(String newDesc){
		description = newDesc;
	}
	
	public void setUser(Player p){
		user = p;
	}
	public Player getUser(){
		return user;
	}
}
