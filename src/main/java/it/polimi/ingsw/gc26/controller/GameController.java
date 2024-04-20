package it.polimi.ingsw.gc26.controller;

import it.polimi.ingsw.gc26.model.game.Message;
import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.game.CommonTable;
import it.polimi.ingsw.gc26.model.game.Game;
import it.polimi.ingsw.gc26.model.game.GameState;
import it.polimi.ingsw.gc26.model.hand.Hand;
import it.polimi.ingsw.gc26.model.player.PersonalBoard;
import it.polimi.ingsw.gc26.model.player.Player;

import java.util.ArrayList;
import java.util.Random;

public class GameController {
    /**
     * This attribute represents the game that the game controller controls
     */
    private final Game game;

    /**
     * This attribute represents the list of players that need to do an action, used only in the game preparation phase
     */
    private ArrayList<Player> playersToWait;

    /**
     * Initializes the game (provided by the main controller)
     *
     * @param game the object that represents the game
     */
    public GameController(Game game) {
        this.game = game;
        this.game.setState(GameState.COMMON_TABLE_PREPARATION);

        this.playersToWait = new ArrayList<>();
    }

    // PHASE 1: Game preparation

    /**
     * Places two resource cards and two gold cards on the common table
     */
    public void prepareCommonTable() {
        if (game.getState().equals(GameState.COMMON_TABLE_PREPARATION)) {
            CommonTable commonTable = game.getCommonTable();

            // Set and shuffle resource cards deck
            commonTable.getResourceDeck().shuffleDeck();
            Card firstResourceCard = commonTable.getResourceDeck().removeCard();
            Card secondResourceCard = commonTable.getResourceDeck().removeCard();

            // Place resource cards on the table
            commonTable.addCard(firstResourceCard, commonTable.getResourceCards(), 0);
            commonTable.addCard(secondResourceCard, commonTable.getResourceCards(), 1);

            // Set and shuffle gold cards deck
            commonTable.getGoldDeck().shuffleDeck();
            Card firstGoldCard = commonTable.getGoldDeck().removeCard();
            Card secondGoldCard = commonTable.getGoldDeck().removeCard();

            // Place resource cards on the table
            commonTable.addCard(firstGoldCard, commonTable.getGoldCards(), 0);
            commonTable.addCard(secondGoldCard, commonTable.getGoldCards(), 1);

            // Change game state
            game.setState(GameState.STARTER_CARDS_DISTRIBUTION);

            // Then prepare starter cards
            this.prepareStarterCards();
        } else {
            //TODO gestisci come cambiare il model quando lo stato è errato
        }
    }

    /**
     * + Gives starter cards to each player
     */
    public void prepareStarterCards() {
        if (game.getState().equals(GameState.STARTER_CARDS_DISTRIBUTION)) {
            CommonTable commonTable = game.getCommonTable();

            // Set and shuffle starter cards deck
            commonTable.getStarterDeck().shuffleDeck();
            for (Player player : game.getPlayers()) {
                // Get starter card
                Card starterCard = commonTable.getStarterDeck().removeCard();

                // Create player hand
                player.createHand();

                // Place starter card to the player's hand
                player.getHand().addCard(starterCard);

                // Make it permanently selected
                player.getHand().setSelectedCard(starterCard);
            }

            // Add all players to list
            playersToWait.addAll(game.getPlayers());

            // Change game state
            game.setState(GameState.WAITING_STARTER_CARD_PLACEMENT);
        } else {
            //TODO gestisci come cambiare il model quando lo stato è errato
        }
    }

    /**
     * Sets the pawn color
     *
     * @param color    chosen color index
     * @param playerID ID of the player who is choosing the color
     */
    public void choosePawnColor(String color, String playerID) {
        if (game.getState().equals(GameState.WAITING_PAWNS_SELECTION)) {
            // Get the player who is choosing the color by his ID
            Player player = game.getPlayerByID(playerID);

            // Set pawn
            player.setPawn(color, game.getAvailablePawns());

            // Remove from list
            playersToWait.remove(player);

            // Check if all players have chosen the pawn
            if (playersToWait.isEmpty()) {
                // Change game state
                game.setState(GameState.HAND_PREPARATION);

                // Prepare hand for each player
                this.preparePlayersHand();
            }
        } else {
            // TODO gestire ome cambiare il model quando lo stato e' errato
        }
    }

