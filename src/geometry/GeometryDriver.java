/**
 * Program Name: GeometryDriver.java
 * Purpose: Here lies a purpose.
 * Coded by: Trigo, Murilo
 * Date: Feb 9, 2016 
 */
package geometry;

public class GeometryDriver
{
	public static void main(String[] args)
	{
		Point C = new Point(20, 20);
		Rectangle R = new Rectangle (C, 20, 20);
		Solid S1 = new Solid(R, 10, 20);
		Solid S2 = new Solid(R, 10, 21);
		System.out.println(S1.superposes(S2));
		System.out.println(S1.equals(S2));

	}
}
