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
        PersonalBoard p = game.getCurrentPlayer().getPersonalBoard();
        Hand h = game.getCurrentPlayer().getHand();
        if (h.getSelectedCard().isEmpty()) {
            // TODO Gestire la non selezione di una carta con eccezione
            return;
        }
        /*if (!(p.getSelectedX() && p.getSelectedY())) { // TODO controllare in altro modo
            // TODO Gestire la non selezione di una posizione con eccezione
            return;
        }*/
        p.playSide(h.getSelectedSide().get());
        h.removeCard(h.getSelectedCard().get());
    }

    public void selectCardFromCommonBoard(Card card) {
        game.getCommonTable().selectCard(card);
    }

    public void drawSelectedCard() {
        CommonTable c = game.getCommonTable();
        Hand hand = game.getCurrentPlayer().getHand();

        // TODO si può migliorare questo codice
        int index;
        index = c.getResourceCardsOnTable().indexOf(c.getSelectedCard());
        if (index != -1){
            Card removedCard = c.removeCardFromTable(c.getResourceCardsOnTable(), index);
            hand.addCard(removedCard);
            Card replacingCard = c.removeCardFromDeck(c.getResourceDeck());
            c.addCardToTable(replacingCard, c.getResourceCardsOnTable(), index);
        }

        index = c.getGoldCardsOnTable().indexOf(c.getSelectedCard());
        if (index != -1){
            Card removedCard = c.removeCardFromTable(c.getGoldCardsOnTable(), index);
            hand.addCard(removedCard);
            Card replacingCard = c.removeCardFromDeck(c.getGoldDeck());
            c.addCardToTable(replacingCard, c.getGoldCardsOnTable(), index);
        }

        if (c.getResourceDeck().getTopCard().equals(c.getSelectedCard())){
            Card removedCard = c.getResourceDeck().removeCard();
            hand.addCard(removedCard);
        }

        if (c.getGoldDeck().getTopCard().equals(c.getSelectedCard())){
            Card removedCard = c.getGoldDeck().removeCard();
            hand.addCard(removedCard);
        }
    }

    public Game getGame() {
        return game;
    }
}
