package frames;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import functionality.General;


@SuppressWarnings("serial")
public class MyPanel extends JPanel{
	
	private Image imagen;
	int nimagen;
	
	public MyPanel(int width, int height){
		nimagen = (int) (System.currentTimeMillis()%General.NIMAGES);
		URL url = this.getClass().getResource("../images/fractal" + nimagen + ".jpg");
		BufferedImage img;
		try { 
			img = ImageIO.read(url);			
			setImagen(img.getScaledInstance(width,height, Image.SCALE_DEFAULT));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	protected void paintComponent(Graphics g){
	    super.paintComponent(g);
	    g.drawImage(getImagen(),0,0,null);
	    
	}
	
	public Image getImagen() {
		return imagen;
	}

	public void setImagen(Image imagen) {
		this.imagen = imagen;
	}

	public void changeSize(int width, int height) {
		URL url = this.getClass().getResource("../images/fractal" + nimagen + ".jpg");
		BufferedImage img;
		try { 
			img = ImageIO.read(url);			
			setImagen(img.getScaledInstance(width,height, Image.SCALE_DEFAULT));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}


}
