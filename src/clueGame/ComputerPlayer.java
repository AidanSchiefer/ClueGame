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
			if (!(_seenCards.contains(card)) && card.getCardName().equals("Weapon")) {
				weaponCards.add(card);
			}
		}
		
		// Extract the players from the deck - ignore player if seen
		ArrayList<Card> playerCards = new ArrayList<Card>();
		
		for (Card card: cardDeck) {
			if (!(_seenCards.contains(card)) && card.getCardName().equals("Player")) {
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
