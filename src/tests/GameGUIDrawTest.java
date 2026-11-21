package tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import gameDisplay.GameCardsPanel;
import gameDisplay.GameControlPanel;
import gameDisplay.GameDrawPanel;
import gameDisplay.GameGUI;

public class GameGUIDrawTest {
    private static final int CELL_SIZE = 30;

    private Board board;
    private GameDrawPanel panel;
    private BufferedImage image;

    @BeforeEach
    void setUp() throws Exception {
        board = Board.getInstance();
        board.setConfigFiles("ClueBoardLayout.csv", "ClueSetup.txt");
        board.initialize();

        SwingUtilities.invokeAndWait(() -> {
            int width = board.getNumColumns() * CELL_SIZE;
            int height = board.getNumRows() * CELL_SIZE;
            panel = new GameDrawPanel();
            panel.setSize(width, height);
            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            Graphics graphics = image.createGraphics();
            try {
                panel.paint(graphics);
            } finally {
                graphics.dispose();
            }
        });
    }

    @Test
    void testBoardDrawing() {
        assertColor(Color.GRAY, 15, 15, "Kitchen floor");
        assertColor(new Color(220, 20, 60), 105, 195, "Walkway");
        assertColor(Color.BLACK, 90, 195, "Grid line");
        assertColor(Color.BLUE, 179, 45, "Right edge of doorway");

        // The Kitchen label is inside the gray cell at row 2 column 2
        int[] labelPixels = image.getRGB(60, 60, CELL_SIZE, CELL_SIZE, null, 0, CELL_SIZE);
        assertTrue(Arrays.stream(labelPixels).anyMatch(pixel -> pixel == Color.BLACK.getRGB()),
                "Kitchen label should be visible");
    }

    @Test
    void testPlayerDrawing() {
        assertPlayer(Color.RED, 20, 13, "Santa");
        assertPlayer(Color.GREEN, 0, 9, "Buddy");
        assertPlayer(new Color(165, 42, 42), 20, 7, "Rudolph");
        assertPlayer(Color.PINK, 14, 0, "Mrs. Claus");
        assertPlayer(Color.YELLOW, 6, 20, "Elfy");
        assertPlayer(Color.BLUE, 6, 0, "Frosty");
    }

    @Test
    void testFrameAndPanels() throws Exception {
        assumeFalse(GraphicsEnvironment.isHeadless(), "JFrame needs a display");

        SwingUtilities.invokeAndWait(() -> {
            GameControlPanel controls = new GameControlPanel();
            GameCardsPanel cards = new GameCardsPanel(board.getPlayers().get(0));
            GameGUI gui = new GameGUI(controls, cards, panel);
            gui.displayGUI(gui);

            JFrame frame = assertInstanceOf(JFrame.class, SwingUtilities.getWindowAncestor(panel));
            try {
                assertEquals("Clue Game", frame.getTitle());
                assertEquals(JFrame.EXIT_ON_CLOSE, frame.getDefaultCloseOperation());
                assertTrue(frame.isVisible());
                assertSame(frame, SwingUtilities.getWindowAncestor(controls));
                assertSame(frame, SwingUtilities.getWindowAncestor(cards));
            } finally {
                frame.dispose();
            }
        });
    }

    private void assertPlayer(Color color, int row, int col, String name) {
        int x = col * CELL_SIZE + CELL_SIZE / 2;
        int y = row * CELL_SIZE + CELL_SIZE / 2;
        assertColor(color, x, y, name);
    }

    private void assertColor(Color color, int x, int y, String description) {
        assertEquals(color.getRGB(), image.getRGB(x, y), description);
    }
}