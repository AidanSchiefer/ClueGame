package clueGame;

import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	// Member variables
	private int _boardRow;
	private int _boardColumn;
	private char _roomInitial;
	private DoorDirection _doorDirection = DoorDirection.NONE;
	private boolean _roomLabel;
	private boolean _roomCenter;
	private char _secretPassage;
	private Set<BoardCell> _adjacencyList;
	private boolean _isRoom = false;
	private boolean _isOccupied = false;

	// Constructor
	public BoardCell(int row, int column) {
		_boardRow = row;
		_boardColumn = column;
		_adjacencyList = new HashSet<>();
	}

	// Setter method to add to the adjacency list
	public void addAdjacency(BoardCell cell) {
		_adjacencyList.add(cell);
	}

	// Getter method to get the adjacency list
	public Set<BoardCell> getAdjList() {
		return _adjacencyList;
	}

	// Setter method to set a room status
	public void setRoom(boolean roomStatus) {
		_isRoom = roomStatus;
	}

	// Getter method to determine if a cell represents a room
	public boolean isRoom() {
		return _isRoom;
	}

	// Setter method to set whether a cell is occupied
	public void setOccupied(boolean occupiedStatus) {
		_isOccupied = occupiedStatus;
	}

	// Getter method to determine if a cell is occupied
	public boolean getOccupied() {
		return _isOccupied;
	}

	// Getter method to get the row
	public int getRow() {
		return _boardRow;
	}

	// Getter method to get the column
	public int getColumn() {
		return _boardColumn;
	}

	// Getter method to get the room initial
	public char getInitial() {
		return _roomInitial;
	}

	// Setter method to assign the room initial
	public void setInitial(char initial) {
		_roomInitial = initial;
	}

	// Function to determine if the given cell is a door
	public boolean isDoorway() {
		return _doorDirection != DoorDirection.NONE;
	}

	// Getter method to get the door direction of a given cell
	public DoorDirection getDoorDirection() {
		return _doorDirection;
	}

	// Setter method to assign the door direction
	public void setDoorDirection(DoorDirection direction) {
		_doorDirection = direction;
	}

	// Function to determine if a cell is a label
	public boolean isLabel() {
		return _roomLabel;
	}

	// Setter method to assign whether a cell is a label
	public void setLabel(boolean label) {
		_roomLabel = label;
	}

	// Function to determine if a cell is the center cell or not
	public boolean isRoomCenter() {
		return _roomCenter;
	}

	// Setter method to assign whether a cell is a center cell
	public void setRoomCenter(boolean center) {
		_roomCenter = center;
	}

	// Getter method to get the symbol of the secret passage
	public char getSecretPassage() {
		return _secretPassage;
	}

	// Setter method to assign the secret passage destination
	public void setSecretPassage(char destination) {
		_secretPassage = destination;
	}
}