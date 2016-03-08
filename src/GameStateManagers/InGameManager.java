package GameStateManagers;

import java.awt.Color;
import java.awt.Dimension;
import geometry.*;
import java.util.ArrayList;

import EngineComponents.Physics;
import EngineComponents.Renderer;
import gameObject.GameObject;

/**
 * 
 * @author Jonathan
 * @since 09/02/2016
 */
public class InGameManager {
	
	public InGameManager(Renderer renderer, Input input, Physics phys){
		this.renderer = renderer;
		this.width = renderer.getWidth();
		this.height = renderer.getHeight();
		this.input = input;
		this.phys = phys;
	}
	
	public void runGame(){
		long startTime, endTime, timeTaken;

		ArrayList<GameObject> objs = new ArrayList<GameObject>();

		objs.add(new GameObject(new Point(50, 50), new Point(75, 75), "resources\\ball.png", null));
		objs.add(new GameObject(new Point(50, height/2), 50, 200, "resources\\paddle.png", null));
		objs.add(new GameObject(new Point(width-100, height/2), 50, 200, "resources\\paddle.png", null));
		objs.add(new GameObject(new Point((width/2), 0), width, 1, "resources/border2.png", null));
		objs.add(new GameObject(new Point((width/2), height-1), width, 1, "resources/border2.png", null));
		objs.add(new GameObject(new Point(0, (height/2)), 1, height, "resources/border1.png", null));
		objs.add(new GameObject(new Point(width-1, (height/2)), 1, height, "resources/border1.png", null));
		
		objs.get(1).setDirection(90);
		System.out.println("Paddle 1 Dir: " + objs.get(1).getPhys().direction);
		objs.get(2).setDirection(90);
		System.out.println("Paddle 2 Dir: " + objs.get(2).getPhys().direction);
		objs.get(0).setDirection(-20);
		objs.get(0).setSpeed(10);
		//TODO: Add the physics engine in here
		//A Change
		//Main game loop
		while(running){
			startTime = System.currentTimeMillis();
			objs.get(1).setSpeed(0);
			objs.get(2).setSpeed(0);
			if(input.getInput().contains("w")){
//				objs.get(0).getBottomRightCorner().translate(1, 1);
//				objs.get(0).getCenter().translate(1, 1);
//				objs.get(0).setCenter(new Point(objs.get(0).getCenter().x+1, objs.get(0).getCenter().y+1));
				objs.get(1).setSpeed(5);
			}else if(input.getInput().contains("s")){
				objs.get(1).setSpeed(-5);
			}
				
			if(input.getInput().contains("i")){
				objs.get(2).setSpeed(5);
			}else if(input.getInput().contains("k")){
				objs.get(2).setSpeed(-5);
			}

			phys.update(objs);
			renderer.render(objs);

			endTime = System.currentTimeMillis();

			timeTaken = endTime - startTime;
			//System.out.println(1000/timeTaken);
		}
	}
	
	private int width;
	private int height;
	private Renderer renderer;
	private Input input;
	private boolean running = true;
	private Physics phys;
}
