/**
 * 
 */
package GameStateManagers;

import java.util.ArrayList;

import Communication.Communication;
import EngineComponents.Renderer;
import gameObject.GameObject;
import geometry.Point;

/**
 * @author Daniel Thertell
 * @version 0.1
 * @since Feb 13, 2016
 */
public class OnlineMultiplayerState {
	private Renderer rend = null;
	private int Width = 0, Height = 0;
	private Input input = null;
	private Communication comm = null;
	private boolean host = false;
	
	//this constructor waits for connection
	public OnlineMultiplayerState(Renderer rend, Input in){
		this.rend = rend;
		this.Height = rend.getHeight();
		this.Width =rend.getWidth();
		this.input = in;
		comm = new Communication();
		host = true;
	}
	
	//this constructor initilizes connection.
	public OnlineMultiplayerState(Renderer rend, Input in, String IP){
		this.rend = rend;
		this.Height = rend.getHeight();
		this.Width =rend.getWidth();
		this.input = in;
		comm = new Communication(IP);
	}
	
	
	/**
	 * loads objects and starts the proper game loop
	 * 
	 *@author Daniel Thertell
	 *@since Feb 13, 2016
	 *@param
	 */
	public void startGame(){
		ArrayList<GameObject> objs = new ArrayList<GameObject>();
		ArrayList<GameObject> transfer = new ArrayList<GameObject>();

		objs.add(new GameObject(new Point(50, 50), new Point(75, 75), "resources\\ball.png", null));
		objs.add(new GameObject(new Point(50, Height/2), 50, 200, "resources\\paddle.png", null));
		objs.add(new GameObject(new Point(Width-100, Height/2), 50, 200, "resources\\paddle.png", null));
		objs.add(new GameObject(new Point((Width/2), 0), Width, 1, "resources/border2.png", null));
		objs.add(new GameObject(new Point((Width/2), Height-1), Width, 1, "resources/border2.png", null));
		objs.add(new GameObject(new Point(0, (Height/2)), 1, Height, "resources/border1.png", null));
		objs.add(new GameObject(new Point(Width-1, (Height/2)), 1, Height, "resources/border1.png", null));
		
		if(host){
			runHost(objs,transfer);
		} else {
			runClient(objs,transfer);
		}
		
	}
	
	
	private void runHost(ArrayList<GameObject> objs, ArrayList<GameObject> transfer){
		//waiting on compleation of physics engine
	}
	
	
	private void runClient(ArrayList<GameObject> objs, ArrayList<GameObject> transfer){
		//waiting on compleation of physics engine
	}
}
