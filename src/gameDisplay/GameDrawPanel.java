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
		_rows = _board.getNumRooms();
		_columns = _board.getNumColumns();
	}
	
	// Overridden paintComponent method
	@Override
	protected void paintComponent(Graphics g) {
		// Call super
		super.paintComponent(g);
		
		// Assign width and height
		_width = getWidth();
		_height = getHeight();
		
		// Calculate proper width and height for the cell size
		
		// Loop through the board, get the room initial, and set the color based off the initial 
		// (Room = Grey, Walkway = Red, Doorway = Blue, Border = Black)
		for (int i = 0; i < _rows; i++) {
			for (int j = 0; j < _columns; j++) {
				// Extract the current cell
				BoardCell currCell = _board.getCell(i, j);
				
				// Call the cells draw method
				currCell.Draw(g,  _width, _height);
			}
		}
		
		// Loop through the players and draw them
		ArrayList<Player> playerList = _board.getPlayers();
		for (Player player: playerList) {
			player.Draw(g, _width, _height);
		}
	}
}
