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
			testBoard.setConfigFiles("ClueLayout.csv", "ClueSetupBadFormat306.txt");

			// Load the good layout file - this should not throw
			testBoard.loadLayoutConfig();

			// Load the bad setup file - this should throw the error
			testBoard.loadSetupConfig();
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

			// Load the bad layout file - this should throw
			testBoard.loadLayoutConfig();

			// Load the good setup file - this should not throw the error
			testBoard.loadSetupConfig();
		});
	}
}
