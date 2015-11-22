import cardGame.*;
import java.util.ArrayList;

public class BlackjackGame {
	private int noOfDecks = 1; // size of communal deck
	protected int defaultMoney = 100;

	private Deck deck;
	private int pot = 0;
	private ArrayList<BlackjackPlayer> players = new ArrayList<BlackjackPlayer>(7);
	private Dealer dealer = new Dealer();

	BlackjackGame() {
		deck = new Deck();

	}

	BlackjackGame(int defaultMoney) {
		this.defaultMoney = defaultMoney;
		deck = new Deck();

	}

	public void turnCycle() {
		for(BlackjackPlayer player:players){
			player.giveTurn();
			player.takeTurn();
			if(player.getBust()){
				dealer.collectFrom(player);
				if(player.getBankrupt()){
					players.remove(player);
				}
			}
		}
	}
	
	public void runGame(){
		while(players.size()>0){
			turnCycle();
		}
	}
	
	public void addPlayer(BlackjackPlayer player){
		players.add(player);
	}

}
