import java.lang.Math.*;
import java.awt.Rectangle;
import java.awt.Point;

/**
 * 	@author Murilo Trigo
 *  @version 1.0a
 * 	@since 2016-01-26
 * 	<p>
 * 	Test - Will this be actually in the javadocs?
 */

public class Physics
{ 
	final private double TIME_UNIT = 1;
	private int screenWidth;
	private int screenHeight;
	private int margin;
	private Point middle;
	
	private Movable paddle1;
	private Movable paddle2;
	private Movable ball;
	
	private double ballSpeed;
	private double paddleSpeed;
	
	private int p1Score = 0,
				p2Score = 0;
	
	private int lastToScore = 0; // 0, 1 or 2 - TODO change to Enumerated Type
	
	private int angleWidth;

	/**
	 *  @return A int value within a given range that represents and angle in degrees
	 *  @accept A int value for how wide the angle deviation should be from 
	 * 			a straight horizontal toss.
	 */
	
	public int getReleaseAngle(int angle)
	{	
		return (int)(Math.random()*(2*angle + 1)) - angle;	// in degrees	
	}
	
	/**
	 * @return A UVector object that holds the value for the direction
	 * 
	 */
	
	public Vectr getReleaseVectr()
	{
		// First decide in which player's directio to release the ball
		int direction = 0;
		if (lastToScore == 0)
		{
			int coinFlip = (int)(Math.random() + 0.5);
			if (coinFlip == 0)
			{
				direction = 1; // to the right
			}
			else
			{
				direction = -1; // to the left
			}
		}
		
		else if (lastToScore == 1)
		{
			direction = -1; // to the left
		}
		
		else if (lastToScore == 2)
		{
			direction = 1; // to the right
		}
		
		else
		{
			System.out.println("ERROR: lastToScore value was invalid");
		}
		
		int releaseAngle = getReleaseAngle(angleWidth);
		
		return new Vectr(
						  direction*Math.cos(Math.toRadians(releaseAngle)), 
						  Math.sin(Math.toRadians(releaseAngle)) 
						);
	}
}

class Vectr
{
	public double x;
	public double y;
	
	Vectr()
	{
		x = 0;
		y = 0;
	}
	
	Vectr(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
}

class Movable extends Rectangle
{
	private Vectr direction;
	private double speed;

	Movable(Point position, int size, double speed)
	{
		super(position.x, position.y, size, size);
		direction = new Vectr(0, 0);
		this.speed = speed;
	}
	
	public void move(Vectr direction, double speed)
	{
		translate(
					(int)(direction.x*speed),
					(int)(direction.y*speed)
				 );
	}
}



