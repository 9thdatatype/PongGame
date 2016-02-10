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
		this.x = 0;
		this.y = 0;
	}
	
	public Point(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Point(Point cloneable)
	{
		this.x = cloneable.x;
		this.y = cloneable.y;
	}
	
	public String toString()
	{
		return "(" + this.x + ", " + this.y + ")";
	}

	public Point add(double dx, double dy)
	{
		return new Point(this.x + dx, this.y + dy);
	}
	
	public Point add(Point P)
	{
		return new Point(this.x + P.x, this.y + P.y);
	}
	
	public boolean equals(Point P)
	{
		if (this.x == P.x && this.y == P.y)
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
		return P.x - this.x;
	}
	
	public double yDist (Point P)
	{
		return P.y - this.y;
	}
	
	public double distance(Point P)
	{
		return Math.sqrt(Math.pow(this.xDist(P), 2) + Math.pow(this.yDist(P), 2));
	}
	
	public boolean isContained(Rectangle R)
	{
		if (this.x < R.tlc.x || this.x > R.brc.x)
		{
			return false;
		}
		
		if (this.y < R.tlc.y || this.y > R.brc.y)
		{
			return false;
		}
		
		return true;
	}
}
