package clueGame;

import java.util.Map;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;


public class Board {

	// Member variables
	private String _clueLayoutCSV;
	private String _clueSetupTXT;
	private int _numRows;
	private int _numColumns;
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
	public void loadSetupConfig() throws FileNotFoundException, BadConfigFormatException {

		// Reset the room map each time the setup file is loaded
		roomMap = new HashMap<>();

		try (Scanner scanner = new Scanner(new File("data", _clueSetupTXT))) {

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine().trim();

				// Skip comments and empty lines
				if (line.isEmpty() || line.startsWith("//")) {
					continue;
				}

				// Separate the entry into its type, name, and initial
				String[] parts = line.split(",", -1);

				if (parts.length != 3) {
					throw new BadConfigFormatException("Setup entries must have three values: " + line);
				}

				String type = parts[0].trim();
				String name = parts[1].trim();
				String symbol = parts[2].trim();

				// Only Room and Space are valid setup types
				if (!type.equals("Room") && !type.equals("Space")) {
					throw new BadConfigFormatException( "Unknown setup type: " + type);
				}

				// Each entry needs a name and a single-character initial
				if (name.isEmpty() || symbol.length() != 1) {
					throw new BadConfigFormatException("Invalid room name or initial: " + line);
				}

				char initial = symbol.charAt(0);

				// Different rooms cannot use the same initial
				if (roomMap.containsKey(initial)) {
					throw new BadConfigFormatException("Duplicate room initial: " + initial);
				}

				// Store the room using its initial as the key
				roomMap.put(initial, new Room(name));
			}
		}

