
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
	 * @param someX
	 * @param someY
	 */
	public HighwayTraffic(Game mainGame, Color c, int amount, int someX, int someY) {
		super(mainGame, someX);
		x = someX;
		y = someY;
		color = c;
		xCoords = new int[amount];
//		if x
//		for(int i=0; i<xCoords.length; i++)
//			xCoords[i] = x+(i*20*SCALE/amount);
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
//	public HighwayTraffic(Game mainGame, Color c, int someX, int someY) {
//		super(mainGame);
//		x = someX;
//		y = someY;
//		color = c;
//		setup();
//	}
	
	/**
	 * Sets up truck objects to draw
	 */
	protected void setup() {
		super.setup();
	}
	
	/**
	 * Sets up the array of truck objects to draw.
	 * After bugs have been completely removed, this will be the 
	 * default setup() array.
	 */
	private void setupArray(){
		width = (int) (1.75*SCALE);
		height = SCALE;
//		for (int i =0; i < xCoords.length; i++)
//			trucks[i] = new Rectangle(xCoords[i], y, width, height);
//		movingRight();
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
			xCoords[i] = x+(i*20*SCALE/xCoords.length);	// updates position
			pane.setColor(color);
			
			
			// this isn't being checked for collision!! - fix
			if (move < 0) {	// moving LEFT
//				xCoords[i] = x-(i*20*SCALE/xCoords.length);	// updates position
				pane.draw(new Rectangle(xCoords[i]-width/4, y+1, width/4, height-2));
			}
			else {	// MOVING RIGHT
//				xCoords[i] = x+(i*20*SCALE/xCoords.length);	// updates position
				pane.draw(new Rectangle(xCoords[i]+width, y+1, width/4, height-2));
			}
			trucks[i] = new Rectangle(xCoords[i], y, width, height);
			pane.draw(trucks[i]);
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
			if((trucks[i].contains(xPoint, yPoint)) || (trucks[i].contains(xPoint+SCALE, yPoint)))
				return true;
		}
		return isInside;
	}
	

}
