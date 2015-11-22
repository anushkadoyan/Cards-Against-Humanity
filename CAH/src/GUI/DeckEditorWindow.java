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
import java.sql.SQLException;
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
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import client.PlayerManager;
import utilities.Card;
import utilities.Deck;

public class DeckEditorWindow extends JFrame{
	
	
	private static final long serialVersionUID = 1L;

	private ButtonGroup group;
	private DefaultListModel <String> deckListModel = new DefaultListModel<String>(), cardListModel = new DefaultListModel<String>();
	private Image blackCard = new ImageIcon("blackcard.png").getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT);
	private Image whiteCard = new ImageIcon("whitecard.png").getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT);
	private Vector<Deck> decks = new Vector<Deck>();
	private int deckSelection = -1;
	private int cardSelection = -1;
	JList<String> deckList = new JList<String>(deckListModel);
	JList<String> cardList = new JList<String>(cardListModel);
	
	

	public DeckEditorWindow() {
		super("Deck Editor");
		setSize(500, 750);
		setResizable(false);
		setLocation(100, 100);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		
		//TEST CODE
		
		
		Deck TESTDECK = new Deck("Snap");
		Vector<Card> cards = new Vector<Card>();
		Deck TESTDECK2 = new Deck("Pop");
		Vector<Card> cards2 = new Vector<Card>();
		decks.add(TESTDECK);
		decks.add(TESTDECK2);
		
		for(int i = 0; i < 5; i++){
			
			Card testCard = new Card(Integer.toString(i), true);
			cards.add(testCard);
			
		}
		TESTDECK.setCards(cards);
		for(int i = 4; i > 0; i--){
			Card testCard2 = new Card(Integer.toString(i), false);
			cards2.add(testCard2);
		}
		TESTDECK2.setCards(cards2);
		
		//create the list models for the GUI Lists
		//deck list model
		//deckListModel = new DefaultListModel<String>();
		
	
		
		for(int i = 0; i < decks.size(); i++){
			deckListModel.addElement(decks.elementAt(i).getName());
			
		}
		
	
        //card list model
        
        
        
        
		
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
//		final JList<String> deckList = new JList<String>(deckListModel);
		JScrollPane deckScrollPane = new JScrollPane(deckList);
		deckScrollPane.setMaximumSize(new Dimension((int)deckPanel.getMaximumSize().getWidth(), 50));
		deckScrollPane.setMinimumSize(new Dimension((int)deckPanel.getMaximumSize().getWidth(), 50));
		deckPanel.add(deckScrollPane);
		deckList.setPreferredSize(new Dimension(200, 200));
		deckList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		

		//create bottom part of deck panel
		//this part has a text field for user to enter a new deck name
		//button for creating new deck is also added here
		JButton createDeckButton = new JButton("Create Deck");
		final JTextField deckNameTF = new JTextField();
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
		
		cardList.setPreferredSize(new Dimension(200, 200));
		cardList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

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
		final JRadioButton blackRadioButton = new JRadioButton("Black Card");
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
			private static final long serialVersionUID = 3469L;
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
		
		
		
		final MyTextArea cardTextArea = new MyTextArea();
		cardTextArea.setText("Cards Against Humanity");
		cardTextArea.setLineWrap(true);
		cardTextArea.setBackgroundImage(whiteCard);
		cardTextArea.setFont(new Font("Helvetica", Font.BOLD, 16));
		cardTextArea.setForeground(Color.BLACK);
		cardTextArea.setPreferredSize(new Dimension(300, 300));
		cardDisplayPanel.add(cardTextArea);
		
		JButton saveCardButton = new JButton("Save Card");
		cardDisplayPanel.add(saveCardButton);
			
		
		saveCardButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent arg0) {
	        	try{
	    			String line = cardTextArea.getText();
//	    			PlayerManager.editCard(decks.elementAt(deckList.getSelectedIndex()).getCards().elementAt(cardList.getSelectedIndex()).getID(), line);
//	    			PlayerManager.getDecks();
	    			Card selectedCard = decks.elementAt(deckList.getSelectedIndex()).getCards().elementAt(cardList.getSelectedIndex());
	    			String oldDesc = selectedCard.getDesc();
	    			System.out.println("Changing the card.. oldDesc:" + oldDesc);
	    			
	    			selectedCard.setDesc(line);
	    			System.out.println("line: " + line);
	    			System.out.println("new desc: " + selectedCard.getDesc());
	    			editCard( decks.elementAt(deckSelection).getCards());
	    			
	    			
	    			
//	    			Card selectedCard = decks.elementAt(deckList.getSelectedIndex()).getCards().elementAt(cardList.getSelectedIndex());
//	    			String oldDesc = selectedCard.getDesc();
//	    			System.out.println("Changing the card.. oldDesc:" + oldDesc);
//	    			
//	    			selectedCard.setDesc(line);
//	    			System.out.println("line: " + line);
//	    			System.out.println("new desc: " + selectedCard.getDesc());
//	    			int index = cardList.getSelectedIndex();
//	    			cardListModel.remove(index);
//	    			cardListModel.insertElementAt(line, index);

//	    			editCard( decks.elementAt(deckSelection).getCards());
	    			
	    			
	    			
	        	}
	        	catch(Exception e){
	        		
	        	}
	        }});
		/*
		-----------------------------------------------------------------
		IMPORTANT FOR CREATING NEW DECK
//		Deck newDeck = new Deck(deckName);
//		newDeck.setOwnerID(PlayerManager.getPlayerID());
//		PlayerManager.createDeck(newDeck);
//		PlayerManager.getDecks();
-------------------------------------------------------------------------
		*/
		
		ListSelectionListener deckListSelectionListener = new ListSelectionListener() {
		      public void valueChanged(ListSelectionEvent listSelectionEvent) {
		    	  System.out.println("Changing selection");
		    	  if(deckList.getSelectedIndex() != -1 && deckList.getSelectedIndex() != deckSelection){
		    		  System.out.println("selection actually changed");
		    		  deckSelection = deckList.getSelectedIndex();
		    		  clearCard();
		    		  showCard(decks.elementAt(deckSelection).getCards());
		    	  }
		    	  
//	    			cardListModel.clear();
//	  		    	for(int i = 0; i < decks.elementAt(deckList.getSelectedIndex()).getCards().size(); i++){
//	  						cardListModel.addElement(decks.elementAt(deckList.getSelectedIndex()).getCards().elementAt(i).getDesc());
//	  						
//	  		    	}
		      }
		    };
		deckList.addListSelectionListener(deckListSelectionListener);
		
		ListSelectionListener cardListSelectionListener = new ListSelectionListener() {
		      public void valueChanged(ListSelectionEvent listSelectionEvent) {
		    	try{
		    		System.out.println("Changing Card Selection");
		    		if(cardList.getSelectedIndex() != -1 && cardList.getSelectedIndex() != deckSelection){
		    			System.out.println("selection actually changed");
		    			cardSelection = cardList.getSelectedIndex();
		    			System.out.println("selection is now: " + cardSelection);
		    		}
		    		
		    		
		    		
		    		
		    	  if(cardList.getSelectedValue() == null){
		    			cardTextArea.setText("");
		    		}
		    	  else{
		    		  cardTextArea.setText(cardList.getSelectedValue());
		    	  }
		    	  if(cardList.getSelectedIndices() == null){
		    		  
		    	  }
		    	  else if(decks.elementAt(deckList.getSelectedIndex()).getCards().elementAt(cardList.getSelectedIndex()).getBlackness() == true){
		    		  cardTextArea.setBackgroundImage(blackCard);
		    		  cardTextArea.setForeground(Color.WHITE);
		    		  revalidate();
		    		  repaint();  
		    	  }
		    	  else{
		    		  cardTextArea.setBackgroundImage(whiteCard);
		    		  cardTextArea.setForeground(Color.BLACK);
		    		  revalidate();
		    		  repaint();  
		    	  }
		    	}
		    	catch(Exception e)
		    	{
		    		
		    	}
		      }
		    };
		cardList.addListSelectionListener(cardListSelectionListener);
	

		createCardButton.addActionListener(new ActionListener() {
			//when create card is clicked, create a blank card 
			//blank card will have description string "new card" 
			//to do this, call 
	        public void actionPerformed(ActionEvent arg0) {
	        	try{
	    			String line = "new card";
	    			
	
	    			Card newCard = new Card(line, blackRadioButton.isSelected());
	    			
	    			addCard(newCard, decks.elementAt(deckSelection).getCards());
//	    			newCard.setDeckID(decks.elementAt(deckList.getSelectedIndex()).getID());
//	    			//PlayerManager.createCard(newCard);
//	    			decks.elementAt(deckList.getSelectedIndex()).getCards().add(newCard);
//	    			
//	    			
//	    			
//	    			cardListModel.addElement(newCard.getDesc());
	    			
//	    			decks = PlayerManager.getDecks();

				
	    			
	    		} catch(Exception e){
	    			// print something???
	    			e.printStackTrace();
	    		}
	        }
	            
	        });
		
		createDeckButton.addActionListener(new ActionListener() {
			//when create card is clicked, create a blank card 
			//blank card will have description string "new card" 
			//to do this, call 
	        public void actionPerformed(ActionEvent arg0) {
	        	
	        	try{
	        		
	    			String deckName = deckNameTF.getText();
	    			if(!deckName.equals("")){
		    			Deck newDeck = new Deck(deckName);
		    			
		    			

		    			
		    			addDeck(newDeck);
//	    			
//		    			deckListModel.addElement(newDeck.getName());
		    			
						
//		    			decks = PlayerManager.getDecks();
//		    			deckNameTF.setText("");
		    			System.out.println("Trying to add a new deck");
	    			} else { System.out.println("The deck name isn't valid or something"); }
	    			

	    			}catch(Exception e){

	    			}
	        }
	            
	    });
		
		
		this.setVisible(true);
	}
	
	public void addDeck(Deck d) { 
		System.out.println("Adding a new deck");
		decks.add(d);
		
//		d.setOwnerID(PlayerManager.getPlayerID());
//		PlayerManager.createDeck(d);
		System.out.println("added the deck");
		clearDeck();
		System.out.println("cleared the deck");
		showDeck();
		System.out.println("showed the deck");
		selectDeck();
	}
	public void clearDeck() {
		
//		deckListModel.clear();
		System.out.println("Cleaing the deck");
//		deckListModel.addElement("TEST ELEMENT");
		System.out.println("Deck size model sz: " + deckListModel.size());
		deckListModel.clear();
		System.out.println("success");
	}
	
	public void selectDeck() {
		System.out.println("deckSelection " + deckSelection);
		
		deckList.setSelectedIndex(deckSelection);
	}
	public void showDeck() {
		for (Deck d : decks) {
			deckListModel.addElement(d.getName());
		}

	}
	
	public void editCard(Vector<Card> cards){
		clearDeck();
		showDeck();
		selectDeck();
		clearCard();
		System.out.println("cleared the card");
		showCard(cards);
		System.out.println("showed the card");
		selectCard();
	}
	
	public void addCard(Card c, Vector<Card> cards) { 
		System.out.println("Adding a new card");
		cards.add(c);
		System.out.println("added the card");
		clearDeck();
		showDeck();
		selectDeck();
		clearCard();
		System.out.println("cleared the card");
		showCard(cards);
		System.out.println("showed the card");
		selectCard();
	}
	public void clearCard() {
		
//		deckListModel.clear();
		System.out.println("Clearing the cards");
//		deckListModel.addElement("TEST ELEMENT");
		System.out.println("card size model sz: " + cardListModel.size());
		cardListModel.clear();
		System.out.println("success");
	}
	
	public void selectCard() {
		System.out.println("cardSelection " + cardSelection);
		cardList.setSelectedIndex(cardSelection);
	}
	
	public void showCard(Vector<Card> cards) {
		for (Card c : cards) {
			cardListModel.addElement(c.getDesc());
		}

	}

	public static void main(String [] args) {
		
		new DeckEditorWindow();
	}

}
