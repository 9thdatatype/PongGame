package GameStateManagers;

import geometry.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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
		//objs.add(new GameObject(new Point(0, 0), new Point(width, height), "resources/pongCourt.png", null));
		
		objs.get(0).setName("Ball");
		objs.get(1).setName("Paddle 1");
		objs.get(2).setName("Paddle 2");
		objs.get(3).setName("Side 1");
		objs.get(4).setName("Side 2");
		objs.get(5).setName("Side 3");
		objs.get(6).setName("Side 4");
		
		objs.get(1).setDirection(-90);
		System.out.println("Paddle 1 Dir: " + objs.get(1).getPhys().direction);
		objs.get(2).setDirection(-90);
		System.out.println("Paddle 2 Dir: " + objs.get(2).getPhys().direction);
		objs.get(0).setDirection(20);
		objs.get(0).setSpeed(ballSpeed);
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
				objs.get(1).setSpeed(speed);
			}else if(input.getInput().contains("s")){
				objs.get(1).setSpeed(-speed);
			}
				
			if(input.getInput().contains("i")){
				objs.get(2).setSpeed(speed);
			}else if(input.getInput().contains("k")){
				objs.get(2).setSpeed(-speed);
			}
			
			
			phys.update(objs);
			renderer.render(objs);
			renderer.render("Player 1 Score: " + p1s + "\t Player 2 Score: " + p2s, 300, 25);
			
			
			//checking and storing collisions
			collisions = phys.checkCollision(objs);
			// calculations
			@SuppressWarnings("rawtypes")
			Set collisionSet = collisions.entrySet();
			@SuppressWarnings("rawtypes")
			Iterator colIterator = collisionSet.iterator();
			//Looping through all collisions
			while (colIterator.hasNext()){
				@SuppressWarnings("rawtypes")
				Map.Entry entry = (Map.Entry)colIterator.next();
				
				GameObject key = (GameObject)entry.getKey();

				ArrayList<GameObject> stuff = (ArrayList<GameObject>)(entry.getValue());
				
				//BALL COLLISION
				if(key.getName().equals("Ball") && stuff.size()>1){
					for(GameObject crash : stuff){
						//System.out.println("Ball Crashed With: " + crash.getName());
						
						if(crash.getName().equals("Paddle 1")){
							//System.out.println("Ball hit Left Paddle");
							ballSpeed++;
							objs.get(0).bounceHorizontal();
							objs.get(0).setSpeed(ballSpeed);
						}else if(crash.getName().equals("Paddle 2")){
							//System.out.println("Ball hit Right Paddle");
							ballSpeed++;
							objs.get(0).bounceHorizontal();
							objs.get(0).setSpeed(ballSpeed);
						}else if(crash.getName().equals("Side 1")){
							objs.get(0).bounceVertical();
							//System.out.println("Ball hit Top");
						}else if(crash.getName().equals("Side 2")){
							objs.get(0).bounceVertical();
							//System.out.println("Ball hit Bottom!");
						}else if(crash.getName().equals("Side 3")){
							p2s++;
							objs.get(0).setCenter(new Point(width/2,height/2));
							ballSpeed = 10;
							objs.get(0).bounceHorizontal();
							objs.get(0).setSpeed(ballSpeed);
						}else if(crash.getName().equals("Side 4")){
							p1s++;
							objs.get(0).setCenter(new Point(width/2,height/2));
							ballSpeed = 10;
							objs.get(0).bounceHorizontal();
							objs.get(0).setSpeed(ballSpeed);
						}
					}
				}//BALL COLLISION DONE
				
			}
			
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
	private HashMap collisions;
	private  int speed = 5;
	private int ballSpeed = speed * 2;
	private int p1s = 0;
	private int p2s = 0;
}
