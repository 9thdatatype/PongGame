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
		
		System.out.println(R);
		System.out.println("\n translate by (-5, -5)");
		R.translate(-5, -5);
		System.out.println("\n" + R);

	}
}
