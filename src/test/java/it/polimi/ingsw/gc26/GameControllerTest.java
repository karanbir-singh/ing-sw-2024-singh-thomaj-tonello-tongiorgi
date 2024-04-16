package it.polimi.ingsw.gc26;

import it.polimi.ingsw.gc26.model.card.StarterCard;
import it.polimi.ingsw.gc26.model.game.Game;
import it.polimi.ingsw.gc26.model.game.GameState;
import it.polimi.ingsw.gc26.model.player.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class GameControllerTest {
    private static GameController gameController;

    private static ArrayList<Player> players;

    @BeforeAll
    static void beforeAll() {
        // Create players
        players = new ArrayList<>();
        players.add(new Player("0", "Pippo"));
        players.add(new Player("1", "Baudo"));
        players.add(new Player("2", "Carlo"));

        // Create game controller
        gameController = new GameController(new Game(players));
    }

    @Test
    void totalGamePreparation() {
        // Start game preparation
        gameController.prepareCommonTable();

        // Each player should play their starter card
        for (Player player : players) {
            gameController.playCardFromHand(player.getID());
        }

        // Common missions preparing and secret missions giving are automatically done

        // Each player should set their secret mission
        for (Player player : players) {
            gameController.selectSecretMission(0, player.getID());
            gameController.setSecretMission(player.getID());
        }

        // First player it's automatically selected

        // Check with assertions
        assertFalse(gameController.getGame().getCommonTable().getResourceCards().isEmpty());
        assertFalse(gameController.getGame().getCommonTable().getGoldCards().isEmpty());
        assertEquals(2, gameController.getGame().getCommonTable().getResourceCards().size());
        assertEquals(2, gameController.getGame().getCommonTable().getGoldCards().size());
        assertEquals(40 - 2 - players.size() * 2, gameController.getGame().getCommonTable().getResourceDeck().getCards().size());
        assertEquals(40 - 2 - players.size(), gameController.getGame().getCommonTable().getGoldDeck().getCards().size());

        for (Player player : gameController.getGame().getPlayers()) {
            assertNotNull(player.getHand());
            assertNotNull(player.getPersonalBoard());
            assertNotNull(player.getSecretMissionHand());
            assertNotNull(player.getPersonalBoard().getSecretMission());

            assertEquals(3, player.getHand().getCards().size());
            assertEquals(0, player.getPersonalBoard().getScore());
            assertEquals(1, player.getPersonalBoard().getOccupiedPositions().size());
            assertEquals(1, player.getSecretMissionHand().getCards().size());

        }
        int numStarterCards = 6 - players.size();
        assertEquals(numStarterCards, gameController.getGame().getCommonTable().getStarterDeck().getCards().size());

        assertEquals(2, gameController.getGame().getCommonTable().getCommonMissions().size());

        assertEquals(gameController.getGame().getPlayers().getFirst(), gameController.getGame().getCurrentPlayer());
    }

    @Test
    void commonTablePreparation() {
        gameController.prepareCommonTable();

        // Check with assertions
        assertFalse(gameController.getGame().getCommonTable().getResourceCards().isEmpty());
        assertFalse(gameController.getGame().getCommonTable().getGoldCards().isEmpty());
        assertEquals(2, gameController.getGame().getCommonTable().getResourceCards().size());
        assertEquals(2, gameController.getGame().getCommonTable().getGoldCards().size());
        assertEquals(38, gameController.getGame().getCommonTable().getResourceDeck().getCards().size());
        assertEquals(38, gameController.getGame().getCommonTable().getGoldDeck().getCards().size());
    }

    @Test
    void starterCardsPreparation() {
        gameController.prepareStarterCards();

        // Check with assertions
        for (Player player : gameController.getGame().getPlayers()) {
            assertNotNull(player.getHand());
            assertEquals(1, player.getHand().getCards().size());
        }
        assertEquals(6 - players.size(), gameController.getGame().getCommonTable().getStarterDeck().getCards().size());
    }

    @Test
    void starterCardPlacement() {
        gameController.prepareStarterCards();

        // Each player select (and eventually turn) the starter card
        gameController.selectCardFromHand(0, players.get(0).getID());
        gameController.turnSelectedCardSide(players.get(0).getID());

        gameController.selectCardFromHand(0, players.get(1).getID());

        gameController.selectCardFromHand(0, players.get(2).getID());
        gameController.turnSelectedCardSide(players.get(2).getID());

        // Each player plays the selected turn
        for (Player player : gameController.getGame().getPlayers()) {
            gameController.playCardFromHand(player.getID());
        }

        assertEquals(40 - players.size() * 2, gameController.getGame().getCommonTable().getResourceDeck().getCards().size());
        assertEquals(40 - players.size(), gameController.getGame().getCommonTable().getGoldDeck().getCards().size());

        // Check with assertions
        for (Player player : gameController.getGame().getPlayers()) {
            assertNotNull(player.getPersonalBoard());
            assertEquals(3, player.getHand().getCards().size());
            assertEquals(0, player.getPersonalBoard().getScore());
            assertEquals(1, player.getPersonalBoard().getOccupiedPositions().size());
        }
    }

    @Test
    void commonMissionsPreparation() {
        // Call the tested method
        gameController.prepareCommonMissions();

        // Check with assertions
        assertEquals(2, gameController.getGame().getCommonTable().getCommonMissions().size());
    }

    @Test
    void secretMissionsSetting() {
        // First of all, each player has their starter card placed
        gameController.prepareStarterCards();

        // Each player select (and eventually turn) the starter card
        gameController.selectCardFromHand(0, players.get(0).getID());
        gameController.turnSelectedCardSide(players.get(0).getID());

        gameController.selectCardFromHand(0, players.get(1).getID());

        gameController.selectCardFromHand(0, players.get(2).getID());
        gameController.turnSelectedCardSide(players.get(2).getID());

        // Each player plays the selected turn
        for (Player player : gameController.getGame().getPlayers()) {
            gameController.playCardFromHand(player.getID());
        }

        // Secret mission are automatically prepared
        gameController.prepareCommonMissions();

        gameController.selectSecretMission(0, players.get(0).getID());
        gameController.selectSecretMission(1, players.get(1).getID());
        gameController.selectSecretMission(1, players.get(2).getID());

        gameController.setSecretMission(players.get(0).getID());
        gameController.setSecretMission(players.get(1).getID());
        gameController.setSecretMission(players.get(2).getID());

        // Check with assertions
        for (Player player : gameController.getGame().getPlayers()) {
            assertNotNull(player.getSecretMissionHand());
            assertEquals(1, player.getSecretMissionHand().getCards().size());
            assertNotNull(player.getPersonalBoard().getSecretMission());
        }
    }

    @Test
    void getWinners() {
        // First of all, each player has their starter card placed
        gameController.prepareStarterCards();

        // Each player select (and eventually turn) the starter card
        gameController.selectCardFromHand(0, players.get(0).getID());
        gameController.turnSelectedCardSide(players.get(0).getID());

        gameController.selectCardFromHand(0, players.get(1).getID());

        gameController.selectCardFromHand(0, players.get(2).getID());
        gameController.turnSelectedCardSide(players.get(2).getID());

        // Each player plays the selected turn
        for (Player player : gameController.getGame().getPlayers()) {
            gameController.playCardFromHand(player.getID());
        }

        // Secret mission are automatically prepared
        gameController.prepareCommonMissions();

        gameController.selectSecretMission(0, players.get(0).getID());
        gameController.selectSecretMission(1, players.get(1).getID());
        gameController.selectSecretMission(1, players.get(2).getID());

        gameController.setSecretMission(players.get(0).getID());
        gameController.setSecretMission(players.get(1).getID());
        gameController.setSecretMission(players.get(2).getID());

        // Set manually current player's score
        gameController.getGame().getCurrentPlayer().getPersonalBoard().setScore(19);

        // Change current player
        gameController.changeTurn();

        // Set manually current player's score
        gameController.getGame().getCurrentPlayer().getPersonalBoard().setScore(23);

        // Change current player
        gameController.changeTurn();

        // Set manually current player's score
        gameController.getGame().getCurrentPlayer().getPersonalBoard().setScore(18);

        // Change game state into END_STAGE
        gameController.getGame().setGameState(GameState.END_STAGE);

        // Set the final round
        gameController.getGame().setFinalRound(gameController.getGame().getRound());

        // In theory, after this call, i have the winners
        gameController.changeTurn();

        // Check with assertions
        System.out.println(gameController.getGame().getWinners());
        assertFalse(gameController.getGame().getWinners().isEmpty());
    }
}