package game;

import java.util.ArrayList;

public class Ship extends VectorMover
{
	private final int MAX_SPEED = 10, ROF = 10;
	private int fire, invuln, lives;
	private boolean transparent;
	private GameSound shoot, hit, engine;
	
	public Ship()
	{
		actor = "Ship";
		setImage("ship.png");
		image.rotateSource(90);
		fire = ROF; invuln = 100; lives = 5;
		transparent = false;
		
		shoot = new GameSound("shoot.aiff");
		hit = new GameSound("hit.wav");
		engine = new GameSound("engine.wav");
	}
	
	public void act()
	{
		keys();
		wrap();
		move();
		damage();
	}
	
	private void keys()
	{
		if(world.isKeyPressed("up"))
		{
			acel();
			engine.play();
			pixelate();
		}
		if(world.isKeyPressed("right"))
		{
			rotate(5);
		}
		if(world.isKeyPressed("left"))
		{
			rotate(-5);
		}
		if(world.isKeyPressed("space"))
		{
			shoot();
		}
	}
	
	private void acel()
	{
		addForce(new Vector(direction, 0.1));
		if(getSpeed()  >= MAX_SPEED)
		{
			getMovement().setLength(MAX_SPEED);
		}
	}
	
	private void pixelate()
	{
		GameWorld gw = (GameWorld)world;
		int ranD = 0;
		if(world.getRandomNumber(0, 2) == 0)
		{
			ranD = direction + world.getRandomNumber(0, 15) + 180;
		}
		else
		{
			ranD = direction - world.getRandomNumber(0, 15) + 180;
		}
		gw.addPixel(false, ranD, X + getBounds().width/2, Y + getBounds().height/2);
	}
	
	private void explode()
	{
		GameWorld gw = (GameWorld)world;
		int ranD; int particles = 150;
		for(int i = 0; i<particles;i++)
		{
			ranD = world.getRandomNumber(0,360);
			gw.addPixel(false, ranD, X, Y);
		}
	}
	
	private void wrap()
	{
		String loc = atWorldBounds(60);
		if(loc.equals("TOP"))
		{
			setLocation(X,world.HEIGHT);
		}
		else if(loc.equals("BOTTOM"))
		{
			setLocation(X,0);
		}
		else if(loc.equals("RIGHT"))
		{
			setLocation(world.WIDTH,Y);
		}
		else if(loc.equals("LEFT"))
		{
			setLocation(0,Y);
		}
	}
	
	private void shoot()
	{
		fire++;
		if(fire >= ROF)
		{
			shoot.play();
			world.addActor(new Shot(direction), X+getImage().getWidth()/2,Y+getImage().getHeight()/2);
			fire = 0;
		}
	}
	
	private void damage()
	{
		if(invuln >= 100)
		{
			Asteroid a = (Asteroid)getIntersectingActor("Asteroid");
			if(a != null)
			{
				a.impact();
				lives--;
				GameWorld gw = (GameWorld)world;
				gw.changeLives(lives);
				invuln = 0;
			}
		}
		else
		{
			flash();
			invuln++;
		}
		
		if(lives <= 0)
		{
			destroy();
		}
	}
	
	private void flash()
	{
		if(invuln % 10 == 0 && !transparent)
		{
			transparent = true;
			image.setTransparency(0.5f, direction);
		}
		else if(invuln % 10 == 0 && transparent)
		{
			transparent = false;
			image.setTransparency(1.0f, direction);
		}
		
		if(invuln % 33 == 0)
		{
			hit.play();
		}
	}
	
	private void destroy()
	{
		explode();
		detonate();
		GameWorld gw = (GameWorld)world;
		gw.endGame();
		world.removeActor(this);
	}
	
	private void detonate()
	{
		ArrayList<Actor> asts = getActors("Asteroid");
		for(Actor a : asts)
		{
			Asteroid ast = (Asteroid)a;
			ast.impact();
		}
	}
}
