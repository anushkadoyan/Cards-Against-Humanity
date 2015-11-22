package server;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

import utilities.Card;
import utilities.Deck;
import utilities.Game;
import utilities.Player;

public class ServerClientCommunicator  extends Thread {
	private Socket socket;
	private ObjectOutputStream oos;
	private BufferedOutputStream bos;
	private ObjectInputStream ois;
	private ServerListener serverListener;
	private GameController gc;
	private Boolean valid;
	private Player player;
	
	public ServerClientCommunicator(Socket socket, ServerListener serverListener) throws IOException {
		this.socket = socket;
		this.serverListener = serverListener;
		this.oos = new ObjectOutputStream(this.socket.getOutputStream());
		this.oos.flush();
		this.ois = new ObjectInputStream(this.socket.getInputStream());
		gc = null;
	}

	public Boolean isValid(){
		return valid;
	}
	
	public void run() {
		try {
			//validating log-in
			Object obj = null;
			System.out.println("Validating.");
			boolean validated = false;
			while(obj==null) {
				try {
					obj = ois.readObject();
				} catch (ClassNotFoundException e) {
					System.out.println("Error reading object in ServerClientCommunicator: " + e.getMessage());
				}
				if (obj instanceof String) {
					System.out.println("Received stuff.");
					String loginInfo = (String) obj;

					String username = loginInfo.substring(0, loginInfo.indexOf(','));
					String password = loginInfo.substring(loginInfo.indexOf(',')+1, loginInfo.length()-1);
					System.out.println("Server receives username and password: " + username + ", " + password);
					
					S
				}
//				if(obj instanceof Player){
//					Player p = (Player)obj;
//					player = ServerManager.VerifyCredentials(p);
//					oos.writeObject(player);
//					oos.flush();
//					valid=player.getID()>0||player.isGuest();
//					if(valid){
//						serverListener.addValidSCC(this);
//						break;
//					}
//				}
				else {
					System.out.println("Expected to receive log-in credentials");
				}
			}
			
			obj = ois.readObject();
			System.out.println("Validating 2.");
			while(obj!=null){
				if(gc==null){
					if(obj instanceof Game){
						Game g = (Game)obj;
						if(g.getID()==-1){
							g = ServerManager.getGame(g);
						}
						else{
							serverListener.joinGame(player, g.getID());
						}
					}
					else if(obj instanceof Deck){
						Deck d = (Deck)obj;
						if(d.getID()==-1){
							ServerManager.addDeck(d);
						}
						else{
							ServerManager.editDeck(d);
						}
					}
					else if(obj instanceof Card){
						Card c = (Card)obj;
						if(c.getID()==-1){
							try {
								ServerManager.AddCard(c);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						else{
							ServerManager.editCard(c);
						}
					}
					else{
						//error
					}
				}
				else{
					if(obj instanceof Card){
						Card c = (Card)obj;
						gc.selectCard(player.getID(), c);
					}
					else if(obj instanceof String){
						String s = (String)obj;
						gc.sendMessage(s);
					}
					else{
						//error
					}
				}
				obj = ois.readObject();
			}			
		} catch (IOException ioe) {
			serverListener.removeValidSCC(this);
			serverListener.removeServerClientCommunicator(this);
			// this means that the socket is closed since no more lines are being received
			try {
				socket.close();
			} catch (IOException ioe1) {
				System.out.println("ioe attempting to close socket in ServerClientCommunicator.run(): " + ioe1.getMessage());
			}
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException in ServerClientCommunicator.run(): " + e.getMessage());
		}
	}
	public void sendGameState(Game g) throws IOException{
		oos.reset();
		oos.writeObject(g);
		oos.flush();
	}
	public Player getPlayer(){
		return player;
	}
	public void sendChatMessage(String text) throws IOException{
		oos.reset();
		oos.writeObject(text);
		oos.flush();
	}
}
