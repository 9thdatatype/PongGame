package GameStateManagers;

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

	public static final int MAX_FPS = 60;
	
	public InGameManager(Renderer renderer, Input input){
		this.renderer = renderer;
		this.width = renderer.getWidth();
		this.height = renderer.getHeight();
		this.input = input;
	}
	
	public void runGame(){
		long startTime, endTime, timeTaken;
		double frameTime = 1000/MAX_FPS;

		ArrayList<GameObject> objs = new ArrayList<GameObject>();

		objs.add(new GameObject(new Point(50, 50), new Point(75, 75), "resources\\ball.png", null));
		objs.add(new GameObject(new Point(50, height/2-100), new Point(100, height / 2 + 100), "resources\\paddle.png", null));
		objs.add(new GameObject(new Point(width-100, height/2-50), new Point(width - 50, height / 2 + 50), "resources\\paddle.png", null));
		objs.add(new GameObject(new Point((width/2), 0), width, 1, "resources/border2.png", null));
		objs.add(new GameObject(new Point((width/2), height-1), width, 1, "resources/border2.png", null));
		objs.add(new GameObject(new Point(0, (height/2)), 1, height, "resources/border1.png", null));
		objs.add(new GameObject(new Point(width-1, (height/2)), 1, height, "resources/border1.png", null));

		//Creates a physics object
//		Physics physics = new Physics(width, height, 25, new Dimension(objs.get(1).getWidth(), objs.get(1).getHeight()), 10, 0.4, objs.get(1).getTopLeftCorner(), objs.get(2).getTopLeftCorner(), 30);
//		physics.startSimulation();
//
//		//Main game loop
//		while(running){
//			startTime = System.currentTimeMillis();
//			
//			if(input.getInput().contains("w")){
//				physics.setp1y(-1);
//				objs.get(1).setTLC(new Point(physics.getP1_x(), physics.getP1_y()));
//			}else if(input.getInput().contains("s")){
//				physics.setp1y(1);
//				objs.get(1).setTLC(new Point(physics.getP1_x(), physics.getP1_y()));
//			}else if(!input.getInput().contains("w") && !input.getInput().contains("s")){
//				physics.setp1y(0);
//				objs.get(1).setTLC(new Point(physics.getP1_x(), physics.getP1_y()));
//			}
//				
//			if(input.getInput().contains("i")){
//				physics.setp2y(-1);
//				objs.get(2).setTLC(new Point(physics.getP2_x(), physics.getP2_y()));
//			}else if(input.getInput().contains("k")){
//				physics.setp2y(1);
//				objs.get(2).setTLC(new Point(physics.getP2_x(), physics.getP2_y()));
//			}else if(!input.getInput().contains("i") && !input.getInput().contains("k")){
//				physics.setp2y(0);
//				objs.get(2).setTLC(new Point(physics.getP2_x(), physics.getP2_y()));
//			}
//
//			objs.get(0).setTLC(new Point(physics.getBall_x(), physics.getBall_y()));
//
//			physics.updateStates();
//
//			renderer.render(objs);
//
//			endTime = System.currentTimeMillis();
//
//			timeTaken = endTime - startTime;
//			//System.out.println(1000/timeTaken);
////			if(timeTaken < frameTime){
////				try {
////					Thread.sleep((long) (frameTime - timeTaken));
////				} catch (InterruptedException e) {
////					e.printStackTrace();
////				}
////			}
//		}
	}
	
	private int width;
	private int height;
	private Renderer renderer;
	private Input input;
	private boolean running = true;
}
