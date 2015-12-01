/** The "Poker Solitaire" class.
 * Plays a simple game of Poker Solitaire
 * @author G Ridout
 * @version October 2012
 */
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.*;

public class PokerSolitaire extends JFrame implements ActionListener {
	static final int TOP_OFFSET = 10;
	static final int LEFT_OFFSET = 13;
	static final Point DECK_POS = new Point(550 + LEFT_OFFSET, 50 + TOP_OFFSET);
	static final int ANIMATION_FRAMES = 6;

	private DrawingPanel cardArea;
	private JMenuItem newGameOption, topScoresOption, exitOption, aboutOption,
			instructionsOption, hintOption, reverseOption, discardOption,
			scoringSystemOption, animationOption;
	private Deck myDeck;
	private PokerHand[] rowHands;
	private PokerHand[] colHands;
	private Card nextCard;
	private boolean[][] spotsTaken;
	private int score;
	private boolean gameOver;

	private ArrayList<Player> topPlayers;
	int noOfHints, noOfDiscards, noOfCheats;
	final int PENALTY = 2;
	Card lastCard;
	int lastCardRow, lastCardColumn;
	boolean reverse;
	private ArrayList<Player> topReversePlayers;
	static boolean animation = false;

	/**
	 * Creates a Simple Poker Solitaire Frame Application
	 */
	public PokerSolitaire() {
		super("Poker Solitaire");

		// Add in a Menu
		JMenuBar menuBar = new JMenuBar();
		JMenu gameMenu = new JMenu("Game");
		gameMenu.setMnemonic('G');
		newGameOption = new JMenuItem("New Game");
		newGameOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				InputEvent.CTRL_MASK));
		newGameOption.addActionListener(this);

		topScoresOption = new JMenuItem("Top Scores");
		topScoresOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,
				InputEvent.CTRL_MASK));
		topScoresOption.addActionListener(this);

		exitOption = new JMenuItem("Exit");
		exitOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				InputEvent.CTRL_MASK));
		exitOption.addActionListener(this);

		animationOption = new JMenuItem("Animation");
		animationOption.addActionListener(this);
		gameMenu.add(animationOption);

		gameMenu.add(newGameOption);
		gameMenu.add(topScoresOption);
		gameMenu.addSeparator();
		gameMenu.add(exitOption);
		menuBar.add(gameMenu);

		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic('H');
		aboutOption = new JMenuItem("About");
		aboutOption.addActionListener(this);
		helpMenu.add(aboutOption);
		instructionsOption = new JMenuItem("Instructions");
		instructionsOption.addActionListener(this);
		helpMenu.add(instructionsOption);
		scoringSystemOption = new JMenuItem("Scoring System");
		scoringSystemOption.addActionListener(this);
		helpMenu.add(scoringSystemOption);
		menuBar.add(helpMenu);
		setJMenuBar(menuBar);

		JMenu cheatMenu = new JMenu("Cheat");
		cheatMenu.setMnemonic('C');
		hintOption = new JMenuItem("Hint");
		hintOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,
				InputEvent.CTRL_MASK));
		hintOption.addActionListener(this);
		cheatMenu.add(hintOption);
		discardOption = new JMenuItem("Discard");
		discardOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
				InputEvent.CTRL_MASK));
		discardOption.addActionListener(this);
		cheatMenu.add(discardOption);
		menuBar.add(cheatMenu);
		setJMenuBar(menuBar);

		JMenu reverseMenu = new JMenu("Reverse");
		reverseMenu.setMnemonic('R');
		reverseOption = new JMenuItem("Reverse");
		reverseOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
				InputEvent.CTRL_MASK));
		reverseOption.addActionListener(this);
		reverseMenu.add(reverseOption);
		menuBar.add(reverseMenu);
		setJMenuBar(menuBar);

		// Set up the layout and add in a DrawingPanel for the cardArea
		// Centre the frame in the middle (almost) of the screen
		setLayout(new BorderLayout());
		cardArea = new DrawingPanel();
		add(cardArea, BorderLayout.CENTER);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screen.width - cardArea.WIDTH) / 2,
				(screen.height - cardArea.HEIGHT) / 2 - 52);

		// Set up the deck and hands
		myDeck = new Deck();

		// We need 10 hands in total
		rowHands = new PokerHand[5];
		colHands = new PokerHand[5];
		for (int hand = 0; hand < 5; hand++) {
			rowHands[hand] = new PokerHand();
			colHands[hand] = new PokerHand();
		}
		// Set up an array to keep track of the spots taken on the table
		spotsTaken = new boolean[5][5];
		newGame();

		try {
			// Try to open the file and read in the top player information
			// read the entire ArrayList from a file
			ObjectInputStream fileIn = new ObjectInputStream(
					new FileInputStream("topPlayers.dat"));
			topPlayers = (ArrayList<Player>) fileIn.readObject();
			fileIn.close();
		} catch (Exception e) // This could include different types of
								// Exceptions
		{
			// If we had trouble reading the file (eg. it doesn't exist) or
			// if our file has errors an Exception will be thrown and we can
			// create a new empty list
			topPlayers = new ArrayList<Player>();
			for (int index = 0; index < 5; index++) {
				topPlayers.add(new Player("Computer", 0));
			}
		}
		try {
			// Try to open the file and read in the top player information
			// read the entire ArrayList from a file
			ObjectInputStream fileIn = new ObjectInputStream(
					new FileInputStream("topReversePlayers.dat"));
			topReversePlayers = (ArrayList<Player>) fileIn.readObject();
			fileIn.close();
		} catch (Exception e) // This could include different types of
								// Exceptions
		{
			// If we had trouble reading the file (eg. it doesn't exist) or
			// if our file has errors an Exception will be thrown and we can
			// create a new empty list
			topReversePlayers = new ArrayList<Player>();
			for (int index = 0; index < 5; index++) {
				topReversePlayers.add(new Player("Computer", 0));
			}
		}
		noOfDiscards = 0;
		noOfHints = 0;
		reverse = false;
	}

	/**
	 * Method that deals with the menu options
	 * 
	 * @param event
	 *            the event that triggered this method
	 */
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == newGameOption) {
			newGame();
		} else if (event.getSource() == topScoresOption) {
			StringBuilder playerData = new StringBuilder();
			if (reverse == false) {
				for (Player next : topPlayers) {
					playerData.append(next.toString() + "\n");
				}
			} else {
				for (Player next : topReversePlayers) {
					playerData.append(next.toString() + "\n");
				}
			}
			JOptionPane.showMessageDialog(cardArea, playerData, "Top Scores",
					JOptionPane.INFORMATION_MESSAGE);
		} else if (event.getSource() == exitOption) {
			System.exit(0);
		} else if (event.getSource() == aboutOption) {
			JOptionPane.showMessageDialog(cardArea, "Poker Solitaire by Ridout"
					+ "\nand Jason Qian" + "\n\u00a9 2012",
					"About Poker Solitaire", JOptionPane.INFORMATION_MESSAGE);
		} else if (event.getSource() == instructionsOption) {
			JOptionPane.showMessageDialog(cardArea,
					"Click empty spaces on the board to drop cards!"
							+ "\nYou get score for each hand you make!"
							+ "\nMake the best hands possible!",
					"Instructions", JOptionPane.INFORMATION_MESSAGE);
		} else if (event.getSource() == hintOption) {
			noOfHints++;
			updateScore();
			rePaintDrawingAreaImmediately();
			int tempScore = 0;
			int highestTempScore = 0;
			int finalColumn = 0;
			int finalRow = 0;
			for (int row = 0; row < 5; row++) {
				for (int column = 0; column < 5; column++) {
					if (!spotsTaken[row][column]) {
						rowHands[row].addACard(nextCard);
						colHands[column].addACard(nextCard);
						tempScore = 0;
						for (int hand = 0; hand < 5; hand++) {
							tempScore += rowHands[hand].getScore();
							tempScore += colHands[hand].getScore();
						}
						if (tempScore > highestTempScore) {
							highestTempScore = tempScore;
							finalRow = 5 - row;
							finalColumn = column + 1;
						}
						rowHands[row].removeACard(nextCard);
						colHands[column].removeACard(nextCard);
					}
				}
			}
			JOptionPane
					.showMessageDialog(
							cardArea,
							"Try row "
									+ finalRow
									+ " and column "
									+ finalColumn
									+ ".\nYou have asked for a Hint! Your score is decreased by 2",
							"Hint!", JOptionPane.INFORMATION_MESSAGE);
		} else if (event.getSource() == reverseOption) {
			boolean justStarted = true;
			for (int row = 0; row < 5; row++) {
				for (int column = 0; column < 5; column++) {
					if (spotsTaken[row][column] != false) {
						justStarted = false;
					}
				}
			}
			if (justStarted) {
				if (reverse) {
					reverse = false;
				} else {
					reverse = true;
				}
				JOptionPane.showMessageDialog(cardArea,
						"The scoring system is now reversed!", "Reversed!",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(cardArea,
						"You can only reverse at the start of the game!",
						"Too Late!", JOptionPane.INFORMATION_MESSAGE);
			}
			updateScore();
			rePaintDrawingAreaImmediately();
		} else if (event.getSource() == discardOption) {
			JOptionPane
					.showMessageDialog(
							cardArea,
							"You have discarded a card!\nYour score has decreased by 2!",
							"Discard!", JOptionPane.INFORMATION_MESSAGE);
			nextCard = myDeck.dealACard();
			nextCard.setLocation(DECK_POS);
			noOfDiscards++;
			updateScore();
			rePaintDrawingAreaImmediately();
		} else if (event.getSource() == scoringSystemOption) {
			if (reverse) {
				StringBuilder pointsString = new StringBuilder();
				pointsString.append(PokerHand.TYPES[0] + " "
						+ PokerHand.SCORES[PokerHand.TYPES.length - 1]);
				for (int handType = 1; handType < PokerHand.TYPES.length; handType++) {
					pointsString.append("\n"
							+ PokerHand.TYPES[handType]
							+ " "
							+ PokerHand.SCORES[PokerHand.TYPES.length
									- handType - 1]);
				}
				JOptionPane.showMessageDialog(cardArea, pointsString,
						"Scoring System!", JOptionPane.INFORMATION_MESSAGE);

			} else {
				StringBuilder pointsString = new StringBuilder();
				pointsString.append(PokerHand.TYPES[PokerHand.TYPES.length - 1]
						+ " " + PokerHand.SCORES[PokerHand.TYPES.length - 1]);
				for (int handType = (PokerHand.TYPES.length - 2); handType >= 0; handType--) {
					pointsString.append("\n" + PokerHand.TYPES[handType] + " "
							+ PokerHand.SCORES[handType]);
				}
				JOptionPane.showMessageDialog(cardArea, pointsString,
						"Scoring System!", JOptionPane.INFORMATION_MESSAGE);
			}
		} else if (event.getSource() == animationOption) {
			if (animation == false) {
				animation = true;
				JOptionPane.showMessageDialog(cardArea,
						"Animation has been turned on!", "Animation Off!",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				animation = false;
				JOptionPane.showMessageDialog(cardArea,
						"Animation has been turned off!", "Animation Off!",
						JOptionPane.INFORMATION_MESSAGE);
			}

		}

	}

	/**
	 * Starts a new game by shuffling the deck and re-initializing the hands and
	 * the spots taken on the table
	 */
	public void newGame() {
		myDeck.shuffle();

		// Clear the hands (the clear method just resets the size to 0)
		// Also reset the spotsTaken all to false
		for (int hand = 0; hand < 5; hand++) {
			rowHands[hand].clear();
			colHands[hand].clear();
			for (int column = 0; column < 5; column++)
				spotsTaken[hand][column] = false;
		}
		score = 0;
		gameOver = false;

		// Deal the first card
		nextCard = myDeck.dealACard();
		nextCard.setLocation(DECK_POS);

		noOfHints = 0;
		noOfDiscards = 0;

		updateScore();
		repaint();
	}

	/**
	 * Updates the score based on the score in all of the hands
	 */
	public void updateScore() {
		score = 0;
		noOfCheats = noOfHints + noOfDiscards;
		if (!reverse) {
			for (int hand = 0; hand < 5; hand++) {
				score += rowHands[hand].getScore();
				score += colHands[hand].getScore();
			}
		} else {
			for (int hand = 0; hand < 5; hand++) {
				score += rowHands[hand].getReverseScore();
				score += colHands[hand].getReverseScore();
			}
		}
		score -= noOfCheats * PENALTY;
	}

	/**
	 * Refresh the drawing area immediately Immediate refresh is needed to show
	 * the animation
	 */
	private void rePaintDrawingAreaImmediately() {
		cardArea.paintImmediately(new Rectangle(0, 0, cardArea.getWidth(),
				cardArea.getHeight()));
	}

	/**
	 * Inner class to keep track of the card area
	 */
	private class DrawingPanel extends JPanel {
		final Color TABLE_COLOUR = new Color(140, 225, 140);
		final int WIDTH = 700;
		final int HEIGHT = 570;

		public DrawingPanel() {
			setPreferredSize(new Dimension(WIDTH, HEIGHT));
			setFont(new Font("Arial", Font.PLAIN, 18));
			this.addMouseListener(new PokerMouseHandler());
			this.setBackground(TABLE_COLOUR);
		}

		/**
		 * Paints the drawing area
		 * 
		 * @param g
		 *            the graphics context to paint
		 */
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			// Draw the spots for the cards to go
			// You may want to make this nicer
			g.setColor(Color.black);
			for (int row = 0; row < 5; row++)
				for (int column = 0; column < 5; column++) {
					int x = column * 100;
					int y = row * 100;
					g.drawRoundRect(x + LEFT_OFFSET, y + TOP_OFFSET, 71, 96, 7,
							7);
				}

			// Draw the hands and their current scores
			// Only the row hands need to be drawn
			g.setColor(Color.blue);
			for (int hand = 0; hand < 5; hand++) {
				rowHands[hand].draw(g);
				g.drawString(String.valueOf(rowHands[hand].getScore()),
						490 + LEFT_OFFSET, hand * 100 + 55 + TOP_OFFSET);
				g.drawString(String.valueOf(colHands[hand].getScore()), hand
						* 100 + 28 + LEFT_OFFSET, 515 + TOP_OFFSET);
			}

			// Draw the total score
			g.setFont(new Font("Arial", Font.BOLD, 24));
			g.setColor(Color.blue);
			g.drawString("Score: " + score, 535, 310);

			// Draw the next card if not game over
			if (!gameOver)
				nextCard.draw(g);
			else {
				g.setColor(Color.blue);
				g.drawString("Game", 547, 110);
				g.drawString("Over", 555, 140);
			}
		}

		/**
		 * Inner class to handle mouse events Extends MouseAdapter instead of
		 * implementing MouseListener since we only need to override
		 * mousePressed
		 */
		private class PokerMouseHandler extends MouseAdapter {
			/**
			 * Handles a mousePress when selecting a spot to place a card
			 * 
			 * @param event
			 *            the event information
			 */
			public void mousePressed(MouseEvent event) {
				// If the game is over, we disable any mouse presses
				if (gameOver) {
					if (JOptionPane.showConfirmDialog(cardArea,
							"Do you want to Play Again?", "Game Over",
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
						newGame();
					return;
				}

				// Figure out the selected row and column on the board
				Point clickPoint = event.getPoint();
				int row = (clickPoint.y - TOP_OFFSET) / 100;
				int column = (clickPoint.x - LEFT_OFFSET) / 100;

				// Ignore clicks off the grid area
				if (row < 0 || row > 4 || column < 0 || column > 4)
					return;

				// Given feedback and ignore clicks on spots already taken
				if (spotsTaken[row][column]) {
					JOptionPane.showMessageDialog(cardArea,
							"This Spot is Taken,\nPlease Select Again",
							"Poker Solitaire", JOptionPane.WARNING_MESSAGE);
					return;
				}

				// Place the next card in this spot
				spotsTaken[row][column] = true;
				Point newCardPos = new Point(column * 100 + LEFT_OFFSET, row
						* 100 + TOP_OFFSET);

				if (animation)
					moveACard(nextCard, newCardPos);
				else
					nextCard.setLocation(newCardPos);

				// ... and add it to the corresponding row and col hand
				rowHands[row].addACard(nextCard);
				colHands[column].addACard(nextCard);
				lastCard = nextCard;
				lastCardRow = row;
				lastCardColumn = column;
				updateScore();
				nextCard = null;

				// Deal the next card if not done
				if (myDeck.cardsLeft() > 27 - noOfDiscards
						&& myDeck.cardsLeft() > 0) {
					nextCard = myDeck.dealACard();
					nextCard.setLocation(DECK_POS);
					rePaintDrawingAreaImmediately();
				}
				// Game is over - check for top scores
				else {
					if (myDeck.cardsLeft() == 0) {
						JOptionPane.showMessageDialog(cardArea,
								"You discarded too much and ran out of cards!",
								"Cheater cheater pumpkin eater",
								JOptionPane.INFORMATION_MESSAGE);
					}
					gameOver = true;
					rePaintDrawingAreaImmediately();
					if (!reverse) {
						if (score > topPlayers.get(4).getScore()) {
							// Update top scores
							// Enter a name dialog box
							String name;
							name = JOptionPane.showInputDialog(
									"Congratulations, you got the Top Score",
									"Please enter your name");
							if (name == null) {
								name = " ";
							}
							topPlayers.add(new Player(name, score));
							Collections.sort(topPlayers);
							topPlayers.remove(5);
							try {
								// Write the entire ArrayList to a file
								ObjectOutputStream fileOut = new ObjectOutputStream(
										new FileOutputStream("topPlayers.dat"));
								fileOut.writeObject(topPlayers);
								fileOut.close();
							} catch (Exception e) {
							}
						}
						if (JOptionPane.showConfirmDialog(cardArea,
								"Would you like to see the high scores?",
								"DO IT", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
							StringBuilder playerData = new StringBuilder();
							for (Player next : topPlayers) {
								playerData.append(next.toString() + "\n");
							}
							JOptionPane.showMessageDialog(cardArea, playerData,
									"Top Scores",
									JOptionPane.INFORMATION_MESSAGE);
						}
					} else {
						if (score > topReversePlayers.get(4).getScore()) {
							// Update top scores
							// Enter a name dialog box
							String name;
							name = JOptionPane.showInputDialog(
									"Congratulations, you got the Top Score",
									"Please enter your name");
							if (name == null) {
								name = " ";
							}
							topReversePlayers.add(new Player(name, score));
							Collections.sort(topReversePlayers);
							topReversePlayers.remove(5);
							try {
								// Write the entire ArrayList to a file
								ObjectOutputStream fileOut = new ObjectOutputStream(
										new FileOutputStream(
												"topReversePlayers.dat"));
								fileOut.writeObject(topReversePlayers);
								fileOut.close();
							} catch (Exception e) {
							}
						}
						if (JOptionPane.showConfirmDialog(cardArea,
								"Would you like to see the high scores?",
								"DO IT", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
							StringBuilder playerData = new StringBuilder();
							for (Player next : topReversePlayers) {
								playerData.append(next.toString() + "\n");
							}
							JOptionPane.showMessageDialog(cardArea, playerData,
									"Top Reverse Scores",
									JOptionPane.INFORMATION_MESSAGE);
						}
					}
					if (JOptionPane.showConfirmDialog(cardArea,
							"Do you want to Play Again?", "Game Over",
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
						newGame();
				}
			}

			/**
			 * Moves a card with a simple animation
			 * 
			 * @param cardToMove
			 *            the card you want to move
			 * @param finalPos
			 *            the final position of the card
			 */
			private void moveACard(Card cardToMove, Point finalPos) {
				int x = cardToMove.x;
				int y = cardToMove.y;
				int dx = (finalPos.x - x) / ANIMATION_FRAMES;
				int dy = (finalPos.y - y) / ANIMATION_FRAMES;
				for (int times = 1; times <= ANIMATION_FRAMES; times++) {
					x += dx;
					y += dy;
					cardToMove.setLocation(x, y);
					rePaintDrawingAreaImmediately();
					delay(50);
				}
				cardToMove.setLocation(finalPos);
				// Clear up whole card area
				rePaintDrawingAreaImmediately();
			}

			/**
			 * Delays the given number of milliseconds
			 * 
			 * @param msec
			 *            the number of milliseconds to delay
			 */
			private void delay(int msec) {
				try {
					Thread.sleep(msec);
				} catch (Exception e) {
				}
			}
		}
	}

	public static void main(String[] args) {
		PokerSolitaire game = new PokerSolitaire();
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.pack();
		game.setVisible(true);

	}
}