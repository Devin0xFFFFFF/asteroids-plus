package game;

public class VectorMover extends Actor
{
	private Vector movement;
    private double exactX;
    private double exactY;

    public VectorMover()
    {
        this(new Vector());
    }

    /**
     * Create new thing initialized with given speed.
     */
    public VectorMover(Vector movement)
    {
        this.movement = movement;
    }

    /**
     * Move in the current movement direction.
     */
    public void move() 
    {
        exactX = exactX + movement.getX();
        exactY = exactY + movement.getY();

        super.setLocation((int) exactX, (int) exactY);
    }

    public boolean atWorldEdge()
    {
        return exactX >= world.getWidth() || exactX < 0
        || exactY >= world.getHeight() || exactY < 0;        
    }
    
    public boolean atRightEdge()
    {
        return exactX >= world.getWidth();     
    }

    public boolean atLeftEdge()
    {
        return exactX < 0;
    }

    /**
     * Set the location using exact (double) co-ordinates.
     */
    public void setLocation(double x, double y) 
    {
        exactX = x;
        exactY = y;
        super.setLocation((int) x, (int) y);
    }

    /**
     * Set the location of this actor. Redefinition of the standard Greenfoot 
     * method to make sure the exact co-ordinates are updated in sync.
     */
    public void setLocation(int x, int y) 
    {
        exactX = x;
        exactY = y;
        super.setLocation(x, y);
    }

    /**
     * Return the exact x co-ordinate (as a double).
     */
    public double getExactX() 
    {
        return exactX;
    }

    /**
     * Return the exact y co-ordinate (as a double).
     */
    public double getExactY() 
    {
        return exactY;
    }

    /**
     * Modify the current movement by adding a new vector to the existing movement.
     */
    public void addForce(Vector force) 
    {
        movement.add(force);
    }

    /**
     * Accelerate the speed of this mover by the given factor. (Factors less than 1 will
     * decelerate.) The direction remains unchanged.
     */
    public void accelerate(double factor)
    {
        movement.scale(factor);
        if (movement.getLength() < 0.15) {
            movement.setNeutral();
        }
    }

    /**
     * Return the speed of this actor.
     */
    public double getSpeed()
    {
        return movement.getLength();
    }

    /**
     * Return the current movement of this object (as a vector).
     */
    public Vector getMovement() 
    {
        return movement;
    }
}
