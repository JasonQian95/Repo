/** Code to test the Deck class
 * @author Ridout
 * @version October 2012
 */

public class DeckTestCode
{
   public static void main(String[] args)
   {
	   System.out.println("Deck test code");
      // Check the Deck class 
      // Create a new Deck and print it (Checks toString)
      Deck myDeck = new Deck();
      System.out.println(myDeck);

      // Deal some cards from the Deck
      while (myDeck.cardsLeft() > 26)
      {
	  Card nextCard = myDeck.dealACard();
	  System.out.print(nextCard + " ");
      }
      System.out.println();
      System.out.println("Cards left in this Deck: " + myDeck.cardsLeft());
      System.out.println(myDeck);
      
      // Shuffle the Deck and Display
      myDeck.shuffle();
      System.out.println("\nShuffled Deck");
      System.out.println(myDeck);
      
      // Find the total value of all of the Cards in the Deck
      System.out.println("\nDeal each Card and find the total value");
      int totalValue = 0;
      while(myDeck.cardsLeft()> 0)
      {
	 Card nextCard = myDeck.dealACard();
	 System.out.print(nextCard+ " ");
	 totalValue += nextCard.getValue();
      }
      System.out.println("\nTotal value: "+ totalValue);
    }

}
