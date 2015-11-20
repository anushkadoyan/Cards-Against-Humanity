package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import client.PlayerManager;
import utilities.Card;
import utilities.Deck;
import utilities.Game;
import utilities.Player;

public class ServerClientCommunicator extends Thread {
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private ServerListener serverListener;
	private GameController gc;
	private Boolean valid;
	private Player player;
	
	public ServerClientCommunicator(Socket socket, ServerListener serverListener) throws IOException {
		this.socket = socket;
		this.serverListener = serverListener;
		this.oos = new ObjectOutputStream(socket.getOutputStream());
		this.ois = new ObjectInputStream(socket.getInputStream());
		gc = null;
		
	}
	public Boolean isValid(){
		return valid;
	}
	
	public void run() {
		try {
			//validating log-in
			Object obj = null;
			while(obj!=null){
				try {
					obj = ois.readObject();
				} catch (ClassNotFoundException e) {
					System.out.println("Error reading object in ServerClientCommunicator: " + e.getMessage());
				}
				if(obj instanceof Player){
					Player p = (Player)obj;
					player = ServerManager.VerifyCredentials(p);
					oos.writeObject(player);
					oos.flush();
					valid=player.getID()>0||player.isGuest();
					if(valid){
						serverListener.addValidSCC(this);
						break;
					}
				}
				else{
					System.out.println("Expected to receive log-in credentials");
				}
			}
			
			
			obj = ois.readObject();
			while(obj!=null){
				if(gc==null){
					if(obj instanceof Game){
						Game g = (Game)obj;
						if(g.getID()==-1){
							//create Game
						}
						else{
							//join Game
						}
					}
					else if(obj instanceof Deck){
						Deck d = (Deck)obj;
						if(d.getID()==-1){
							//create Deck
						}
						else{
							//modify deck
						}
					}
					else if(obj instanceof Card){
						Card c = (Card)obj;
						if(c.getID()==-1){
							//create Card
						}
						else{
							//modify Card
						}
					}
					else{
						//error
					}
				}
				else{
					if(obj instanceof Card){
						//select Card
					}
					else if(obj instanceof String){
						//chat stuff
					}
					else{
						//error
					}
					//game stuff
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
}
