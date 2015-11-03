import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 * The Lightbulb class
 * 
 * Creates a lightbulb object, which "lights up" if it is connected to a circuit
 * 
 * @author James Lee & Jason Qian
 * @version January 22, 2013
 */
public class Lightbulb extends PhysicsObject {

	// Variables
	private boolean onOff;
	private Image lightBulbOnImage;
	private Image lightBulbOffImage;
	private Image currentImage;
	Rectangle lightbulbPosEnd;
	Rectangle lightbulbNegEnd;
	int whichEnd = 0; // 2 = positive, 1 = negative
	boolean posSideConnected = false;
	boolean negSideConnected = false;

	/**
	 * The constructor for the lightbulb class
	 * 
	 * @param x
	 *            the x position of the lightbulb (top left corner)
	 * @param y
	 *            the y position of the lightbulb (top left corner)
	 */
	public Lightbulb(int x, int y) {
		super(x, y, new ImageIcon("lightbulboff.png").getImage());
		lightBulbOffImage = new ImageIcon("lightbulboff.png").getImage();
		lightBulbOnImage = new ImageIcon("lightbulbon.png").getImage();
		currentImage = lightBulbOffImage;
		onOff = OFF;
		lightbulbPosEnd = new Rectangle(x, y + 30, 30, 30);
		lightbulbNegEnd = new Rectangle(x + 30, y + 30, 30, 30);
	}

	/**
	 * Updates the voltage of the lightbulb
	 * 
	 * @param voltage
	 *            the new voltage of the lightbulb
	 */
	public void updateLightbulb(double voltage) {
		if (voltage > 0 && connectedWires.size() >= 2) {
			onOff = ON;
		} else {
			onOff = OFF;
		}
		updateImage();
	}

	/**
	 * Updates the image of the battery, if it is connected it changes from the
	 * off image to the on image
	 * 
	 */
	public void updateImage() {
		if (this.onOff == ON) {
			currentImage = lightBulbOnImage;
		} else if (this.onOff == OFF) {
			currentImage = lightBulbOffImage;
		}
	}

	/**
	 * Returns whether the lightbulb is on or off
	 * 
	 * @return whether the lightbulb is on or off
	 */
	public boolean onOrOff() {
		return this.onOff;
	}

	/**
	 * Which end the wire is connected to
	 * 
	 * @param p
	 *            the point where the user clicked
	 * 
	 * @return the terminal in which the point lies (2 = positive end, 1 =
	 *         negative end)
	 */
	public int connectToWhichEnd(Point p) {
		if (lightbulbPosEnd.contains(p))
			return 2;
		else if (lightbulbNegEnd.contains(p))
			return 1;
		return 0;
	}

	/**
	 * Updates the hitbox of each node (where the user clicks)
	 * 
	 */
	public void updateHitbox() {
		lightbulbPosEnd = new Rectangle(x, y + 30, 30, 30);
		lightbulbNegEnd = new Rectangle(x + 30, y + 30, 30, 30);
	}

	/**
	 * Gives the point where the wires are connected in this given terminal
	 * 
	 * @param terminal
	 *            the terminal where the point lies
	 * 
	 * @return the point in the terminal
	 */
	public Point getTerminalPoint(int terminal) {
		if (terminal == 2)
			return new Point(x + 15, y + 60);
		return new Point(x + 45, y + 60);
	}

	/**
	 * Draws the image
	 * 
	 * @param g
	 *            the graphics
	 */
	public void drawImage(Graphics g) {
		g.drawImage(currentImage, x, y, null);
	}
}