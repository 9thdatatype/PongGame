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
		this.direction = 0;
		this.speed = 0;
	}
	
	public Object2D(double tlcx, double tlcy, double brcx, double brcy)
	{
		this(new Rectangle(tlcx, tlcy, brcx, brcy));
	}
	
	public Object2D(Rectangle R, double speed)
	{
		this(R);
		this.speed = speed;
	}
	
	public Object2D(Rectangle R, double speed, double direction)
	{
		this(R, speed);
		this.direction = direction;
	}
	
	public Object2D(Point center, double width, double height)
	{
		this(new Rectangle(center, width, height));
	}
	
	public String toString()
	{
		return super.toString() + "\nspeed: " + this.speed 
				+ "\ndirection: " + this.direction;
	}

	public boolean superposes(Object2D O)
	{
		return super.equals((Rectangle)O);
	}
	
	public boolean equals(Object2D O)
	{
		boolean superposes = this.superposes(O);
		
		return (this.speed == O.speed) 
				&& (this.direction == O.direction)
				&& superposes;
	}
	
	public double dPdx()
	{
		return speed*Math.cos(Math.toRadians(direction));
	}
	
	public double dPdy()
	{
		return speed*Math.sin(Math.toRadians(direction));
	}
	
	public Rectangle top()
	{
		return new Rectangle(this.tlc, this.tlc.add(this.width, 1));
	}
	
	public Rectangle left()
	{
		return new Rectangle(this.tlc, this.tlc.add(1, this.height));
	}
	
	public Rectangle bottom()
	{
		return new Rectangle(this.brc.add(-this.width, -1), this.brc);
	}
	
	public Rectangle right()
	{
		return new Rectangle(this.brc.add(-1, -this.height), this.brc);
	}
	
	public Rectangle[] sides()
	{
		Rectangle[] sides = {this.top(), this.left(), this.bottom(), this.right()};
		return sides;
	}
	
	public void move()
	{
		this.translate(this.dPdx(), this.dPdy());
	}
}
