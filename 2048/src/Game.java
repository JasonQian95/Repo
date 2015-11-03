import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.KeyStroke;
import javax.swing.border.BevelBorder;

public class Game extends JFrame implements KeyListener, ActionListener {

	private static class Block extends Rectangle {
		protected static int baseValue;
		protected int power;
		protected int xPos, yPos;
		protected static final int WIDTH = 50;
		protected static final int HEIGHT = 50;

		Block(int value, int xPos, int yPos) {
			this.power = 0;
			this.xPos = xPos;
			this.yPos = yPos;
		}

		protected void combine(Block other) {
			if (this.power == 0) {
				this.power = other.power;
				other.power = 0;
			} else if (this.power == other.power) {
				this.power = this.power + 1;
				other.power = 0;
			}
			return;
		}

		public void repaint(Graphics g) {
			int colourVariable = 255 - (this.power * 50);
			g.setColor(new Color(255, 255 / 4, 255 / 4, 3 * 256 / 4));
			g.fillRect(this.xPos + 1, this.yPos + 1, Block.WIDTH - 2,
					Block.HEIGHT - 2);
			g.setColor(Color.BLACK);
			if (this.power != 0) {
				int charsToDrawLength = 1;
				char[] charsToDraw;
				if (game.showPower) {
					charsToDraw = new char[charsToDrawLength];
					charsToDraw[0] = String.valueOf(this.power).charAt(0);
				} else {
					charsToDrawLength = String.valueOf(
							(int) Math.pow(this.baseValue, this.power))
							.length();
					charsToDraw = new char[charsToDrawLength];
					for (int i = 0; i < charsToDrawLength; i++) {
						charsToDraw[i] = String.valueOf(
								(int) Math.pow(this.baseValue, this.power))
								.charAt(i);
					}
				}
				g.setFont(font);
				g.drawChars(charsToDraw, 0, charsToDrawLength,
						(int) (this.xPos + Block.WIDTH / 2) - charsToDrawLength * 5,
						(int) (this.yPos + Block.HEIGHT / 2) + 3);
			}
		}
	}

	private class Grid extends Rectangle {
		protected int columns, rows;
		protected Block[][] blocks;

		Grid(int blockValues, int rows, int columns) {
			Block.baseValue = blockValues;
			this.rows = rows;
			this.columns = columns;
			blocks = new Block[columns][rows];
			for (int i = 0; i < columns; i++) {
				for (int j = 0; j < rows; j++) {
					blocks[i][j] = new Block(0, Block.HEIGHT * i, Block.WIDTH
							* j);
				}
			}
			generateNewBlock();
		}

		protected void generateNewBlock() {
			ArrayList<Block> emptyBlocks = new ArrayList<Block>();
			for (Block[] column : blocks) {
				for (Block block : column) {
					if (block.power == 0) {
						emptyBlocks.add(block);
					}
				}
			}
			if (emptyBlocks.isEmpty()) {
				game.gameOver(Game.LOSS);
				return;
			}
			emptyBlocks.get((int) (Math.random() * emptyBlocks.size())).power = 1;
			return;
		}

		protected void shiftLeft() {
			for (int iteration = 0; iteration < columns - 1; iteration++) {
				for (int i = 0; i < columns - 1; i++) {
					for (int j = 0; j < rows; j++) {
						blocks[i][j].combine(blocks[i + 1][j]);
					}
				}
			}
			generateNewBlock();
		}

		protected void shiftRight() {
			for (int iteration = 0; iteration < columns - 1; iteration++) {
				for (int i = columns - 1; i > 0; i--) {
					for (int j = 0; j < rows; j++) {
						blocks[i][j].combine(blocks[i - 1][j]);
					}
				}
			}
			generateNewBlock();
		}

		protected void shiftUp() {
			for (int iteration = 0; iteration < rows - 1; iteration++) {
				for (int i = 0; i < columns; i++) {
					for (int j = 0; j < rows - 1; j++) {
						blocks[i][j].combine(blocks[i][j + 1]);
					}
				}
			}
			generateNewBlock();
		}

