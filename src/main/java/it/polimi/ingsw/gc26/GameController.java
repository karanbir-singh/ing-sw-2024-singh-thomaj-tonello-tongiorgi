package it.polimi.ingsw.gc26;

import it.polimi.ingsw.gc26.model.game.Message;
import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.game.CommonTable;
import it.polimi.ingsw.gc26.model.game.Game;
import it.polimi.ingsw.gc26.model.game.GameState;
import it.polimi.ingsw.gc26.model.hand.Hand;
import it.polimi.ingsw.gc26.model.player.PersonalBoard;
import it.polimi.ingsw.gc26.model.player.Player;

public class GameController {

    /**
     * This attribute represents the game that the game controller controls
     */
    private final Game game;

    /**
     * Initializes the game (provided by the main controller)
     *
     * @param game the object that represents the game
     */
    public GameController(Game game) {
        this.game = game;
    }

    /**
     * Returns the player associated to the playerID
     *
     * @param playerID ID of the searched player
     */
    private Player getPlayer(String playerID) {
        return game.getPlayers().stream().filter((Player p) -> p.getID().equals(playerID)).findAny().get();
    }

    // PHASE 1: Game preparation

    /**
     * Places two resource cards and two gold cards on the common table
     */
    public void prepareCommonTable() {
        if (game.getGameState().equals(GameState.INITIAL_STAGE)) {
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
        } else {
            //TODO gestisci come cambiare il model quando lo stato è errato
        }
    }

    /**
     * + Gives starter cards to each player
     */
    public void prepareStarterCards() {
        if (game.getGameState().equals(GameState.INITIAL_STAGE)) {
            CommonTable commonTable = game.getCommonTable();

            // Set and shuffle starter cards deck
            commonTable.getStarterDeck().shuffleDeck();
            for (Player p : game.getPlayers()) {
                // Get starter card
                Card starterCard = commonTable.getStarterDeck().removeCard();

                // Create player hand
                p.createHand();

                // Place starter card to the player's hand
                p.getHand().addCard(starterCard);
            }
        } else {
            //TODO gestisci come cambiare il model quando lo stato è errato
        }
    }

    /**
     * Gives the first three playable cards to each player
     */
    public void preparePlayersHand() {
        if (game.getGameState().equals(GameState.INITIAL_STAGE)) {
            CommonTable commonTable = game.getCommonTable();

            for (Player p : game.getPlayers()) {
                // Add 2 Resources Card to the hand
                p.getHand().addCard(commonTable.getResourceDeck().removeCard());
                p.getHand().addCard(commonTable.getResourceDeck().removeCard());

                // Add 1 Gold Card to the hand
                p.getHand().addCard(commonTable.getGoldDeck().removeCard());
            }
        } else {
            //TODO gestisci come cambiare il model quando lo stato è errato
        }
    }

    /**
     * Places two common mission cards on the common table
     */
    public void prepareCommonMissions() {
        if (game.getGameState().equals(GameState.INITIAL_STAGE)) {
            CommonTable commonTable = game.getCommonTable();

            // Set and shuffle mission cards deck
            commonTable.getMissionDeck().shuffleDeck();
            Card firstCommonMission = commonTable.getMissionDeck().removeCard();
            Card secondCommonMission = commonTable.getMissionDeck().removeCard();

            // Place common mission cards on the table
            commonTable.addCard(firstCommonMission, commonTable.getCommonMissions(), 0);
            commonTable.addCard(secondCommonMission, commonTable.getCommonMissions(), 1);
        } else {
            //TODO gestisci come cambiare il model quando lo stato è errato
        }
    }

