import cardGame.*;

public class BlackjackPlayer extends Player {
	private int bet = 0;
	private boolean bust = false;
	private int insurance = 0;
	private boolean bankrupt = true;
	private boolean blackjack = false;

	public BlackjackPlayer() {
		super();
	}

	public BlackjackPlayer(int money) {
		super(money);
		bankrupt = money > 0 ? false : true;
	}

	public BlackjackPlayer(int money, String name) {
		super(money, name);
		bankrupt = money > 0 ? false : true;
	}

	public void bet(int bet) {
		this.bet += bet;
		addMoney(-bet);
	}

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

	/**
	public void takeTurn() {
		// ask for bet
		while (getTurn()) {
			if (takeAction()) {
				endTurn();
			}
		}
	}

	public boolean takeAction(Action action) {
		return true;
	}

	private interface Action {
		public boolean hit(Deck deck);

		public boolean doubleDown(Deck deck);

		public boolean split(Deck deck);

		public void insure();

		public void stand();
	}
	**/

	public boolean hit(Deck deck) {
		draw(deck);
		checkBust();
		return bust;
	}

	public boolean doubleDown(Deck deck) {
		this.bet = 2 * bet;
		draw(deck);
		checkBust();
		return true;
	}

	public boolean split(Deck deck) {
		if (getHand().getSize() != 2) {
			System.out.println("Cannot split");
		}
		// TODO
		return bust;
	}

	public boolean insure() {
		if (getHand().getSize() != 2) {
			return false;
		}
		insurance = this.bet / 2;
		return false;
	}

	public boolean stand() {
		endTurn();
		return true;
	}

	public void drawNewHand(Deck deck) {
		discard();
		draw(deck, 2);
	}

	public void addMoney(int money) {
		super.addMoney(money);
		bankrupt = this.getMoney() > 0 ? false : true;
	}

	public void reset() {
		discard();
		checkBust();
		bet = 0;
		insurance = 0;
		blackjack = false;
		endTurn();
	}

	public int getBet() {
		return bet;
	}

	public void setBet(int bet) {
		this.bet = bet;
	}

	public boolean hasInsurance() {
		return (insurance > 0);
	}

	public boolean getBankrupt() {
		return bankrupt;
	}

	public boolean getBust() {
		return bust;
	}

	public boolean getBlackjack() {
		return blackjack;
	}
}
