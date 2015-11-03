import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * The "PhysicsObject" class. An abstract class that all Batteries, Lightbulbs,
 * and Resistors are based off of.
 * 
 * @author Jason Qian and James Lee
 * @version January 22, 2013
 */
public abstract class PhysicsObject extends Rectangle {
	protected int xPos, yPos;
	protected Image image;
	public static final boolean ON = true;
	public static final boolean OFF = false;
	protected int whichEnd = 0;
	ArrayList<Wire> connectedWires = new ArrayList<Wire>();

	public PhysicsObject(int x, int y, Image image) {
		super(x, y, image.getWidth(null), image.getHeight(null));
		this.image = image;
		xPos = x;
		yPos = y;
	}

	/**
	 * Draws this object's image
	 * 
	 * @param g
	 *            the graphics
	 */
	public void drawImage(Graphics g) {
		g.drawImage(image, x, y, null);
	}

	/**
	 * Gives the column that this object is in
	 * 
	 * @return the column of this object
	 */
	public int getColumn() {
		return this.xPos / 30;
	}

	/**
	 * Gives the row that this object is in
	 * 
	 * @return the row of this object
	 */
	public int getRow() {
		return this.yPos / 30;
	}

	/**
	 * Moves this object to a new point
	 */
	public void move(Point initialPos, Point finalPos) {
		translate(finalPos.x - initialPos.x, finalPos.y - initialPos.y);
	}

	/**
	 * Returns 0 if its not connected to anything
	 * 
	 * @return zero
	 */
	public int connectToWhichEnd(Point point) {
		return 0;
	}

	/**
	 * Gives all the wires connected to this object
	 * 
	 * @return all the connected wires
	 */
	public ArrayList<Wire> getConnectedWires() {
		return connectedWires;
	}

	/**
	 * Adds a wire to the ArrayList of connected wires
	 */
	public void addConnectedWire(Wire wire) {
		connectedWires.add(wire);
	}

	/**
	 * Gets the other wire connected to this object. Assumes there are only 2
	 * wires connected.
	 */
	public ArrayList<Wire> getOtherWire(Wire wire) {
		ArrayList<Wire> otherWires = new ArrayList<Wire>();
		for (int wireNo = 0; wireNo < connectedWires.size(); wireNo++) {
			if (connectedWires.get(wireNo) != wire)
				otherWires.add(connectedWires.get(wireNo));
		}
		return otherWires;
	}

	/**
	 * Returns which terminal the user connected the wire to
	 */
	public abstract Point getTerminalPoint(int terminal);

	/**
	 * Updates the hitbox for this object after moving it
	 */
	public abstract void updateHitbox();
}