    /**
     * Gives two mission cards to each player
     */
    public void prepareSecretMissions() {
        if (game.getGameState().equals(GameState.INITIAL_STAGE)) {
            CommonTable commonTable = game.getCommonTable();

            for (Player p : game.getPlayers()) {
                // Get two mission cards from the table
                Card firstSecretMission = commonTable.getMissionDeck().removeCard();
                Card secondSecretMission = commonTable.getMissionDeck().removeCard();

                // Create the secondary hand
                p.createSecretMissionHand();

                // Place starter card to the player's secondary hand
                p.getSecretMissionHand().addCard(firstSecretMission);
                p.getSecretMissionHand().addCard(secondSecretMission);
            }
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
        if (game.getGameState().equals(GameState.INITIAL_STAGE)) {
            // Get the player who is selecting the card by his ID
            Player player = getPlayer(playerID);

            // Check if the given index is correct
            if (cardIndex >= 0 && cardIndex < 2) {
                Card secretMission = player.getSecretMissionHand().getCards().get(cardIndex);

                // Select the chosen card
                player.getSecretMissionHand().setSelectedCard(secretMission);
            } else {
                // TODO gestire indice non è corretto
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
        if (game.getGameState().equals(GameState.INITIAL_STAGE)) {
            // Get the player who is setting the card by his ID
            Player player = getPlayer(playerID);

            // Check if the player selected a card
            if (player.getSecretMissionHand().getSelectedCard().isPresent()) {
                // Set the secret mission on the personal board of the players
                player.getPersonalBoard().setSecretMission(player.getSecretMissionHand().getSelectedCard().get());

                // Remove the card from the secondary hand
                player.getSecretMissionHand().removeCard(player.getSecretMissionHand().getSelectedCard().get());
            } else {
                // TODO gestire se non è stata selezionata la missione
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
        if (game.getGameState().equals(GameState.INITIAL_STAGE)) {
            // Get the player by his ID
            Player player = getPlayer(playerID);

            // Set the player as the first
            player.setFirstPlayer();
            game.setCurrentPlayer(player);

            // Change game state into GAME_IN_PROGRESS
            game.setGameState(GameState.GAME_IN_PROGRESS);
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
        Player player = getPlayer(playerID);

        if (game.getGameState().equals(GameState.INITIAL_STAGE)) {
            // Check if the given starter card index is correct
            if (cardIndex == 0) {
                Card selectedCard = player.getHand().getCards().get(cardIndex);

                // Set the selected card
                player.getHand().setSelectedCard(selectedCard);
            } else {
                // TODO gestire indice non è corretto
            }
        } else {
            // Check if the given index is correct
            if (cardIndex >= 0 && cardIndex < 3) {
                Card selectedCard = player.getHand().getCards().get(cardIndex);

                // Set the selected card
                player.getHand().setSelectedCard(selectedCard);
            } else {
                // TODO gestire indice non è corretto
            }
        }
    }

    /**
     * Turns the side of the selected card
     *
     * @param playerID ID of the player who is turning the side of the selected card
     */
    public void turnSelectedCardSide(String playerID) {
        // Get player who is turning the selected card side
        Player player = getPlayer(playerID);

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
        Player player = getPlayer(playerID);

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
        Player player = getPlayer(playerID);

        // Check if it's the player's turn
        if (player.equals(game.getCurrentPlayer())) {
            // Check if it's the INITIAL_STAGE: the player is placing the starter card
            if (game.getGameState().equals(GameState.INITIAL_STAGE)) {
                // If the player doesn't have a personal board, it means he's placing a starter card
                if (player.getPersonalBoard() == null) {
                    // Check if there is a selected card
                    if (player.getHand().getSelectedCard().isPresent()) {
                        // Create the personal board with the selected starter card side
                        player.createPersonalBoard(player.getHand().getSelectedSide().get());

                        // Remove the starter card from the hand
                        player.getHand().removeCard(player.getHand().getSelectedCard().get());
                    } else {
                        // TODO gestire cosa fare quando la carta non è selezionata
                    }
                }
            } else {
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

            }
        } else {
            // TODO gestire cosa fare quando non è il giocatore corrente a provare a giocare la carta selezionata
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
        if (game.getGameState().equals(GameState.GAME_IN_PROGRESS) || game.getGameState().equals(GameState.END_STAGE)) {
            // Check if it's the current player
            if (getPlayer(playerID).equals(game.getCurrentPlayer())) {
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
        if (game.getGameState().equals(GameState.GAME_IN_PROGRESS) || game.getGameState().equals(GameState.END_STAGE)) {
            // Get the player who is trying to draw the selected card on the common table
            Player player = getPlayer(playerID);

            // Check if it's the turn of the player
            if (player.equals(game.getCurrentPlayer())) {
                // Get the common table and hand
                CommonTable commonTable = game.getCommonTable();
                Hand hand = player.getHand();

                // Check if there is a selected card on the common table
                if (commonTable.getSelectedCard().isPresent()) {
                    Card removedCard = commonTable.removeSelectedCard();
                    hand.addCard(removedCard);
                }

                // Check if player's score is greater or equal then 20 points OR decks are both empty
                if (player.getPersonalBoard().getScore() >= 20 ||
                        (commonTable.getResourceDeck().getCards().isEmpty() &&
                                commonTable.getGoldDeck().getCards().isEmpty())) {
                    // Change game state into END_STAGE
                    game.setGameState(GameState.END_STAGE);

                    // Set the final round
                    game.setFinalRound(game.getRound() + 1);
                }
            }
        } else {
            //TODO gestisci come cambiare il model quando lo stato è errato
        }
    }

    public void changeTurn() {
        game.goToNextPlayer();
    }

// PHASE 3: End game


    public Game getGame() {
        return game;
    }

    public void addMessage(Message message) {
        this.getGame().getChat().addMessage(message);
    }
}
