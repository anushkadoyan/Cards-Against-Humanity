package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import GUI.GamePanel;
import customUI.AllImages;
import customUI.PaintedButton;
import server.GameController;
import utilities.Deck;
import utilities.Game;
import utilities.Player;

public class ClientPanel extends JPanel{
	
	private static final long serialVersionUID = -8118487733501250161L;
	
	private LoginScreen loginScreen;
	private RegisterScreen registerScreen;
	private LobbyScreen lobbyScreen;
	private GamePanel gamePanel;
	private boolean isGuest;

	// stream
	private ObjectOutputStream oos;
	private ObjectInputStream ios;
	
	public ClientPanel(ObjectOutputStream outputStream, ObjectInputStream inputStream) {
		this.oos = outputStream;
		this.ios = inputStream;
	
		loginScreen = new LoginScreen(AllImages.getImage("images/wallpaper.png"), 
			new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae){
				/*
				JButton button = (JButton)ae.getSource();
				if(button.getText().equals("Guest")) {
					System.out.println("guest");
					isGuest= true;
					System.out.println(isGuest);
					lobbyScreen.setGuest(isGuest);
				}
				System.out.println("Lobby screen");

				String userName = loginScreen.getUsername();
				String password = loginScreen.getPassword();
				System.out.println("username, pw: " + userName + ", " + password);

				try {
					oos.writeObject(userName + "," + password);
				} catch (IOException e) {
					e.printStackTrace();
				}

				Object player = null;
				try {
					System.out.println("Trying to receive player object back from server to fulfill the login credentials");
					player = ios.readObject();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (player != null) {
					*/
						System.out.println("Going to lobby screen cuz the player credentials were correct");
						ClientPanel.this.removeAll();
						ClientPanel.this.add(lobbyScreen);
						ClientPanel.this.revalidate();
						ClientPanel.this.repaint();
					/*	
					} else {
						System.out.println("Player credentials incorrect. Go back to login screen.");
					}
				}
			*/
			}
			
		}, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae){
				ClientPanel.this.removeAll();
				ClientPanel.this.add(registerScreen);
				ClientPanel.this.revalidate();
				ClientPanel.this.repaint();
			}
			
		});
		refreshComponents();

		setLayout(new BorderLayout());
		add(loginScreen);
	}
	
	private void refreshComponents(){

		lobbyScreen = new LobbyScreen(AllImages.getImage("images/wallpaper.png"), isGuest, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae){
				ClientPanel.this.removeAll();
				//Update to add gamePanel
				ClientPanel.this.add(gamePanel);
				Player player = new Player("user","pass");
				Deck deck = new Deck(1,"dumb");
				//Game game = new Game(player);
				new GameController(null);
				ClientPanel.this.revalidate();
				ClientPanel.this.repaint();
			}
		}, oos, ios);
		registerScreen = new RegisterScreen(AllImages.getImage("images/wallpaper.png"), new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae){
				String name = registerScreen.getName();
				String username = registerScreen.getUsername();
				String password = registerScreen.getPassword();
				//Check for valid parameters
				if (name.equals(null)){
					JOptionPane.showMessageDialog(ClientPanel.this, "Please enter valid name", "Notice", JOptionPane.INFORMATION_MESSAGE);
				}
				else if (username.equals(null) || password.equals(null)){
					JOptionPane.showMessageDialog(ClientPanel.this, "Please enter valid username/password", "Notice", JOptionPane.INFORMATION_MESSAGE);
				}
				else if (username.length() < 4 || password.length() < 4){
					JOptionPane.showMessageDialog(ClientPanel.this, "Username and password must contain more than 3 characters", "Notice", JOptionPane.INFORMATION_MESSAGE);
				}
				else{
					//Check if valid with server
					//Create account, add to database
					String newName = registerScreen.getName();
					String newUsername = registerScreen.getUsername();
					String newPassword = registerScreen.getPassword();
					//Send account information to be added to database
					/*
					try {
						oos.writeObject(newName + "," + newUsername + "," + newPassword);
					} catch (IOException e) {
						e.printStackTrace();
					}
					*/
					JOptionPane.showMessageDialog(ClientPanel.this, "Account Created", "Notice", JOptionPane.INFORMATION_MESSAGE);
					ClientPanel.this.removeAll();
					ClientPanel.this.add(loginScreen);
					ClientPanel.this.revalidate();
					ClientPanel.this.repaint();
				}
			}
		}, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae){
				ClientPanel.this.removeAll();
				ClientPanel.this.add(loginScreen);
				ClientPanel.this.revalidate();
				ClientPanel.this.repaint();
			}
		});
		
		//dummy player names and scores
		 HashMap<String, Integer> m = new HashMap<String, Integer>();
		  m.put("You",0);  
		  m.put("Player1",0);  
		  m.put("Guest",0);  
		  m.put("Johnny",0); 
			gamePanel = new GamePanel(m,AllImages.getImage("images/wallpaper.png"));

	}

}
