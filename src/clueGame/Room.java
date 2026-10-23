package clueGame;

public class Room {
	// Member variables
	private String _name;
	private BoardCell _centerCell;
	private BoardCell _labelCell;
	
	// Default constructor
	public Room() {
		super();
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
}
