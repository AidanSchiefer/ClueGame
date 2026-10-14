// Authors: Aidan Schiefer, Chris Hussain

package tests;


//used static so we don't have to type Assertions.assertEquals everytime 
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
	
	//target tests:
	
	
}
