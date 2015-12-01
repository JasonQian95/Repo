/**
 * The PokerHand class stores and creates PokerHand objects used for poker-based
 * card games
 * 
 * @author Jason Qian
 * @version November 11, 2012
 */

public class PokerHand extends Hand {
	// Different Hand types
	final static int ROYAL_FLUSH = 9; // 10, J, Q, K and A in the same suit
	final static int STRAIGHT_FLUSH = 8; // straight with all cards in the same
											// suit
	final static int FOUR_OF_A_KIND = 7; // 4 cards of the same rank
	final static int FULL_HOUSE = 6; // 3 of a kind and a pair
	final static int FLUSH = 5; // 5 cards of the same suit
	final static int STRAIGHT = 4; // 5 cards in a row 8, 9, T, J, Q - A is high
									// or low
	final static int THREE_OF_A_KIND = 3; // 3 cards with the same rank
	final static int TWO_PAIR = 2; // 2 sets of 2 cards with the same rank
	final static int PAIR = 1; // 2 cards with the same rank
	final static int NOTHING = 0; //

	final static String[] TYPES = { "Nothing", "Pair", "Two Pair",
			"Three of a Kind ", "Straight", "Flush", "Full House",
			"Four of a Kind", "Straight Flush", "Royal Flush" };
	final static int[] SCORES = { 0, 1, 3, 6, 12, 5, 10, 16, 30, 50 };

	/**
	 * Creates a new Poker Hand object; will be empty
	 */
	public PokerHand() {
		super();
	}

	/**
	 * Creates a new Poker sHand object from a String; the Cards in the Hand
	 * will be determined by the input of the String
	 */
	public PokerHand(String handStr) {
		super(handStr);
	}

	/**
	 * Tells what kind of poker hand it is
	 * 
	 * @return the type of poker hand
	 */
	public int getType() {
		// Declare variables
		int[] rankCount = new int[15];
		int[] suitCount = new int[5];
		boolean flush = false;
		boolean straight = false;
		int noOfTrips = 0;
		int noOfPairs = 0;
		int consecCards = 0;
		int flushSuit = 0;

		// Add each Card in the Hand to an array of ranks and suits
		for (Card nextCard : hand) {
			rankCount[nextCard.getRank()]++;
			suitCount[nextCard.getSuit()]++;
		}

		// Set the aces to both its values
		rankCount[1] = rankCount[14];

		// Check through the ranks array for pairs, trips, quads, and straights
		for (int next : rankCount) {

			// Pair/trip/quad check
			if (next == 2) {
				noOfPairs++;
			} else if (next == 3) {
				noOfTrips++;
			} else if (next == 4) {
				// A four of a kind cannot be any other hand for 5 and 7 card
				// hands; we can return it right away
				return FOUR_OF_A_KIND;
			}

			// Straight check
			if (next >= 1) {
				consecCards++;
			} else {
				consecCards = 0;
			}
			if (consecCards >= 5) {
				straight = true;
			}
		}

		// Don't count aces twice
		if (rankCount[1] == 2) {
			noOfPairs--;
		}
		if (rankCount[1] == 3) {
			noOfTrips--;
		}

		// Check through the suits array for flushes. Record the suit of the
		// flush (needed for 7 card hands)
		for (int next : suitCount) {
			if (next >= 5) {
				flush = true;
				for (Card nextCard : hand) {
					if (suitCount[nextCard.getSuit()] >= 5) {
						flushSuit = nextCard.getSuit();
					}
				}
			}
		}

		// If a straight and a flush are both present, check to see it if it a
		// straight flush
		if (flush == true && straight == true) {

			// Another array only of ranks of the same suit. Works the same as
			// the previous rank array, but it is only necessary to check for
			// straights here
			int[] straightFlushCount = new int[15];
			for (Card nextCard : hand) {
				if (nextCard.getSuit() == flushSuit) {
					straightFlushCount[nextCard.getRank()]++;
				}
			}
			straightFlushCount[1] = straightFlushCount[14];
			consecCards = 0;

			// Variables
			boolean straightFlush = false;
			boolean royalFlush = false;

			// Check if a straight flush is present
			for (int next : straightFlushCount) {
				if (next >= 1) {
					consecCards++;
				} else {
					consecCards = 0;
				}
				if (consecCards >= 5) {
					straightFlush = true;

					// Check if the straight flush is a royal flush
					if (straightFlushCount[10] >= 1
							&& straightFlushCount[11] >= 1
							&& straightFlushCount[12] >= 1
							&& straightFlushCount[13] >= 1
							&& straightFlushCount[14] >= 1) {
						royalFlush = true;
					}
				}
			}

			// Return statements
			if (royalFlush) {
				return ROYAL_FLUSH;
			} else if (straightFlush) {
				return STRAIGHT_FLUSH;
			}
		}

		// Return statements for other hands
		if (flush == true) {
			return FLUSH;
		} else if (straight == true) {
			return STRAIGHT;
		}
		if (noOfTrips >= 1) {
			if (noOfPairs >= 1 || noOfTrips >= 2) {
				return FULL_HOUSE;
			} else {
				return THREE_OF_A_KIND;
			}
		}
		if (noOfPairs >= 2) {
			return TWO_PAIR;
		} else if (noOfPairs == 1) {
			return PAIR;
		}
		return NOTHING;
		/*
		 * boolean flush = true; boolean straight = true; int noOfSameCards = 1;
		 * boolean trips = false; int noOfPairs = 0; for (int index = 0; index <
		 * 4; index++) { if (hand.get(4).suit - hand.get(index).suit != 0) {
		 * flush = false; } if (hand.get(4).rank - hand.get(index).rank != 4 -
		 * index) { straight = false; } } for (int index = 1; index < 5;
		 * index++) { if (hand.get(index).rank - hand.get(index - 1).rank == 0)
		 * { noOfSameCards++; if (noOfSameCards == 2) { noOfPairs++; } if
		 * (noOfSameCards == 3) { noOfPairs--; trips = true; } if (noOfSameCards
		 * == 4) { return FOUR_OF_A_KIND; } } else { noOfSameCards = 1; } } if
		 * (straight == true && flush == true) { if (hand.get(0).rank == 10) {
		 * return ROYAL_FLUSH; } return STRAIGHT_FLUSH; } if (trips == true) {
		 * if (noOfPairs == 1) { return FULL_HOUSE; } else { return
		 * THREE_OF_A_KIND; } } if (flush == true) { return FLUSH; } if
		 * (straight == true) { return STRAIGHT; } if (noOfPairs == 2) { return
		 * TWO_PAIR; } if (noOfPairs == 1) { return PAIR; } return NOTHING;
		 */
	}

	/**
	 * Gets the point value of the hand
	 * 
	 * @return the score value of the hand
	 */
	public int getScore() {
		return SCORES[getType()];
	}

	/**
	 * Gets the reverse point value of the hand
	 * 
	 * @return the reverse score value of the hand
	 */
	public int getReverseScore() {
		return SCORES[9 - getType()];
	}
}
