package game;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.event.MouseInputListener;

public class World extends Applet implements Runnable, KeyListener, MouseInputListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//CORE VARIABLES
	public final int WIDTH = 1200, HEIGHT = 720;
	public Image offscreen;
	public Graphics d;
	public boolean running = true;
	public int fps = 20;
	
	//ACTOR LISTS
	public ArrayList<Actor> actors = new ArrayList<Actor>();
	public ArrayList<Actor> addList = new ArrayList<Actor>();
	public ArrayList<Actor> removeList = new ArrayList<Actor>();
	
	//RESOURCES
	public ArrayList<GameSound> sounds = new ArrayList<GameSound>();
	public GameImage background = new GameImage("default_background.png");
		
	//LISTENER VARIABLES
	private KeyEvent[] keys = new KeyEvent[222];
	private boolean[] keysPressed = new boolean[222];
	public MouseEvent mouse;
	public boolean mousePressed = false, mouseClicked = false;
		
	//ADDITIONAL VARIABLES
	private Random random = new Random();
	
	
	//RUN
	public void run()
	{
		loadWorld();
		loadResources();
		while(running)
		{
			act();
			addActors();
			runActors();
			removeActors();
			repaint();
			try
			{
				Thread.sleep(fps);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	//OVERLOADED METHODS
	
	public void loadWorld()
	{
		
	}
	
	public void loadResources()
	{
		
	}
	
	public void act()
	{
		
	}
	
	//RESOURCES
	
	public GameSound getSound(String path)
	{
		for(GameSound gs : sounds)
		{
			if(gs.getPath().equals(path))
			{
				return gs;
			}
		}
		return new GameSound("default_sound.wav");
	}
	
	//BACKGROUND
	
	public void setBackground(String img)
	{
		background = new GameImage(img);
		background.resize(WIDTH,HEIGHT);
	}
	
	public void setBackground(BufferedImage img)
	{
		background = new GameImage(img);
		background.resize(WIDTH,HEIGHT);
	}
	
	public BufferedImage getWorldBackground()
	{
		return background.getImage();
	}
	
	//ACTORS
	
	public void runActors()
	{
		for(Actor a : actors)
		{
			a.act();
		}
	}
	
	public void addActor(Actor a)
	{
		addList.add(a);
		a.world = this;
	}
	
	public void addActor(Actor a, int x, int y)
	{
		addList.add(a);
		a.world = this;
		a.setLocation(x,y);
	}
	
	public void addActors()
	{
		for(Actor a : addList)
		{
			actors.add(a);
		}
		addList.clear();
	}
	
	public void removeActor(Actor a)
	{
		removeList.add(a);
	}
	
	public void removeActors()
	{
		for(Actor a : removeList)
		{
			actors.remove(a);
		}
		removeList.clear();
	}
	
	public ArrayList<Actor> getActors()
	{
		return actors;
	}
	
	public ArrayList<Actor> getActors(String type)
	{
		ArrayList<Actor> actorList = new ArrayList<Actor>();
		for(Actor a : actors)
		{
			if(a.actor.equals(type))
			{
				actorList.add(a);
			}
		}
		return actorList;
	}
	
	public Actor getActor(String type)
	{
		for(Actor a : actors)
		{
			if(a.actor.equals(type))
			{
				return a;
			}
		}
		return null;
	}
	
	//BOUNDS
	public Rectangle worldBounds()
	{
		return new Rectangle(0,0,WIDTH,HEIGHT);
	}
	
	public Rectangle topBound(int buffer)
	{
		return new Rectangle(0,-buffer*2,WIDTH,buffer);
	}
	
	public Rectangle bottomBound(int buffer)
	{
		return new Rectangle(0,HEIGHT+buffer,WIDTH,buffer);
	}
	
	public Rectangle rightBound(int buffer)
	{
		return new Rectangle(-buffer*2,0,buffer,HEIGHT);
	}
	
	public Rectangle leftBound(int buffer)
	{
		return new Rectangle(WIDTH+buffer,0,buffer,HEIGHT);
	}
	
	//INPUT

	public void keyPressed(KeyEvent e) 
	{
		keys[e.getKeyCode()] = e;
		keysPressed[e.getKeyCode()] = true;
	}
	
	public void keyReleased(KeyEvent e) 
	{
		keys[e.getKeyCode()] = null;
		keysPressed[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) 
	{
		
	}
		
	public boolean isKeyPressed(String KEY)
	{
		for(int i=0;i<keys.length;i++)
		{
			if(keys[i] != null && keysPressed[i] && KEY.equals(getKey(keys[i])))
			{
				return true;
			}
		}
		return false;
	}
		
	private String getKey(KeyEvent key)
	{
		switch(key.getKeyCode())
		{
			case 8 : return "backspace";
			case 9 : return "tab";
			case 13 : return "enter";
			case 16 : return "shift";
			case 17 : return "ctrl";
			case 18 : return "alt";
			case 27 : return "esc";
			case 32 : return "space";
			case 37 : return "left";
			case 38 : return "up";
			case 39 : return "right";
			case 40 : return "down";
		}
		return "" + key.getKeyChar();
	}
	
	public boolean click(int button)
	{
		if(mouseClicked && button == mouse.getButton())
		{
			mouseClicked = false;
			return true;
		}
		return false;
	}
	
	public boolean doubleClick(int button)
	{
		if(mouseClicked && mouse.getClickCount() == 2 && button == mouse.getButton())
		{
			mouseClicked = false;
			return true;
		}
		return false;
	}
	
	public void mouseClicked(MouseEvent e) 
	{
		mouse = e;
		mouseClicked = true;
	}

	public void mouseEntered(MouseEvent e) 
	{
		mouse = e;
	}

	public void mouseExited(MouseEvent e) 
	{
		mouse = e;
	}

	public void mousePressed(MouseEvent e) 
	{
		mousePressed = true;
		mouse = e;
	}

	public void mouseReleased(MouseEvent e) 
	{
		mousePressed = false;
		mouse = e;
	}

	public void mouseDragged(MouseEvent e) 
	{
		mouse = e;
	}

	public void mouseMoved(MouseEvent e) 
	{
		mouse = e;
	}
	
	
	//OTHER METHODS
	public int getRandomX()
	{
		return random.nextInt(WIDTH);
	}
	
	public int getRandomY()
	{
		return random.nextInt(HEIGHT);
	}
	
	public int getRandomNumber(int min, int max)
	{
		int num = random.nextInt(max);
		while(num < min)
		{
			num = random.nextInt(max);
		}
		return num;
	}
		
}
