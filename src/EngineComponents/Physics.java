package EngineComponents;

import java.awt.Rectangle;
import java.awt.Point;
import java.awt.Dimension;

/**
 * 	@author Murilo Trigo
 *  @version 1.2.2a
 * 	@since 2016-01-26
 * 	<p>
 * 	A physics engine for a game of Pong
 */

public class Physics
{ 
	// Screen variables
	//private int screenWidth; - not in use
	//private int screenHeight; - not in use
	private Point screenCenter;
	
	// Game Objects
	private Ball ball;
	private Dimension paddleHitbox;
	private Paddle paddle1;
	private Paddle paddle2;
	
	private Rectangle[] solids = new Rectangle[6]; // THIS SIZE MUST MATCH ALL THE SURFACES !! TODO Make this array safer. Lists perhaps.
	
	// Game State Variables
	private int lastToScore = 0; // must always be either 0, 1 or 2 //TODO change to Enumerated Type?		
	private int p1Score = 0;
	private int p2Score = 0;	
	private int p1y = 0; // 1 for moving down, -1 for moving up and 0 for no movement.			
	private int p2y = 0; // 1 for moving down, -1 for moving up and 0 for no movement.
	
	// Game Ruleset Variables
	private int updatesPerRefresh; // Essentially the ball speed
	private int releaseAngleDelta = 89; // possible value in degrees from the deviation from the horizontal
	private double paddleSpeedFactor = 0.5;
	
	//TODO Import these from constructor after the new constructor is ready
	private Point paddle1StartPos = new Point(200, 200); 
	private Point paddle2StartPos = new Point(800, 200);
	
	/**
	 * Class constructor
	 * @param screenWidth Width of the gamespace in pixels
	 * @param screenHeight Height of the gamespace in pixels
	 * @param ballSize Size of the side of the ball in pixels
	 * @param paddleDimensions A Dimension object with the dimensions of the paddles in pixels
	 * @param updatesPerRefresh Perceived game speed
	 * @param paddleSpeedFactor Paddle's speed as a factor of the ball's.
	 * @param releaseAngleDelta Value of the delta of difference possible between the horizontal
	 * 			and the release angle
	 */
	
	public Physics(int screenWidth, int screenHeight, int ballSize, 
				   Dimension paddleDimensions, int updatesPerRefresh, 
				   double paddleSpeedFactor, int releaseAngleDelta)
	{
		//this.screenWidth = screenWidth;
		//this.screenHeight = screenHeight;		
		screenCenter = new Point(screenWidth/2 - ballSize/2, screenHeight/2 - ballSize/2);
		this.updatesPerRefresh = updatesPerRefresh;
		ball = new Ball(screenCenter, ballSize);
		paddleHitbox =  paddleDimensions;
		this.paddleSpeedFactor = paddleSpeedFactor;
		this.releaseAngleDelta = releaseAngleDelta;
		
		BounceSurface topEdge = new BounceSurface( 
								new Rectangle(0, 0, screenWidth, 1),  'y');
		BounceSurface bottomEdge = new BounceSurface( 
								   new Rectangle(0, screenHeight, screenWidth, 1),  'y');
		
		ScoreSurface leftEdge = new ScoreSurface( 
			     				new Rectangle(0, 0, 1, screenHeight),  2);
		ScoreSurface rightEdge = new ScoreSurface( 
  				  				 new Rectangle(screenWidth, 0, 1, screenHeight),  1);
		
		Rectangle test = new Rectangle(paddle1StartPos , paddleHitbox);
		Rectangle test2 = new Rectangle(paddle2StartPos, paddleHitbox);
		paddle1 = new Paddle(test, 'x');
		paddle2 = new Paddle(test2, 'x');
		
		// TEST SURFACES
		/*
		BounceSurface leftEdge = new BounceSurface( 
							     new Rectangle(0, 0, 1, screenHeight),  'x');
		BounceSurface rightEdge = new BounceSurface( 
				   				  new Rectangle(screenWidth, 0, 1, screenHeight),  'x');
		*/
		solids[0] = topEdge;
		solids[1] = bottomEdge;
		solids[2] = leftEdge;
		solids[3] = rightEdge;
		solids[4] = paddle1;
		solids[5] = paddle2;

	}
	
