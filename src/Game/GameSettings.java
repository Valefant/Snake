package Game;

import java.awt.Color;

public class GameSettings {

	// WINDOW
	public static final String WINDOW_TITLE = "Snake";
	public static final int WINDOW_WIDTH = 640;
	public static final int WINDOW_HEIGHT = 480;
	public static final boolean RESIZEABLE = false;
	
	// GAME
	public static final int FPS= 30;
	public static final int ROW = 24;
	public static final int COLUMN = 32;
	public static final int SCORE_X_POS = 0 + 5;
	public static final int SCORE_Y_POS = WINDOW_HEIGHT - 10;
	public static Color SNAKE_COLOR = new Color(30, 150, 60);
	public static Color FRUIT_COLOR = new Color(200, 20, 50);
	
}