    /**
     * Gives the first three playable cards to each player
     */
    public void preparePlayersHand() {
        if (game.getState().equals(GameState.HAND_PREPARATION)) {
            CommonTable commonTable = game.getCommonTable();

            // Give hand cards to each player
            for (Player player : game.getPlayers()) {
                // Add 2 Resources Card to the hand
                player.getHand().addCard(commonTable.getResourceDeck().removeCard());
                player.getHand().addCard(commonTable.getResourceDeck().removeCard());

                // Add 1 Gold Card to the hand
                player.getHand().addCard(commonTable.getGoldDeck().removeCard());
            }

            // Change game state
            game.setState(GameState.COMMON_MISSION_PREPARATION);

            // Then prepare common mission
            this.prepareCommonMissions();
        } else {
            //TODO gestisci come cambiare il model quando lo stato è errato
        }
    }

    /**
     * Places two common mission cards on the common table
     */
    public void prepareCommonMissions() {
        if (game.getState().equals(GameState.COMMON_MISSION_PREPARATION)) {
            CommonTable commonTable = game.getCommonTable();

            // Set and shuffle mission cards deck
            commonTable.getMissionDeck().shuffleDeck();
            Card firstCommonMission = commonTable.getMissionDeck().removeCard();
            Card secondCommonMission = commonTable.getMissionDeck().removeCard();

            // Place common mission cards on the table
            commonTable.addCard(firstCommonMission, commonTable.getCommonMissions(), 0);
            commonTable.addCard(secondCommonMission, commonTable.getCommonMissions(), 1);

            // Change game state
            game.setState(GameState.SECRET_MISSION_DISTRIBUTION);

            // Then give two secret missions to each player
            this.prepareSecretMissions();
        } else {
            //TODO gestisci come cambiare il model quando lo stato è errato
        }
    }

    /**
     * Gives two mission cards to each player
     */
    public void prepareSecretMissions() {
        if (game.getState().equals(GameState.SECRET_MISSION_DISTRIBUTION)) {
            CommonTable commonTable = game.getCommonTable();

            for (Player player : game.getPlayers()) {
                // Get two mission cards from the table
                Card firstSecretMission = commonTable.getMissionDeck().removeCard();
                Card secondSecretMission = commonTable.getMissionDeck().removeCard();

                // Create the secondary hand
                player.createSecretMissionHand();

                // Place starter card to the player's secondary hand
                player.getSecretMissionHand().addCard(firstSecretMission);
                player.getSecretMissionHand().addCard(secondSecretMission);
            }

            // Add all players to list
            playersToWait.addAll(game.getPlayers());

            // Change game state
            game.setState(GameState.WAITING_SECRET_MISSION_CHOICE);
        } else {
            //TODO gestisci come cambiare il model quando lo stato è errato
        }
    }

    /**
     * Selects the chosen secret mission card
     *
     * @param cardIndex index of the selected mission card
     * @param playerID  ID of the player who is selecting the secret mission card
     */
    public void selectSecretMission(int cardIndex, String playerID) {
        if (game.getState().equals(GameState.WAITING_SECRET_MISSION_CHOICE)) {
            // Get the player who is selecting the card by his ID
            Player player = game.getPlayerByID(playerID);

            // Get selected secret mission
            Card secretMission = player.getSecretMissionHand().getCard(0, 2, cardIndex);

            // Select the chosen card
            if (secretMission != null) {
                player.getSecretMissionHand().setSelectedCard(secretMission);
            }
        } else {
            //TODO gestisci come cambiare il model quando lo stato è errato
        }
    }

