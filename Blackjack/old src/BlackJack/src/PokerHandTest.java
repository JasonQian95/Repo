// Checks all possible poker hands
// Frequencies should match the expected values
import java.util.Scanner;
import java.io.*;

public class PokerHandTest
{
    public static void main (String[] args) throws IOException
    {
	Scanner allHands = new Scanner (new File ("allHands.txt"));
	int[] frequency = new int [10];

	System.out.println ("Analysing all possible 5 card hands");
	long startTime = System.currentTimeMillis ();

	// Read in each hand and tally the frequencies of each hand's value
	// Also total the number of hands analysed
	int noOfHands = 0;

	while (allHands.hasNextLine ())
	{
	    PokerHand nextHand = new PokerHand (allHands.nextLine ());
	    frequency [nextHand.getType ()]++;
	    noOfHands++;
	}
	allHands.close ();

	// Display a summary of the results
	double totalPercent = 0;
	System.out.println ("Type of Hand      Number of Hands   Percentage");

	for (int type = 0 ; type <= 9 ; type++)
	{
	    double percent = 100.0 * frequency [type] / noOfHands;
	    System.out.printf ("%-18s%,12d%14.2f%n", PokerHand.TYPES [type], frequency [type], percent);
	    totalPercent += percent;
	}
	System.out.printf ("Totals  %,22d%14.2f%n", noOfHands, totalPercent);

	// Display the run time for this program
	long endTime = System.currentTimeMillis ();
	long totalTime = (endTime - startTime);
	System.out.println ("Total time: " + (totalTime / 1000.0) + " seconds");
	System.out.println ("Program complete");
    } // main method
}

