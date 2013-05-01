
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
	int length;
	
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
	public RiverTraffic(Game mainGame, Color c, int amount, int someX, int someY) {
		super(mainGame, someX);
		x = someX;
		y = someY;
		color = Color.blue;
		xCoords = new int[amount];
		saveMe = someX;
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
//	public RiverTraffic(Game mainGame, Color c, int someX, int someY) {
//		super(mainGame);
//		x = someX;
//		y = someY;
//		color = c;
//		setup();
//	}
	
	/**
	 * Sets up river objects to draw
	 */
	protected void setup() {
		super.setup();
		double factor = Math.random();
		if (factor <= 0.15)
			length = (int) ((factor + 0.25) * 200);
		else if (factor >= .50)
			length = 100;
		else
			length = (int) (factor * 200);
	}

	
	/**
	 * Sets up the array of truck objects to draw.
	 * After bugs have been completely removed, this will be the 
	 * default setup() array.
	 */
	private void setupArray(){
//		length = 2*SCALE;
		height = SCALE;
//		for (int i =0; i < xCoords.length; i++)
//			trucks[i] = new Rectangle(xCoords[i], y, length, height);
//		movingRight();
	}
	
	/**
	 * Draws river objects
	 * Overrides parents method
	 */
	public void paint(Graphics pane) {
		super.paint(pane);
		Graphics2D pane2 = (Graphics2D)pane;
//		Color color = new Color(139, 69, 19);
		drawLogs(pane2);
	}

	/**
	 * To draw moving logs
	 * @param pane
	 */
	private void drawLogs(Graphics2D pane) {
		Color BROWN = new Color(205, 133, 63); // LIGHT BROWN
		for(int i=0; i<xCoords.length; i++) {
			xCoords[i] = x+(i*20*SCALE/xCoords.length);	// updates position
			trucks[i] = new Rectangle(xCoords[i], y, length, height);
			pane.setColor(Color.BLACK);
			pane.draw(trucks[i]);
			pane.setColor(BROWN);
			pane.fill(trucks[i]);
		}
	}

	/**
	 * Checks to see if Frogger is "inside" of 
	 */
	@Override
	public boolean isInside(int xPoint, int yPoint) {
		boolean isInside = false;
		for(int i = 0; i < trucks.length; i++) {
			if(!trucks[i].contains(xPoint, yPoint) || !trucks[i].contains(xPoint+SCALE, yPoint))
				return true;
		}
		return false;
	}
	
}
