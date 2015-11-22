package cardGame;
import java.util.ArrayList;

public class Deck {

	static final int[] ranks = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 };
	static final String[] ranksAsStrings = { "2", "3", "4", "5", "6", "7", "8",
			"9", "10", "J", "Q", "K", "A" };
	static final char[] suits = { 'S', 'H', 'D', 'C' };
	private char[] ordering = suits;
	static final int deckSize = 52;
	private ArrayList<Card> deck;

	public Deck() {
		deck = new ArrayList<Card>(deckSize);
		for (int rank : ranks) {
			for (char suit : suits) {
				deck.add(new Card(rank, suit));
			}
		}
	}

	public Card draw() {
		return deck.remove(deck.size());
	}

	public void shuffle() {
		for (int i = 0; i < deckSize; i++) {
			int cardToShuffle = (int) Math.random() * deckSize;
			Card tempCard = deck.get(i);
			deck.set(i, deck.get(cardToShuffle));
			deck.set(cardToShuffle, tempCard);
		}
	}

	public void sortBySuit(char[] ordering) {
		// TODO
	}

	public void sortBySuit(String ordering) {
		// TODO
	}
	
	public void sortBySuit() {
		// TODO
	}

	public void sortByRank() {
		// TODO
	}

	public String getSuitOrder() {
		return ordering.toString();
	}

	public void setOrdering(char[] ordering) {
		if (Card.checkValidOrdering(ordering)) {
			this.ordering = ordering;
		}
	}

	public void setOrdering(String ordering) {
		if (Card.checkValidOrdering(ordering)) {
			for (int i = 0; i < ordering.length(); i++) {
				this.ordering[i] = ordering.charAt(i);
			}
		}
	}

	public String toString() {
		String string = "[";
		for (Card card : deck) {
			string += card.toString() + ", ";
		}
		string += "]";
		return string;
	}
}
