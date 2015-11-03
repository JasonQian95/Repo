import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 * The battery class
 * 
 * The battery object, creates and allows the user to change the values
 * (voltage) for this battery
 * 
 * @author James Lee & Jason Qian
 * @version January 22, 2013
 */
public class Battery extends PhysicsObject {

	// Variables
	private double voltage;
	boolean posTerminalConnected = false;
	boolean negTerminalConnected = false;
	Rectangle batteryPosEnd;
	Rectangle batteryNegEnd;
	int whichEnd; // 1 = negative, 2 = positive
	ArrayList<Wire> positiveNode = new ArrayList<Wire>();
	ArrayList<Wire> negativeNode = new ArrayList<Wire>();

	/**
	 * The constructor for the battery
	 * 
	 * @param voltage
	 *            the voltage of the battery
	 * @param x
	 *            the x position of the battery (top left corner)
	 * @param y
	 *            the y position of the battery (top left corner)
	 */
	public Battery(int voltage, int x, int y) {
		super(x, y, new ImageIcon("battery.png").getImage());
		this.voltage = voltage;
		batteryPosEnd = new Rectangle(x, y + 30, 30, 30);
		batteryNegEnd = new Rectangle(x + 30, y + 30, 30, 30);
	}

	/**
	 * Updates the voltage of the battery
	 * 
	 * @param voltage
	 *            the new voltage of the battery
	 */
	public void updateVoltage(double voltage) {
		this.voltage = voltage;
	}

	/**
	 * Gives the voltage of the battery
	 * 
	 * @return the voltage of this battery
	 */
	public double getVoltage() {
		return this.voltage;
	}

	/**
	 * Returns the column this component is in
	 * 
	 * @return the column that this component is in
	 */
	public int getColumn() {
		return this.x / 30;
	}

	/**
	 * Returns the row this component is in
	 * 
	 * @return the row that this component is in
	 */
	public int getRow() {
		return this.y / 30;
	}

	/**
	 * Returns which terminal the user connected the wire to
	 * 
	 * @param p
	 *            the point where the user pressed
	 * 
	 * @return the terminal that the user connected the wire to (2 = positive
	 *         node, 1 = negative node)
	 */
	public int connectToWhichEnd(Point p) {
		if (batteryPosEnd.contains(p))
			return 2;
		else if (batteryNegEnd.contains(p))
			return 1;
		return 0;
	}

	/**
	 * Returns the array of wires connected to the positive node
	 * 
	 * @return the array of wires connected to the batteries positive node
	 */
	public ArrayList<Wire> getPositiveNode() {
		return this.positiveNode;
	}

	/**
	 * Returns the array of wires connected to the negative node
	 * 
	 * @return the array of wires connected to the batteries negative node
	 */
	public ArrayList<Wire> getNegativeNode() {
		return this.negativeNode;
	}

	/**
	 * Adds a wire to the positive node
	 * 
	 * @param wire
	 *            the wire to add to the positive node
	 */
	public void addToPositiveNode(Wire wire) {
		positiveNode.add(wire);
	}

	/**
	 * Adds a wire to the negative node
	 * 
	 * @param wire
	 *            the wire to add to the negative node
	 */
	public void addToNegativeNode(Wire wire) {
		negativeNode.add(wire);
	}

	/**
	 * Updates the hitbox of each node (where the user clicks)
	 * 
	 */
	public void updateHitbox() {
		batteryPosEnd = new Rectangle(x, y + 30, 30, 30);
		batteryNegEnd = new Rectangle(x + 30, y + 30, 30, 30);
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
}