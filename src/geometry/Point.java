/**
 * Program Name: Point.java
 * Purpose: Here lies a purpose.
 * Coded by: Trigo, Murilo
 * Date: Feb 9, 2016 
 */
package geometry;

public class Point
{
	public double x;
	public double y;
	
	public Point()
	{
		x = 0;
		y = 0;
	}
	
	public Point(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Point(Point cloneable)
	{
		x = cloneable.x;
		y = cloneable.y;
	}
	
	public int getX()
	{
		return (int)Math.round(x);
	}
	
	public int getY()
	{
		return (int)Math.round(y);
	}
	
	public String toString()
	{
		return "(" + x + ", " + y + ")";
	}

	public Point add(double dx, double dy)
	{
		return new Point(x + dx, y + dy);
	}
	
	public Point add(Point P)
	{
		return new Point(x + P.x, y + P.y);
	}
	
	public boolean equals(Point P)
	{
		if (x == P.x && y == P.y)
		{
			return true;
		}
		
		return false;
	}
	
	public void translate(double dx, double dy)
	{
		x += dx;
		y += dy;
	}
	
	public void moveTo(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	public double xDist(Point P)
	{
		return P.x - x;
	}
	
	public double yDist (Point P)
	{
		return P.y - y;
	}
	
	public double distance(Point P)
	{
		return Math.sqrt(Math.pow(xDist(P), 2) + Math.pow(yDist(P), 2));
	}
	
	public boolean isContained(Rectangle R)
	{
		if (x < R.tlc.x || x > R.brc.x)
		{
			return false;
		}
		
		if (y < R.tlc.y || y > R.brc.y)
		{
			return false;
		}
		
		return true;
	}
}