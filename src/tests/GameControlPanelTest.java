package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.ComputerPlayer;
import gameDisplay.GameControlPanel;

public class GameControlPanelTest {
    private GameControlPanel panel;
    private JTextField name;
    private JTextField roll;
    private JTextField guess;
    private JTextField result;

    @BeforeEach
    void setUp() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            panel = new GameControlPanel();

            // Locate fields within the existing nested panels
            name = getField(0, 0, 1);
            roll = getField(0, 1, 1);
            guess = getField(1, 0, 0);
            result = getField(1, 1, 0);
        });
    }

    @Test
    void testTurnUpdatesNameColorAndRoll() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            panel.setTurn(new ComputerPlayer("Santa", "Red", 0, 0), 5);

            assertEquals("Santa", name.getText());
            assertEquals(Color.RED, name.getBackground());
            assertEquals("5", roll.getText());

            // A new turn must replace the previous player's information
            panel.setTurn(new ComputerPlayer("Frosty", "Blue", 0, 0), 2);

            assertEquals("Frosty", name.getText());
            assertEquals(Color.BLUE, name.getBackground());
            assertEquals("2", roll.getText());
        });
    }

    @Test
    void testGuessAndResultUpdates() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            panel.setGuess("Santa, Kitchen, Candy Cane");
            panel.setGuessResult("Suggestion disproved");

            assertEquals("Santa, Kitchen, Candy Cane", guess.getText());
            assertEquals("Suggestion disproved", result.getText());

            // Each setter updates its own field independently
            panel.setGuess("I have no guess!");
            assertEquals("I have no guess!", guess.getText());
            assertEquals("Suggestion disproved", result.getText());

            panel.setGuessResult("No suggestion made");
            assertEquals("No suggestion made", result.getText());
            assertEquals("I have no guess!", guess.getText());
        });
    }

    @Test
    void testButtonsAndNoneditableFields() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JPanel top = (JPanel) panel.getComponent(0);

            assertEquals("Make Accusation",
                    ((JButton) top.getComponent(2)).getText());
            assertEquals("Next",
                    ((JButton) top.getComponent(3)).getText());

            for (JTextField field :
                    new JTextField[] { name, roll, guess, result }) {
                assertFalse(field.isEditable(),
                        "Display fields should not be editable");
            }
        });
    }

    // Retrieve a text field from the current control panel layout.
    private JTextField getField(int row, int section, int index) {
        JPanel rowPanel = (JPanel) panel.getComponent(row);
        JPanel sectionPanel = (JPanel) rowPanel.getComponent(section);
        return (JTextField) sectionPanel.getComponent(index);
    }
}