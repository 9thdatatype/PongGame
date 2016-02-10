/**
 * Program Name: Solid.java
 * Purpose: Here lies a purpose.
 * Coded by: Trigo, Murilo
 * Date: Feb 9, 2016 
 */
package geometry;

public class Solid extends Rectangle
{
	public Rectangle[] sides = new Rectangle[4];
	public Rectangle top;
	public Rectangle left;
	public Rectangle bottom;
	public Rectangle right;
	
	public double speed;
	public double direction; // angle in degrees

	
	
	public Solid(Rectangle R)
	{
		super(R);
		this.top = new Rectangle(R.tlc, R.tlc.add(R.width, 1));
		this.left = new Rectangle(R.tlc, R.tlc.add(1, R.height));
		this.bottom = new Rectangle(R.brc.add(-R.width, -1), R.brc);
		this.right = new Rectangle(R.brc.add(-1, -R.height), R.brc);
		this.sides[0] = this.top;
		this.sides[1] = this.left;
		this.sides[2] = this.bottom;
		this.sides[3] = this.right;
		this.direction = 0;
		this.speed = 0;
	}
	
	public Solid(Rectangle R, double speed)
	{
		this(R);
		this.speed = speed;
	}
	
	public Solid(Rectangle R, double speed, double direction)
	{
		this(R, speed);
		this.direction = direction;
	}
	
	public Solid(Point P, double width, double height)
	{
		this(new Rectangle(P, width, height));
	}
	
	public String toString()
	{
		return super.toString() + "\nspeed: " + this.speed 
				+ "\ndirection: " + this.direction;
	}

	public boolean superposes(Solid S)
	{
		return super.equals((Rectangle)S);
	}
	
	public boolean equals(Solid S)
	{
		boolean superposes = this.superposes(S);
		
		return (this.speed == S.speed) 
				&& (this.direction == S.direction)
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
	
	public void move()
	{
		this.translate(this.dPdx(), this.dPdy());
	}
}
