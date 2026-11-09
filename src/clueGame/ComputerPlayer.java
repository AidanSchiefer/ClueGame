package clueGame;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{
	// Constructor
	public ComputerPlayer(String name, String color, int row, int column) {
		super(name, color, row, column);
	}
	
	// Overridden implementation of updateHand
	@Override
	public void updateHand(Card card) {
		hand.add(card);
	}
	
	// Getter method for the name
	public String getName() {
		return _name;
	}
	
	// Getter method for the color
	public String getColor() {
		return _color;
	}
	
	// Getter method for the row
	public int getRow() {
		return _row;
	}
	
	// Getter method for the column
	public int getColumn() {
		return _column;
	}
	
	// Getter method for the players hand
	public ArrayList<Card> getHand(){
		return hand;
	}
	
	// Method to create a suggestion
	public Solution createSuggestion() {
		return null;
	}
	
	// Method to select a target
	public BoardCell selectTarget(Set<BoardCell> targets) {
		// Get the board instance
		Board board = Board.getInstance();
		
		// Create a list for tracking the necessary targets
		ArrayList<BoardCell> necessaryTargets = new ArrayList<BoardCell>();
		
		// Loop through the targets list
		for (BoardCell cell: targets) {
			// If the target is a room and is not in the seen list, add to the list
			if (cell.isRoom()) {
				Room cellRoom = board.getRoom(cell.getInitial());
				
				// Loop through the seen list
				for (Card seen: _seenCards) {
					// If the room is not in the seen list, add it to the list
					if (!cellRoom.getName().equals(seen.getCardName())) {
						necessaryTargets.add(cell);
					}
				}
			}
		}
		
		// If the size of necessaryTargets is greater than 1, return a random index
		if (necessaryTargets.size() > 1) {
			// Create a random variable
			Random rand = new Random();
			
			// Generate a random index
			int randIndex = rand.nextInt(necessaryTargets.size());
			
			// Return the random index
			return necessaryTargets.get(randIndex);
		}
		// Else if the size of necessary targets is 1, return the target
		else if (necessaryTargets.size() == 1) {
			return necessaryTargets.get(0);
		}
		
		// Neither condition was met, return null
		return null;
	}
}
