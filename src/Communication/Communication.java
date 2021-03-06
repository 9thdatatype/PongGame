package Communication;
/**
 * Program Name: Communication.java 
 * Purpose: communicate over the internet with another device
 * Author: Dan Thertell
 * Date: Jan 26, 2016
 */

import java.awt.Point;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import gameObject.GameObject;


public class Communication {
private Socket comm = null;
private DataOutputStream dOut;
private DataInputStream daIn;
private ObjectOutputStream OOut;
private ObjectInputStream OIn;
private double x=0, y=0;
private int type = 0;
private Point[] Objects = new Point[3];
private ArrayList<GameObject> gameObjects;
public static final int LEFT_PADDLE = 0, RIGHT_PADDLE = 1, BALL = 2;




/**
 * Method Name: Communication()
 * Date Added: 26/1/2016
 * Purpose: creates object and initilizes connection
 * Accepts: ip address as string
 * Returns: communication object
 * Coder: Daniel Thertell
 */
public Communication(String IP){
	try {
		JOptionPane.showMessageDialog(null, "Connecting to: " + IP);
		comm = new Socket(IP,8800);
		dOut = new DataOutputStream(comm.getOutputStream());
		daIn = new DataInputStream(comm.getInputStream());
		OOut = new ObjectOutputStream(comm.getOutputStream());
		OIn = new ObjectInputStream(comm.getInputStream());
		
		initilize();
		
		System.out.println("Connected to: " + comm.getInetAddress());
	} catch (IOException e) {
		System.out.println("Failed to connect");
		e.printStackTrace();
		
	}
}


/**
 * Method Name: Communication()
 * Date Added: 26/1/2016
 * Purpose: creates object and waits for connection
 * Accepts: null
 * Returns: communication object
 * Coder: Daniel Thertell
 */
public Communication(){
	try {
		JOptionPane.showMessageDialog(null, "Awaiting connection");
		ServerSocket in = new ServerSocket(8800);
		comm = in.accept();
		dOut = new DataOutputStream(comm.getOutputStream());
		daIn = new DataInputStream(comm.getInputStream());
		OOut = new ObjectOutputStream(comm.getOutputStream());
		OIn = new ObjectInputStream(comm.getInputStream());
		
		System.out.println("Connected to: " + comm.getInetAddress());
		
		initilize();
		
		in.close();
	} catch (IOException e) {
		System.out.println("Failed to start Server");
		e.printStackTrace();
		
	}
}

/**
 * @deprecated
 * 
 *@author Daniel Thertell
 *@since Feb 13, 2016
 *@param
 */
private void initilize(){
	Objects[0] = new Point(0,0);
	Objects[1] = new Point(0,0);
	Objects[2] = new Point(0,0);
}

/**
 * @deprecated
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
 * attempts to send a game object;
 * 
 *@author Daniel Thertell
 *@since Feb 10, 2016
 *@param Game object 
 */
public boolean sendObject(GameObject object){
	try {
		OOut.writeObject(object);
		OOut.flush();
		return true;
	} catch (IOException e) {
		e.printStackTrace();
		return false;
	}
}

/**
 * tries to grab an object
 * 
 *@author Daniel Thertell
 *@since Feb 10, 2016
 *@param
 */
public boolean checkObject(){
	try {
		gameObjects.add((GameObject)OIn.readObject());
		return true;
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
		return false;
	} catch (IOException e) {
		e.printStackTrace();
		return false;
	}
}
/**
 * 
 * takes the object at the top of the list and removes it from the list
 *@author Daniel Thertell
 *@since Feb 10, 2016
 *@param
 */
public GameObject getObject(){
	checkObject();
	if(gameObjects.size()>0){
	GameObject temp = gameObjects.get(0);
	gameObjects.remove(0);
	return temp;
	}
	return null;
}


/**
 * takes all objects in the list and clears the list
 * 
 *@author Daniel Thertell
 *@since Feb 13, 2016
 *@param
 */
public ArrayList<GameObject> getAllObjects(){
	boolean valid = true;
	while(valid){
		valid = checkObject();
	}
	ArrayList<GameObject> temp = new ArrayList<GameObject>(gameObjects);
	for(int i = 0; i < temp.size(); i++){
		gameObjects.remove(i);
	}
	return temp;
}

/**
 * @deprecated
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
		int overflow = 0;
		while(overflow < 3){
		try {
			x = daIn.readDouble();
			y = daIn.readDouble();
			type = daIn.readInt();
			overflow++;
			Objects[type] = new Point((int)x,(int)y);
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
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
 * @deprecated
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
 * @deprecated
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
 * @deprecated
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

/**
 * @deprecated
 * Method Name: getObjects()
 * Date Added: 03/2/2016
 * Purpose: grabbs as many objects as it can and puts them in a rectangle array
 * Accepts: null
 * Returns: rectangle array
 * Coder: Daniel Thertell
 */
public Point[] getObjects() {
	return Objects;
}

}
