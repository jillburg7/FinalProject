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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import movingObjects.Frogger;
import movingObjects.HighwayTraffic;
import movingObjects.RiverTraffic;
import movingObjects.Traffic;


public class Game extends Frame implements KeyListener {
	
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
	
	private Traffic[] trucks = new Traffic[3];
	
	
	
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
		setBounds(150, 150, 20*SCALE, 25*SCALE);
		setBackground(Color.BLACK);
		
		
		setVisible(true);
		
		window = new WindowCloser();
		addWindowListener(window);
		
		addKeyListener(this);
		
		frogger = new Frogger();
		timerDisplay = new TimerDisplay(this, 10);
		
		traffic[0] = new HighwayTraffic(this, Color.GREEN,	1*SCALE,	18*SCALE);
		traffic[1] = new HighwayTraffic(this, Color.PINK,	19*SCALE,	19*SCALE);
		traffic[2] = new HighwayTraffic(this, Color.RED, 	1*SCALE,	20*SCALE);
		traffic[3] = new HighwayTraffic(this, Color.CYAN,	19*SCALE,	21*SCALE);
		
		traffic[4] = new HighwayTraffic(this, 1*SCALE, 22*SCALE);
		
//		initialize(traffic);
		
		
		traffic[5] = new RiverTraffic(this, Color.ORANGE, 19*SCALE, 5*SCALE);
		traffic[6] = new RiverTraffic(this, Color.ORANGE, 1*SCALE, 6*SCALE);
		traffic[7] = new RiverTraffic(this, Color.ORANGE, 19*SCALE, 7*SCALE);
		traffic[8] = new RiverTraffic(this, Color.ORANGE, 1*SCALE, 8*SCALE);
		traffic[9] = new RiverTraffic(this, Color.ORANGE, 19*SCALE, 9*SCALE);

		repaint();

		for(int i = 0; i<traffic.length; i++) {
			if (i % 2 == 0)
				traffic[i].movingRight();
			else
				traffic[i].movingLeft();
		}
		
		
		initializationComplete = true;
	}
	
	private void initialize(Traffic[] movingTrucks) {
		for(int i = 0; i < movingTrucks.length; i++) {
			movingTrucks[i] = new HighwayTraffic(this, (20*(i%2))*SCALE, (i+18)*SCALE);
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
//		System.out.println("PAINTING");
		Graphics2D pane2 = (Graphics2D)pane;
		Font myFont = new Font("Monospaced", NORMAL, 20);
		
		if (initializationComplete) {
			pane2.setFont(myFont);
			pane2.setColor(Color.WHITE);
			pane2.drawString("Frogger!", 150, 2*SCALE);
			frogger.paint(pane);
			timerDisplay.paint(pane);
			for(int i=0; i<traffic.length; i++){
				traffic[i].paint(pane);
//				if((traffic[i].x % (20*SCALE) == 0) || (traffic[i].x == 0)) {
////					initialize(traffic);
//					traffic[i].reset();
//				}
			}
		}
		
		
		
		if (gameStarted)
			startTime();
		else if(dead)
			gameOver();
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
//		if(frogger.lives == 0) 
		for(int i=0; i<traffic.length; i++)
			traffic[i].reset();
	}
	
	/**
	 * To check if the frogger is safe; i.e., not roadkill, fish food,
	 * drownt. 
	 */
	private void checkCollision() {
		if(frogger.compareTo(traffic[4]) == 0)	// compares objects
			gameOver();
//			Toolkit.getDefaultToolkit().beep();
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
}
