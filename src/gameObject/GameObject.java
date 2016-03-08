package gameObject;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

import java.awt.Color;
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
	private transient File imgFile; // File object of the image
	private BufferedImage img; // The BufferedImage object of the image
	private transient Color colour; //The color to draw the object if there is no supplied image

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

	public GameObject(double x, double y, double x2, double y2, String imgFilePath, Color colour){

		this.phys = new Object2D(x, y, x2, y2);

		this.colour = colour;

		setImage(imgFilePath);
	}

	/**
	 * Creates a GameObject based on the center the width and the height
	 * @param center the center of the object
	 * @param width the length of the side along the x axis
	 * @param height the length of the side along the y axis
	 * @param imgFilePath path to the image file
	 */

	public GameObject(Point center, double width, double height, String imgFilePath, Color colour){

		this.phys = new Object2D(center, width, height);

		this.colour = colour;

		setImage(imgFilePath);

	}

	/**
	 * Creates a GameObject that is square based on the center and the size
	 * @param center the center of the object
	 * @param size the size of the object this is the size of any side
	 * @param imgFilePath the path to the image file
	 */

	public GameObject(Point center, double size, String imgFilePath, Color colour){

		this(center, size, size, imgFilePath, colour);

		setImage(imgFilePath);
	}

	/**
	 * Creates a GameObject based on two corners as arrays
	 * @param topLeftCorner as an array of size 2 the top left corner of the object
	 * @param bottomRightCorner as an array of size 2 the bottom right corner of the object 
	 * @param imgFilePath path to the image file to be loaded
	 */

	public GameObject(double[] topLeftCorner, double bottomRightCorner[], String imgFilePath, Color colour){

		this(topLeftCorner[0], topLeftCorner[1], 
				bottomRightCorner[0], bottomRightCorner[1], imgFilePath, colour);

		setImage(imgFilePath);
	}

	/**
	 * Creates a GameObject based on two corners as points
	 * @param topLeftCorner the top left corner of the object
	 * @param bottomRightCorner the bottom right corner of the object
	 * @param imgFilePath path to the image file to be loaded
	 */

	public GameObject(Point topLeftCorner, Point bottomRightCorner, String imgFilePath, Color colour){

		this(topLeftCorner.x, topLeftCorner.y,
				bottomRightCorner.x, bottomRightCorner.y, imgFilePath, colour);

		setImage(imgFilePath);
	}

	/**
	 * Returns the image that is associated with this object
	 * @return the objects image
	 */

	public BufferedImage getImage(){

		return img;
	}

	/**
	 * Gets a copy of the physical state of the object
	 * @return a point representation of the objects center point
	 */

	public Object2D getPhys(){
		return new Object2D(phys);
	}
	
	public void setSpeed(double spd)
	{
		phys.speed = spd;
	}
	
	public void setDirection(double dir)
	{
		phys.direction = dir;
	}
	
	public void bouncehorizontal()
	{
		phys.reflectHorizontal();
	}
	
	
	/**
	 * Gets a copy of top left corner of the current object
	 * @return a point representation of the objects top left corner
	 */

	public Point getTopLeftCorner(){

		return new Point(phys.tlc);
	}

	/**
	 * Gets a copy of the bottom right corner of the current object
	 * @return a point representation of the objects bottom right corner
	 */

	public Point getBottomRightCorner(){

		return new Point(phys.brc);
	}

	/**
	 * Gets a copy of the center of the current object
	 * @return a point representation of the objects center point
	 */

	public Point getCenter(){

		return new Point(phys.center);
	}

	/**
	 * Gets the width of the current object
	 * @return the width
	 */

	public int getWidth(){

		return (int)Math.round(phys.width);
	}

	/**
	 * Gets the height of the current object
	 * @return the height
	 */

	public int getHeight(){

		return (int)Math.round(phys.height);
	}

	/**
	 * A symbolic name for an object
	 * @return the name of the object
	 */

	public String getName(){

		return name;
	}

	/**
	 * Will update the state of the Object2D undernearth GameObject
	 * as if it had a different center
	 * @param center the new center point of the object
	 */

	public void setCenter(Point center){

		this.phys.reCenter(center);
	}
	
	/**
	 * Will update the state of the Object2D undernearth GameObject
	 * as if it had a different top left corner
	 * @param center the new center point of the object
	 */

	public void setTopLeftCorner(Point center){

		phys.setTLC(center);
	}
	
	/**
	 * Will update the state of the Object2D undernearth GameObject
	 * as if it had a different bottom right corner
	 * @param center the new center point of the object
	 */

	public void setBottomRightCorner(Point center){

		phys.setBRC(center);
	}
	
	

	public void setColour(Color colour){
		this.colour = colour;
		setImage(imgFile.getAbsolutePath());
	}
	
	
	public void setName(String name){
		this.name = name;
	}

	private void setImage(String imgFilePath){
		
		boolean imgAvailable = true;

		if(imgFilePath == null)
			imgAvailable = false;
		
		else{
			imgFile = new File(imgFilePath);
			try {
				if(colour == null)
					colour = Color.white;
				
				img = drawBackground(ImageIO.read(imgFile));
			} catch (IOException e) {
				e.printStackTrace();
				imgAvailable = false;
			}
		}

		if(!imgAvailable){
			drawColour();
		}
	}
	
	private void drawColour(){
		int w = (int)Math.round(phys.width);
		int h = (int)Math.round(phys.height);
		img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		for(int i = 0; i < w; ++i)
			for(int j = 0; j < h; ++j)
				img.setRGB(i, j, colour.getRGB());
	}
	
	private BufferedImage drawBackground(BufferedImage imgOverlay){
		int w = (int)Math.round(phys.width);
		int h = (int)Math.round(phys.height);
		BufferedImage temp = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		
		for(int i = 0; i < w; ++i)
			for(int j = 0; j < h; ++j)
				temp.setRGB(i, j, colour.getRGB());
		
		w = imgOverlay.getWidth() < w ? imgOverlay.getWidth() : w;
		h = imgOverlay.getHeight() < h ? imgOverlay.getHeight() : h;
		
		for(int i = 0; i < w; ++i)
			for(int j = 0; j < h; ++j)
				temp.setRGB(i, j, (imgOverlay.getRGB(i, j) & (255 << 24)) < 0 ? imgOverlay.getRGB(i, j) : temp.getRGB(i, j));
		
		return temp;
	}
}