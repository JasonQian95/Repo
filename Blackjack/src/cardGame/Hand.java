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

	public void draw(Deck deck, int number) {
		for (int i = 0; i < number; i++) {
			hand.add(deck.draw());
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

	public ArrayList<Card> getHand() {
		return hand;
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
