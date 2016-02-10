package EngineComponents;

import java.util.ArrayList;
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
		ArrayList<GameObject> clickedList = new ArrayList<GameObject>();
		boolean empty = true;
		
		for (GameObject object : array)
		{			
			if (object.getPhys().contains(P))
			{
				clickedList.add(object);
				empty = false;
			}
		}
		
		if (empty)
		{
			return null;
		}
		else
		{
			return clickedList;
		}
	}
	
	public ArrayList<GameObject> checkClick (double x, double y, ArrayList<GameObject> array)
	{
		return checkClick(new Point(x, y), array);
	}
}
