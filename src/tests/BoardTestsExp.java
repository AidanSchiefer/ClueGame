// Authors: Aidan Schiefer, Chris Hussain

package tests;


//used static so we don't have to type Assertions.assertEquals every time 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import experiment.TestBoard;
import experiment.TestBoardCell;

public class BoardTestsExp {
	// Member variables
	TestBoard board;

	// Constructor and setup before each test
	@BeforeEach
	public void setUp() {
		board = new TestBoard();
	}

	// Adjacency tests:

	// Top left corner has two neighbors
	@Test
	public void testAdjacencyTopLeft() {
		Set<TestBoardCell> adjacent = board.getCell(0, 0).getAdjList();

		assertEquals(2, adjacent.size());
		assertTrue(adjacent.contains(board.getCell(0, 1)));
		assertTrue(adjacent.contains(board.getCell(1, 0)));
	}

	// Bottom right corner has two neighbors
	@Test
	public void testAdjacencyBottomRight() {
		Set<TestBoardCell> adjacent = board.getCell(3, 3).getAdjList();

		assertEquals(2, adjacent.size());
		assertTrue(adjacent.contains(board.getCell(2, 3)));
		assertTrue(adjacent.contains(board.getCell(3, 2)));
	}

	// Right edge has three neighbors when not at a corner
	@Test
	public void testAdjacencyRightEdge() {
		Set<TestBoardCell> adjacent = board.getCell(1, 3).getAdjList();

		assertEquals(3, adjacent.size());
		assertTrue(adjacent.contains(board.getCell(0, 3)));
		assertTrue(adjacent.contains(board.getCell(2, 3)));
		assertTrue(adjacent.contains(board.getCell(1, 2)));
	}

	//tests adjacency at left corner
	@Test
	public void testAdjacencyLeftCorner() {
		Set<TestBoardCell> adjacent = board.getCell(3, 0).getAdjList();

		assertEquals(2, adjacent.size());
		assertTrue(adjacent.contains(board.getCell(2, 0)));
		assertTrue(adjacent.contains(board.getCell(3, 1)));
	}

	// Interior cells have four bordering neighbors
	@Test
	public void testAdjacencyMiddle() {
		Set<TestBoardCell> adjacent = board.getCell(2, 2).getAdjList();

		assertEquals(4, adjacent.size());
		assertTrue(adjacent.contains(board.getCell(1, 2)));
		assertTrue(adjacent.contains(board.getCell(3, 2)));
		assertTrue(adjacent.contains(board.getCell(2, 1)));
		assertTrue(adjacent.contains(board.getCell(2, 3)));
	}

	// Target tests:

	// Tests targets after rolling a 1 - 3
	@Test
	public void testRoll1_3() {
		// Set a basic starting position at (0,0)
		TestBoardCell cell = board.getCell(0, 0);

		//----- Set roll to 1 then test targets -----
		// Calculate the targets for a roll of 1
		board.calcTargets(cell, 1);

		// Get each target from the target list from board
		Set<TestBoardCell> targets1 = board.getTargets();

		// Check if the total number of targets is 2
		assertEquals(2, targets1.size());

		// Check that the target set has the expected target cells
		assertTrue(targets1.contains(board.getCell(0, 1)));
		assertTrue(targets1.contains(board.getCell(1, 0)));

		//----- Set roll to 2 then test targets -----
		// Calculate the targets for a roll of 2
		board.calcTargets(cell, 2);

		// Get each target from the target list from board
		Set<TestBoardCell> targets2 = board.getTargets();

		// Check if the total number of targets is 3
		assertEquals(3, targets2.size());

		// Check that the target set has the expected target cells
		assertTrue(targets2.contains(board.getCell(0, 2)));
		assertTrue(targets2.contains(board.getCell(2, 0)));
		assertTrue(targets2.contains(board.getCell(1, 1)));

		//----- Set roll to 3 then test targets -----
		// Calculate the targets for a roll of 3
		board.calcTargets(cell, 3);

		// Get each target from the target list from board
		Set<TestBoardCell> targets3 = board.getTargets();

		// Check if the total number of targets is 4
		assertEquals(4, targets3.size());

		// Check that the target set has the expected target cells
		assertTrue(targets3.contains(board.getCell(0, 3)));
		assertTrue(targets3.contains(board.getCell(3, 0)));
		assertTrue(targets3.contains(board.getCell(2, 1)));
		assertTrue(targets3.contains(board.getCell(1, 2)));
	}