	/**
	 * Class constructor
	 * @param screenWidth Width of the gamespace in pixels
	 * @param screenHeight Height of the gamespace in pixels
	 * @param ballSize Size of the side of the ball in pixels
	 * @param paddleDimensions A Dimension object with the dimensions of the paddles in pixels
	 * @param updatesPerFrame Represents the perceived game speed
	 */
	
	public Physics(int screenWidth, int screenHeight, int ballSize, 
				   Dimension paddleDimensions, int updatesPerRefresh)
	{
		//this.screenWidth = screenWidth;
		//this.screenHeight = screenHeight;		
		screenCenter = new Point(screenWidth/2 - ballSize/2, screenHeight/2 - ballSize/2);
		this.updatesPerRefresh = updatesPerRefresh;
		ball = new Ball(screenCenter, ballSize);
		paddleHitbox =  paddleDimensions;
		
		BounceSurface topEdge = new BounceSurface( 
								new Rectangle(0, 0, screenWidth, 1),  'y');
		BounceSurface bottomEdge = new BounceSurface( 
								   new Rectangle(0, screenHeight, screenWidth, 1),  'y');
		
		ScoreSurface leftEdge = new ScoreSurface( 
			     				new Rectangle(0, 0, 1, screenHeight),  2);
		ScoreSurface rightEdge = new ScoreSurface( 
  				  				 new Rectangle(screenWidth, 0, 1, screenHeight),  1);
		
		Rectangle test = new Rectangle(paddle1StartPos , paddleHitbox);
		Rectangle test2 = new Rectangle(paddle2StartPos, paddleHitbox);
		paddle1 = new Paddle(test, 'x');
		paddle2 = new Paddle(test2, 'x');
		
		// TEST SURFACES
		/*
		BounceSurface leftEdge = new BounceSurface( 
							     new Rectangle(0, 0, 1, screenHeight),  'x');
		BounceSurface rightEdge = new BounceSurface( 
				   				  new Rectangle(screenWidth, 0, 1, screenHeight),  'x');
		*/
		solids[0] = topEdge;
		solids[1] = bottomEdge;
		solids[2] = leftEdge;
		solids[3] = rightEdge;
		solids[4] = paddle1;
		solids[5] = paddle2;

	}
	
	/**
	 * @return value of the x coordenate of the top-left corner of the player 1's paddle.
	 */
	
	public int getP1_x()
	{
		return paddle1.x;
	}
	
	/**
	 * @return value of the y coordenate of the top-left corner of the player 1's paddle.
	 */
	
	public int getP1_y()
	{
		return paddle1.y;
	}
	
	/**
	 * @return value of the x coordenate of the top-left corner of the player 2's paddle.
	 */
	
	public int getP2_x()
	{
		return paddle2.x;
	}
	
	/**
	 * @return value of the y coordenate of the top-left corner of the player 2's paddle.
	 */
	
	public int getP2_y()
	{
		return paddle2.y;
	}
	
	/**
	 * @return value of the x coordenate of the top-left corner of the ball.
	 */

	public int getBall_x()
	{
		return ball.x;
	}
	
	/**
	 * @return value of the y coordenate of the top-left corner of the ball.
	 */
	
	public int getBall_y()
	{
		return ball.y;
	}
	
	/**
	 * @return current value of the player 1's score
	 */
	
	public int getP1Score()
	{
		return p1Score;
	}

	/**
	 * @return current value of the player 2's score
	 */
	
	public int getP2Score()
	{
		return p2Score;
	}

