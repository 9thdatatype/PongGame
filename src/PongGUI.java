import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JFrame;

import Communication.Communication;
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
		jframe.setResizable(true);
		jframe.setVisible(true);

		startMenu();
		
		cWidth = jframe.getContentPane().getWidth();
		cHeight = jframe.getContentPane().getHeight();
	}

	public void run(){
		long startTime, endTime, timeTaken;
		double frameTime = 1000/MAX_FPS;

		Renderer renderer = new Renderer((Graphics2D)jframe.getContentPane().getGraphics(), cWidth, cHeight, Color.black);

		ArrayList<GameObject> objs = new ArrayList<GameObject>();

		objs.add(new GameObject(new Point(50, 50), new Point(75, 75), "resources\\ball.png"));
		objs.add(new GameObject(new Point(50, cHeight/2-100), new Point(100, cHeight / 2 + 100), "resources\\paddle.png"));
		objs.add(new GameObject(new Point(cWidth-100, cHeight/2-50), new Point(cWidth - 50, cHeight / 2 + 50), "resources\\paddle.png"));

		//Creates a physics object
		Physics physics = new Physics(cWidth, cHeight, 25, new Dimension(objs.get(1).getWidth(), objs.get(1).getHeight()), 10, 0.4, objs.get(1).getTopLeftCorner(), objs.get(2).getTopLeftCorner(), 30);
		physics.startSimulation();

		//Creates an Input object
		Input in = new Input(jframe);

		//Main game loop
		while(running){
			startTime = System.currentTimeMillis();
			
			if(in.getInput().contains("w")){
				physics.setp1y(-1);
				objs.get(1).setTLC(new Point(physics.getP1_x(), physics.getP1_y()));
			}else if(in.getInput().contains("s")){
				physics.setp1y(1);
				objs.get(1).setTLC(new Point(physics.getP1_x(), physics.getP1_y()));
			}else if(!in.getInput().contains("w") && !in.getInput().contains("s")){
				physics.setp1y(0);
				objs.get(1).setTLC(new Point(physics.getP1_x(), physics.getP1_y()));
			}
				
			if(in.getInput().contains("i")){
				physics.setp2y(-1);
				objs.get(2).setTLC(new Point(physics.getP2_x(), physics.getP2_y()));
			}else if(in.getInput().contains("k")){
				physics.setp2y(1);
				objs.get(2).setTLC(new Point(physics.getP2_x(), physics.getP2_y()));
			}else if(!in.getInput().contains("i") && !in.getInput().contains("k")){
				physics.setp2y(0);
				objs.get(2).setTLC(new Point(physics.getP2_x(), physics.getP2_y()));
			}


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

	private void startMenu(){
		java.awt.Rectangle button = new java.awt.Rectangle(0, 0, width, height/2);
		java.awt.Rectangle button2 = new java.awt.Rectangle(0, height/2, width, height/2);
		
		Input input = new Input(jframe);
		
		
		while(true){
			Point mpos = input.getMousePos();
			if(mpos != null){
				if(button.contains(mpos)){
					Communication comm = new Communication("192.168.43.5");
					comm.send(15, 53, 0);
					comm.send(45, 35, 1);
					comm.send(10, 50, 2);
				}else if(button2.contains(mpos)){
					Communication comm = new Communication();
				}
				input.clearInput();
			}
		}
	}
	
}
// end class