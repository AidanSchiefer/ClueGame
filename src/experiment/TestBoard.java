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
		_testBoard = new HashSet<>();
		_targetCells = new HashSet<>();

		// make 16 cells 
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				_testBoard.add(new TestBoardCell(row, col));
			}
		}
	}

	// TODO
	// Method to calculate legal targets for a move from startCell of length pathLength
	//to be implemented later
	public void calcTargets(TestBoardCell startCell, int pathLength) {

	}

	// Getter method that returns the cell at the board at row, col
	public TestBoardCell getCell(int row, int col) {
		for (TestBoardCell cell : _testBoard) {
			if (cell.getRow() == row && cell.getColumn() == col) {
				return cell;
			}
		}
		return null;
	}

	// Gets the targets last created by calcTargets
	// we aren't going to use this right now
	public Set<TestBoardCell> getTargets(){
		return _targetCells;
	}
}
