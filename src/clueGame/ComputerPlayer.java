package clueGame;

import java.util.ArrayList;

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
}
