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
	
	// TODO: Create test for adjacency at a door. Ensure adj includes available walkway and room center
	@Test
	public void testDoorAdj() {
		// Get the adj list for one of the doorways
		Set<BoardCell> testAdjList = board.getAdjList(6, 1);
		// Test it has the correct amount of adjacencies
		assertEquals(3, testAdjList.size());
		// Check that the list contains the correct cells
		assertTrue(testAdjList.contains(board.getCell(6, 0)));
		assertTrue(testAdjList.contains(board.getCell(6, 2)));
		assertTrue(testAdjList.contains(board.getCell(9, 2)));
		
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
	
	// TODO: Create test for adjacency at room
	@Test
	public void testRoomAdj() {
		
	}
	
	// TODO: Create a test for adjacency at Secret Passage
	@Test
	public void testSecretPassAdj() {
		
	}
	
	// TODO: Create a test for adjacency at walkway
	@Test
	public void testWalkAdj() {
		
	}
	
	// TODO: Create test for target at room Santa's Bedroom
	@Test
	public void testSantasBedTarget() {
		
	}
	
	// TODO: Create test for target at room Elf Dorm
	@Test
	public void testElfDormTarget() {
		
	}
	
	// TODO: Create test for target at doorway
	@Test
	public void testDoorwayTarget() {
		
	}
	
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
		assertTrue(targets.contains(board.getCell(5, 10)));
		assertTrue(targets.contains(board.getCell(6, 12)));
		
		// Test a roll of 5
		board.calcTargets(board.getCell(6, 10), 5);
		// Get the target list
		targets = board.getTargets();
		// Test that the correct amount of targets exist in the list
		assertEquals(8, targets.size());
		// Test that some of the correct cells exist in the target list
		assertTrue(targets.contains(board.getCell(6, 15)));
		assertTrue(targets.contains(board.getCell(5, 14)));
		assertTrue(targets.contains(board.getCell(8, 7)));
	}
	
	// TODO: Create test for targets where spaces are occupied
	@Test
	public void testOccupiedTarget() {
		
	}
}
