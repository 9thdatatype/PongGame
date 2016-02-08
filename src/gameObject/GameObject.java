package gameObject;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * This class is used for all game objects
 * @author Jonathan Spaulding
 * @since 02/06/2016
 */
public class GameObject implements Serializable {
	
	/**
	 * Creates a GameObject based on two corners
	 * @param x top left corner x component
	 * @param y top left corner y component
	 * @param x2 bottom right corner x component
	 * @param y2 bottom right corner y component
	 * @param imgFilePath path to the image file to be loaded
	 */
	public GameObject(int x, int y, int x2, int y2, String imgFilePath){
		tlc = new Point(x, y);
		brc = new Point(x2, y2);

		center = new Point((tlc.x + brc.x) / 2, (tlc.y + brc.y) /2);
		
		oWidth = brc.x - tlc.x;
		oHeight = brc.y - tlc.y;
		
		imgFile = new File(imgFilePath);
		try {
			img = ImageIO.read(imgFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates a GameObject based on two corners as arrays
	 * @param topLeftCorner as an array of size 2 the top left corner of the object
	 * @param bottomRightCorner as an array of size 2 the bottom right corner of the object 
	 * @param imgFilePath path to the image file to be loaded
	 */
	public GameObject(int[] topLeftCorner, int bottomRightCorner[], String imgFilePath){
		tlc.setLocation(topLeftCorner[0], topLeftCorner[1]);
		brc.setLocation(bottomRightCorner[0], bottomRightCorner[1]);

		center = new Point(tlc.x + brc.x / 2, tlc.y + brc.y /2);
		
		oWidth = brc.x - tlc.x;
		oHeight = brc.y - tlc.y;
		
		imgFile = new File(imgFilePath);
		try {
			img = ImageIO.read(imgFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates a GameObject based on two corners as points
	 * @param topLeftCorner the top left corner of the object
	 * @param bottomRightCorner the bottom right corner of the object
	 * @param imgFilePath path to the image file to be loaded
	 */
	public GameObject(Point topLeftCorner, Point bottomRightCorner, String imgFilePath){
		tlc = topLeftCorner;
		brc = bottomRightCorner;

		center = new Point(tlc.x + brc.x / 2, tlc.y + brc.y /2);
		
		oWidth = brc.x - tlc.x;
		oHeight = brc.y - tlc.y;
		
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
		tlc = new Point(center.x - size/2, center.y - size/2);
		brc = new Point(center.x + size/2, center.y + size/2);

		this.center = center;
		
		oWidth = brc.x - tlc.x;
		oHeight = brc.y - tlc.y;
		
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
	public GameObject(Point center, int width, int height, String imgFilePath){
		tlc = new Point(center.x - width/2, center.y - height/2);
		brc = new Point(center.x + width/2, center.y + height/2);
		
		this.center = center;

		oWidth = brc.x - tlc.x;
		oHeight = brc.y - tlc.y;
		
		imgFile = new File(imgFilePath);
		try {
			img = ImageIO.read(imgFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the image that is associated with this object
	 * @return the objects image
	 */
	public BufferedImage getImage(){return img;}
	
	/**
	 * Gets the top left corner of the current object
	 * @return a point representation of the objects top left corner
	 */
	public Point getTopLeftCorner(){return tlc;}
	
	/**
	 * Gets the bottom right corner of the current object
	 * @return a point representation of the objects bottom right corner
	 */
	public Point getBottomRightCorner(){return brc;}
	
	/**
	 * Gets the center of the current object
	 * @return a point representation of the objects center point
	 */
	public Point getCenter(){return center;}
	
	/**
	 * Gets the width of the current object
	 * @return the width
	 */
	public int getWidth(){return oWidth;}
	
	/**
	 * Gets the height of the current object
	 * @return the height
	 */
	public int getHeight(){return oHeight;}
	
	/**
	 * A symbolic name for an object
	 * @return the name of the object
	 */
	public String getName(){return name;}
	
	/**
	 * Only here to support the older physics engine
	 * @deprecated
	 */
	public void setTLC(Point newTLC){
		tlc = newTLC;
		brc = new Point(tlc.x + oWidth, tlc.y + oHeight);
		
		center = new Point(tlc.x + brc.x / 2, tlc.y + brc.y /2);
	}
	
	/**
	 * Will update the center of the object
	 * @param center the new center point of the object
	 */
	public void setCenter(Point center){
		this.center = center;
		
		tlc = new Point(center.x - oWidth/2, center.y - oHeight/2);
		brc = new Point(center.x + oWidth/2, center.y + oHeight/2);
	}

	/*
	 * 
	 * Private section
	 * 
	 */
	
	//Object position
	
	//Top Left Corner
	private Point tlc;
	//Bottom Right Corner
	private Point brc;
	//The center of the object
	private Point center;
	//Object Width
	private int oWidth;
	//Object Height
	private int oHeight;
	
	//Object texture
	
	//File object of the image
	private File imgFile;
	//The BufferedImage object of the image
	private BufferedImage img;
	
	//Used for network game play
	private static final long serialVersionUID = 1L;
	
	//Name of the object
	private String name;

}