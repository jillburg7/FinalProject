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
	private Traffic[] traffic;
	
	/**
	 * 
	 */
	private TrafficThread trafficThread;
	
	/**
	 * 
	 */
	public boolean running;

	
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
		setTitle("Frogger!"); 	// name, location and size of da frame
		setBounds(0, 150, 20*SCALE, 25*SCALE);
		setBackground(Color.BLACK);
		setVisible(true);

		window = new WindowCloser();
		addWindowListener(window);
		
		addKeyListener(this);
		
		frogger = new Frogger();
		timerDisplay = new TimerDisplay(this, 50);
		traffic = new Traffic[10];
		initialize(traffic);
		running = true;
		trafficThread = new TrafficThread(this);
		trafficThread.start();
	
		initializationComplete = true;
	}

	/**
	 * To set up positions, shapes, and moving directions for traffic 
	 * objects
	 * @param movingObjects	array of objects to initialize
	 */
	private void initialize(Traffic[] movingObjects) {
		// initializes trucks
		for(int i = 0; i < movingObjects.length/2; i++) {

			if (i % 2 == 0) 	// [0], [2], [4] 	RIGHT
				movingObjects[i] = new HighwayTraffic(this, Color.green, 2, 15*SCALE, (i+16)*SCALE);
			else				// [1], [3] 		LEFT
				movingObjects[i] = new HighwayTraffic(this, Color.pink, 2, 8*SCALE, (i+16)*SCALE);
		}

		// initializes logs
		for(int i = movingObjects.length/2; i < movingObjects.length; i++) {
			if (i % 2 == 0)	// [6], [8] 		RIGHT
				movingObjects[i] = new RiverTraffic(this, 2, 10*SCALE, (i+2)*SCALE);
			else			// [5], [7], [9]	LEFT
				movingObjects[i] = new RiverTraffic(this, 2, 5*SCALE, (i+2)*SCALE);
		}
		
		for(int i = 0; i < movingObjects.length; i++) {
			if (i % 2 == 0)		// even, starts at index [0] -> [8]
				movingObjects[i].movingRight();
			else				// odd; starts at index [1] -> [9]
				movingObjects[i].movingLeft();
		}
	}


	/**
	 * Paints the graphics on the window
	 */
	public void paint(Graphics pane) {
		Graphics2D pane2 = (Graphics2D)pane;
		Font myFont = new Font("Monospaced", NORMAL, 20);

		getBackground(pane);
		
		if (gameStarted) {
			startTime();
			checkCollision();
		}
		else if(dead) {
			pane2.setFont(myFont);
			pane2.setColor(Color.red);
			pane2.drawString("Game Over!", 145, 13*SCALE);
			pane2.setColor(Color.green);
			if (frogger.livesLeft() != 0)
				pane2.drawString("Try Again", 150, 14*SCALE);
		}

		if (initializationComplete) {
			pane2.setFont(myFont);
			pane2.setColor(Color.WHITE);
			pane2.drawString("Frogger!", 150, 2*SCALE);
			
			timerDisplay.paint(pane);
			
			//RiverTraffic drawn first to draw frogger on top [5] -> [9]
			for(int i = traffic.length/2; i < traffic.length; i++) 
				traffic[i].paint(pane);
			
			frogger.paint(pane);
			
			// HighwayTraffic drawn last so frogger = roadkill [0] -> [4]
			for (int i = 0; i < traffic.length/2; i++)
				traffic[i].paint(pane);
		}
	}
	
	
	
	/**
	 * Paints the background of Frogger
	 * @param pane
	 */
	private void getBackground(Graphics pane) {
		pane.setColor(Color.blue);
		pane.fillRect(0, 7*SCALE,  20*SCALE, 5*SCALE);	// WATER
		
		Color DARK_GREEN = new Color(30, 200, 50);
		pane.setColor(DARK_GREEN);
		pane.fillRect(0, 5*SCALE, 20*SCALE, SCALE);	// BUSHES
		
		for(int i = 0; i < 11; i += 2) {
			pane.setColor(DARK_GREEN);
			pane.fillRect(2*i*SCALE-SCALE, 6*SCALE, 2*SCALE, SCALE);	// bushes
			pane.setColor(Color.yellow);
			pane.fillOval((i*2*SCALE)+25, 6*SCALE, SCALE+10, SCALE);	// GOLDEN LILY PADS
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
		running = false;
		Toolkit.getDefaultToolkit().beep();
		repaint();
		if(frogger.livesLeft() == 0) {}
			System.out.println("GAME OVER!");
			//end GAME!
	}

	/**
	 * To check if the frogger is safe; i.e., not roadkill, fish food,
	 * drownt. 
	 */
	private void checkCollision() {
		for (int i = 0; i < traffic.length/2; i++){
			if (traffic[i].isInside(frogger.getX()-1, frogger.getY()+1))
				dead = true;
		}
		for(int i = traffic.length/2; i < traffic.length; i++){
			if (!traffic[i].isInside(frogger.getX()-1, frogger.getY()+1))
				dead = true;
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
		if ((frogger.livesLeft() != 0) && (e.getKeyCode() == KeyEvent.VK_SPACE)) {
			running = true;
			timerDisplay = new TimerDisplay(this, 50);
			frogger.reset();
			dead = false;
			initializationComplete = true;
			repaint();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}
}
