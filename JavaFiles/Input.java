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
	String output = "";
	
	
	
	
	
	/**
	 * Method Name: Input()
	 * Date Added: 26/1/2016
	 * Purpose: Creates new listeners from frame
	 * Accepts: JFrame
	 * Returns: Input object
	 * Coder: Daniel Thertell
	 */
	Input(JFrame Main){
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
		output = output + evt.getKeyChar();
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
			System.out.println(tmp+tmp2);
			output = tmp + tmp2;
			
		}
	}
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
 * Method Name: clearInput()
 * Date Added: 31/1/2016
 * Purpose: clears the string buffer
 * Accepts: null
 * Returns: null
 * Coder: Daniel Thertell
 */	

public void clearInput(){
	output = "";
}


}

