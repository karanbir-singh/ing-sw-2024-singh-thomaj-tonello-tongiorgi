package it.polimi.ingsw.gc26.model.game;

import it.polimi.ingsw.gc26.model.deck.Deck;
import it.polimi.ingsw.gc26.model.player.Pawn;
import it.polimi.ingsw.gc26.model.player.Player;
import it.polimi.ingsw.gc26.model.player.PlayerState;
import it.polimi.ingsw.gc26.network.ModelObservable;
import it.polimi.ingsw.gc26.network.VirtualView;
import it.polimi.ingsw.gc26.parser.ParserCore;
import it.polimi.ingsw.gc26.view_model.SimplifiedCommonTable;
import it.polimi.ingsw.gc26.view_model.SimplifiedGame;

import java.io.Serializable;
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
    private final ArrayList<Player> players;
    /**
     * This attribute represents the common table to all the players
     */
    private final CommonTable commonTable;
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
    /**
     * This attribute represents the observers if this game
     */
    private final ModelObservable observable;

    /**
     * Setups the games
     *
     * @param players list of players of the game
     */
    public Game(ArrayList<Player> players, ArrayList<VirtualView> clients) {
        this.numberOfPlayers = players.size();

        this.players = new ArrayList<>();
        this.players.addAll(players);

        // Add observers
        this.observable = new ModelObservable();
        for (int i = 0; i < clients.size(); i++) {
            this.observable.addObserver(clients.get(i), players.get(i).getID());
        }
        for (Player player : players) {
            player.setObservable(this.observable);
        }

        // Create each deck
        ParserCore p = new ParserCore("src/main/resources/Data/CodexNaturalisCards.json");
        Deck goldCardDeck = p.getGoldCards();
        Deck resourceCardDeck = p.getResourceCards();
        Deck missionDeck = p.getMissionCards();
        Deck starterDeck = p.getStarterCards();

        // Create common table
        this.commonTable = new CommonTable(resourceCardDeck, goldCardDeck, starterDeck, missionDeck, this.observable);

        // Fill list of available pawns
        availablePawns = new ArrayList<>();
        availablePawns.add(Pawn.BLUE);
        availablePawns.add(Pawn.RED);
        availablePawns.add(Pawn.YELLOW);
        availablePawns.add(Pawn.GREEN);

        this.winners = new ArrayList<>();
        this.round = 0;
        this.finalRound = -1;

        // Create chat box
        this.chat = new Chat(this.observable);
    }

    /**
     * Returns the player by his ID
     *
     * @param playerID ID of the searched player
     */
    public Player getPlayerByID(String playerID) {
        return players.stream().filter((Player p) -> p.getID().equals(playerID)).findAny().get();
    }

    /**
     * Returns the player by his nickname
     *
     * @param playerNickname Nickname of the searched player
     */
    public Player getPlayerByNickname(String playerNickname) {
        if (playerNickname.isEmpty()) {
            return null;
        }
        try {
            return players.stream().filter((Player p) -> p.getNickname().equals(playerNickname)).findAny().get();
        } catch (Exception e) {
            return null;
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

            //TODO CAMBIARE STATO DEL GAME IN WINNER E NOTIFICARE
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
        this.setState(GameState.WINNER);
        // TODO update simplified player
    }

    /**
     * Sets the current player to the parameter given
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
                                -1),
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
            case WINNER:
                message = "Game ended, here are the winners";
        }


        HashMap<String, Integer> points = new HashMap<>();
        for (Player player : this.players) {
            if (player.getPersonalBoard() != null) {
                points.put(player.getNickname(), player.getPersonalBoard().getScore());
            } else {
                points.put(player.getNickname(), 0);
            }

        }
        ArrayList<String> nicknameWinners = new ArrayList<>();
        for (Player winner : this.winners) {
            nicknameWinners.add(winner.getNickname());
        }
        String currentPlayerNickname = null;
        if (this.currentPlayer != null) {
            currentPlayerNickname = this.currentPlayer.getNickname();
        }

        HashMap<String, Pawn> pawnsSelected = new HashMap<>();
        for (Player player : this.players) {
            pawnsSelected.put(player.getNickname(), player.getPawnColor());
        }
        this.observable.notifyUpdateGame(new SimplifiedGame(this.gameState, currentPlayerNickname, points, nicknameWinners, this.availablePawns, pawnsSelected), message);

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
     * Checks if the passed pawn is available
     *
     * @param pawn pawn to check
     * @return true if availablePawn contains pawn
     */
    public boolean checkPawnAvailability(Pawn pawn) {
        return availablePawns.contains(pawn);
    }

    /**
     * Removes passed pawn from availablePawns
     *
     * @param pawn pawn to remove
     */
    public void removePawn(Pawn pawn) {
        availablePawns.remove(pawn);

        HashMap<String, Integer> points = new HashMap<>();
        for (Player player : this.players) {
            if (player.getPersonalBoard() != null) {
                points.put(player.getNickname(), player.getPersonalBoard().getScore());
            } else {
                points.put(player.getNickname(), 0);
            }

        }
        ArrayList<String> nicknameWinners = new ArrayList<>();
        for (Player winner : this.winners) {
            nicknameWinners.add(winner.getNickname());
        }
        String currentPlayerNickname = null;
        if (this.currentPlayer != null) {
            currentPlayerNickname = this.currentPlayer.getNickname();
        }

        HashMap<String, Pawn> pawnsSelected = new HashMap<>();
        for (Player player : this.players) {
            pawnsSelected.put(player.getNickname(), player.getPawnColor());
        }

        this.observable.notifyUpdateGame(
                new SimplifiedGame(gameState, currentPlayerNickname, points, nicknameWinners, availablePawns, pawnsSelected),
                pawn.toString() + " color has been chosen"
        );
    }

    /**
     * Sets final round
     *
     * @param finalRound the last round, after that there will be declared the winners
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

    /**
     * Notifies the clients of triggered error
     *
     * @param clientID     id of the client
     * @param errorMessage error message
     */
    public void sendError(String clientID, String errorMessage) {
        this.observable.notifyError(errorMessage, clientID);
    }

    // THIS IS FOR TESTING
    public ArrayList<Player> getWinners() {
        return winners;
    }

    public ModelObservable getObservable() {
        return this.observable;
    }
}
