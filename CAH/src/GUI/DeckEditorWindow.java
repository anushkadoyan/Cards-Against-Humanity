package GUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.GrayFilter;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

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
	

	public DeckEditorWindow() {
		super("Deck Editor");
		setSize(500, 900);
		setResizable(false);
		setLocation(100, 100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		deckListModel = new DefaultListModel<String>();
        deckListModel.addElement("Jane Doe");
        deckListModel.addElement("John Smith");
        deckListModel.addElement("Kathy Green");
        
        cardListModel = new DefaultListModel<String>();
        cardListModel.addElement("Jane Doe");
        cardListModel.addElement("John Smith");
        cardListModel.addElement("Kathy Green");
		
		JPanel deckPanel = new JPanel();//top panel
		getContentPane().add(deckPanel);
		deckPanel.setLayout(new BoxLayout(deckPanel, BoxLayout.Y_AXIS));
		deckPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
		deckPanel.setMaximumSize(new Dimension(5000, 100));
		
		JLabel deckLabel = new JLabel("Decks");
		JPanel deckLabelPanel = new JPanel(new BorderLayout());
		deckLabelPanel.add(deckLabel, BorderLayout.WEST);
		deckPanel.add(deckLabelPanel);
		
		JList<String> deckList = new JList<String>(deckListModel);
		JScrollPane deckScrollPane = new JScrollPane(deckList);
		deckScrollPane.setMaximumSize(new Dimension((int)deckPanel.getMaximumSize().getWidth(), 50));
		deckScrollPane.setMinimumSize(new Dimension((int)deckPanel.getMaximumSize().getWidth(), 50));
		deckPanel.add(deckScrollPane);
		deckList.setPreferredSize(new Dimension(200, 200));

		JButton createDeckButton = new JButton("Create Deck");
		JPanel deckButtonPanel = new JPanel(new BorderLayout());
		deckButtonPanel.add(createDeckButton, BorderLayout.EAST);
		createDeckButton.setPreferredSize(new Dimension(90, 25));
		createDeckButton.setMargin(new Insets(5, 5, 5, 5));
		deckPanel.add(deckButtonPanel);
		
		
		JPanel cardPanel = new JPanel();//middle panel
		getContentPane().add(cardPanel);
		cardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
		cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
		cardPanel.setMaximumSize(new Dimension(5000, 100));
		
		JLabel cardsLabel = new JLabel("Selected Deck: ");
		JPanel cardLabelPanel = new JPanel(new BorderLayout());
		cardLabelPanel.add(cardsLabel, BorderLayout.WEST);
		cardPanel.add(cardLabelPanel);
		
		JList<String> cardList = new JList<String>(cardListModel);
		cardList.setPreferredSize(new Dimension(200, 200));
		
		JScrollPane cardScrollPane = new JScrollPane(cardList);
		cardPanel.add(cardScrollPane);
		cardScrollPane.setMaximumSize(new Dimension((int)deckPanel.getMaximumSize().getWidth(), 50));
		cardScrollPane.setMinimumSize(new Dimension((int)deckPanel.getMaximumSize().getWidth(), 50));
		
		JButton createCardButton = new JButton("Create Card");
		JPanel cardButtonPanel = new JPanel(new BorderLayout());
		cardButtonPanel.add(createCardButton, BorderLayout.EAST);
		createCardButton.setPreferredSize(new Dimension(90, 25));
		createCardButton.setMargin(new Insets(5, 5, 5, 5));
		cardPanel.add(cardButtonPanel);
		
		
		JPanel cardDisplayPanel = new JPanel();//bottom panel
		getContentPane().add(cardDisplayPanel);
		cardDisplayPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
		
		JPanel radioPanel = new JPanel();
		radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.X_AXIS));
		
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
		
//		JTextPane cardTextPane = new JTextPane();
//		StyledDocument doc = cardTextPane.getStyledDocument();
//		SimpleAttributeSet center = new SimpleAttributeSet();
//		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
//		doc.setParagraphAttributes(0, doc.getLength(), center, false);
//		cardTextPane.setPreferredSize(new Dimension(400, 400));
//		cardDisplayPanel.add(cardTextPane);
		
		
		
		
		JRadioButton whiteRadioButton = new JRadioButton("White Card");
		radioPanel.add(whiteRadioButton);
		JRadioButton blackRadioButton = new JRadioButton("Black Card");
		radioPanel.add(blackRadioButton);
		whiteRadioButton.setSelected(true);
		
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
	        	cardTextArea.setBackgroundImage(blackCard);
	        	cardTextArea.setForeground(Color.WHITE);
	        	revalidate();
	        	repaint();
	        	
	    			
	    			//put in if statement to check if card is black or white
	    			//PlayerManager.editCard(new Card(line, true));
	    		
	            
	        }});
		
		
		group = new ButtonGroup();
		group.add(whiteRadioButton);
		group.add(blackRadioButton);
		
		JButton saveCardButton = new JButton("Save Card");
		saveCardButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent arg0) {
	      
	    			String line = cardTextArea.getText();
	    			System.out.println(line);
	    			//put in if statement to check if card is black or white
	    			//PlayerManager.editCard(new Card(line, true));
	    		
	            
	        }});
		radioPanel.add(saveCardButton);
		
		cardDisplayPanel.add(radioPanel);
		
		
		/*createCardButton.addActionListener(new ActionListener() {
			//when create card is clicked, create a blank card 
			//blank card will have description string "new card" 
			//to do this, call 
	        public void actionPerformed(ActionEvent arg0) {
	        	try {
	    			String line = cardTextPane.getDocument().getText(0,  cardTextPane.getDocument().getLength());
	    			System.out.println(line);
	    			//put in if statement to check if card is black or white
	    			PlayerManager.editCard(new Card(line, true));
	    		} catch (BadLocationException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	            
	        }});*/
		
		
		this.setVisible(true);
	}

	public static void main(String [] args) {
		DeckEditorWindow cs = new DeckEditorWindow();
	}

}
