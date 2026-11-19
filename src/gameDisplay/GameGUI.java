package gameDisplay;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import clueGame.*;

public class GameGUI extends JPanel{
	// Member variables
	private GameControlPanel _controlPanel;
	private GameCardsPanel _cardPanel;
	private GameDrawPanel _boardPanel;
	
	// Constructor
	public GameGUI(GameControlPanel control, GameCardsPanel cards, GameDrawPanel board) {
		_controlPanel = control;
		_cardPanel = cards;
		_boardPanel = board;
	}
	
	// Method to display the full GUI
	public void displayGUI(GameGUI panel) {
		// Create the JFrame
		JFrame frame = new JFrame("Clue Game");
		
		// Set frame exit condition
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Set the frame size
		frame.setSize(700, 700);
		
		// Add the components
		frame.add(_boardPanel, BorderLayout.CENTER);
		frame.add(_controlPanel, BorderLayout.SOUTH);
		frame.add(_cardPanel, BorderLayout.EAST);
		
		// Set the frame to visible
		frame.setVisible(true);
		
		// Update the panel
		revalidate();
		repaint();
	}
	
	// Main method for testing
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Create and load the board
				Board board = Board.getInstance();
				board.setConfigFiles("ClueBoardLayout.csv", "ClueSetup.txt"); 
				board.initialize();
				
				HumanPlayer human = new HumanPlayer("Santa", "Red", 0, 0);
				
				GameControlPanel control = new GameControlPanel();
				GameCardsPanel cards = new GameCardsPanel(human);
				GameDrawPanel boardDraw = new GameDrawPanel();
				
				GameGUI gui = new GameGUI(control, cards, boardDraw);
				gui.displayGUI(gui);
			}
		});
	}
}
