package GameStateManagers;

import java.awt.Color;
import java.awt.Graphics2D;
import geometry.*;
import java.util.ArrayList;

import EngineComponents.Physics;
import EngineComponents.Renderer;
import gameObject.GameObject;


/**
 * @author Daniel Thertell
 * @version 0.1
 * @since Feb 8, 2016
 */

public class GameMenu {
	private Physics tempPhysics;
	private int width = 0;
	private Renderer rend = null;
	private Input input;
	private int selection = 0;
	
	public GameMenu(Graphics2D graphics, int width, int height, Input in){
		//Edited By: Jonathan Spaulding
		//Edits: added a parameter of type input to the constructor
		//Edited On: 09/02/2016
		
		this.width = width;
		rend = new Renderer(graphics, width, height, Color.BLUE);
		input = in;
	}
	
	public void drawMainMenu(){
		//getting filepaths
		String singlePlayerPath = "resources/singleplayer.png";
		String multiplayerPath = "resources/multiplayer.png";
		String exitPath = "resources/exit.png";
		
		//Making game objects for the menu buttons
		// Possible bug if you change the width it seems to change the height as well
		GameObject singlePlayer = new GameObject (new Point(width/2, 200), 175, 95, singlePlayerPath, Color.black);
		GameObject multiPlayer = new GameObject (new Point(width/2, 300), 175, 95, multiplayerPath, Color.black);
		GameObject exit = new GameObject (new Point(width/2, 400), 175, 95, exitPath, Color.black);
		
		//storing objects in array list
		ArrayList<GameObject> menuItems = new ArrayList<GameObject>();
		menuItems.add(singlePlayer);
		menuItems.add(multiPlayer);
		menuItems.add(exit);
		
		//rendering array list
		rend.render(menuItems);
		
		Point mouseClick = null;
		
		
		input.setAutoclear(false);
		long time = System.nanoTime();
		
		String in = "";
		while(true){
			
			mouseClick = input.getMousePos();
			in = input.getInput();
			input.clearInput();
			System.out.println(in);
			//outer if
			//System.out.println("Checking: " + (System.nanoTime()-time));
			if(mouseClick != null){
				tempPhysics = new Physics();
				//System.out.println("click: " + (System.nanoTime()-time));
				try {
					ArrayList<GameObject> crash = tempPhysics.checkClick(mouseClick, menuItems);
						if(crash.size()>0 && crash.get(0).equals(singlePlayer)){
							System.out.println("Single Player selected");
							System.out.println("Breakpoint: " + (System.nanoTime()-time));
							break;
						}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}else if(in != null){
				//System.out.println("Key Found: " + (System.nanoTime()-time));
				//inner if
				if(in.contains("w") && selection > 0){
					menuItems.get(selection).setColour(Color.black);
					selection--;
					System.out.println("UP");
				}else if(in.contains("s") && selection < 2){
					menuItems.get(selection).setColour(Color.black);
					selection++;
					System.out.println("DOWN");
				}//end inner if
				
				menuItems.get(selection).setColour(Color.GREEN);
				//System.out.println("colour changed: " + (System.nanoTime()-time));
				rend.render(menuItems);
				//System.out.println("done drawing: " + (System.nanoTime()-time));
				time = System.nanoTime();
			}//end outer if
			
			
			
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		input.setAutoclear(true);
		
		InGameManager igm = new InGameManager(rend, input);
		igm.runGame();
		
		//Physics.newThingy(mouseClick,menuItems);
	}
	
	public void drawMultiplayerMenu() {
		String JoinPath = "/resources/join.png";
		String HostPath = "/resources/host.png";
		String BackPath = "/resources/back.png";
		
		
		GameObject Join = new GameObject (new Point(width/2, 200), 175, 95, JoinPath, null);
		GameObject Host = new GameObject (new Point(width/2, 300), 175, 95, HostPath, null);
		GameObject Back = new GameObject (new Point(width/2, 400), 175, 95, BackPath, null);

		
		ArrayList<GameObject> menuItems = new ArrayList<GameObject>();
		menuItems.add(Join);
		menuItems.add(Host);
		menuItems.add(Back);
		
		
		rend.render(menuItems);
		
		Point mouseClick = null;
		input.clearInput();
		
		while(true){
			mouseClick = input.getMousePos();
			if(mouseClick != null){
				//Physics.newThingy(mouseClick,menuItems);
				break;
			}
			input.clearInput();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		

	}
	
	
}
