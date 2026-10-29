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
	
	// TODO: Create test for adjacency at a door
	
	// TODO: Create test for adjacency at room
	
	// TODO: Create a test for adjacency at Secret Passage
	
	// TODO: Create a test for adjacency at walkway
	
	// TODO: Create test for target at room Santa's Bedroom
	
	// TODO: Create test for target at room Elf Dorm
	
	// TODO: Create test for target at doorway
	
	// TODO: Create test for target in walkway
	
	// TODO: Create test for targets where spaces are occupied
}
