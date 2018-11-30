package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class GameGraphic 
{
	private BufferedImage image;
	public int width, height;
	private Color color = Color.black;
	private Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
	
	public GameGraphic(int width, int height)
	{
		image = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		this.width = width; this.height = height;
	}
	
	public GameGraphic(BufferedImage img)
	{
		image = img;
		width = img.getWidth(); height = img.getHeight();
	}
	
	public void setColor(Color col)
	{
		color = col;
	}
	
	public void setFont(Font ft)
	{
		font = ft;
	}
	
	public void setSize(int w, int h)
	{
		width = w; height = h;
	}
	
	public BufferedImage getImage()
	{
		return image;
	}
	
	public void clearImage()
	{
		image = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
	}
	
	public void drawString(String text)
	{
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = img.createGraphics();
		g.setColor(color);
		g.setFont(font);
		g.drawString(text, 0, height/2);
		g.dispose();
		image = img;
	}
	
	public void fillRect()
	{
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = img.createGraphics();
        g.setColor(color);
        g.fillRect(0, 0, width, height);
        g.dispose();
        
        image = img;
	}
	
	public void fillOval()
	{
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = img.createGraphics();
        g.setColor(color);
        g.fillOval(0, 0, width, height);
        g.dispose();
        
        image = img;
	}
}
