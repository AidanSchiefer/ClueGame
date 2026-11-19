package gameDisplay;

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

public class GameDrawPanel extends JPanel {
	// Member Variables
	private Board _board;
	private int _rows, _columns, _width, _height;
	
	// Constructor
	public GameDrawPanel() {
		// get the Board instance
		_board = Board.getInstance();
		
		// Get the rows and columns for the board size
		_rows = _board.getNumRows();
		_columns = _board.getNumColumns();
	}
	
	// Overridden paintComponent method
	@Override
	protected void paintComponent(Graphics g) {
		// Call super
		super.paintComponent(g);
		
		// Assign width and height and calculate the total size of the cell to be drawn
		_width = getWidth() / _columns;
		_height = getHeight() / _rows;
				
		// Loop through the board, get the room initial, and set the color based off the initial 
		// (Room = Grey, Walkway = Red, Doorway = Blue, Border = Black)
		for (int i = 0; i < _rows; i++) {
			for (int j = 0; j < _columns; j++) {
				// Extract the current cell
				BoardCell currCell = _board.getCell(i, j);
				
				// Call the cells draw method
				currCell.draw(g,  _width, _height);
			}
		}
		
		// Loop through the board again, but extract and draw the roomLabel
		for (int i = 0; i < _rows; i++) {
			for (int j = 0; j < _columns; j++) {
				// Extract the current cell
				BoardCell currCell = _board.getCell(i, j);
				
				// If the cell is a room, draw it
				if (currCell.isLabel()) {
					// Extract the room label
					Room roomLabel = _board.getRoom(currCell);
					
					// Draw the room
					roomLabel.draw(g, _width, _height);
				}
			}
		}
		
		// Loop through the players and draw them
		ArrayList<Player> playerList = _board.getPlayers();
		for (Player player: playerList) {
			player.draw(g, _width, _height);
		}
	}
	
	// Method to redraw the board
	public void redraw() {
		repaint();
	}
	
	// Main method for basic testing
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Create and load the board
				Board board = Board.getInstance();
				board.setConfigFiles("ClueBoardLayout.csv", "ClueSetup.txt"); 
				board.initialize();
				
				// Create JFrame and panel objects
				JFrame frame = new JFrame("Test Board Drawing");
				GameDrawPanel panel = new GameDrawPanel();
				
				// Set frame conditions
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(450, 350);
				frame.add(panel);
				frame.setVisible(true);
			}
		});
	}
}
