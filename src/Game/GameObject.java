package Game;

public class GameObject {

	private Position position;
	
//	private GameObject(final Colour colour) {
//		this.colour = colour;
//	}
	
	public void setPosition(final Position position) {
		this.position = position;
	}
	
	// better implementation of the random method in the game object?
//	public void randomPosition() {
//		position.setX((int) (Math.random() * GameSettings.COLUMN));
//		position.setY((int) (Math.random() * GameSettings.ROW));
//	}
	
	public Position getPosition() {
		return position;
	}
	
}
