package tests;

import static org.junit.Assert.*;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;

public class ExceptionTests {
	// Verify that malformed configuration files throw the expected exception.
	@Test
	public void testBadSetup() throws BadConfigFormatException, FileNotFoundException{
		assertThrows(BadConfigFormatException.class, () -> {
			// Create a test board
			Board testBoard = Board.getInstance();

			// Load the bad setup file and the good layout file
			testBoard.setConfigFiles("ClueBoardLayout.csv", "ClueSetupBadFormat306.txt");

			// The bad setup file should throw before the layout is loaded.
			testBoard.loadSetupConfig();
			testBoard.loadLayoutConfig();
		});
	}

	// Verify that malformed configuration files throw the expected exception.
	@Test
	public void testBadLayout() throws BadConfigFormatException, FileNotFoundException{
		assertThrows(BadConfigFormatException.class, () -> {
			// Create a test board
			Board testBoard = Board.getInstance();

			// Load the good setup file and the bad layout file
			testBoard.setConfigFiles("ClueLayoutBadRoom306.csv", "ClueSetup306.txt");

			// Load the valid setup before checking the invalid layout.
			testBoard.loadSetupConfig();
			// The layout contains an undefined room initial.
			testBoard.loadLayoutConfig();
		});
	}
}
