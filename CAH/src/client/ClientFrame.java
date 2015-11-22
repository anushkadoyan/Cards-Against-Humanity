package client;

import java.awt.Dimension;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ClientFrame extends JFrame{
	
	private static final long serialVersionUID = -7114633140703492838L;
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	public ClientFrame(Socket socket){
		this.socket = socket;
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		setTitle("CAH");
		setSize(new Dimension(1024,768));
		setMinimumSize(new Dimension(800,600));
		setMaximumSize(new Dimension(1024,768));
		add(new ClientPanel(oos, ois));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
	
	/*
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
	*/
	
}
