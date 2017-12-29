package Game;

import java.util.LinkedList;
import java.util.List;

public class Snake extends GameObject {

	// SnakeBodyParts snakeBodyParts;
	List<SnakeBody> snakeBodyParts = new LinkedList<>();
	private SnakeBody tail = new SnakeBody();
	
	public boolean bodyPartsEmpty() {
		return snakeBodyParts.isEmpty();
	}
	
	public void addBodyPart(final Position position) {
		SnakeBody part = new SnakeBody();
		part.setPosition(new Position(position));
		
		snakeBodyParts.add(part);
	}
	
	public SnakeBody getBodyPart(final int index) {
		return snakeBodyParts.get(index);
	}
	
	public int getLength() {
		return snakeBodyParts.size();
	}
	
	public void reposition() {
		if (!snakeBodyParts.isEmpty()) {
			// temporary solution
			SnakeBody sB = new SnakeBody();
			// new body because otherwise we will reference the snakehead
			sB.setPosition(new Position(getPosition()));
			
			List<SnakeBody> snakeBodyPartsCopy = new LinkedList<>(snakeBodyParts);
			
			snakeBodyParts.set(0, sB);
			
			// point to the copy and no to the original
			for (int part = 1; part < snakeBodyParts.size(); part++) {
				snakeBodyParts.set(part, snakeBodyPartsCopy.get(part-1));
			}
	
		}
	}

	public SnakeBody getTail() {
		return tail;
	}
	
	public SnakeBody getLastBodyPart() {
		return snakeBodyParts.get(snakeBodyParts.size() - 1);
	}
	
	public void moveRight() {
		reposition();
		getPosition().setX(getPosition().getX() + 1);
	}
	
	public void moveLeft() {
		reposition();
		getPosition().setX(getPosition().getX() - 1);
	}
	
	public void moveDown() {
		reposition();
		getPosition().setY(getPosition().getY() + 1);
	}
	
	public void moveUp() {
		reposition();
		getPosition().setY(getPosition().getY() - 1);
	}
	
}
