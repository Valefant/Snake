package Game;

import java.awt.Dimension;
import javax.swing.JFrame;

public class App {

	public static void main(String[] args) {
		JFrame jFrame = new JFrame("Snake");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setResizable(false);
		jFrame.setLocationRelativeTo(null);
		jFrame.setContentPane(new GamePanel());
		jFrame.getContentPane().setPreferredSize(new Dimension(GameSettings.WINDOW_WIDTH, GameSettings.WINDOW_HEIGHT));
		jFrame.pack();
		jFrame.setVisible(true);
	}
	
}
