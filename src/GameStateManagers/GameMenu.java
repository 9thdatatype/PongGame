/**
 * 
 */
package GameStateManagers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import EngineComponents.Renderer;
import gameObject.GameObject;


/**
 * @author Daniel Thertell
 * @version 0.1
 * @since Feb 8, 2016
 */

public class GameMenu {

	int width = 0, height = 0;
	Renderer rend = null;
	
	
	public GameMenu(Graphics2D graphics, int width, int height){

		this.width = width;
		this.height = height;
		rend = new Renderer(graphics , width, height, Color.BLUE);
	}
	
	public void drawMainMenu(){
		//getting filepaths
		String singlePlayerPath = "resources/singleplayer.png";
		String multiplayerPath = "resources/multiplayer.png";
		String exitPath = "resources/exit.png";
		
		System.out.println(height);
		
		//lots of calculations and stuff. also getting images and making objects
		GameObject singlePlayer = new GameObject (new Point(width/2,(int)(height*0.1)), (width-(int)(width*0.15))/2, (height-(int)(height*0.2)/3),singlePlayerPath);
		GameObject multiPlayer = new GameObject (new Point(width/2,(int)((height*0.1) + height *0.2 / 3)), (width-(int)(width*0.15))/2, (height-(int)(height*0.2)/3),multiplayerPath);
		GameObject exit = new GameObject (new Point(width/2,(int)((height*0.1) + (height *0.2 / 3)*2)), (width-(int)(width*0.15))/2, (height-(int)(height*0.2)/3),exitPath);
		
		System.out.println(singlePlayer.getHeight());
		
		
		//storing objects in array list
		ArrayList<GameObject> menuItems = new ArrayList<GameObject>();
		menuItems.add(singlePlayer);
		menuItems.add(multiPlayer);
		menuItems.add(exit);
		
		//rendering array list
		rend.render(menuItems);
	}
	
	public void drawMultiplayerMenu() {
		String JoinPath = "/resources/join.png";
		String HostPath = "/resources/host.png";
		String BackPath = "/resources/back.png";
		
		
		GameObject Join = new GameObject (new Point(width/2,(int)(height*0.1)), (width-(int)(width*0.15))/2, (height-(int)(height*0.2)/3),JoinPath);
		GameObject Host = new GameObject (new Point(width/2,(int)((height*0.1) + height *0.2 / 3)), (width-(int)(width*0.15))/2, (height-(int)(height*0.2)/3),HostPath);
		GameObject Back = new GameObject (new Point(width/2,(int)((height*0.1) + (height *0.2 / 3)*2)), (width-(int)(width*0.15))/2, (height-(int)(height*0.2)/3),BackPath);

		
		ArrayList<GameObject> menuItems = new ArrayList<GameObject>();
		menuItems.add(Join);
		menuItems.add(Host);
		menuItems.add(Back);
		
		
		rend.render(menuItems);
	}
	
	
}
