package Game;

public class Board {

	private int[][] board;
	private Snake snake;
	private Fruit fruit;
	
	public Board(final int row, final int column) {
		// set up board and generate snake and fruit object
		board = new int[row][column];
		snake = new Snake();
		fruit = new Fruit();
		
		fillGrid(GameObjectType.EMPTY);
		// set random position of snake and fruit
		setRandomPosition(snake, GameObjectType.SNAKE);
		setRandomPosition(fruit, GameObjectType.FRUIT);
	}
	
	private void fillGrid(final GameObjectType gameObjectType) {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				board[r][c] = gameObjectType.ordinal();
			}
		}
	}
	
	private void setRandomPosition(final GameObject gameObject, final GameObjectType gameObjectType) {
		Position randomPosition = Position.getRandomPosition(GameSettings.COLUMN, GameSettings.ROW);
		
		// the cell of our board can should not be empty and when this case occurs
		// we will recursively call the same method to try it again
		if (getGameObjectTypeOnBoard(randomPosition) != GameObjectType.EMPTY) {
			setRandomPosition(gameObject, gameObjectType);
		} else {
			setGameObjectTypeOnBoard(randomPosition, gameObjectType);
			gameObject.setPosition(randomPosition);	
		}
	}
	
	public void updateSnakePosition() {
		// snake head
		if (!isSnakeCollided())
			setGameObjectTypeOnBoard(snake.getPosition(), GameObjectType.SNAKE);
		
		// attach body to snake head
		for (int i = 0; i < snake.getLength(); i++) {
			setGameObjectTypeOnBoard(snake.getBodyPart(i).getPosition(), GameObjectType.SNAKE);
		}
		
		// remove the last body part from the board
		setGameObjectTypeOnBoard(snake.getTail().getPosition(), GameObjectType.EMPTY);
	}
	
	
	private GameObjectType getGameObjectTypeOnBoard(final Position position) {
		return GameObjectType.values()[board[position.getY()][position.getX()]];
	}
	 
	private void setGameObjectTypeOnBoard(final Position position, final GameObjectType gameObjectType) {
		board[position.getY()][position.getX()] = gameObjectType.ordinal();
	}
	
	public int[][] getBoard() {
		return board;
	}
	
	public Snake getSnake() {
		return snake;
	}
	
	public Fruit getFruit() {
		return fruit;
	}

	public boolean isSnakeCollided() {
		// outsource in own method
		// snake hits himself
		for (int i = 0; i < snake.getLength(); i++) {
			if (snake.getPosition().equals(snake.getBodyPart(i).getPosition())) {
				return true;
			}
		}
		
		// outsource in own method
		// hits wall
		if (snake.getPosition().getX() > GameSettings.COLUMN - 1 || snake.getPosition().getX() < 0
				|| snake.getPosition().getY() > GameSettings.ROW - 1 || snake.getPosition().getY() < 0) {
			return true;
		}
		
		return false;
	}

	public boolean isFruitEaten() {
		if (snake.getPosition().equals(fruit.getPosition())) {
			setRandomPosition(fruit, GameObjectType.FRUIT);
			return true;
		}
		
		return false;
	}
	
	// DEBUG
	public void printSnakeCoord() {
		System.out.println("X: " + snake.getPosition().getX() + ", Y: " + snake.getPosition().getY());
	}
	
}