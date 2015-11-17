
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;


public class PaintedButton extends JButton{
	private static final long serialVersionUID = 7074393176317490987L;

	private Image toDraw;
	private final Image mUp;
	
	
	public PaintedButton(String name, Image inUp) {
		super(name);
		toDraw = mUp = inUp;
		
		
	
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(toDraw, 0, 0, getWidth(), getHeight(), null);
		super.paintComponent(g);
	}
	
	@Override
	protected void paintBorder(Graphics g) {
		//paint no border
	}
	
}
