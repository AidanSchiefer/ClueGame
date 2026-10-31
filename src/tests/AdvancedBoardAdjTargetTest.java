package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class AdvancedBoardAdjTargetTest {
	// Create a private member for the test board
	private static Board board;

	// Before all setup method
	@BeforeAll
	public static void setUp() {
		// Create the board instance
		board = Board.getInstance();

		// Load the configuration files
		board.setConfigFiles("ClueBoardLayout.csv", "ClueSetup.txt");

		// Initialize the board
		board.initialize();
	}

	// Create test of adjacency at a door. Ensure adj includes available walkway and room center
	@Test
	public void testDoorAdj() {
		// Get the adj list for one of the doorways
		Set<BoardCell> testAdjList = board.getAdjList(6, 1);
		// Test it has the correct amount of adjacencies
		assertEquals(3, testAdjList.size());
		// Check that the list contains the correct cells
		assertTrue(testAdjList.contains(board.getCell(6, 0)));
		assertTrue(testAdjList.contains(board.getCell(6, 2)));
		assertTrue(testAdjList.contains(board.getCell(3, 2)));

		// Get the adj list for another doorway
		testAdjList = board.getAdjList(13, 8);
		// Test it has the correct amount of adjacencies
		assertEquals(4, testAdjList.size());
		// Check that the list contains the correct cells
		assertTrue(testAdjList.contains(board.getCell(13, 7)));
		assertTrue(testAdjList.contains(board.getCell(13, 9)));
		assertTrue(testAdjList.contains(board.getCell(12, 8)));
		assertTrue(testAdjList.contains(board.getCell(17, 9)));
	}

	// Create test for adjacency at room. Its only allowed adjacencies are
	// doorways and other room centers IF there is a secret passage
	@Test
	public void testRoomAdj() {
		// Check the adjacency for the Kitchen
		Set<BoardCell> testAdjList = board.getAdjList(3, 2);
		// Test that it has the correct amount of adjacencies
		assertEquals(1, testAdjList.size());
		// Check that the list contains the correct cells
		assertTrue(testAdjList.contains(board.getCell(6, 1)));

		// Check the adjacencies for factory (one door, secret passage)
		testAdjList = board.getAdjList(2, 17);
		// Test that it has the correct amount of adjacencies
		assertEquals(2, testAdjList.size());
		// Check that the list contains the correct cells
		assertTrue(testAdjList.contains(board.getCell(5, 15)));
		assertTrue(testAdjList.contains(board.getCell(9, 2)));

		// Check the adjacencies for Reindeer Pen (three doors)
		testAdjList = board.getAdjList(17, 9);
		// Check that the list contains the correct amount of adjacencies
		assertEquals(3, testAdjList.size());
		// Check that the list contains the correct cells
		assertTrue(testAdjList.contains(board.getCell(16, 7)));
		assertTrue(testAdjList.contains(board.getCell(13, 8)));
		assertTrue(testAdjList.contains(board.getCell(16, 12)));
	}

	// Create a test for adjacency at walkway
	@Test
	public void testWalkAdj() {
		// Get a walkway that has a door adjacent to it
		Set<BoardCell> testAdjList = board.getAdjList(4, 10);
		// Check that the list contains the correct amount of cells
		assertEquals(3, testAdjList.size());
		// Check that the list contains the correct cells
		assertTrue(testAdjList.contains(board.getCell(4, 9)));
		assertTrue(testAdjList.contains(board.getCell(4, 11)));
		assertTrue(testAdjList.contains(board.getCell(5, 10)));
		// Assert that the room cell above it is not in the list
		assertFalse(testAdjList.contains(board.getCell(3, 10)));
	}

	// Create test for target at room Santa's Bedroom
	@Test
	public void testSantasBedTarget() {
		// Test targets, roll 1
		board.calcTargets(board.getCell(18, 17), 1);
		// Get the targets list
		Set<BoardCell> targets = board.getTargets();
		// Check that the list contains the correct amount of cells
		assertEquals(2, targets.size());
		// Check that the list contains the correct cells
		assertTrue(targets.contains(board.getCell(19, 13)));
		assertTrue(targets.contains(board.getCell(13, 16)));

		// Test targets, roll 2
		board.calcTargets(board.getCell(18, 17), 2);
		// Get the targets list
		targets = board.getTargets();
		// Check that the list contains the correct amount of cells
		assertEquals(6, targets.size());
		// Check that the list contains some of the correct cells
		assertTrue(targets.contains(board.getCell(13, 15)));
		assertTrue(targets.contains(board.getCell(13, 17)));
		assertTrue(targets.contains(board.getCell(18, 13)));
		assertTrue(targets.contains(board.getCell(20, 13)));

		// Test targets, roll 3
		board.calcTargets(board.getCell(18, 17), 3);
		// Get the targets list
		targets = board.getTargets();
		// Check that the list contains the correct amount of cells
		assertEquals(8, targets.size());
		// Check that the list contains some of the correct cells
		assertTrue(targets.contains(board.getCell(11, 16)));
		assertTrue(targets.contains(board.getCell(12, 17)));
		assertTrue(targets.contains(board.getCell(13, 18)));
		assertTrue(targets.contains(board.getCell(18, 12)));
	}

	// Create test for target at room Living Room.
	// Regardless of roll, can take secret passage
	@Test
	public void testLivingRoomTarget() {
		// Test targets, roll 1
		board.calcTargets(board.getCell(18, 2), 1);
		// Get the targets list
		Set<BoardCell> targets = board.getTargets();
		// Check that the list contains the correct amount of cells
		assertEquals(2, targets.size());
		// Check that the list contains the correct cells
		assertTrue(targets.contains(board.getCell(17, 5)));
		assertTrue(targets.contains(board.getCell(17, 9))); // Secret Passage to Reindeer Pen

		// Test targets, roll 2
		board.calcTargets(board.getCell(18, 2), 2);
		// Get the targets list
		targets = board.getTargets();
		// Check that the list contains the correct amount of cells
		assertEquals(3, targets.size());
		// Check that the list contains the correct cells
		assertTrue(targets.contains(board.getCell(17, 9))); // Secret Passage to Reindeer Pen
		assertTrue(targets.contains(board.getCell(17, 6)));
		assertTrue(targets.contains(board.getCell(16, 5)));

		// Test targets, roll 3
		board.calcTargets(board.getCell(18, 2), 3);
		// Get the targets list
		targets = board.getTargets();
		// Check that the list contains the correct amount of cells
		assertEquals(5, targets.size());
		// Check that the list contains some of the correct cells
		assertTrue(targets.contains(board.getCell(17, 9))); // Secret Passage to Reindeer Pen
		assertTrue(targets.contains(board.getCell(18, 6)));
		assertTrue(targets.contains(board.getCell(15, 5)));
		assertTrue(targets.contains(board.getCell(17, 7)));
	}

	// Create test for target at doorway, can always enter the room
	@Test
	public void testDoorwayTarget() {
		// Test targets, roll 1
		board.calcTargets(board.getCell(1, 5), 1);
		// Get the targets list
		Set<BoardCell> targets = board.getTargets();
		// Check that the list contains the correct amount of cells
		assertEquals(3, targets.size());
		// Check that the list contains the correct cells
		assertTrue(targets.contains(board.getCell(3, 7))); // Room center
		assertTrue(targets.contains(board.getCell(0, 5)));
		assertTrue(targets.contains(board.getCell(2, 5)));

		// Test targets, roll 2
		board.calcTargets(board.getCell(1, 5), 2);
		// Get the targets list
		targets = board.getTargets();
		// Check that the list contains the correct amount of cells
		assertEquals(2, targets.size());
		// Check that the list contains the correct cells
		assertTrue(targets.contains(board.getCell(3, 7))); // Room center
		assertTrue(targets.contains(board.getCell(3, 5)));

		// Test targets, roll 3
		board.calcTargets(board.getCell(1, 5), 3);
		// Get the targets list
		targets = board.getTargets();
		// Check that the list contains the correct amount of cells
		assertEquals(2, targets.size());
		// Check that the list contains the correct cells
		assertTrue(targets.contains(board.getCell(3, 7))); // Room center
		assertTrue(targets.contains(board.getCell(4, 5)));
	}

	// Create test target in walkway
	@Test
	public void testWalkwayTarget() {
		// Test a roll of 1
		board.calcTargets(board.getCell(6, 10), 1);
		// Get the target list
		Set<BoardCell> targets = board.getTargets();
		// Test that the correct amount of targets exist in the list
		assertEquals(3, targets.size());
		// Check that the correct cells exist in the target list
		assertTrue(targets.contains(board.getCell(6, 9)));
		assertTrue(targets.contains(board.getCell(6, 11)));
		assertTrue(targets.contains(board.getCell(5, 10)));

		// Test a roll of 2
		board.calcTargets(board.getCell(6, 10), 2);
		// Get the target list
		targets = board.getTargets();
		// Check that the correct amount of targets exist in the list
		assertEquals(7, targets.size());
		// Test that some of the correct cells exist in the target list
		assertTrue(targets.contains(board.getCell(7, 9)));
		assertTrue(targets.contains(board.getCell(4, 10)));
		assertTrue(targets.contains(board.getCell(6, 12)));

		// Test a roll of 5
		board.calcTargets(board.getCell(6, 10), 5);
		// Get the target list
		targets = board.getTargets();
		// Test that the correct amount of targets exist in the list
		assertEquals(23, targets.size());
		// Test that some of the correct cells exist in the list
		assertTrue(targets.contains(board.getCell(6, 15)));
		assertTrue(targets.contains(board.getCell(5, 14)));
		assertTrue(targets.contains(board.getCell(8, 7)));
	}

	// Create test for targets where spaces are occupied
	@Test
	public void testOccupiedTarget() {
		// Test a walkway that has two options blocked
		// Set two walkways to occupied
		board.getCell(6, 12).setOccupied(true);
		board.getCell(7, 13).setOccupied(true);
		// Calculate the targets, roll 1
		board.calcTargets(board.getCell(6, 13), 1);
		// Set the walkways back to unoccupied
		board.getCell(6, 12).setOccupied(false);
		board.getCell(7, 13).setOccupied(false);
		// Get the targets
		Set<BoardCell> targets = board.getTargets();
		// Check that the correct amount of targets exist in the list
		assertEquals(2, targets.size());
		// Check that the correct cells exist in the list
		assertTrue(targets.contains(board.getCell(5, 13)));
		assertTrue(targets.contains(board.getCell(6, 14)));

		// Test entering a room that is occupied
		// Set a room as occupied
		board.getCell(2, 11).setOccupied(true);
		// Calculate the targets
		board.calcTargets(board.getCell(4, 11), 1);
		// Set the room to unoccupied
		board.getCell(2, 11).setOccupied(false);
		// Get the targets list
		targets = board.getTargets();
		// Check that the correct amount of targets exist in the list
		assertEquals(4, targets.size());
		// Check that the correct cells exist in the list
		assertTrue(targets.contains(board.getCell(2, 11))); // Room center
		assertTrue(targets.contains(board.getCell(4, 10)));
		assertTrue(targets.contains(board.getCell(4, 12)));
		assertTrue(targets.contains(board.getCell(5, 11)));

		// Test leaving a room with a blocked doorway at closet
		// Set a doorway to occupied
		board.getCell(8, 14).setOccupied(true);
		// Calculate the targets, roll 3
		board.calcTargets(board.getCell(9, 17), 3);
		// Set the doorway back to unoccupied
		board.getCell(8, 14).setOccupied(false);
		// Get the targets list
		targets = board.getTargets();
		// Check that the correct amount of targets exist in the list
		assertEquals(6, targets.size());
		// Check that some of the correct cells exist in the list
		assertTrue(targets.contains(board.getCell(7, 15)));
		assertTrue(targets.contains(board.getCell(6, 16)));
		assertTrue(targets.contains(board.getCell(5, 17)));
		assertTrue(targets.contains(board.getCell(18, 17))); // Secret passage to Santa's Bedroom
		assertFalse(targets.contains(board.getCell(8, 14))); // Blocked doorway
	}

	// Verify that movement stays inside all four edges of our board
	@Test
	public void testBoardEdges() {
		assertEquals(Set.of(board.getCell(1, 5)),
				board.getAdjList(0, 5));
		assertEquals(Set.of(board.getCell(19, 7)),
				board.getAdjList(20, 7));
		assertEquals(Set.of(board.getCell(6, 1)),
				board.getAdjList(6, 0));
		assertEquals(Set.of(board.getCell(6, 19)),
				board.getAdjList(6, 20));
	}

	// Ordinary room cells and label cells are rooms, but have no movement links
	@Test
	public void testNonCenterRoomCells() {
		assertTrue(board.getCell(0, 0).isRoom());
		assertTrue(board.getAdjList(0, 0).isEmpty());
		assertTrue(board.getCell(2, 2).isRoom());
		assertTrue(board.getAdjList(2, 2).isEmpty());
	}

	// An interior walkway connects to all four neighboring walkways
	@Test
	public void testFourWalkwayNeighbors() {
		Set<BoardCell> expected = Set.of(
				board.getCell(5, 13),
				board.getCell(7, 13),
				board.getCell(6, 12),
				board.getCell(6, 14));

		assertEquals(expected, board.getAdjList(6, 13));
	}
}