package movingObjects;

/**
 * Traffic class defines the animation of objects in the window.
 * 
 * @author JillianJiggs
 */
public class TrafficThread extends Thread {
	
	/**
	 * The object to notify to update position
	 */
	private Traffic moveObject;
	
	/**
	 * Time it takes to start at one side of the window and get to the
	 * other side.
	 */
	protected int counter = 0;
	
	/**
	 * Speed to control how fast the objects are moving based on the
	 * thread speed.
	 */
	private int speedScale;
	
	private long lastFrame = 0;
	
	/**
	 * Default constructor.
	 * No objects are notified of the game timer.
	 */
	public TrafficThread() {
		super("TrafficThread");	// a name for the thread
		moveObject = null;		// no one will be notified
		speedScale = 100;
	}
	
	/**
	 * Constructor intializes the object
	 * @param someTraffic	object to notify
	 */
	public TrafficThread(Traffic someTraffic) {
		super("TrafficThread");		// a name for the thread
		moveObject = someTraffic;	// object to notify
		speedScale = 100;
	}	
	
	/**
	 * To change the speed of the thread
	 * @param speed the scale to increase (or decrease) speed
	 */
	protected void setSpeed(int speed) {
		speedScale += -speed; 
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
		lastFrame = System.nanoTime() - speedScale;
		
		try {
			while (true) {
				counter++;		// increment counter
				Thread.sleep(speedScale - (System.nanoTime() - lastFrame) / 1000000);
				lastFrame = System.nanoTime();
				moveObject.takeNotice();
			}
		} catch (InterruptedException iex) {
			System.out.println("sucks to be you");
		}
	}
}
