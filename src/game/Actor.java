package game;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Actor 
{
	public GameImage image = new GameImage("default_image.png");
	public int X = 0,Y = 0;
	public int direction = 0;
	public World world;
	public String actor = "Actor";
	
	public Actor()
	{
		
	}
	
	public void act()
	{
		
	}
	
	public BufferedImage getImage()
	{
		return image.getImage();
	}
	
	public void setImage(String img)
	{
		image = new GameImage(img);
	}
	
	public void setImage(BufferedImage img)
	{
		image = new GameImage(img);
	}
	
	public void setImage(GameImage img)
	{
		image = img;
	}
	
	public void setLocation(int x, int y)
	{
		X = x; Y = y;
	}
	
	public void move(int speed)
	{
		double angle = Math.toRadians(direction);
        int x = (int) Math.round(X + Math.cos(angle) * speed);
        int y = (int) Math.round(Y + Math.sin(angle) * speed);
        
        setLocation(x, y);
	}
	
	public void setRotation(int angle)
	{
		while(!(angle <= 360 && angle >= 0))
		{
			if(angle > 360)
			{
				angle -= 360;
			}
			else if(angle < 0)
			{
				angle += 360;
			}
		}
		direction = angle;
		if(direction == 360)
		{
			direction = 0;
		}
		image.rotate(direction);
	}
	
	public void rotate(int rate)
	{
		direction += rate;
		if(direction >= 360)
		{
			direction -= 360;
		}
		if(direction < 0)
		{
			direction += 360;
		}
		image.rotate(direction);
	}
	
	public void turnTowards(int x, int y)
	{
		setRotation((int)Math.toDegrees(Math.atan2(x - X, y - Y)));
	}
	
	public void turnTowards(String type)
	{
		Actor a = getActor(type);
		setRotation((int)Math.toDegrees(Math.atan2(a.X - X, a.Y - Y)));
	}
	
	public void scale(int x, int y)
	{
		image.resize(x, y);
	}
	
	public void flipHorizontal()
	{
		image.flipHorizontal();
	}
	
	public void flipVertical()
	{
		image.flipVertical();
	}
	
	public Rectangle getBounds()
	{
		return new Rectangle(X, Y, image.getImage().getWidth(), image.getImage().getHeight());
	}
	
	public int rightEdge()
	{
		return getBounds().x + getBounds().width;
	}
	
	public int leftEdge()
	{
		return getBounds().x;
	}
	
	public int topEdge()
	{
		return getBounds().y;
	}
	
	public int bottomEdge()
	{
		return getBounds().y + getBounds().height;
	}
	
	public boolean atWorldEdge()
	{
		return leftEdge()<=0 || topEdge()<=0 || rightEdge() >= world.WIDTH || bottomEdge() >= world.HEIGHT;
	}
	
	public String atWorldBounds(int buffer)
	{
		String result = "";
		if(getBounds().intersects(world.topBound(buffer)))
		{
			result = "TOP";
		}
		else if(getBounds().intersects(world.bottomBound(buffer)))
		{
			result = "BOTTOM";
		}
		else if(getBounds().intersects(world.rightBound(buffer)))
		{
			result = "RIGHT";
		}
		else if(getBounds().intersects(world.leftBound(buffer)))
		{
			result = "LEFT";
		}
		return result;
	}
	
	public Actor getActor(String type)
	{
		ArrayList<Actor> actors = world.getActors();
		for(Actor a : actors)
		{
			if(a != this && a.actor.equals(type))
			{
				return a;
			}
		}
		return null;
	}
	
	public ArrayList<Actor> getActors(String type)
	{
		ArrayList<Actor> actors = world.getActors();
		ArrayList<Actor> actorList = new ArrayList<Actor>();
		for(Actor a : actors)
		{
			if(a != this && a.actor.equals(type))
			{
				actorList.add(a);
			}
		}
		return actorList;
	}
	
	public Actor getIntersectingActor(String type)
	{
		ArrayList<Actor> actors = world.getActors();
		for(Actor a : actors)
		{
			if(a != this && a.actor.equals(type) && getBounds().intersects(a.getBounds()))
			{
				return a;
			}
		}
		return null;
	}
	
	public ArrayList<Actor> getIntersectingActors(String type)
	{
		ArrayList<Actor> actors = world.getActors();
		ArrayList<Actor> intersectingActors = new ArrayList<Actor>();
		for(Actor a : actors)
		{
			if(a != this && a.actor.equals(type) && getBounds().intersects(a.getBounds()))
			{
				intersectingActors.add(a);
			}
		}
		return intersectingActors;
	}
	
	public ArrayList<Actor> getActorsInRange(String type, int range)
	{
		ArrayList<Actor> actors = world.getActors();
		ArrayList<Actor> intersectingActors = new ArrayList<Actor>();
		for(Actor a : actors)
		{
			if(a != this && actors.equals(a.actor) && X - a.X <= range && Y - a.Y <= range)
			{
				intersectingActors.add(a);
			}
		}
		return intersectingActors;
	}
}