    /**
     * Sets the selected secret mission card on the personal board of the player
     *
     * @param playerID ID of the player who is setting the secret mission card
     */
    public void setSecretMission(String playerID) {
        if (game.getState().equals(GameState.WAITING_SECRET_MISSION_CHOICE)) {
            // Get the player who is setting the card by his ID
            Player player = game.getPlayerByID(playerID);

            // Set the secret mission on the personal board of the players
            Card secretMission = player.getPersonalBoard().setSecretMission(player.getSecretMissionHand().getSelectedCard());

            if (secretMission != null) {
                // Remove the card from the secondary hand
                player.getSecretMissionHand().removeCard(secretMission);

                // Remove player from list
                playersToWait.remove(player);

                // Check if all players have chosen the secret
                if (playersToWait.isEmpty()) {
                    // Get first player ID randomly and start game
                    String firstPlayerID = game.getPlayers().get(new Random().nextInt(game.getPlayers().size())).getID();

                    // Change game state
                    game.setState(GameState.FIRST_PLAYER_EXTRACTION);

                    // Then extract first player
                    this.setFirstPlayer(firstPlayerID);
                }
            }
        } else {
            //TODO gestisci come cambiare il model quando lo stato è errato
        }
    }

    /**
     * Sets the first player of the game
     *
     * @param playerID ID of the first player of the games
     */
    public void setFirstPlayer(String playerID) {
        if (game.getState().equals(GameState.FIRST_PLAYER_EXTRACTION)) {
            // Get the player by his ID
            Player player = game.getPlayerByID(playerID);

            // Set the player as the first
            game.getPlayers().getFirst().setNotFirstPlayer();
            player.setFirstPlayer();
            game.setCurrentPlayer(player);

            // Position the first player as the first element of the players' list
            game.getPlayers().remove(player);
            game.getPlayers().addFirst(player);

            // Change game state into GAME_IN_PROGRESS
            game.setState(GameState.GAME_STARTED);
        } else {
            //TODO gestisci come cambiare il model quando lo stato è errato
        }
    }

// PHASE 2: Game Flow

    /**
     * Selects the chosen card in the hand
     *
     * @param cardIndex index of the card in the player's hand
     * @param playerID  ID of the player who is selected the card to play
     */
    public void selectCardFromHand(int cardIndex, String playerID) {
        // Get the player who is selected the card to play
        Player player = game.getPlayerByID(playerID);

        // Get card to select
        Card selectedCard = player.getHand().getCard(0, 3, cardIndex);

        // Set the selected card
        player.getHand().setSelectedCard(selectedCard);
    }

    /**
     * Turns the side of the selected card
     *
     * @param playerID ID of the player who is turning the side of the selected card
     */
    public void turnSelectedCardSide(String playerID) {
        // Get player who is turning the selected card side
        Player player = game.getPlayerByID(playerID);

        // Turn selected card side
        player.getHand().turnSide();
    }

    /**
     * Selects a position on the personal board where the player wants to place the selected card
     *
     * @param selectedX coordinate on the X axis of the chosen position on the personal board
     * @param selectedY coordinate on the Y axis of the chosen position on the personal board
     * @param playerID  ID of the player who is selecting the position on the personal board
     */
    public void selectPositionOnBoard(int selectedX, int selectedY, String playerID) {
        // Get the player who is selecting the position on his personal board
        Player player = game.getPlayerByID(playerID);

        // Get the personal board
        PersonalBoard personalBoard = player.getPersonalBoard();

        // Set the selected position
        personalBoard.setPosition(selectedX, selectedY);
    }

