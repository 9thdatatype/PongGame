/**
 * Program Name: Solid.java
 * Purpose: Here lies a purpose.
 * Coded by: Trigo, Murilo
 * Date: Feb 9, 2016 
 */
package geometry;

public class Object2D extends Rectangle
{
	public double speed;
	public double direction; // angle in degrees

	public Object2D(Rectangle R)
	{
		super(R);
		direction = 0;
		speed = 0;
	}
	
	public Object2D(Object2D cloneable)
	{
		this(new Rectangle(cloneable.tlc, cloneable.brc), cloneable.speed, cloneable.direction);
	}
	
	public Object2D(double tlcx, double tlcy, double brcx, double brcy)
	{
		this(new Rectangle(tlcx, tlcy, brcx, brcy));
	}
	
	public Object2D(Rectangle R, double spd)
	{
		this(R);
		speed = spd;
	}
	
	public Object2D(Rectangle R, double spd, double dir)
	{
		this(R, spd);
		direction = dir;
	}
	
	public Object2D(Point center, double w, double h)
	{
		this(new Rectangle(center, w, h));
	}
	
	public String toString()
	{
		return super.toString() + "\nspeed: " + speed 
				+ "\ndirection: " + direction;
	}

	public boolean superposes(Object2D O)
	{
		return super.equals((Rectangle)O);
	}
	
	public boolean equals(Object2D O)
	{
		boolean superposes = superposes(O);
		
		return (speed == O.speed) 
				&& (direction == O.direction)
				&& superposes;
	}
	
	public double xDir()
	{
		return Math.cos(Math.toRadians(direction));
	}
	
	public double yDir()
	{
		return Math.sin(Math.toRadians(direction));
	}
	
	public void reflectHorizontal()
	{
		direction = Math.toDegrees(Math.atan2(yDir(), -xDir()));
	}
	
	public void reflectVertical()
	{
		direction = Math.toDegrees(Math.atan2(-yDir(), xDir())); 
	}
	
	public Rectangle top()
	{
		return new Rectangle(tlc, tlc.add(width, 1));
	}
	
	public Rectangle left()
	{
		return new Rectangle(tlc, tlc.add(1, height));
	}
	
	public Rectangle bottom()
	{
		return new Rectangle(brc.add(-width, -1), brc);
	}
	
	public Rectangle right()
	{
		return new Rectangle(brc.add(-1, -height), brc);
	}
	
	public Rectangle[] sides()
	{
		Rectangle[] sides = {top(), left(), bottom(), right()};
		return sides;
	}
	
	public Point move()
	{
		translate(xDir()*speed, yDir()*speed);
		return center;
	}
}