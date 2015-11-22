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
				boolean valid = false;

				if (valid) {
					ClientPanel.this.removeAll();
					ClientPanel.this.add(lobbyScreen);
					ClientPanel.this.revalidate();
					ClientPanel.this.repaint();
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
