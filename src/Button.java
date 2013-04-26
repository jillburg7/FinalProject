import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;

/**
 * Transparent buttons
 * @author JillianJiggs
 *
 */
public class Button {

	/**
	 * Coordinates of upper left corner of button's bounding box
	 */
	protected int x, y;

	/**
	 * Width and height of button's bounding box
	 */
	protected int width, height;

	/**
	 * Whether the button is raised or pushed
	 */
	private boolean up;

	/**
	 * What it says on the button
	 */
	protected String label;

	/**
	 * color of button
	 */
	private Color buttColor;

	/**
	 * The shape of the button
	 */
	protected Shape shape;


	/**
	 * Default constructor
	 */
	public Button(){}


	/**
	 * Creates button with the given label, coordinates and dimensions
	 * @param someLabel what it says on the button
	 * @param someX the left coordinate of the bounding box
	 * @param someY the top coordinate of the bounding box
	 * @param someWidth the width of the bounding box
	 * @param someHeight the height of the bounding box
	 */
	public Button(String someLabel, int someX, int someY,
			int someWidth, int someHeight) {	
		label = someLabel;
		buttColor = new Color(0, 255, 0, 0);
		x = someX;
		y = someY;
		width = someWidth;
		height = someHeight;
		setShape();
	}


	/**
	 * Sets the shape of the button to a rectangle.
	 */
	public void setShape(){
		shape = new Rectangle(x, y, width, height);
	}

	/**
	 * Used to see if clicks are inside button
	 * @param pointX x value to be checked
	 * @param pointY y value to be checked
	 * @return true if point is inside or on border of button
	 */
	public boolean isInside(int pointX, int pointY){
		return shape.contains(pointX,pointY);
	}

	/**
	 * To change whether the button is "up" or "down."
	 */
	public void flip(){
		up = !up;
	}

	/**
	 * draws the button
	 * @param pane
	 */
	public void paint(Graphics pane){
		Graphics2D pane2 = (Graphics2D)pane;	 //So we can work with arbitrary shapes
		pane2.setColor(buttColor);
		pane2.fill(shape);
		pane2.setColor(Color.GREEN);
		int labelWidth = pane2.getFontMetrics().stringWidth(label);
		int labelHeight = pane2.getFontMetrics().getAscent();
		pane2.drawString(label, x + (width - labelWidth)/2, y + (height + labelHeight)/2);
	}


}
