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

	// Setter method to add to the adjacency list
	public void addAdjacency(TestBoardCell cell) {
		_adjacencyList.add(cell);
	}

	// Getter method to get the adjacency list
	public Set<TestBoardCell> getAdjList(){
		return _adjacencyList;
	}

	// Setter method to set a room status
	public void setRoom(boolean roomStatus) {
		_isRoom = roomStatus;
	}

	public boolean isRoom() {
		return _isRoom;
	}

	public void setOccupied(boolean occupiedStatus) {
		_isOccupied = occupiedStatus;
	}

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
