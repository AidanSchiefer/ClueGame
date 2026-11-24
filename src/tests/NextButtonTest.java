package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test; 

import clueGame.*;
import gameDisplay.*;

import java.awt.Color;

/*
 * Methods to implement:
 * In Board class: startGame(), nextTurn(), getCurrentPlayer(), getCurrentRoll(), isTurnFinished(), handleClick()
 * */

public class NextButtonTest {
	// Member variable
	private Board board;
	
	// Setup class
	@BeforeEach
	public void setUp() {
		// Get the board instance
		board = Board.getInstance();
		
		// Load the board and setup configs
		board.setConfigFiles("ClueBoardLayout.csv", "ClueSetup.txt");
		board.initialize();
		
		// Start the game starting with the human player
		board.startGame();
	}
	
	//----- Tests -----
	
	@Test
	// Test to ensure that the first turn belongs to the human player
	public void testFirstPlayer() {
		// Assert that the current player is the human player
		assertTrue(board.getCurrentPlayer() instanceof HumanPlayer);
		
		// Assert that the first player in the player list is the current player
		assertSame(board.getPlayers().get(0), board.getCurrentPlayer());
		
		// Assert that the human players turn is not over
		assertFalse(board.isTurnFinished());
	}
	
	@Test
	// Go through every players turn,  then back to the human player
	public void testTurnOrder() {
		// Get the player list from the board
		ArrayList<Player> players = board.getPlayers();
		
		// Finish the humans turn
		finishHumanTurn();
		
		// Loop through each computer player
		for (int i = 1; i < players.size(); i++) {
		    assertTrue(board.nextTurn());
		    assertSame(players.get(i), board.getCurrentPlayer());
		    assertTrue(board.getCurrentPlayer() instanceof ComputerPlayer);
		}
		
		// Move back around to the human
		assertTrue(board.nextTurn());
		assertSame(players.get(0), board.getCurrentPlayer());
	}
	
	@Test
	// Test if a given roll is valid
	public void testValidRoll() {
		// Loop through two rounds of the game
		for (int i = 0; i < board.getPlayers().size() * 2; i ++) {
			// get the current roll
			int roll = board.getCurrentRoll();
			
			// Assert that the roll is between 1 and 6
			assertTrue(roll >= 1 && roll <= 6);
			
			// Finish the current turn and move on
			if (!board.isTurnFinished()) {
			    finishHumanTurn();
			}
			board.nextTurn();
		}
	}
	
	@Test
	// Test to make sure the human player has targets
	public void testHumanTargets() {
		// Ensure the current player is the human player
		assertTrue(board.getCurrentPlayer() instanceof HumanPlayer);
		
		// Test that the target list is not empty
		assertFalse(board.getTargets().isEmpty());
	}
	
	@Test
	// Test for a valid click
	public void testValidClick() {
		// Get the human player
		Player human = board.getCurrentPlayer();
		
		// Extract a cell that is in the humans target list
		BoardCell goodCell = board.getTargets().iterator().next();
		
		// Assert that the human made the click
		assertTrue(board.handleClick(goodCell));
		
		// Assert that the humans location matches the target cells location
		assertEquals(goodCell.getRow(), human.getRow());
		assertEquals(goodCell.getColumn(), human.getColumn());
		
		// Ensure the humans turn is over
		assertTrue(board.isTurnFinished());
	}
	
	@Test
	// Test to ensure the game cannot advance until the human player moves
	public void testCannotAdvance() {
		// Create a human player
		Player human = board.getCurrentPlayer();
		
		// Assert that it is not the mext turn
		assertFalse(board.nextTurn());
		
		// Assert that the player object and current player are the same
		assertSame(human, board.getCurrentPlayer());
		
		// End human turn. Next turn should be available
		finishHumanTurn();
		assertTrue(board.nextTurn());
		assertNotSame(human, board.getCurrentPlayer());
	}
	
	//----- Helper methods -----
	
	// Helper to complete the human's turn
	private void finishHumanTurn() {
		// Get a valid target
		BoardCell target = board.getTargets().iterator().next();
		
		// Simulate the human clicking the target
		board.handleClick(target);
	}
}
