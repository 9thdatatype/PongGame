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
		
		width = brc.x - tlc.x;
		height = brc.y - tlc.y;
		
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
		
		width = brc.x - tlc.x;
		height = brc.y - tlc.y;
		
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
		
		width = brc.x - tlc.x;
		height = brc.y - tlc.y;
		
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
		
		width = brc.x - tlc.x;
		height = brc.y - tlc.y;
		
		imgFile = new File(imgFilePath);
		try {
			img = ImageIO.read(imgFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public GameObject(Point center, int width, int height, String imgFilePath){
		tlc = new Point(center.x - width, center.y - height);
		brc = new Point(center.x + width, center.y + height);
		
		this.center = center;

		width = brc.x - tlc.x;
		height = brc.y - tlc.y;
		
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

	public void setImage(BufferedImage img){this.img = img;}
	
	public void setCenter(Point center){
		this.center = center;
		
		tlc = new Point(center.x - width, center.y - height);
		brc = new Point(center.x + width, center.y + height);
	}

	/*
	 * 
	 * Private section
	 * 
	 */

	private Point tlc;
	private Point brc;
	private Point center;
	private int width;
	private int height;
	private File imgFile;
	private BufferedImage img;

}