import static java.lang.Math.*;

/* *	Physics.java 
 *	Calculates the positions and movements of the player 1 
 *	and player 2 paddles as well as of the pong ball.
 *
 *	Last Rev. Date: 2016-02-02*/

/**
 * 	@author Murilo Trigo
 *  @version 1.0a
 * 	@since 2016-01-26
 */

public class Physics
{ 
	final private static double TIME_UNIT = 2;
	final private static double[] SCREEN_DIMENSIONS = {1000, 10000};
	final private static double MARGIN = 5;
	final private static double[] NET_CENTER = {SCREEN_DIMENSIONS[0]/2, SCREEN_DIMENSIONS[0]/2};
	final private static double[] PADDLE_DIMENSIONS = new double[2];
	final private static double[] BALL_DIMENSIONS = new double[2];
	final private static double BALL_SPEED = 5,
	  							PADDLE_SPEED = 100;

	private static int p1Score = 0,
					   p2Score = 0;
	
	private static int lastToScore = 0; // 0, 1 or 2 - TODO change to Enumerated Type
	
	private static double[] ballPos = new double[2],
							p1Pos = new double[2], 
							p2Pos = new double[2];
	
	private static double[] ballVelocity = new double[2],
						    p1Velocity = new double[2],
						    p2Velocity = new double[2];
	
	public static int getP1Score()
	{
		return p1Score;
	}

	public static int getP2Score()
	{
		return p2Score;
	}

	public static double[] getBallPos()
	{
		return ballPos;
	}

	public static double[] getP1Pos()
	{
		return p1Pos;
	}

	public static double[] getP2Pos()
	{
		return p2Pos;
	}
	
	public static String getBallPosString()
	{
		return "(" + ballPos[0] + ", " + ballPos[1] +")";
	}
	

	/**
	 * 	@return a random int value within the range
	 * 	of plus or minus a given range. The returned value is an angle in degrees.
	 * 	Accepts nothing.
	 */
	
	public static int getReleaseAngle()
	{	
		final int MAX_ANGLE = 30; // in degrees		
		return (int)(random()*(2*MAX_ANGLE + 1)) - MAX_ANGLE;	
	}
	
	public static double[] getReleaseVelocity()
	{
		// First decide in which player's directio to release the ball
		int direction = 0;
		if (lastToScore == 0)
		{
			int coinFlip = (int)(random() + 0.5);
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
		
		int releaseAngle = getReleaseAngle();
		double[] releaseVelocity = {
									direction*cos(toRadians(releaseAngle))*BALL_SPEED, 
									sin(toRadians(releaseAngle))*BALL_SPEED 
								   };
		return releaseVelocity;
	}
	
	public static void updateBallPos()
	{
		double[] dPos = {ballVelocity[0]*TIME_UNIT , ballVelocity[1]*TIME_UNIT};
		ballPos[0] = ballPos[0] + dPos[0];
		ballPos[1] = ballPos[1] + dPos[1];	
	}
	
	public static void releaseBall()
	{
		ballPos[0] = NET_CENTER[0];
		ballPos[1] = NET_CENTER[1];
		ballVelocity = getReleaseVelocity();
	}
}



