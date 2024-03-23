package it.polimi.ingsw.gc26.model.player;

import it.polimi.ingsw.gc26.model.card_side.Symbol;
import it.polimi.ingsw.gc26.model.deck.Deck;
import it.polimi.ingsw.gc26.model.game.Game;
import jdk.jfr.Experimental;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonalBoardTest {

    @Test
    void firstPlaySide() throws Exception {
        Game game = new Game(2);
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getInitialDeck();
        Deck missionDeck = game.getCommonTable().getMissionDeck();
        Player p1 = new Player(3,"Bob");
        game.addPlayer(p1);
        game.setCurrentPlayer(p1);
        game.getCurrentPlayer().setPersonalBoard(initialDeck.getDeck().get(0).getFront(),
                missionDeck.getDeck().get(0), missionDeck.getDeck().get(1), missionDeck.getDeck().get(2));
        PersonalBoard pb = game.getCurrentPlayer().getPersonalBoard();


        pb.setPosition(1,1);
        pb.playSide(resourceDeck.getDeck().get(0).getFront());
        pb.setPosition(2,2);
        pb.playSide(resourceDeck.getDeck().get(1).getFront());
        pb.setPosition(0,2);
        pb.playSide(goldDeck.getDeck().get(0).getFront());
        pb.setPosition(1,3);
        pb.playSide(goldDeck.getDeck().get(3).getFront());
        pb.setPosition(3,3);
        pb.playSide(resourceDeck.getDeck().get(2).getBack());
        //non conto le risorse permanenti dei back delle carte, aspetto gabi
        assertEquals(pb.getResourceQuantity(Symbol.INSECT) , 2);
        assertEquals(pb.getResourceQuantity(Symbol.PLANT) , 2);
        assertEquals(pb.getResourceQuantity(Symbol.FUNGI) , 0);
        assertEquals(pb.getResourceQuantity(Symbol.ANIMAL) , 0);
        assertEquals(pb.getResourceQuantity(Symbol.QUILL) , 1);
        assertEquals(pb.getResourceQuantity(Symbol.MANUSCRIPT) , 0);
        assertEquals(pb.getResourceQuantity(Symbol.INKWELL) , 0);
        assertEquals(pb.getScore(), 5);

        assertTrue(pb.getOccupiedPositions().get(0).getSide().getUPRIGHT().isHidden());

        assertTrue(pb.getOccupiedPositions().get(1).getSide().getUPRIGHT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(1).getSide().getUPLEFT().isHidden());

        assertTrue(pb.getOccupiedPositions().get(2).getSide().getUPRIGHT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(2).getSide().getUPLEFT().isHidden());

        assertTrue(pb.getOccupiedPositions().get(3).getSide().getUPRIGHT().isHidden());



    }
    @Test
    void secondPlaySide() throws Exception {
        Game game = new Game(2);
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getInitialDeck();
        Deck missionDeck = game.getCommonTable().getMissionDeck();
        Player p1 = new Player(3,"Bob");
        game.addPlayer(p1);
        game.setCurrentPlayer(p1);
        game.getCurrentPlayer().setPersonalBoard(initialDeck.getDeck().get(5).getFront(),
                missionDeck.getDeck().get(0), missionDeck.getDeck().get(1), missionDeck.getDeck().get(2));
        PersonalBoard pb = game.getCurrentPlayer().getPersonalBoard();


        pb.setPosition(-1,1);
        pb.playSide(resourceDeck.getDeck().get(39).getFront());
        pb.setPosition(-2,0);
        pb.playSide(resourceDeck.getDeck().get(38).getFront());
        pb.setPosition(-3,1);
        pb.playSide(resourceDeck.getDeck().get(36).getFront());
        pb.setPosition(-4,0);
        pb.playSide(goldDeck.getDeck().get(38).getFront());
        //non conto le risorse permanenti dei back delle carte, aspetto gabi
        assertEquals(pb.getResourceQuantity(Symbol.INSECT) , 3);
        assertEquals(pb.getResourceQuantity(Symbol.PLANT) , 2);
        assertEquals(pb.getResourceQuantity(Symbol.FUNGI) , 1);
        assertEquals(pb.getResourceQuantity(Symbol.ANIMAL) , 1);
        assertEquals(pb.getResourceQuantity(Symbol.QUILL) , 1);
        assertEquals(pb.getResourceQuantity(Symbol.MANUSCRIPT) , 0);
        assertEquals(pb.getResourceQuantity(Symbol.INKWELL) , 0);
        assertEquals(pb.getScore(), 5);

        assertTrue(pb.getOccupiedPositions().get(0).getSide().getUPLEFT().isHidden());

        assertTrue(pb.getOccupiedPositions().get(1).getSide().getDOWNLEFT().isHidden());


        assertTrue(pb.getOccupiedPositions().get(2).getSide().getUPLEFT().isHidden());

        assertTrue(pb.getOccupiedPositions().get(3).getSide().getDOWNLEFT().isHidden());



    }


    @Test
    void Diagonal6PlantCombinationMission() throws Exception{
        Game game = new Game(2);
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getInitialDeck();
        Deck missionDeck = game.getCommonTable().getMissionDeck();
        Player p1 = new Player(3,"Bob");
        game.addPlayer(p1);
        game.setCurrentPlayer(p1);
        game.getCurrentPlayer().setPersonalBoard(initialDeck.getDeck().get(0).getFront(),
                missionDeck.getDeck().get(1), missionDeck.getDeck().get(6), missionDeck.getDeck().get(3));
        PersonalBoard pb = game.getCurrentPlayer().getPersonalBoard();
        //get(1) è la missione corretta

        pb.setPosition(1,-1);
        pb.playSide(resourceDeck.getDeck().get(0).getBack());
        pb.setPosition(2,-2);
        pb.playSide(resourceDeck.getDeck().get(1).getBack());
        pb.setPosition(3,-3);
        pb.playSide(resourceDeck.getDeck().get(2).getBack());
        pb.setPosition(4,-4);
        pb.playSide(resourceDeck.getDeck().get(3).getBack());
        pb.setPosition(5,-5);
        pb.playSide(resourceDeck.getDeck().get(4).getBack());
        pb.setPosition(6,-6);
        pb.playSide(resourceDeck.getDeck().get(5).getBack());
        pb.endGame();
        assertEquals(pb.getScore(), 4);
    }

    @Test
    void DiagonalAndLpatternCombinationMission() throws Exception{
        Game game = new Game(2);
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getInitialDeck();
        Deck missionDeck = game.getCommonTable().getMissionDeck();
        Player p1 = new Player(3,"Bob");
        game.addPlayer(p1);
        game.setCurrentPlayer(p1);
        game.getCurrentPlayer().setPersonalBoard(initialDeck.getDeck().get(0).getFront(),
                missionDeck.getDeck().get(0), missionDeck.getDeck().get(2), missionDeck.getDeck().get(6));
        PersonalBoard pb = game.getCurrentPlayer().getPersonalBoard(); //get(2) per diagonale e get(6) per L
        //usiamo animals e un fungi in alto a destra

        pb.setPosition(1,1);
        pb.playSide(resourceDeck.getDeck().get(10).getBack()); //questi sono tutti ANIMALS
        pb.setPosition(2,2);
        pb.playSide(resourceDeck.getDeck().get(11).getBack());
        pb.setPosition(3,3);
        pb.playSide(resourceDeck.getDeck().get(12).getBack());
        pb.setPosition(2,4);
        pb.playSide(resourceDeck.getDeck().get(13).getBack());
        pb.setPosition(3,5);
        pb.playSide(resourceDeck.getDeck().get(20).getBack()); //questo è FUNGI
        pb.endGame();
        assertEquals(pb.getScore(), 5);
    }

    @Test
    void itemPatternFUNGIMission() throws Exception{
        Game game = new Game(2);
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getInitialDeck();
        Deck missionDeck = game.getCommonTable().getMissionDeck();
        Player p1 = new Player(3,"Bob");
        game.addPlayer(p1);
        game.setCurrentPlayer(p1);
        game.getCurrentPlayer().setPersonalBoard(initialDeck.getDeck().get(0).getFront(),
                missionDeck.getDeck().get(12), missionDeck.getDeck().get(2), missionDeck.getDeck().get(6)); //get(12) mission 2 punti per ogni 3FUNGI
        PersonalBoard pb = game.getCurrentPlayer().getPersonalBoard();

        //la carta iniziale ha una risorsa permanente INSECT
        pb.setPosition(-1,-1);
        pb.playSide(resourceDeck.getDeck().get(20).getBack()); //questi sono tutti FUNGI
        pb.setPosition(-2,-2);
        pb.playSide(resourceDeck.getDeck().get(21).getBack());
        pb.setPosition(-1,-3);
        pb.playSide(resourceDeck.getDeck().get(22).getBack());
        pb.setPosition(0,-2);
        pb.playSide(resourceDeck.getDeck().get(23).getBack());
        pb.setPosition(1,-1);
        pb.playSide(resourceDeck.getDeck().get(24).getBack()); //questo è FUNGI
        pb.setPosition(2,-2);
        pb.playSide(resourceDeck.getDeck().get(25).getBack()); //questo è FUNGI
        pb.setPosition(1,1);
        pb.playSide(resourceDeck.getDeck().get(26).getBack()); //questo è FUNGI
        pb.endGame();

        assertEquals(1,pb.getResourceQuantity(Symbol.INSECT));
        assertEquals(0,pb.getResourceQuantity(Symbol.ANIMAL));
        assertEquals(7,pb.getResourceQuantity(Symbol.FUNGI));
        assertEquals(0,pb.getResourceQuantity(Symbol.PLANT));
        assertEquals(0,pb.getResourceQuantity(Symbol.MANUSCRIPT));
        assertEquals(0,pb.getResourceQuantity(Symbol.QUILL));
        assertEquals(0,pb.getResourceQuantity(Symbol.INKWELL));

        assertEquals(4, pb.getScore());
        //check carta iniziale
        assertFalse(pb.getOccupiedPositions().get(0).getSide().getUPLEFT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(0).getSide().getUPRIGHT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(0).getSide().getDOWNLEFT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(0).getSide().getDOWNRIGHT().isHidden());

        //check carta 1
        assertFalse(pb.getOccupiedPositions().get(1).getSide().getUPLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(1).getSide().getUPRIGHT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(1).getSide().getDOWNLEFT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(1).getSide().getDOWNRIGHT().isHidden());

        //check carta 2
        assertFalse(pb.getOccupiedPositions().get(2).getSide().getUPLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(2).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(2).getSide().getDOWNLEFT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(2).getSide().getDOWNRIGHT().isHidden());
        //check carta 3
        assertFalse(pb.getOccupiedPositions().get(3).getSide().getUPLEFT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(3).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(3).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(3).getSide().getDOWNRIGHT().isHidden());
        //check carta 4
        assertFalse(pb.getOccupiedPositions().get(4).getSide().getUPLEFT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(4).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(4).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(4).getSide().getDOWNRIGHT().isHidden());
        //check carta 5
        assertFalse(pb.getOccupiedPositions().get(5).getSide().getUPLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(5).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(5).getSide().getDOWNLEFT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(5).getSide().getDOWNRIGHT().isHidden());
        //check carta 6
        assertFalse(pb.getOccupiedPositions().get(6).getSide().getUPLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(6).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(6).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(6).getSide().getDOWNRIGHT().isHidden());
        //check carta 7
        assertFalse(pb.getOccupiedPositions().get(7).getSide().getUPLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(7).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(7).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(7).getSide().getDOWNRIGHT().isHidden());




    }

    @Test
    void InitialCardBack() throws Exception {
        //random test to check if the algorithm works with the initial card backwards
        Game game = new Game(2);
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getInitialDeck();
        Deck missionDeck = game.getCommonTable().getMissionDeck();
        Player p1 = new Player(3,"Bob");
        game.addPlayer(p1);
        game.setCurrentPlayer(p1);
        game.getCurrentPlayer().setPersonalBoard(initialDeck.getDeck().get(0).getBack(),
                missionDeck.getDeck().get(0), missionDeck.getDeck().get(2), missionDeck.getDeck().get(6));
        PersonalBoard pb = game.getCurrentPlayer().getPersonalBoard();

        pb.setPosition(1,1);
        pb.playSide(resourceDeck.getDeck().get(0).getFront());
        pb.setPosition(-1, 1);
        pb.playSide(resourceDeck.getDeck().get(1).getFront());
        pb.endGame();

        assertEquals(0, pb.getScore());
        assertEquals(1, pb.getResourceQuantity(Symbol.INSECT));
        assertEquals(1, pb.getResourceQuantity(Symbol.ANIMAL));
        assertEquals(0, pb.getResourceQuantity(Symbol.FUNGI));
        assertEquals(4, pb.getResourceQuantity(Symbol.PLANT));
        assertEquals(0, pb.getResourceQuantity(Symbol.MANUSCRIPT));
        assertEquals(0, pb.getResourceQuantity(Symbol.QUILL));
        assertEquals(0, pb.getResourceQuantity(Symbol.INKWELL) );
        //initial card
        assertTrue(pb.getOccupiedPositions().get(0).getSide().getUPLEFT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(0).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(0).getSide().getDOWNLEFT().isHidden());

        //First resource card
        assertFalse(pb.getOccupiedPositions().get(1).getSide().getUPLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(1).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(1).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(1).getSide().getDOWNRIGHT().isHidden());

        //Second resource card
        assertFalse(pb.getOccupiedPositions().get(2).getSide().getUPLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(2).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(2).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(2).getSide().getDOWNRIGHT().isHidden());

    }

}