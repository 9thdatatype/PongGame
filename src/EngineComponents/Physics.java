
//package EngineComponents; DE-COMMENT LATER

import java.awt.Rectangle;
import java.awt.Point;
import java.awt.Dimension;

/**
 * 	@author Murilo Trigo
 *  @version 1.2.0a
 * 	@since 2016-01-26
 * 	<p>
 * 	A physics engine for a game of Pong
 */

public class Physics
{ 
	private int screenWidth;
	private int screenHeight;
	private int margin = 5;
	private Point screenCenter;
	
	private Ball ball;
	private double ballSpeed;

	private Rectangle paddleHurtbox;
	private Rectangle paddle1; //TODO Change to the new Paddle class that inherits from BounceSurface
	private Rectangle paddle2; //TODO Change to the new Paddle class that inherits from BounceSurface
	
	private int p1Score = 0,
				p2Score = 0;

	//TODO change to Enumerated Type?	
	private int lastToScore = 0; // must always be either 0, 1 or 2					
	private int angleWidth = 30; // possible value in degrees from the deviation from the horizontal
	
	//TODO Import these from constructor after the new constructor is ready
	private Point paddle1StartPos = new Point((int)(screenWidth /(double)4), (int)(screenHeight/(double)4)); // irrelevant for now
	private Point paddle2StartPos = new Point((int)(screenWidth*(double)3/4), (int)(screenHeight/(double)4)); // irrelevant for now
	
	//TODO Make this array safer. Lists perhaps.
	private Rectangle[] solids = new Rectangle[4]; // THIS SIZE MUST MATCH ALL THE SURFACES !!
	
	/**
	 * Class constructor
	 * @param screenWidth An int value
	 * @param screenHeight An int value
	 * @param ballSpeed An double value - pixels moved per update
	 * @param ballSize A int value - size of the side of the ball
	 * @param paddleSpeed An double value - pixels moved per update
	 * @param paddleSize An int value - only the height
	 */
	
	public Physics(int screenWidth, int screenHeight, double ballSpeed, 
				   int ballSize, double paddleSpeed, Dimension paddleDimensions)
	{
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;		
		//screenCenter = new Point(screenWidth/2 - ballSize/2, screenHeight/2 - ballSize/2);
		screenCenter = new Point(400, 400);
		this.ballSpeed = ballSpeed;
		ball = new Ball(screenCenter, ballSize);
		//paddleHurtbox =  new Rectangle(paddle1StartPos, paddleDimensions);
		
		BounceSurface topEdge = new BounceSurface( 
								new Rectangle(margin, margin, screenWidth - 2*margin, 1),  'y');
		BounceSurface bottomEdge = new BounceSurface( 
								   new Rectangle(screenHeight - margin, margin, screenWidth - 2*margin, 1),  'y');
		
		// TEST SURFACES
		BounceSurface leftEdge = new BounceSurface( 
							     new Rectangle(margin, margin, 1, screenHeight -2*margin),  'x');
		BounceSurface rightEdge = new BounceSurface( 
				   				  new Rectangle(screenWidth - margin, margin, 1, screenHeight -2*margin),  'x');
		
		// Ghost paddles to satisfy getters
		paddle1 = new Rectangle(0, 0, 0, 0);
		paddle2 = new Rectangle(0, 0, 0, 0);
		
		solids[0] = topEdge;
		solids[1] = bottomEdge;
		solids[2] = leftEdge;
		solids[3] = rightEdge;
	}
	
	//TODO New constructor that takes in all the necessary variables from one object
	
	public int getP1_x()
	{
		return paddle1.x;
	}
	
	public int getP1_y()
	{
		return paddle1.y;
	}
	
	public int getP2_x()
	{
		return paddle2.x;
	}
	
	public int getP2_y()
	{
		return paddle2.y;
	}

	public int getBall_x()
	{
		return ball.x;
	}
	
	public int getBall_y()
	{
		return ball.y;
	}
	
	public int getP1Score()
	{
		return p1Score;
	}

