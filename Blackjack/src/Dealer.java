import java.util.HashMap;
import java.util.Map;

import cardGame.*;

public class Dealer extends Player {
	private Map<BlackjackPlayer, Integer> bets = new HashMap<BlackjackPlayer, Integer>();
	private boolean bust = false;

	Dealer() {
		super(10000, "Dealer");
	}

	public void checkBust() {
		// TODO: fix?
		int sum = 0;
		int noOfAces = 0;
		for (Card card : getHandArrayList()) {
			if (card.getRank() >= 2 && card.getRank() <= 10) {
				sum += card.getRank();
			}
			if (card.getRank() >= 10) {
				sum += 10;
			}
			if (card.getRank() == 1) {
				noOfAces++;
				sum += 11;
			}
		}
		if (sum > 21) {
			for (int i = 0; i < noOfAces; i++) {
				if (sum > 21) {
					noOfAces--;
					sum -= 10;
				}
			}
		}

		getHand().setSum(sum);
		if (sum > 21) {
			bust = true;
		}
	}

	public boolean hit(Deck deck) {
		draw(deck);
		checkBust();
		return bust;
	}

	// American rules, don't hit on soft 17
	public void takeTurn(Deck deck) {
		while (getHand().getSum() < 17) {
			hit(deck);
		}
		if (bust) {
			payOutToAll();
			return;
		}
		for (BlackjackPlayer player : bets.keySet()) {
			if (this.getHand().compareTo(player.getHand()) > 0) {
				collectFrom(player);
			}
			if (this.getHand().compareTo(player.getHand()) < 0) {
				payOutTo(player);
			}
			if (this.getHand().compareTo(player.getHand()) == 0) {
				// Can compare to see if one player has blackjack, but instead
				// just split
				bets.remove(player);
			}
		}
	}

	public void takeBet(BlackjackPlayer player) {
		bets.put(player, player.getBet());
	}

	public void takeBet(BlackjackPlayer player, int bet) {
		if (player.getBet() != bet) {
			// TODO: error
		}
		bets.put(player, bet);
	}

	public void payOutToAll() {
		for (BlackjackPlayer player : bets.keySet()) {
			player.addMoney(bets.get(player) * 2);
			player.setBet(0);
			bets.remove(player);
		}
	}

	public void payOutTo(BlackjackPlayer player) {
		player.addMoney(bets.get(player) * 2);
		player.setBet(0);
		bets.remove(player);
	}

	public void collectFromAll() {
		for (BlackjackPlayer player : bets.keySet()) {
			this.addMoney(bets.get(player));
			player.setBet(0);
			bets.remove(player);
		}
	}

	public void collectFrom(BlackjackPlayer player) {
		this.addMoney(bets.get(player));
		player.setBet(0);
		bets.remove(player);
	}
}
