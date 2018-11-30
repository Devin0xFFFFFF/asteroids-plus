package game;

public class Shot extends VectorMover
{
	public Shot(int direction)
	{
		actor = "Shot";
		setImage("shot.png");
		image.rotateSource(90);
		addForce(new Vector(direction,10));
		setRotation(direction);
	}
	
	public void act()
	{
		collisions();
		move();
	}
	
	private void collisions()
	{
		Asteroid a = (Asteroid)getIntersectingActor("Asteroid");
		if(a != null)
		{
			a.impact();
			destroy();
		}
		else if(atEdge())
		{
			destroy();
		}
	}
	
	private boolean atEdge()
	{
		return rightEdge() >= world.WIDTH+50 || leftEdge() <= -50 
		|| topEdge() <= -50 || bottomEdge() >= world.HEIGHT + 50;
	}
	
	private void destroy()
	{
		world.removeActor(this);
	}
}
