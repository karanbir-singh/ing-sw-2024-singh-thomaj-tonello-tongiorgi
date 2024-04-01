package it.polimi.ingsw.gc26;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.game.CommonTable;
import it.polimi.ingsw.gc26.model.game.Game;
import it.polimi.ingsw.gc26.model.hand.Hand;
import it.polimi.ingsw.gc26.model.player.PersonalBoard;
import it.polimi.ingsw.gc26.model.player.Player;

public class GameController {
    private final Game game;

    public GameController(Game game) {
        this.game = game;
    }

    private Player getPlayer(int playerID) {
        return game.getPlayers().stream().filter((Player p) -> p.getID() == playerID).findAny().get();
    }

    // PHASE 1: Game preparation
    public void prepareCommonTable() {
        CommonTable commonTable = game.getCommonTable();

        // Setup resource cards on the table
        commonTable.getResourceDeck().shuffleDeck();
        Card firstResourceCard = commonTable.getResourceDeck().removeCard();
        Card secondResourceCard = commonTable.getResourceDeck().removeCard();
        commonTable.addCard(firstResourceCard, commonTable.getResourceCards(), 0);
        commonTable.addCard(secondResourceCard, commonTable.getResourceCards(), 1);

        // Setup gold cards on the table
        commonTable.getGoldDeck().shuffleDeck();
        Card firstGoldCard = commonTable.getGoldDeck().removeCard();
        Card secondGoldCard = commonTable.getGoldDeck().removeCard();
        commonTable.addCard(firstGoldCard, commonTable.getGoldCards(), 0);
        commonTable.addCard(secondGoldCard, commonTable.getGoldCards(), 1);
    }

    public void prepareStarterCards() {
        CommonTable commonTable = game.getCommonTable();

        commonTable.getStarterDeck().shuffleDeck();
        for (Player p : game.getPlayers()) {
            Card starterCard = commonTable.getStarterDeck().removeCard();

            // Create player hand
            p.createHand();

            // Choose starter card side through his hand
            p.getHand().addCard(starterCard);
        }
    }

    public void preparePlayersHand() {
        CommonTable commonTable = game.getCommonTable();
        for (Player p : game.getPlayers()) {
            // Add 2 Resources Card
            p.getHand().addCard(commonTable.getResourceDeck().removeCard());
            p.getHand().addCard(commonTable.getResourceDeck().removeCard());

            // Add 1 Gold Card
            p.getHand().addCard(commonTable.getGoldDeck().removeCard());
        }
    }

    public void prepareCommonMissions() {
        CommonTable commonTable = game.getCommonTable();

        // Setup 2 common missions
        commonTable.getMissionDeck().shuffleDeck();
        Card firstCommonMission = commonTable.getMissionDeck().removeCard();
        Card secondCommonMission = commonTable.getMissionDeck().removeCard();
        commonTable.addCard(firstCommonMission, commonTable.getCommonMissions(), 0);
        commonTable.addCard(secondCommonMission, commonTable.getCommonMissions(), 1);
    }

    public void prepareSecretMissions() {
        CommonTable commonTable = game.getCommonTable();

        for (Player p : game.getPlayers()) {
            Card firstSecretMission = commonTable.getMissionDeck().removeCard();
            Card secondSecretMission = commonTable.getMissionDeck().removeCard();

            p.createSecretMissionHand();

            p.getSecretMissionHand().addCard(firstSecretMission);
            p.getSecretMissionHand().addCard(secondSecretMission);
        }
    }

    public void selectSecretMission(int cardIndex, int playerID) {
        Player player = getPlayer(playerID);
        if (cardIndex >= 0 && cardIndex < 2) {
            Card secretMission = player.getSecretMissionHand().getCards().get(cardIndex);
            player.getSecretMissionHand().setSelectedCard(secretMission);
        } else {
            // TODO gestire indice non è corretto
        }
    }

    public void setSecretMission(int playerID) {
        Player player = getPlayer(playerID);
        if (player.getSecretMissionHand().getSelectedCard().isPresent()) {
            player.getPersonalBoard().setSecretMission(player.getSecretMissionHand().getSelectedCard().get());
            player.getSecretMissionHand().removeCard(player.getSecretMissionHand().getSelectedCard().get());
        } else {
            // TODO gestire se non è stata selezionata la missione
        }
    }

    public void setFirstPlayer(int playerID) {
        Player player = getPlayer(playerID);
        player.setFirstPlayer();
        game.setCurrentPlayer(player);
    }

