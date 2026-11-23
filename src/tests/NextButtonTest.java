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
	
	// Tests
	
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
		for (Player player: players) {
			// Ensure it is time for the next turn
			assertTrue(board.nextTurn());
			
			// Make sure the current player in the loop and the current player are the same
			assertSame(player, board.getCurrentPlayer());
			
			// Assert that the current player is a computer player
			assertTrue(board.getCurrentPlayer() instanceof ComputerPlayer);
		}
		
		// Move back around to the human
		assertTrue(board.nextTurn());
		assertSame(players.get(0), board.getCurrentPlayer());
	}
	
	// Helper methods
	
	// Helper to complete the human's turn
	private void finishHumanTurn() {
		// Get a valid target
		BoardCell target = board.getTargets().iterator().next();
		
		// Simulate the human clicking the target
		board.handleClick(target);
	}
}
