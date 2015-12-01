import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class Game {

	private int width = 800;
	private int height = 600;
	private DrawingPanel drawingPanel;
	private final Font font = new Font("Georgia", Font.BOLD, 18);

	private JFrame window;
	private SinglePlayerGame blackjackGame = new SinglePlayerGame();

	Game() {
		window = new JFrame("Blackjack and Hookers");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLayout(new BorderLayout());

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation((screen.width - width) / 2,
				(screen.height - height) / 2 - 100);
		window.setSize(width, height);

		drawingPanel = new DrawingPanel();
		drawingPanel.setBackground(new Color(255, 255, 255));
		drawingPanel.setBorder(BorderFactory
				.createBevelBorder(BevelBorder.RAISED));
		window.add(drawingPanel, BorderLayout.CENTER);
		window.setContentPane(drawingPanel);
		window.setResizable(false);
		window.setVisible(true);
		drawingPanel.repaint();
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
		Game game = new Game();
		while(true){ // Placeholder
			game.blackjackGame.startTurnCycle();
			for (BlackjackPlayer player: game.blackjackGame.getPlayers()){
				player.giveTurn();
				player.bet(Integer.parseInt(JOptionPane.showInputDialog(
				        game.window, 
				        "Enter bet", 
				        "Bet", 
				        JOptionPane.QUESTION_MESSAGE)));
				while (player.hasTurn()){
					
				}
			}
			game.blackjackGame.dealerTurn();
			game.blackjackGame.cleanUp();
		}
	}
}
