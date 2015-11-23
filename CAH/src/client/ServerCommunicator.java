package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import utilities.Card;
import utilities.Deck;
import utilities.Game;
import utilities.Player;

public class ServerCommunicator extends Thread{
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Player p;
	private Boolean valid;
	private Game game;
	
	public ServerCommunicator(Socket socket, Player p) throws IOException {
		this.socket = socket;
		this.oos = new ObjectOutputStream(socket.getOutputStream());
		this.ois = new ObjectInputStream(socket.getInputStream());
		oos.writeObject(p);
		try {
			p = (Player)ois.readObject();
			valid = p.getID()>0||p.isGuest();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Boolean isValid(){
		return valid;
	}
	public void run(){
		try{
			Object obj = ois.readObject();
			while(obj!=null){
				if(obj instanceof Game){
					game = (Game)obj;
				}
			}
		}
		catch(IOException ioe){
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
