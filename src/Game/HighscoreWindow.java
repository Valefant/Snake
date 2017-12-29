package Game;

import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class HighscoreWindow extends JDialog {

	private JTextField nameTextField;
	private JLabel[] names;
	private JLabel[] scores;
	private JButton saveBtn;
	private JButton restartBtn;
	private JButton exitBtn;
	private List<Highscore> highscores;
	
	private final String FILE_NAME = "highscores.dat";
	private final int SHOW_MAX = 5;
	
	private int currentScore;
	private boolean restart = false;
	
	public HighscoreWindow(final int currentScore) {
		highscores = new ArrayList<>();
		
		this.currentScore = currentScore;
		// look if there is any highscore file you can read of
		readHighscoresFromFile(FILE_NAME);
		
		buildGui();
//		buildListeners();
	}
	
	private void readHighscoresFromFile(final String fileName) {
		try {
			final FileInputStream is = new FileInputStream(FILE_NAME);
			final ObjectInputStream ois = new ObjectInputStream(is);
			highscores = (List<Highscore>) ois.readObject();
			is.close();
			ois.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private void writeHighscoresToFile(final String fileName) {
		try {
			final FileOutputStream out = new FileOutputStream(FILE_NAME);
			final ObjectOutputStream oos = new ObjectOutputStream(out);
			oos.writeObject(highscores);
			out.close();
			oos.close();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	private void buildGui() {
		setTitle("Highscores");
		setPreferredSize(new Dimension(480, 320));
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);
		
		final int size = highscores.size();
		names = new JLabel[size];
		scores = new JLabel[size];
		
		for (int i = 0; i < size; i++) {
			final Highscore highscore = highscores.get(i);
			names[i] = new JLabel(highscore.getName());
			scores[i] = new JLabel(String.valueOf(highscore.getScore()));
		}
		
		setLayout(new GridLayout(0, 3));
		
		nameTextField = new JTextField();
		
		add(new JLabel("Player:"));
		add(nameTextField);
		add(new JLabel());
		
		for (int i = 0; i < SHOW_MAX; i++) {
			add(new JLabel((i + 1) + "."));
			
			if (size > 0 && i < size) { 
				add(names[i]);
				add(scores[i]);
			} else {
				add(new JLabel(""));
				add(new JLabel(""));
			}
		}
		
		saveBtn = new JButton("Save");
		restartBtn = new JButton("Restart");
		exitBtn = new JButton("Exit");
		
		add(saveBtn);
		add(restartBtn);
		add(exitBtn);
		
		saveBtn.addActionListener(event -> {
			highscores.add(new Highscore(nameTextField.getText(), currentScore));
			Collections.sort(highscores);
			writeHighscoresToFile(FILE_NAME);
			System.out.println("Saved");
		});
		
		restartBtn.addActionListener(event -> {
			restart = true;
			dispose();
		});
		
		exitBtn.addActionListener(event -> {
			System.exit(0);
		});
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public boolean restartSelected() {
		return restart;
	}
	
	private void buildListeners() {
		saveBtn.addActionListener(event -> {
			highscores.add(new Highscore(nameTextField.getText(), currentScore));
			Collections.sort(highscores);
			writeHighscoresToFile(FILE_NAME);
			System.out.println("Saved");
		});
		
		restartBtn.addActionListener(event -> {
			// maybe return a value to the gamePanel
		});
		
		exitBtn.addActionListener(event -> {
			
		});
	}
	
}
