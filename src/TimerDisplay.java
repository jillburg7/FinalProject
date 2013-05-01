import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Toolkit;

/**
 * This class displays the timer graphics and updates accordingly when
 * notified by the Timer thread to repaint.
 * 
 * @author JillianJiggs
 *
 */
public class TimerDisplay {
	
	/**
	 * refresh the timer graphics
	 */
	private Frame gameFrame;
	
	/**
	 * refresh the timer
	 */
	protected boolean running = false;

	/**
	 * count down timer till the player is out of time
	 */
	private TimerThread timer;
	
	/**
	 * Maximum time the player has to complete the round.
	 */
	private final int CLOCK;
	
	/**
	 * pointer to timer decrementer
	 */
	private int timeElapsed;
	
	/**
	 * Location & dimensions of time display
	 */
	private int x = 250, y = 480, width = 100, height = 18;
	
	
	/**
	 * Default constructor - does nothing important.
	 */
	public TimerDisplay() {
		CLOCK = 5;
	}
	
	/**
	 * Constructor to initialize the timer thread and the display to
	 * paint on the window.
	 * @param mainGame	window to paint objects
	 */
	public TimerDisplay(Frame mainGame, int clockTime) {
		CLOCK = clockTime;
		gameFrame = mainGame;			// the frame to paint stuff
		timer = new TimerThread(this);	// initial thread
		timer.setTimer(CLOCK);		// sets the initial thread timer
	}

	/**
	 * Starts running the timer thread
	 */
	protected void startTimer() {
		running = true;
		timer.start();
	}
	
	/**
	 * Paints & refreshes the timer display in the game window.
	 * @param pane graphics object to paint the window
	 */
	public void paint(Graphics pane) {
		pane.setColor(Color.YELLOW);
		pane.drawRect(x, y, width, height);
		pane.fillRect(x, y, width, height);
		pane.drawString("TIME", x + width +5, y + 15);
		
		timeElapsed = timer.decrementTimer;
		
		if ((timeElapsed > 0) && (timeElapsed <= CLOCK)) {
			updateTimer(pane);
			if (timeElapsed == CLOCK) {
				running = false;
				Toolkit.getDefaultToolkit().beep();	// beeps when times up
			}
		}
	}

	/**
	 * Updates timer displayed in the window
	 * @param pane	the object to paint with
	 */
	protected void updateTimer(Graphics pane) {
		int blackWidth = timeElapsed * (width/CLOCK)-1;
		pane.setColor(Color.BLACK);
		pane.fillRect(x+1, y+1, blackWidth, height-1);
	}
	
	/**
	 * While the timer thread is running, the timer graphics need to 
	 * be refreshed to display the amount of time left in the round.
	 */
	protected void takeNotice() {
		if (running) {
			gameFrame.repaint();
		}
	}
	
	protected void stopTime() {
		running = false;
//		timer.stop();
	}
	
}
