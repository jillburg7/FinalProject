

/**
 * TimerThread class defines a resetable timer, which beeps when the 
 * timerDisplay set is has elapsed. The TimerThread will notify the object 
 * registered when timer is instantiated.
 * 
 * @author JillianJiggs
 */
public class TimerThread extends Thread {
	
	/** 
	 * The amount of elapsed time during game play.
	 */
	protected int decrementTimer = 0; 
	
	/**
	 * Initial amount of time to complete the round before the game
	 * is over;
	 */
	private int clock;
	
	/**
	 * The object to notify to update time & time display
	 */
	private TimerDisplay timerDisplay;

	/**
	 * Default constructor.
	 * No objects are notified of the game timer.
	 */
	public TimerThread() {
		super("TimerThread");	// a name for the thread being created
		timerDisplay = null;		// no one will be notified
	}
	
	/**
	 * Constructor intializes the object
	 * @param timeListener	object to notify
	 */
	public TimerThread(TimerDisplay timeListener) {
		super("TimerThread");		// a name for the thread being created
		timerDisplay = timeListener;	// who will be notified
	}
	
	/**
	 * Sets the initial time for each round of the game.
	 * @param countdown	seconds until round is over
	 */
	public void setTimer(int countdown) {
		clock = countdown;
	}
	
	/**
	 * If this thread was constructed using a separate Runnable run
	 * object, then that Runnable object's run method is called;
	 * otherwise, this method does nothing and returns.
	 * 
	 * Overrides the run() in the parent class
	 */
	@Override
	public void run() {
		try {
			while (decrementTimer < clock) {	// timer is running
				decrementTimer ++;				// decrements the time
				Thread.sleep(1000L);
				timerDisplay.takeNotice();
			}
			System.out.println("Times up!");
		} catch (InterruptedException iex) {}
	}


	
}
