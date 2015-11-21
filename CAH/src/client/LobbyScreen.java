package client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import customUI.ImagePanel;

public class LobbyScreen extends ImagePanel{

	private static final long serialVersionUID = 9038189205616516369L;
	private JPanel centerPanel, southPanel, westPanel, eastPanel, northPanel;
	private JButton viewDeckButton, createGameButton, connectButton;
	private JTable gameTable;
	private JScrollPane jsp;
	private Image img;
	
	public LobbyScreen(Image inImage){
		super(inImage);
		img = inImage;
		
		westPanel = new JPanel();
			westPanel.setOpaque(false);
		eastPanel = new JPanel();
			eastPanel.setOpaque(false);
		northPanel = new JPanel();
			northPanel.setOpaque(false);
		
		viewDeckButton = new JButton("View/Edit Deck");
			viewDeckButton.setFont(new Font("Andalus", Font.PLAIN, 10));
			viewDeckButton.setPreferredSize(new Dimension(110,40));
		createGameButton = new JButton("Create Game");
			createGameButton.setFont(new Font("Andalus", Font.PLAIN, 12));
			createGameButton.setPreferredSize(new Dimension(110,40));
		connectButton = new JButton("Connect");
			connectButton.setFont(new Font("Andalus", Font.PLAIN, 12));
			connectButton.setPreferredSize(new Dimension(110,40));
		
		southPanel = new JPanel();
		southPanel.setLayout(new FlowLayout());
		southPanel.add(viewDeckButton);
		southPanel.add(new JLabel(""));
		southPanel.add(connectButton);
		southPanel.add(new JLabel(""));
		southPanel.add(createGameButton);
		southPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		southPanel.setOpaque(false);
			
		
		
		Object[] tableHeaders = new Object[] {"Game/Host", "Players", "Progress"};
		DefaultTableModel tableModel = new DefaultTableModel(tableHeaders, 0){
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
		
		//gameTable.getColumn("Game/Host").setPreferredWidth();
		gameTable.getColumn("Players").setPreferredWidth(50);
		gameTable.getColumn("Progress").setPreferredWidth(100);
		
		gameTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e){
				if (gameTable.getSelectedRow() > -1){
					//Do something
				}
			}
		});
		gameTable.setOpaque(false);
		
		jsp = new JScrollPane(gameTable);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(jsp, BorderLayout.CENTER);
		centerPanel.setBorder(BorderFactory.createEmptyBorder(40,40,20,40));
		centerPanel.setOpaque(false);
		
		this.setLayout(new BorderLayout());
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
		this.add(northPanel, BorderLayout.NORTH);
		this.add(westPanel, BorderLayout.WEST);
		this.add(eastPanel, BorderLayout.EAST);
		
		this.setOpaque(false);
		centerPanel.setOpaque(false);
		southPanel.setOpaque(false);
	
	}
}
