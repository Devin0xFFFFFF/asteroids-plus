package game;
import java.awt.Graphics;

public class Window extends GameWorld
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init()
	{
		setSize(WIDTH,HEIGHT);
		Thread game = new Thread(this); 
		game.start();
		offscreen = createImage(WIDTH,HEIGHT);
		d = offscreen.getGraphics();
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public void paint(Graphics g)
	{
		d.clearRect(0, 0, WIDTH, HEIGHT);
		try
		{
			d.drawImage(getWorldBackground(), 0, 0, this);
			d.drawImage(message.getImage(), message.width-message.width/2+150, message.height-message.height/2+50, this);
		}
		catch(Exception e){}
		
		for(Actor a : actors)
		{
			d.drawImage(a.getImage(), a.X, a.Y, this);
		}	
		g.drawImage(offscreen,  0, 0, this);
	}
	
	public void update(Graphics g)
	{
		paint(g);
	}
}
