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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

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
		
		addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent event) {
		        if (SwingUtilities.isLeftMouseButton(event)) {
		            handleBoardClick(event);
		        }
		    }
		});
	}
	
	private void handleBoardClick(MouseEvent event) {
	    if (_board.isTurnFinished()) {
	        return;
	    }

	    // Recalculate cell size so clicks still work after resizing.
	    int cellWidth = getWidth() / _columns;
	    int cellHeight = getHeight() / _rows;
	    int x = event.getX();
	    int y = event.getY();
	    BoardCell clicked = null;

	    if (x >= 0 && y >= 0 && x < cellWidth * _columns && y < cellHeight * _rows) {
	        clicked = _board.getCell(y / cellHeight, x / cellWidth);
	    }

	    if (_board.handleClick(clicked)) {
	        redraw();
	    } else {
	        JOptionPane.showMessageDialog(this, "Choose a highlighted destination.",
	                "Invalid Move", JOptionPane.WARNING_MESSAGE);
	    }
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
				if (_board.isHumanTarget(currCell)) {
				    g.setColor(new Color(255, 230, 0, 150));
				    g.fillRect(j * _width + 1, i * _height + 1, _width - 1, _height - 1);
				}
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
		drawPlayers(g);
	}
	
	// Draw players sharing a room as separate tokens in its center cell.
	private void drawPlayers(Graphics g) {
	    Map<BoardCell, List<Player>> playersByCell = new HashMap<>();
	    for (Player player : _board.getPlayers()) {
	        BoardCell cell = _board.getCell(player.getRow(), player.getColumn());
	        playersByCell.computeIfAbsent(cell, key -> new ArrayList<>()).add(player);
	    }

	    for (List<Player> players : playersByCell.values()) {
	        if (players.size() == 1) {
	            players.get(0).draw(g, _width, _height);
	            continue;
	        }

	        int columns = Math.min(3, players.size());
	        int rows = (players.size() + columns - 1) / columns;
	        int tokenWidth = Math.max(1, _width / columns);
	        int tokenHeight = Math.max(1, _height / rows);

	        for (int i = 0; i < players.size(); i++) {
	            Player player = players.get(i);
	            int x = player.getColumn() * _width + (i % columns) * tokenWidth;
	            int y = player.getRow() * _height + (i / columns) * tokenHeight;
	            g.setColor(player.getColor());
	            g.fillOval(x, y, tokenWidth, tokenHeight);
	            g.setColor(Color.BLACK);
	            g.drawOval(x, y, tokenWidth, tokenHeight);
	        }
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
