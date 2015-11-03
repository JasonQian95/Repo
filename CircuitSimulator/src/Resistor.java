import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 * The resistor class
 * 
 * Creates a resistor object, which has a default resistance of 10 Ohms
 * 
 * @author James Lee & Jason Qian
 * @version January 22, 2013
 */
public class Resistor extends PhysicsObject {

	// Variables
	private double resistance;
	Rectangle resistorPosEnd;
	Rectangle resistorNegEnd;
	double voltage;
	private double amperage;
	int whichEnd; // 2 = positive, 1 = negative

	/**
	 * The constructor for a resistor
	 * 
	 * @param resistance
	 *            the resistance of the resistor
	 * @param x
	 *            the x position of the resistor (top left corner)
	 * @param y
	 *            the y position of the resistor (top left corner)
	 */
	public Resistor(int resistance, int x, int y) {
		super(x, y, new ImageIcon("resistor.png").getImage());
		this.resistance = resistance;
		resistorPosEnd = new Rectangle(x - 10, y - 10, 40, 40);
		resistorNegEnd = new Rectangle(x + 30, y, 40, 40);
		voltage = 0;
		amperage = 0;
	}

	/**
	 * Sets the voltage of the component
	 * 
	 * @param voltage
	 *            the new voltage of the component
	 */
	public void setVoltage(double voltage) {
		this.voltage = voltage;
	}

	/**
	 * Sets the amperage of the component
	 * 
	 * @param amperage
	 *            the new amperage of the component
	 */
	public void setAmperage(double amperage) {
		this.amperage = amperage;
	}

	/**
	 * Sets the resistance of the component
	 * 
	 * @param resistance
	 *            the new resistance of the component
	 */
	public void setResistance(double resistance) {
		this.resistance = resistance;
	}

	/**
	 * Returns the resistance of the resistor
	 * 
	 * @return the resistance of the resistor
	 */
	public double getResistance() {
		return this.resistance;
	}

	/**
	 * Returns the voltage of the component
	 * 
	 * @return the voltage of the component
	 */
	public double getVoltage() {
		return this.voltage;
	}

	/**
	 * Returns the current of the component
	 * 
	 * @return the current of the component
	 */
	public double getAmperage() {
		return this.amperage;
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
		if (resistorPosEnd.contains(p))
			return 2;
		else if (resistorNegEnd.contains(p))
			return 1;
		return 0;
	}

	/**
	 * Updates the hitbox of each node (where the user clicks)
	 * 
	 */
	public void updateHitbox() {
		resistorPosEnd = new Rectangle(x - 10, y - 10, 40, 40);
		resistorNegEnd = new Rectangle(x + 30, y, 40, 40);
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
			return new Point(x, y + 15);
		return new Point(x + 60, y + 15);
	}
}