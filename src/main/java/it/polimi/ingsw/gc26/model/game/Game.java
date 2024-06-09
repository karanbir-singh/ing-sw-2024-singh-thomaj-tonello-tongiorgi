package it.polimi.ingsw.gc26.model.game;

import it.polimi.ingsw.gc26.Printer;
import it.polimi.ingsw.gc26.model.ModelObservable;
import it.polimi.ingsw.gc26.model.deck.Deck;
import it.polimi.ingsw.gc26.model.player.Pawn;
import it.polimi.ingsw.gc26.model.player.Player;
import it.polimi.ingsw.gc26.parser.ParserCore;
import it.polimi.ingsw.gc26.model.player.PlayerState;
import java.io.Serializable;
import it.polimi.ingsw.gc26.network.VirtualView;
import it.polimi.ingsw.gc26.view_model.SimplifiedCommonTable;
import it.polimi.ingsw.gc26.view_model.SimplifiedGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * This class represents an entire Game play. It has a minimum number of player of two and a maximum number of player of four.
 * To play more than one game, more instances of game have to be created
 */
public class Game implements Serializable {
    /**
     * This attribute represents the maximum number of players per game
     */
    public static final int MAX_NUM_PLAYERS = 4;
    /**
     * This attribute represents the actual number of players
     */
    private final int numberOfPlayers;
    /**
     * This attribute represents the current game's state
     */
    private GameState gameState;
    /**
     * This attribute represents the current player
     */
    private Player currentPlayer;
    /**
     * THis attribute represents all the players in the game
     */
    private ArrayList<Player> players;
    /**
     * This attribute represents the common table to all the players
     */
    private CommonTable commonTable;
    /**
     * This attribute represents how many rounds have been played
     */
    private int round;
    /**
     * This attribute represents the final round of the game
     */
    private int finalRound;
    /**
     * This attribute represents the winners of the game
     */
    private ArrayList<Player> winners;
    /**
     * This attribute represents the chat. It stores all the messages.
     */
    private final Chat chat;
    /**
     * This attribute represents the available pawns in the game
     */
    private final ArrayList<Pawn> availablePawns;

    private  ModelObservable observable; //unico per il game

    /**
     * Initializes the game, creates the decks and sets the common table
     *
     * @param players list of players of the game
     */
    public Game(ArrayList<Player> players, ArrayList<VirtualView> clients) {
        this.numberOfPlayers = players.size();

        this.players = new ArrayList<>();
        this.players.addAll(players);
        this.winners = new ArrayList<>();

        this.observable = new ModelObservable();
        for (int i = 0; i < clients.size(); i++) {
            this.observable.addObserver(clients.get(i), players.get(i).getID());
        }
        for(Player player: players) {
            player.setObservable(this.observable);
        }

        ParserCore p = new ParserCore("src/main/resources/Data/CodexNaturalisCards.json");
        Deck goldCardDeck = p.getGoldCards();
        Deck resourceCardDeck = p.getResourceCards();
        Deck missionDeck = p.getMissionCards();
        Deck starterDeck = p.getStarterCards();

        this.commonTable = new CommonTable(resourceCardDeck, goldCardDeck, starterDeck, missionDeck, this.observable);
        this.round = 0;
        this.finalRound = -1;
        this.chat = new Chat(this.observable);

        availablePawns = new ArrayList<>();
        availablePawns.add(Pawn.BLUE);
        availablePawns.add(Pawn.RED);
        availablePawns.add(Pawn.YELLOW);
        availablePawns.add(Pawn.GREEN);


    }

    /**
     * Returns the player associated to the playerID
     *
     * @param playerID ID of the searched player
     */
    public Player getPlayerByID(String playerID) {
        return players.stream().filter((Player p) -> p.getID().equals(playerID)).findAny().get();
    }

