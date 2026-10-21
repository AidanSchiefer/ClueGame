package clueGame;

import java.util.Set;

import experiment.TestBoardCell;

public class Board {
	
	// Member variables
	private String _clueLayoutCSV;
	private String _clueSetupTXT;
	private int numRows;
	private int numColumns;
	// Set that contains the board
	private Set<BoardCell> _testBoard;
	// Set that contains target cells
	private Set<BoardCell> _targetCells;
	// Set that contains the visited cells
	private Set<BoardCell> _visited;
	// private Map<Character, Room> roomMap

    // variable and methods used for singleton pattern
    private static Board theInstance = new Board();
    
    // constructor is private to ensure only one can be created
    private Board() {
           super() ;
    }
    
    // this method returns the only Board
    public static Board getInstance() {
           return theInstance;
    }
    
    // Method to set the configuration files
    public void setConfigFiles(String layoutCsv, String setupTxt) {
    	_clueLayoutCSV = layoutCsv;
    	_clueSetupTXT = setupTxt;
    }
    
    /*
     * initialize the board (since we are using singleton pattern)
     */
    public void initialize()
    {
    }
    
	// Method to calculate legal targets for a move from startCell of length pathLength
	public void calcTargets(BoardCell startCell, int pathLength) {
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
	
	private void findAllTargets(BoardCell thisCell, int numSteps) {
		// Get the adjacency list from the startCell
		Set<BoardCell> adjList = thisCell.getAdjList();

		// Loop through each cell in the adjacency list
		for (BoardCell adjCell: adjList){
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
	public BoardCell getCell(int row, int col) {
		for (BoardCell cell : _testBoard) {
			if (cell.getRow() == row && cell.getColumn() == col) {
				return cell;
			}
		}
		return null;
	}

	// Gets the targets last created by calcTargets
	public Set<BoardCell> getTargets(){
		return _targetCells;
	}
}
