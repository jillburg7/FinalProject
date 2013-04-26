package movingObjects;
import java.awt.Color;
import java.awt.Frame;
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
	 * @param someX
	 * @param someY
	 */
	public HighwayTraffic(Frame mainGame, Color c, int amount, int someX, int someY) {
		super(mainGame);
		x = someX;
		y = someY;
		color = c;
		xCoords = new int[amount];
		for(int i=0; i<xCoords.length; i++)
			xCoords[i] = x+(i*20*SCALE/amount);
		trucks = new Rectangle[amount];
		setupArray();
	}
	
	/**
	 * Constructor to set up moving highway objects
	 * @param mainGame	window to paint objects
	 * @param c		color to paint objects
	 * @param someX
	 * @param someY
	 */
	public HighwayTraffic(Frame mainGame, Color c, int someX, int someY) {
		super(mainGame);
		x = someX;
		y = someY;
		color = c;
		setup();
	}
	
	/**
	 * Sets up truck objects to draw
	 */
	protected void setup() {
		super.setup();
		object = new Rectangle(x, y, width, height);
	}
	
	/**
	 * Sets up the array of truck objects to draw.
	 * After bugs have been completely removed, this will be the 
	 * default setup() array.
	 */
	private void setupArray(){
		width = (int) (2*SCALE);
		height = SCALE;
		for (int i =0; i < xCoords.length; i++)
			trucks[i] = new Rectangle(xCoords[i], y, width, height);
		movingRight();
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
	 * To draw one moving truck
	 * @param pane2
	 */
	private void drawTruck(Graphics2D pane2){
		pane2.setColor(color);
		pane2.draw3DRect(x, y, width, height, true);
		if (move < 0)
			pane2.draw3DRect(x-SCALE/2, y+1, width/4, height-2, true);
		else
			pane2.draw3DRect(x+width, y+1, width/4, height-2, true);
		pane2.setColor(Color.LIGHT_GRAY);
		pane2.fill3DRect(x+1, y+1, width-1, height-1, true);
	}
	
	/**
	 * To draw moving trucks
	 * @param pane
	 */
	private void drawTrucks(Graphics2D pane) {
		for(int i=0; i<xCoords.length; i++) {
			xCoords[i] = x+(i*20*SCALE/xCoords.length);	// updates position
			pane.setColor(color);
			trucks[i] = new Rectangle(xCoords[i], y, width, height);
			pane.draw(trucks[i]);
			
			// this isn't being checked for collision!! - fix
			if (move < 0)
				pane.draw(new Rectangle(xCoords[i]-SCALE/2, y+1, width/4, height-2));
			else
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
		for(int i = 0; i < trucks.length; i++) {
			if(trucks[i].contains(xPoint, yPoint) || trucks[i].contains(xPoint+SCALE, yPoint))
				return true;
		}
		return isInside;
	}
	

}
