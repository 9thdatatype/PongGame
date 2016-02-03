import java.awt.Rectangle;
import java.awt.Point;
import java.awt.Dimension;

/**
 * 	@author Murilo Trigo
 *  @version 1.7a
 * 	@since 2016-01-26
 * 	<p>
 * 	Physics engine for a game of Pong
 */

public class Physics
{ 
	private int screenWidth;
	private int screenHeight;
	private int margin = 5;
	private Point screenCenter;
	
	private Rectangle paddle1;
	private Rectangle paddle2;
	private Ball ball;
	
	private int p1Score = 0,
				p2Score = 0;

	private int lastToScore = 0; // 0, 1 or 2 - TODO change to Enumerated Type	
	private int angleWidth = 30;

	private Point paddle1StartPos = new Point((int)(screenWidth /(double)4), (int)(screenHeight/(double)4));
	private Point paddle2StartPos = new Point((int)(screenWidth*(double)3/4), (int)(screenHeight/(double)4));
	
	private Rectangle[] solids = new Rectangle[7]; 
	
	/* [0] null
	 * [1] top edge
	 * [2] right paddle
	 * [3] bottom edge
	 * [4] left paddle
	 * [5] left edge - player 2 score zone
	 * [6] right edge - player 1 score zone 
	 */
	
	/**
	 * Class constructor
	 * @param screenWidth An int value
	 * @param screenHeight An int value
	 * @param margin Int value - all around margin of the edge of the screen
	 * @param ballSpeed An double value - pixels moved per frame
	 * @param ballSize A int value - size of the side of the ball
	 * @param paddleSpeed An double value - pixels moved per frame
	 * @param paddleSize An int value - only the height
	 */
	
	public Physics(int screenWidth, int screenHeight, double ballSpeed, 
				   int ballSize, double paddleSpeed, int paddleSize)
	{
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		
		screenCenter = new Point(screenWidth/2 - ballSize/2, screenHeight/2 - ballSize/2);
		ball = new Ball(screenCenter, ballSize, ballSpeed);
		
		Rectangle empty = new Rectangle(0, 0, 0, 0);
		Rectangle topEdge = new Rectangle(margin, margin, screenWidth, 1);
		Rectangle bottomEdge = new Rectangle(screenHeight - margin, margin, screenWidth, 1);
		Rectangle paddle1 = new Rectangle(paddle1StartPos, new Dimension(1, paddleSize)); // left paddle
		Rectangle paddle2 = new Rectangle(paddle2StartPos, new Dimension(1, paddleSize)); // right paddle
		Rectangle leftEdge = new Rectangle (margin, margin, 1, screenHeight);
		Rectangle rightEdge = new Rectangle (screenWidth - margin, margin, 1, screenHeight);
		
		solids[0] = empty; 
		solids[1] = topEdge;
		solids[2] = paddle2;
		solids[3] = bottomEdge;
		solids[4] = paddle1;
		solids[5] = leftEdge;
		solids[6] = rightEdge;
	}
	
	public Point getPaddle1()
	{
		return paddle1.getLocation();
	}

	public Point getPaddle2Pos()
	{
		return paddle2.getLocation();
	}

	public Point getBallPos()
	{
		return ball.getLocation();
	}

	public int getP1Score()
	{
		return p1Score;
	}

	public int getP2Score()
	{
		return p2Score;
	}

	/**
	 *  A int value for how wide the angle deviation should be from a straight
	 *  	when relesing the ball from the middle horizontal toss.
	 *  @return A int value within a given range that represents and angle in degrees
	 */
	
	private int getReleaseAngle(int angle)
	{	
		return (int)(Math.random()*(2*angle + 1)) - angle;	// in degrees	
	}
	
	/**
	 * @return A Vectr object that holds the value for the relase direction of the ball
	 */
	
	private Vectr getReleaseVectr()
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
	
	/**
	 * Resets the ball to the center of the screen and gives it
	 * a random start direction within a pre-defined margin 
	 */
	
	private void reset(Ball ball)
	{
		ball.setDirection(getReleaseVectr());
		ball.setLocation(screenCenter);
	}
	
	/**
	 * Call to start the Physics simulation
	 */
	
	public void startSimulation()
	{
		reset(ball);
		p1Score = 0;
		p2Score = 0;
		lastToScore = 0;
	}
	
	
	/**
	 * @param The ball object and an array with all game's solid objects
	 * 			in a particular order.
	 * @return an int value indicating either that no collision has occurred 
	 * 			(zero) or indicating the array index of the object with which a 
	 * 			collision has occurred.
	 */
	
	private int checkCollision(Ball ball, Rectangle[] solidsArray)
	{
		for (int i = 0; i < solidsArray.length; i++)
		{
			if (ball.intersects(solidsArray[i]))
			{
				return i;
			}
		}
		return 0; // no collision occurred
	}
	
	/**
	 * @param The ball object and an array with all game's solid objects
	 * 			in a particular order.
	 * 	Checks for a collision or lack thereof and decides how to update
	 * 	the game objects.
	 */
	
	private void bounce(Ball ball, Rectangle[] solidsArray)
	{
		int collision = checkCollision(ball, solidsArray);
		
		if (collision != 0)
		{
			switch (collision)
			{
				case 1: // top edge
				case 3: // bottom edge
					ball.getDirection().y *= -1;
					break; 
				case 2: // right paddle
				case 4: // left paddle
					ball.getDirection().x *= -1;
					break;
				case 5: // left edge
					p2Score++;
					lastToScore = 2;
					reset(ball);
					break;
				case 6: // right edge
					p1Score++;
					lastToScore = 1;
					reset(ball);
					break;
			}
		}
	}
	
	/**
	 * Updates the state of the game - call this each game frame.
	 */
	
	public void updateStates()
	{
		ball.move();
		bounce(ball, solids);
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

@SuppressWarnings("serial")
class Ball extends Rectangle
{
	private Vectr direction;
	private double speed;

	Ball(Point position, int size, double speed)
	{
		super(position.x, position.y, size, size);
		direction = new Vectr(0, 0);
		this.speed = speed;
	}
	
	public Vectr getDirection()
	{
		return direction;
	}

	public void setDirection(Vectr direction)
	{
		this.direction = direction;
	}

	public double getSpeed()
	{
		return speed;
	}

	/**
	 * Moves the ball according to a pre-established speed in
	 * conjunction with the current velocity direction.
	 */
	
	public void move()
	{
		translate((int)(direction.x*speed), (int)(direction.y*speed));
	}	
}
