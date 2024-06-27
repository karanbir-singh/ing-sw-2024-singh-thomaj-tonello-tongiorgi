package it.polimi.ingsw.gc26.controller;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.game.Game;
import it.polimi.ingsw.gc26.model.game.GameState;
import it.polimi.ingsw.gc26.model.player.Pawn;
import it.polimi.ingsw.gc26.model.player.Player;
import it.polimi.ingsw.gc26.model.player.PlayerState;
import it.polimi.ingsw.gc26.request.game_request.ChoosePawnColorRequest;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

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
        game = new Game(players, new ArrayList<>());

        try {
            gameController = new GameController(game, -1);
            gameController.setDebug(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    void pawnColorChoosing() {
        beforeAll();

        starterCardPlacement();

        assertEquals(GameState.WAITING_PAWNS_SELECTION, game.getState());
        gameController.choosePawnColor("RED", players.get(0).getID());
        assertEquals("RED", players.get(0).getPawnColor().toString());
        assertEquals(3, gameController.getGame().getAvailablePawns().size());

        for (Pawn pawn : gameController.getGame().getAvailablePawns()) {
            assertNotEquals(pawn, players.get(0).getPawnColor());
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
        gameController.selectCardFromCommonTable(0, currentPlayer.getID());
        gameController.drawSelectedCard(currentPlayer.getID());

        // Now try to draw again
        gameController.selectCardFromCommonTable(1, currentPlayer.getID());
        gameController.drawSelectedCard(currentPlayer.getID());

        assertEquals(3, currentPlayer.getHand().getCards().size());
        assertFalse(game.getCommonTable().getSelectedCard().isPresent());
        assertEquals(31, game.getCommonTable().getResourceDeck().getCards().size());
        assertEquals(2, game.getCommonTable().getResourceCards().size());
    }

    @Test
    void containsOnlyNull() {
        beforeAll();

        ArrayList<Card> cards = new ArrayList<>();
        cards.add(null);
        cards.add(null);

        assertTrue(gameController.containsOnlyNull(cards));
    }

    @Test
    void emptyCommonTable() {
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


        // empty common table
        game.getCommonTable().getResourceDeck().getCards().clear();
        game.getCommonTable().getGoldDeck().getCards().clear();
        game.getCommonTable().getResourceCards().set(1, null);
        game.getCommonTable().getGoldCards().set(0, null);
        game.getCommonTable().getGoldCards().set(1, null);

        gameController.selectCardFromCommonTable(0, currentPlayer.getID());
        gameController.drawSelectedCard(currentPlayer.getID());

        assertEquals(GameState.WINNER, game.getState());
    }

    @Test
    void broadcastMessage() {
        beforeAll();

        Player sender = players.getFirst();

        String receveirName = "";
        gameController.addMessage("This is a broadcast message!", receveirName, sender.getID(), null);

        assertEquals(1, gameController.getGame().getChat().getMessages().size());
        assertEquals("This is a broadcast message!", gameController.getGame().getChat().getMessages().get(0).getText());
        assertEquals(sender, gameController.getGame().getChat().getMessages().get(0).getSender());
        assertNull(gameController.getGame().getChat().getMessages().get(0).getReceiver());
    }

    @Test
    void directMessage() {
        beforeAll();

        Player sender = players.getFirst();

        String receveirName = players.get(1).getNickname();
        gameController.addMessage("This is a direct message!", receveirName, sender.getID(), null);

        assertEquals(1, gameController.getGame().getChat().getMessages().size());
        assertEquals("This is a direct message!", gameController.getGame().getChat().getMessages().get(0).getText());
        assertEquals(sender, gameController.getGame().getChat().getMessages().get(0).getSender());
        assertEquals(players.get(1), gameController.getGame().getChat().getMessages().get(0).getReceiver());
    }

    @Test
    void reAddView() {
        beforeAll();
        gameController.reAddView(null, players.get(0).getID());

        assertEquals(1, gameController.getGame().getObservable().getClients().size());
    }

    @Test
    void backupFileExistence() {
        beforeAll();

        gameController.backup();

        File file = new File(GameController.GAME_CONTROLLER_FILE_PATH + "-1.bin");
        assertTrue(file.exists());
    }

    @Test
    void backupCheck() {
        beforeAll();

        gameController.backup();

        try {
            FileInputStream fileInputStream = new FileInputStream(GameController.GAME_CONTROLLER_FILE_PATH + "-1.bin");
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);

            GameController fromBackup = (GameController) inputStream.readObject();

            assertNotNull(fromBackup);
            assertNotNull(fromBackup.getGame());
            for (int i = 0; i < players.size(); i++) {
                assertEquals(players.get(i).getID(), fromBackup.getGame().getPlayers().get(i).getID());
                assertEquals(players.get(i).getNickname(), fromBackup.getGame().getPlayers().get(i).getNickname());
            }

        } catch (IOException | ClassNotFoundException e) {
        }
    }

    @Test
    void checkRequests() {
        beforeAll();

        gameController.addRequest(new ChoosePawnColorRequest("RED", "0"));
        assertEquals(1, gameController.getGameRequests().size());
    }
}