		protected void shiftDown() {
			for (int iteration = 0; iteration < rows - 1; iteration++) {
				for (int i = 0; i < columns; i++) {
					for (int j = rows - 1; j > 0; j--) {
						blocks[i][j].combine(blocks[i][j - 1]);
					}
				}
			}
			generateNewBlock();
		}

		public void repaint(Graphics g) {
			for (int i = 0; i < columns; i++) {
				for (int j = 0; j < rows; j++) {
					g.drawRect(i * Block.WIDTH, j * Block.HEIGHT, Block.WIDTH,
							Block.HEIGHT);
					/*
					 * char[] charsToDraw = new char[2]; charsToDraw[0] =
					 * String.valueOf(i).charAt(0); charsToDraw[1] =
					 * String.valueOf(j).charAt(0); g.setFont(font);
					 * g.drawChars(charsToDraw, 0, 2, i * Block.WIDTH + 15, j *
					 * Block.HEIGHT + 15);
					 */
					blocks[i][j].repaint(g);
				}
			}
		}
	}

	static int blockValue = 2;
	static int rows = 4;
	static int columns = 4;
	int width;
	int height;
	DrawingPanel drawingPanel;
	Grid grid;
	static final Font font = new Font("Georgia", Font.BOLD, 18);

	protected static Game game;
	protected static final int LOSS = -1;
	protected static final int WIN = 1;
	protected boolean showPower = false;

