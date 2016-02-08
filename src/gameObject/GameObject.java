package gameObject;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * 
 * @author Jonathan
 * @since 02/06/2016
 */
public class GameObject {

	/**
	 * Creates a GameObject
	 * @param x top left corner x component
	 * @param y top left corner y component
	 * @param x2 bottom right corner x component
	 * @param y2 bottom right corner y component
	 * @param imgFilePath path to the image file to be loaded
	 */
	public GameObject(int x, int y, int x2, int y2, String imgFilePath){
		tlc = new Point(x, y);
		brc = new Point(x2, y2);

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
	 * Creates a GameObject
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
	 * Creates a GameObject
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
	 * Creates a GameObject
	 * @param center
	 * @param size
	 * @param imgFilePath
	 */
	public GameObject(Point center, int size, String imgFilePath){
		tlc = new Point(center.x - size, center.y - size);
		brc = new Point(center.x + size, center.y + size);

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

	public GameObject(Point center, int oWidth, int oHeight, String imgFilePath){
		tlc = new Point(center.x - oWidth, center.y - oHeight);
		brc = new Point(center.x + oWidth, center.y + oHeight);
		
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

	public BufferedImage getImage(){return img;}
	public Point getTopLeftCorner(){return tlc;}
	public Point getBottomRightCorner(){return brc;}
	public Point getCenter(){return center;}
	public int getWidth(){return oWidth;}
	public int getHeight(){return oHeight;}
	
	/**
	 * 
	 * @deprecated
	 */
	public void setTLC(Point newTLC){
		tlc = newTLC;
		brc = new Point(tlc.x + oWidth, tlc.y + oHeight);
		
		center = new Point(tlc.x + brc.x / 2, tlc.y + brc.y /2);
	}
	
	public void setCenter(Point center){
		this.center = center;
		
		tlc = new Point(center.x - oWidth, center.y - oHeight);
		brc = new Point(center.x + oWidth, center.y + oHeight);
	}

	/*
	 * 
	 * Private section
	 * 
	 */

	//Object position
	private Point tlc;
	private Point brc;
	private Point center;
	private int oWidth;
	private int oHeight;
	
	//Object texture
	private File imgFile;
	private BufferedImage img;

}