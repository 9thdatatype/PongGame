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
		tlc = new Point();
		brc = new Point();
		center = new Point();
		width = 0;
		height = 0;
	}
	
	public Rectangle(Point topLeftCorner, Point bottomRightCorner)
	{
		tlc = topLeftCorner;
		brc = bottomRightCorner;
		width = tlc.xDist(brc);
		height = tlc.yDist(brc);
		center = tlc.add(width/2, height/2);
	}
	
	public Rectangle(Point P, double w, double h)
	{
		center = P;
		width = w;
		height = h;
		tlc = center.add(-width/2, -height/2);
		brc = center.add(width/2, height/2);
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
		return "tlc = " + tlc + "\nbrc = " + brc + "\ncenter = " + center 
						+ "\nwidth = " + width + "\nheight = " + height;
	}
	
	public boolean equals(Rectangle R)
	{
		if (!tlc.equals(R.tlc))
		{
			return false;
		}
		
		if (!brc.equals(R.brc))
		{
			return false;
		}
		
		if (!center.equals(R.center))
		{
			return false;
		}
		
		if (width != R.width)
		{
			return false;
		}
		
		if (height != R.height)
		{
			return false;
		}
		
		return true;
	}
	
	public boolean contains(Point P)
	{
		if (P.x < tlc.x || P.x > brc.x)
		{
			return false;
		}
		
		if (P.y < tlc.y || P.y > tlc.y)
		{
			return false;
		}
		
		return true;
	}
	
	public boolean contains(double x, double y)
	{
		return contains(new Point(x, y));
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
		if (tlc.x < R.tlc.x)
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
		if (tlc.y < R.tlc.y)
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
		tlc = P.add(-width/2, -height/2);
		brc = P.add(width/2, height/2);
		center = P;
	}
	
	public void reCenter(double x, double y)
	{
		reCenter(new Point(x, y));
	}
	
	public void setTLC(Point P)
	{
		reCenter(P.add(width/2, height/2));
	}
	
	public void setTLC(double x, double y)
	{
		setTLC(new Point(x, y));
	}
	
	public void setBRC(Point P)
	{
		reCenter(P.add(-width/2, -height/2));
	}
	
	public void setBRC(double x, double y)
	{
		setBRC(new Point(x, y));
	}
	
	public void translate(double dx, double dy)
	{
		reCenter(center.add(dx, dy));
	}
}
