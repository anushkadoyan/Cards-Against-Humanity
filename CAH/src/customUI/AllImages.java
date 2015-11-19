package customUI;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class AllImages {
	private static Map<String, Image> imageMap;

	static{
		imageMap = new HashMap<String,Image>();
	}
	
	private AllImages(){}
	
	public static Image getImage(String name){
		Image image = imageMap.get(name);
		if (image == null){
			try{
				image = ImageIO.read(new File(name));
			} catch(IOException e){
				System.out.println("IOException Reading Image: " + name + e.getMessage());
				return null;
			}
			imageMap.put(name, image);
		}
		return image;
	}
}