    /**
     * Returns the player associated to the playerNickname
     *
     * @param playerNickname Nickname of the searched player
     */
    public Player getPlayerByNickname(String playerNickname) {
        if (playerNickname.equals("")) {
            return null;
        }
        try {
            return players.stream().filter((Player p) -> p.getNickname().equals(playerNickname)).findAny().get();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Adds a player in the game
     *
     * @param newPlayer new player to be added in the game
     */
    public void addPlayer(Player newPlayer) {
        if (this.players.size() < numberOfPlayers) {
            this.players.add(newPlayer);
        }
        if (this.players.size() == numberOfPlayers) {
            gameState = GameState.COMMON_TABLE_PREPARATION;
        }
    }

    /**
     * Returns the next player
     */
    public Player getNextPlayer() {
        return players.get((players.indexOf(this.currentPlayer) + 1) % this.numberOfPlayers);
    }

    /**
     * Sets the currentPlayer to the next one in an infinite cycle
     */
    public void goToNextPlayer() {
        // Check if game is on END_STAGE state, if the round it's the final round and if the next player is the first
        if (this.gameState.equals(GameState.END_STAGE) && this.round == this.finalRound &&
                getNextPlayer().isFirstPlayer()) {

            // Call end game to update score (valuate missions)
            players.forEach(player -> player.getPersonalBoard().endGame(commonTable.getCommonMissions()));

            // Calculate the max score
            int maxScore = players.stream()
                    .mapToInt(player -> player.getPersonalBoard().getScore())
                    .max()
                    .orElse(-1);

            // Get winners of the game
            winners = players.stream()
                    .filter(player -> player.getPersonalBoard().getScore() == (maxScore % 29))
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        // Change current player
        this.currentPlayer = getNextPlayer();

        // Change player's state
        this.currentPlayer.setState(PlayerState.PLAYING, currentPlayer.getID());

        HashMap<String,Integer> points = new HashMap<>();
        for(Player player : this.players){
            if(player.getPersonalBoard() != null){
                points.put(player.getNickname(),player.getPersonalBoard().getScore());
            }else{
                points.put(player.getNickname(),0);
            }

        }

        // Check if the next current player is the first player
        if (this.currentPlayer.isFirstPlayer()) {
            // Then increase the round
            this.increaseRound();
        }
        this.observable.notifyMessage("It's you turn now",this.currentPlayer.getID());
        ArrayList<String> nicknameWinners = new ArrayList<>();
        for(Player winner : this.winners){
            nicknameWinners.add(winner.getNickname());
        }
        String currentPlayerNickname = null;
        if(this.currentPlayer != null){
            currentPlayerNickname = this.currentPlayer.getNickname();
        }

        HashMap<String, Pawn> pawnsSelected = new HashMap<>();
        for (Player player : this.players) {
            pawnsSelected.put(player.getNickname(), player.getPawnColor());
        }
        String message = "Current player has changed!";
        this.observable.notifyUpdateGame(new SimplifiedGame(gameState, currentPlayerNickname, points, nicknameWinners, availablePawns, pawnsSelected), message);
        // TODO update simplified Game & player
    }

    /**
     * Sets the current Player to the parameter given
     *
     * @param currentPlayer new current player
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Returns the current player
     *
     * @return current player
     */
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     * Return the common table for every player
     *
     * @return common table
     */
    public CommonTable getCommonTable() {
        return this.commonTable;
    }

    /**
     * Increases number of rounds
     */
    public void increaseRound() {
        this.round += 1;
    }

    /**
     * Return the current round
     *
     * @return round
     */
    public int getRound() {
        return this.round;
    }

    /**
     * Returns the number of player for the game
     *
     * @return number of player
     */
    public int getNumberOfPlayers() {
        return this.numberOfPlayers;
    }


    /**
     * Returns game state
     *
     * @return gameState
     */
    public GameState getState() {
        return gameState;
    }

    /**
     * Sets the game state to the parameter given
     *
     * @param newGameState new game state
     */
    public void setState(GameState newGameState) {
        this.gameState = newGameState;

        String message = null;

        switch (newGameState) {
            case COMMON_TABLE_PREPARATION:
                message = "Common Table Preparation...";
                break;
            case STARTER_CARDS_DISTRIBUTION:
                message = "Starter Cards Distribution...";
                this.observable.notifyUpdateCommonTable(
                        new SimplifiedCommonTable(
                                commonTable.getResourceDeck().getTopCard(),
                                commonTable.getGoldDeck().getTopCard(),
                                commonTable.getCommonMissions(),
                                commonTable.getResourceCards(),
                                commonTable.getGoldCards(),
                                6),
                        "Card added from common table"
                );
                break;
            case WAITING_STARTER_CARD_PLACEMENT:
                message = "Waiting players for placing starter card...";
                break;
            case WAITING_PAWNS_SELECTION:
                message = "Waiting players for selecting pawns...\n" + getAvailablePawns();
                break;
            case HAND_PREPARATION:
                message = "Prepare players hand...";
                break;
            case COMMON_MISSION_PREPARATION:
                message = "Common Mission Preparation...";
                break;
            case SECRET_MISSION_DISTRIBUTION:
                message = "Secret Mission Distribution...";
                break;
            case WAITING_SECRET_MISSION_CHOICE:
                message = "Waiting players for choosing secret mission...";
                break;
            case FIRST_PLAYER_EXTRACTION:
                message = "First player extraction...";
                break;
            case GAME_STARTED:
                message = "GAME STARTED!";
                break;
        }


        HashMap<String,Integer> points = new HashMap<>();
        for(Player player : this.players){
            if(player.getPersonalBoard() != null){
                points.put(player.getNickname(),player.getPersonalBoard().getScore());
            }else{
                points.put(player.getNickname(),0);
            }

        }
        ArrayList<String> nicknameWinners = new ArrayList<>();
        for(Player winner : this.winners){
            nicknameWinners.add(winner.getNickname());
        }
        String currentPlayerNickname = null;
        if(this.currentPlayer != null){
            currentPlayerNickname = this.currentPlayer.getNickname();
        }

        HashMap<String, Pawn> pawnsSelected = new HashMap<>();
        for (Player player : this.players) {
            pawnsSelected.put(player.getNickname(), player.getPawnColor());
        }
        this.observable.notifyUpdateGame(new SimplifiedGame(this.gameState,currentPlayerNickname,points,nicknameWinners,this.availablePawns, pawnsSelected), message);

    }

    /**
     * Returns an array of players
     *
     * @return players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Returns a list of available pawn color
     *
     * @return list of available colors
     */
    public ArrayList<Pawn> getAvailablePawns() {
        return availablePawns;
    }

    /**
     * Sets final round
     *
     * @param finalRound
     */
    public void setFinalRound(int finalRound) {
        this.finalRound = finalRound;
    }

    /**
     * Returns the group's chat
     *
     * @return chat
     */
    public Chat getChat() {
        return this.chat;
    }

    // THIS IS FOR TESTING
    public ArrayList<Player> getWinners() {
        return winners;
    }

    public void errorState(String clientID){
        this.observable.notifyError("YOU CANNOT DO THAT NOW",clientID);
    }

    public String[][] printableGame(Player player) {
        //COMMON TABLE: check if missions are already present
        String[][] commonTablePrint;
        if (getCommonTable().getCommonMissions().isEmpty()) {
            commonTablePrint = commonTable.printableCommonTable();
        } else {
            commonTablePrint = commonTable.printableCommonTableAndMissions();
        }
        //SCORES
        String[][] scores = printableScores();
        //PERSONAL BOARD
        String[][] personalBoardPrint = player.getPersonalBoard().printablePersonalBoard();
        //HAND: check if secret mission is already present
        String[][] handPrint;
        if (player.getPersonalBoard().getSecretMission() == null) {
            handPrint = player.getHand().printableHand();
        } else {
            handPrint = player.printableHandAndMission();
        }
        //calculate dimensions
        int yDim = commonTablePrint.length + personalBoardPrint.length + handPrint.length + 4;
        int xDim = Math.max(Math.max(commonTablePrint[0].length + scores[0].length + 1, personalBoardPrint[0].length), handPrint[0].length);
        //utils
        Printer printer = new Printer();
        int y = 0;

        //initialize empty matrix
        String[][] printableGame = new String[yDim][xDim];
        for (int i = 0; i < yDim; i++) {
            for (int j = 0; j < xDim; j++) {
                printableGame[i][j] = "\t";
            }
        }

        //DESIGN THE MATRIX
        //show common table
        printableGame[y][0] = "COMMON TABLE:";
        y++;
        printer.addPrintable(commonTablePrint, printableGame, 0, y);

        //show scores

        printer.addPrintable(scores, printableGame, commonTablePrint[0].length + 1, y + 1);
        y += commonTablePrint.length;

        //show personal board
        printableGame[y][0] = "\nYOUR PERSONAL BOARD:\n";
        y++;
        printer.addPrintable(personalBoardPrint, printableGame, (xDim - personalBoardPrint[0].length) / 2, y);
        y += personalBoardPrint.length;

        //show player's hand
        printableGame[y][0] = "\nYOUR HAND:\n";
        y++;
        printer.addPrintable(handPrint, printableGame, 0, y);

        return printableGame;
    }

    public String[][] printableScores() {
        //dimensions
        int xDim = 2;
        int yDim = players.size() + 1;
        String[][] scores = new String[yDim][xDim];

        //calculate the spaces needed to align the names
        int maxLenght = 0;
        StringBuilder spaces;
        for (Player p : players) {
            maxLenght = Math.max(maxLenght, p.getNickname().length());
        }
        maxLenght++;

        //design the matrix
        scores[0][0] = "CURRENT SCORES:";
        scores[0][1] = "";
        for (Player p : players) {
            int i = 0;
            spaces = new StringBuilder();
            while (i + p.getNickname().length() < maxLenght) {
                spaces.append(" ");
                i++;
            }
            scores[players.indexOf(p) + 1][0] = p.getNickname() + spaces;
            scores[players.indexOf(p) + 1][1] = p.printableScore();
        }

        return scores;
    }

    public ModelObservable getObservable(){
        return this.observable;
    }
}
