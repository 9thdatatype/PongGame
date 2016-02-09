import java.awt.Graphics2D;

import javax.swing.JFrame;

import GameStateManagers.GameMenu;
import GameStateManagers.Input;

/**
 * @author Jonathan Spaulding
 * @version 0.0002
 * @since 2016-02-02
 */
public class PongGUI {

	/**
	 * Sets the size of the window
	 * @param width the width of the window
	 * @param height the height of the window
	 */
	public PongGUI(int width, int height){
		sWidth = width;
		sHeight = height;
	}
	
	/**
	 * Will create and show the window
	 */
	public void createAndShowGUI(){
		//You want the jframe to close everything when it is closed
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Sets the size of the jframe
		jframe.setSize(sWidth, sHeight);
		//Makes it a set size 
		jframe.setResizable(false);
		//Makes the jframe visible
		jframe.setVisible(true);
		
		//Set the content width
		cWidth = jframe.getContentPane().getWidth();
		//Set the content height
		cHeight = jframe.getContentPane().getHeight();
		
		//Set up the game menu
		GameMenu gmenu = new GameMenu((Graphics2D)(jframe.getContentPane().getGraphics()),cWidth,cHeight, new Input(jframe));
		//Draw the main menu
		gmenu.drawMainMenu();
	}

	/*
	 * Private portion of code
	 */

	//Visual elements

	//The actual JFrame object
	private JFrame jframe = new JFrame();
	//Screen Width
	public int sWidth;
	//Screen Height
	public int sHeight;
	//ContentPane width
	private int cWidth;
	//ContentPane height
	private int cHeight;
}
// end class