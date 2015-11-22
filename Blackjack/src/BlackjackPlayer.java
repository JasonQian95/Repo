import cardGame.*;

public class BlackjackPlayer extends Player {
	private int bet = 0;
	private boolean bust = false;
	private int insurance = 0;
	private boolean bankrupt = true;

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

	public void takeTurn() {
		//ask for bet
		while(getTurn()){
			if (takeAction()){
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

	public boolean hit(Deck deck) {
		draw(deck);
		checkBust();
		return bust;
	}

	public boolean doubleDown(Deck deck) {
		this.bet = 2 * bet;
		draw(deck);
		checkBust();
		return bust;
	}

	public boolean split(Deck deck) throws CannotSplitException {
		if (getHand().getSize() != 2) {
			throw new CannotSplitException();
		}
		// TODO
		return bust;
	}

	public boolean insure() {
		if (getHand().getSize() != 2) {
			return;
		}
		insurance = this.bet / 2;
		return false;
	}

	public boolean stand() {
		endTurn();
		return true;
	}

	public void drawNewHand(Deck deck) {
		getHand().draw(deck, 2);
	}

	public void addMoney(int money) {
		super.addMoney(money);
		bankrupt = this.getMoney() > 0 ? false : true;
	}

	public int getBet() {
		return bet;
	}

	public void setBet(int bet) {
		this.bet = bet;
	}

	public int getInsurance() {
		return insurance;
	}

	public boolean getBankrupt() {
		return bankrupt;
	}

	public boolean getBust() {
		return bust;
	}

	private class CannotSplitException extends Exception {

		public CannotSplitException() {
		}

		public CannotSplitException(String message) {
			super(message);
		}
	}
}
