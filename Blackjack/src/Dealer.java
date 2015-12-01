import java.util.HashMap;
import java.util.Map;

import cardGame.*;

public class Dealer extends Player {
	private Map<BlackjackPlayer, Integer> bets = new HashMap<BlackjackPlayer, Integer>();
	//Insurance bets stored on player, not in dealer
	private boolean bust = false;
	private boolean blackjack = false;

	Dealer() {
		super(10000, "Dealer");
	}

	// Methods shared with BlackjackPlayer
	public void checkBust() {
		int sum = 0;
		int noOfAces = 0;
		for (Card card : getHandArrayList()) {
			/**
			 * if (card.getRank() == 1) { noOfAces++; } sum +=
			 * BlackjackGame.cardValues.get(card.getRank());
			 **/
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

		if (sum == 21) {
			checkBlackjack();
		}
		if (sum > 21) {
			bust = true;
			sum = 0;
		}
		getHand().setSum(sum);
	}

	public boolean checkBlackjack() {
		if (getHand().getSize() == 2
				&& (getHand().contains(1, '*') && (getHand().contains(10, '*')
						|| getHand().contains(11, '*')
						|| getHand().contains(12, '*') || getHand().contains(
						13, '*')))) {
			return true;

		}
		return false;
	}

	public boolean hit(Deck deck) {
		draw(deck);
		checkBust();
		return bust;
	}

	public void drawNewHand(Deck deck) {
		discard();
		draw(deck, 2);
	}

	// End of methods shared with BlackjackPlayer

	// American rules, don't hit on soft 17
	public void takeTurn(Deck deck) {
		while (getHand().getSum() < 17) {
			hit(deck);
		}
		pay();
	}

	public void takeBet(BlackjackPlayer player) {
		bets.put(player, player.getBet());
	}

	public void takeBet(BlackjackPlayer player, int bet) {
		// TODO account for insurance and double down
		if (player.getBet() != bet) {
			System.out.println("Mismatching bets");
		}
		bets.put(player, bet);
	}

	public void pay() {
		for (BlackjackPlayer player : bets.keySet()) {
			if (player.getBust()) {
				collectFrom(player);
			} else if (this.getHand().compareTo(player.getHand()) > 0) {
				if (blackjack && player.hasInsurance()) {
					payInsurance(player);
				}
				collectFrom(player);
			} else if (this.getHand().compareTo(player.getHand()) < 0) {
				if (player.checkBlackjack()) {
					payOutToBlackjack(player);
				} else {
					payOutTo(player);
				}
			} else if (this.getHand().compareTo(player.getHand()) == 0) {
				if (blackjack && !player.getBlackjack()) {
					collectFrom(player);
				} else {
					bets.remove(player);
				}
			}
		}
	}

	public void payOutTo(BlackjackPlayer player) {
		player.addMoney(bets.get(player) * 2);
		addMoney(-bets.get(player));
		player.setBet(0);
		bets.remove(player);
	}

	public void payOutToBlackjack(BlackjackPlayer player) {
		player.addMoney((int) (bets.get(player) * 2.5));
		addMoney((int) (-bets.get(player) * 1.5));
		player.setBet(0);
		bets.remove(player);
	}

	public void collectFrom(BlackjackPlayer player) {
		this.addMoney(bets.get(player));
		addMoney(bets.get(player));
		player.setBet(0);
		bets.remove(player);
	}

	public void payInsurance(BlackjackPlayer player) {
		player.addMoney((int) (bets.get(player) * 1.5));
		addMoney((int) (-bets.get(player) * 1.5));
	}
}
