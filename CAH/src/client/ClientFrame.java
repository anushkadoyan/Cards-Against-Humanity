package client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import server.Server;

public class ClientFrame extends JFrame{
	
	private static final long serialVersionUID = -7114633140703492838L;
	
	{
		setTitle("CAH");
		setSize(new Dimension(1024,768));
		setMinimumSize(new Dimension(800,600));
		setMaximumSize(new Dimension(1024,768));
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
		    	new HostAndPortGUI(new ActionListener(){
		    		@Override
		    		public void actionPerformed(ActionEvent ae){
		    			ClientFrame cf = new ClientFrame();
				    	cf.setVisible(true);
		    		}
		    	});
		    	
		    }
		});
	}
	
}
