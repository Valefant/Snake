package Game;

public class Position {

	private int x;
	private int y;
	
	public Position(final Position position) {
		this.x = position.getX();
		this.y = position.getY();
	}
	
	public Position(final int x, final int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setX(final int x) {
		this.x = x;
	}

	public void setY(final int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public static Position getRandomPosition(final int x, final int y) {
		return new Position((int) (Math.random() * GameSettings.COLUMN), (int) (Math.random() * GameSettings.ROW));
	}
	
	@Override
	public boolean equals(Object object) {
		Position compare = (Position) object;
		
		if(x == compare.getX() && y == compare.getY()) {
			return true;
		}
		
		return false;
	}

	@Override
	public String toString() {
		return "Position [x=" + x + ", y=" + y + "]";
	}
	
}
