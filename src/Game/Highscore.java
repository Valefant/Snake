package Game;

import java.io.Serializable;

public class Highscore implements Serializable, Comparable<Highscore> {

	private static final long serialVersionUID = 1L;
	private String name;
	private int score;
	
	public Highscore(final String name, final int score) {
		this.name = name;
		this.score = score;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setScore(final int score) {
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
	}
	
	@Override
	public String toString() {
		return name + ":" + score;
	}

	@Override
	public int compareTo(Highscore highscore) {
		return -1 * ((Integer) score).compareTo(highscore.score);
	}
	
}
