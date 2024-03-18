package it.polimi.ingsw.gc26.model;

import it.polimi.ingsw.gc26.model.game.CommonTable;
import it.polimi.ingsw.gc26.model.game.Game;
import it.polimi.ingsw.gc26.model.player.Player;

public class Controller {


    public void selectCardFromHand(Card card){
        Hand hand;
        hand = game.player.getHand();
        hand.setSelectedCard(card);
        //vedere qua con turn side

    }

    public void selectPositionOnBoard(int x, int y){
        PersonalBoard personalBoard;
        personalBoard = game.player.personalBoard;
        if(!personalBoard.checkIfPlayable(x,y)){
            return;
        }
        personalBoard.setPosition(x,y);
    }

    public void playCardFromHand(){
        if(hand.getSelectedCard().isPresent() && personalBoard.getSelectedX() && personalBoard.getSelectedY()){
            personalBoard.playSide();
        }
    }


    public void selectCardFromCommonBoard(Card card){
        commonTable.selectCard(card);
    }

    public void drawSelectCard(){
        int index;
        index = game.commonTable.resourceCardsOnTable.indexOf(commonTable.getSelectedCard());
        if(index != -1){
            game.commonTable.removeCardFromTable(game.commonTable.getResourceCards()); // matrice;
        }
    }
}
