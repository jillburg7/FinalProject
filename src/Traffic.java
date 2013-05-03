

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;

/**
 * Parent class object for all moving traffic - hence, Frogger-life-
 * threatening hazard objects. Handles updating position of movement,
 * which is based on the TrafficThread. Also includes everything in
 * common to all moving hazards in the game.
 * 
 * @author JillianJiggs
 */
public abstract class Traffic {

	/**
	 * refresh the timer graphics
	 */
	private Game gameFrame;
	
	/**
	 * Object scale factor
	 */
	protected final int SCALE = 20;
	
	/**
	 * Dimensions and location of objects
	 */
	protected int x, y, width, height;

	/**
	 * Color to paint objects
	 */
	protected Color color;
	
	/**
	 * Coordinates of other objects in same "lane" of traffic
	 */
	public int[] xCoords;

	/**
	 * Position updater - based on thread
	 */
	protected int move;
	
	/**
	 * To draw multiple moving objects in the same "lane" of traffic
	 */
	protected Shape[] objects;
	
	/**
	 * Default constructor for Traffic objects
	 */
	public Traffic() {}
	
	/**
	 * 
	 * @param mainGame
	 * @param someX
	 */
	public Traffic(Game mainGame, int someX) {
		gameFrame = mainGame;
		x = someX;
	}
	
	/**
	 * Sets up objects to draw
	 */
	protected void setup() {
		width = 2*SCALE;
		height = SCALE;
	}
	
	/**
	 * Amount to redraw the objects moving left
	 */
	public void movingLeft(){
		move = -5;
	}
	
	/**
	 * Amount to redraw the objects moving right
	 */
	public void movingRight(){
		move = 5;
	}
	
	/**
	 * 
	 */
	public void updatePosition() {
		x = x % (10*SCALE);
		if (move < 0 && (x % (10*SCALE) == 0))	// LEFT
			x = 10*SCALE;
	}
	
	/**
	 * Paint method to update the position of objects for the child
	 * classes to redraw the location of the traffic objects.
	 * @param pane	to paint...nothing here :)
	 */
	public void paint(Graphics pane) {
		if(gameFrame.running) {
			x = x + move;	// moves objects position even time its repainted
			updatePosition();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public int[] getxCoords() {
		return xCoords;
	}
	
	/**
	 * @return x-coordinate, which is updated each time the game is 
	 * repainted
	 */
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	/**
	 * 
	 * @param xPoint
	 * @param yPoint
	 * @return
	 */
	public abstract boolean isInside(int xPoint, int yPoint);

}
