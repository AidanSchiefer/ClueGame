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

	// Constructor that assigns the room name
	public Room(String name) {
		_name = name;
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

	// Setter method to assign the center cell
	public void setCenterCell(BoardCell centerCell) {
		_centerCell = centerCell;
	}

	// Setter method to assign the label cell
	public void setLabelCell(BoardCell labelCell) {
		_labelCell = labelCell;
	}
}