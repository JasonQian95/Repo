/**
 * The Deck class stores and creates Deck objects used for the BlackJack game
 * 
 * @author Jason Qian
 * @version October 18, 2012
 */
public class Deck {
	protected Card[] deck;
	private int topCard;

	/**
	 * The Deck Constructor; creates a Deck object. Will create a defulat deck
	 * of 52 Card objects
	 */
	public Deck() {
		deck = new Card[52];
		topCard = 0;
		for (int suit = 1; suit <= 4; suit++) {
			for (int rank = 2; rank <= 14; rank++) {
				deck[topCard++] = new Card(rank, suit);
			}
		}
	}

	/**
	 * Shuffles all cards back into the deck and randomizes it
	 */
	public void shuffle() {
		topCard = deck.length;
		for (int cardNo = 0; cardNo < topCard; cardNo++) {
			int swapCardIndex = (int) (Math.random() * topCard);
			Card swapCard = deck[cardNo];
			deck[cardNo] = deck[swapCardIndex];
			deck[swapCardIndex] = swapCard;
		}
	}

	/**
	 * Deals the top card of the deck
	 * 
	 * @return the card being dealt; the top card of the deck
	 */
	public Card dealACard() {
		return deck[--topCard];
	}

	/**
	 * Readds the last dealt card to the deck
	 */
	public void undoLastDealtCard() {
		topCard++;
	}

	/**
	 * Tells how many cards are left in the deck
	 * 
	 * @return the number of cards left in the deck
	 */
	public int cardsLeft() {
		return topCard;
	}

	/**
	 * Creates a String object with the Deck's information
	 * 
	 * @return the String version of the Deck object; the rank and suit of each
	 *         card left in the deck
	 */
	public String toString() {
		StringBuilder deckStr = new StringBuilder();
		for (int cardNo = 0; cardNo < 52; cardNo++) {
			deckStr.append(deck[cardNo].toString());
			deckStr.append(" ");
		}
		return deckStr.toString();
	}
}