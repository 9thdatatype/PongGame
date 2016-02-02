/**
 * Program Name: Communication.java 
 * Purpose: communicate over the internet with another device
 * Author: Dan Thertell
 * Date: Jan 26, 2016
 *
 * @author Dan Thertell
 *  @version 1.0c
 * 	@since 2016-01-26
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

import javax.swing.JOptionPane;


public class Communication {
private Socket comm = null;
private DataOutputStream dOut;
private DataInputStream daIn;
private double x=0, y=0;
private int type = 0;
protected static int LEFT_PADDLE = 1, RIGHT_PADDLE = 2, BALL = 3;




/**
 *
 *
 *  @param String IP
 * 	@returns Communication
 * Method Name: Communication()
 * Date Added: 26/1/2016
 * Purpose: creates object and initilizes connection
 * Accepts: ip address as string
 * Returns: communication object
 * Coder: Daniel Thertell
 */
Communication(String IP){
	try {
		JOptionPane.showMessageDialog(null, "Connecting to: " + IP);
		comm = new Socket(IP,8800);
		dOut = new DataOutputStream(comm.getOutputStream());
		daIn = new DataInputStream(comm.getInputStream());

		System.out.println("Connected to: " + comm.getInetAddress());
	} catch (IOException e) {
		System.out.println("Failed to connect");
		e.printStackTrace();
		
	}
}


/**
 *@param void
 *@returns Communication
 * Method Name: Communication()
 * Date Added: 26/1/2016
 * Purpose: creates object and waits for connection
 * Accepts: null
 * Returns: communication object
 * Coder: Daniel Thertell
 */
Communication(){
	try {
		JOptionPane.showMessageDialog(null, "Awaiting connection");
		ServerSocket in = new ServerSocket(8800);
		comm = in.accept();
		dOut = new DataOutputStream(comm.getOutputStream());
		daIn = new DataInputStream(comm.getInputStream());
		
		System.out.println("Connected to: " + comm.getInetAddress());
		in.close();
	} catch (IOException e) {
		System.out.println("Failed to start Server");
		e.printStackTrace();
		
	}
}


/**
 * Method Name: send()
 * Date Added: 26/1/2016
 * Purpose: tries to send 2 doubles representing xpos and ypos
 * Accepts: doubles represinting x and y
 * Returns: null
 * Coder: Daniel Thertell
 */
public void send(double x, double y, int type){
	try {
		dOut.writeDouble(x);
		dOut.flush();
		
		dOut.writeDouble(y);
		dOut.flush();
		
		dOut.writeInt(type);
		dOut.flush();
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		System.out.println("error sending doubles");
		e.printStackTrace();
		
	}
}

/**
 * Method Name: check()
 * Date Added: 26/1/2016
 * Purpose: checks to see if a x and y pos exist and stores them
 * Accepts: null
 * Returns: true if sucessfull
 * Coder: Daniel Thertell
 */
public boolean check(){
	try {
		//System.out.println("Reading");
		
		x = daIn.readDouble();
		y = daIn.readDouble();
		type = daIn.readInt();
		
		//System.out.println("X: " + x + ", Y: " + y);
		
		if (type == -1){
			comm.close();
			System.out.println("Disconnected");
			return false;
		}else{
			return true;
		}
		
	} catch (IOException e) {
		System.out.println("Failed to read");
		e.printStackTrace();
		return false;
	}
}


/**
 * Method Name: getX()
 * Date Added: 26/1/2016
 * Purpose: returns the last x value read
 * Accepts: null
 * Returns: double represint x pos
 * Coder: Daniel Thertell
 */
public double getX(){
	return x;
}


/**
 * Method Name: getY()
 * Date Added: 26/1/2016
 * Purpose: returns the last y value read
 * Accepts: null
 * Returns: double representing y pos
 * Coder: Daniel Thertell
 */
public double getY(){
	return y;
}

/**
 * Method Name: getType()
 * Date Added: 26/1/2016
 * Purpose: gets Object type
 * Accepts: null
 * Returns: int represinting type
 * Coder: Daniel Thertell
 */

public int getType(){
	return type;
}

/**
 * Method Name: close()
 * Date Added: 2/2/2016
 * Purpose: trys to close connection. has buggs 
 * Accepts: null
 * Returns: boolean represinting sucess
 * Coder: Daniel Thertell
 */
public boolean close(){
	try {
		dOut.writeDouble(0);
		dOut.flush();
		dOut.writeDouble(0);
		dOut.flush();
		dOut.writeInt(-1);
		dOut.flush();
		comm.close();
		//closes local socket but crashes remote socket.  TODO FIX THE DAM BUG!!
		return true;
	} catch (IOException e) {
		e.printStackTrace();
		return false;
	}
}

}
