import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class Pathfinder extends JFrame implements MouseListener {

	private static class Node extends Rectangle {
		public static final String[] terrainTypes = { "Road", "Plains", "Hill",
				"Mountain", "Water" };
		public static final int[] terrainTypesInt = { 0, 1, 2, 3, 4 };
		public static final double[] movementCosts = { 0.5, 1, 2, 3, 9999 };
		protected String terrain;
		protected double movementCost;
		protected ArrayList<Node> neighbours;
		protected int xPos, yPos;
		protected static final int WIDTH = 20;
		protected static final int HEIGHT = 20;
		protected boolean onPath = false;
		protected boolean searched = false;

		Node(int terrain, int xPos, int yPos) {
			super(xPos, yPos, WIDTH, HEIGHT);
			this.xPos = xPos;
			this.yPos = yPos;
			this.setTerrain(terrain);
			this.neighbours = new ArrayList<Node>();
		}

		Node(int terrain, int xPos, int yPos, Node[] nodes) {
			super(xPos, yPos, WIDTH, HEIGHT);
			this.xPos = xPos;
			this.yPos = yPos;
			this.setTerrain(terrain);
			this.neighbours = new ArrayList<Node>();
			this.addNeighbours(nodes);
		}

		public void addNeighbours(Node[] nodes) {
			for (int i = 0; i < nodes.length; i++) {
				this.neighbours.add(nodes[i]);
			}
			return;
		}

		public Node[] getNeighbours() {
			Node[] neighboursArray = new Node[this.neighbours.size()];
			neighboursArray = this.neighbours.toArray(neighboursArray);
			return neighboursArray;
		}

		public String toString() {
			return (Integer.toString(this.xPos) + ", "
					+ Integer.toString(this.yPos) + ", " + this.terrain);
		}

		public void setTerrain(int terrain) {
			this.terrain = Node.terrainTypes[terrain];
			this.movementCost = Node.movementCosts[terrain];
		}

		public void repaint(Graphics g) {
			g.drawRect(this.xPos, this.yPos, Node.WIDTH, Node.HEIGHT);
			switch (this.terrain) {
			case "Road":
				g.setColor(Color.WHITE);
				g.fillRect(this.xPos + 1, this.yPos + 1, Node.WIDTH - 2,
						Node.HEIGHT - 2);
				g.setColor(Color.BLACK);
				g.drawLine(this.xPos + 2, this.yPos + 4, this.xPos + 8,
						this.yPos + 4);
				g.drawLine(this.xPos + 2, this.yPos + 6, this.xPos + 8,
						this.yPos + 6);
				break;
			case "Plains":
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(this.xPos + 1, this.yPos + 1, Node.WIDTH - 2,
						Node.HEIGHT - 2);
				g.setColor(Color.BLACK);
				break;
			case "Hill":
				g.setColor(Color.GRAY);
				g.fillRect(this.xPos + 1, this.yPos + 1, Node.WIDTH - 2,
						Node.HEIGHT - 2);
				g.setColor(Color.BLACK);
				g.drawArc(this.xPos + 3, this.yPos + 5, 4, 4, 0, 180);
				break;
			case "Mountain":
				g.setColor(Color.DARK_GRAY);
				g.fillRect(this.xPos + 1, this.yPos + 1, Node.WIDTH - 2,
						Node.HEIGHT - 2);
				g.setColor(Color.BLACK);
				g.drawString("^", this.xPos + 3, this.yPos + 12);
				break;
			case "Water":
				g.setColor(Color.BLUE);
				g.fillRect(this.xPos + 1, this.yPos + 1, Node.WIDTH - 2,
						Node.HEIGHT - 2);
				g.setColor(Color.BLACK);
				break;
			}
			/*
			 * if (this.searched == true) { g.setColor(Color.YELLOW);
			 * g.fillRect(this.xPos + 5, this.yPos + 5, Node.WIDTH - 10,
			 * Node.HEIGHT - 10); g.setColor(Color.BLACK); }
			 */
			if (this.onPath == true) {
				g.setColor(Color.RED);
				g.fillRect(this.xPos + 5, this.yPos + 5, Node.WIDTH - 10,
						Node.HEIGHT - 10);
				g.setColor(Color.BLACK);
			}
		}
	}

	final boolean DIAGONALS = false;
	final int WIDTH = 800;
	final int HEIGHT = 600;
	ArrayList<ArrayList<Node>> map = new ArrayList<ArrayList<Node>>();
	DrawingPanel drawingPanel;
	Node start = null;
	Node goal = null;

	Pathfinder() {
		super("Grid");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		// Container content = getContentPane();

		drawingPanel = new DrawingPanel();
		drawingPanel.setBackground(new Color(255, 255, 255));
		drawingPanel.setBorder(BorderFactory
				.createBevelBorder(BevelBorder.RAISED));
		drawingPanel.addMouseListener(this);
		add(drawingPanel, BorderLayout.CENTER);
		this.setContentPane(drawingPanel);

		setVisible(true);
		createNodes();
	}

	public Node[] breadthFirstSearch(Node start, Node end) {
		Map<Node, Node> paths = new HashMap<Node, Node>();
		ArrayList<Node> closedNodes = new ArrayList<Node>();
		ArrayList<Node> openNodes = new ArrayList<Node>();
		openNodes.add(start);

		while (!openNodes.isEmpty()) {
			Node current = openNodes.get(0);
			openNodes.remove(0);
			closedNodes.add(current);
			if (current == end) {
				// reconstruct path
				ArrayList<Node> finalPath = new ArrayList<Node>();
				finalPath.add(current);
				current.onPath = true;
				while (paths.containsKey(current)) {
					current = paths.get(current);
					finalPath.add(current);
					current.onPath = true;
				}
				repaint();
				Node[] finalPathArray = new Node[finalPath.size()];
				finalPathArray = finalPath.toArray(finalPathArray);
				return finalPathArray;
			}
			Node[] neighbours = (Node[]) current.getNeighbours();
			for (Node neighbour : neighbours) {
				if (!closedNodes.contains(neighbour)) {
					if (!openNodes.contains(neighbour)) {
						paths.put(neighbour, current);
						openNodes.add(neighbour);
						neighbour.searched = true;
					}
				}
			}
		}
		repaint();
		return new Node[0];
	}
	
	public Node[] dijkstra(Node start, Node end) {
		Map<Node, Node> paths = new HashMap<Node, Node>();
		ArrayList<Node> closedNodes = new ArrayList<Node>();
		ArrayList<Node> openNodes = new ArrayList<Node>();
		openNodes.add(start);

		while (!openNodes.isEmpty()) {
			Node current = openNodes.get(0);
			openNodes.remove(0);
			closedNodes.add(current);
			if (current == end) {
				// reconstruct path
				ArrayList<Node> finalPath = new ArrayList<Node>();
				finalPath.add(current);
				current.onPath = true;
				while (paths.containsKey(current)) {
					current = paths.get(current);
					finalPath.add(current);
					current.onPath = true;
				}
				repaint();
				Node[] finalPathArray = new Node[finalPath.size()];
				finalPathArray = finalPath.toArray(finalPathArray);
				return finalPathArray;
			}
			Node[] neighbours = (Node[]) current.getNeighbours();
			for (Node neighbour : neighbours) {
				if (!closedNodes.contains(neighbour)) {
					if (!openNodes.contains(neighbour)) {
						paths.put(neighbour, current);
						openNodes.add(neighbour);
						neighbour.searched = true;
					}
				}
			}
		}
		repaint();
		return new Node[0];
	}

	public Node[] aStar(Node start, Node end) {
		Map<Node, Node> paths = new HashMap<Node, Node>();
		ArrayList<Node> closedNodes = new ArrayList<Node>();
		ArrayList<Node> openNodes = new ArrayList<Node>();
		openNodes.add(start);

		while (!openNodes.isEmpty()) {
			Node current = openNodes.get(openNodes.size() - 1);
			openNodes.remove(openNodes.size() - 1);
			closedNodes.add(current);
			if (current == end) {
				// reconstruct path
				ArrayList<Node> finalPath = new ArrayList<Node>();
				Set<Node> keys = paths.keySet();
				finalPath.add(current);
				current.onPath = true;
				while (paths.containsValue(current)) {
					for (Node node : keys) {
						if (paths.get(node) == current) {
							current = node;
							break;
						}
					}
					current = paths.get(current);
					finalPath.add(current);
					current.onPath = true;
				}
				Node[] finalPathArray = new Node[finalPath.size()];
				finalPathArray = finalPath.toArray(finalPathArray);
				return finalPathArray;
			}
			Node[] neighbours = (Node[]) current.getNeighbours();
			for (int i = 0; i < neighbours.length; i++) {
				if (!closedNodes.contains(neighbours[i])) {
					if (!openNodes.contains(neighbours[i])) {
						paths.put(neighbours[i], current);
						openNodes.add(neighbours[i]);
						neighbours[i].searched = true;
					}
				}
			}
		}
		return new Node[0];
	}

	public void createNodes() {
		int maxI = 0;
		int maxJ = 0;
		for (int i = 0; i < WIDTH; i += Node.WIDTH) {
			map.add(new ArrayList<Node>());
			maxI++;
			for (int j = 0; j < HEIGHT; j += Node.HEIGHT) {
				map.get((int) i / Node.WIDTH).add(new Node(0, i, j));
				if (maxI == 1) {
					maxJ++;
				}
			}
		}

		for (int i = 0; i < maxI; i++) {
			for (int j = 0; j < maxJ; j++) {
				ArrayList<Node> neighbours = new ArrayList<Node>();
				if (i - 1 >= 0) {
					neighbours.add(map.get(i - 1).get(j));
					if (DIAGONALS) {
						if (j - 1 >= 0) {
							neighbours.add(map.get(i - 1).get(j - 1));
						}
						if (j + 1 < maxJ) {
							neighbours.add(map.get(i - 1).get(j + 1));
						}
					}
				}
				if (i + 1 < maxI) {
					neighbours.add(map.get(i + 1).get(j));
					if (DIAGONALS) {
						if (j - 1 >= 0) {
							neighbours.add(map.get(i + 1).get(j - 1));
						}
						if (j + 1 < maxJ) {
							neighbours.add(map.get(i + 1).get(j + 1));
						}
					}
				}
				if (j - 1 >= 0) {
					neighbours.add(map.get(i).get(j - 1));
				}
				if (j + 1 < maxJ) {
					neighbours.add(map.get(i).get(j + 1));
				}
				Node[] neighboursArray = new Node[neighbours.size()];
				neighboursArray = neighbours.toArray(neighboursArray);
				map.get(i).get(j).addNeighbours(neighboursArray); // Cast to
																	// Node
			}
		}
		randomizeNodes(maxI, maxJ); // Cast to Node[]
		return;
	}

	public void randomizeNodes() {
		for (int i = 0; i < WIDTH; i += Node.WIDTH) {
			for (int j = 0; j < HEIGHT; j += Node.HEIGHT) {
				randomizeNode(map.get((int) i / Node.WIDTH).get(
						(int) j / Node.WIDTH));
			}
		}
		repaint();
		return;
	}

	public void randomizeNodes(int maxI, int maxJ) {
		for (int i = 0; i < maxI; i++) {
			for (int j = 0; j < maxJ; j++) {
				randomizeNode(map.get(i).get(j));
			}
		}
		repaint();
		return;
	}

	public void randomizeNode(Node node) {
		Map<String, Integer> neighbourTypes = new HashMap<String, Integer>();
		for (Node neighbour : node.neighbours) {
			String terrainType = neighbour.terrain;
			if (neighbourTypes.containsKey(terrainType)) {
				neighbourTypes.put(terrainType,
						neighbourTypes.get(terrainType) + 1);
			} else {
				neighbourTypes.put(terrainType, 1);
			}
		}
		/*
		 * for (int i = 0; i < node.neighbours.size(); i++) { String terrainType
		 * = node.neighbours.get(i).terrain; if
		 * (neighbourTypes.containsKey(terrainType)) {
		 * neighbourTypes.put(terrainType, neighbourTypes.get(terrainType) + 1);
		 * } else { neighbourTypes.put(terrainType, 1); } }
		 */
		Map<String, Integer> terrainChance = new HashMap<String, Integer>();
		for (String terrain : Node.terrainTypes) {
			terrainChance.put(terrain, 100 / Node.terrainTypes.length);
		}
		for (String terrain : Node.terrainTypes) {
			terrainChance.put(terrain, terrainChance.get(terrain)
					+ (Node.terrainTypes.length - 1) * 4);
			for (String otherTerrains : Node.terrainTypes) {
				if (terrain.equals(terrain)) {
					terrainChance.put(otherTerrains,
							terrainChance.get(otherTerrains)
									- (Node.terrainTypes.length - 1));
				}
			}
		}
		int[] typePercent = new int[100];
		int index = 0;
		for (int i = 0; i < Node.terrainTypes.length; i++) {
			for (int j = 0; j < terrainChance.get(Node.terrainTypes[i]); j++) {
				typePercent[index] = Node.terrainTypesInt[i];
				index++;
			}
		}

		node.setTerrain(typePercent[(int) (Math.random() * 100)]);
		repaint();
		return;
	}

	private class DrawingPanel extends JPanel {
		DrawingPanel() {
			super();
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.BLACK);
			for (int i = 0; i < map.size(); i++) {
				for (int j = 0; j < map.get(i).size(); j++) {
					Node node = map.get(i).get(j);
					node.repaint(g);
				}
			}
		}
	}

	public static void main(String args[]) {
		Pathfinder pathfinder = new Pathfinder();
		return;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (start == null) {
			start = map.get((int) e.getX() / Node.WIDTH).get(
					(int) e.getY() / Node.HEIGHT);
		} else if (goal == null) {
			goal = map.get((int) e.getX() / Node.WIDTH).get(
					(int) e.getY() / Node.HEIGHT);
			dijkstra(start, goal);
			start = null;
			goal = null;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}
}
