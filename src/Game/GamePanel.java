package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements KeyListener {

	private Board board;
	private boolean gameOver;

	private boolean leftPressed;
	private boolean rightPressed;
	private boolean downPressed;
	private boolean upPressed;
	private boolean pause;
	
	private int time = 0;
	private double speedModifier = 3;
	private int counter = 0;

	public GamePanel() {
		setBackground(Color.LIGHT_GRAY);
		addKeyListener(this);
		setFocusable(true);
		requestFocus();

		board = new Board(GameSettings.ROW, GameSettings.COLUMN);
		rightPressed = true;
		pause = false;
		gameOver = false;
		
		loop();
	}
	
	public void loop() {
		final Timer timer = new Timer(true);
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
			
				if (pause == true) {
					return;
				}
				
				if (time++ >= 90) {
					time = 0;
					
					if (speedModifier > 0) {
						speedModifier -= 0.2;
					}
				}
				
				if (counter++ < speedModifier) return;
				else counter = 0;
				
				if (!gameOver) {
					
					if (board.getSnake().bodyPartsEmpty()) {
						board.getSnake().getTail().setPosition(new Position(board.getSnake().getPosition()));
					} else {
						board.getSnake().getTail().setPosition(new Position(board.getSnake().getLastBodyPart().getPosition()));
					}
					
					if (rightPressed) {
						board.getSnake().moveRight();
					} else if (leftPressed) {
						board.getSnake().moveLeft();
					} else if (downPressed) {
						board.getSnake().moveDown();
					} else if (upPressed) {
						board.getSnake().moveUp();
					}
					
					board.updateSnakePosition();
					
					if (board.isSnakeCollided()) {
						restart();
					}
					
//					board.printSnakeCoord();
					
					if (board.isFruitEaten()) {
						System.out.println("Eaten");
						Position position = new Position(board.getSnake().getPosition());
						
						// refactor
						if (board.getSnake().getLength() < 1) {
							if (rightPressed) {
								position.setX(position.getX() - 1);
							} else if (leftPressed) {
								position.setX(position.getX() + 1);
							} else if (downPressed) {
								position.setY(position.getY() - 1);
							} else {
								position.setY(position.getY() + 1);
							}
						} else {
							int x = board.getSnake().getLastBodyPart().getPosition().getX();
							int y = board.getSnake().getLastBodyPart().getPosition().getY();
							
							if (rightPressed) {
								position.setX(x - 1 < 0 ? x : x - 1);
							} else if (leftPressed) {
								position.setX(x + 1 >= GameSettings.COLUMN ? x : x + 1);
							} else if (downPressed) {
								position.setY(y - 1 < 0 ? y : y + 1);
							} else {
								position.setY(y + 1 >= GameSettings.ROW ? y : y + 1);
							}
						}
						
						board.getSnake().addBodyPart(position);
					}
					
					repaint();
					
				}
						
			}
			
		}, 0, 1000/GameSettings.FPS);
	}
	
	private void restart() {		
		setEnabled(false);
		HighscoreWindow highscoreWindow = new HighscoreWindow(board.getSnake().getLength());

		if (!highscoreWindow.isActive()) {
			if (highscoreWindow.restartSelected()) {
				board = new Board(GameSettings.ROW, GameSettings.COLUMN);
			}
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		
		for (int r = 0; r < GameSettings.ROW; r++) {
			for (int c = 0; c < GameSettings.COLUMN; c++) {
				final int cell = board.getBoard()[r][c];
				
				if (cell == GameObjectType.SNAKE.ordinal()) {
					g.setColor(GameSettings.SNAKE_COLOR);
					g.fillRect((GameSettings.WINDOW_WIDTH / GameSettings.COLUMN) * c, (GameSettings.WINDOW_HEIGHT / GameSettings.ROW) * r, GameSettings.WINDOW_WIDTH / GameSettings.COLUMN, GameSettings.WINDOW_HEIGHT / GameSettings.ROW);
				} else if (cell == GameObjectType.FRUIT.ordinal()) {
					g.setColor(GameSettings.FRUIT_COLOR);
					g.fillOval((GameSettings.WINDOW_WIDTH / GameSettings.COLUMN) * c, (GameSettings.WINDOW_HEIGHT / GameSettings.ROW) * r, GameSettings.WINDOW_WIDTH / GameSettings.COLUMN, GameSettings.WINDOW_HEIGHT / GameSettings.ROW);
				}
			}
		}
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, 14));
		g.drawString("Score: " + board.getSnake().getLength(), GameSettings.SCORE_X_POS, GameSettings.SCORE_Y_POS);
	}
	
	// outsource in InputManager
	@Override
	public void keyPressed(KeyEvent e) {
		final int key = e.getKeyCode();

		if (key == KeyEvent.VK_SPACE) {
			if (pause) {
				pause = false;
			} else {
				pause = true;
			}

			return;
		}
		
		switch (key) {
			case KeyEvent.VK_RIGHT:
				if (!leftPressed) {
					downPressed = false;
					upPressed = false;
					rightPressed = true;
				}
				break;
			case KeyEvent.VK_LEFT:
				if (!rightPressed) {
					downPressed = false;
					upPressed = false;
					leftPressed = true;
				}
				break;
			case KeyEvent.VK_DOWN:
				if (!upPressed) {
					rightPressed = false;
					leftPressed = false;
					downPressed = true;
				}
				break;
			case KeyEvent.VK_UP:
				if (!downPressed) {
					rightPressed = false;
					leftPressed = false;
					upPressed = true;
				}
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
	
}
