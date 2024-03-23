package it.polimi.ingsw.gc26;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.game.CommonTable;
import it.polimi.ingsw.gc26.model.game.Game;
import it.polimi.ingsw.gc26.model.hand.Hand;
import it.polimi.ingsw.gc26.model.player.PersonalBoard;

public class GameController {
    private Game game;

    public GameController(Game game) {
        this.game = game;
    }

    public void selectCardFromHand(Card card) {
        game.getCurrentPlayer().getHand().setSelectedCard(card);
    }

    public void turnSelectedCardSide() {
        game.getCurrentPlayer().getHand().turnSide();
    }

    public void selectPositionOnBoard(int x, int y) {
        PersonalBoard p = game.getCurrentPlayer().getPersonalBoard();
        if (!p.checkIfPlayable(x, y)) {
            // TODO Gestire posizione errata con eccezione
            return;
        }
        p.setPosition(x, y);
    }

    public void playCardFromHand() {
        PersonalBoard personalBoard = game.getCurrentPlayer().getPersonalBoard();
        Hand h = game.getCurrentPlayer().getHand();
        if (h.getSelectedCard().isEmpty()) {
            // TODO Gestire la non selezione di una carta con eccezione
            return;
        }
        personalBoard.playSide(h.getSelectedSide().get());
        h.removeCard(h.getSelectedCard().get());
    }

    public void selectCardFromCommonTable(Card card) {
        game.getCommonTable().selectCard(card);
    }

    public void drawSelectedCard() {
        CommonTable commonTable = game.getCommonTable();
        Hand hand = game.getCurrentPlayer().getHand();

        // TODO si può migliorare questo codice
        int index;
        index = commonTable.getResourceCards().indexOf(commonTable.getSelectedCard());
        if (index != -1){
            Card removedCard = commonTable.removeCard(commonTable.getResourceCards(), index);
            hand.addCard(removedCard);

            Card replacingCard = commonTable.getResourceDeck().removeCard();
            commonTable.addCard(replacingCard, commonTable.getResourceCards(), index);
        }

        index = commonTable.getGoldCards().indexOf(commonTable.getSelectedCard());
        if (index != -1){
            Card removedCard = commonTable.removeCard(commonTable.getGoldCards(), index);
            hand.addCard(removedCard);

            Card replacingCard = commonTable.getGoldDeck().removeCard();
            commonTable.addCard(replacingCard, commonTable.getGoldCards(), index);
        }

        if (commonTable.getResourceDeck().getTopCard().equals(commonTable.getSelectedCard())){
            Card removedCard = commonTable.getResourceDeck().removeCard();
            hand.addCard(removedCard);
        }

        if (commonTable.getGoldDeck().getTopCard().equals(commonTable.getSelectedCard())){
            Card removedCard = commonTable.getGoldDeck().removeCard();
            hand.addCard(removedCard);
        }
    }

    public Game getGame() {
        return game;
    }
}
