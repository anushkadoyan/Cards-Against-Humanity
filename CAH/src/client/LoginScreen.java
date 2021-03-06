package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import customUI.ImagePanel;

public class LoginScreen extends ImagePanel{
	private static final long serialVersionUID = -7404180270690905673L;
	
	private JButton loginButton, registerButton, guestButton;
	private JLabel userLabel, passLabel;
	private JTextField userText;
	private JPasswordField passText;
	private JPanel userPanel, passPanel, buttonPanel;
	
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	public LoginScreen(Image inImage, ActionListener loginAction, ActionListener registerAction){
		super(inImage);
		System.out.println("ON LOGIN SCREEN");
		
		this.setOpaque(false);
		
		loginButton = new JButton("Login");
			loginButton.setFont(new Font("Andalus", Font.PLAIN, 12));
			loginButton.setPreferredSize(new Dimension(90,40));
			loginButton.addActionListener(loginAction);
			loginButton.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
			loginButton.setBackground(Color.WHITE);
			loginButton.setOpaque(true);
			/*
			loginButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent ae){
					Socket s = null;
					try{
						s = new Socket(name, port);
					}
					catch(Exception ex){
						//unsuccessful connect
					}
					Player p = null;
					ServerCommunicator sc = new ServerCommunicator(s, p);
					Boolean loginWasValid = sc.isValid();
					if(loginWasValid){
						PlayerManager.setServerCommunicator(sc);
						//do stuff
					}
				}
			});
			*/
		registerButton = new JButton("Register");
		registerButton.setFont(new Font("Andalus", Font.PLAIN, 12));
		registerButton.setPreferredSize(new Dimension(90,40));
		registerButton.addActionListener(registerAction);
		registerButton.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
		registerButton.setBackground(Color.WHITE);
		registerButton.setOpaque(true);
			
		guestButton = new JButton("Guest");
		guestButton.setFont(new Font("Andalus", Font.PLAIN, 12));
		guestButton.setPreferredSize(new Dimension(90,40));
		guestButton.addActionListener(loginAction);
		guestButton.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
		guestButton.setBackground(Color.WHITE);
		guestButton.setOpaque(true);
		
		userLabel = new JLabel("Username:");
		userLabel.setFont(new Font("Andalus", Font.BOLD, 20));
		userLabel.setForeground(Color.WHITE);
		passLabel = new JLabel("Password:");
		passLabel.setFont(new Font("Andalus", Font.BOLD, 20));
		passLabel.setForeground(Color.WHITE);
			
		userText = new JTextField(8);
		userText.setFont(new Font("Andalus", Font.PLAIN, 20));
		passText = new JPasswordField(8);
		passText.setFont(new Font("Andalus", Font.PLAIN, 20));
		passText.setEchoChar('*');
			
		userPanel = new JPanel();
		userPanel.setLayout(new FlowLayout());
		
		passPanel = new JPanel();
		passPanel.setLayout(new FlowLayout());
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		
		userPanel.add(userLabel);
		userPanel.add(userText);
		
		passPanel.add(passLabel);
		passPanel.add(passText);
			
		buttonPanel.add(loginButton);
		buttonPanel.add(guestButton);
		buttonPanel.add(registerButton);
		
		userPanel.setOpaque(false);
		passPanel.setOpaque(false);
		buttonPanel.setOpaque(false);
		
		userPanel.setPreferredSize(new Dimension(300,60));
		passPanel.setPreferredSize(new Dimension(300,60));
		buttonPanel.setPreferredSize(new Dimension(300,60));
		
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.gridy = 1;
		this.add(userPanel, gbc);
		gbc.gridy = 2;
		this.add(passPanel, gbc);
		gbc.gridy = 4;
		this.add(buttonPanel, gbc);
		
	}
	
	public String getUsername() {
		return userText.getText();
	}
	
	public String getPassword() {
		return new String(passText.getPassword()); 
	}

}
