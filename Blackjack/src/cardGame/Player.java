package cardGame;

import java.util.ArrayList;

public class Player {

	private Hand hand;
	private int money = 0;
	private boolean turn = true;
	private String name;

	public Player() {
		hand = new Hand();
	}

	public Player(int money) {
		hand = new Hand();
		this.money = money;
	}

	public Player(String name) {
		this.name = name;
		hand = new Hand();
	}

	public Player(int money, String name) {
		hand = new Hand();
		this.name = name;
		this.money = money;
	}

	public void draw(Deck deck) {
		hand.draw(deck);
	}

	public void newHand() {
		hand = new Hand();
	}
	
	public Hand getHand() {
		return hand;
	}

	public ArrayList<Card> getHandArrayList() {
		return hand.getHand();
	}

	public int getMoney() {
		return money;
	}

	public void addMoney(int money) {
		this.money += money;
	}

	public boolean getTurn() {
		return turn;
	}

	public void giveTurn() {
		turn = true;
	}

	public void endTurn() {
		turn = false;
	}

	public String getName() {
		return name;
	}

	private class NotTurnException extends Exception {

		public NotTurnException() {
		}

		public NotTurnException(String message) {
			super(message);
		}
	}
}
