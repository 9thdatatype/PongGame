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
	
	public static ArrayList<GameObject> update (ArrayList<GameObject> list)
	{
		ArrayList<GameObject> nextFrameList = new ArrayList<GameObject>(list); 
		for (GameObject O : nextFrameList)
		{
			O.setCenter(O.getPhys().move()); // .move() is being called at a copy of the actual 2D Object, 
										     // but it nonetheless returns the Point that is the center we want
		}
		
		return nextFrameList;
	}
	
	public static ArrayList<GameObject> checkClick (Point P, ArrayList<GameObject> list)
	{
		ArrayList<GameObject> clickedList = new ArrayList<GameObject>(0);
		
		for (GameObject object : list)
		{			
			if (object.getPhys().contains(P))
			{
				clickedList.add(object);
			}
		}

		return clickedList;
	}
	
	
	public static ArrayList<GameObject> checkClick (double x, double y, ArrayList<GameObject> list)
	{
		return checkClick(new Point(x, y), list);
	}
	
	
	public static ArrayList<GameObject> checkCollisionOneToMany (GameObject A, ArrayList<GameObject> list)
	{
		ArrayList<GameObject> inCollision = new ArrayList<GameObject>(0);
		
		for (GameObject O : list)
		{
			if (A.getPhys().intersects(O.getPhys()))
			{
				inCollision.add(O);
			}
		}
		
		return inCollision;
	}
	
	
	public static HashMap<GameObject, ArrayList<GameObject>> checkCollision(ArrayList<GameObject> list)
	{
		HashMap<GameObject, ArrayList<GameObject>> collisionTable = new HashMap<GameObject, ArrayList<GameObject>>(0);

		for (GameObject A : list )
		{
			collisionTable.put(A, checkCollisionOneToMany(A, list));
		}
		
		return collisionTable;
	}
	
}
