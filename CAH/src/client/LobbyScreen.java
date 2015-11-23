package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import GUI.DeckEditorWindow;
import customUI.ImagePanel;

public class LobbyScreen extends ImagePanel{

	private static final long serialVersionUID = 9038189205616516369L;
	private JPanel centerPanel, southPanel, westPanel, eastPanel, northPanel;
	private JButton viewDeckButton, createGameButton, connectButton;
	private static JTable gameTable;
	private static DefaultTableModel tableModel;
	private JScrollPane jsp;
	private boolean isGuest = false;
	private DeckEditorWindow deckEditor = new DeckEditorWindow();
	
	// output to server
	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	public LobbyScreen(Image inImage, boolean guest, ActionListener connectAction, final ObjectOutputStream oStream, ObjectInputStream iStream){
		super(inImage);
		this.isGuest = guest;
		System.out.println("in lobbyscreen");

		// i/o to server
		oos = oStream;
		ois = iStream;

		System.out.println("is oos null? " + (oos == null));
		System.out.println(isGuest);
		westPanel = new JPanel();
		westPanel.setOpaque(false);
		eastPanel = new JPanel();
		eastPanel.setOpaque(false);
		northPanel = new JPanel();
		northPanel.setOpaque(false);
		
		viewDeckButton = new JButton("View/Edit Deck");
		viewDeckButton.setFont(new Font("Andalus", Font.PLAIN, 10));
		viewDeckButton.setPreferredSize(new Dimension(120,40));
		viewDeckButton.setBackground(Color.WHITE);
		viewDeckButton.setOpaque(true);
		viewDeckButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae){
				System.out.println("SHOW DECKS BUTTON PRESSED");
				// let the server know that you want to see the deck
				try {
					System.out.println("About to write to the object");
					System.out.println("is oos null???: " + (oos == null));
					System.out.println("is oStream null???: " + (oStream == null));
					oos.writeObject("showDeck");
					oos.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				deckEditor.setVisible(true);
				
			}
		});
			
		createGameButton = new JButton("Create Game");
		if(isGuest == true) {
			createGameButton.setEnabled(false);
			viewDeckButton.setEnabled(false);

		}
		createGameButton.setFont(new Font("Andalus", Font.PLAIN, 12));
		createGameButton.setPreferredSize(new Dimension(120,40));
		createGameButton.setBackground(Color.WHITE);
		createGameButton.setOpaque(true);
			
		
		connectButton = new JButton("Connect");
		connectButton.setEnabled(false);
		connectButton.setFont(new Font("Andalus", Font.PLAIN, 12));
		connectButton.setPreferredSize(new Dimension(120,40));
		connectButton.setBackground(Color.WHITE);
		connectButton.setOpaque(true);
		connectButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae){
				int index = gameTable.getSelectedRow();
				//Connect to game 'index'
			}
		});
		connectButton.addActionListener(connectAction);
		
		southPanel = new JPanel();
		southPanel.setLayout(new FlowLayout());
		southPanel.add(viewDeckButton);
		southPanel.add(new JLabel(""));
		southPanel.add(connectButton);
		southPanel.add(new JLabel(""));
		southPanel.add(createGameButton);
		southPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		//Create table
		Object[] tableHeaders = new Object[] {"Game/Host", "Players", "Progress"};
		tableModel = new DefaultTableModel(tableHeaders, 0){
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		
		gameTable = new JTable(tableModel){
			private static final long serialVersionUID = -6465578622780769821L;

			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
				Component c = super.prepareRenderer(renderer, row, column);
				if (c instanceof JComponent){
					((JComponent) c).setOpaque(false);
				}
				return c;
			}
		};
		
		//Sizing columns
		gameTable.getColumnModel().getColumn(0).setPreferredWidth(400);
		gameTable.getColumnModel().getColumn(1).setPreferredWidth(50);
		gameTable.getColumnModel().getColumn(2).setPreferredWidth(100);
		
		//Align center
		DefaultTableCellRenderer center = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		for (int i = 0; i < 3; i++){
			gameTable.getColumnModel().getColumn(i).setCellRenderer(center);
		}
		
		gameTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e){
				if (gameTable.getSelectedRow() >= 0){
					connectButton.setEnabled(true);
				}
			}
		});
		
		jsp = new JScrollPane(gameTable);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(jsp, BorderLayout.CENTER);
		centerPanel.setBorder(BorderFactory.createEmptyBorder(80,80,30,80));
		
		this.setLayout(new BorderLayout());
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
		this.add(northPanel, BorderLayout.NORTH);
		this.add(westPanel, BorderLayout.WEST);
		this.add(eastPanel, BorderLayout.EAST);
		
		gameTable.setOpaque(false);
		this.setOpaque(false);
		centerPanel.setOpaque(false);
		southPanel.setOpaque(false);
		
		add("Test Game", 3, "Waiting");
		add("Test Game 2", 4, "In Progress");
	
	}
	
	public void setGuest(boolean is) {
		isGuest = is;
		createGameButton.setEnabled(false);
		viewDeckButton.setEnabled(false);
	}
	
	public void add (String host, int numPlayers, String progress){
		Object[] row = {host, numPlayers + "/5", progress};
		tableModel.addRow(row);
	}
}
