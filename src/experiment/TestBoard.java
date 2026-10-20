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
	// Set that contains the visited cells
	private Set<TestBoardCell> _visited;

	// Constructor
	public TestBoard() {
		_testBoard = new HashSet<>();
		_targetCells = new HashSet<>();
		_visited = new HashSet<>();

		// make 16 cells 
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				_testBoard.add(new TestBoardCell(row, col));
			}
		}

		// Build adjacency lists after all cells are created
		for (TestBoardCell cell : _testBoard) {
			int row = cell.getRow();
			int col = cell.getColumn();

			// Neighbor above
			if (row > 0) {
				cell.addAdjacency(getCell(row - 1, col));
			}

			// Neighbor below
			if (row < 3) {
				cell.addAdjacency(getCell(row + 1, col));
			}

			// Neighbor to the left
			if (col > 0) {
				cell.addAdjacency(getCell(row, col - 1));
			}

			// Neighbor to the right
			if (col < 3) {
				cell.addAdjacency(getCell(row, col + 1));
			}
		}
	}

	// Method to calculate legal targets for a move from startCell of length pathLength
	public void calcTargets(TestBoardCell startCell, int pathLength) {
		// Clear out the target and visited lists
		_targetCells.clear();
		_visited.clear();
		
		// Add the starting cell to the visited list so we can't re-enter it
		_visited.add(startCell);
		
		// Call findAllTargets with the starting cell
		findAllTargets(startCell, pathLength);
		
		// Once the recursion tree has finished executing, remove the starting cell from the visited list
		_visited.remove(startCell);
	}

	private void findAllTargets(TestBoardCell thisCell, int numSteps) {
		// Get the adjacency list from the startCell
		Set<TestBoardCell> adjList = thisCell.getAdjList();

		// Loop through each cell in the adjacency list
		for (TestBoardCell adjCell: adjList){
			// If already in visited list or the cell is occupied, skip the rest of the loop
			if (_visited.contains(adjCell) || adjCell.getOccupied()) {
				continue;
			}

			// Add adjCell to the visited list
			_visited.add(adjCell);

			// If numSteps == 1 or is a room. add adjCell to targets
			if (numSteps == 1 || adjCell.isRoom()) {
				_targetCells.add(adjCell);
			}
			// else, call findAllTargets with adjCell, numSteps - 1
			else {
				findAllTargets(adjCell, numSteps - 1);
			}

			// Remove adjCell from the visited list
			_visited.remove(adjCell);
		}
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
	public Set<TestBoardCell> getTargets(){
		return _targetCells;
	}
}
