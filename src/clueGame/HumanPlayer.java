package clueGame;

public class HumanPlayer extends Player{
	// Constructor
	public HumanPlayer(String name, String color, int row, int col) {
		super(name, color, row, col);
	}
	
	// Implementation of updateHand
	@Override
	public void updateHand(Card card) {
		hand.add(card);
	}
}
