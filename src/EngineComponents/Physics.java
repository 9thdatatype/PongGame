package EngineComponents;

import java.awt.Rectangle;
import java.awt.Point;
import java.awt.Dimension;

/**
 * 	@author Murilo Trigo
 *  @version 1.2.1a
 * 	@since 2016-01-26
 * 	<p>
 * 	A physics engine for a game of Pong
 */

public class Physics
{ 
	// Screen variables
	private int screenWidth;
	private int screenHeight;
	private int margin = 5;
	private Point screenCenter;
	
	// Game Objects
	private Ball ball;
	private Rectangle paddleHurtbox;
	private Rectangle paddle1; //TODO Change to the new Paddle class that inherits from BounceSurface
	private Rectangle paddle2; //TODO Change to the new Paddle class that inherits from BounceSurface

	//TODO Make this array safer. Lists perhaps.
	private Rectangle[] solids = new Rectangle[4]; // THIS SIZE MUST MATCH ALL THE SURFACES !!
	
	// Game State Variables
	private int lastToScore = 0; // must always be either 0, 1 or 2 //TODO change to Enumerated Type?		
	private int p1Score = 0;
	private int p2Score = 0;	
				
	// Game Ruleset Variables
	// private double ballSpeed; - Not in use currently 
	//TODO Set a variable for the relationship between the ball speed and the paddle speed
	private int updatesPerFrame;
	private int angleWidth = 30; // possible value in degrees from the deviation from the horizontal
	
	//TODO Import these from constructor after the new constructor is ready
	private Point paddle1StartPos = new Point((int)(screenWidth /(double)4), (int)(screenHeight/(double)4)); // irrelevant for now
	private Point paddle2StartPos = new Point((int)(screenWidth*(double)3/4), (int)(screenHeight/(double)4)); // irrelevant for now
	
	/**
	 * Class constructor
	 * @param screenWidth Width of the gamespace in pixels
	 * @param screenHeight Height of the gamespace in pixels
	 * @param ballSize Size of the side of the ball in pixels
	 * @param paddleDimensions A Dimension object with the dimensions of the paddles in pixels
	 * @param updatesPerFrame Represents the perceived game speed
	 */
	
	public Physics(int screenWidth, int screenHeight, int ballSize, 
				   Dimension paddleDimensions, int updatesPerFrame)
	{
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;		
		screenCenter = new Point(screenWidth/2 - ballSize/2, screenHeight/2 - ballSize/2);
		this.updatesPerFrame = updatesPerFrame;
		ball = new Ball(screenCenter, ballSize);
		//paddleHurtbox =  new Rectangle(paddle1StartPos, paddleDimensions);
		
		BounceSurface topEdge = new BounceSurface( 
								new Rectangle(0, margin, screenWidth, 1),  'y');
		BounceSurface bottomEdge = new BounceSurface( 
								   new Rectangle(0, screenHeight - margin, screenWidth, 1),  'y');
		
		// TEST SURFACES
		BounceSurface leftEdge = new BounceSurface( 
							     new Rectangle(margin, 0, 1, screenHeight),  'x');
		BounceSurface rightEdge = new BounceSurface( 
				   				  new Rectangle(screenWidth - margin, 0, 1, screenHeight),  'x');
		
		// Ghost paddles to satisfy getters
		paddle1 = new Rectangle(0, 0, 0, 0);
		paddle2 = new Rectangle(0, 0, 0, 0);
		
		solids[0] = topEdge;
		solids[1] = bottomEdge;
		solids[2] = leftEdge;
		solids[3] = rightEdge;
	}
	
	//TODO New constructor that takes in all the necessary variables from one single object
	
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

	public static int roundToInt(double value)
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
		int releaseAngle = getReleaseAngle(angleWidth);
		
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
		ball.direction = getReleaseVectr();
		ball.setLocation(screenCenter);
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
				}
				
				else if (solid instanceof BounceSurface)
				{	
					ballObj.revertAxis(((BounceSurface) solid).axisOfReflection);			
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
		for(int i = 0; i < updatesPerFrame; i++)
		{
			ball.move();
			checkCollision(ball, solids);
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

class Surface extends Rectangle
{
	private double xfloat;
	private double yfloat;
	
	public Surface(Rectangle hurtbox)
	{
		super(hurtbox);
		xfloat = hurtbox.x;
		yfloat = hurtbox.y;
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
	
	public ScoreSurface(Rectangle hurtbox, int pointToPlayer)
	{
		super(hurtbox);
		setXfloat(hurtbox.x);
		setYfloat(hurtbox.y);
		this.pointToPlayer = pointToPlayer;
	}
}

@SuppressWarnings("serial")
class BounceSurface extends Surface 
{	
	public char axisOfReflection; // x or y
	
	public BounceSurface(Rectangle hurtbox, char axis)
	{
		super(hurtbox);
		setXfloat(hurtbox.x);
		setYfloat(hurtbox.y);
		this.axisOfReflection = axis;
	}
}