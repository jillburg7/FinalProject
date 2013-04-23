import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Implements a window listener to detect when a user wishes to close, or exit,
 * the application by using the ESC key or Exit button in the window panel.
 */
public class WindowCloser implements WindowListener {

	@Override
	public void windowOpened(WindowEvent e) {}

	/**
	 * Terminates window
	 */
	@Override
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}

	/**
	 * Terminates window
	 */
	@Override
	public void windowClosed(WindowEvent e) {
		System.exit(0);
	}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowDeactivated(WindowEvent e) {}

}
