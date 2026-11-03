package clueGame;

import java.util.*;

public abstract class Player {
	// Member variables
	// Members for player name and color
	protected String _name, _color;
	// Variables for row and column
	protected int _row, _column;
	// Member list for hand of cards
	protected ArrayList<Card> hand;
	
	// Constructor
	public Player(String name, String color, int row, int col) {
		hand = new ArrayList<Card>();
		_name = name;
		_color = color;
		_row = row;
		_column = col;
	}
	
	// Abstract method for updateHand
	public abstract void updateHand(Card card);
}