    // PHASE 2: Game Flow
    public void selectCardFromHand(int cardIndex, int playerID) {
        Player player = getPlayer(playerID);
        if (cardIndex >= 0 && cardIndex < 3) {
            Card selectedCard = player.getHand().getCards().get(cardIndex);
            player.getHand().setSelectedCard(selectedCard);
        } else {
            // TODO gestire indice non è corretto
        }
    }

    public void turnSelectedCardSide(int playerID) {
        Player player = getPlayer(playerID);
        player.getHand().turnSide();
    }

    public void selectPositionOnBoard(int selectedX, int selectedY, int playerID) {
        Player player = getPlayer(playerID);
        PersonalBoard p = player.getPersonalBoard();
        if (p.checkIfPlayablePosition(selectedX, selectedY)) {
            p.setPosition(selectedX, selectedY);
        } else {
            // TODO gestire se la posizione scelta non è valida
        }

    }

    public void playCardFromHand(int playerID) {
        Player player = getPlayer(playerID);
        if (player.equals(game.getCurrentPlayer())) {
            if (player.getPersonalBoard() == null) {
                if (player.getHand().getSelectedCard().isPresent()) {
                    player.createPersonalBoard(player.getHand().getSelectedSide().get());
                    player.getHand().removeCard(player.getHand().getSelectedCard().get());
                    return;
                } else {
                    // TODO gestire cosa fare quando la carta non è selezionata
                }
            }
            PersonalBoard personalBoard = player.getPersonalBoard();
            Hand hand = player.getHand();
            if (hand.getSelectedCard().isEmpty()) {
                // TODO gestire cosa fare quando la carta non è selezionata
                return;
            }
            personalBoard.playSide(hand.getSelectedSide().get());
            hand.removeCard(hand.getSelectedCard().get());
        }

    }

    public void selectCardFromCommonTable(int cardX, int cardY, int playerID) {
        Card selectedCard = null;
        if (cardY == 0) {
            if (cardX == 0) {
                selectedCard = game.getCommonTable().getResourceCards().get(0);
            } else if (cardX == 1) {
                selectedCard = game.getCommonTable().getResourceCards().get(1);
            } else if (cardX == 2) {
                selectedCard = game.getCommonTable().getResourceDeck().getTopCard();
            } else {
                // TODO gestire indice X non corretto
            }
        } else if (cardY == 1) {
            if (cardX == 0) {
                selectedCard = game.getCommonTable().getGoldCards().get(0);
            } else if (cardX == 1) {
                selectedCard = game.getCommonTable().getGoldCards().get(1);
            } else if (cardX == 2) {
                selectedCard = game.getCommonTable().getGoldDeck().getTopCard();
            } else {
                // TODO gestire indice X non corretto
            }
        } else {
            // TODO gestire indice Y non corretto
        }

        game.getCommonTable().selectCard(selectedCard);
    }

    public void drawSelectedCard(int playerID) {
        Player player = getPlayer(playerID);

        if (player.equals(game.getCurrentPlayer())) {
            CommonTable commonTable = game.getCommonTable();
            Hand hand = player.getHand();
            // TODO si può migliorare questo codice
            if (commonTable.getSelectedCard().isPresent()) {
                int index;
                index = commonTable.getResourceCards().indexOf(commonTable.getSelectedCard().get());
                if (index != -1) {
                    Card removedCard = commonTable.removeCard(commonTable.getResourceCards(), index);
                    hand.addCard(removedCard);

                    Card replacingCard = commonTable.getResourceDeck().removeCard();
                    commonTable.addCard(replacingCard, commonTable.getResourceCards(), index);
                }

                index = commonTable.getGoldCards().indexOf(commonTable.getSelectedCard().get());
                if (index != -1) {
                    Card removedCard = commonTable.removeCard(commonTable.getGoldCards(), index);
                    hand.addCard(removedCard);

                    Card replacingCard = commonTable.getGoldDeck().removeCard();
                    commonTable.addCard(replacingCard, commonTable.getGoldCards(), index);
                }

                if (commonTable.getResourceDeck().getTopCard().equals(commonTable.getSelectedCard().get())) {
                    Card removedCard = commonTable.getResourceDeck().removeCard();
                    hand.addCard(removedCard);
                }

                if (commonTable.getGoldDeck().getTopCard().equals(commonTable.getSelectedCard().get())) {
                    Card removedCard = commonTable.getGoldDeck().removeCard();
                    hand.addCard(removedCard);
                }
            }
        }
    }

    public void changeTurn() {
        game.goToNextPlayer();
    }

    // PHASE 3: End game


    public Game getGame() {
        return game;
    }
}
