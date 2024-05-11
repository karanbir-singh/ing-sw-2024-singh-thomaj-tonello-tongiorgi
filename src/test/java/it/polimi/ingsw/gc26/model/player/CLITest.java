package it.polimi.ingsw.gc26.model.player;

import it.polimi.ingsw.gc26.controller.GameController;
import it.polimi.ingsw.gc26.Printer;
import it.polimi.ingsw.gc26.model.card.StarterCard;
import it.polimi.ingsw.gc26.model.game.Game;
import it.polimi.ingsw.gc26.model.game.GameState;
import it.polimi.ingsw.gc26.model.card_side.Symbol;
import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.deck.Deck;
import it.polimi.ingsw.gc26.model.card_side.Side;
import it.polimi.ingsw.gc26.model.game.CommonTable;
import it.polimi.ingsw.gc26.model.game.Game;
import it.polimi.ingsw.gc26.model.game.GameState;
import it.polimi.ingsw.gc26.model.hand.Hand;
import it.polimi.ingsw.gc26.network.ClientController;
import it.polimi.ingsw.gc26.network.RMI.VirtualRMIView;
import it.polimi.ingsw.gc26.network.VirtualView;
import org.junit.jupiter.api.Test;
import it.polimi.ingsw.gc26.Printer;

import java.nio.channels.Pipe;
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

        for (Player p: players) {
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

        assertEquals(GameState.SECRET_MISSION_DISTRIBUTION, game.getState());

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

        //assertEquals(GameState.WAITING_PAWNS_SELECTION, game.getState());

        gameController.choosePawnColor("BLUE", players.get(0).getID());
        gameController.choosePawnColor("GREEN", players.get(1).getID());
        gameController.choosePawnColor("YELLOW", players.get(2).getID());

        // Secret mission are automatically prepared
        gameController.prepareCommonMissions();

        gameController.prepareSecretMissions();

        //assertEquals(GameState.WAITING_SECRET_MISSION_CHOICE, game.getState());

        gameController.selectSecretMission(0, players.get(0).getID());
        gameController.selectSecretMission(1, players.get(1).getID());
        gameController.selectSecretMission(1, players.get(2).getID());

        gameController.setSecretMission(players.get(0).getID());
        gameController.setSecretMission(players.get(1).getID());
        gameController.setSecretMission(players.get(2).getID());

        for (Player p: players) {
            System.out.println("     " + p.getNickname() + "'S HAND:\n");
            printer.showPrintable(p.printableHandAndMission());
            System.out.println("\n");
        }

        //assertEquals(GameState.GAME_STARTED, game.getState());

        // Check with assertions
        for (Player player : game.getPlayers()) {
            assertNotNull(player.getSecretMissionHand());
            assertEquals(1, player.getSecretMissionHand().getCards().size());
            assertNotNull(player.getPersonalBoard().getSecretMission());
        }
    }


    @Test
    public void showHand(){
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

    @Test
    public void commonTableCLI() {
        Game game = new Game(new ArrayList<>(), new ArrayList<>());
        GameController gc = new GameController(game);

        game.setState(GameState.COMMON_TABLE_PREPARATION);
        gc.prepareCommonTable();
        game.setState(GameState.COMMON_MISSION_PREPARATION);
        gc.prepareCommonMissions();


        /*ArrayList<Player> players = new ArrayList<>();

        players.add(new Player("0", "Pippo"));
        players.add(new Player("1", "Baudo"));
        players.add(new Player("2", "Carlo"));
        players.add(new Player("4", "Giancarlo Peppino"));

        Game game = new Game(players);
        GameController gc = new GameController(game);

        game.setState(GameState.COMMON_TABLE_PREPARATION);
        gc.prepareCommonTable();
        game.setState(GameState.COMMON_MISSION_PREPARATION);
        gc.prepareCommonMissions();

        players.get(0).setPawn("BLUE", game.getAvailablePawns());
        players.get(1).setPawn("YELLOW", game.getAvailablePawns());
        players.get(2).setPawn("RED", game.getAvailablePawns());
        players.get(3).setPawn("GREEN", game.getAvailablePawns());

        for (Player p: players) {
            p.createPersonalBoard();
            p.getPersonalBoard().setScore(players.indexOf(p)*3 + 7);
        }

        game.showCommonTable();
        System.out.print("\n");*/
    }
/*
    @Test
    public void emptySideCLI() {
        Game game = new Game(new ArrayList<>());
        CommonTable ct = game.getCommonTable();
        String[][] empty = ct.emptyPrintable(3,3);

        for (int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                System.out.print(empty[i][j]);
            }
            System.out.print("\n");
        }
    }

    @Test
    public void scoreCLI() {
        Game game = new Game(new ArrayList<>());
        GameController gameController = new GameController(game);
        ArrayList<Player> players;
        String score;

        players = new ArrayList<>();
        players.add(new Player("0", "Pippo"));
        players.add(new Player("1", "Baudo"));
        players.add(new Player("2", "Carlo"));
        players.add(new Player("4", "Kevin"));

        players.get(0).setPawn("BLUE", game.getAvailablePawns());
        players.get(1).setPawn("YELLOW", game.getAvailablePawns());
        players.get(2).setPawn("RED", game.getAvailablePawns());
        players.get(3).setPawn("GREEN", game.getAvailablePawns());

        for (Player p: players) {
            p.createPersonalBoard();
            p.getPersonalBoard().setScore(players.indexOf(p)*3 + 7);
            score = p.printableScore();
            System.out.println(p.getNickname() + " " + score);
        }
    }

    @Test
    public void missionCardCLI(){
        Game game = new Game(new ArrayList<>());
        Deck missionDeck = game.getCommonTable().getMissionDeck();
        String[][] s;
        Printer printer = new Printer();

        for(int i=0; i<16; i++){
            s = missionDeck.getCards().get(i).getFront().printableSide();
            printer.showPrintable(s);
            System.out.println("\n");
        }

    }

    @Test
    void personalBoardCLI1() {
        Game game = new Game(new ArrayList<>());
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
