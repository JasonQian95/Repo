/**
 * The Card class stores and creates Card objects used for the BlackJack game
 * 
 * @author Jason Qian
 * @version October 15, 2012
 */
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Comparator;
import javax.swing.ImageIcon;

public class Card extends Rectangle implements Comparable<Card> {
	// Data Fields
	private int rank;
	private int suit;
	private Image image;

	// Constant Comparator object for comparing Card objects by Suit
	public static final Comparator<Card> SUIT_ORDER = new SuitOrder();

	/**
	 * The Card Constructor; creates a Card object
	 * 
	 * @param rank
	 *            the rank of the new Card object
	 * @param suit
	 *            the suit of the new Card object
	 */
	public Card(int rank, int suit) {
		super(0, 0, 0, 0);
		this.rank = rank;
		this.suit = suit;

		// Load up the appropriate image file for this card
		// File names use 01 for Ace
		if (rank == 14)
			rank = 1;
		String imageFileName = String.valueOf(rank) + " DCHS".charAt(suit)
				+ ".gif";
		if (imageFileName.length() < 7)
			imageFileName = "images\\0" + imageFileName;
		else
			imageFileName = "images\\" + imageFileName;

		image = new ImageIcon(imageFileName).getImage();

		// Set the size of the card based on the image size
		setSize(image.getWidth(null), image.getHeight(null));
	}

	/**
	 * The Card constructor; creates a Card object from a String
	 * 
	 * @param card
	 *            the String input of the rank and suit
	 */
	public Card(String card) {
		super(0, 0, 0, 0);
		rank = " 123456789TJQKA".indexOf(card.charAt(0));
		suit = " DCHS".indexOf(card.charAt(1));

		// Load up the appropriate image file for this card
		// File names use 01 for Ace
		if (rank == 14)
			rank = 1;
		String imageFileName = String.valueOf(rank) + " DCHS".charAt(suit)
				+ ".gif";
		if (imageFileName.length() < 7)
			imageFileName = "images\\0" + imageFileName;
		else
			imageFileName = "images\\" + imageFileName;

		image = new ImageIcon(imageFileName).getImage();

		// Set the size of the card based on the image size
		setSize(image.getWidth(null), image.getHeight(null));
	}

	/**
	 * Compares the rank of this card to the rank of another
	 * 
	 * @param other
	 *            the Card to compare to
	 * @return a value < 0 if the rank of this card is lower than the other, a
	 *         value > 0, if the rank of this card is higher than that of the
	 *         other if the ranks of the two are the same, it will repeat for
	 *         suits. Will return 0 if the two cards are the exact same.
	 */
	public int compareTo(Card other) {
		if (this.rank - other.rank != 0) {
			return this.rank - other.rank;
		}
		return this.suit - other.suit;
	}

	/**
	 * Creates a String object with the Card's information
	 * 
	 * @return the String version of the Card object; the rank and suit
	 */
	public String toString() {
		StringBuilder newStr = new StringBuilder();
		newStr.append(" 123456789TJQKA".charAt(rank));
		newStr.append(" DCHS".charAt(suit));

		return newStr.toString();
	}

	/**
	 * Finds the value (point value) of the Card object
	 * 
	 * @return the blackjack value of the Card
	 */
	public int getValue() {
		if (this.rank <= 9) {
			return this.rank;
		} else if (this.rank == 14) {
			return 11;
		}
		return 10;
	}

	/**
	 * Finds the rank of the Card object
	 * 
	 * @return the rank of the Card
	 */
	public int getRank() {
		return this.rank;
	}

	/**
	 * Finds the suit of the Card object
	 * 
	 * @return the suit of the Card
	 */
	public int getSuit() {
		return this.suit;
	}

	/**
	 * Finds if the Card is an ace
	 * 
	 * @return true if the card is an ace, false otherwise.
	 */
	public boolean isAce() {
		if (this.rank == 1 || this.rank == 14) {
			return true;
		}
		return false;
	}

	/**
	 * Draws a card in a Graphics context
	 * 
	 * @param g
	 *            Graphics to draw the card in
	 */
	public void draw(Graphics g) {
		g.drawImage(image, x, y, null);
	}

	/**
	 * An inner Comparator class that compares two Card objects by their suit,
	 * then by rank
	 */
	private static class SuitOrder implements Comparator<Card> {
		/**
		 * Compares the suits, then ranks of two Card objects
		 * 
		 * @param first
		 *            the first Card to compare
		 * @param second
		 *            the second Card to compare
		 * @return a value < 0 if the first Card has a lower suit, or if the two
		 *         cards have the same suit and the first Card has a lower rank,
		 *         a value > 0 if first Card has a higher suit, or if the two
		 *         cards have the same suit and the second Card has a higher
		 *         rank and 0 if the two Cards are the same
		 */
		public int compare(Card first, Card second) {
			if (first.suit - second.suit != 0) {
				return first.suit - second.suit;
			}
			return first.rank - second.rank;
		}
	}
}
