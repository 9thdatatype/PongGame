package gameObject;

import java.awt.Color;

import geometry.Point;

@SuppressWarnings("serial")
public class GameButton extends GameObject {

	public GameButton(double x, double y, double x2, double y2, String imgFilePath, Color colour) {
		super(x, y, x2, y2, imgFilePath, colour);
	}

	public GameButton(Point center, double width, double height, String imgFilePath, Color colour){
		super(center, width, height, imgFilePath, colour);
	}
	
	public GameButton(Point center, double size, String imgFilePath, Color colour){
		super(center, size, imgFilePath, colour);
	}
	
	public GameButton(double[] topLeftCorner, double bottomRightCorner[], String imgFilePath, Color colour){
		super(topLeftCorner, bottomRightCorner, imgFilePath, colour);
	}
	
	public GameButton(Point topLeftCorner, Point bottomRightCorner, String imgFilePath, Color colour){
		super(topLeftCorner, bottomRightCorner, imgFilePath, colour);
	}
	
	public void setSelected(boolean isSelected){
		selected = isSelected;
	}
	
	public void setSelectedColour(Color selectedColour){
		this.selectedColour = selectedColour;
	}
	
	public boolean isSelected(){return selected;}
	
	private boolean selected;
	private Color selectedColour = Color.black;
	
}