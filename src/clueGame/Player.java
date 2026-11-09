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
	
	// Getter method for the name
	public abstract String getName();
	
	// Getter method for the row
	public abstract int getRow();
	
	// Getter method for the column
	public abstract int getColumn();
	
	// Getter method for the players hand
	public abstract ArrayList<Card> getHand();
	
	// Getter method for the color
	public abstract String getColor();
	
	// Method to dispute a suggestion
	public Card disproveSuggestion(Card player, Card weapon, Card room) {
		// Create a temporary list to hold possible suggestions
		ArrayList<Card> possibleSuggestions = new ArrayList<Card>();
		
		// Loop through the players hand
		for (Card card: hand) {
			if (card == player || card == weapon || card == room) {
				possibleSuggestions.add(card);
			}
		}
		
		// If the length is greater than 1, return a random index
		if (possibleSuggestions.size() > 1) {
			// Create a random object
			Random rand = new Random();
			
			// Generate a random index
			int randIndex = rand.nextInt(possibleSuggestions.size());
			
			// Return the random index
			return possibleSuggestions.get(randIndex);
		}
		// Else if the length is 1, return the index
		else if (possibleSuggestions.size() == 1) {
			return possibleSuggestions.get(0);
		}
		
		// Neither condition was met, return null
		return null;
	}
}
