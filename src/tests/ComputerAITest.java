package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.*;

public class ComputerAITest {
    private Board board;
    private BoardCell kitchen;
    private BoardCell pantry;
    private BoardCell walkway1;
    private BoardCell walkway2;

    @BeforeEach
    void setUp() {
        board = Board.getInstance();
        board.setConfigFiles("ClueBoardLayout.csv", "ClueSetup.txt");
        board.initialize();

        kitchen = board.getRoom('K').getCenterCell();
        pantry = board.getRoom('P').getCenterCell();
        walkway1 = board.getCell(2, 5);
        walkway2 = board.getCell(3, 5);
    }

    @Test
    void suggestionUsesCurrentRoomAndOnlyUnseenPersonAndWeapon() {
        ComputerPlayer player = playerWithUnseenCards(Set.of("Buddy", "Snowball"));

        Solution suggestion = player.createSuggestion();

        assertEquals("Kitchen", suggestion.getRoomSolution().getCardName());
        assertEquals("Buddy", suggestion.getPersonSolution().getCardName());
        assertEquals("Snowball", suggestion.getWeaponSolution().getCardName());
    }

    @Test
    void suggestionRandomlyChoosesUnseenPeopleAndWeapons() {
        Set<String> people = new HashSet<>();
        Set<String> weapons = new HashSet<>();
        Set<String> unseen = Set.of("Buddy", "Rudolph", "Snowball", "Gift Wrap");

        // Give each trial the same starting hand and seen cards
        for (int i = 0; i < 100; i++) {
            Solution suggestion = playerWithUnseenCards(unseen).createSuggestion();
            people.add(suggestion.getPersonSolution().getCardName());
            weapons.add(suggestion.getWeaponSolution().getCardName());
        }

        assertEquals(Set.of("Buddy", "Rudolph"), people);
        assertEquals(Set.of("Snowball", "Gift Wrap"), weapons);
    }

    @Test
    void targetIsRandomWhenThereAreNoRooms() {
        assertAllTargetsAreSelected(playerInKitchen(), Set.of(walkway1, walkway2));
    }

    @Test
    void targetPrefersAnUnseenRoom() {
        ComputerPlayer player = playerInKitchen();

        // An unseen room is preferred even with an empty seen list
        assertSame(pantry, player.selectTarget(Set.of(pantry, walkway1, walkway2)));

        player.updateSeen(findCard("Kitchen"));
        for (int i = 0; i < 100; i++) {
            assertSame(pantry, player.selectTarget(Set.of(pantry, kitchen, walkway1)));
        }
    }

    @Test
    void targetIsRandomIncludingTheRoomWhenTheRoomIsSeen() {
        ComputerPlayer player = playerInKitchen();
        player.updateSeen(findCard("Pantry"));
        player.updateSeen(findCard("Santa"));

        assertAllTargetsAreSelected(player, Set.of(pantry, walkway1, walkway2));
    }

    private void assertAllTargetsAreSelected(ComputerPlayer player, Set<BoardCell> targets) {
        Set<BoardCell> selected = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            selected.add(player.selectTarget(targets));
        }
        assertEquals(targets, selected);
    }

    private ComputerPlayer playerInKitchen() {
        return new ComputerPlayer("Frosty", "Blue", kitchen.getRow(), kitchen.getColumn());
    }

    private ComputerPlayer playerWithUnseenCards(Set<String> unseen) {
        ComputerPlayer player = playerInKitchen();

        // Put one person and one weapon in hand, Mark the other known cards seen
        for (Card card : board.getDeck()) {
            String name = card.getCardName();
            if (name.equals("Santa") || name.equals("Candy Cane")) {
                player.updateHand(card);
            } else if (card.getCardType() != CardType.ROOM && !unseen.contains(name)) {
                player.updateSeen(card);
            }
        }
        return player;
    }

    private Card findCard(String name) {
        return board.getDeck().stream()
                .filter(card -> card.getCardName().equals(name))
                .findFirst()
                .orElseThrow();
    }
}