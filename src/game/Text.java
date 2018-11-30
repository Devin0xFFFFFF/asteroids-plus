package game;

import java.awt.Color;
import java.awt.Font;

public class Text extends Actor
{
	public String text;
	public int width, height;
	public Font font;
	public Color color;
	private GameGraphic g;
	
	public Text(String text)
	{
		this.text = text;
		width = 100; height = 100;
		font = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
		color = Color.black;
		g = new GameGraphic(width,height);
		update(text);
	}
	
	public void update(String text)
	{
		this.text = text;
		setText();
	}
	
	private void setText()
	{
		g.clearImage();
		g.drawString(text);
		setImage(g.getImage());
	}
	
	public void setStyle(Color col, Font ft)
	{
		color = col; font = ft;
		g.setColor(color);
		g.setFont(font);
	}
	
	public void setSize(int w, int h)
	{
		width = w; height = h;
		g.setSize(width,height);
	}
}
