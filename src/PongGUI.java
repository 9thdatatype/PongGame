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
 * @version 0.0002
 * @since 2016-02-02
 */
public class PongGUI {

	public static int MAX_FPS = 60;

	public int width;
	public int height;

	PongGUI(int width, int height){
		this.width = width;
		this.height = height;
	}

	public void createAndShowGUI(){
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setSize(width, height);
		jframe.setResizable(false);
		jframe.setVisible(true);

		cWidth = jframe.getContentPane().getWidth();
		cHeight = jframe.getContentPane().getHeight();
	}

	public void run(){
		long startTime, endTime, timeTaken;
		double frameTime = 1000/MAX_FPS;

		Renderer renderer = new Renderer((Graphics2D)jframe.getContentPane().getGraphics(), cWidth, cHeight, Color.black);

		ArrayList<GameObject> objs = new ArrayList<GameObject>();

		objs.add(new GameObject(new Point(50, 50), new Point(75, 75), "resources\\ball.png"));
		objs.add(new GameObject(new Point(50, cHeight/2-50), new Point(100, cHeight / 2 + 50), "resources\\paddle.png"));
		objs.add(new GameObject(new Point(cWidth-100, cHeight/2-50), new Point(cWidth - 50, cHeight / 2 + 50), "resources\\paddle.png"));

		//Creates a physics object
		Physics physics = new Physics(cWidth, cHeight, 25, new Dimension(0,0), 15);
		physics.startSimulation();

		//Creates an Input object
		Input in = new Input(jframe);

		//Main game loop
		while(running){
			startTime = System.currentTimeMillis();

			//TODO: add the code to move paddle one
			if(in.getInput().contains("w"))
				objs.get(1).setTLC(new Point(objs.get(1).getTopLeftCorner().x, objs.get(1).getTopLeftCorner().y - 1));
			else if(in.getInput().contains("s"))
				objs.get(1).setTLC(new Point(objs.get(1).getTopLeftCorner().x, objs.get(1).getTopLeftCorner().y + 1));
			
			if(in.getInput().contains("i"))
				objs.get(2).setTLC(new Point(objs.get(2).getTopLeftCorner().x, objs.get(2).getTopLeftCorner().y - 1));
			else if(in.getInput().contains("k"))
				objs.get(2).setTLC(new Point(objs.get(2).getTopLeftCorner().x, objs.get(2).getTopLeftCorner().y + 1));


			objs.get(0).setTLC(new Point(physics.getBall_x(), physics.getBall_y()));

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
	private int cWidth;
	private int cHeight;

	// Game Variables

	private boolean running = true;

}
// end class