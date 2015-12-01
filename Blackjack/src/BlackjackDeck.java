import cardGame.*;
import java.util.HashMap;
import java.util.Map;

public class BlackjackDeck extends Deck {
	protected static Map<Integer, Integer>cardValues = new HashMap<Integer,Integer>();
	
	public BlackjackDeck() {
		super();
		initalizeMappings();
	}	
	
	public BlackjackDeck(int noOfDecks) {
		super(noOfDecks);
		initalizeMappings();
	}
	
	private void initalizeMappings(){
		for(int i = 2; i<=10;i++){
			cardValues.put(i, i);
		}
		cardValues.put(11, 10);
		cardValues.put(12, 10);
		cardValues.put(13, 10);
		cardValues.put(1, 11);
	}
}
