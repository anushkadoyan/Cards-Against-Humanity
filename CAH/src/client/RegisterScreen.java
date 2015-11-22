package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import customUI.ImagePanel;

public class RegisterScreen extends ImagePanel{

	private static final long serialVersionUID = 2012111864876508736L;
	
	private JButton registerButton, cancelButton;
	private JLabel userLabel, passLabel, nameLabel;
	private JTextField userText, nameText;
	private JPasswordField passText;
	private JPanel userPanel, passPanel, buttonPanel, namePanel;
	private String name, username, password;
	
	public RegisterScreen(Image inImage, ActionListener returnAction, ActionListener cancelAction){
		super(inImage);
		this.setOpaque(false);
		
		registerButton = new JButton("Register");
		registerButton.setFont(new Font("Andalus", Font.PLAIN, 12));
		registerButton.setPreferredSize(new Dimension(90,40));
		registerButton.addActionListener(returnAction);
		registerButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae){
				name = nameText.getText();
				username = userText.getText();
				password = new String(passText.getPassword());
			}
		});
		registerButton.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
		registerButton.setBackground(Color.WHITE);
		registerButton.setOpaque(true);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setFont(new Font("Andalus", Font.PLAIN, 12));
		cancelButton.setPreferredSize(new Dimension(90,40));
		cancelButton.addActionListener(cancelAction);
		cancelButton.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
		cancelButton.setBackground(Color.WHITE);
		cancelButton.setOpaque(true);
		
		nameLabel = new JLabel("Name:");
		nameLabel.setFont(new Font("Andalus", Font.BOLD, 20));
		nameLabel.setForeground(Color.WHITE);
		
		userLabel = new JLabel("Username:");
		userLabel.setFont(new Font("Andalus", Font.BOLD, 20));
		userLabel.setForeground(Color.WHITE);
		
		passLabel = new JLabel("Password:");
		passLabel.setFont(new Font("Andalus", Font.BOLD, 20));
		passLabel.setForeground(Color.WHITE);
		
		nameText = new JTextField(8);
		nameText.setFont(new Font("Andalus", Font.PLAIN, 20));
		
		userText = new JTextField(8);
		userText.setFont(new Font("Andalus", Font.PLAIN, 20));
		
		passText = new JPasswordField(8);
		passText.setFont(new Font("Andalus", Font.PLAIN, 20));
		passText.setEchoChar('*');
		
		namePanel = new JPanel();
		namePanel.setLayout(new FlowLayout());
		
		userPanel = new JPanel();
		userPanel.setLayout(new FlowLayout());
		
		passPanel = new JPanel();
		passPanel.setLayout(new FlowLayout());
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		
		namePanel.add(nameLabel);
		namePanel.add(nameText);
		
		userPanel.add(userLabel);
		userPanel.add(userText);
		
		passPanel.add(passLabel);
		passPanel.add(passText);
			
		buttonPanel.add(cancelButton);
		buttonPanel.add(registerButton);
		
		namePanel.setOpaque(false);
		userPanel.setOpaque(false);
		passPanel.setOpaque(false);
		buttonPanel.setOpaque(false);
		
		namePanel.setPreferredSize(new Dimension(250,60));
		userPanel.setPreferredSize(new Dimension(300,60));
		passPanel.setPreferredSize(new Dimension(300,60));
		buttonPanel.setPreferredSize(new Dimension(300,60));
		
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.gridy = 1;
		this.add(namePanel, gbc);
		gbc.gridy = 2;
		this.add(userPanel, gbc);
		gbc.gridy = 3;
		this.add(passPanel, gbc);
		gbc.gridy = 5;
		this.add(buttonPanel, gbc);
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getPassword(){
		return password;
	}
	
	public String getName(){
		return name;
	}
}
