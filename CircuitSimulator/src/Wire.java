import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

/**
 * The wire class
 * 
 * Creates a wire object, which connects components together
 * 
 * @author James Lee & Jason Qian
 * @version January 22, 2013
 */
public class Wire {

	// Variables
	int wireStartX;
	int wireStartY;
	int wireMiddleX;
	int wireMiddleY;
	Point middle;
	int wireEndX;
	int wireEndY;
	int wireStartComponent;
	ArrayList<PhysicsObject> connectedComponents = new ArrayList<PhysicsObject>();
	int[] terminal = new int[2];
	protected Color color;

	/**
	 * The wire constructor
	 * 
	 * @param x1
	 *            the starting x position of the wire
	 * @param y1
	 *            the starting y position of the wire
	 * @param x2
	 *            the ending x position of the wire
	 * @param y2
	 *            the ending y position of the wire
	 */
	public Wire(int x1, int y1, int x2, int y2) {
		wireStartX = x1;
		wireStartY = y1;
		wireEndX = x2;
		wireEndY = y2;
		color = (new Color(0, 0, 255));
		connectedComponents.add(new Battery(0, 0, 0));
		connectedComponents.add(new Battery(0, 0, 0));
	}

	/**
	 * The color of the wire
	 * 
	 * @return the color of the wire
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * Updates the position of the wire
	 * 
	 * @param x1
	 *            the new starting x position
	 * @param y1
	 *            the new starting y position
	 * @param x2
	 *            the new ending x position
	 * @param y2
	 *            the new ending y position
	 */
	public void updatePosition(int x1, int y1, int x2, int y2) {
		wireStartX = x1;
		wireStartY = y1;
		wireEndX = x2;
		wireEndY = y2;
	}

	/**
	 * Draws the wire on the screen
	 * 
	 * @param g
	 *            the graphics
	 */
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(this.getColor());
		g2.setStroke(new BasicStroke(10));
		wireStartX = connectedComponents.get(0).getTerminalPoint(terminal[0]).x;
		wireStartY = connectedComponents.get(0).getTerminalPoint(terminal[0]).y;
		wireEndX = connectedComponents.get(1).getTerminalPoint(terminal[1]).x;
		wireEndY = connectedComponents.get(1).getTerminalPoint(terminal[1]).y;

		// Find the middle of the wire
		if (wireStartX >= wireEndX) {
			if (wireStartY <= wireEndY || wireStartY > wireEndY) {
				wireMiddleX = connectedComponents.get(1).getTerminalPoint(
						terminal[1]).x;
				wireMiddleY = connectedComponents.get(0).getTerminalPoint(
						terminal[0]).y;
			}
		} else if (wireStartX < wireEndX) {
			if (wireStartY <= wireEndY || wireStartY > wireEndY) {
				wireMiddleX = connectedComponents.get(1).getTerminalPoint(
						terminal[1]).x;
				wireMiddleY = connectedComponents.get(0).getTerminalPoint(
						terminal[0]).y;
			}
		}

		// Draw the wire
		if (wireMiddleX == 0 && wireMiddleY == 0) {
			g2.drawLine(wireStartX, wireStartY, wireEndX, wireEndY);
			if (wireStartY > wireEndY) {
				wireMiddleY = wireStartY - wireEndY;
			} else if (wireStartY < wireEndY) {
				wireMiddleY = wireEndY - wireStartY;
			}
		} else {
			g2.drawLine(wireStartX, wireStartY, wireMiddleX, wireMiddleY);
			g2.drawLine(wireMiddleX, wireMiddleY, wireEndX, wireEndY);
		}
		if (wireStartX == wireEndX) {
			wireMiddleX = wireStartX;
			if (wireStartY > wireEndY) {
				wireMiddleY = (wireStartY + wireEndY) / 2;
			}
			if (wireStartY < wireEndY) {
				wireMiddleY = (wireEndY + wireStartY) / 2;
			}
		}
		if (wireStartY == wireEndY) {
			wireMiddleY = wireStartY;
			if (wireStartX > wireEndX) {
				wireMiddleX = (wireStartX + wireEndX) / 2;
			}
			if (wireStartX < wireEndX) {
				wireMiddleX = (wireEndX + wireStartX) / 2;
			}
		}
		g2.setColor(Color.BLACK);
	}

	/**
	 * Draws the drawing wire
	 * 
	 * @param g
	 *            the graphics
	 */
	public void drawMoving(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(this.getColor());
		g2.setStroke(new BasicStroke(10));

		if (wireMiddleX == 0 && wireMiddleY == 0)
			g2.drawLine(wireStartX, wireStartY, wireEndX, wireEndY);
		else {
			g2.drawLine(wireStartX, wireStartY, wireMiddleX, wireMiddleY);
			g2.drawLine(wireMiddleX, wireMiddleY, wireEndX, wireEndY);
		}

		g2.setColor(Color.BLACK);
	}

	/**
	 * Sets the color of the wire
	 * 
	 * @param color
	 *            the color the wire will be set to
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Returns the components connected to this wire
	 * 
	 * @return the components connected to this wire
	 */
	public ArrayList<PhysicsObject> getConnectedComponents() {
		return connectedComponents;
	}

	/**
	 * Sets the components connected to this wire
	 * 
	 * @param object1
	 *            the first component
	 * @param object2
	 *            the second component
	 * @param terminal1
	 *            the terminal of the first component
	 * @param terminal2
	 *            the terminal of the second component
	 */
	public void setConnectedComponents(PhysicsObject object1,
			PhysicsObject object2, int terminal1, int terminal2) {
		connectedComponents.set(0, object1);
		connectedComponents.set(1, object2);
		this.terminal[0] = terminal1;
		this.terminal[1] = terminal2;

		// Add the components to the wire
		object1.addConnectedWire(this);
		object2.addConnectedWire(this);

		Battery batt;
		if (object1 instanceof Battery) {
			batt = (Battery) object1;
		} else if (object2 instanceof Battery) {
			batt = (Battery) object2;
		} else {
			return;
		}

		// Add the wire to the battery
		if (batt.getPositiveNode().size() <= batt.getNegativeNode().size()) {
			batt.addToPositiveNode(this);
		} else {
			batt.addToNegativeNode(this);
		}

	}

	/**
	 * Return the middle of the wire
	 * 
	 * @return the middle of the wire
	 */
	public Point getMiddlePoint() {
		middle = new Point(wireMiddleX, wireMiddleY);
		return middle;
	}

	/**
	 * Get the other component connected to the wire
	 * 
	 * @param object
	 *            the first object connected to the wire
	 * 
	 * @return the other object connected to the wire
	 */
	public ArrayList<PhysicsObject> getOtherComponent(PhysicsObject object) {
		ArrayList<PhysicsObject> otherObjects = new ArrayList<PhysicsObject>();
		for (int objectNo = 0; objectNo < connectedComponents.size(); objectNo++) {
			if (connectedComponents.get(objectNo) != object) {
				otherObjects.add(connectedComponents.get(objectNo));
			}
		}
		return otherObjects;
	}
}