    /**
     * Places the selected side on the personal board
     *
     * @param playerID ID of the player who is playing the selected side of the card in the hand
     */
    public void playCardFromHand(String playerID) {
        // Get player who is trying to place the selected side
        Player player = game.getPlayerByID(playerID);

        // Check if it's the INITIAL_STAGE: the player is placing the starter card
        if (game.getState().equals(GameState.WAITING_STARTER_CARD_PLACEMENT)) {
            // If the player doesn't have a personal board, it means he's placing a starter card
            if (player.getPersonalBoard() == null) {
                // Check if there is a selected card
                if (player.getHand().getSelectedCard().isPresent()) {
                    // Create the personal board
                    player.createPersonalBoard();

                    // Place starter card
                    player.getPersonalBoard().playSide(player.getHand().getSelectedSide().get());

                    // Remove the starter card from the hand
                    player.getHand().removeCard(player.getHand().getSelectedCard().get());

                    playersToWait.remove(player);

                    // Check if all players played the starter card
                    if (playersToWait.isEmpty()) {
                        playersToWait.addAll(game.getPlayers());

                        // Change game state
                        game.setState(GameState.WAITING_PAWNS_SELECTION);
                    }
                }
            }
        } else {
            // Check if it's the player's turn
            if (player.equals(game.getCurrentPlayer())) {
                // Otherwise get the player's personal board and his hand
                PersonalBoard personalBoard = player.getPersonalBoard();
                Hand hand = player.getHand();

                // Check if there is a selected card on the hand
                if (hand.getSelectedCard().isPresent()) {
                    // Place the selected card side on the personal board
                    personalBoard.playSide(hand.getSelectedSide().get());

                    // Remove card from the hand
                    hand.removeCard(hand.getSelectedCard().get());
                } else {
                    // TODO gestire cosa fare quando la carta non è selezionata
                }
            } else {
                // TODO gestire cosa fare quando non è il giocatore corrente a provare a giocare la carta selezionata
            }
        }
    }

    /**
     * Selects the card on the common table that the currentPlayer wants to draw
     *
     * @param cardX    coordinate on the X axis of the card on the common table
     * @param cardY    coordinate on the Y axis of the card on the common table
     * @param playerID ID of the player who is trying to select the card on the common table
     */
    public void selectCardFromCommonTable(int cardX, int cardY, String playerID) {
        if (game.getState().equals(GameState.GAME_STARTED) || game.getState().equals(GameState.END_STAGE)) {
            // Check if it's the current player
            if (game.getPlayerByID(playerID).equals(game.getCurrentPlayer())) {
                // Set the selected card on the common table
                game.getCommonTable().selectCard(cardX, cardY);
            }
        } else {
            //TODO gestisci come cambiare il model quando lo stato è errato
        }
    }

    /**
     * Draw a card from the common table and places it on the current player's hand
     *
     * @param playerID ID of the player who is trying to draw the selected card on the common table
     */
    public void drawSelectedCard(String playerID) {
        if (game.getState().equals(GameState.GAME_STARTED) || game.getState().equals(GameState.END_STAGE)) {
            // Get the player who is trying to draw the selected card on the common table
            Player player = game.getPlayerByID(playerID);

            // Check if it's the turn of the player
            if (player.equals(game.getCurrentPlayer())) {
                // Get the common table and hand
                CommonTable commonTable = game.getCommonTable();
                Hand hand = player.getHand();

                // Get removed card
                Card removedCard = commonTable.removeSelectedCard();

                if (removedCard != null) {
                    // Add card in player's hand
                    hand.addCard(removedCard);

                    // Check if player's score is greater or equal then 20 points OR decks are both empty
                    if (player.getPersonalBoard().getScore() >= 20 || (commonTable.getResourceDeck().getCards().isEmpty() && commonTable.getGoldDeck().getCards().isEmpty())) {
                        // Change game state into END_STAGE
                        game.setState(GameState.END_STAGE);

                        // Set the final round
                        game.setFinalRound(game.getRound() + 1);
                    }

                    // Change turn to next player
                    this.changeTurn();
                }
            }
        } else {
            //TODO gestisci come cambiare il model quando lo stato è errato
        }
    }

    /**
     * Add a new message into the chat
     *
     * @param message          body of the message
     * @param senderID         ID of the sender
     * @param receiverNickname nickname of the receiver
     * @param time             local time of the client
     */
    public void addMessage(String message, String receiverNickname, String senderID, String time) {
        Message msg = new Message(message, game.getPlayerByNickname(receiverNickname), game.getPlayerByID(senderID), time);
        game.getChat().addMessage(msg);
    }

    /**
     * Prints the personal board of given player nickname
     *
     * @param nickname nickname of the player
     * @param playerID ID of the player who is requesting to print personal board
     */
    public void printPersonalBoard(String nickname, String playerID) {
        Player player = game.getPlayerByNickname(nickname);

        // TODO need to add a method in PersonalBoard for update view
    }

    /**
     * Changes the current player
     */
    public void changeTurn() {
        game.goToNextPlayer();
    }
}
