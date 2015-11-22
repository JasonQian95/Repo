package cardGame;

public class Card implements Comparable<Card> {

	private int rank;
	private char suit;
	static final char[] defaultOrdering = { 'C', 'D', 'H', 'S' };

	public Card(int rank, char suit) {
		this.rank = rank;
		this.suit = suit;
	}

	public int compareTo(Card other)  {
		if (this.rank != other.rank) {
			return compareRank(other);
		} else {
			try {
				return compareSuit(other, defaultOrdering);
			} catch (InvalidOrderingException e) {
				// Won't happen
				return 0;
			}
		}
	}

	public int compareRank(Card other) {
		return this.rank - other.rank;
	}

	public int compareSuit(Card other, String ordering) throws InvalidOrderingException {
		if (!checkValidOrdering(ordering)) {
			throw new InvalidOrderingException("Invalid ordering");
		}
		return ordering.indexOf(this.suit) - ordering.indexOf(other.suit);
	}

	public int compareSuit(Card other, char[] ordering)
			throws InvalidOrderingException {
		if (!checkValidOrdering(ordering)) {
			throw new InvalidOrderingException("Invalid ordering");
		}
		String orderingString = "";
		for (char suit : ordering) {
			orderingString += suit;
		}
		return compareSuit(other, orderingString);
	}

	public static boolean checkValidOrdering(char[] ordering) {
		String orderingString = "";
		for (char suit : ordering) {
			orderingString += suit;
		}
		return checkValidOrdering(orderingString);
	}

	public static boolean checkValidOrdering(String ordering) {
		if (ordering.length() != 4) {
			return false;
		}
		if (!ordering.contains("C")) {
			return false;
		}
		if (!ordering.contains("D")) {
			return false;
		}
		if (!ordering.contains("H")) {
			return false;
		}
		if (!ordering.contains("S")) {
			return false;
		}
		return true;
	}

	public String toString() {
		return Integer.toString(this.rank) + this.suit;
	}

	public int getRank() {
		return this.rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public char getSuit() {
		return this.suit;
	}

	public void setSuit(char suit) {
		this.suit = suit;
	}

	private class InvalidOrderingException extends Exception {
		
		public InvalidOrderingException() {
		}

		public InvalidOrderingException(String message) {
			super(message);
		}
	}
}
