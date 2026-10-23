package clueGame;

import java.util.HashSet;
import java.util.Set;

import experiment.TestBoardCell;
import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	// Member variables
		private int _boardRow;
		private int _boardColumn;
		private char _roomInitial;
		private DoorDirection _doorDirection;
		private boolean _roomLabel;
		private boolean _roomCenter;
		private char secretPassage;
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
		public Set<BoardCell> getAdjList(){
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
