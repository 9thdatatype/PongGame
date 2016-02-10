/**
 * Program Name: Rectangle.java
 * Purpose: Here lies a purpose.
 * Coded by: Trigo, Murilo
 * Date: Feb 9, 2016 
 */
package geometry;

public class Rectangle
{
	public Point tlc; // Top Left Corner
	public Point brc; // Bottom Right Corner
	public Point center;
	public double width;
	public double height;
	
	public Rectangle()
	{
		this.tlc = new Point();
		this.brc = new Point();
		this.center = new Point();
		this.width = 0;
		this.height = 0;
	}
	
	public Rectangle(Point topLeftCorner, Point bottomRightCorner)
	{
		this.tlc = topLeftCorner;
		this.brc = bottomRightCorner;
		this.width = tlc.xDist(brc);
		this.height = tlc.yDist(brc);
		this.center = new Point(tlc.x + width/2, tlc.y + height/2);
	}
	
	public Rectangle(Point center, double width, double height)
	{
		this.center = center;
		this.width = width;
		this.height = height;
		this.tlc = new Point(center.x - width/2, center.y - height/2);
		this.brc = new Point(center.x + width/2, center.y + height/2);
	}
	
	public Rectangle(Rectangle cloneable)
	{
		this(cloneable.tlc, cloneable.brc);
	}
	
	public Rectangle(double tlcx, double tlcy, double brcx, double brcy)
	{
		this(new Point(tlcx, tlcy), new Point(brcx, brcy));
	}
	
	public Rectangle(double[] tlc, double[] brc)
	{
		this(tlc[0], tlc[1], brc[0], brc[1]);
	}
	
	public Rectangle(double[] array)
	{
		this(array[0], array[1], array[2], array[3]);
	}
	
	public String toString()
	{
		return "tlc = " + this.tlc + "\nbrc = " + this.brc + "\ncenter = " + this.center 
						+ "\nwidth = " + this.width + "\nheight = " + this.height;
	}
	
	public boolean equals(Rectangle R)
	{
		if (!this.tlc.equals(R.tlc))
		{
			return false;
		}
		
		if (!this.brc.equals(R.brc))
		{
			return false;
		}
		
		if (!this.center.equals(R.center))
		{
			return false;
		}
		
		if (this.width != R.width)
		{
			return false;
		}
		
		if (this.height != R.height)
		{
			return false;
		}
		
		return true;
	}
	
	public boolean contains(Point P)
	{
		if (P.x < this.tlc.x || P.x > this.brc.x)
		{
			return false;
		}
		
		if (P.y < this.tlc.y || P.y > this.tlc.y)
		{
			return false;
		}
		
		return true;
	}
	
	public boolean contains(double x, double y)
	{
		return this.contains(new Point(x, y));
	}
	
	public double area()
	{
		return width*height;
	}
	
	public boolean intersects(Rectangle R)
	{
		boolean horizontally = false;;
		boolean vertically = false;
		
		Rectangle lowX = R;
		Rectangle highX = this;
		if (this.tlc.x < R.tlc.x)
		{
			lowX = this;
			highX = R;
		}
		
		if (lowX.brc.x > highX.tlc.x)
		{
			horizontally = true;
		}
		
		Rectangle lowY = R;
		Rectangle highY = this;
		if (this.tlc.y < R.tlc.y)
		{
			lowY = this;
			highY = R;
		}
		
		if(lowY.brc.y > highY.tlc.y)
		{
			vertically = true;
		}
		
		return (horizontally && vertically);
	}
	
	public void reCenter(Point P)
	{
		this.tlc = P.add(-width/2, -height/2);
		this.brc = P.add(width/2, height/2);
		this.center = P;
	}
	
	public void reCenter(double x, double y)
	{
		this.reCenter(new Point(x, y));
	}
	
	public void translate(double dx, double dy)
	{
		this.reCenter(this.center.add(dx, dy));
	}
}
