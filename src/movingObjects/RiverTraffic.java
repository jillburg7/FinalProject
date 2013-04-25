package movingObjects;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;


/**
 * This class defines the river objects and how they are drawn and
 * move.
 * @author JillianJiggs
 */
public class RiverTraffic extends Traffic {
	/**
	 * Default constructor - calls parent constructor
	 */
	public RiverTraffic(){
		super();
	}
	
	/**
	 * Constructor to set up moving river objects
	 * @param mainGame	window to paint objects
	 * @param someX
	 * @param someY
	 */
	public RiverTraffic(Frame mainGame, Color c, int amount, int someX, int someY) {
		super(mainGame);
		x = someX;
		y = someY;
		color = Color.blue;
		xCoords = new int[amount];
		for(int i=0; i<xCoords.length; i++)
			xCoords[i] = x+(i*20*SCALE/amount);
		trucks = new Rectangle[amount];
		setupArray();
	}
	
	/**
	 * Constructor to set up moving river objects
	 * @param mainGame	window to paint objects
	 * @param c		color to paint objects
	 * @param someX
	 * @param someY
	 */
	public RiverTraffic(Frame mainGame, Color c, int someX, int someY) {
		super(mainGame);
		x = someX;
		y = someY;
		color = c;
		setup();
	}
	
	/**
	 * Sets up river objects to draw
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
		width = (int) (1.5*SCALE);
		height = SCALE;
		for (int i =0; i < xCoords.length; i++)
			trucks[i] = new Rectangle(xCoords[i], y, width, height);
		movingRight();
	}
	
	/**
	 * Draws river objects
	 * Overrides parents method
	 */
	public void paint(Graphics pane) {
		super.paint(pane);
		Graphics2D pane2 = (Graphics2D)pane;
//		drawLog(pane2);
//		pane2.setBackground(Color.BLUE);
		for(int i=0; i<xCoords.length; i++) {
			xCoords[i] = x+(i*20*SCALE/xCoords.length);	// updates position
			trucks[i] = new Rectangle(xCoords[i], y, width, height);
			pane2.draw(trucks[i]);
			pane2.setColor(Color.orange);
			pane2.fill(trucks[i]);
		}
	}
	
	/**
	 * To draw one moving log
	 * @param pane2 to draw with 
	 */
	private void drawLog(Graphics2D pane){
		pane.setColor(color);
		pane.draw3DRect(x, y, width, height, true);
		pane.setColor(Color.ORANGE);
		pane.fill3DRect(x+1, y+1, width-1, height-1, true);
	}

	/**
	 * To draw moving logs
	 * @param pane
	 */
	private void drawLogs(Graphics2D pane) {
		
	}

	/**
	 * Checks to see if Frogger is "inside" of 
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
