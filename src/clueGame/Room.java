package clueGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class Room {
	// Member variables
	// Variable to hold the name of the room
	private String _name;
	// Variables to hold the center cell and label cell of a given room
	private BoardCell _centerCell, _labelCell;

	// Default constructor
	public Room() {
		super();
	}

	// Constructor that assigns the room name - add center cell and label cell
	public Room(String name) {
		_name = name;
	}

	// Getter method to return the name of the room
	public String getName() {
		return _name;
	}

	// Getter method to return the center cell
	public BoardCell getCenterCell() {
		return _centerCell;
	}

	// Getter method to return the label cell
	public BoardCell getLabelCell() {
		return _labelCell;
	}

	// Setter method to assign the center cell
	public void setCenterCell(BoardCell centerCell) {
		_centerCell = centerCell;
	}

	// Setter method to assign the label cell
	public void setLabelCell(BoardCell labelCell) {
		_labelCell = labelCell;
	}
	
	// Draw method to draw the room name
	public void draw(Graphics g, int width, int height) {
		// Set the font of the text and scale the font with the size of a cell
		int fontSize = Math.max(8, Math.min(width, height) / 2);
		g.setFont(new Font("Arial", Font.BOLD, 14));
		
		// Set the color of the word(s)
		g.setColor(Color.BLACK);
		
		// Get the font metrics so the text can be centered
		FontMetrics metrics = g.getFontMetrics();
		int textWidth = metrics.stringWidth(_name);
		
		// Center the text on the label cell
		int x = _labelCell.getColumn() * width + width / 2 - textWidth / 2;
		int y = _labelCell.getRow() * height + height / 2 + metrics.getAscent() / 2;
		
		// Draw the string
		g.drawString(_name, x, y);
	}
}