	/**
	 * @param value the of the player 1's vertical direction 
	 * Possible values are:
	 * (+1) - downwards
	 * (-1) - upwards
	 * ( 0) - still
	 */
	
	public void setp1y(int value)
	{
		if (value == 1 || value == 0 || value == -1)
		{
			p1y = value;
		}
		else
		{
			System.out.println("ERROR 06: Value provided for setp1y was not valid [must be 0, 1 or -1]");
		}
	}
	
	/**
	 * @param value the of the player 2's vertical direction 
	 * Possible values are:
	 * (+1) - downwards
	 * (-1) - upwards
	 * ( 0) - still
	 */
	
	public void setp2y(int value)
	{
		if (value == 1 || value == 0 || value == -1)
		{
			p2y = value;
		}
		else
		{
			System.out.println("ERROR 06: Value provided for setp2y was not valid [must be 0, 1 or -1]");
		}
	}
		
	protected static int roundToInt(double value)
	{
		double result = value + 0.5;
		return ((int)(result*10))/10;
	}
	
	/**
	 * @param variance Value of the angle in degrees of the possible deviation 
	 * of the initial ball release angle away from the horizontal
	 * @return A positive or negative value for an angle in degrees. Possible
	 * values are limited to be within the variance range of the horizontal.
	 */
	
	private static int getReleaseAngle(int variance) 
	{	
		double rawVariance = Math.random()*(2*variance +1);
		return (int)rawVariance - variance;
	}
	
	/**
	 * The horizontal direction will always point to the last player 
	 * that scored a point, except on the first ball release, in which
	 * case that choice is randomly made
	 * @return A Vectr object with a possible direction vector
	 * within the pre-defined angle deviation from the horizontal 
	 */
	
	private Vectr getReleaseVectr()
	{
		// First it must decide in which player's directio to release the ball
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
		
		// Then, get the release angle
		int releaseAngle = getReleaseAngle(releaseAngleDelta);
		
		// And then return a Vectr object based on that angle
		return new Vectr(
						  direction*Math.cos(Math.toRadians(releaseAngle)), 
						  Math.sin(Math.toRadians(releaseAngle)) 
						);
	}
	
	/**
	 * Reset the ball object's position to the defined
	 * middle of the screen and re-assigns a constrained
	 * randomized release direction to it. 
	 */
	
	private void reset(Ball ball)
	{
		ball.setXfloat(screenCenter.x);
		ball.setYfloat(screenCenter.y);
		ball.direction = getReleaseVectr();
	}
	
	/**
	 * Set the initial values for a game simulation
	 * and calls the reset method for the ball object.
	 */
	
	public void startSimulation()
	{
		reset(ball);
		p1Score = 0;
		p2Score = 0;
		lastToScore = 0;
	}
	
	/**
	 * Method used to increment the score of a player
	 * @param The value passed for this argument should always be
	 * either 1 (for player 1) or 2 (for player 2). An error message
	 * is displayed otherwise. This method is supposed to be called 
	 * as a consequence of a collision with a ScoreSurface object.
	 */
	
	private void incScore(int playerNum)
	{
		if (playerNum == 1)
		{
			p1Score++;
			lastToScore = 1;
		}
		
		else if (playerNum == 2)
		{
			p2Score++;
			lastToScore = 2;
		}
		
		else
		{
			System.out.println("ERROR 02: Wrong value was given to the incScore function.");
		}
	}
	
	/**
	 * Every time it's called, this method will check the if a collision between
	 * the provided ball object and every object in the provided array has occured.
	 * If a colision did occur, then the appropriate action is taken.
	 * @param ballObj The ball object being used in the game simulation
	 * @param solids An array containg all the surfaces that the ball should
	 * have an interaction with. These objects should be either a ScoreSurface
	 * object, a BounceSurface object or a subclass of either. !! The array should
	 * also always be COMPLETELY FULL. Otherwise an run-time error will occur !!
	 */
	
