package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JPanel;

import GUI.GamePanel;
import customUI.AllImages;

public class ClientPanel extends JPanel{
	
	private static final long serialVersionUID = -8118487733501250161L;
	
	private LoginScreen loginScreen;
	private RegisterScreen registerScreen;
	private LobbyScreen lobbyScreen;
	private GamePanel gamePanel;

	// stream
	private ObjectOutputStream oos;
	private ObjectInputStream ios;
	
	public ClientPanel(ObjectOutputStream outputStream, ObjectInputStream inputStream) {
		oos = outputStream;
		ios = inputStream;
	}
	{	
		loginScreen = new LoginScreen(AllImages.getImage("images/wallpaper.png"), 
			new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae){
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					if (player != null) {
						System.out.println("Going to lobby screen cuz the player credentials were correct");
						ClientPanel.this.removeAll();
						ClientPanel.this.add(lobbyScreen);
						ClientPanel.this.revalidate();
						ClientPanel.this.repaint();
					} else {
						System.out.println("Player credentials incorrect. Go back to login screen.");
					}
				}
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
		lobbyScreen = new LobbyScreen(AllImages.getImage("images/wallpaper.png"), new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae){
				ClientPanel.this.removeAll();
				//Update to add gamePanel
				ClientPanel.this.add(gamePanel);
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
		gamePanel = new GamePanel(AllImages.getImage("images/wallpaper.png"));
	}

}
