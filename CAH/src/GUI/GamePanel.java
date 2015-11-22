package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import com.sun.glass.events.MouseEvent;

import javax.swing.*;

import customUI.ImageLibrary;
import customUI.PaintedButton;
import customUI.PaintedPanel;
import utilities.Card;

public class GamePanel extends PaintedPanel{
	
	//Images and icons

	private Image toDraw;
	private static final long serialVersionUID = 1L;
	private JPanel top, bottom, bottom1, bottom2, left, right;
	private Image white = ImageLibrary.getImage("images/whitecard.png"); 
	private Image black = ImageLibrary.getImage("images/blackcard.png"); 
	private Image whiteback = ImageLibrary.getImage("images/whitecard.png"); 
	private Image blackback = ImageLibrary.getImage("images/blackback1.png"); 
	private Image casino = ImageLibrary.getImage("images/casino.jpg"); 
	private Icon cigar = new ImageIcon("images/cigar.gif");
	private JLabel cigarL = new JLabel(cigar);
	private Icon whiskey = new ImageIcon("images/whiskey.png");
	private JLabel whiskeyL = new JLabel(whiskey);
	private boolean placedCard = false;
	private int cardPosition = 0;
	private PaintedButton lastCard = new PaintedButton("",white);
	// menu stuff
	JMenuBar menuBar;
	JMenu menu;

	//Constructor
	public GamePanel(Image i) {
		super(i);
		
		setLayout(new BorderLayout());
		this.setOpaque(false);
		createGUI();
	}
	
	
	public void createGUI () {
		toDraw = white;
		
		PaintedPanel wCard = new PaintedPanel(white);
		wCard.setOpaque(false);
		top = new JPanel();
		top.setOpaque(false);
		
		bottom = new JPanel();
		bottom1 = new JPanel();
		bottom2= new JPanel();

		bottom.setLayout(new BorderLayout());
		bottom.add(bottom1,BorderLayout.NORTH);
		bottom.setOpaque(false);
		bottom.add(bottom2,BorderLayout.CENTER);
		
		bottom2.setLayout(new GridLayout(1,5, 10, 1));
		bottom2.setOpaque(false);
		
		bottom1.setOpaque(false);
		bottom1.setPreferredSize(new Dimension(600,130));
		bottom1.setLayout(new BorderLayout());
		left = new JPanel();
		right = new JPanel();

		left.setOpaque(false);
		bottom1.add(left,BorderLayout.CENTER);
		bottom1.add(right, BorderLayout.EAST);

		right.setPreferredSize(new Dimension(200, 100));
		right.setOpaque(false);
		right.setLayout(new FlowLayout());
		right.add(whiskeyL);
		right.add(cigarL);
		right.setBorder(BorderFactory.createEmptyBorder(0,0,20,0)); 


		//top left bottom right
		top.setBorder(BorderFactory.createEmptyBorder(40,0,30,0));
//		top.setBorder(BorderFactory.creatEmpty // Especially important
//		bottom1.setBackground(Color.red);
		top.setLayout(new GridLayout(1,5,10,40));
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
		Image i = ImageLibrary.getImage("images/wallpaper.png");
		
		// create the game panel
		GamePanel gp = new GamePanel(i);
		//	public Card(int id, String desc, Boolean black){

		Card[] cards = new Card[5];
		for(int a=0; a<cards.length; a++) {
			cards[a] = new Card(a, "test card " +a,false);
		}

		gp.displayCards(cards);
		
		gp.setTopCards();
		// create the frame
		JFrame jf = new JFrame("THE FRAME");
		jf.add(gp); // add the panel
		jf.setBounds(0, 0, 1024, 768); 
		jf.repaint();
		jf.setVisible(true);
		jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
		gp.addMenu(jf);
		
	}

