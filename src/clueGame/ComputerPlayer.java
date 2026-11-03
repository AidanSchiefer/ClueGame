package clueGame;

public class ComputerPlayer extends Player{
	// Constructor
	public ComputerPlayer(String name, String color, int row, int column) {
		super(name, color, row, column);
	}
	
	// Overridden implementation of updateHand
	@Override
	public void updateHand(Card card) {
		
	}
}
