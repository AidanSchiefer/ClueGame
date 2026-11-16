package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.*;
import gameDisplay.GameCardsPanel;

public class GameCardsPanelTest {
    private HumanPlayer human;
    private ComputerPlayer buddy;
    private ComputerPlayer frosty;
    private GameCardsPanel panel;

    @BeforeEach
    void setUp() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            human = new HumanPlayer("Santa", "Red", 0, 0);
            buddy = new ComputerPlayer("Buddy", "Green", 0, 0);
            frosty = new ComputerPlayer("Frosty", "Blue", 0, 0);

            human.updateHand(new Card("Santa", CardType.PERSON));
            human.updateHand(new Card("Kitchen", CardType.ROOM));
            human.updateHand(new Card("Candy Cane", CardType.WEAPON));

            panel = new GameCardsPanel(human);
        });
    }

    @Test
    void testHumanHandDisplay() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            // Each category initially has a hand card and no seen cards
            assertCategory(0, "Santa", "None");
            assertCategory(1, "Kitchen", "None");
            assertCategory(2, "Candy Cane", "None");

            // Refresh also picks up changes to the human's hand
            human.updateHand(new Card("Gift Wrap", CardType.WEAPON));
            panel.updatePanels();

            assertCategory(2, "Candy Cane", "Gift Wrap", "None");
        });
    }

    @Test
    void testSeenCardsUpdateCorrectCategories() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            panel.addSeenCard(new Card("Buddy", CardType.PERSON), buddy);
            panel.addSeenCard(new Card("Pantry", CardType.ROOM), frosty);
            panel.addSeenCard(new Card("Snowball", CardType.WEAPON), buddy);

            assertCategory(0, "Santa", "Buddy");
            assertCategory(1, "Kitchen", "Pantry");
            assertCategory(2, "Candy Cane", "Snowball");

            // Seen cards must not be added to the human's hand
            assertEquals(3, human.getHand().size());
        });
    }

    @Test
    void testOwnerColorsAndNoDuplicates() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            panel.addSeenCard(new Card("Buddy", CardType.PERSON), buddy);
            panel.addSeenCard(new Card("Frosty", CardType.PERSON), frosty);

            // Seeing the same card again must not duplicate its field
            panel.addSeenCard(new Card("Buddy", CardType.PERSON), buddy);
            panel.updatePanels();

            assertCategory(0, "Santa", "Buddy", "Frosty");

            List<JTextField> people =
                    fields((Container) panel.getComponent(0));

            assertEquals(Color.GREEN, people.get(1).getBackground());
            assertEquals(Color.BLUE, people.get(2).getBackground());

            for (JTextField field : fields(panel)) {
                assertFalse(field.isEditable());
            }
        });
    }

    // Check card text and order within a category
    private void assertCategory(int index, String... expected) {
        List<JTextField> actual = fields((Container) panel.getComponent(index));

        assertEquals(expected.length, actual.size());

        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual.get(i).getText());
        }
    }

    // Find text fields without adding test-only getters to the GUI
    private List<JTextField> fields(Container container) {
        List<JTextField> result = new ArrayList<>();

        for (Component component : container.getComponents()) {
            if (component instanceof JTextField) {
                result.add((JTextField) component);
            } else if (component instanceof Container) {
                result.addAll(fields((Container) component));
            }
        }

        return result;
    }
}