		if (roomMap.isEmpty()) {
			throw new BadConfigFormatException("The setup file contains no rooms or spaces.");
		}
	}


	// Method to set up the layout config
	public void loadLayoutConfig()
			throws FileNotFoundException, BadConfigFormatException {

		// The setup file defines the initials used in the layout
		if (roomMap == null || roomMap.isEmpty()) {
			throw new BadConfigFormatException("Load the setup file before the layout file.");
		}

		// Store the rows so we can determine the grid dimensions
		List<String[]> rows = new ArrayList<>();

		try (Scanner scanner =
				new Scanner(new File("data", _clueLayoutCSV))) {

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine().trim();

				if (line.isEmpty()) {
					throw new BadConfigFormatException("The layout contains an empty row.");
				}

				String[] cells = line.split(",", -1);

				// Every row must contain the same number of columns
				if (!rows.isEmpty() && cells.length != rows.get(0).length) {
					throw new BadConfigFormatException("All layout rows must have the same number of columns.");
				}

				rows.add(cells);
			}
		}

		if (rows.isEmpty()) {
			throw new BadConfigFormatException("The layout file is empty.");
		}

		// Create the grid using the dimensions read from the file
		_numRows = rows.size();
		_numColumns = rows.get(0).length;
		_board = new BoardCell[_numRows][_numColumns];

		// Clear room references from any previously loaded layout
		for (Room room : roomMap.values()) {
			room.setCenterCell(null);
			room.setLabelCell(null);
		}

		// Assign each cell in the board its corresponding value
		for (int row = 0; row < _numRows; row++) {
			for (int col = 0; col < _numColumns; col++) {
				String entry = rows.get(row)[col].trim();

				// Entries contain an initial and an optional modifier
				if (entry.length() < 1 || entry.length() > 2) {
					throw new BadConfigFormatException("Invalid layout entry at " + row + ", " + col + ": " + entry);
				}

				char initial = entry.charAt(0);
				Room room = roomMap.get(initial);

				// Every initial must appear in the setup file
				if (room == null) {
					throw new BadConfigFormatException("Unknown room initial: " + initial);
				}

				BoardCell cell = new BoardCell(row, col);
				cell.setInitial(initial);
				cell.setRoom(initial != 'W' && initial != 'X');
				_board[row][col] = cell;

				// A single-character entry has no additional properties
				if (entry.length() == 1) {
					continue;
				}

				char modifier = entry.charAt(1);

				// Interpret doorway, label, center, and passage markers
				switch (modifier) {
				case '^':
					cell.setDoorDirection(DoorDirection.UP);
					break;

				case 'v':
					cell.setDoorDirection(DoorDirection.DOWN);
					break;

				case '<':
					cell.setDoorDirection(DoorDirection.LEFT);
					break;

				case '>':
					cell.setDoorDirection(DoorDirection.RIGHT);
					break;

				case '#':
					// Each room can have only one label cell
					if (room.getLabelCell() != null) {
						throw new BadConfigFormatException("Multiple label cells for " + room.getName());
					}

					cell.setLabel(true);
					room.setLabelCell(cell);
					break;

				case '*':
					// Each room can have only one center cell
					if (room.getCenterCell() != null) {
						throw new BadConfigFormatException("Multiple center cells for " + room.getName());
					}

					cell.setRoomCenter(true);
					room.setCenterCell(cell);
					break;

				default:
					// Other valid room initials indicate secret passages
					if (!roomMap.containsKey(modifier) || modifier == 'W' || modifier == 'X') {
						throw new BadConfigFormatException("Invalid secret passage destination: " + modifier);
					}

					cell.setSecretPassage(modifier);
					break;
				}

				// Doorway markers belong on walkway cells
				if (cell.isDoorway() && initial != 'W') {
					throw new BadConfigFormatException("Doorways must be marked on walkway cells.");
				}

				// Labels, centers, and passages belong inside rooms
				if ((cell.isLabel() || cell.isRoomCenter() || cell.getSecretPassage() != '\0') && (initial == 'W' || initial == 'X')) {
					throw new BadConfigFormatException("Room markers must be inside rooms.");
				}
			}
		}
	}

	/*
	 * initialize the board (since we are using singleton pattern)
	 */
	public void initialize() {
		// Reset movement data for the newly loaded board
		_targetCells = new HashSet<>();
		_visited = new HashSet<>();

		// Clear the previous grid and dimensions
		_board = null;
		_numRows = 0;
		_numColumns = 0;

		try {
			// Call loadSetupConfig and loadLayoutConfig
			loadSetupConfig();
			loadLayoutConfig();
			calculateAdjacencies();
		}
		catch (FileNotFoundException | BadConfigFormatException e) {
			// Display a useful message if initialization fails
			System.err.println(
					"Unable to initialize board: " + e.getMessage());
		}
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
			// Skip visited cells and occupied walkways; rooms can hold multiple players.
			if (_visited.contains(adjCell) || (adjCell.getOccupied() && !adjCell.isRoom())) {
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
		return _board[row][col];
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
		return roomMap.get(cell.getInitial());
	}
	
	// Getter method to get the number of rooms and spaces in the setup
	public int getNumRooms() {
		return roomMap.size();
	}

	// Build movement connections after every cell and room center is loaded.
	private void calculateAdjacencies() {
		for (int row = 0; row < _numRows; row++) {
			for (int col = 0; col < _numColumns; col++) {
				BoardCell cell = getCell(row, col);

				if (cell.getInitial() == 'W') {
					addWalkwayNeighbor(cell, row - 1, col);
					addWalkwayNeighbor(cell, row + 1, col);
					addWalkwayNeighbor(cell, row, col - 1);
					addWalkwayNeighbor(cell, row, col + 1);

					if (cell.isDoorway()) {
						connectDoorToRoom(cell);
					}
				}

				if (cell.getSecretPassage() != '\0') {
					BoardCell source = getRoom(cell).getCenterCell();
					BoardCell destination =
						getRoom(cell.getSecretPassage()).getCenterCell();
					source.addAdjacency(destination);
				}
			}
		}
	}

	// Add an orthogonal neighbor only when it is an in-bounds walkway.
	private void addWalkwayNeighbor(BoardCell cell, int row, int col) {
		if (row < 0 || row >= _numRows || col < 0 || col >= _numColumns) {
			return;
		}

		BoardCell neighbor = getCell(row, col);
		if (neighbor.getInitial() == 'W') {
			cell.addAdjacency(neighbor);
		}
	}

	// The arrow identifies the room; movement uses its center, not its wall cell.
	private void connectDoorToRoom(BoardCell door) {
		int row = door.getRow();
		int col = door.getColumn();

		switch (door.getDoorDirection()) {
		case UP:
			row--;
			break;
		case DOWN:
			row++;
			break;
		case LEFT:
			col--;
			break;
		case RIGHT:
			col++;
			break;
		default:
			return;
		}

		BoardCell center = getRoom(getCell(row, col)).getCenterCell();
		door.addAdjacency(center);
		center.addAdjacency(door);
	}
}

