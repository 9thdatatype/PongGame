package gameObject;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

import geometry.*;

/**
 * This class is used for all game objects
 * @author Jonathan Spaulding, Murilo Trigo
 * @since 02/06/2016
 */
public class GameObject implements Serializable {
	
	// Privates
	private Object2D phys;
	
	//Object texture
	private File imgFile; // File object of the image
	private BufferedImage img; // The BufferedImage object of the image
	
	private static final long serialVersionUID = 1L; //Used for network game play
	
	private String name; //Name of the object
	
	/**
	 * Creates a GameObject based on two corners
	 * @param x top left corner x component
	 * @param y top left corner y component
	 * @param x2 bottom right corner x component
	 * @param y2 bottom right corner y component
	 * @param imgFilePath path to the image file to be loaded
	 */
	
	public GameObject(double x, double y, double x2, double y2, String imgFilePath){
		
		this.phys = new Object2D(x, y, x2, y2);
		
		imgFile = new File(imgFilePath);
		try {
			img = ImageIO.read(imgFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates a GameObject based on the center the width and the height
	 * @param center the center of the object
	 * @param width the length of the side along the x axis
	 * @param height the length of the side along the y axis
	 * @param imgFilePath path to the image file
	 */
	
	public GameObject(Point center, double width, double height, String imgFilePath){
		
		this.phys = new Object2D(center, width, height);
		
		imgFile = new File(imgFilePath);
		try {
			img = ImageIO.read(imgFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates a GameObject that is square based on the center and the size
	 * @param center the center of the object
	 * @param size the size of the object this is the size of any side
	 * @param imgFilePath the path to the image file
	 */
	
	public GameObject(Point center, int size, String imgFilePath){
		
		this(center, size, size, imgFilePath);
	}
	
	/**
	 * Creates a GameObject based on two corners as arrays
	 * @param topLeftCorner as an array of size 2 the top left corner of the object
	 * @param bottomRightCorner as an array of size 2 the bottom right corner of the object 
	 * @param imgFilePath path to the image file to be loaded
	 */
	
	public GameObject(double[] topLeftCorner, double bottomRightCorner[], String imgFilePath){
		
		this(topLeftCorner[0], topLeftCorner[1], 
			bottomRightCorner[0], bottomRightCorner[1], imgFilePath);
	}

	/**
	 * Creates a GameObject based on two corners as points
	 * @param topLeftCorner the top left corner of the object
	 * @param bottomRightCorner the bottom right corner of the object
	 * @param imgFilePath path to the image file to be loaded
	 */
	
	public GameObject(Point topLeftCorner, Point bottomRightCorner, String imgFilePath){
		
		this(topLeftCorner.x, topLeftCorner.y,
			bottomRightCorner.x, bottomRightCorner.y, imgFilePath);
	}

	/**
	 * Returns the image that is associated with this object
	 * @return the objects image
	 */
	
	public BufferedImage getImage(){
		
		return img;
		}
	
	/**
	 * Gets the top left corner of the current object
	 * @return a point representation of the objects top left corner
	 */
	
	public Point getTopLeftCorner(){
		
		return this.phys.tlc;
		}
	
	/**
	 * Gets the bottom right corner of the current object
	 * @return a point representation of the objects bottom right corner
	 */
	
	public Point getBottomRightCorner(){
		
		return this.phys.brc;
		}
	
	/**
	 * Gets the center of the current object
	 * @return a point representation of the objects center point
	 */
	
	public Point getCenter(){
		
		return this.phys.center;
		}
	
	/**
	 * Gets the width of the current object
	 * @return the width
	 */
	
	public int getWidth(){
		
		return (int)Math.round(this.phys.width);
		}
	
	/**
	 * Gets the height of the current object
	 * @return the height
	 */
	
	public int getHeight(){
		
		return (int)Math.round(this.phys.height);
		}
	
	/**
	 * A symbolic name for an object
	 * @return the name of the object
	 */
	
	public String getName(){
		
		return name;
		}
	
	/**
	 * Will update the center of the object
	 * @param center the new center point of the object
	 */
	
	public void setCenter(Point center){
		
		this.phys.reCenter(center);
	}



}