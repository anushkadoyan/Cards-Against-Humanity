package client;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JPanel;

import customUI.AllImages;

public class ClientPanel extends JPanel{
	
	private static final long serialVersionUID = -8118487733501250161L;
	
	private LoginScreen loginScreen;
	private RegisterScreen registerScreen;
	private LobbyScreen lobbyScreen;
	
	{	
		loginScreen = new LoginScreen(AllImages.getImage("images/wallpaper.png"), 
			new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae){
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
		lobbyScreen = new LobbyScreen(AllImages.getImage("images/wallpaper.png"));
		registerScreen = new RegisterScreen(AllImages.getImage("images/wallpaper.png"), new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae){
				ClientPanel.this.removeAll();
				ClientPanel.this.add(loginScreen);
				ClientPanel.this.revalidate();
				ClientPanel.this.repaint();
			}
		});
	}

}
