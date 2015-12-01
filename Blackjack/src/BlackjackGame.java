import cardGame.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class BlackjackGame {
	private int noOfDecks = 1; // size of communal deck
	protected int defaultMoney = 100;
	protected static Map<Integer, Integer> cardValues = new HashMap<Integer, Integer>();
	private Deck deck;
	private int pot = 0;
	private ArrayList<BlackjackPlayer> players = new ArrayList<BlackjackPlayer>(
			7);
	private int currentTurn = 0;
	private int currentPlayer;
	private Dealer dealer = new Dealer();

	BlackjackGame() {
		deck = new Deck();
		initalizeMappings();
	}

	BlackjackGame(int defaultMoney) {
		this.defaultMoney = defaultMoney;
		deck = new Deck();
		initalizeMappings();
	}

	private void initalizeMappings() {
		for (int i = 2; i <= 10; i++) {
			cardValues.put(i, i);
		}
		cardValues.put(11, 10);
		cardValues.put(12, 10);
		cardValues.put(13, 10);
		cardValues.put(1, 11);
	}

	public void addPlayer(BlackjackPlayer player) {
		players.add(player);
	}

	/**
	public void turnCycle() {
		startTurnCycle();
		for (BlackjackPlayer player : players) {
			player.giveTurn();
			while (player.hasTurn()) {
				String action = requestAction(player);
				if (takeAction(player, action)) {
					player.endTurn();
				}
			}
		}
		dealerTurn();
		cleanUp();
	}

	public String requestAction(BlackjackPlayer player) {
		String action = "";
		return action;
	}

	public boolean takeAction(BlackjackPlayer player, String action) {
		switch (action) {
		case "hit":
			return player.hit(deck);
		case "stand":
			return player.stand();
		case "double down":
			return player.doubleDown(deck);
		case "insure":
			return player.insure();
		case "split":
			return player.split(deck);
		}
		System.out.println("Invalid action");
		return false;
	}
	**/

	public void startTurnCycle() {
		dealer.drawNewHand(deck);
		for (BlackjackPlayer player : players) {
			player.drawNewHand(deck);
		}
	}

	public void dealerTurn() {
		dealer.takeTurn(deck);
	}

	public void cleanUp() {
		for (BlackjackPlayer player : players) {
			player.shuffleBack(deck);
			if (player.getBankrupt()) {
				players.remove(player);
			}
			player.reset();
		}
	}

	public int getNoOfPlayers() {
		return players.size();
	}

	public BlackjackPlayer[] getPlayers(){
		return players.toArray(new BlackjackPlayer[players.size()]);
	}
	
	public BlackjackPlayer getPlayer(int playerNo) {
		return playerNo < players.size() ? players.get(playerNo) : null;
	}
}
