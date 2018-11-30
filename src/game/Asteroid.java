package game;

public class Asteroid extends VectorMover
{
	private int level, width, height, edgeCounter, COUNTER_LIMIT;
	private  static GameSound e1 = new GameSound("boom_1.aiff"),e2 = new GameSound("boom_2.aiff"),e3 = new GameSound("boom_3.aiff");
	
	public Asteroid(int level, int w, int h, int d, int f, int imgNum)
	{
		actor = "Asteroid";
		
		this.level = level;
		this.width = w; this.height = h;
		
		edgeCounter = 0; COUNTER_LIMIT = 300;
		
		setImage("asteroid_"+imgNum+".png");
		image.scaleSource(width,height);
		
		setRotation(d);
		addForce(new Vector(direction,f));
	}
	
	public void act()
	{
		wrap();
		move();
	}
	
	private void wrap()
	{
		if(atWorldEdge())
		{
			edgeCounter++;
		}
		else
		{
			edgeCounter = 0;
		}
		
		if(edgeCounter >= COUNTER_LIMIT)
		{
			destroy();
			edgeCounter = 0;
		}
		
		String loc = atWorldBounds(width + 20*level);
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
	
	public void impact()
	{
		GameWorld gw = (GameWorld)world;
		pixelate();
		level--;
		if(level <= 0)
		{
			gw.changeScore(1);
			destroy();
		}
		else
		{
			gw.changeScore(1);
			spawnChildren(world.getRandomNumber(1,4));
			destroy();
		}
	}
	
	private void spawnChildren(int amount)
	{
		int ranD, ranF, ranI;
		for(int i=0;i<=amount;i++)
		{
			ranD = world.getRandomNumber(0,360);
			ranF = world.getRandomNumber(3,5);
			ranI = world.getRandomNumber(1,4);
			world.addActor(new Asteroid(level,width/2,height/2, ranD, ranF, ranI), X, Y);
		}
	}
	
	private void pixelate()
	{
		GameWorld gw = (GameWorld)world;
		int ranD; int particles = world.getRandomNumber(4, 10)*level;
		for(int i = 0; i<particles;i++)
		{
			ranD = world.getRandomNumber(0,360);
			gw.addPixel(true, ranD, X, Y);
		}
	}
	
	public void destroy()
	{
		int ranS = world.getRandomNumber(1,4);
		switch(ranS)
		{
			case 1 : e1.play();
			break;
			case 2 : e2.play();
			break;
			case 3 : e3.play();
		}
		world.removeActor(this);
	}
}
