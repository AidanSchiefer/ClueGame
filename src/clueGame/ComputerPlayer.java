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
	
	public void updateSeen(Card card) {
		_seenCards.add(card);
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
		// Get the instance of board
		Board board = Board.getInstance();
		
		// Get the deck of cards from the board
		ArrayList<Card> cardDeck = board.getDeck();
		
		// Get the cell the player is in
		BoardCell playerCell = board.getCell(_row, _column);
		
		// Get the room name
		String roomName = board.getRoom(playerCell).getName();
		Card roomCard = null;
		
		// Find the corresponding room card
		for (Card card: cardDeck) {
			if (roomName.equals(card.getCardName())) {
				roomCard = card;
			}
		}
		
		// Extract the weapons from the deck - ignore weapon if seen
		ArrayList<Card> weaponCards = new ArrayList<Card>();
		
		for (Card card: cardDeck) {
			if (!(_seenCards.contains(card)) && card.getCardType() == CardType.WEAPON && !hand.contains(card)) {
				
				weaponCards.add(card);
			}
		}
		
		// Extract the players from the deck - ignore player if seen
		ArrayList<Card> playerCards = new ArrayList<Card>();
		
		for (Card card: cardDeck) {
			if (!(_seenCards.contains(card)) && card.getCardType() == CardType.PERSON && !hand.contains(card)) {
				playerCards.add(card);
			}
		}
		
		// Create a random object for player and weapons
		Random randWeapon = new Random();
		Random randPlayer = new Random();
		
		// Generate random indices
		int randWeaponIndex = randWeapon.nextInt(weaponCards.size());
		int randPlayerIndex = randPlayer.nextInt(playerCards.size());
		
		// Store the randomly selected cards names
		Card weaponCard = weaponCards.get(randWeaponIndex);
		Card playerCard = playerCards.get(randPlayerIndex);
		
		// Create the solution object
		Solution suggestion = new Solution(roomCard, playerCard, weaponCard);
		
		// Add room card, player card, and weapon card to the seen list
		_seenCards.add(roomCard);
		_seenCards.add(playerCard);
		_seenCards.add(weaponCard);
		
		// Return the suggestion
		return suggestion;
	}
	
	// Method to select a target
	public BoardCell selectTarget(Set<BoardCell> targets) {
		// Get the board instance
		Board board = Board.getInstance();
		
		// Create a random object
		Random rand = new Random();
		
		// If there are no targets, there is nothing to select, return null
		if (targets.isEmpty()) {
			return null;
		}
		
		// Create a list to track the rooms that have not been seen
		ArrayList<BoardCell> unseenRooms = new ArrayList<BoardCell>();
		
		// Loop through the targets list
		for (BoardCell cell: targets) {
			// Check to see if a cell is a room
			if (cell.isRoom()) {
				// Extract the room name
				String roomName = board.getRoom(cell.getInitial()).getName();
				
				// Create a boolean to track if a room is seen
				boolean roomSeen = false;
				
				// Loop through the seen list to see if the room has been seen
				for (Card seen: _seenCards) {
					if (roomName.equals(seen.getCardName())) {
						roomSeen = true;
					}
				}
				
				// Only add the room to the list if it has not been seen
				if (!roomSeen) {
					unseenRooms.add(cell);
				}
			}
		}
		
		// If there are unseen rooms, randomly choose one, then return it
		if (unseenRooms.size() > 0) {
			int randomIndex = rand.nextInt(unseenRooms.size());
			return unseenRooms.get(randomIndex);
		}
		
		// Otherwise, randomly choose from all targets
		ArrayList<BoardCell> allTargets = new ArrayList<BoardCell>(targets);
		int randomIndex = rand.nextInt(allTargets.size());
		return allTargets.get(randomIndex);
	}
}
