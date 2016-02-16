package EngineComponents;

import java.util.ArrayList;
import java.util.HashMap;

import geometry.*;
import gameObject.*;

/**
 * 	@author Murilo Trigo
 *  @version 1.2.2a
 * 	@since 2016-01-26
 * 	<p>
 * 	A physics engine for a game of Pong
 */

public class Physics
{ 
	
	public ArrayList<GameObject> checkClick (Point P, ArrayList<GameObject> array)
	{
		ArrayList<GameObject> clickedList = new ArrayList<GameObject>(0);
		
		for (GameObject object : array)
		{			
			if (object.getPhys().contains(P))
			{
				clickedList.add(object);
			}
		}

		return clickedList;
	}
	
	public ArrayList<GameObject> checkClick (double x, double y, ArrayList<GameObject> array)
	{
		return checkClick(new Point(x, y), array);
	}
	
	
	
	
	public ArrayList<GameObject> checkCollisionOneToMany (GameObject A, ArrayList<GameObject> array)
	{
		ArrayList<GameObject> inCollision = new ArrayList<GameObject>(0);
		
		for (GameObject O : array)
		{
			if (A.getPhys().intersects(O.getPhys()))
			{
				inCollision.add(O);
			}
		}
		
		return inCollision;
		
		
	}
	
	
	public HashMap<GameObject, ArrayList<GameObject>> checkCollision(ArrayList<GameObject> array)
	{
		HashMap<GameObject, ArrayList<GameObject>> collisionTable = new HashMap<GameObject, ArrayList<GameObject>>(0);

		for (GameObject A : array )
		{
			collisionTable.put(A, checkCollisionOneToMany(A, array));
		}
		
		return collisionTable;
	}
	
}
