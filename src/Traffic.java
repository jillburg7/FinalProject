

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
	 * Thread that animates the movement of traffic objects
	 */
//	private TrafficThread movingHazard;
	
	/**
	 * Speed of object moving across display
	 */
	private int speed;
	
	/**
	 * Object scale factor
	 */
	protected final int SCALE = 20;
	
	/**
	 * Dimensions and location of trucks
	 */
	public int x;
	protected int y, width, height;

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
	 * To draw objects
	 */
	protected Shape object;
	
	/**
	 * To draw multiple moving objects in the same "lane" of traffic
	 */
	protected Shape[] trucks;
	
	public Traffic() {}
	
	public Traffic(Game mainGame, int someX) {
		gameFrame = mainGame;
//		movingHazard = new TrafficThread(this);
//		running = true;
//		movingHazard.start();
		setup();
	}
	
	/**
	 * Sets up objects to draw
	 */
	protected void setup() {
		width = 2*SCALE;
		height = SCALE;
		speed = 1;
	}
	
	/**
	 * Amount to redraw the objects moving left
	 */
	public void movingLeft(){
		move = -5;
		x=400;
	}
	
	/**
	 * Amount to redraw the objects moving right
	 */
	public void movingRight(){
		move = 5;
	}
	
	
	/**
	 * Controls how fast the highway traffic is moving
	 */
	public void saved() {
		if (move < 0){}	// moving Left <-
	}
	
	/**
	 * Paint method to update the position of objects for the child
	 * classes to redraw the location of the traffic objects.
	 * @param pane	to paint...nothing here :)
	 */
	public void paint(Graphics pane) {
		if(gameFrame.running)
			x = x + move;	
	}
	
	/**
	 * 
	 * @return
	 */
	public int[] getxCoords() {
		return xCoords;
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
	
	/**
	 * While the Hazard thread is running, the traffic graphics need 
	 * to be refreshed to animate moving across the screen.
	 */
	protected void takeNotice() {
		if (gameFrame.running)
			gameFrame.repaint();
	}
	
	/**
	 * Kills game (currently), evently to be used for reset of game 
	 * and rounds, until game is over = no more Frogger lives. RIP
	 */
	public void stopTraffic() {
		gameFrame.running = false;
	}
	
	public void restart() {
		gameFrame.running = true;
		
	}
}
