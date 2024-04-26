package it.polimi.ingsw.gc26;

import it.polimi.ingsw.gc26.controller.GameController;
import it.polimi.ingsw.gc26.model.card.StarterCard;
import it.polimi.ingsw.gc26.model.game.Game;
import it.polimi.ingsw.gc26.model.game.GameState;
import it.polimi.ingsw.gc26.model.player.Player;
import it.polimi.ingsw.gc26.model.player.PlayerState;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class GameControllerTest {
    private static GameController gameController;

    private static Game game;

    private static ArrayList<Player> players;

    static void beforeAll() {
        // Create players
        players = new ArrayList<>();
        players.add(new Player("0", "Pippo"));
        players.add(new Player("1", "Baudo"));
        players.add(new Player("2", "Carlo"));

        // Create game controller
        game = new Game(players);
        gameController = new GameController(game);
    }

    @Test
    void commonTablePreparation() {
        beforeAll();
        gameController.prepareCommonTable();
        assertEquals(GameState.WAITING_STARTER_CARD_PLACEMENT, game.getState());

        // Check with assertions
        assertFalse(game.getCommonTable().getResourceCards().isEmpty());
        assertFalse(game.getCommonTable().getGoldCards().isEmpty());
        assertEquals(2, game.getCommonTable().getResourceCards().size());
        assertEquals(2, game.getCommonTable().getGoldCards().size());
        assertEquals(38, game.getCommonTable().getResourceDeck().getCards().size());
        assertEquals(38, game.getCommonTable().getGoldDeck().getCards().size());
    }

    @Test
    void starterCardsPreparation() {
        beforeAll();
        game.setState(GameState.STARTER_CARDS_DISTRIBUTION);
        gameController.prepareStarterCards();
        assertEquals(GameState.WAITING_STARTER_CARD_PLACEMENT, game.getState());

        // Check with assertions
        for (Player player : game.getPlayers()) {
            assertNotNull(player.getHand());
            assertEquals(1, player.getHand().getCards().size());
        }
        assertEquals(6 - players.size(), game.getCommonTable().getStarterDeck().getCards().size());
    }

    @Test
    void starterCardPlacement() {
        beforeAll();
        game.setState(GameState.STARTER_CARDS_DISTRIBUTION);
        gameController.prepareStarterCards();
        assertEquals(GameState.WAITING_STARTER_CARD_PLACEMENT, game.getState());

        // Each player select (and eventually turn) the starter card side
        gameController.turnSelectedCardSide(players.get(0).getID());

        gameController.turnSelectedCardSide(players.get(2).getID());

        // Each player plays the selected starter card
        for (Player player : game.getPlayers()) {
            gameController.playCardFromHand(player.getID());
        }

        assertEquals(GameState.WAITING_PAWNS_SELECTION, game.getState());

        // Check with assertions
        for (Player player : game.getPlayers()) {
            assertNotNull(player.getPersonalBoard());
            assertEquals(0, player.getHand().getCards().size());
            assertEquals(0, player.getPersonalBoard().getScore());
            assertEquals(1, player.getPersonalBoard().getOccupiedPositions().size());
        }
    }

    @Test
    void commonMissionsPreparation() {
        beforeAll();
        // Call the tested method
        game.setState(GameState.COMMON_MISSION_PREPARATION);
        gameController.prepareCommonMissions();

        assertEquals(GameState.WAITING_SECRET_MISSION_CHOICE, game.getState());

        // Check with assertions
        assertEquals(2, game.getCommonTable().getCommonMissions().size());
    }

    @Test
    void secretMissionsSetting() {
        beforeAll();
        // First of all, each player has their starter card placed
        game.setState(GameState.STARTER_CARDS_DISTRIBUTION);
        gameController.prepareStarterCards();
        assertEquals(GameState.WAITING_STARTER_CARD_PLACEMENT, game.getState());

        // Each player select (and eventually turn) the starter card side
        gameController.turnSelectedCardSide(players.get(0).getID());

        gameController.turnSelectedCardSide(players.get(2).getID());

        // Each player plays the selected turn
        for (Player player : game.getPlayers()) {
            gameController.playCardFromHand(player.getID());
        }

        assertEquals(GameState.WAITING_PAWNS_SELECTION, game.getState());

        gameController.choosePawnColor("BLUE", players.get(0).getID());
        gameController.choosePawnColor("GREEN", players.get(1).getID());
        gameController.choosePawnColor("YELLOW", players.get(2).getID());

        // Secret mission are automatically prepared
        gameController.prepareCommonMissions();

        assertEquals(GameState.WAITING_SECRET_MISSION_CHOICE, game.getState());

        gameController.selectSecretMission(0, players.get(0).getID());
        gameController.selectSecretMission(1, players.get(1).getID());
        gameController.selectSecretMission(1, players.get(2).getID());

        gameController.setSecretMission(players.get(0).getID());
        gameController.setSecretMission(players.get(1).getID());
        gameController.setSecretMission(players.get(2).getID());

        assertEquals(GameState.GAME_STARTED, game.getState());

        // Check with assertions
        for (Player player : game.getPlayers()) {
            assertNotNull(player.getSecretMissionHand());
            assertEquals(1, player.getSecretMissionHand().getCards().size());
            assertNotNull(player.getPersonalBoard().getSecretMission());
        }
    }

    @Test
    void getWinners() {
        beforeAll();
        // First of all, each player has their starter card placed
        game.setState(GameState.STARTER_CARDS_DISTRIBUTION);
        gameController.prepareStarterCards();
        assertEquals(GameState.WAITING_STARTER_CARD_PLACEMENT, game.getState());

        // Each player select (and eventually turn) the starter card side
        gameController.turnSelectedCardSide(players.get(0).getID());

        gameController.turnSelectedCardSide(players.get(2).getID());

        // Each player plays the selected turn
        for (Player player : game.getPlayers()) {
            gameController.playCardFromHand(player.getID());
        }

        assertEquals(GameState.WAITING_PAWNS_SELECTION, game.getState());

        gameController.choosePawnColor("BLUE", players.get(0).getID());
        gameController.choosePawnColor("GREEN", players.get(1).getID());
        gameController.choosePawnColor("YELLOW", players.get(2).getID());

        // Secret mission are automatically prepared
        gameController.prepareCommonMissions();

        assertEquals(GameState.WAITING_SECRET_MISSION_CHOICE, game.getState());

        gameController.selectSecretMission(0, players.get(0).getID());
        gameController.selectSecretMission(1, players.get(1).getID());
        gameController.selectSecretMission(1, players.get(2).getID());

        gameController.setSecretMission(players.get(0).getID());
        gameController.setSecretMission(players.get(1).getID());
        gameController.setSecretMission(players.get(2).getID());

        assertEquals(GameState.GAME_STARTED, game.getState());

        // Check with assertions
        for (Player player : game.getPlayers()) {
            assertNotNull(player.getSecretMissionHand());
            assertEquals(1, player.getSecretMissionHand().getCards().size());
            assertNotNull(player.getPersonalBoard().getSecretMission());
        }

        // Set manually current player's score
        game.getCurrentPlayer().getPersonalBoard().setScore(19);

        // Change current player
        gameController.changeTurn();

        // Set manually current player's score
        game.getCurrentPlayer().getPersonalBoard().setScore(23);

        // Change current player
        gameController.changeTurn();

        // Set manually current player's score
        game.getCurrentPlayer().getPersonalBoard().setScore(18);

        // Change game state into END_STAGE
        game.setState(GameState.END_STAGE);

        // Set the final round
        game.setFinalRound(game.getRound());

        gameController.changeTurn();

        // Check with assertions
        assertFalse(game.getWinners().isEmpty());
    }

    @Test
    void tryPlayingTwoCardsInARow() {
        beforeAll();

        // Prepare initial things
        gameController.prepareCommonTable();
        gameController.turnSelectedCardSide(players.get(0).getID());
        gameController.turnSelectedCardSide(players.get(2).getID());
        for (Player player : game.getPlayers()) {
            gameController.playCardFromHand(player.getID());
        }
        gameController.choosePawnColor("BLUE", players.get(1).getID());
        gameController.choosePawnColor("GREEN", players.get(0).getID());
        gameController.choosePawnColor("YELLOW", players.get(2).getID());

        gameController.selectSecretMission(0, players.get(0).getID());

        gameController.selectSecretMission(1, players.get(2).getID());
        gameController.setSecretMission(players.get(2).getID());

        gameController.selectSecretMission(0, players.get(1).getID());

        gameController.setSecretMission(players.get(1).getID());
        gameController.setSecretMission(players.get(0).getID());

        Player currentPlayer = game.getCurrentPlayer();

        gameController.selectCardFromHand(1, currentPlayer.getID());
        gameController.turnSelectedCardSide(currentPlayer.getID());
        gameController.selectPositionOnBoard(1, 1, currentPlayer.getID());
        gameController.playCardFromHand(currentPlayer.getID());

        // Now we try to play a second card, even if the current play can't do these
        gameController.selectCardFromHand(0, currentPlayer.getID());
        gameController.turnSelectedCardSide(currentPlayer.getID());
        gameController.selectPositionOnBoard(2, 2, currentPlayer.getID());
        gameController.playCardFromHand(currentPlayer.getID());

        // Check that the second card it's not played
        assertEquals(2, currentPlayer.getHand().getCards().size());
        assertEquals(PlayerState.CARD_PLAYED, currentPlayer.getState());
        assertEquals(2, currentPlayer.getPersonalBoard().getOccupiedPositions().size());
    }

    @Test
    void tryDrawingTwoCardsInARow() {
        beforeAll();

        // Prepare initial things
        gameController.prepareCommonTable();
        gameController.turnSelectedCardSide(players.get(0).getID());
        gameController.turnSelectedCardSide(players.get(2).getID());
        for (Player player : game.getPlayers()) {
            gameController.playCardFromHand(player.getID());
        }
        gameController.choosePawnColor("BLUE", players.get(1).getID());
        gameController.choosePawnColor("GREEN", players.get(0).getID());
        gameController.choosePawnColor("YELLOW", players.get(2).getID());

        gameController.selectSecretMission(0, players.get(0).getID());

        gameController.selectSecretMission(1, players.get(2).getID());
        gameController.setSecretMission(players.get(2).getID());

        gameController.selectSecretMission(0, players.get(1).getID());

        gameController.setSecretMission(players.get(1).getID());
        gameController.setSecretMission(players.get(0).getID());

        Player currentPlayer = game.getCurrentPlayer();

        gameController.selectCardFromHand(1, currentPlayer.getID());
        gameController.turnSelectedCardSide(currentPlayer.getID());
        gameController.selectPositionOnBoard(1, 1, currentPlayer.getID());
        gameController.playCardFromHand(currentPlayer.getID());
        gameController.selectCardFromCommonTable(0, 0, currentPlayer.getID());
        gameController.drawSelectedCard(currentPlayer.getID());

        // Now try to draw again
        gameController.selectCardFromCommonTable(1, 0, currentPlayer.getID());
        gameController.drawSelectedCard(currentPlayer.getID());

        assertEquals(3, currentPlayer.getHand().getCards().size());
        assertFalse(game.getCommonTable().getSelectedCard().isPresent());
        assertEquals(31, game.getCommonTable().getResourceDeck().getCards().size());
        assertEquals(2, game.getCommonTable().getResourceCards().size());
    }
}