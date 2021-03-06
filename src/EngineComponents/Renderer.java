package EngineComponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gameObject.GameObject;
import geometry.Point;

/**
 * 
 * @author Jonathan
 * @since 02/06/2016
 * <p>
 *  This class is used to draw objects to the screen
 */
public class Renderer {

	/**
	 * Creates a Renderer object with the specified size and background colour
	 * @param g2d A handle to the graphics that is to be drawn to
	 * @param width the total width of the graphics to be drawn to
	 * @param height the total height of the graphics to be drawn to
	 * @param background the colour that the system should clear the screen to
	 */
	public Renderer(Graphics2D g2d, int width, int height, Color background, Font font){
		this.g2d = g2d;
		sWidth = width;
		sHeight = height;

		g2d.setFont(font);

		img = new BufferedImage(sWidth, sHeight, BufferedImage.TYPE_INT_ARGB);
		backgroundImg = new BufferedImage(sWidth, sHeight, BufferedImage.TYPE_INT_ARGB);

		for(int i = 0; i < sWidth; ++i)
			for(int j = 0; j < sHeight; ++j)
				backgroundImg.setRGB(i, j, background.getRGB());

		g2d.setBackground(background);
	}

	/**
	 * Will draw the objects in the array list to the screen
	 * @param objs an arrayList of GameObjects
	 */
	public void render(ArrayList<GameObject> objs, ArrayList<String> strings, ArrayList<Point> points){
		drawer = (Graphics2D) img.getGraphics();

		for(GameObject obj:objs)
			drawer.drawImage(
					obj.getImage(),
					obj.getTopLeftCorner().getX(),
					obj.getTopLeftCorner().getY(),
					obj.getBottomRightCorner().getX(),
					obj.getBottomRightCorner().getY(),
					0,
					0, 
					obj.getWidth(),
					obj.getHeight(),
					null);

		if(strings != null)
			for(int i = 0; i < strings.size(); ++i)
				((Graphics2D)(img.getGraphics())).drawString(strings.get(i), points.get(i).getX(), points.get(i).getY());

		g2d.drawImage(img, 0, 0, sWidth, sHeight, 0, 0, sWidth, sHeight, null);
	}

	public void render(String text, int x, int y){
		((Graphics2D)(img.getGraphics())).drawString(text, x, y);

	}

	/**
	 * get the width of this render objects graphics context
	 * @return the width
	 */
	public int getWidth(){return sWidth;}

	/**
	 * get the height of this render objects graphics context
	 * @return the height
	 */
	public int getHeight(){return sHeight;}

	/*
	 * 
	 * Private portion of code
	 * 
	 */

	private Graphics2D g2d;
	private Graphics2D drawer;
	private	BufferedImage img;
	private BufferedImage backgroundImg;
	private int sWidth;
	private int sHeight;
}
