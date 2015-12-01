/**
 * Creates a Poker Deck that can be used to generate all possible Poker Hands
 * 
 * @author G. Ridout
 * @version October 2012
 */
public class PokerDeck extends Deck
{
	private int[] cardsInHand;

	/**
	 * Creates a PokerDeck that can be used to generate all of the possible
	 * hands of the given handSize
	 * 
	 * @param handSize
	 *            the number of cards in each generated PokerHand
	 */
	public PokerDeck(int handSize)
	{
		// Creates the underlying Deck object
		super();

		// Set up the starting hand with all different cards
		// Uses card numbers from 0 to 51 to keep track of each card
		cardsInHand = new int[handSize];
		for (int card = 0; card < handSize; card++)
			cardsInHand[card] = card;

		// Since we will increase the last card when we get the next hand
		// (see below), we need this to get the proper first hand
		cardsInHand[handSize - 1]--;
	}

	/**
	 * Generates the next unique PokerHand from this PokerDeck The number of
	 * cards in each hand is set when this PokerDeck was created
	 * 
	 * @return the next unique PokerHand from this PokerDeck
	 */
	public PokerHand getNextHand()
	{
		// Increase the card number of the last card in the hand
		int cardToChange = cardsInHand.length - 1;
		cardsInHand[cardToChange]++;

		// If the card number is too big, try increasing the previous card
		// This continues until we find a card that can be increased. If no
		// card can be increased, we have generated all hands - return null
		int endOfDeck = deck.length - cardsInHand.length;
		while (cardsInHand[cardToChange] > endOfDeck + cardToChange)
		{
			cardToChange--;
			if (cardToChange < 0)
				return null;
			cardsInHand[cardToChange]++;
		}

		// Reset all of the cards after the changed card to be 1 greater than
		// the previous card. This will ensure we get unique hands
		for (int card = cardToChange + 1; card < cardsInHand.length; card++)
			cardsInHand[card] = cardsInHand[card - 1] + 1;

		// Generate a new hand to return, based on the cardsInHand
		PokerHand nextHand = new PokerHand();
		for (int card = 0; card < cardsInHand.length; card++)
			nextHand.addACard(deck[cardsInHand[card]]);
		return nextHand;
	}
}
