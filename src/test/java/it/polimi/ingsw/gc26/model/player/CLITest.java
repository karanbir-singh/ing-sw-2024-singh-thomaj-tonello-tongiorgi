package it.polimi.ingsw.gc26.model.player;

import it.polimi.ingsw.gc26.controller.GameController;
import it.polimi.ingsw.gc26.client.Printer;
import it.polimi.ingsw.gc26.model.game.Game;
import it.polimi.ingsw.gc26.model.game.GameState;
import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.deck.Deck;
import it.polimi.ingsw.gc26.model.hand.Hand;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class CLITest {
    private static GameController gameController;

    private static Game game;

    private static ArrayList<Player> players;

    private static final Printer printer = new Printer();

    static void gameSetUp() throws RemoteException {
        // Create players
        players = new ArrayList<>();
        players.add(new Player("0", "Pippo"));
        players.add(new Player("1", "Baudo"));
        players.add(new Player("2", "Carlo"));

        // Create game controller
        game = new Game(players, new ArrayList<>());
        gameController = new GameController(game);
        gameController.prepareCommonTable();

    }

    @Test
    void starterCardDistribution() throws RemoteException {
        gameSetUp();
        game.setState(GameState.STARTER_CARDS_DISTRIBUTION);
        gameController.prepareStarterCards();

        for (Player p : players) {
            System.out.println("     " + p.getNickname() + "'S HAND:\n");
            printer.showPrintable(p.getHand().printableHand());
            System.out.println("\n");
        }
    }

    @Test
    void starterCardPlacement() throws RemoteException {
        gameSetUp();
        game.setState(GameState.STARTER_CARDS_DISTRIBUTION);
        gameController.prepareStarterCards();

        // Each player select (and eventually turn) the starter card side
        gameController.turnSelectedCardSide(players.get(0).getID());

        gameController.turnSelectedCardSide(players.get(2).getID());

        // Each player plays the selected starter card
        for (Player player : game.getPlayers()) {
            gameController.playCardFromHand(player.getID());
            System.out.println("\n     " + player.getNickname() + "'S PERSONAL BOARD:\n");
            printer.showPrintable(player.getPersonalBoard().printablePersonalBoard());
        }
    }

    @Test
    void commonMissionsPreparation() throws RemoteException {
        gameSetUp();

        game.setState(GameState.COMMON_MISSION_PREPARATION);
        gameController.prepareCommonMissions();

        printer.showPrintable(game.getCommonTable().printableCommonTable());

        // Check with assertions
        assertEquals(2, game.getCommonTable().getCommonMissions().size());
    }

    @Test
    void secretMissionsSetting() throws RemoteException {
        gameSetUp();
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

        game.setState(GameState.WAITING_PAWNS_SELECTION);

        gameController.choosePawnColor("BLUE", players.get(0).getID());
        gameController.choosePawnColor("GREEN", players.get(1).getID());
        gameController.choosePawnColor("YELLOW", players.get(2).getID());

        // Secret mission are automatically prepared
        gameController.prepareCommonMissions();

        game.setState(GameState.SECRET_MISSION_DISTRIBUTION);

        gameController.prepareSecretMissions();

        assertEquals(GameState.WAITING_SECRET_MISSION_CHOICE, game.getState());

        gameController.selectSecretMission(0, players.get(0).getID());
        gameController.selectSecretMission(1, players.get(1).getID());
        gameController.selectSecretMission(1, players.get(2).getID());

        gameController.setSecretMission(players.get(0).getID());
        gameController.setSecretMission(players.get(1).getID());
        gameController.setSecretMission(players.get(2).getID());

        for (Player p : players) {
            System.out.println("     " + p.getNickname() + "'S HAND:\n");
            printer.showPrintable(p.printableHandAndMission());
            System.out.println("\n");
        }

        // Check with assertions
        for (Player player : game.getPlayers()) {
            assertNotNull(player.getSecretMissionHand());
            assertEquals(1, player.getSecretMissionHand().getCards().size());
            assertNotNull(player.getPersonalBoard().getSecretMission());
        }
    }

    @Test
    void fullGamePrint() throws RemoteException {
        gameSetUp();
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


        for (Player p : players) {
            printer.showPrintable(game.printableGame(p));
        }

        game.setState(GameState.WAITING_PAWNS_SELECTION);

        gameController.choosePawnColor("BLUE", players.get(0).getID());
        gameController.choosePawnColor("GREEN", players.get(1).getID());
        gameController.choosePawnColor("YELLOW", players.get(2).getID());

        // Secret mission are automatically prepared
        gameController.prepareCommonMissions();

        game.setState(GameState.SECRET_MISSION_DISTRIBUTION);

        gameController.prepareSecretMissions();

        assertEquals(GameState.WAITING_SECRET_MISSION_CHOICE, game.getState());

        gameController.selectSecretMission(0, players.get(0).getID());
        gameController.selectSecretMission(1, players.get(1).getID());
        gameController.selectSecretMission(1, players.get(2).getID());

        gameController.setSecretMission(players.get(0).getID());
        gameController.setSecretMission(players.get(1).getID());
        gameController.setSecretMission(players.get(2).getID());


        for (Player p : players) {
            printer.showPrintable(game.printableGame(p));
        }

        // Check with assertions
        for (Player player : game.getPlayers()) {
            assertNotNull(player.getSecretMissionHand());
            assertEquals(1, player.getSecretMissionHand().getCards().size());
            assertNotNull(player.getPersonalBoard().getSecretMission());
        }
    }

    @Test
    void tryPlayingTwoCardsInARow() throws RemoteException {
        gameSetUp();

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

        for (Player p : players) {
            printer.showPrintable(game.printableGame(p));
        }

        // Check that the second card it's not played
        assertEquals(2, currentPlayer.getHand().getCards().size());
        assertEquals(PlayerState.CARD_PLAYED, currentPlayer.getState());
        assertEquals(2, currentPlayer.getPersonalBoard().getOccupiedPositions().size());
    }


    @Test
    public void showHand() {
        Game game = new Game(new ArrayList<>(), new ArrayList<>());
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        ArrayList<Card> cards = new ArrayList<>();
        Hand myHand = new Hand(cards);

        cards.add(resourceDeck.getCards().get(7));
        cards.add(goldDeck.getCards().get(4));
        cards.add(goldDeck.getCards().get(10));

        printer.showPrintable(myHand.printableHand());

    }

    /*@Test
    void personalBoardCLI1() {
        Game game = new Game(new ArrayList<>(), new ArrayList<>());
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getStarterDeck();
        PersonalBoard pb = new PersonalBoard();
        pb.setPosition(0,0);
        pb.playSide(initialDeck.getCards().get(0).getFront());
        pb.setPosition(-1, -1);
        pb.playSide(resourceDeck.getCards().get(10).getBack());
        pb.setPosition(1, -1);
        pb.playSide(resourceDeck.getCards().get(0).getBack());
        pb.setPosition(-1, 1);
        pb.playSide(resourceDeck.getCards().get(20).getBack());
        pb.setPosition(1, 1);
        pb.playSide(resourceDeck.getCards().get(39).getBack());
        pb.setPosition(-2, -2);
        pb.playSide(resourceDeck.getCards().get(10).getFront());
        pb.setPosition(2, -2);
        pb.playSide(resourceDeck.getCards().get(0).getFront());
        pb.setPosition(-2, 2);
        pb.playSide(resourceDeck.getCards().get(20).getFront());
        pb.setPosition(2, 2);
        pb.playSide(resourceDeck.getCards().get(39).getFront());

        pb.showBoard();
    }

    @Test
    void personalBoardCLI2() {
        Game game = new Game(new ArrayList<>());
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getStarterDeck();
        PersonalBoard pb = new PersonalBoard();
        pb.setPosition(0,0);
        pb.playSide(initialDeck.getCards().get(0).getFront());
        pb.setPosition(-1, -1);
        pb.playSide(resourceDeck.getCards().get(38).getFront());
        pb.setPosition(1, -1);
        pb.playSide(resourceDeck.getCards().get(0).getFront());

        pb.showBoard();
        System.out.print("\n");

    }*/

}