	// Tests targets after rolling a 4-6
	@Test
	public void testRoll4_6() {
		// Set a basic starting position at (0,0)
		TestBoardCell cell = board.getCell(0, 0);

		//----- Set roll to 4 then test targets -----
		// Calculate the targets for a roll of 4
		board.calcTargets(cell, 4);

		// Get each target from the target list from board
		Set<TestBoardCell> targets4 = board.getTargets();

		// Check if the total number of targets is 5
		assertEquals(5, targets4.size());

		// Check that the target set has the expected target cells
		assertTrue(targets4.contains(board.getCell(1, 3)));
		assertTrue(targets4.contains(board.getCell(1, 1)));
		assertTrue(targets4.contains(board.getCell(2, 0)));
		assertTrue(targets4.contains(board.getCell(2, 2)));
		assertTrue(targets4.contains(board.getCell(3, 1)));

		//----- Set roll to 5 then test targets -----
		// Calculate the targets for a roll of 5
		board.calcTargets(cell, 5);

		// Get each target from the target list from board
		Set<TestBoardCell> targets5 = board.getTargets();

		// Check if the total number of targets is 8
		assertEquals(8, targets5.size());

		// Check that the target set has the expected target cells
		assertTrue(targets5.contains(board.getCell(0, 1)));
		assertTrue(targets5.contains(board.getCell(0, 3)));
		assertTrue(targets5.contains(board.getCell(1, 0)));
		assertTrue(targets5.contains(board.getCell(1, 2)));
		assertTrue(targets5.contains(board.getCell(2, 1)));
		assertTrue(targets5.contains(board.getCell(2, 3)));
		assertTrue(targets5.contains(board.getCell(3, 0)));
		assertTrue(targets5.contains(board.getCell(3, 2)));

		//----- Set roll to 6 then test targets -----
		// Calculate the targets for a roll of 6
		board.calcTargets(cell, 6);

		// Get each target from the target list from board
		Set<TestBoardCell> targets6 = board.getTargets();

		// Check if the total number of targets is 7
		assertEquals(4, targets6.size());

		// Check that the target set has the expected target cells
		assertTrue(targets6.contains(board.getCell(0, 2)));
		assertTrue(targets6.contains(board.getCell(1, 1)));
		assertTrue(targets6.contains(board.getCell(1, 3)));
		assertTrue(targets6.contains(board.getCell(2, 0)));
		assertTrue(targets6.contains(board.getCell(2, 2)));
		assertTrue(targets6.contains(board.getCell(3, 1)));
		assertTrue(targets6.contains(board.getCell(3, 3)));
	}
	
	// Test to determine targets if a cell is a room
	@Test
	public void testRoomTargets() {
		// Create the starting cell
		TestBoardCell cell = board.getCell(0, 0);
		
		// Set one target to be a room
		board.getCell(0, 1).setRoom(true);
		
		// Roll a one
		board.calcTargets(cell, 1);
		
		// Get the target list
		Set<TestBoardCell> targets = board.getTargets();
		
		// Test to see if the target set is the right size
		assertEquals(2, targets.size());
		
		// Test to see if the targets match
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 0)));
	}
	
	// Test to determine if an occupied square is unavailable
	@Test
	public void testOccupationTargets() {
		// Create the starting cell
		TestBoardCell cell = board.getCell(0, 0);
		
		// Set one target to be occupied
		board.getCell(1, 0).setOccupied(true);
		
		// Roll a one
		board.calcTargets(cell, 1);
		
		// Get the target list
		Set<TestBoardCell> targets = board.getTargets();
		
		// Test to see if the target set is the right size
		assertEquals(1, targets.size());
		
		// Test to see if the targets match
		assertTrue(targets.contains(board.getCell(0, 1)));
	}

}
