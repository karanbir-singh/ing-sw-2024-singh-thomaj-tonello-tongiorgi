package it.polimi.ingsw.gc26;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.game.Game;
import it.polimi.ingsw.gc26.model.player.Player;

import java.util.*;

public class MainController {
    private Map<String, GameController> gamesControllers;

    public MainController() {
        this.gamesControllers = new HashMap<>();
    }

    public void createGame(int numPlayers) throws Exception {
        if (numPlayers > 1 && numPlayers <= Game.MAX_NUM_PLAYERS) {
            String gameID = UUID.randomUUID().toString();
            gamesControllers.put(gameID, new GameController(new Game(numPlayers)));
        }
        // TODO gestire cosa fare in cui il numero di giocatori è negativo o maggiori di Game.MAX_NUM_PLAYERS
    }

    public void joinGame(String gameID, Player newPlayer) {
        Game game = gamesControllers.get(gameID).getGame();
        if (game.getPlayers().size() < gamesControllers.get(gameID).getGame().getNumberOfPlayers()) {
            game.addPlayer(newPlayer);
        } else {
            // TODO gestire in altro modo questo ramo
        }
    }

    public ArrayList<String> getGamesIDs() {
        return new ArrayList<>(gamesControllers.keySet());
    }

    public GameController getGameController(String gameID) {
        return gamesControllers.get(gameID);
    }

    public ArrayList<GameController> getGameControllers() {
        return new ArrayList<>(new HashSet<>(gamesControllers.values()));
    }

    public void deleteGame(String gameID) {
        gamesControllers.remove(gameID);
    }

    public static void main(String[] args) throws Exception {
        MainController mainController = new MainController();

        // Create a game and players
        mainController.createGame(2);
        Player player1 = new Player(0, "Player 1");
        Player player2 = new Player(1, "Player 2");

        // Add players
        mainController.joinGame(mainController.getGamesIDs().getFirst(), player1);
        mainController.joinGame(mainController.getGamesIDs().getFirst(), player2);

        // PHASE 1: Game preparation
        // Get controller
        GameController gameController = mainController.getGameController(mainController.getGamesIDs().getFirst());
        // Set first player
        gameController.setFirstPlayer(player1.getID());
        Player currentPlayer = gameController.getGame().getCurrentPlayer();

        // Prepare game common table
        gameController.prepareCommonTable();

        // Give 1 starter card to each player
        gameController.prepareStarterCards();

        /* Players choose which side of starter card to play */
        // Player 1
        Scanner scanner = new Scanner(System.in);

        System.out.println(currentPlayer.getNickname());
        gameController.selectCardFromHand(0, currentPlayer.getID());
        System.out.println("Do you want to change turn? y/n");
        String turnSideDecision = scanner.nextLine();
        if (turnSideDecision.equals("yes") || turnSideDecision.equals("y")) {
            gameController.turnSelectedCardSide(currentPlayer.getID());
            System.out.println("side changed");
        }
        gameController.playCardFromHand(currentPlayer.getID());

        gameController.changeTurn();
        currentPlayer = gameController.getGame().getCurrentPlayer();

        System.out.println(currentPlayer.getNickname());
        // Player 2
        gameController.selectCardFromHand(0, currentPlayer.getID());
        System.out.println("Do you want to change turn? y/n");
        turnSideDecision = scanner.nextLine();
        if (turnSideDecision.equals("yes") || turnSideDecision.equals("y")) {
            gameController.turnSelectedCardSide(currentPlayer.getID());
            System.out.println("side changed");
        }
        gameController.playCardFromHand(currentPlayer.getID());

        // Prepare players hand with 2 resource cards and 1 gold card
        gameController.preparePlayersHand();

        // Prepare common table
        gameController.prepareCommonMissions();

        // Give 2 secret missions to each player
        gameController.prepareSecretMissions();

        gameController.changeTurn();
        currentPlayer = gameController.getGame().getCurrentPlayer();
        /* Players choose their secret mission */
        // Player 1
        System.out.println(currentPlayer.getNickname());
        System.out.println("Insert mission card index: ");
        int missionCardIndex = Integer.parseInt(scanner.nextLine());
        gameController.selectSecretMission(missionCardIndex, currentPlayer.getID());
        gameController.setSecretMission(currentPlayer.getID());

        gameController.changeTurn();
        currentPlayer = gameController.getGame().getCurrentPlayer();

        System.out.println(currentPlayer.getNickname());
        // Player 2
        System.out.println("Insert mission card index: ");
        missionCardIndex = Integer.parseInt(scanner.nextLine());
        gameController.selectSecretMission(missionCardIndex, currentPlayer.getID());
        gameController.setSecretMission(currentPlayer.getID());

        // Set first player
        gameController.setFirstPlayer(player1.getID());
        currentPlayer = gameController.getGame().getCurrentPlayer();
        // PHASE 2: Game flow
        while (true) {
            System.out.println("----" + currentPlayer.getNickname() +"'s turn----");
            currentPlayer.getPersonalBoard().showBoard();

            System.out.println("Insert the card index you want to select:");
            int cardIndex = Integer.parseInt(scanner.nextLine());

            gameController.selectCardFromHand(cardIndex, currentPlayer.getID());

            System.out.println("Do you want to change side? y/n");
            turnSideDecision = scanner.nextLine();

            if (turnSideDecision.equals("yes") || turnSideDecision.equals("y")) {
                gameController.turnSelectedCardSide(currentPlayer.getID());
                System.out.println("side changed");
            }

            System.out.println("Choose the position on the board where you want to place the card:");
            System.out.print("X: ");
            int selectedX = Integer.parseInt(scanner.nextLine());
            System.out.print("Y: ");
            int selectedY = Integer.parseInt(scanner.nextLine());
            gameController.selectPositionOnBoard(selectedX, selectedY, currentPlayer.getID());
            gameController.playCardFromHand(currentPlayer.getID());

            // Pescaggio
            System.out.println("Insert the position of the card you want to draw");
            System.out.print("X: ");
            int cardX = Integer.parseInt(scanner.nextLine());
            System.out.print("Y: ");
            int cardY = Integer.parseInt(scanner.nextLine());
            gameController.selectCardFromCommonTable(cardX, cardY, currentPlayer.getID());
            gameController.drawSelectedCard(currentPlayer.getID());

            // Cambio turno
            gameController.changeTurn();

            currentPlayer = gameController.getGame().getCurrentPlayer();
        }

        // PHASE 3: End Game
    }
}
