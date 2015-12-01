import java.io.Serializable;

public class Player implements Comparable<Player>, Serializable {
	private String name;
	private int score;

	Player(String name, int score) {
		this.name = name;
		this.score = score;
	}

	public String getName() {
		return this.name;
	}

	public int getScore() {
		return this.score;
	}

	public void setScore(int newScore) {
		score = newScore;
	}

	public int compareTo(Player other) {
		return -(this.score - other.score);
	}

	public String toString() {
		StringBuilder newStr = new StringBuilder();
		newStr.append(name + " " + score);
		return newStr.toString();
	}
}
