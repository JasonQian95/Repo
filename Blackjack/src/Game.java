import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.KeyStroke;
import javax.swing.border.BevelBorder;

public class Game {

	int width;
	int height;
	DrawingPanel drawingPanel;
	static final Font font = new Font("Georgia", Font.BOLD, 18);

	protected static Game game;
	protected JFrame window;

	Game() {
		window = new JFrame("Blackjack and Hookers");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLayout(new BorderLayout());
		// Container content = getContentPane();

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation((screen.width - width) / 2,
				(screen.height - height) / 2 - 100);

		drawingPanel = new DrawingPanel();
		drawingPanel.setBackground(new Color(255, 255, 255));
		drawingPanel.setBorder(BorderFactory
				.createBevelBorder(BevelBorder.RAISED));
		window.add(drawingPanel, BorderLayout.CENTER);
		window.setContentPane(drawingPanel);
		window.setResizable(false);
		window.setVisible(true);

		SinglePlayerGame blackjackGame = new SinglePlayerGame();
	}
	
	private class DrawingPanel extends JPanel {
		DrawingPanel() {
			super();
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.BLACK);
			//repaint(g);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		game = new Game();
	}
}
