// The "CardTest" class.

public class CheckAllPokerHands {
	final static int HAND_SIZE = 7;

	public static void main(String[] args) {
		PokerDeck myDeck = new PokerDeck(HAND_SIZE);
		int[] noOfEachHand = new int[10];

		System.out.println("Generating and analysing all possible " + HAND_SIZE
				+ " card hands");
		long startTime = System.currentTimeMillis();

		// Generate each hand and tally the number of each hand's rank
		// Also total the number of hands analyzed
		int noOfHands = 0;
		PokerHand nextHand;
		while ((nextHand = myDeck.getNextHand()) != null) {
			noOfEachHand[nextHand.getType()]++;
			noOfHands++;
		}

		// Display a summary of the results
		double totalPercent = 0;
		System.out.println("Type of Hand      Number of Hands   Percentage");

		for (int rank = 0; rank <= 9; rank++) {
			double percent = 100.0 * noOfEachHand[rank] / noOfHands;
			totalPercent += percent;
			System.out.printf("%-18s%,12d%14.4f%n", PokerHand.TYPES[rank],
					noOfEachHand[rank], percent);
		}
		System.out.printf("Totals    %,20d%14.4f%n", noOfHands, totalPercent);

		// Display the run time for this program
		long endTime = System.currentTimeMillis();
		long totalTime = (endTime - startTime);
		System.out.println("Total time: " + (totalTime / 1000.0) + " seconds");
		System.out.println("Program complete");
	} // main method
} // CardTest class

