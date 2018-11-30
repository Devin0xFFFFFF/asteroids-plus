package game;

import java.awt.Color;

public class Pixel extends VectorMover
{
	public boolean active, decaying;
	private int decay, DECAY_LIMIT;
	private double DECAY_RATE;
	private float alpha;
	private int SPEED;
	private GameGraphic img;
	
	public Pixel()
	{
		actor = "Pixel";
		
		img = new GameGraphic(3,3);
		img.setColor(Color.white);
		img.fillRect();
		setImage(img.getImage());
		
		SPEED = 5;
		
		decaying = true;
		decay = 1; DECAY_LIMIT = 50;
		alpha = 0.0f; DECAY_RATE = 0.05;
		image.setTransparency(alpha, direction);
	}
	
	public void act()
	{
		if(active)
		{
			delete();
			move();
		}
	}
	
	public void activate(int dir, int x, int y)
	{
		setLocation(x,y); setRotation(dir);
		addForce(new Vector(direction, SPEED));
		active = true;
		alpha = 1.0f;
		image.setTransparency(alpha, direction);
	}
	
	private void delete()
	{
		if(atEdge() || decay >= DECAY_LIMIT)
		{
			destroy();
		}
		else if(decaying)
		{
			decay();
		}
	}
	
	private boolean atEdge()
	{
		return rightEdge() >= world.WIDTH+50 || leftEdge() <= -50 
		|| topEdge() <= -50 || bottomEdge() >= world.HEIGHT + 50;
	}
	
	private void decay()
	{
		decay++;
		alpha-=DECAY_RATE;
		if(alpha < 0)
		{
			alpha = 0;
			decay = DECAY_LIMIT;
		}
		image.setTransparency(alpha, direction);
	}
	
	private void destroy()
	{
		setLocation(-10,0);
		getMovement().setLength(0);
		alpha = 0.0f; decay = 0;
		active = false;
		image.setTransparency(alpha, direction);
	}
	
	public void style(int x, int y, Color col)
	{
		img.clearImage(); img.setSize(x, y); img.setColor(col);
		img.fillRect(); setImage(img.getImage());
	}
	
	public void setSpeed(int speed)
	{
		SPEED = speed;
	}
	
	public void setDecay(double rate)
	{
		DECAY_RATE = rate;
	}
}
