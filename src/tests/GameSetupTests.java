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

/*
 * We need these getters and function
 * Board getters: getPlayers(), getDeck(), getTheAnswer()
 * Player getters: getName(), getColor(), getRow(), getColumn(), getHand()
 * getColor() returns a String, matching the current Player constructor
*/
public class GameSetupTests {
    private Board board;

    @BeforeEach
    public void setUp() {
        // Reload the singleton before each test
        board = Board.getInstance();
        board.setConfigFiles("ClueBoardLayout.csv", "ClueSetup.txt");
        board.initialize();
    }

    @Test
    public void testLoadPlayers() throws IOException {
        // Check player data, one human, and five computers
        List<String[]> expected = setupEntries("Player");
        List<Player> players = board.getPlayers();
        assertEquals(6, expected.size());
        assertEquals(6, players.size());
        assertTrue(players.get(0) instanceof HumanPlayer);
        int humans = 0;
        int computers = 0;
        Set<String> names = new HashSet<>();

        for (int i = 0; i < expected.size(); i++) {
            String[] entry = expected.get(i);
            Player player = players.get(i);
            assertEquals(entry[1], player.getName());
            assertEquals(entry[2], player.getColor());
            assertEquals(Integer.parseInt(entry[4]), player.getRow());
            assertEquals(Integer.parseInt(entry[5]), player.getColumn());
            assertTrue(names.add(player.getName()), "Duplicate player");
            if (entry[3].equals("Human")) {
                assertTrue(player instanceof HumanPlayer);
                humans++;
            } else {
                assertEquals("Computer", entry[3]);
                assertTrue(player instanceof ComputerPlayer);
                computers++;
            }
        }
        assertEquals(1, humans);
        assertEquals(5, computers);
    }

    @Test
    public void testCompleteDeck() throws IOException {
        // Check all room, person, and weapon cards
        List<String[]> rooms = setupEntries("Room");
        List<String[]> people = setupEntries("Player");
        List<String[]> weapons = setupEntries("Weapon");
        assertEquals(9, rooms.size());
        assertEquals(6, people.size());
        assertTrue(weapons.size() >= 6, "Setup needs at least six weapons");

        Set<String> expected = new HashSet<>();
        for (String[] room : rooms) expected.add(CardType.ROOM + ":" + room[1]);
        for (String[] person : people) expected.add(CardType.PERSON + ":" + person[1]);
        for (String[] weapon : weapons) expected.add(CardType.WEAPON + ":" + weapon[1]);
        assertEquals(rooms.size() + people.size() + weapons.size(), board.getDeck().size());
        assertEquals(expected, uniqueCardKeys(board.getDeck()));
    }

    @Test
    public void testCards() {
        // Check card data and equality by name and type
        Card room = new Card(new String("Kitchen"), CardType.ROOM);
        assertEquals("Kitchen", room.getCardName());
        assertEquals(CardType.ROOM, room.getCardType());
        assertTrue(room.equals(new Card(new String("Kitchen"), CardType.ROOM)));
        assertFalse(room.equals(new Card("Pantry", CardType.ROOM)));
        assertFalse(room.equals(new Card("Kitchen", CardType.WEAPON)));
    }
    
    @Test
    public void testSolution() {
        // Check one solution card of each type from the deck
        Set<String> original = uniqueCardKeys(board.getDeck());
        board.deal();
        List<Card> answer = solutionCards();
        assertEquals(CardType.ROOM, answer.get(0).getCardType());
        assertEquals(CardType.PERSON, answer.get(1).getCardType());
        assertEquals(CardType.WEAPON, answer.get(2).getCardType());
        for (Card card : answer) assertTrue(original.contains(key(card)));
    }

    @Test
    public void testDeal() {
        // Check all cards dealt once and balanced player hands
        List<Card> original = new ArrayList<>(board.getDeck());
        Set<String> expected = uniqueCardKeys(original);
        assertFalse(expected.isEmpty());
        assertEquals(6, board.getPlayers().size());
        board.deal();

        List<Card> distributed = new ArrayList<>(solutionCards());
        int cardsToDeal = original.size() - 3;
        int playerCount = board.getPlayers().size();
        assertEquals(6, playerCount);
        int minimum = cardsToDeal / playerCount;
        int maximum = (cardsToDeal + playerCount - 1) / playerCount;
        for (Player player : board.getPlayers()) {
            assertNotNull(player.getHand());
            int size = player.getHand().size();
            assertTrue(size >= minimum && size <= maximum, "Uneven deal");
            distributed.addAll(player.getHand());
        }

        assertEquals(original.size(), distributed.size());
        assertEquals(expected, uniqueCardKeys(distributed));
    }
 // Get the three solution cards
    private List<Card> solutionCards() {
        Solution answer = board.getTheAnswer();
        assertNotNull(answer);
        assertNotNull(answer.getRoomSolution());
        assertNotNull(answer.getPersonSolution());
        assertNotNull(answer.getWeaponSolution());
        return List.of(answer.getRoomSolution(), answer.getPersonSolution(),
                answer.getWeaponSolution());
    }

    // Compare card values and reject duplicates
    private static Set<String> uniqueCardKeys(List<Card> cards) {
        assertNotNull(cards);
        Set<String> keys = new HashSet<>();
        for (Card card : cards) {
            assertNotNull(card);
            assertNotNull(card.getCardName());
            assertNotNull(card.getCardType());
            assertTrue(keys.add(key(card)), "Duplicate card: " + key(card));
        }
        return keys;
    }

    private static String key(Card card) {
        return card.getCardType() + ":" + card.getCardName();
    }

    // Read expected values from the setup file
    private static List<String[]> setupEntries(String type) throws IOException {
        List<String[]> entries = new ArrayList<>();
        for (String line : Files.readAllLines(Path.of("data", "ClueSetup.txt"))) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("//")) continue;
            String[] parts = line.split(",", -1);
            for (int i = 0; i < parts.length; i++) parts[i] = parts[i].trim();
            if (parts[0].equals(type)) {
                int length = type.equals("Player") ? 6 : type.equals("Weapon") ? 2 : 3;
                assertEquals(length, parts.length, "Malformed entry: " + line);
                assertFalse(parts[1].isEmpty());
                entries.add(parts);
            }
        }
        return entries;
    }
}