import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.*;

/**
 * The "Physics Simulator" class. Allows the user to create and simulate a
 * circuit. Also finds the voltage, current, and resistance of each resistor and
 * the curcuit's curface charge density
 * 
 * @author Jason Qian and James Lee
 * @version January 22, 2013
 */
public class PhysicsSimulator extends JFrame implements ActionListener {
	protected JTextField textField;
	protected JTextArea textArea;
	private DrawingPanel simArea;
	Font font;
	private JMenuItem newSim, about, circuitOneOption;
	Image battery = new ImageIcon("battery.png").getImage();
	Image resistor = new ImageIcon("resistor.png").getImage();
	Image wire = new ImageIcon("wire.png").getImage();
	Image lightbulbon = new ImageIcon("lightbulbon.png").getImage();
	Image lightbulboff = new ImageIcon("lightbulboff.png").getImage();
	Image pointer = new ImageIcon("pointer.png").getImage();
	int wireStartPointX, wireStartPointY;
	Image[] componentImages = { battery, resistor, wire, lightbulbon,
			lightbulboff, pointer };
	// battery = 0, resistor = 1, wire = 2, lightbulb = 3, switch = 4, pointer =
	// 5
	boolean wireStart = false;
	String[] componentList = { "Battery", "Resistor", "Wire", "Lightbulb",
			"Switch", "Pointer" };
	int currentComponent = 5;
	int[][] grid;
	int noOfRows, noOfColumns;
	int screenMaxX, screenMaxY;
	boolean batteryPlaced = false;
	int componentToMove;
	int componentSelected = -1;
	Point lastPoint;
	Point beforeDrag;
	boolean move = false;
	PhysicsObject obj;
	int terminalTemp;
	double input;
	Wire newWire;
	Wire drawingWire;
	ArrayList<Wire> wires = new ArrayList<Wire>();
	Battery batt;
	ArrayList<Lightbulb> lightbulbs = new ArrayList<Lightbulb>();
	ArrayList<Resistor> resistors = new ArrayList<Resistor>();
	ArrayList<PhysicsObject> components = new ArrayList<PhysicsObject>();
	ArrayList<ArrayList<PhysicsObject>> gridObjects = new ArrayList<ArrayList<PhysicsObject>>();// Associated
	// ArrayList
	// since
	// null
	// Objects
	// cannot
	// be
	// drawn
	ArrayList<ArrayList<Resistor>> circuits = new ArrayList<ArrayList<Resistor>>();// Parallel,
	// then
	// series
	int totalVoltage;
	int drawCircuitOne = 0;
	boolean circuitOne = false;
	ArrayList<Double> surfaceChargeVolts;
	ArrayList<Integer> surfaceChargeXPos;
	ArrayList<Integer> surfaceChargeYPos;
	ArrayList<Integer> surfaceChargePlusOrMinus;// 1 for pos, -1 for neg, 0 for
												// zero