	public void displayCards(Card[] pCards) {
		PaintedButton[] cards = new PaintedButton[5];
		int counter = 1;
		for(int i = 0; i<=4;i++) {
			if(pCards!=null) {

				cards[i] = new PaintedButton(pCards[i].getDesc(),white);
				cards[i].setOpaque(false);
//				card.setBorder(BorderFactory.createEmptyBorder(0,0,0,0)); // Especially important
				cards[i].setHorizontalAlignment(SwingConstants.LEFT);
				cards[i].setVerticalAlignment(SwingConstants.TOP);
				cards[i].setFont(new Font("Helvetica", Font.BOLD, 16));
				cards[i].setMargin(new Insets(20,20, 20, 20));
//				  cards[i].setMargin(new Insets(1,1,1,1));
				cards[i].setBackground(null);
				cards[i].setOpaque(false);
				cards[i].setBorderPainted(false);
				cards[i].setContentAreaFilled(false);
			    Border border = new LineBorder(Color.BLACK, 5);

				bottom2.add(cards[i]);
				counter++;
//				cards[i].addMouseListener(new MouseAdapter()
//		        {
//		            public void mouseEntered(MouseEvent e)
//		            {
//		            	e.getClass().setBorder(border);
//
//		            }
//		        });
				cards[i].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						PaintedButton button = (PaintedButton)e.getSource();

				        String buttonText = button.getText();
						addCard(buttonText);
						// TODO Auto-generated method stub
						
						//When other card placed, the one before reappears in its original position
						for(int i=0; i<bottom2.getComponents().length; i++) {
							PaintedButton card = (PaintedButton) bottom2.getComponent(i);

							if(!card.isVisible()) {
//								System.out.println(top.getComponent(i).getClass());
								
								card.setVisible(true);

							}
						}
						button.setVisible(false);

					}
					
				});
			}
			
			
		}
	}
	public void setTopCards() {
		PaintedButton[] topCards = new PaintedButton[5];
		int index = 1;

		for(PaintedButton card: topCards) {
			if(index!=5) {
				card = new PaintedButton("Test card",white);
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
				if(index==1) {
					card.setVisible(true);

				}
				top.add(card);
			}
			index++;
		}
		
	}
	
	public void removeCards() {
		for(int i=1; i<top.getComponents().length; i++) {
			PaintedButton card = (PaintedButton) top.getComponent(i);
			card.setVisible(false);
		}
	}
	
	public void addCard(String text) {
		String lastCard = "";
		//if haven't placed card yet, find available card area
		if(!placedCard) {
			for(int i=1; i<top.getComponents().length; i++) {
				PaintedButton card = (PaintedButton) top.getComponent(i);

				if(!card.isVisible()) {
//					System.out.println(top.getComponent(i).getClass());
					
					card.setText(text);
					card.setVisible(true);
					placedCard=true;
					cardPosition = i;
					break;
				}
			}
		}
		else {
			PaintedButton card = (PaintedButton) top.getComponent(cardPosition);
			card.setText(text);
			card.setVisible(true);
			placedCard=true;

		}
		
		 
	}

	// creates menu bar and attaches it to the given JFrame
	private void addMenu(final JFrame jf) {
		// initialize
		menuBar = new JMenuBar();
		menu = new JMenu("Settings");

		// setting options
		JMenuItem menuItem1 = new JMenuItem("Go to Lobby");
		JMenuItem menuItem2 = new JMenuItem("Close Client");

		// add the "Go To Lobby" listener
		menuItem1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: GO TO LOBBY (SO DISCONNECT FROM THE GAME
				// AND SHOW THE LOBBY GUI);
				System.out.println("Clicked on 'Go To Lobby'");
			}
		});
		
		// add the "Close Client" listener
		menuItem2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: DISCONNECT FROM GAME AND CLOSE THE FRAME
				System.out.println("Clicked on 'Close Client'");
				
				// close the frame
				jf.dispose();
			}
		});
		
		// add menuItems to menu. Add menu to menuBar. Add menuBar to JFrame
		menuBar.add(menu);
		menu.add(menuItem1);
		menu.add(menuItem2);
		jf.setJMenuBar(menuBar);
	}
	
}
