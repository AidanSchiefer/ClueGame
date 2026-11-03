package clueGame;

public class ComputerPlayer extends Player{
	// Constructor
	public ComputerPlayer(String name, String color, int row, int column) {
		super(name, color, row, column);
	}
	
	// Overridden implementation of updateHand
	@Override
	public void updateHand(Card card) {
		// Check if hand is empty. If empty, add the card to the hand
		if (hand.isEmpty()) {
			hand.add(card);
			return;
		}
		
		// The card is not in the hand, check to make sure it already isn't in the hand
		for (Card current: hand) {
			// If the card is already in the hand, move on to the next card
			if (current.equals(card)) {
				continue;
			}
			// Card not in hand, add to the hand
			hand.add(card);
		}
	}
}
