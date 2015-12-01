/**
 * The Hand class stores and creates Hand objects used for the BlackJack game
 * 
 * @author Jason Qian
 * @version October 22, 2012
 */
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Hand {
	protected ArrayList<Card> hand;
	Deck deck;

	/**
	 * Creates a new Hand object; will be empty
	 */
	public Hand() {
		hand = new ArrayList<Card>(21);
	}

	/**
	 * Creates a new Hand object from a String; the Cards in the Hand will be
	 * determined by the input of the String
	 */
	public Hand(String handStr) {
		hand = new ArrayList<Card>((handStr.length() + 1) / 3);
		Scanner scan = new Scanner(handStr);
		while (scan.hasNext()) {
			hand.add(new Card(scan.next()));
		}
		scan.close();
	}

	/**
	 * Sorts the hand by suit, and within suits by rank
	 */
	public void sortBySuit() {
		Collections.sort(hand, Card.SUIT_ORDER);
	}

	/**
	 * Sorts the Hand by rank, and within ranks by suit
	 */
	public void sortByRank() {
		Collections.sort(hand);
	}

	/**
	 * Gives the blackjack value of the hand
	 * 
	 * @return the blackjack value of the hand
	 */
	public int getValue() {
		int handValue = 0;
		int noOf11s = 0;
		for (int card = 0; card < hand.size(); card++) {
			handValue += hand.get(card).getValue();
			if (hand.get(card).getValue() == 11) {
				noOf11s++;
			}
		}
		while (handValue > 21 && noOf11s > 0) {
			handValue -= 10;
			noOf11s--;
		}
		return handValue;
	}

	/**
	 * Adds the given Card to the hand
	 * 
	 * @param cardToAdd
	 *            the card to be added
	 */
	public void addACard(Card cardToAdd) {
		hand.add(cardToAdd);
	}

	/**
	 * Removes the given Card to the hand
	 * 
	 * @param cardToRemove
	 *            the card to be removed
	 */
	public void removeACard(Card cardToRemove) {
		for (int index = 0; index < hand.size(); index++) {
			if (hand.get(index) == cardToRemove) {
				hand.remove(index);
			}
		}
	}

	/**
	 * Clears the hand; will result in an empty hand
	 */
	public void clear() {
		hand.clear();
	}

	/**
	 * Determines whether or not the hand is a "blackjack"
	 * 
	 * @return true if the hand is a blackjack; false otherwise
	 */
	public boolean isBlackJack() {
		if (this.getValue() == 21 && hand.size() == 2) {
			return true;
		}
		return false;
	}

	/**
	 * Creates a String object with the Hand's information
	 * 
	 * @return the String version of the Hand object; the rank and suit of each
	 *         card in the hand
	 */
	public String toString() {
		StringBuilder handStr = new StringBuilder(hand.size() * 3);
		for (int index = 0; index < hand.size(); index++) {
			handStr.append(hand.get(index));
			handStr.append(" ");
		}
		return handStr.toString();
	}

	/**
	 * Displays the hand in a Graphics window
	 * 
	 * @param g
	 *            Graphics context display the hand
	 */
	public void draw(Graphics g) {
		for (Card next : hand)
			next.draw(g);
	}
}
