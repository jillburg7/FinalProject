
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * The class that defines the Frogger game character. This class
 * handles all movement, user interaction, and life-threatening 
 * obstacles that Frogger encounters.
 *  @author JillianJiggs
 *
 */
public class Frogger {

	/**
	 * Object scale factor
	 */
	private final int SCALE = 20;
	
	/**
	 * Location of Frogger
	 */
	private int x, y;
	
	/**
	 * Froggers lives
	 */
	private int lives;
	
	/** 
	 * Default constructor
	 * 	- Initializes data fields
	 */
	public Frogger() {
		x = 9*SCALE;
		y = 23*SCALE;
		lives = 3;
	}
	
		/**
	 * Moves Frogger in the direction specified by keyboard 
	 * @param horizontal
	 * @param vertical
	 */
	public void move(int horizontal, int vertical){
		if ((x+horizontal+1)>0 && (x+horizontal)<(20*SCALE))
			x = x + horizontal;
		if ((y+vertical)>(5*SCALE) && (y+vertical)<(23*SCALE))
			y = y + vertical;	
	}
	
	/**
	 * Draws Frogger
	 * @param pane graphics object to paint Frogger
	 */
	public void paint(Graphics pane) {
		Graphics2D pane2 = (Graphics2D)pane;
		updatePos(pane2);
		drawLives(pane2);
	}
	
	/**
	 * Paints Frogger at the updated location
	 * @param pane graphics object to paint Frogger
	 */
	private void updatePos(Graphics2D pane) {
		pane.setColor(Color.BLACK);
		pane.drawOval(x, y, SCALE, SCALE);
		pane.setColor(Color.GREEN);
		pane.fillOval(x+1, y+1, SCALE-2, SCALE-2);
	}
	
	private void drawLives(Graphics2D pane) {
		for(int i = 0; i < lives; i++) {
			pane.drawOval(20+(5*i*SCALE/6), 24*SCALE, SCALE/2, SCALE/2);
			pane.fillOval(20+(5*i*SCALE/6), 24*SCALE, SCALE/2, SCALE/2);
		}
	}
	
	/**
	 * gets Froggers (top-left) x location
	 * @return x coordinate location
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * gets Froggers (top-left) y location
	 * @return y coordinate location
	 */
	public int getY() {
		return y;
	}
	
	protected int livesLeft() {
		return lives;
	}
	
	public void reset() {
		System.out.println("Frogger = dead");
		lives -= 1;
		x = 9*SCALE;
		y = 23*SCALE;
	}
}