	/**
	 * Creates a Simple Poker Solitaire Frame Application
	 */
	public PhysicsSimulator() {
		super("PhysicsSimulator");
		// Add in a Menu
		JMenuBar menuBar = new JMenuBar();
		JMenu simMenu = new JMenu("Simulator");
		JMenu premadeCircuits = new JMenu("Premade Circuits");
		simMenu.setMnemonic('S');
		newSim = new JMenuItem("New Simulator");
		newSim.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				InputEvent.CTRL_MASK));
		newSim.addActionListener(this);
		circuitOneOption = new JMenuItem("Circuit One");
		circuitOneOption.addActionListener(this);
		premadeCircuits.add(circuitOneOption);
		simMenu.add(newSim);
		menuBar.add(simMenu);
		menuBar.add(premadeCircuits);
		setJMenuBar(menuBar);
		about = new JMenuItem("About");
		about.addActionListener(this);
		// Set up the layout and add in a DrawingPanel for the cardArea
		// Center the frame in the middle (almost) of the screen
		setLayout(new BorderLayout());
		simArea = new DrawingPanel();
		add(simArea, BorderLayout.CENTER);
		font = new Font("Comic Sans MS", Font.PLAIN, 14);
		simArea.setFont(font);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screen.width - simArea.WIDTH) / 2,
				(screen.height - simArea.HEIGHT) / 2 - 52);
		this.setResizable(false);
		noOfColumns = screen.width / 30;
		noOfRows = screen.height / 30;
		screenMaxX = screen.width;
		screenMaxY = screen.height;
		grid = new int[noOfColumns][noOfRows];
		for (int i = 0; i < noOfColumns; i++) {
			gridObjects.add(new ArrayList<PhysicsObject>(noOfRows));
		}
		for (int i = 0; i < 1; i++) {
			circuits.add(new ArrayList<Resistor>());
		}
		totalVoltage = 0;
		surfaceChargeVolts = new ArrayList<Double>();
		surfaceChargeXPos = new ArrayList<Integer>();
		surfaceChargeYPos = new ArrayList<Integer>();
		surfaceChargePlusOrMinus = new ArrayList<Integer>();
	}

	/**
	 * Method that deals with the menu options
	 * 
	 * @param event
	 *            the event that triggered this method
	 */
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == newSim) {
			newSim();
		} else if (event.getSource() == about) {
			JOptionPane
					.showMessageDialog(
							simArea,
							"A surface charge density simulator\nby Jason Qian and James Lee\n\u00a9 2013",
							"About", JOptionPane.INFORMATION_MESSAGE);
		} else if (event.getSource() == circuitOneOption && circuitOne == false) {
			// Creates a preset circuit
			newSim();
			circuitOne = true;
			batteryPlaced = true;
			batt = new Battery(10, 330, 90);
			Resistor res1 = new Resistor(10, 270, 300);
			Resistor res2 = new Resistor(10, 390, 300);
			components.add(batt);
			components.add(res1);
			components.add(res2);
			wires.add(new Wire(345, 150, 270, 315));
			wires.get(wires.size() - 1)
					.setConnectedComponents(batt, res1, 2, 2);
			wires.add(new Wire(375, 150, 450, 315));
			wires.get(wires.size() - 1)
					.setConnectedComponents(batt, res2, 1, 1);
			wires.add(new Wire(330, 315, 390, 315));
			wires.get(wires.size() - 1)
					.setConnectedComponents(res1, res2, 1, 2);
			repaint();
		}
	}

	/**
	 * Resets everything, creating a new simulation
	 */
	public void newSim() {
		circuitOne = false;
		wireStart = false;
		currentComponent = 5;
		batteryPlaced = false;
		componentToMove = -1;
		componentSelected = 0;
		wires = new ArrayList<Wire>();
		lightbulbs = new ArrayList<Lightbulb>();
		resistors = new ArrayList<Resistor>();
		components = new ArrayList<PhysicsObject>();
		surfaceChargeVolts = new ArrayList<Double>();
		surfaceChargeXPos = new ArrayList<Integer>();
		surfaceChargeYPos = new ArrayList<Integer>();
		surfaceChargePlusOrMinus = new ArrayList<Integer>();
		gridObjects = new ArrayList<ArrayList<PhysicsObject>>(noOfColumns);// Associated
		for (int i = 0; i < noOfColumns; i++) {
			gridObjects.add(new ArrayList<PhysicsObject>(noOfRows));
		}
		repaint();
	}

	/**
	 * Inner class to keep track of the card area
	 */
	private class DrawingPanel extends JPanel {
		final int WIDTH = 990;
		final int HEIGHT = 600;

		public DrawingPanel() {
			setPreferredSize(new Dimension(WIDTH, HEIGHT));
			setFont(new Font("Arial", Font.PLAIN, 18));
			this.addMouseListener(new MouseHandler());
			this.addMouseMotionListener(new MouseMotionHandler());
		}

		/**
		 * Paints the drawing area
		 * 
		 * @param g
		 *            the graphics context to paint
		 */
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			this.setBackground(Color.WHITE);
			g.setColor(Color.GREEN);
			g.fillRect(690, 0, 300, 600);
			g.setColor(Color.BLACK);
			g.drawRect(690, 0, 299, 599);
			g.drawRect(0, 0, 690, 599);
			g.drawString("Circuit Components", 772, 40);
			g.drawLine(750, 50, 930, 50);
			g.drawString("Selected: " + componentList[currentComponent], 760,
					580);
			if (componentSelected >= 0 && components.size() > 0) {
				if (components.get(componentSelected) instanceof Battery) {
					g.drawString("Voltage: " + batt.getVoltage() + " Volts",
							760, 530);
				} else if (components.get(componentSelected) instanceof Resistor) {
					Resistor tempResistor = (Resistor) components
							.get(componentSelected);
					g.drawString(
							"Voltage: "
									+ (double) Math.round(tempResistor
											.getVoltage() * 100) / 100
									+ " Volts", 760, 520);
					g.drawString(
							"Current: "
									+ (double) Math.round(tempResistor
											.getAmperage() * 100) / 100
									+ " Amps", 760, 540);
					g.drawString("Resistance: " + tempResistor.getResistance()
							+ " Ohms", 760, 560);
				}
			}
			for (int i = 0; i < 3; i++) {
				g.drawRect(725, 100 + i * 150, 100, 100);
				g.drawRect(875, 100 + i * 150, 100, 100);
			}
			g.setColor(Color.WHITE);
			for (int i = 0; i < 3; i++) {
				g.fillRect(726, 101 + i * 150, 99, 99);
				g.fillRect(876, 101 + i * 150, 99, 99);
			}
			g.drawImage(battery, 745, 120, this);
			g.drawImage(resistor, 895, 140, this);
			g.drawImage(wire, 745, 265, this);
			g.drawImage(lightbulbon, 900, 275, this);
			g.drawImage(pointer, 920, 435, this);
			g.setColor(Color.BLACK);
			g.drawString("Click to show", 729, 439);
			g.drawString("surface charge", 726, 456);
			for (int i = 0; i < components.size(); i++) {
				components.get(i).drawImage(g);
				if (components.get(i) instanceof Lightbulb) {
					if (isConnected(batt) > -1) {
						Lightbulb lightbulb = (Lightbulb) components.get(i);
						lightbulb.updateLightbulb(batt.getVoltage());
					}
				}
			}
			for (int i = 0; i < wires.size(); i++) {
				wires.get(i).draw(g);
			}
			if (wireStart) {
				drawingWire.drawMoving(g);
			}
			g.setColor(new Color(200, 200, 0, 65));
			if (currentComponent % 2 == 0) {
				g.fillRect(725, 100 + currentComponent * 75, 100, 100);
			} else if (currentComponent % 2 == 1) {
				g.fillRect(875, 100 + currentComponent * 75 - 75, 100, 100);
			}

			// Draws the surface charge density numbers if they have been found
			g.setColor(Color.BLACK);
			for (int voltNumber = 0; voltNumber < surfaceChargeVolts.size(); voltNumber++) {
				if (surfaceChargePlusOrMinus.get(voltNumber) == 1) {
					g.drawString(
							"+" + surfaceChargeVolts.get(voltNumber) + "V",
							surfaceChargeXPos.get(voltNumber),
							surfaceChargeYPos.get(voltNumber));
				} else if (surfaceChargePlusOrMinus.get(voltNumber) == -1) {
					g.drawString(
							"-" + surfaceChargeVolts.get(voltNumber) + "V",
							surfaceChargeXPos.get(voltNumber),
							surfaceChargeYPos.get(voltNumber));
				} else if (surfaceChargePlusOrMinus.get(voltNumber) == 0) {
					g.drawString(surfaceChargeVolts.get(voltNumber) + "V",
							surfaceChargeXPos.get(voltNumber),
							surfaceChargeYPos.get(voltNumber));
				}
			}
		}

		/**
		 * Simplifies the circuit to an ArrayList of series circuits and adds
		 * them to an ArrayList
		 * 
		 * @param batt
		 *            the battery the circuit is originating from
		 * @return an ArrayList of all the series circuits. Each of these
		 *         ArrayList's circuits are parallel to each other. Each
		 *         ArrayList contains an ArrayList of all the resistors in the
		 *         circuit
		 */
		public ArrayList<ArrayList<Resistor>> determineCircuits(Battery battery) {
			int noOfPaths = 0;
			// Only the positive node, to avoid out of bounds errors
			noOfPaths = batt.getPositiveNode().size();
			if (isConnected(batt) == -1) {
				return circuits;
			}
			circuits.clear();
			for (int i = 0; i < noOfPaths; i++) {
				circuits.add(new ArrayList<Resistor>());
			}
			// Starting at the battery, trace along the resistors, adding them
			// to an ArrayList until the original battery if found again. Then
			// repeat for every wire attached to the battery
			for (int pathNo = 1; pathNo <= noOfPaths; pathNo++) {
				String currentObjectType = "PhysicsObject";
				Wire currentWire = battery.getPositiveNode().get(pathNo - 1);
				PhysicsObject currentObject = batt;
				do {
					if (currentObjectType.equals("PhysicsObject")) {
						currentObject = currentWire.getOtherComponent(
								currentObject).get(0);
						currentObjectType = "Wire";
						if (currentObject instanceof Resistor) {
							circuits.get(pathNo - 1).add(
									(Resistor) currentObject);
						}
					} else if (currentObjectType.equals("Wire")) {
						currentWire = currentObject.getOtherWire(currentWire)
								.get(0);
						currentObjectType = "PhysicsObject";
					}
				} while (currentObject != battery);
			}
			updateValues();
			return circuits;
		}

		/**
		 * Checks if the circuit is connected and how many resistors are in the
		 * circuit
		 * 
		 * @param batt
		 *            the battery that the circuit originated from
		 * @return -1 if the circuit is incomplete, otherwise the number of
		 *         resistors in the circuit (can be 0)
		 */
		public int isConnected(Battery batt) {
			// Returns -1 if not connected, otherwise returns the number of
			// resistors in the circuit
			int noOfResistors = 0;
			if (batt.getPositiveNode().size() == 0
					|| batt.getNegativeNode().size() == 0) {
				return -1;
			}
			// Trace along the circuit, counting all the resistors connected to
			// it
			for (int run = 0; run < batt.getPositiveNode().size(); run++) {
				Wire currentWire = batt.getPositiveNode().get(run);
				PhysicsObject currentObject = batt;
				String currentObjectType = "Wire";
				do {
					if (currentObjectType.equals("Wire")) {
						if (currentWire.getOtherComponent(currentObject) != null) {
							currentObject = currentWire.getOtherComponent(
									currentObject).get(0);
						} else {
							return -1;
						}
						currentObjectType = "PhysicsObject";
					} else if (currentObjectType.equals("PhysicsObject")) {
						if (currentObject.getOtherWire(currentWire) != null) {
							if (currentObject.getOtherWire(currentWire).size() == 0) {
								return -1;
							}
							currentWire = currentObject.getOtherWire(
									currentWire).get(0);
						} else {
							return -1;
						}
						if (currentObject instanceof Resistor) {
							noOfResistors++;
						}
						currentObjectType = "Wire";
					}
				} while (currentObject != batt);
			}
			return noOfResistors;
		}

		/**
		 * Updates all the voltage and amperage values of the resistors that are
		 * in a circuit.
		 */
		public void updateValues() {
			int totalNoOfParallel = circuits.size();
			// Reset any previous circuit values
			for (int parallelNumber = 0; parallelNumber < totalNoOfParallel; parallelNumber++) {
				int totalNoOfSeries = circuits.get(parallelNumber).size();
				for (int seriesNumber = 0; seriesNumber < totalNoOfSeries; seriesNumber++) {
					circuits.get(parallelNumber).get(seriesNumber)
							.setAmperage(0);
					circuits.get(parallelNumber).get(seriesNumber)
							.setVoltage(0);
				}
			}
			// Set this circuit's values. These are the formulas for series
			// circuits, since all the circuits should've been simplified into
			// series circuits already
			for (int parallelNumber = 0; parallelNumber < totalNoOfParallel; parallelNumber++) {
				int totalNoOfSeries = circuits.get(parallelNumber).size();
				double totalResistance = 0;
				for (int seriesNumber = 0; seriesNumber < totalNoOfSeries; seriesNumber++) {
					totalResistance += circuits.get(parallelNumber)
							.get(seriesNumber).getResistance();
				}
				double constantAmps = batt.getVoltage() / totalResistance;
				for (int seriesNumber = 0; seriesNumber < totalNoOfSeries; seriesNumber++) {
					circuits.get(parallelNumber).get(seriesNumber)
							.setAmperage(constantAmps);
					circuits.get(parallelNumber)
							.get(seriesNumber)
							.setVoltage(
									circuits.get(parallelNumber)
											.get(seriesNumber).getAmperage()
											* circuits.get(parallelNumber)
													.get(seriesNumber)
													.getResistance());
				}
			}
		}

		/**
		 * Finds and draws the surface charge density of each wire in the
		 * circuit
		 * 
		 * @param batt
		 *            the battery that the circuit originates from
		 */
		public void surfaceChargeDensity(Battery battery) {
			//Reset previous information
			surfaceChargeVolts = new ArrayList<Double>();
			surfaceChargeXPos = new ArrayList<Integer>();
			surfaceChargeYPos = new ArrayList<Integer>();
			surfaceChargePlusOrMinus = new ArrayList<Integer>();
			determineCircuits(batt);
			int noOfResistors = isConnected(batt);
			// Exit if not connected
			if (noOfResistors == -1) {
				return;
			}
			for (int run = 0; run < batt.getPositiveNode().size(); run++) {
				double currentVoltagePos = batt.getVoltage() / 2;
				double currentVoltageNeg = batt.getVoltage() / 2;
				int currentColorValuePos = 255;
				int currentColorValueNeg = 255;
				boolean continuePos = true;
				boolean continueNeg = true;
				String plusOrMinus = "";
				Wire lastWirePos = new Wire(0, 0, 0, 0);
				Wire lastWireNeg = new Wire(0, 0, 0, 0);
				Wire currentWirePos = battery.getPositiveNode().get(run);
				Wire currentWireNeg = battery.getNegativeNode().get(run);
				PhysicsObject lastObjectPos = batt;
				PhysicsObject lastObjectNeg = batt;
				Color color = (new Color(currentColorValuePos, 0, 0));
				color = (new Color(0, currentColorValueNeg, 0));

				// Trace along the circuit from both sides, adding in the
				// surface charge values. Stop once they pass each other, or
				// reach the same wire. If one side reaches 0 volts, it will
				// stop. Adds all the needed coordinates and values to
				// associated ArrayLists to print later
				do {
					if (continuePos == true) {
						if (lastObjectPos instanceof Resistor) {
							Resistor tempResistor = (Resistor) lastObjectPos;
							currentVoltagePos -= tempResistor.getVoltage();
						}
						if (currentVoltagePos > 0) {
							currentColorValuePos = (int) (currentColorValuePos
									* currentVoltagePos / (batt.getVoltage() / 2));
							plusOrMinus = "+";
							color = (new Color(currentColorValuePos, 0, 0));
							currentWirePos.setColor(color);

							//To be printed in paintComponent
							surfaceChargeVolts.add((double) Math
									.round(currentVoltagePos * 100) / 100);
							surfaceChargeXPos.add((int) currentWirePos
									.getMiddlePoint().getX() - 51);
							surfaceChargeYPos.add((int) currentWirePos
									.getMiddlePoint().getY() + 30);
							surfaceChargePlusOrMinus.add(1);
							lastWirePos = currentWirePos;
							lastObjectPos = currentWirePos.getOtherComponent(
									lastObjectPos).get(0);
							currentWirePos = lastObjectPos.getOtherWire(
									currentWirePos).get(0);
						} else {
							continuePos = false;
						}
					}
					if (continueNeg == true) {
						if (lastObjectNeg instanceof Resistor) {
							Resistor tempResistor = (Resistor) lastObjectNeg;
							currentVoltageNeg -= tempResistor.getVoltage();
						}
						if (currentVoltageNeg > 0) {
							currentColorValueNeg = (int) (currentColorValueNeg
									* currentVoltageNeg / (batt.getVoltage() / 2));
							plusOrMinus = "-";
							color = (new Color(0, currentColorValueNeg, 0));
							currentWireNeg.setColor(color);

							surfaceChargeVolts.add((double) Math
									.round(currentVoltageNeg * 100) / 100);
							surfaceChargeXPos.add((int) currentWireNeg
									.getMiddlePoint().getX() + 17);
							surfaceChargeYPos.add((int) currentWireNeg
									.getMiddlePoint().getY() + 30);
							surfaceChargePlusOrMinus.add(-1);
							lastWireNeg = currentWireNeg;
							lastObjectNeg = currentWireNeg.getOtherComponent(
									lastObjectNeg).get(0);
							currentWireNeg = lastObjectNeg.getOtherWire(
									currentWireNeg).get(0);
						} else {
							continueNeg = false;
						}
					}
				} while (currentWirePos != currentWireNeg
						&& currentWirePos != lastWireNeg
						&& currentWireNeg != lastWirePos);

				if (continuePos == false) {
					if (lastObjectNeg instanceof Resistor) {
						Resistor tempResistor = (Resistor) lastObjectNeg;
						currentVoltageNeg -= tempResistor.getVoltage();
					}
					if (currentVoltageNeg >= 0) {
						currentColorValueNeg = (int) (currentColorValueNeg
								* currentVoltageNeg / (batt.getVoltage() / 2));
					}
				}
				if (continueNeg == false) {
					if (lastObjectPos instanceof Resistor) {
						Resistor tempResistor = (Resistor) lastObjectPos;
						currentVoltagePos -= tempResistor.getVoltage();
					}
					if (currentVoltagePos >= 0) {
						currentColorValuePos = (int) (currentColorValuePos
								* currentVoltagePos / (batt.getVoltage() / 2));
					}
				}

				if (currentWirePos == currentWireNeg) {
					if (continuePos == true && continueNeg == true) {
						if (lastObjectPos instanceof Resistor) {
							Resistor tempResistor = (Resistor) lastObjectPos;
							currentVoltagePos -= tempResistor.getVoltage();
						}
						if (lastObjectNeg instanceof Resistor) {
							Resistor tempResistor = (Resistor) lastObjectNeg;
							currentVoltageNeg -= tempResistor.getVoltage();
						}
					}
					double tempNumber = 1000;
					//This should always happen. If tempNumber is still 1000 then there was an error.
					if ((double) Math.round(Math.abs(currentVoltagePos) * 100000) / 100000 == (double) Math
							.round(Math.abs(currentVoltagePos) * 100000) / 100000) {
						tempNumber = Math.abs(currentVoltagePos);
					}
					if (currentVoltagePos < 0) {
						currentColorValueNeg = (int) (currentColorValueNeg
								* currentVoltageNeg / (batt.getVoltage() / 2));
						color = (new Color(0, currentColorValueNeg, 0));
						currentWirePos.setColor(color);
						plusOrMinus = "-";
					} else if (currentVoltageNeg < 0) {
						currentColorValuePos = (int) (currentColorValuePos
								* currentVoltagePos / (batt.getVoltage() / 2));
						currentWirePos.setColor(color);
						color = (new Color(currentColorValuePos, 0, 0));
						currentWirePos.setColor(color);
						plusOrMinus = "+";
					} else {
						color = (new Color(0, 0, 255));
						currentWirePos.setColor(color);
						plusOrMinus = "";
					}

					surfaceChargeVolts.add((double) Math
							.round(tempNumber * 100) / 100);
					surfaceChargeXPos.add((int) currentWirePos.getMiddlePoint()
							.getX() - 36);
					surfaceChargeYPos.add((int) currentWirePos.getMiddlePoint()
							.getY() + 30);
					if (plusOrMinus == "+") {
						surfaceChargePlusOrMinus.add(1);
					} else if (plusOrMinus == "-") {
						surfaceChargePlusOrMinus.add(-1);
					} else if (plusOrMinus == "") {
						surfaceChargePlusOrMinus.add(0);
					}
				}
			}
		}

		/**
		 * Fixes the coordinate entered to be one of the grid space numbers
		 * coordinates.
		 * 
		 * @param position
		 *            the coordinate to be fixed
		 * @return the nearest multiple of 30 to the coordinate, rounded down.
		 *         This is the position it will be on the grid.
		 */
		public int snapToGrid(int position) {
			return (int) (position / 30) * 30;
		}

		/**
		 * Inner class to handle mouse events Extends MouseAdapter instead of
		 * implementing MouseListener since we only need to override
		 * mousePressed
		 */
		private class MouseHandler extends MouseAdapter {
			/**
			 * Deals with mouse clicks
			 * 
			 * @param event
			 *            the event that triggered this method
			 */
			public void mousePressed(MouseEvent event) {
				Point point = event.getPoint();
				String[] voltageList = { "3", "6", "9", "12" };
				String[] resistanceList = { "10", "20", "40", "80", "160" };
				// If the user clicked on the drawing board with a component
				// selected
				if (point.x > 0 && point.x < 690 && point.y > 0
						&& point.y < 600) {
					if (currentComponent == 5) {
						for (int i = 0; i < components.size(); i++) {
							if (components.get(i).contains(point)) {
								componentToMove = i;
								componentSelected = i;
								lastPoint = new Point(
										components.get(componentToMove).x,
										components.get(componentToMove).y);
								// Updating the resistance and voltage of
								// resistors and batteries
								if (componentSelected >= 0
										&& event.getButton() == MouseEvent.BUTTON3) {
									if (components.get(componentSelected) instanceof Battery) {
										try {
											batt.updateVoltage(JOptionPane
													.showOptionDialog(
															null,
															"Please select a voltage",
															"Voltage Selection",
															JOptionPane.YES_NO_OPTION,
															JOptionPane.PLAIN_MESSAGE,
															null, voltageList,
															null) * 3 + 3);
										} catch (Exception e) {
										}
									} else if (components
											.get(componentSelected) instanceof Resistor) {
										Resistor tempResistor = (Resistor) components
												.get(componentSelected);
										tempResistor
												.setResistance(Math.pow(
														2,
														JOptionPane
																.showOptionDialog(
																		null,
																		"Please select a resistance",
																		"Resistance Selection",
																		JOptionPane.YES_NO_OPTION,
																		JOptionPane.PLAIN_MESSAGE,
																		null,
																		resistanceList,
																		null)) * 10);
									} else if (components
											.get(componentSelected) instanceof Lightbulb) {
									}
									repaint();
								}
								return;
							}
						}
						componentSelected = -1;
					}
					// Creating PhysicsObjects
					else {
						if (currentComponent == 1) {
							Resistor resist = new Resistor(10,
									snapToGrid(point.x), snapToGrid(point.y));
							resistors.add(resist);
							components.add(resist);
						}
						if (currentComponent == 0) {
							if (batteryPlaced == false) {
								batteryPlaced = true;
								batt = new Battery(9, snapToGrid(point.x),
										snapToGrid(point.y));
								components.add(batt);
							}
						}
						if (currentComponent == 3) {
							Lightbulb lbulb = new Lightbulb(
									snapToGrid(point.x), snapToGrid(point.y));
							lightbulbs.add(lbulb);
							components.add(lbulb);
						}

						// Creating wires
						if (currentComponent == 2) {
							newWire = new Wire(0, 0, 0, 0);
							for (int c = 0; c < components.size(); c++)
								if (components.get(c).connectToWhichEnd(point) > 0) {
									components.get(c).whichEnd = components
											.get(c).connectToWhichEnd(point);
									if (wireStart == false) {
										wireStart = true;
										newWire.wireStartComponent = c;
										if (components.get(c) instanceof Resistor) {
											wireStartPointY = components.get(c).y + 15;
											if (components.get(c).whichEnd == 1) {
												wireStartPointX = components
														.get(c).x + 60;
											} else if (components.get(c).whichEnd == 2) {
												wireStartPointX = components
														.get(c).x;
											}
											drawingWire = new Wire(
													wireStartPointX,
													wireStartPointY, point.x,
													point.y);
										} else {
											if (components.get(c).whichEnd == 1) {
												wireStartPointX = components
														.get(c).x + 45;
												wireStartPointY = components
														.get(c).y + 60;
											} else if (components.get(c).whichEnd == 2) {
												wireStartPointX = components
														.get(c).x + 15;
												wireStartPointY = components
														.get(c).y + 60;
											}
											drawingWire = new Wire(
													wireStartPointX,
													wireStartPointY, point.x,
													point.y);
										}
										obj = components.get(c);
										terminalTemp = components.get(c).whichEnd;
										break;
									}
									if (wireStart == true
											&& newWire.wireStartComponent != c) {
										newWire.wireStartComponent = c;
										wireStart = false;
										drawingWire = null;
										if (components.get(c) instanceof Resistor) {
											if (components.get(c).whichEnd == 1) {
												newWire = new Wire(
														wireStartPointX,
														wireStartPointY,
														components.get(c).x + 60,
														components.get(c).y + 15);
											} else if (components.get(c).whichEnd == 2) {
												newWire = new Wire(
														wireStartPointX,
														wireStartPointY,
														components.get(c).x,
														components.get(c).y + 15);
											}
										} else {
											if (components.get(c).whichEnd == 1) {
												newWire = new Wire(
														wireStartPointX,
														wireStartPointY,
														components.get(c).x + 45,
														components.get(c).y + 60);
											} else if (components.get(c).whichEnd == 2) {
												newWire = new Wire(
														wireStartPointX,
														wireStartPointY,
														components.get(c).x + 15,
														components.get(c).y + 60);
											}
										}
										wires.add(newWire);
										newWire.setConnectedComponents(obj,
												components.get(c),
												terminalTemp,
												components.get(c).whichEnd);
										break;
									}
								}
						}
					}
				}
				// If the user selected a component
				if (point.x > 725 && point.x < 825 && point.y < 200
						&& point.y > 100)
					currentComponent = 0;
				else if (point.x > 875 && point.x < 975 && point.y < 200
						&& point.y > 100)
					currentComponent = 1;
				else if (point.x > 725 && point.x < 825 && point.y < 350
						&& point.y > 250)
					currentComponent = 2;
				else if (point.x > 875 && point.x < 975 && point.y < 350
						&& point.y > 250)
					currentComponent = 3;
				else if (point.x > 875 && point.x < 975 && point.y < 500
						&& point.y > 400)
					currentComponent = 5;
				else if (point.x > 725 && point.x < 825 && point.y < 500
						&& point.y > 400) {
					surfaceChargeDensity(batt);
					determineCircuits(batt);
				} else {
					grid[(int) (point.getX() / 30)][(int) (point.getY() / 30)] = currentComponent;
				}
				repaint();
			}

			/**
			 * Deals with all triggers involving the releasing of the mouse
			 * button
			 * 
			 * @param event
			 *            the event that triggered this method
			 */
			public void mouseReleased(MouseEvent event) {
				Point point = event.getPoint();
				for (int i = 0; i < components.size(); i++) {
					components.get(i).updateHitbox();
				}
				if (componentToMove >= 0) {
					componentToMove = -1;
				}
				repaint();

				// Finishing wires
				if (currentComponent == 2) {
					for (int c = 0; c < components.size(); c++) {
						if (components.get(c).connectToWhichEnd(point) > 0) {
							components.get(c).whichEnd = components.get(c)
									.connectToWhichEnd(point);
							if (wireStart == true
									&& newWire.wireStartComponent != c) {
								newWire.wireStartComponent = c;
								wireStart = false;
								drawingWire = null;
								if (components.get(c) instanceof Resistor) {
									if (components.get(c).whichEnd == 1) {
										newWire = new Wire(wireStartPointX,
												wireStartPointY,
												components.get(c).x + 60,
												components.get(c).y + 15);
									} else if (components.get(c).whichEnd == 2) {
										newWire = new Wire(wireStartPointX,
												wireStartPointY,
												components.get(c).x,
												components.get(c).y + 15);
									}
								} else {
									if (components.get(c).whichEnd == 1) {
										newWire = new Wire(wireStartPointX,
												wireStartPointY,
												components.get(c).x + 45,
												components.get(c).y + 60);
									} else if (components.get(c).whichEnd == 2) {
										newWire = new Wire(wireStartPointX,
												wireStartPointY,
												components.get(c).x + 15,
												components.get(c).y + 60);
									}
								}
								wires.add(newWire);
								newWire.setConnectedComponents(obj,
										components.get(c), terminalTemp,
										components.get(c).whichEnd);
								break;
							}
						}
					}
					wireStart = false;
				}
			}
		}

		/**
		 * Inner class to handle mouse movement events
		 */
		private class MouseMotionHandler implements MouseMotionListener {
			/**
			 * Deals with all mouse movement events
			 * 
			 * @param event
			 *            the event that triggered this method
			 */
			public void mouseMoved(MouseEvent event) {
				Point cp = event.getPoint();
				if (currentComponent == 5) {
					for (int i = 0; i < components.size(); i++)
						if (components.get(i).contains(cp)) {
							setCursor(Cursor
									.getPredefinedCursor(Cursor.HAND_CURSOR));
							return;
						}
					setCursor(Cursor.getDefaultCursor());
				}
			}

			/**
			 * Deals with all mouse dragging events
			 * 
			 * @param event
			 *            the event that triggered this method
			 */
			public void mouseDragged(MouseEvent event) {
				Point currentPoint = event.getPoint();
				// Create wires
				if (wireStart && currentComponent == 2) {
					drawingWire.updatePosition(drawingWire.wireStartX,
							drawingWire.wireStartY, currentPoint.x,
							currentPoint.y);
				}
				// Drag components
				if (currentComponent == 5 && componentToMove > -1) {
					if (currentPoint.x < 640 && currentPoint.x > 0
							&& currentPoint.y < 570 && currentPoint.y > 0) {
						components.get(componentToMove).x = snapToGrid(currentPoint.x);
						components.get(componentToMove).y = snapToGrid(currentPoint.y);
					}
					// Surface charge has to be reset since the coordinates of
					// the numbers will no longer be correct
					surfaceChargeVolts = new ArrayList<Double>();
					surfaceChargeXPos = new ArrayList<Integer>();
					surfaceChargeYPos = new ArrayList<Integer>();
					surfaceChargePlusOrMinus = new ArrayList<Integer>();
				}
				repaint();
			}
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		PhysicsSimulator sim = new PhysicsSimulator();
		sim.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		sim.pack();
		sim.setVisible(true);
	}
}