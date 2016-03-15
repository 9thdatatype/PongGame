package GameStateManagers;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import geometry.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

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
		rend = new Renderer(graphics, width, height, Color.BLUE, new Font(Font.SERIF, 12, 22));
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
		
		singlePlayer.setName("singlePlayer");
		multiPlayer.setName("multiPlayer");
		exit.setName("exit");
		
		
		//storing objects in array list
		ArrayList<GameObject> menuItems = new ArrayList<GameObject>();
		menuItems.add(singlePlayer);
		menuItems.add(multiPlayer);
		menuItems.add(exit);
		
		//rendering array list
		rend.render(menuItems, null, null);
		
		Point mouseClick = null;
				
		input.setAutoclear(false);
		
		String in = "";
		while(true){
			
			mouseClick = input.getMousePos();
			in = input.getInput();
			input.clearInput();
			//outer if
			if(mouseClick != null){
				tempPhysics = new Physics();
				
				try {
					ArrayList<GameObject> crash = tempPhysics.checkClick(mouseClick, menuItems);
						if(crash.size()>0){
							
							menuProcess(crash.get(0).getName());
							
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
					//System.out.println("UP");
				}else if(in.contains("s") && selection < 2){
					menuItems.get(selection).setColour(Color.black);
					selection++;
					//System.out.println("DOWN");
				}else if(in.contains("\n")){
					String select = menuItems.get(selection).getName();
					
					menuProcess(select);// checks the input
					
					break; // defaults to single player using break
				}//end inner if
				
				menuItems.get(selection).setColour(Color.GREEN);
				
				rend.render(menuItems, null, null);
			}//end outer if
			
			
			
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		input.setAutoclear(true);
		
		InGameManager igm = new InGameManager(rend, input, tempPhysics);
		igm.runGame();
		
		//Physics.newThingy(mouseClick,menuItems);
	}
	
	public void drawMultiplayerMenu() {
		String JoinPath = "resources/Join.png";
		String HostPath = "resources/Host.png";
		String BackPath = "resources/Back.png";
		
		
		GameObject Join = new GameObject (new Point(width/2, 200), 175, 95, JoinPath, Color.black);
		GameObject Host = new GameObject (new Point(width/2, 300), 175, 95, HostPath, Color.black);
		GameObject Back = new GameObject (new Point(width/2, 400), 175, 95, BackPath, Color.black);

		
		Join.setName("Join");
		Host.setName("Host");
		Back.setName("Back");
		
		
		ArrayList<GameObject> menuItems = new ArrayList<GameObject>();
		menuItems.add(Join);
		menuItems.add(Host);
		menuItems.add(Back);
		
		
		rend.render(menuItems, null, null);
		
		Point mouseClick = null;
		input.clearInput();
		String in = "";	
		
		while(true){
			
			mouseClick = input.getMousePos();
			in = input.getInput();
			input.clearInput();
			//outer if
			if(mouseClick != null){
				tempPhysics = new Physics();
				
				try {
					ArrayList<GameObject> crash = tempPhysics.checkClick(mouseClick, menuItems);
						if(crash.size()>0){
							
							multiplayerMenuProcess(crash.get(0).getName());
							
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
					//System.out.println("UP");
				}else if(in.contains("s") && selection < 2){
					menuItems.get(selection).setColour(Color.black);
					selection++;
					//System.out.println("DOWN");
				}else if(in.contains("\n")){
					String select = menuItems.get(selection).getName();
					
					multiplayerMenuProcess(select);// checks the input
					
					break; // defaults to single player using break
				}//end inner if
				
				menuItems.get(selection).setColour(Color.GREEN);
				
				rend.render(menuItems, null, null);
			}
		
		}
	}
	
	private void menuProcess(String choice){
		if(choice.equals("multiPlayer")){
			drawMultiplayerMenu();
		}
		else if (choice.equals("exit")){
			System.exit(0);
		}
	}
	
	private void multiplayerMenuProcess(String choice){
		OnlineMultiplayerState online = null;
		if(choice.equals("Join")){
			String IP = JOptionPane.showInputDialog("Enter IP Address");
			online = new OnlineMultiplayerState(rend, input, IP);
		}else if (choice.equals("Host")){
			online = new OnlineMultiplayerState(rend, input);
		}else if (choice.equals("Back")){
			drawMainMenu();
		}
		online.startGame();
	}
	
	
}