	public int getP2Score()
	{
		return p2Score;
	}

	private int getReleaseAngle(int variance) 
	{	
		double rawVariance = Math.random()*(2*variance +1);
		return (int)rawVariance - variance;
	}
	
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
			System.out.println("ERROR 05: lastToScore value was invalid");
		}
		
		if (direction != 1 && direction != -1) 
			System.out.println("ERROR 01: Direction chosen for release"
								+ " Vectr not corrently assigned");
		
		int releaseAngle = getReleaseAngle(angleWidth);
		
		return new Vectr(
						  direction*Math.cos(Math.toRadians(releaseAngle)), 
						  Math.sin(Math.toRadians(releaseAngle)) 
						);
	}
	
	private void reset(Ball ball)
	{
		ball.direction = getReleaseVectr();
		System.out.println("Ball direction is now (" + ball.direction.x + ", " + ball.direction.y + ")");
		ball.setLocation(screenCenter);
	}
	
	public void startSimulation()
	{
		reset(ball);
		p1Score = 0;
		p2Score = 0;
		lastToScore = 0;
	}
	
	private void incScore(int playerNum)
	{
		if (playerNum == 1)
		{
			p1Score++;
		}
		
		else if (playerNum == 2)
		{
			p2Score++;
		}
		
		else
		{
			System.out.println("ERROR 02: Wrong value was given to the incScore function.");
		}
	}
	
	private void checkCollision(Ball ballObj, Rectangle[] solids)
	{
		// get ready for poor's man polymorphism now
		
		for (Rectangle solid : solids)
		{
			if (ballObj.intersects(solid))
			{				
				if (solid instanceof ScoreSurface)
				{
					incScore(((ScoreSurface)solid).pointToPlayer);
				}
				
				else if (solid instanceof BounceSurface)
				{
					ballObj.revertAxis(((BounceSurface) solid).axisOfReflection);
					System.out.println("Hit a bouncable surface!");
				}
				
				else
				{
					System.out.println("ERROR 04: A solid in the solids array wasn't bouncable or scorble");
				}
			}
		}
	}
	
	public void updateStates() // CHANGE TO ACCOUNT FOR EVERY SINGLE PIXEL
	{
		for(int i = 0; i < ballSpeed; i++)
		{
			ball.move();
			checkCollision(ball, solids);
		}
	}
	
	public static int roundToInt(double value)
	{
		double result = value + 0.5;
		return ((int)(result*10))/10;
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
	public Vectr direction;
	public double xfloat;
	public double yfloat;

	Ball(Point position, int size)
	{
		super(position.x, position.y, size, size);
		xfloat = position.x;
		yfloat = position.y;
		direction = new Vectr(0, 0);
	}
	
	public void revertAxis(char axis)
	{
		if(axis == 'x')
		{
			direction.x *= -1;
		}
		
		else if (axis == 'y')
		{
			direction.y *= -1;
		}
		
		else
		{
			System.out.println("ERROR 03: Invalid axis provided for revertAxis functon");
		}
	}
	
	public void translate(double dx, double dy)
	{
		xfloat += dx;
		x = Physics.roundToInt(xfloat);
		
		yfloat += dy;
		y = Physics.roundToInt(yfloat);
	}
	
	public void move()
	{
		translate(direction.x, direction.y);
	}
}

@SuppressWarnings("serial")
class ScoreSurface extends Rectangle 
{
	public int pointToPlayer; // 1 or 2
	public double x = super.x;
	public double y = super.y;
	
	public ScoreSurface(Rectangle hurtbox, int pointToPlayer)
	{
		super(hurtbox);
		this.pointToPlayer = pointToPlayer;
	}
}

@SuppressWarnings("serial")
class BounceSurface extends Rectangle 
{	
	public boolean bounciness;
	public char axisOfReflection; // x or y
	public double x = super.x;
	public double y = super.y;
	
	public BounceSurface(Rectangle hurtbox, char axis)
	{
		super(hurtbox);
		this.axisOfReflection = axis;
	}
}