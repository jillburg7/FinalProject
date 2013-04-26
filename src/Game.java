/** 
 * Main Game Action:
 * 	- Keeps track of current game scores, Frogger lives, timer
 * 	- Handles "background" obstables including: 
 * 		- highway traffic
 * 		- river traffic
 * 		- frame boundaries
 * 	- Handles user interfacing; how the user interacts using keyboard
 * 
 * @author JillianJiggs
 *
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import movingObjects.Frogger;
import movingObjects.HighwayTraffic;
import movingObjects.RiverTraffic;
import movingObjects.Traffic;


public class Game extends Frame implements KeyListener, MouseListener {

	/**
	 * Object scale factor
	 */
	private final int SCALE = 20;

	/**
	 * A handle on the window; terminates window when requested.
	 */
	private WindowCloser window;

	/**
	 * So the System doesn't try to paint the buttons before they are
	 * initialized
	 */
	private boolean initializationComplete = false;

	/** 
	 * To tell if the game has started or not
	 */
	private boolean gameStarted = false;

	/**
	 * To decided it frogger died or game over
	 */
	private boolean dead = false;

	/**
	 * Player character
	 */
	private Frogger frogger;

	/**
	 * Handles the game timer to display amount of time elapsed in the
	 * round. Starts the timer thread while the game is being played
	 */
	private TimerDisplay timerDisplay;

	/**
	 * Array of Traffic Hazards, continuously moving on the window
	 */
	private Traffic[] traffic = new Traffic[10];

	/**
	 * Play again button
	 */
	private Button butt;


	/**
	 * Starts game
	 */
	public static void main(String[] args) {
		new Game();
	}

	/**
	 * Default constructor - sets up the frame & all objects to be 
	 * drawn in the window.
	 */
	public Game() {
		setTitle("Frogger!"); // name, location and size of our frame
		setBounds(0, 150, 20*SCALE, 25*SCALE);
		setBackground(Color.BLACK);
		setVisible(true);

		window = new WindowCloser();
		addWindowListener(window);
		
		addKeyListener(this);

		frogger = new Frogger();
		timerDisplay = new TimerDisplay(this, 10);
		butt = new Button("Try Again", 175, 13*SCALE, 50, 50);
		initialize(traffic);

		repaint();
		
		initializationComplete = true;
	}

	private void initialize(Traffic[] movingObjects) {
		// initializes trucks
		for(int i = 0; i < movingObjects.length/2; i++) {

			if (i % 2 == 0)
				movingObjects[i] = new HighwayTraffic(this, Color.green, 2, (20*(i%2))*SCALE, (i+16)*SCALE);
			else
				movingObjects[i] = new HighwayTraffic(this, Color.pink, 3, (20*(i%2))*SCALE, (i+16)*SCALE);
		}

		// initializes logs
		for(int i = movingObjects.length/2; i < movingObjects.length; i++) {
			if (i % 2 == 0)
				movingObjects[i] = new RiverTraffic(this, Color.white, 3, (20*(i%2))*SCALE, (i+2)*SCALE);
			else
				movingObjects[i] = new RiverTraffic(this, Color.white, 2, (20*(i%2))*SCALE, (i+2)*SCALE);
		}
		
		for(int i = 0; i<traffic.length; i++) {
			if (i % 2 == 0)
				traffic[i].movingRight();
			else
				traffic[i].movingLeft();
		}
	}


	/**
	 * Paints the graphics on the window
	 */
	public void paint(Graphics pane) {
		Graphics2D pane2 = (Graphics2D)pane;
		Font myFont = new Font("Monospaced", NORMAL, 20);

		if (gameStarted) {
			startTime();
			checkCollision();
		}
		else if(dead) {	
			pane2.setFont(myFont);
			pane2.setColor(Color.red);
			pane2.drawString("Game Over!", 150, 13*SCALE);
			frogger.lives -= 1;
			butt.paint(pane2);
		}

		if (initializationComplete) {
			pane2.setFont(myFont);
			pane2.setColor(Color.WHITE);
			pane2.drawString("Frogger!", 150, 2*SCALE);
			frogger.paint(pane);
			timerDisplay.paint(pane);
			for(int i=0; i<traffic.length; i++){
				traffic[i].x = traffic[i].x % (10*SCALE);
				traffic[i].paint(pane);
			}
		}
	}

	/**
	 * Starts the game timer, which Counts down the time to complete a
	 * round of Frogger. Only starts the timer when key is pressed and
	 * Frogger makes its first move into oncoming traffic.
	 */
	private void startTime() {
		if((timerDisplay.running == false) && gameStarted) {
			try {
				timerDisplay.startTimer();
			}
			catch (Exception e) {
				System.out.println("GAME OVER!");
				gameStarted = false;
				dead = true;
			}
		}
	}

	/**
	 * Handles what happens when Frogger loses all of its lives
	 */
	private void gameOver() {
		timerDisplay.stopTime();
		for(int i = 0; i < traffic.length; i++)
			traffic[i].stopTraffic();
		Toolkit.getDefaultToolkit().beep();
		repaint();
	}

	/**
	 * To check if the frogger is safe; i.e., not roadkill, fish food,
	 * drownt. 
	 */
	private void checkCollision() {
		for(int i = 0; i<traffic.length; i++){
			if (traffic[i].isInside(frogger.getX(), frogger.getY())) {
				dead = true;
				gameOver();
			}
		}	
		if (dead)
			gameOver();
	}





	/**
	 * KeyListener interface that handles events triggered by keyboard
	 * presses, releases, and typed. 
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (dead == false) {
			gameStarted = true;

			if (e.getKeyCode() == KeyEvent.VK_LEFT)
				frogger.move(-SCALE, 0);
			else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
				frogger.move(SCALE, 0);
			else if (e.getKeyCode() == KeyEvent.VK_UP)
				frogger.move(0, -SCALE);
			else if (e.getKeyCode() == KeyEvent.VK_DOWN)
				frogger.move(0, SCALE);

			repaint();
			checkCollision();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		if (butt.isInside(x, y)) {
			Traffic[] traffic = new Traffic[10];
			initialize(traffic);
			timerDisplay = new TimerDisplay(this, 10);
			frogger.reset();
			dead = false;
			initializationComplete = true;
			repaint();
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
