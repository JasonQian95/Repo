public class SinglePlayerGame extends BlackjackGame{

	public SinglePlayerGame() {
		super();
		addPlayer(new BlackjackPlayer(defaultMoney, "You"));
		runGame();
	}
	
	public SinglePlayerGame(int money) {
		super(money);
		addPlayer(new BlackjackPlayer(defaultMoney, "You"));
		runGame();
	}

}