	Game() {
		super("2048");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		// Container content = getContentPane();

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screen.width - width) / 2,
				(screen.height - height) / 2 - 100);

		JMenu gameMenu = new JMenu("Game");

		JMenuItem newOption = new JMenuItem("New");
		newOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				InputEvent.CTRL_MASK));
		newOption.addActionListener(this);
		newOption.setActionCommand("New");
		gameMenu.add(newOption);
		gameMenu.addSeparator();

		JMenuItem exitOption = new JMenuItem("Exit");
		exitOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				InputEvent.CTRL_MASK));
		exitOption.addActionListener(this);
		exitOption.setActionCommand("Exit");
		gameMenu.add(exitOption);

		JMenu baseMenu = new JMenu("Base");
		ButtonGroup baseButtonGroup = new ButtonGroup();
		JCheckBox basePower = new JCheckBox("Show Powers");
		basePower.setActionCommand("Power");
		basePower.addActionListener(this);
		baseMenu.add(basePower);
		JRadioButton baseTwo = new JRadioButton("2");
		baseTwo.setActionCommand("B2");
		baseTwo.addActionListener(this);
		baseMenu.add(baseTwo);
		baseButtonGroup.add(baseTwo);
		baseTwo.setSelected(true);
		JRadioButton baseThree = new JRadioButton("3");
		baseThree.setActionCommand("B3");
		baseThree.addActionListener(this);
		baseMenu.add(baseThree);
		baseButtonGroup.add(baseThree);
		JRadioButton baseFour = new JRadioButton("4");
		baseFour.setActionCommand("B4");
		baseFour.addActionListener(this);
		baseMenu.add(baseFour);
		baseButtonGroup.add(baseFour);

		JMenu columnMenu = new JMenu("Columns");
		ButtonGroup columnButtonGroup = new ButtonGroup();
		JRadioButton columnFour = new JRadioButton("4");
		columnFour.setActionCommand("C4");
		columnFour.addActionListener(this);
		columnMenu.add(columnFour);
		columnButtonGroup.add(columnFour);
		columnFour.setSelected(true);
		JRadioButton columnFive = new JRadioButton("5");
		columnFive.setActionCommand("C5");
		columnFive.addActionListener(this);
		columnMenu.add(columnFive);
		columnButtonGroup.add(columnFive);
		JRadioButton columnSix = new JRadioButton("6");
		columnSix.setActionCommand("C6");
		columnSix.addActionListener(this);
		columnMenu.add(columnSix);
		columnButtonGroup.add(columnSix);

		JMenu rowMenu = new JMenu("Rows");
		ButtonGroup rowButtonGroup = new ButtonGroup();
		JRadioButton rowFour = new JRadioButton("4");
		rowFour.setActionCommand("R4");
		rowFour.addActionListener(this);
		rowMenu.add(rowFour);
		rowButtonGroup.add(rowFour);
		rowFour.setSelected(true);
		JRadioButton rowFive = new JRadioButton("5");
		rowFive.setActionCommand("R5");
		rowFive.addActionListener(this);
		rowMenu.add(rowFive);
		rowButtonGroup.add(rowFive);
		JRadioButton rowSix = new JRadioButton("6");
		rowSix.setActionCommand("R6");
		rowSix.addActionListener(this);
		rowMenu.add(rowSix);
		rowButtonGroup.add(rowSix);

		JMenuBar mainMenu = new JMenuBar();
		mainMenu.add(gameMenu);
		mainMenu.add(baseMenu);
		mainMenu.add(columnMenu);
		mainMenu.add(rowMenu);
		setJMenuBar(mainMenu);

		drawingPanel = new DrawingPanel();
		drawingPanel.setBackground(new Color(255, 255, 255));
		drawingPanel.setBorder(BorderFactory
				.createBevelBorder(BevelBorder.RAISED));
		drawingPanel.addKeyListener(this);
		add(drawingPanel, BorderLayout.CENTER);
		this.setContentPane(drawingPanel);
		setVisible(true);

		this.createNewGrid();
	}

	public void createNewGrid() {
		width = (columns + 1) * Block.WIDTH;
		height = (rows + 1) * Block.HEIGHT;
		setSize(width - 30, height + 15);
		grid = new Grid(blockValue, rows, columns);
		drawingPanel.requestFocusInWindow();
		repaint();
	}

	public void gameOver(int status) {
		if (status == game.LOSS) {
			if (JOptionPane.showConfirmDialog(this,
					"You're out of moves!\nRestart?", "Game Over",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				game.createNewGrid();
			}
		}
		if (status == game.WIN) {
			if (JOptionPane.showConfirmDialog(this, "You Win!\nRestart?",
					"Victory!", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				game.createNewGrid();
			}
		}
	}

	private class DrawingPanel extends JPanel {
		DrawingPanel() {
			super();
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.BLACK);
			grid.repaint(g);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		game = new Game();
		return;
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_LEFT
				|| e.getKeyCode() == KeyEvent.VK_A) {
			grid.shiftLeft();
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT
				|| e.getKeyCode() == KeyEvent.VK_D) {
			grid.shiftRight();
		} else if (e.getKeyCode() == KeyEvent.VK_UP
				|| e.getKeyCode() == KeyEvent.VK_W) {
			grid.shiftUp();
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN
				|| e.getKeyCode() == KeyEvent.VK_S) {
			grid.shiftDown();
		}
		repaint();
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if ("New".equals(e.getActionCommand())) // Selected "New"
		{
			game.createNewGrid();
		} else if ("Exit".equals(e.getActionCommand())) // Selected "Exit"
		{
			System.exit(0);
		} else if ("Power".equals(e.getActionCommand())) // Selected "Exit"
		{
			game.showPower = true;
			game.repaint();
		} else if ('B' == e.getActionCommand().charAt(0)) {
			Game.blockValue = Character.getNumericValue(e.getActionCommand()
					.charAt(1));
			game.createNewGrid();
		} else if ('C' == e.getActionCommand().charAt(0)) {
			Game.columns = Character.getNumericValue(e.getActionCommand()
					.charAt(1));
			game.createNewGrid();
		} else if ('R' == e.getActionCommand().charAt(0)) {
			Game.rows = Character.getNumericValue(e.getActionCommand()
					.charAt(1));
			game.createNewGrid();
		}
		/*
		 * else if (e.getSource() == aboutMenuItem) // Selected "About" {
		 * JOptionPane.showMessageDialog(this, "by YOUR NAME HERE" +
		 * "\n\u00a9 2012", "About Connect Four",
		 * JOptionPane.INFORMATION_MESSAGE); }
		 */
	}

}