	private void checkCollision(Ball ballObj, Rectangle[] solids)
	{
		// POOR'S MAN POLYMORPHISM ACTION - funny - Jonathan
		
		for (Rectangle solid : solids)
		{
			if (ballObj.intersects(solid))
			{				
				if (solid instanceof ScoreSurface)
				{
			
					incScore(((ScoreSurface)solid).pointToPlayer);
					reset(ball);
				}
				
				else if (solid instanceof BounceSurface)
				{	
					if (solid instanceof Paddle)
					{
						ballObj.revertAxis(((Paddle)solid).axisOfReflection);
					}
					else
					{
						ballObj.revertAxis(((BounceSurface)solid).axisOfReflection);
					}
							
				}
				
				else
				{
					System.out.println("ERROR 04: A solid in the solids array wasn't bouncable or scorble");
				}
			}
		}
	}
	
	/**
	 * Runs the simulation as many times as currently set
	 * by a game variable and updates the state of all game 
	 * objects and variables.
	 */
	
	public void updateStates() 
	{
		for(int i = 0; i < updatesPerRefresh; i++)
		{
			paddle1.direction.y = p1y*paddleSpeedFactor;
			paddle2.direction.y = p2y*paddleSpeedFactor;
			paddle1.move();
			paddle2.move();
			ball.move();
			checkCollision(ball, solids);
			
			//System.out.println("P1: " + p1Score + " P2: " + p2Score);
		}
	}
}

/**
 * Class that represents a vector with double precision. Used
 * for direction calculations. 
 */

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

/**
 * Class for the ball object used in the
 * simulation. 
 */

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
	
	public void setXfloat(double value)
	{
		xfloat = value;
		x = Physics.roundToInt(xfloat);
		
	}

	public void setYfloat(double value)
	{
		yfloat = value;
		y = Physics.roundToInt(yfloat);
	}
	
	/**
	 * Reverses the direction of the current velocity of
	 * the ball object in one of its axis.
	 * @param axis Either 'x' or 'y'. An error message is given
	 * otherwise.
	 */
	
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
	
	public String toString()
	{
		return "(" + direction.x + ", " + direction.y +")";
	}
}

@SuppressWarnings("serial")
class Surface extends Rectangle
{
	public double xfloat;
	public double yfloat;
	
	public Surface(Rectangle hitbox)
	{
		super(hitbox);
		xfloat = hitbox.x;
		yfloat = hitbox.y;
	}

	public double getXfloat()
	{
		return xfloat;
	}

	public double getYfloat()
	{
		return yfloat;
	}

	public void setXfloat(double value)
	{
		xfloat = value;
		x = Physics.roundToInt(xfloat);
		
	}

	public void setYfloat(double value)
	{
		yfloat = value;
		y = Physics.roundToInt(yfloat);
	}
}

@SuppressWarnings("serial")
class ScoreSurface extends Surface 
{
	public int pointToPlayer; // 1 or 2
	
	public ScoreSurface(Rectangle hitbox, int pointToPlayer)
	{
		super(hitbox);
		setXfloat(hitbox.x);
		setYfloat(hitbox.y);
		this.pointToPlayer = pointToPlayer;
	}
}

@SuppressWarnings("serial")
class BounceSurface extends Surface 
{	
	public char axisOfReflection; // x or y
	
	public BounceSurface(Rectangle hitbox, char axis)
	{
		super(hitbox);
		setXfloat(hitbox.x);
		setYfloat(hitbox.y);
		this.axisOfReflection = axis;
	}
}

@SuppressWarnings("serial")
class Paddle extends BounceSurface
{
	public Vectr direction;
	
	public Paddle(Rectangle hitbox, char axis)
	{
		super(hitbox, axis);
		direction = new Vectr(0, 0);
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
		translate(0, direction.y);
	}
	
}