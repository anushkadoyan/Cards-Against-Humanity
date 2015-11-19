package utilities;

public class Card {
	private String description;
	private Boolean isBlack;
	private int ID;
	
	public Card(String desc, Boolean black){
		ID = -1;
		description = desc;
		isBlack = black;
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
}
