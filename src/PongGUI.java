import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JFrame;

import EngineComponents.Physics;
import EngineComponents.Renderer;
import gameObject.GameObject;

/**
 * @author Jonathan Spaulding
 * @version 0.0001
 * @since 2016-02-02
 */
public class PongGUI {

	public static int MAX_FPS = 60;

	public int width = 960;
	public int height = 720;

	PongGUI(){}

	PongGUI(int width, int height){
		this.width = width;
		this.height = height;
	}

	public void createAndShowGUI(){
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setSize(width, height);
		jframe.setResizable(false);
		jframe.setVisible(true);
	}

	public void run(){
		long startTime, endTime, timeTaken;
		double frameTime = 1000/MAX_FPS;

		Renderer renderer = new Renderer((Graphics2D)jframe.getGraphics(), width, height, Color.black);
		
		ArrayList<GameObject> objs = new ArrayList<GameObject>();
				
		objs.add(new GameObject(new Point(50, 50), new Point(75, 75), "resources\\ball.png"));
		
		//Creates a physics object
		Physics physics = new Physics(width, height, 25, null, 1);
		physics.startSimulation();

		//Creates an Input object
		Input in = new Input(jframe);

		//Main game loop
		while(running){
			startTime = System.currentTimeMillis();
			
			objs.get(0).setCenter(new Point(physics.getBall_x(), physics.getBall_y()));
			
			physics.updateStates();
			
			renderer.render(objs);

			endTime = System.currentTimeMillis();

			timeTaken = endTime - startTime;
			//System.out.println(timeTaken);
			if(timeTaken < frameTime){
				try {
					Thread.sleep((long) (frameTime - timeTaken));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/*
	 * Private portion of code
	 */

	//Visual elements

	private JFrame jframe = new JFrame();

	// Game Variables

	private boolean running = true;

}
// end class