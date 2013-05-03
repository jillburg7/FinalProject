
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;


/**
 * This class defines the highway objects and how they are drawn and
 * move.
 * @author JillianJiggs
 */
public class HighwayTraffic extends Traffic {

	
	/**
	 * Default constructor - calls parent constructor
	 */
	public HighwayTraffic(){
		super();
	}
	
	/**
	 * Constructor to set up moving highway objects
	 * @param mainGame	window to paint objects
	 * @param c
	 * @param amount
	 * @param someX	start x-location
	 * @param someY	y-Location
	 */
	public HighwayTraffic(Game mainGame, Color c, int amount, int someX, int someY) {
		super(mainGame, someX);
		x = someX;
		y = someY;
		color = c;
		xCoords = new int[amount];
		objects = new Rectangle[amount];
		setup();
	}
	
	/**
	 * Sets up the array of truck objects to draw.
	 */
	protected void setup() {
		super.setup();
		width = (int) (1.75*SCALE);
	}
	
	/**
	 * Draws highway objects
	 * Overrides parents method
	 */
	public void paint(Graphics pane) {
		super.paint(pane);		// call super class's paint method
		Graphics2D pane2 = (Graphics2D)pane;
		drawTrucks(pane2);
	}
	
	/**
	 * To draw moving trucks
	 * @param pane
	 */
	private void drawTrucks(Graphics2D pane) {
		for(int i=0; i<xCoords.length; i++) {
			xCoords[i] = x + (i*20*SCALE/xCoords.length);	// updates position
			objects[i] = new Rectangle(xCoords[i], y, width, height); //draws actual trucks
			pane.setColor(color);
			pane.draw(objects[i]);

			//front of truck
			if (move < 0) 	// moving LEFT
				pane.draw(new Rectangle(xCoords[i]-width/4, y+1, width/4, height-2));
			
			else 	// MOVING RIGHT
				pane.draw(new Rectangle(xCoords[i]+width, y+1, width/4, height-2));
			
			pane.setColor(Color.LIGHT_GRAY);
			pane.fill(new Rectangle(xCoords[i]+1, y+1, width-1, height-1));
		}
	}
	
	/**
	 * 
	 * @param xPoint
	 * @param yPoint
	 * @return
	 */
	@Override
	public boolean isInside(int xPoint, int yPoint) {
		boolean isInside = false;
		for(int i = 0; i < objects.length; i++) {
			if((objects[i].contains(xPoint, yPoint)) || (objects[i].contains(xPoint+SCALE, yPoint)))
				return true;
		}
		return isInside;
	}
	

}
