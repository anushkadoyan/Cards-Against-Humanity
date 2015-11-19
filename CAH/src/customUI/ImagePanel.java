package customUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class ImagePanel extends JPanel{

	private static final long serialVersionUID = 1863103710429224973L;
	
	private Image inImage;
	
	public ImagePanel(Image img){
		this.inImage = img;
		this.setBackground(Color.WHITE);
		this.setOpaque(true);
	}
	
	public void setImage(Image img){
		this.inImage = img;
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(inImage, 0, 0, getWidth(), getHeight(), null);
	}

}
