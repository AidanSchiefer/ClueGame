package tests;

import static org.junit.Assert.*;
import java.io.FileNotFoundException;

import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;

public class ExceptionTests {
	// Test that tests whether a bad Setup file is given
	@Test
	public void testBadSetup() throws BadConfigFormatException, FileNotFoundException{
		assertThrows(BadConfigFormatException.class, () -> {
			// Create a test board
			Board testBoard = Board.getInstance();

			// Load the bad setup file and the good layout file
			testBoard.setConfigFiles("ClueBoardLayout.csv", "ClueSetupBadFormat306.txt");

			// Good setup should load successfully
			testBoard.loadSetupConfig();

			// Bad layout should throw here
			testBoard.loadLayoutConfig();
		});
	}

	// Test that tests whether a bad Setup file is given
	@Test
	public void testBadLayout() throws BadConfigFormatException, FileNotFoundException{
		assertThrows(BadConfigFormatException.class, () -> {
			// Create a test board
			Board testBoard = Board.getInstance();

			// Load the good setup file and the bad layout file
			testBoard.setConfigFiles("ClueLayoutBadRoom306.csv", "ClueSetup306.txt");

			// Good setup should load successfully
			testBoard.loadSetupConfig();

			// Bad layout should throw here
			testBoard.loadLayoutConfig();
		});
	}
}
