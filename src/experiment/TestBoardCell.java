// Authors: Aidan Schiefer, Chris Hussain

package experiment;
import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {
	// Member variables
	private int _boardRow;
	private int _boardColumn;
	private Set<TestBoardCell> _adjacencyList;
	private boolean _isRoom = false;
	private boolean _isOccupied = false;

	// Constructor
	public TestBoardCell(int row, int column) {
		_boardRow = row;
		_boardColumn = column;
		_adjacencyList = new HashSet<>();
	}

	// TODO
	// Setter method to add to the adjacency list
	//to be implemented later
	public void addAdjacency(TestBoardCell cell) {

	}

	// Getter method to get the adjacency list
	public Set<TestBoardCell> getAdjList(){
		return _adjacencyList;
	}

	// TODO
	// Setter method to set a room status
	public void setRoom(boolean roomStatus) {
		_isRoom = roomStatus;
	}

	// TODO
	public boolean isRoom() {
		return _isRoom;
	}

	// TODO
	public void setOccupied(boolean occupiedStatus) {
		_isOccupied = occupiedStatus;
	}

	// TODO
	// Getter method to determine if a room is occupied
	public boolean getOccupied() {
		return _isOccupied;
	}

	public int getRow() {
		return _boardRow;
	}

	public int getColumn() {
		return _boardColumn;
	}
}
