import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

/**
 * @author Jonathan Spaulding
 * @version 0.0001
 * @since 2016-02-02
 */
public class PongGUI {

	public static int MAX_FPS = 60;

	public int width = 960;
	public int height = 720;

	PongGUI(){}

	PongGUI(int width, int height){
		this.width = width;
		this.height = height;
	}

	public void createAndShowGUI(){
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setSize(width, height);
		jframe.setResizable(false);
		jframe.setVisible(true);
	}

	public void run(){
		g2d = (Graphics2D) jframe.getGraphics();

		BufferedImage img = new BufferedImage(jframe.getWidth(), jframe.getHeight(), BufferedImage.TYPE_INT_ARGB);
		BufferedImage background = new BufferedImage(jframe.getWidth(), jframe.getHeight(), BufferedImage.TYPE_INT_ARGB);

		long startTime, endTime, timeTaken;
		double frameTime = 1000/MAX_FPS;

		int x = 50, y = 50;
		
		for(int i = 0; i < jframe.getWidth(); ++i)
			for(int j = 0; j < jframe.getHeight(); ++j)
				background.setRGB(i, j, Color.white.getRGB());

		Physics engine = new Physics(width, height, 5, 50, 0, 0);
		engine.startSimulation();
		
		while(running){
			startTime = System.currentTimeMillis();
			
			img.setData(background.getRaster());
			
			int array[] = new int[50*50];
			
			for(int i = 0; i < array.length; ++i)
				array[i] = Color.black.getRGB();
			
			engine.updateStates();
			
			x = (int)(engine.getBallPos().x);
			y = (int)(engine.getBallPos().y);
			
			img.setRGB(x, y, 50, 50, array, 0, 0);

			g2d.drawImage(img, 0, 0, this.jframe);
			
			
			endTime = System.currentTimeMillis();

			timeTaken = endTime - startTime;
			//System.out.println(timeTaken);
			if(timeTaken < frameTime)
				try {
					Thread.sleep((long) (frameTime - timeTaken));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}	
		}
	}

	/*
	 * Private portion of code
	 */

	//Visual elements

	private JFrame jframe = new JFrame();
	private Graphics2D g2d;

	// Game Variables

	private boolean running = true;

}
// end class