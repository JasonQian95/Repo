import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * BlackJackTest.java
 * 
 * @author Ridout
 * @version October 2012
 */

public class HandTestCode {
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("Hand test code");

		// Check the Blackjack methods with some hands in a file
		Scanner handFile = new Scanner(new File("hands.txt"));

		while (handFile.hasNextLine()) {
			String handStr = handFile.nextLine();
			Hand nextHand = new Hand(handStr);
			System.out.print(nextHand + " Points: " + nextHand.getValue());
			System.out.print(nextHand.isBlackJack()? " : Blackjack" : "");
			System.out.println();
		}
		handFile.close();

		// Check the sorting methods in the Hand class
		// Make a hand out of a shuffled deck
		Deck myDeck = new Deck();
		myDeck.shuffle();
		Hand myHand = new Hand();
		while (myDeck.cardsLeft() > 0)
			myHand.addACard(myDeck.dealACard());

		// Display the shuffled cards and the ordered cards
		System.out.println("Shuffled Cards");
		System.out.println(myHand);
		System.out.println("Cards sorted by Suit");
		myHand.sortBySuit();
		System.out.println(myHand);

		// Mix up the deck again, clear the Hand and deal a new Hand
		myDeck.shuffle();
		myHand.clear();
		System.out.println("Empty Hand:" + myHand + "*");
		while (myDeck.cardsLeft() > 0)
			myHand.addACard(myDeck.dealACard());
		System.out.println("Cards sorted by Rank");
		myHand.sortByRank();
		System.out.println(myHand);
	}
}
