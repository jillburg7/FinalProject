
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;


/**
 * This class defines the river objects and how they are drawn and
 * move.
 * @author JillianJiggs
 */
public class RiverTraffic extends Traffic {
	protected int saveMe;

	
	/**
	 * Default constructor - calls parent constructor
	 */
	public RiverTraffic(){
		super();
	}
	
	/**
	 * Constructor to set up moving river objects
	 * @param mainGame	window to paint objects
	 * @param c
	 * @param amount
	 * @param someX
	 * @param someY
	 */
	public RiverTraffic(Game mainGame, int amount, int someX, int someY) {
		super(mainGame, someX);
		saveMe = someX;
		x = someX;
		y = someY;
		color = new Color(205, 133, 63); // LIGHT BROWN
		xCoords = new int[amount];
		objects = new Rectangle[amount];
		setup();
	}

	
	/**
	 * Sets up the array of river objects to draw
	 */
	protected void setup() {
		super.setup();
		int length;
		double factor = Math.random();
		if (factor <= 0.15)
			length = (int) ((factor + 0.25) * 200);
		else if (factor >= .50)
			length = 100;
		else
			length = (int) (factor * 200);
		
		width = length;
	}

	/**
	 * Draws river objects
	 * Overrides parents method
	 */
	public void paint(Graphics pane) {
		super.paint(pane);
		Graphics2D pane2 = (Graphics2D)pane;
		drawLogs(pane2);
	}

	/**
	 * To draw moving logs
	 * @param pane
	 */
	private void drawLogs(Graphics2D pane) {
		
		for(int i=0; i<xCoords.length; i++) {
			xCoords[i] = x + (i*20*SCALE/xCoords.length);	// updates position
			objects[i] = new Rectangle(xCoords[i], y, width, height);
			
			pane.setColor(Color.BLACK);
			pane.draw(objects[i]);
			pane.setColor(color);
//			pane.fill(objects[i]);
			pane.fill(new Rectangle(xCoords[i]+1, y+1, width-1, height-1));
		}
	}

	/**
	 * Checks to see if Frogger is "inside" of 
	 */
	@Override
	public boolean isInside(int xPoint, int yPoint) {
		boolean isInside = false;
		for(int i = 0; i < objects.length; i++) {
			if(!objects[i].contains(xPoint, yPoint) || !objects[i].contains(xPoint+SCALE, yPoint))
				return true;
		}
		return isInside;
	}
	
}
