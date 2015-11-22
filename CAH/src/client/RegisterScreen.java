package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import customUI.ImagePanel;

public class RegisterScreen extends ImagePanel{

	private static final long serialVersionUID = 2012111864876508736L;
	
	private JButton registerButton, cancelButton;
	private JLabel userLabel, passLabel;
	private JTextField userText;
	private JPasswordField passText;
	private JPanel userPanel, passPanel, buttonPanel;
	private String username, password;
	
	public RegisterScreen(Image inImage, ActionListener returnAction, ActionListener cancelAction){
		super(inImage);
		this.setOpaque(false);
		
		registerButton = new JButton("Register");
		registerButton.setFont(new Font("Andalus", Font.PLAIN, 12));
		registerButton.setPreferredSize(new Dimension(90,40));
		registerButton.addActionListener(returnAction);
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
			
		buttonPanel.add(cancelButton);
		buttonPanel.add(registerButton);
		
		userPanel.setOpaque(false);
		passPanel.setOpaque(false);
		buttonPanel.setOpaque(false);
		
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		this.add(Box.createGlue());
		this.add(userPanel);
		this.add(passPanel);
		this.add(Box.createGlue());
		this.add(buttonPanel);
	}
	
	private boolean validInfo(String user, String pass){
		if (user.equals(null) || pass.equals(null)){
			return false;
		}
		if (pass.length() < 4){
			return false;
		}
		return true;
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getPassword(){
		return password;
	}
}
