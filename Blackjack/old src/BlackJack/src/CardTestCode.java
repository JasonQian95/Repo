/**
 * Simple code to test your Card class
 * 
 * @author G. Ridout
 * @version October 2012
 */
public class CardTestCode {

	public static void main(String[] args) {
		// Code to test the Card class
		// Comment out any code you haven't written a method for yet

		// Generate all possible cards (all 4 suits and all 13 ranks)
		// This code will test the Card constructor and toString method
		System.out.println("Display all possible cards");
		for (int suit = 1; suit <= 4; suit++) {
			// This code assumes Ace is high (rank is 14)
			for (int rank = 2; rank <= 14; rank++) {
				// Create and display each card
				Card nextCard = new Card(rank, suit);
				System.out.print(nextCard + " ");
			}
			// Put each new suit on a new line
			System.out.println();
		}

		// Random cards to test the String parameter constructor
		System.out.println("Checking the String parameter constructor");
		Card first = new Card("TD");
		System.out.println("Should be TD: " + first);
		Card second = new Card("JS");
		System.out.println("Should be JS: " + second);
		Card third = new Card("3C");
		System.out.println("Should be 3C: " + third);
		Card fourth = new Card("AH");
		System.out.println("Should be AH: " + fourth);

		// Test the compareTo method
		System.out.println("Checking compareTo");
		System.out.println("Should be positive: " + second.compareTo(first));
		System.out.println("Should be negative: " + third.compareTo(first));
		System.out.println("Should be zero: " + second.compareTo(second));

		// Generate all possible cards and their blackjack values
		// This code will test the Card constructor and toString method
		System.out
				.println("Display all possible cards and their blackjack values");
		for (int suit = 1; suit <= 4; suit++) {
			// This code assumes Ace is high (rank is 14)
			for (int rank = 2; rank <= 14; rank++) {
				// Create and display each card
				Card nextCard = new Card(rank, suit);
				System.out.printf("%s:%2d ", nextCard, nextCard.getValue());
			}
			// Put each new suit on a new line
			System.out.println();
		}

		// Code to test the isAce method
		// This code should only display the 4 Aces
		System.out.println("Display only the aces");
		for (int suit = 1; suit <= 4; suit++)
			for (int rank = 2; rank <= 14; rank++) {
				// Create and display any aces
				Card nextCard = new Card(rank, suit);
				if (nextCard.isAce())
					System.out.print(nextCard + " ");
			}
	}
}