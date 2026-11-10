package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.*;

public class GameSolutionTest {
    private Board board;
    private Card santa;
    private Card candyCane;
    private Card kitchen;

    @BeforeEach
    void setUp() {
        board = Board.getInstance();
        board.setConfigFiles("ClueBoardLayout.csv", "ClueSetup.txt");
        board.initialize();

        santa = findCard("Santa");
        candyCane = findCard("Candy Cane");
        kitchen = findCard("Kitchen");
    }

    @Test
    void accusationRequiresAllThreeCardsToMatch() {
        board.deal();
        Solution answer = board.getTheAnswer();
        Card person = answer.getPersonSolution();
        Card weapon = answer.getWeaponSolution();
        Card room = answer.getRoomSolution();

        assertAll(
            () -> assertTrue(board.checkAccusation(person, weapon, room)),
            () -> assertFalse(board.checkAccusation(differentCard(person), weapon, room)),
            () -> assertFalse(board.checkAccusation(person, differentCard(weapon), room)),
            () -> assertFalse(board.checkAccusation(person, weapon, differentCard(room)))
        );
    }

    @Test
    void playerReturnsTheirOnlyMatchingCard() {
        Player player = board.getPlayers().get(1);
        player.updateHand(candyCane);
        player.updateHand(findCard("Pantry"));

        assertSame(candyCane, player.disproveSuggestion(santa, candyCane, kitchen));
    }

    @Test
    void playerReturnsNullWithNoMatchingCard() {
        Player player = board.getPlayers().get(1);
        player.updateHand(findCard("Pantry"));

        assertNull(player.disproveSuggestion(santa, candyCane, kitchen));
    }

    @Test
    void playerRandomlyChoosesBetweenMatchingCards() {
        Player player = board.getPlayers().get(1);
        player.updateHand(santa);
        player.updateHand(kitchen);
        player.updateHand(findCard("Pantry"));

        // Both matching cards should appear across many repeated calls
        Set<Card> shown = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            shown.add(player.disproveSuggestion(santa, candyCane, kitchen));
        }

        assertEquals(Set.of(santa, kitchen), shown);
    }

    @Test
    void boardReturnsNullWhenNobodyCanDisprove() {
        assertNull(board.handleSuggestion(santa, candyCane, kitchen));
    }

    @Test
    void boardReturnsCardWhenOnlyTheHumanCanDisprove() {
        board.getPlayers().get(0).updateHand(candyCane);

        assertSame(candyCane, board.handleSuggestion(santa, candyCane, kitchen));
    }

    @Test
    void boardReturnsTheFirstMatchingCardInListOrder() {
        // Player 0 has no match, Players 1 and 2 each have one
        board.getPlayers().get(1).updateHand(candyCane);
        board.getPlayers().get(2).updateHand(kitchen);

        assertSame(candyCane, board.handleSuggestion(santa, candyCane, kitchen));
    }

    private Card findCard(String name) {
        return board.getDeck().stream()
                .filter(card -> card.getCardName().equals(name))
                .findFirst()
                .orElseThrow();
    }

    private Card differentCard(Card original) {
        return board.getDeck().stream()
                .filter(card -> card.getCardType() == original.getCardType())
                .filter(card -> !card.equals(original))
                .findFirst()
                .orElseThrow();
    }
    
    @Test
    void boardSkipsSuggesterAndStartsWithNextPlayer() {
        // Player 1 is the suggester and initially has the only matching card
        board.getPlayers().get(1).updateHand(santa);
        assertNull(board.handleSuggestion(santa, candyCane, kitchen, 1));

        // Player 2 should answer before player 0
        board.getPlayers().get(0).updateHand(kitchen);
        board.getPlayers().get(2).updateHand(candyCane);
        assertSame(candyCane, board.handleSuggestion(santa, candyCane, kitchen, 1));
    }
}