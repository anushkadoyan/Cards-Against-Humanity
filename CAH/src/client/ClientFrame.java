package client;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ClientFrame extends JFrame{
	
	private static final long serialVersionUID = -7114633140703492838L;
	
	{
		setTitle("CAH");
		setSize(new Dimension(640,480));
		setMinimumSize(new Dimension(640,480));
		setMaximumSize(new Dimension(960,720));
		add(new ClientPanel());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String [] args){
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	ClientFrame cf = new ClientFrame();
		    	cf.setVisible(true);
		    }
		});
	}
	
}
