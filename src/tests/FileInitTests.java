package tests;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import java.io.FileNotFoundException;
import org.junit.*;
import org.junit.jupiter.api.BeforeAll;

import clueGame.*;

public class FileInitTests {
	// Constants that I will use to test whether the file was loaded correctly
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 21;
	public static final int NUM_COLUMNS = 21;
	
	// Static board variable so there can be only one
	public static Board board;
	
	// Set up section to initialize the board and config files
	@BeforeAll
	public static void setUp() {
		// Get the only instance of the board
		board = Board.getInstance();
		
		// Initialize the configuration files
		board.setConfigFiles("ClueBoardLayout.csv", "ClueSetup.txt");
		
		// Initialize the board
		board.initialize();
	}
	
	// Ensure the layout and setup files are loaded correctly
	@Test 
	public void testFileInit() {
		// Call both load layout functions. This test will fail if the load fails
		board.loadLayoutConfig();
		board.loadSetupConfig();
	}
	
	// Ensure the correct number of rows/columns have been read
	@Test
	public void testRowCol() {
		// Verify that the correct number of rows have been read
		assertEquals(NUM_ROWS, board.getNumRows());
		
		// Verify that the correct number of columns have been read
		assertEquals(NUM_COLUMNS, board.getNumColumns());
	}
	
	// Verify at least one doorway in each direction. Also verify cells that don't contain doorways return false for isDoorway()
	@Test
	public void testDoorwayDirection() {
		// Verify doorway UP
		BoardCell cell = board.getCell(6, 1);
		// Verify that the cell is a door
		assertTrue(cell.isDoorway());
		// Verify the doorway is pointing up
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
		
		// Verify doorway DOWN
		cell = board.getCell(7, 17);
		// Verify that the cell is a door
		assertTrue(cell.isDoorway());
		// Verify the doorway is pointing down
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
		
		// Verify doorway LEFT
		cell = board.getCell(8, 6);
		// Verify that the cell is a door
		assertTrue(cell.isDoorway());
		// Verify that the doorway is pointing left
		assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
		
		// Verify doorway RIGHT
		cell = board.getCell(8,  14);
		// Verify that the cell is a door
		assertTrue(cell.isDoorway());
		// Verify that the doorway is pointing right
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
		
		// Verify a regular cell is not a doorway
		cell = board.getCell(0, 0);
		assertFalse(cell.isDoorway());
		assertEquals(DoorDirection.NONE, cell.getDoorDirection());
	}
	
	// Check that the correct number of doors have been loaded
	@Test
	public void testNumDoors() {
		// Counter to count the number of doors
		int numDoors = 0;
		
		// Loop through the board and increase the counter every time you find a doorway
		for (int row = 0; row < board.getNumRows(); row++)
			for (int col = 0; col < board.getNumColumns(); col++) {
				// Temporarily store the cell
				BoardCell cell = board.getCell(row, col);
				
				// If the cell is a doorway, increase the counter
				if (cell.isDoorway()) {
					numDoors++;
				}
			}
		
		// Ensure that the number of doors equals 15
		Assert.assertEquals(15, numDoors);
	}
	
	// Check some of the cells to ensure that they have the correct initials and names
	@Test
	public void testInitialsNames() {
		// Test three rooms to see if they have been loaded correctly
		assertEquals("Kitchen", board.getRoom('K').getName());
		assertEquals("Elf Dorms", board.getRoom('E').getName());
		assertEquals("Pantry", board.getRoom('P').getName());
	}
	
	// Check that rooms have the proper center cell and label cell
	@Test
	public void testCenterLabelCells() {
		// Test four different cells to see if they are center or label cells
		
		// Label tests
		BoardCell cell = board.getCell(2, 2);
		assertTrue(cell.isLabel());
		
		cell = board.getCell(16, 9);
		assertTrue(cell.isLabel());
		
		// Center tests
		cell = board.getCell(2, 17);
		assertTrue(cell.isRoomCenter());
		
		cell = board.getCell(9, 2);
		assertTrue(cell.isRoomCenter());
	}
}
