package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import customUI.ImageLibrary;
import customUI.PaintedButton;
import customUI.PaintedPanel;

public class GamePanel extends PaintedPanel{
	private static final long serialVersionUID = 1L;

	private Image toDraw;

	public GamePanel(Image i) {
		super(i);
		
		setLayout(new BorderLayout());
//		PaintedPanel back = new PaintedPanel(image);
//		add(back);
		this.setOpaque(false);
		Image white = ImageLibrary.getImage("images/whitecard.png"); 
		Image black = ImageLibrary.getImage("images/blackcard.png"); 
		Image whiteback = ImageLibrary.getImage("images/whitecard.png"); 
		Image blackback = ImageLibrary.getImage("images/blackback1.png"); 

		Image casino = ImageLibrary.getImage("images/casino.jpg"); 
		toDraw = white;
		PaintedPanel wCard = new PaintedPanel(white);
		wCard.setOpaque(false);
		JPanel top = new JPanel();
		top.setOpaque(false);
		
		JPanel bottom = new JPanel();
		JPanel bottom1 = new JPanel();
		JPanel bottom2= new JPanel();

		bottom.setLayout(new BorderLayout());
		bottom.add(bottom1,BorderLayout.NORTH);

		bottom.add(bottom2,BorderLayout.CENTER);
		bottom2.setLayout(new GridLayout(1,5, 10, 1));
		bottom2.setOpaque(false);
		bottom.setOpaque(false);
		bottom1.setOpaque(false);
		bottom1.setPreferredSize(new Dimension(600,130));
		bottom1.setLayout(new BorderLayout());
		JPanel right = new JPanel();
		JPanel left = new JPanel();
		left.setOpaque(false);
		bottom1.add(left,BorderLayout.CENTER);
		bottom1.add(right, BorderLayout.EAST);
		right.setPreferredSize(new Dimension(200, 100));
		right.setOpaque(false);
		Icon cigar = new ImageIcon("images/cigar.gif");
		JLabel cigarL = new JLabel(cigar);
		Icon whiskey = new ImageIcon("images/whiskey.png");
		JLabel whiskeyL = new JLabel(whiskey);
		right.setLayout(new FlowLayout());

		right.add(whiskeyL);
		right.add(cigarL);
		right.setBorder(BorderFactory.createEmptyBorder(0,0,20,0)); // Especially important

		PaintedButton[] cards = new PaintedButton[5];
		int counter = 1;
		for(PaintedButton card: cards) {
			card = new PaintedButton("Bad word " + Integer.toString(counter),white);
			card.setOpaque(false);
//			card.setBorder(BorderFactory.createEmptyBorder(0,0,0,0)); // Especially important
			  card.setHorizontalAlignment(SwingConstants.LEFT);
			  card.setVerticalAlignment(SwingConstants.TOP);
			  card.setFont(new Font("Helvetica", Font.BOLD, 16));
			  card.setMargin(new Insets(20,20, 20, 20));
//			  card.setMargin(new Insets(1,1,1,1));
			  card.setBackground(null);
			  card.setOpaque(false);
			  card.setBorderPainted(false);
			card.setContentAreaFilled(false);
			bottom2.add(card);
			counter++;
			card.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
				}
				
			});
			
		}
		//top left bottom right
		top.setBorder(BorderFactory.createEmptyBorder(40,0,50,0)); // Especially important
//		top.setBorder(BorderFactory.creatEmpty // Especially important
//		bottom1.setBackground(Color.red);
		top.setLayout(new GridLayout(1,5,10,40));
		PaintedButton[] topCards = new PaintedButton[5];
//		PaintedButton blackC = new PaintedButton("<html><font color=\"white\">Bad word?</font></html>",black);
		PaintedButton blackC = new PaintedButton("",blackback);

		blackC.setHorizontalAlignment(SwingConstants.LEFT);
		blackC.setVerticalAlignment(SwingConstants.TOP);
		blackC.setFont(new Font("Helvetica", Font.BOLD, 16));
		blackC.setMargin(new Insets(20,20, 20, 20));
//		  card.setMargin(new Insets(1,1,1,1));
		blackC.setBackground(null);
		blackC.setOpaque(false);
		blackC.setBorderPainted(false);
		blackC.setContentAreaFilled(false);
		top.add(blackC);
		int index = 1;
		for(PaintedButton card: topCards) {
			if(index!=5) {
				card = new PaintedButton("<html><font color=\"white\">Bad word?</font></html>",white);
				card.setHorizontalAlignment(SwingConstants.LEFT);
				card.setVerticalAlignment(SwingConstants.TOP);
				card.setFont(new Font("Helvetica", Font.BOLD, 16));
				card.setMargin(new Insets(20,20, 20, 20));
//				  card.setMargin(new Insets(1,1,1,1));
				card.setBackground(null);
				card.setOpaque(false);
				card.setBorderPainted(false);
				card.setContentAreaFilled(false);
				card.setVisible(false);

				top.add(card);
				
				
			}
			index++;
		}
		
//		top.add(wCard);
//		top.add(new JButton("1"));
//
//		top.add(new JButton("1"));
		this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10)); // Especially important
		
		bottom.setPreferredSize(new Dimension(600, 390));

		add(top, BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);
	}
	
	public static void main(String[] args) {
		createGamePanel();
	}
	
	private static void createGamePanel() {
		System.out.println("Running the game panel");

		// create the image
		Image i = ImageLibrary.getImage("images/casino.jpg");
		
		// create the game panel
		GamePanel gp = new GamePanel(i);

		// create the frame
		JFrame jf = new JFrame("THE FRAME");
		jf.add(gp); // add the panel
		jf.setBounds(0, 0, 1200, 800); // 3:2 display ratio for now
		jf.repaint();
		jf.setVisible(true);
	}

}
