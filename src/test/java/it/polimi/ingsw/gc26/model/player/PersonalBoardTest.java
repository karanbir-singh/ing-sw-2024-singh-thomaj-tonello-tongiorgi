package it.polimi.ingsw.gc26.model.player;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.card_side.Symbol;
import it.polimi.ingsw.gc26.model.deck.Deck;
import it.polimi.ingsw.gc26.model.hand.Hand;
import it.polimi.ingsw.gc26.model.game.Game;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PersonalBoardTest {

    @Test
    void firstPlaySide() throws Exception {
        Game game = new Game(2);
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getStarterDeck();
        Deck missionDeck = game.getCommonTable().getMissionDeck();
        Player p1 = new Player(3,"Bob");
        game.addPlayer(p1);
        game.setCurrentPlayer(p1);
        game.getCurrentPlayer().createPersonalBoard(initialDeck.getCards().get(0).getFront());
        PersonalBoard pb = game.getCurrentPlayer().getPersonalBoard();


        pb.setPosition(1,1);
        pb.playSide(resourceDeck.getCards().get(0).getFront());
        pb.setPosition(2,2);
        pb.playSide(resourceDeck.getCards().get(1).getFront());
        pb.setPosition(0,2);
        pb.playSide(goldDeck.getCards().get(0).getFront());
        pb.setPosition(1,3);
        pb.playSide(goldDeck.getCards().get(3).getFront());
        pb.setPosition(3,3);
        pb.playSide(resourceDeck.getCards().get(2).getBack());


        assertEquals( 2,pb.getResourceQuantity(Symbol.INSECT) );
        assertEquals( 2, pb.getResourceQuantity(Symbol.PLANT));
        assertEquals(0,pb.getResourceQuantity(Symbol.FUNGI));
        assertEquals(0, pb.getResourceQuantity(Symbol.ANIMAL) );
        assertEquals( 1, pb.getResourceQuantity(Symbol.QUILL) );
        assertEquals(0, pb.getResourceQuantity(Symbol.MANUSCRIPT));
        assertEquals( 0, pb.getResourceQuantity(Symbol.INKWELL) );
        assertEquals( 5, pb.getScore());

        assertTrue(pb.getOccupiedPositions().get(0).getSide().getUPRIGHT().isHidden());

        assertTrue(pb.getOccupiedPositions().get(1).getSide().getUPRIGHT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(1).getSide().getUPLEFT().isHidden());

        assertTrue(pb.getOccupiedPositions().get(2).getSide().getUPRIGHT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(2).getSide().getUPLEFT().isHidden());

        assertTrue(pb.getOccupiedPositions().get(3).getSide().getUPRIGHT().isHidden());
        pb.showBoard();


    }
    @Test
    void secondPlaySide() throws Exception {
        Game game = new Game(2);
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getStarterDeck();
        Deck missionDeck = game.getCommonTable().getMissionDeck();
        Player p1 = new Player(3,"Bob");
        game.addPlayer(p1);
        game.setCurrentPlayer(p1);
        game.getCurrentPlayer().createPersonalBoard(initialDeck.getCards().get(5).getFront());
        PersonalBoard pb = game.getCurrentPlayer().getPersonalBoard();


        pb.setPosition(-1,1);
        pb.playSide(resourceDeck.getCards().get(39).getFront());
        pb.setPosition(-2,0);
        pb.playSide(resourceDeck.getCards().get(38).getFront());
        pb.setPosition(-3,1);
        pb.playSide(resourceDeck.getCards().get(36).getFront());
        pb.setPosition(-4,0);
        pb.playSide(goldDeck.getCards().get(38).getFront());
        //non conto le risorse permanenti dei back delle carte, aspetto gabi
        assertEquals( 3,pb.getResourceQuantity(Symbol.INSECT));
        assertEquals(2,pb.getResourceQuantity(Symbol.PLANT));
        assertEquals(1,pb.getResourceQuantity(Symbol.FUNGI) );
        assertEquals( 1,pb.getResourceQuantity(Symbol.ANIMAL));
        assertEquals(1,pb.getResourceQuantity(Symbol.QUILL));
        assertEquals(0, pb.getResourceQuantity(Symbol.MANUSCRIPT) );
        assertEquals( 0, pb.getResourceQuantity(Symbol.INKWELL));
        assertEquals(5, pb.getScore());

        assertTrue(pb.getOccupiedPositions().get(0).getSide().getUPLEFT().isHidden());

        assertTrue(pb.getOccupiedPositions().get(1).getSide().getDOWNLEFT().isHidden());


        assertTrue(pb.getOccupiedPositions().get(2).getSide().getUPLEFT().isHidden());

        assertTrue(pb.getOccupiedPositions().get(3).getSide().getDOWNLEFT().isHidden());
        pb.showBoard();


    }

    @Test
    void Diagonal6PlantCombinationMission() throws Exception{
        Game game = new Game(2);
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getStarterDeck();
        Deck missionDeck = game.getCommonTable().getMissionDeck();
        Player p1 = new Player(3,"Bob");
        game.addPlayer(p1);
        game.setCurrentPlayer(p1);
        game.getCurrentPlayer().createPersonalBoard(initialDeck.getCards().get(0).getFront());
        game.getCurrentPlayer().getPersonalBoard().setSecretMission(missionDeck.getCards().get(1));
        PersonalBoard pb = game.getCurrentPlayer().getPersonalBoard();
        //get(1) è la missione corretta

        pb.setPosition(1,-1);
        pb.playSide(resourceDeck.getCards().get(0).getBack());
        pb.setPosition(2,-2);
        pb.playSide(resourceDeck.getCards().get(1).getBack());
        pb.setPosition(3,-3);
        pb.playSide(resourceDeck.getCards().get(2).getBack());
        pb.setPosition(4,-4);
        pb.playSide(resourceDeck.getCards().get(3).getBack());
        pb.setPosition(5,-5);
        pb.playSide(resourceDeck.getCards().get(4).getBack());
        pb.setPosition(6,-6);
        pb.playSide(resourceDeck.getCards().get(5).getBack());
        ArrayList<Card> commonMissions = new ArrayList<>();
        commonMissions.add(missionDeck.getCards().get(6));
        commonMissions.add(missionDeck.getCards().get(3));
        pb.endGame(commonMissions);
        assertEquals( 4, pb.getScore());
        pb.showBoard();
    }

    /**
     * test to check that, when two diagonal combination overlap, the resource and gold cards are considered just once
     * @throws Exception
     */
    @Test
    void OverlappingDiagonalCombinationMission() throws Exception{
        Game game = new Game(2);
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getStarterDeck();
        Deck missionDeck = game.getCommonTable().getMissionDeck();
        Player p1 = new Player(3,"Bob");
        game.addPlayer(p1);
        game.setCurrentPlayer(p1);
        game.getCurrentPlayer().createPersonalBoard(initialDeck.getCards().get(0).getFront());
        game.getCurrentPlayer().getPersonalBoard().setSecretMission(missionDeck.getCards().get(1));
        PersonalBoard pb = game.getCurrentPlayer().getPersonalBoard();
        //get(1) è la missione corretta

        pb.setPosition(1,-1);
        pb.playSide(resourceDeck.getCards().get(0).getBack());
        pb.setPosition(2,-2);
        pb.playSide(resourceDeck.getCards().get(1).getBack());
        pb.setPosition(3,-3);
        pb.playSide(resourceDeck.getCards().get(2).getBack());
        pb.setPosition(4,-4);
        pb.playSide(resourceDeck.getCards().get(3).getBack());
        pb.setPosition(5,-5);
        pb.playSide(resourceDeck.getCards().get(4).getBack());

        ArrayList<Card> commonMissions = new ArrayList<>();
        commonMissions.add(missionDeck.getCards().get(6));
        commonMissions.add(missionDeck.getCards().get(3));
        pb.endGame(commonMissions);
        assertEquals( 2, pb.getScore());
        pb.showBoard();
    }

    @Test
    void DiagonalAndLpatternCombinationMission() throws Exception{
        Game game = new Game(2);
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getStarterDeck();
        Deck missionDeck = game.getCommonTable().getMissionDeck();
        Player p1 = new Player(3,"Bob");
        game.addPlayer(p1);
        game.setCurrentPlayer(p1);
        game.getCurrentPlayer().createPersonalBoard(initialDeck.getCards().get(0).getFront());
        game.getCurrentPlayer().getPersonalBoard().setSecretMission(missionDeck.getCards().get(0));
        PersonalBoard pb = game.getCurrentPlayer().getPersonalBoard(); //get(2) per diagonale e get(6) per L
        //usiamo animals e un fungi in alto a destra

        pb.setPosition(1,1);
        pb.playSide(resourceDeck.getCards().get(10).getBack()); //questi sono tutti ANIMALS
        pb.setPosition(2,2);
        pb.playSide(resourceDeck.getCards().get(11).getBack());
        pb.setPosition(3,3);
        pb.playSide(resourceDeck.getCards().get(12).getBack());
        pb.setPosition(2,4);
        pb.playSide(resourceDeck.getCards().get(13).getBack());
        pb.setPosition(3,5);
        pb.playSide(resourceDeck.getCards().get(20).getBack()); //questo è FUNGI
        ArrayList<Card> commonMissions = new ArrayList<>();
        commonMissions.add(missionDeck.getCards().get(2));
        commonMissions.add(missionDeck.getCards().get(6));
        pb.endGame(commonMissions);
        assertEquals( 5,pb.getScore());
        pb.showBoard();
    }

    @Test
    void itemPatternFUNGIMission() throws Exception{
        Game game = new Game(2);
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getStarterDeck();
        Deck missionDeck = game.getCommonTable().getMissionDeck();
        Player p1 = new Player(3,"Bob");
        game.addPlayer(p1);
        game.setCurrentPlayer(p1);
        game.getCurrentPlayer().createPersonalBoard(initialDeck.getCards().get(0).getFront()); //get(12) mission 2 punti per ogni 3FUNGI
        game.getCurrentPlayer().getPersonalBoard().setSecretMission(missionDeck.getCards().get(12));
        PersonalBoard pb = game.getCurrentPlayer().getPersonalBoard();

        //la carta iniziale ha una risorsa permanente INSECT
        pb.setPosition(-1,-1);
        pb.playSide(resourceDeck.getCards().get(20).getBack()); //questi sono tutti FUNGI
        pb.setPosition(-2,-2);
        pb.playSide(resourceDeck.getCards().get(21).getBack());
        pb.setPosition(-1,-3);
        pb.playSide(resourceDeck.getCards().get(22).getBack());
        pb.setPosition(0,-2);
        pb.playSide(resourceDeck.getCards().get(23).getBack());
        pb.setPosition(1,-1);
        pb.playSide(resourceDeck.getCards().get(24).getBack()); //questo è FUNGI
        pb.setPosition(2,-2);
        pb.playSide(resourceDeck.getCards().get(25).getBack()); //questo è FUNGI
        pb.setPosition(1,1);
        pb.playSide(resourceDeck.getCards().get(26).getBack()); //questo è FUNGI
        ArrayList<Card> commonMissions = new ArrayList<>();
        commonMissions.add(missionDeck.getCards().get(2));
        commonMissions.add(missionDeck.getCards().get(6));
        pb.endGame(commonMissions);

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
        pb.showBoard();

    }

    @Test
    void overlappingTwoLPattern() throws Exception{
        Game game = new Game(2);
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getStarterDeck();
        Deck missionDeck = game.getCommonTable().getMissionDeck();
        Player p1 = new Player(3,"Bob");
        game.addPlayer(p1);
        game.setCurrentPlayer(p1);
        game.getCurrentPlayer().createPersonalBoard(initialDeck.getCards().get(0).getFront()); //get(12) mission 2 punti per ogni 3FUNGI
        game.getCurrentPlayer().getPersonalBoard().setSecretMission(missionDeck.getCards().get(7));
        PersonalBoard pb = game.getCurrentPlayer().getPersonalBoard();
        //missione L pattern tipo 4 con ANIMAL e INSECT e INSECT

        //la carta iniziale ha una risorsa permanente INSECT
        pb.setPosition(1,-1); //Tipo ANIMAL
        pb.playSide(resourceDeck.getCards().get(10).getBack());
        pb.setPosition(2,-2);//tipo INSECT
        pb.playSide(resourceDeck.getCards().get(30).getBack());
        pb.setPosition(3,-3);//tipo INSECT
        pb.playSide(resourceDeck.getCards().get(31).getBack());
        pb.setPosition(2,-4);//tipo INSECT
        pb.playSide(resourceDeck.getCards().get(32).getBack());
        pb.setPosition(2,0);//tipo INSECT
        pb.playSide(resourceDeck.getCards().get(33).getBack());
        pb.setPosition(1,1);//tipo ANIMAL
        pb.playSide(resourceDeck.getCards().get(11).getBack());
        ArrayList<Card> commonMissions = new ArrayList<>();
        commonMissions.add(missionDeck.getCards().get(1));
        commonMissions.add(missionDeck.getCards().get(0));
        pb.endGame(commonMissions);

        assertEquals(6,pb.getResourceQuantity(Symbol.INSECT)); // a causa della carta iniziale
        assertEquals(2,pb.getResourceQuantity(Symbol.ANIMAL));
        assertEquals(0,pb.getResourceQuantity(Symbol.FUNGI));
        assertEquals(0,pb.getResourceQuantity(Symbol.PLANT));
        assertEquals(0,pb.getResourceQuantity(Symbol.MANUSCRIPT));
        assertEquals(0,pb.getResourceQuantity(Symbol.QUILL));
        assertEquals(0,pb.getResourceQuantity(Symbol.INKWELL));

        assertEquals(3, pb.getScore());
        //check carta iniziale
        assertFalse(pb.getOccupiedPositions().get(0).getSide().getUPLEFT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(0).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(0).getSide().getDOWNLEFT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(0).getSide().getDOWNRIGHT().isHidden());

        //check carta 1
        assertFalse(pb.getOccupiedPositions().get(1).getSide().getUPLEFT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(1).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(1).getSide().getDOWNLEFT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(1).getSide().getDOWNRIGHT().isHidden());

        //check carta 2
        assertFalse(pb.getOccupiedPositions().get(2).getSide().getUPLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(2).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(2).getSide().getDOWNLEFT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(2).getSide().getDOWNRIGHT().isHidden());
        //check carta 3
        assertFalse(pb.getOccupiedPositions().get(3).getSide().getUPLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(3).getSide().getUPRIGHT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(3).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(3).getSide().getDOWNRIGHT().isHidden());
        //check carta 4
        assertFalse(pb.getOccupiedPositions().get(4).getSide().getUPLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(4).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(4).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(4).getSide().getDOWNRIGHT().isHidden());
        //check carta 5
        assertTrue(pb.getOccupiedPositions().get(5).getSide().getUPLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(5).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(5).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(5).getSide().getDOWNRIGHT().isHidden());
        //check carta 6
        assertFalse(pb.getOccupiedPositions().get(6).getSide().getUPLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(6).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(6).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(6).getSide().getDOWNRIGHT().isHidden());
        pb.showBoard();
    }

    @Test
    void InitialCardBack() throws Exception {
        //random test to check if the algorithm works with the initial card backwards
        Game game = new Game(2);
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getStarterDeck();
        Deck missionDeck = game.getCommonTable().getMissionDeck();
        Player p1 = new Player(3,"Bob");
        game.addPlayer(p1);
        game.setCurrentPlayer(p1);
        game.getCurrentPlayer().createPersonalBoard(initialDeck.getCards().get(0).getBack());
        game.getCurrentPlayer().getPersonalBoard().setSecretMission(missionDeck.getCards().get(0));

        PersonalBoard pb = game.getCurrentPlayer().getPersonalBoard();

        pb.setPosition(1,1);
        pb.playSide(resourceDeck.getCards().get(0).getFront());
        pb.setPosition(-1, 1);
        pb.playSide(resourceDeck.getCards().get(1).getFront());
        ArrayList<Card> commonMissions = new ArrayList<>();
        commonMissions.add(missionDeck.getCards().get(2));
        commonMissions.add(missionDeck.getCards().get(6));
        pb.endGame(commonMissions);

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
        pb.showBoard();

    }


    /**
     * test where we check a particular case where a position is blocked for one card but playable for another card like
     *   X
     * C   C
     * and also I tested the use of a gold card with points.
     * @throws Exception
     */
    @Test
    void NonPlayablePositionAndBlockedPositionTest() throws Exception{
        //TODO TO COMPLETE
        Game game = new Game(2);
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getStarterDeck();
        Deck missionDeck = game.getCommonTable().getMissionDeck();
        Player p1 = new Player(3,"Bob");
        game.addPlayer(p1);
        game.setCurrentPlayer(p1);
        game.getCurrentPlayer().createPersonalBoard(initialDeck.getCards().get(0).getFront()); //get(12) mission 2 punti per ogni 3FUNGI
        game.getCurrentPlayer().getPersonalBoard().setSecretMission(missionDeck.getCards().get(7));
        PersonalBoard pb = game.getCurrentPlayer().getPersonalBoard();
        //missione L pattern tipo 4 con ANIMAL e INSECT e INSECT

        //la carta iniziale ha una risorsa permanente INSECT
        pb.setPosition(1,1); //Tipo PLANT
        pb.playSide(resourceDeck.getCards().get(10).getBack());
        pb.setPosition(-1,1);//tipo PLANT
        pb.playSide(resourceDeck.getCards().get(11).getBack());
        pb.setPosition(1,-1);//tipo plant
        pb.playSide(resourceDeck.getCards().get(12).getBack());
        pb.setPosition(2,0);//tipo plant
        pb.playSide(goldDeck.getCards().get(15).getFront()); //carta che da 1 punto per ogni animal
        //pb.playSide(resourceDeck.getCards().get(4).getBack());
        ArrayList<Card> commonMissions = new ArrayList<>();
        commonMissions.add(missionDeck.getCards().get(1));
        commonMissions.add(missionDeck.getCards().get(0));
        pb.endGame(commonMissions);

        assertEquals(0,pb.getResourceQuantity(Symbol.INSECT)); // a causa della carta iniziale
        assertEquals(1,pb.getResourceQuantity(Symbol.ANIMAL));
        assertEquals(1,pb.getResourceQuantity(Symbol.FUNGI));
        assertEquals(4,pb.getResourceQuantity(Symbol.PLANT));
        assertEquals(0,pb.getResourceQuantity(Symbol.MANUSCRIPT));
        assertEquals(0,pb.getResourceQuantity(Symbol.QUILL));
        assertEquals(1,pb.getResourceQuantity(Symbol.INKWELL));

        assertEquals(3, pb.getScore());
        //check carta iniziale
        assertTrue(pb.getOccupiedPositions().get(0).getSide().getUPLEFT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(0).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(0).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(0).getSide().getDOWNRIGHT().isHidden());

        //check carta 1
        assertTrue(pb.getOccupiedPositions().get(1).getSide().getUPLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(1).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(1).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(1).getSide().getDOWNRIGHT().isHidden());

        //check carta 2
        assertTrue(pb.getOccupiedPositions().get(2).getSide().getUPLEFT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(2).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(2).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(2).getSide().getDOWNRIGHT().isHidden());
        //check carta 3
        assertFalse(pb.getOccupiedPositions().get(3).getSide().getUPLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(3).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(3).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(3).getSide().getDOWNRIGHT().isHidden());
        //check carta 4
        assertFalse(pb.getOccupiedPositions().get(4).getSide().getUPLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(4).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(4).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(4).getSide().getDOWNRIGHT().isHidden());
        pb.showBoard();
    }

    /**
     * test to check the case where the goldCard that assigns the points proportionally to the covered corners is played, covering 4 corners
     * @throws Exception
     */
    @Test
    void fourCorners() throws Exception{
        Game game = new Game(2);
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getStarterDeck();
        Deck missionDeck = game.getCommonTable().getMissionDeck();
        Player p1 = new Player(3,"Bob");
        game.addPlayer(p1);
        game.setCurrentPlayer(p1);
        game.getCurrentPlayer().createPersonalBoard(initialDeck.getCards().get(0).getBack());
        game.getCurrentPlayer().getPersonalBoard().setSecretMission(missionDeck.getCards().get(0));

        PersonalBoard pb = game.getCurrentPlayer().getPersonalBoard();

        pb.setPosition(1,1);
        pb.playSide(resourceDeck.getCards().get(8).getFront());
        pb.setPosition(-1, 1);
        pb.playSide(resourceDeck.getCards().get(0).getBack());
        pb.setPosition(2,2);
        pb.playSide(resourceDeck.getCards().get(33).getFront());
        pb.setPosition(-2, 2);
        pb.playSide(resourceDeck.getCards().get(2).getBack());
        pb.setPosition(-1, 3);
        pb.playSide(resourceDeck.getCards().get(9).getFront());
        pb.setPosition(1,3);
        pb.playSide(goldDeck.getCards().get(6).getFront());
        pb.setPosition(0,2);
        pb.playSide(goldDeck.getCards().get(3).getFront());


        assertEquals(13, pb.getScore());
        assertEquals(3, pb.getResourceQuantity(Symbol.INSECT));
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
        assertFalse(pb.getOccupiedPositions().get(0).getSide().getDOWNRIGHT().isHidden());

        //Card in 1,1
        assertTrue(pb.getOccupiedPositions().get(1).getSide().getUPLEFT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(1).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(1).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(1).getSide().getDOWNRIGHT().isHidden());

        //Card in -1,1
        assertTrue(pb.getOccupiedPositions().get(2).getSide().getUPRIGHT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(2).getSide().getUPLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(2).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(2).getSide().getDOWNRIGHT().isHidden());

        //Card in 2,2
        assertFalse(pb.getOccupiedPositions().get(3).getSide().getUPRIGHT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(3).getSide().getUPLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(3).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(3).getSide().getDOWNRIGHT().isHidden());

        //Card in -2,2
        assertFalse(pb.getOccupiedPositions().get(4).getSide().getUPLEFT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(4).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(4).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(4).getSide().getDOWNRIGHT().isHidden());

        //Card in -1,3
        assertFalse(pb.getOccupiedPositions().get(5).getSide().getUPLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(5).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(5).getSide().getDOWNLEFT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(5).getSide().getDOWNRIGHT().isHidden());

        //Card in 1,3
        assertFalse(pb.getOccupiedPositions().get(6).getSide().getUPLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(6).getSide().getUPRIGHT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(6).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(6).getSide().getDOWNRIGHT().isHidden());

        //Card in 0,2
        assertFalse(pb.getOccupiedPositions().get(7).getSide().getUPLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(7).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(7).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(7).getSide().getDOWNRIGHT().isHidden());

        pb.showBoard();
    }



}