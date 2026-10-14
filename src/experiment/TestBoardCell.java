// Authors: Aidan Schiefer, Chris Hussain

package experiment;
import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {
	// Member variables
	private int _boardRow;
	private int _boardColumn;
	private Set<TestBoardCell> _adjacencyList;
	
	// Constructor
	public TestBoardCell(int row, int column) {
		_boardRow = row;
		_boardColumn = column;
	}
	
	// Setter method to add to the adjacency list
	void addAdjacency(TestBoardCell cell) {
		
	}
	
	// Getter method to get the adjacency list
	Set<TestBoardCell> getAdjList(){
		return _adjacencyList;
	}
	
	// Setter method to set a room status
	void setRoom(boolean isRoom) {
		
	}
	
	// Getter method to determine if a room is occupied
	boolean getOccupied() {
		return true;
	}
}
