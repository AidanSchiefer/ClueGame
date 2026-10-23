package clueGame;

import java.util.Map;
import java.util.HashSet;
import java.util.Set;

import experiment.TestBoardCell;

public class Board {

	// Member variables
	private String _clueLayoutCSV;
	private String _clueSetupTXT;
	private int _numRows;
	private int _numColumns;
	// Set that contains the board - Not using this
	private Set<BoardCell> _testBoard;
	// Set that contains target cells
	private Set<BoardCell> _targetCells;
	// Set that contains the visited cells
	private Set<BoardCell> _visited;
	// Grid for the board
	private BoardCell[][] _board;
	private Map<Character, Room> roomMap;

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

	// Method to load the setup config
	public void loadSetupConfig() {

	}

	// Method to set up the layout config
	public void loadLayoutConfig() {

	}

	/*
	 * initialize the board (since we are using singleton pattern)
	 */
	public void initialize(){

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

	// TODO: Change this implementation to use the 2D array instead of the set
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

	// Method to return the adjacency list for a given cell
	public Set<BoardCell> getAdjList(int row, int col) {
		return _board[row][col].getAdjList();
	}

	// Getter method to get number of rows
	public int getNumRows() {
		return _numRows;
	}

	// Getter method to get number of columns
	public int getNumColumns() {
		return _numColumns;
	}

	// Getter method that returns the room object if it corresponds to the parameter char
	public Room getRoom(char symbol) {
		// Return the desired room based off of symbol
		return roomMap.get(symbol);
	}

	// Getter method that returns the room object if it corresponds to the parameter char
	public Room getRoom(BoardCell cell) {
		// Return the desired room based off of symbol
		return roomMap.get(cell);
	}
}
