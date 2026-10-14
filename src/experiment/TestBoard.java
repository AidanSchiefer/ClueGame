// Authors: Aidan Schiefer, Chris Hussain

package experiment;
import java.util.HashSet;
import java.util.Set;

public class TestBoard {
	// Member variables
	// Set that contains the board
	private Set<TestBoardCell> _testBoard;
	// Set that contains target cells
	private Set<TestBoardCell> _targetCells;
	
	// Constructor
	public TestBoard() {
		
	}
	
	// Method to calculate legal targets for a move from startCell of length pathLength
	void calcTargets(TestBoardCell startCell, int pathLength) {
		
	}
	
	// Getter method that returns the cell at the board at row, col
	TestBoardCell getCell(int row, int col) {
		return null;
	}
	
	// Gets the targets last created by calcTargets
	Set<TestBoardCell> getTargets(){
		return null;
	}
}
