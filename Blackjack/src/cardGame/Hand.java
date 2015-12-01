package cardGame;

import java.util.ArrayList;

public class Hand {
	private ArrayList<Card> hand;
	private int sum = 0;

	public Hand() {
		hand = new ArrayList<Card>(2);
	}

	public String toString() {
		String string = "[";
		for (Card card : hand) {
			string += card.toString();
		}
		string += "]";
		return string;
	}

	public void draw(Deck deck) {
		hand.add(deck.draw());
	}

	public void draw(Deck deck, int noOfCards) {
		for (int i = 0; i < noOfCards; i++) {
			draw(deck);
		}
	}

	public void shuffleBack(Deck deck) {
		for (Card card : hand) {
			deck.addCard(card);
		}
	}

	public int compareTo(Hand other) {
		return this.sum - other.sum;
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

	public Card[] getHand() {
		Card[] handAsArray = new Card[hand.size()];
		handAsArray = hand.toArray(handAsArray);
		return handAsArray;
	}

	public boolean contains(Card card) {
		return hand.contains(card);
	}

	public boolean contains(int rank, char suit) {
		if (!((rank >= 1) && (rank <= 13))) {
			if ("CDHS".indexOf(suit) >= 0) {
				return contains(new Card(rank, suit));
			}
			if (suit == '*') {
				for (Card card : hand) {
					if (card.getRank() == rank) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public int getSize() {
		return this.hand.size();
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}
}
