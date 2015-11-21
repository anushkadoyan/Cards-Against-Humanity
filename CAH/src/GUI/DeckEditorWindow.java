package GUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import client.PlayerManager;
import utilities.Card;
import utilities.Deck;

public class DeckEditorWindow extends JFrame{
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
//	private JList deckList, cardList;
//	private JScrollPane deckScroll, cardScroll;
//	private JButton createDeckButton, createCardButton, saveCardButton;
//	private JTextArea cardViewer;
//	private JRadioButton blackCard, whiteCard;
	private Deck cDeck;
	private ButtonGroup group;
	private DefaultListModel <String>deckListModel, cardListModel;
	private Image blackCard = new ImageIcon("blackcard.png").getImage().getScaledInstance(400, 400, Image.SCALE_DEFAULT);
	private Image whiteCard = new ImageIcon("whitecard.png").getImage().getScaledInstance(400, 400, Image.SCALE_DEFAULT);
	private Vector<Deck> decks;
	private 

	public DeckEditorWindow() {
		super("Deck Editor");
		setSize(500, 900);
		setResizable(false);
		setLocation(100, 100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		//create the list models for the GUI Lists
		//deck list model
		deckListModel = new DefaultListModel<String>();
        deckListModel.addElement("Jane Doe");
        deckListModel.addElement("John Smith");
        deckListModel.addElement("Kathy Green");
        //card list model
        cardListModel = new DefaultListModel<String>();
        cardListModel.addElement("Jane Doe");
        cardListModel.addElement("John Smith");
        cardListModel.addElement("Kathy Green");
		
        //create top deck panel
		JPanel deckPanel = new JPanel();//top panel
		getContentPane().add(deckPanel);
		deckPanel.setLayout(new BoxLayout(deckPanel, BoxLayout.Y_AXIS));
		deckPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
		deckPanel.setMaximumSize(new Dimension(5000, 100));
		
		//deck Top label
		JLabel deckLabel = new JLabel("Decks");
		JPanel deckLabelPanel = new JPanel(new BorderLayout());
		deckLabelPanel.add(deckLabel, BorderLayout.WEST);
		deckPanel.add(deckLabelPanel);
		
		//create deck list
		JList<String> deckList = new JList<String>(deckListModel);
		JScrollPane deckScrollPane = new JScrollPane(deckList);
		deckScrollPane.setMaximumSize(new Dimension((int)deckPanel.getMaximumSize().getWidth(), 50));
		deckScrollPane.setMinimumSize(new Dimension((int)deckPanel.getMaximumSize().getWidth(), 50));
		deckPanel.add(deckScrollPane);
		deckList.setPreferredSize(new Dimension(200, 200));

		//create bottom part of deck panel
		//this part has a text field for user to enter a new deck name
		//button for creating new deck is also added here
		JButton createDeckButton = new JButton("Create Deck");
		JTextField deckNameTF = new JTextField();
		JLabel deckNameLabel = new JLabel("New Deck Name:");
		JPanel deckButtonPanel = new JPanel(new BorderLayout());
		deckButtonPanel.add(deckNameLabel, BorderLayout.WEST);
		deckButtonPanel.add(deckNameTF, BorderLayout.CENTER);
		deckButtonPanel.add(createDeckButton, BorderLayout.EAST);
		createDeckButton.setPreferredSize(new Dimension(90, 25));
		createDeckButton.setMargin(new Insets(5, 5, 5, 5));
		deckPanel.add(deckButtonPanel);
		
		//create card list panel
		JPanel cardPanel = new JPanel();//middle panel
		getContentPane().add(cardPanel);
		cardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
		cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
		cardPanel.setMaximumSize(new Dimension(5000, 100));
		
		//top label for selected deck
		//the cards label should be reflect the deck selection in the deck panel
		//this means the card label must be updated, reevaluated and redrawn
		//each time a user selects a different deck
		JLabel cardsLabel = new JLabel("Selected Deck: ");
		JPanel cardLabelPanel = new JPanel(new BorderLayout());
		cardLabelPanel.add(cardsLabel, BorderLayout.WEST);
		cardPanel.add(cardLabelPanel);
		
		//create the card list
		JList<String> cardList = new JList<String>(cardListModel);
		cardList.setPreferredSize(new Dimension(200, 200));

		JScrollPane cardScrollPane = new JScrollPane(cardList);
		cardPanel.add(cardScrollPane);
		cardScrollPane.setMaximumSize(new Dimension((int)deckPanel.getMaximumSize().getWidth(), 50));
		cardScrollPane.setMinimumSize(new Dimension((int)deckPanel.getMaximumSize().getWidth(), 50));
		
		//create card button will create a new card and add it to the card list
		//the default will have description "new card". User must edit and save
		//the card in order to make a custom card from the generic blank one.
		JButton createCardButton = new JButton("Create Card");
		JPanel cardButtonPanel = new JPanel(new BorderLayout());
		cardButtonPanel.add(createCardButton, BorderLayout.EAST);
		createCardButton.setPreferredSize(new Dimension(90, 25));
		createCardButton.setMargin(new Insets(5, 5, 5, 5));
		
		JPanel radioPanel = new JPanel();
		radioPanel.setLayout(new FlowLayout());
		
		JRadioButton whiteRadioButton = new JRadioButton("White Card");
		radioPanel.add(whiteRadioButton);
		JRadioButton blackRadioButton = new JRadioButton("Black Card");
		radioPanel.add(blackRadioButton);
		whiteRadioButton.setSelected(true);
		
		group = new ButtonGroup();
		group.add(whiteRadioButton);
		group.add(blackRadioButton);
		cardButtonPanel.add(radioPanel, BorderLayout.CENTER);
		cardPanel.add(cardButtonPanel);
		
		
		//create the card editing and display panel. Gives user the ability
		//to edit card and preview how the card will look.
		JPanel cardDisplayPanel = new JPanel();//bottom panel
		getContentPane().add(cardDisplayPanel);
		cardDisplayPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
	
		//create the radio buttons in the cardDisplayPanel
		//allows user to elect whether he is making a black or white card
		
		
		class MyTextArea extends JTextArea {
		    private Image backgroundImage;

		    public MyTextArea() {
		        super();
		        setOpaque(false);
		    }

		    public void setBackgroundImage(Image image) {
		        this.backgroundImage = image;
		        this.repaint();
		    }

		    @Override
		    protected void paintComponent(Graphics g) {
		        g.setColor(getBackground());
		        g.fillRect(0, 0, getWidth(), getHeight());

		        if (backgroundImage != null) {
		            g.drawImage(backgroundImage, 0, 0, this);
		        }

		        super.paintComponent(g);
		    }
		}
		
		
		
		MyTextArea cardTextArea = new MyTextArea();
		cardTextArea.setText("Cards Against Humanity");
		cardTextArea.setLineWrap(true);
		cardTextArea.setBackgroundImage(whiteCard);
		cardTextArea.setFont(new Font("Helvetica", Font.BOLD, 16));
		cardTextArea.setForeground(Color.BLACK);
		cardTextArea.setPreferredSize(new Dimension(400, 400));
		cardDisplayPanel.add(cardTextArea);
		
		JButton saveCardButton = new JButton("Save Card");
		cardDisplayPanel.add(saveCardButton);
		
//		JTextPane cardTextPane = new JTextPane();
//		StyledDocument doc = cardTextPane.getStyledDocument();
//		SimpleAttributeSet center = new SimpleAttributeSet();
//		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
//		doc.setParagraphAttributes(0, doc.getLength(), center, false);
//		cardTextPane.setPreferredSize(new Dimension(400, 400));
//		cardDisplayPanel.add(cardTextPane);
		
		
		/*
		-----------------------------------------------------------------------
		IMPORTANT CODE
		USE FOR PAINTING THE TEXT AREA
		CHECK IF THE CARD SELECTED IN CARDLIST IS BLACK OR WHITE AND CHANGE 
		THE BACKGROUND ACCORDINGLY USING THE FOLLOWING CODE
		
//		cardTextArea.setBackgroundImage(blackCard);
//    	cardTextArea.setForeground(Color.WHITE);
//    	revalidate();
//    	repaint();
		
		------------------------------------------------------------------------
		*/
		
		whiteRadioButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent arg0) {
	        	cardTextArea.setBackgroundImage(whiteCard);
	        	cardTextArea.setForeground(Color.BLACK);
	        	revalidate();
	        	repaint();
	        	
	    			
	    			//put in if statement to check if card is black or white
	    			//PlayerManager.editCard(new Card(line, true));
	    		
	            
	        }});
		blackRadioButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent arg0) {
	        	
	        	
	    			
	    			//put in if statement to check if card is black or white
	    			//PlayerManager.editCard(new Card(line, true));
	    		
	            
	        }});
		
		
		
		
		
		
		saveCardButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent arg0) {
	      
	    			String line = cardTextArea.getText();
	    			System.out.println(line);
	    			//put in if statement to check if card is black or white
	    			//PlayerManager.editCard(new Card(line, true));
	    		
	            
	        }});
		
		
		
		
		
		
		
		createCardButton.addActionListener(new ActionListener() {
			//when create card is clicked, create a blank card 
			//blank card will have description string "new card" 
			//to do this, call 
	        public void actionPerformed(ActionEvent arg0) {
	        	
	    			String line = "new card";
	    			System.out.println("new card created");
	    			//put in if statement to check if card is black or white
	    			Card newCard = new Card(line, false);
	    			//newCard.setDeckID(PlayerManager.getDecks().elementAt(int selectedIndex).getID());
	    			PlayerManager.createCard(newCard);
	    			decks = PlayerManager.getDecks();
	    			
	    		}
	            
	        });
		
		
		this.setVisible(true);
	}

	public static void main(String [] args) {
		DeckEditorWindow cs = new DeckEditorWindow();
	}

}
