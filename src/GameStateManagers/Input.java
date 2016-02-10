package GameStateManagers;
import geometry.*;

import javax.swing.JFrame;

/**
 * Program Name: Input.java 
 * Purpose: 
 * Author: Dan Thertell
 * Date: Jan 26, 2016
 */

/**
 * @author Dan
 *
 */



public class Input {
	private String output = "";
	private Point mousePoint = null;
	
	
	
	
	/**
	 * Method Name: Input()
	 * Date Added: 26/1/2016
	 * Purpose: Creates new listeners from frame
	 * Accepts: JFrame
	 * Returns: Input object
	 * Coder: Daniel Thertell
	 */
	public Input(JFrame Main){
		Main.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
            	//creates key press listener
                InputKeyPress(evt);//listener calls this function
            }
	});
		Main.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
            	//creates key release listener
                InputKeyRelease(evt);
            }
        });
		
		Main.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                myMouseClicked(evt);
            }
        });
		
}

	
	/**
	 * Method Name: InputKeyPress()
	 * Date Added: 26/1/2016
	 * Purpose: adds pressed key to string
	 * Accepts: java.awt.event.KeyEvent
	 * Returns: null
	 * Coder: Daniel Thertell
	 */	
private void InputKeyPress(java.awt.event.KeyEvent evt){
	if(!output.contains(""+evt.getKeyChar())){
	output = output + evt.getKeyChar();
	}
	}

/**
 * Method Name: InputKeyRelease()
 * Date Added: 26/1/2016
 * Purpose: removes released key from string
 * Accepts: java.awt.event.KeyEvent
 * Returns: null
 * Coder: Daniel Thertell
 */	

private void InputKeyRelease(java.awt.event.KeyEvent evt){
	for(int i=0;i<output.length();i++){
		if(output.charAt(i) == evt.getKeyChar()){
			String tmp = output.substring(i+1);
			String tmp2 = output.substring(0, i);
			
			//for debuging
			//System.out.println(tmp+tmp2);
			output = tmp + tmp2;
			
		}
	}
}


private void myMouseClicked(java.awt.event.MouseEvent evt){
	mousePoint = new Point(evt.getX(),evt.getY());
}
/**
 * Method Name: getInput()
 * Date Added: 26/1/2016
 * Purpose: transfers string of all buttons pressed
 * Accepts: null
 * Returns: string containing char values of pressed buttons
 * Coder: Daniel Thertell
 */	
public String getInput(){
	return output;
}

/**
 * Clears string buffer and mouse point
 * @since 31/1/2016
 * @author Daniel Thertell
 */	

public void clearInput(){
	output = "";
	mousePoint = null;
}


/**
 * mouse point
 * @return returns the position of a mouse click
 * @since 02/08/2016
 */	
public Point getMousePos(){
	return mousePoint;
}


}

