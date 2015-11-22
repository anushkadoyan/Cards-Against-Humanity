package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
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
	private boolean isGuest = false;
	
	{	
		loginScreen = new LoginScreen(AllImages.getImage("images/wallpaper.png"), 
			new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae){
				JButton button = (JButton)ae.getSource();
				if(button.getText().equals("Guest")) {
					System.out.println("guest");
					isGuest= true;
					System.out.println(isGuest);
					lobbyScreen.setGuest(isGuest);
				}
				ClientPanel.this.removeAll();
				
				ClientPanel.this.add(lobbyScreen);
				ClientPanel.this.revalidate();
				ClientPanel.this.repaint();

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
//				Game game = new Game(player);
				new GameController(null);
				ClientPanel.this.revalidate();
				ClientPanel.this.repaint();
			}
		});
		registerScreen = new RegisterScreen(AllImages.getImage("images/wallpaper.png"), new ActionListener(){
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
