package it.polimi.ingsw.gc26.model.player;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.card_side.Symbol;
import it.polimi.ingsw.gc26.model.game.Deck;
import it.polimi.ingsw.gc26.model.game.Game;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PersonalBoardTest {
    private static Game game;

    private static ArrayList<Player> players;

    static void beforeAll() {
        // Create players
        players = new ArrayList<>();
        players.add(new Player("0", "Pippo"));
        players.add(new Player("1", "Baudo"));
        players.add(new Player("2", "Carlo"));

        // Create game controller
        game = new Game(players, new ArrayList<>());
    }

    @Test
    void firstPlaySide() {
        beforeAll();
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getStarterDeck();
        Deck missionDeck = game.getCommonTable().getMissionDeck();
        Player p1 = players.getFirst();
        game.setCurrentPlayer(p1);
        game.getCurrentPlayer().createPersonalBoard();
        PersonalBoard pb = game.getCurrentPlayer().getPersonalBoard();
        pb.playSide(initialDeck.getCards().get(0).getFront(), game.getCurrentPlayer().getID());

        pb.setPosition(1, 1, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(0).getFront(), game.getCurrentPlayer().getID());
        pb.setPosition(2, 2, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(1).getFront(), game.getCurrentPlayer().getID());
        pb.setPosition(0, 2, game.getCurrentPlayer().getID());
        pb.playSide(goldDeck.getCards().get(0).getFront(), game.getCurrentPlayer().getID());
        pb.setPosition(1, 3, game.getCurrentPlayer().getID());
        pb.playSide(goldDeck.getCards().get(3).getFront(), game.getCurrentPlayer().getID());
        pb.setPosition(3, 3, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(2).getBack(), game.getCurrentPlayer().getID());


        assertEquals(2, pb.getResourceQuantity(Symbol.INSECT));
        assertEquals(2, pb.getResourceQuantity(Symbol.PLANT));
        assertEquals(0, pb.getResourceQuantity(Symbol.FUNGI));
        assertEquals(0, pb.getResourceQuantity(Symbol.ANIMAL));
        assertEquals(1, pb.getResourceQuantity(Symbol.QUILL));
        assertEquals(0, pb.getResourceQuantity(Symbol.MANUSCRIPT));
        assertEquals(0, pb.getResourceQuantity(Symbol.INKWELL));
        assertEquals(5, pb.getScore());

        assertTrue(pb.getOccupiedPositions().get(0).getSide().getUPRIGHT().isHidden());

        assertTrue(pb.getOccupiedPositions().get(1).getSide().getUPRIGHT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(1).getSide().getUPLEFT().isHidden());

        assertTrue(pb.getOccupiedPositions().get(2).getSide().getUPRIGHT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(2).getSide().getUPLEFT().isHidden());

        assertTrue(pb.getOccupiedPositions().get(3).getSide().getUPRIGHT().isHidden());
    }

    @Test
    void secondPlaySide() {
        beforeAll();
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getStarterDeck();
        Deck missionDeck = game.getCommonTable().getMissionDeck();
        Player p1 = players.getFirst();
        game.setCurrentPlayer(p1);
        game.getCurrentPlayer().createPersonalBoard();
        PersonalBoard pb = game.getCurrentPlayer().getPersonalBoard();
        pb.playSide(initialDeck.getCards().get(5).getFront(), game.getCurrentPlayer().getID());

        pb.setPosition(-1, 1, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(39).getFront(), game.getCurrentPlayer().getID());
        pb.setPosition(-2, 0, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(38).getFront(), game.getCurrentPlayer().getID());
        pb.setPosition(-3, 1, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(36).getFront(), game.getCurrentPlayer().getID());
        pb.setPosition(-4, 0, game.getCurrentPlayer().getID());
        pb.playSide(goldDeck.getCards().get(38).getFront(), game.getCurrentPlayer().getID());
        //non conto le risorse permanenti dei back delle carte, aspetto gabi
        assertEquals(3, pb.getResourceQuantity(Symbol.INSECT));
        assertEquals(2, pb.getResourceQuantity(Symbol.PLANT));
        assertEquals(1, pb.getResourceQuantity(Symbol.FUNGI));
        assertEquals(1, pb.getResourceQuantity(Symbol.ANIMAL));
        assertEquals(1, pb.getResourceQuantity(Symbol.QUILL));
        assertEquals(0, pb.getResourceQuantity(Symbol.MANUSCRIPT));
        assertEquals(0, pb.getResourceQuantity(Symbol.INKWELL));
        assertEquals(5, pb.getScore());

        assertTrue(pb.getOccupiedPositions().get(0).getSide().getUPLEFT().isHidden());

        assertTrue(pb.getOccupiedPositions().get(1).getSide().getDOWNLEFT().isHidden());


        assertTrue(pb.getOccupiedPositions().get(2).getSide().getUPLEFT().isHidden());

        assertTrue(pb.getOccupiedPositions().get(3).getSide().getDOWNLEFT().isHidden());
    }

    @Test
    void Diagonal6PlantCombinationMission() {
        beforeAll();
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getStarterDeck();
        Deck missionDeck = game.getCommonTable().getMissionDeck();
        Player p1 = players.getFirst();
        game.setCurrentPlayer(p1);
        game.getCurrentPlayer().createPersonalBoard();
        game.getCurrentPlayer().getPersonalBoard().setSecretMission(Optional.of(missionDeck.getCards().get(1)), game.getCurrentPlayer().getID());
        PersonalBoard pb = game.getCurrentPlayer().getPersonalBoard();
        pb.playSide(initialDeck.getCards().get(0).getFront(), game.getCurrentPlayer().getID());
        //get(1) è la missione corretta

        pb.setPosition(1, -1, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(0).getBack(), game.getCurrentPlayer().getID());
        pb.setPosition(2, -2, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(1).getBack(), game.getCurrentPlayer().getID());
        pb.setPosition(3, -3, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(2).getBack(), game.getCurrentPlayer().getID());
        pb.setPosition(4, -4, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(3).getBack(), game.getCurrentPlayer().getID());
        pb.setPosition(5, -5, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(4).getBack(), game.getCurrentPlayer().getID());
        pb.setPosition(6, -6, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(5).getBack(), game.getCurrentPlayer().getID());
        ArrayList<Card> commonMissions = new ArrayList<>();
        commonMissions.add(missionDeck.getCards().get(6));
        commonMissions.add(missionDeck.getCards().get(3));
        pb.endGame(commonMissions);
        assertEquals(4, pb.getScore());
    }

    @Test
    void OverlappingDiagonalCombinationMission() {
        beforeAll();
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getStarterDeck();
        Deck missionDeck = game.getCommonTable().getMissionDeck();
        Player p1 = players.getFirst();
        game.setCurrentPlayer(p1);
        game.getCurrentPlayer().createPersonalBoard();
        game.getCurrentPlayer().getPersonalBoard().setSecretMission(Optional.of(missionDeck.getCards().get(1)), game.getCurrentPlayer().getID());
        PersonalBoard pb = game.getCurrentPlayer().getPersonalBoard();
        pb.playSide(initialDeck.getCards().get(0).getFront(), game.getCurrentPlayer().getID());

        pb.setPosition(1, -1, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(0).getBack(), game.getCurrentPlayer().getID());
        pb.setPosition(2, -2, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(1).getBack(), game.getCurrentPlayer().getID());
        pb.setPosition(3, -3, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(2).getBack(), game.getCurrentPlayer().getID());
        pb.setPosition(4, -4, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(3).getBack(), game.getCurrentPlayer().getID());
        pb.setPosition(5, -5, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(4).getBack(), game.getCurrentPlayer().getID());
        pb.setPosition(6, -6, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(5).getBack(), game.getCurrentPlayer().getID());
        pb.setPosition(7, -7, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(6).getBack(), game.getCurrentPlayer().getID());
        pb.setPosition(8, -8, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(7).getBack(), game.getCurrentPlayer().getID());
        pb.setPosition(9, -9, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(8).getBack(), game.getCurrentPlayer().getID());

        ArrayList<Card> commonMissions = new ArrayList<>();
        commonMissions.add(missionDeck.getCards().get(6));
        commonMissions.add(missionDeck.getCards().get(3));
        pb.endGame(commonMissions);
        assertEquals(6, pb.getScore());
    }


    @Test
    void DiagonalAndLpatternCombinationMission() {
        beforeAll();
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getStarterDeck();
        Deck missionDeck = game.getCommonTable().getMissionDeck();
        Player p1 = players.getFirst();
        game.setCurrentPlayer(p1);
        game.getCurrentPlayer().createPersonalBoard();
        game.getCurrentPlayer().getPersonalBoard().setSecretMission(Optional.of(missionDeck.getCards().get(0)), game.getCurrentPlayer().getID());
        PersonalBoard pb = game.getCurrentPlayer().getPersonalBoard(); //get(2) per diagonale e get(6) per L
        pb.playSide(initialDeck.getCards().get(0).getFront(), game.getCurrentPlayer().getID());
        //usiamo animals e un fungi in alto a destra

        pb.setPosition(1, 1, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(10).getBack(), game.getCurrentPlayer().getID()); //questi sono tutti ANIMALS
        pb.setPosition(2, 2, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(11).getBack(), game.getCurrentPlayer().getID());
        pb.setPosition(3, 3, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(12).getBack(), game.getCurrentPlayer().getID());
        pb.setPosition(2, 4, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(13).getBack(), game.getCurrentPlayer().getID());
        pb.setPosition(3, 5, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(20).getBack(), game.getCurrentPlayer().getID()); //questo è FUNGI
        ArrayList<Card> commonMissions = new ArrayList<>();
        commonMissions.add(missionDeck.getCards().get(2));
        commonMissions.add(missionDeck.getCards().get(6));
        pb.endGame(commonMissions);
        assertEquals(5, pb.getScore());
    }


    @Test
    void itemPatternFUNGIMission() {
        beforeAll();
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getStarterDeck();
        Deck missionDeck = game.getCommonTable().getMissionDeck();
        Player p1 = players.getFirst();
        game.setCurrentPlayer(p1);
        game.getCurrentPlayer().createPersonalBoard(); //get(12) mission 2 punti per ogni 3FUNGI
        game.getCurrentPlayer().getPersonalBoard().setSecretMission(Optional.of(missionDeck.getCards().get(12)), game.getCurrentPlayer().getID());
        PersonalBoard pb = game.getCurrentPlayer().getPersonalBoard();
        pb.playSide(initialDeck.getCards().get(0).getFront(), game.getCurrentPlayer().getID());

        //la carta iniziale ha una risorsa permanente INSECT
        pb.setPosition(-1, -1, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(20).getBack(), game.getCurrentPlayer().getID()); //questi sono tutti FUNGI
        pb.setPosition(-2, -2, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(21).getBack(), game.getCurrentPlayer().getID());
        pb.setPosition(-1, -3, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(22).getBack(), game.getCurrentPlayer().getID());
        pb.setPosition(0, -2, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(23).getBack(), game.getCurrentPlayer().getID());
        pb.setPosition(1, -1, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(24).getBack(), game.getCurrentPlayer().getID()); //questo è FUNGI
        pb.setPosition(2, -2, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(25).getBack(), game.getCurrentPlayer().getID()); //questo è FUNGI
        pb.setPosition(1, 1, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(26).getBack(), game.getCurrentPlayer().getID()); //questo è FUNGI
        ArrayList<Card> commonMissions = new ArrayList<>();
        commonMissions.add(missionDeck.getCards().get(2));
        commonMissions.add(missionDeck.getCards().get(6));
        pb.endGame(commonMissions);

        assertEquals(1, pb.getResourceQuantity(Symbol.INSECT));
        assertEquals(0, pb.getResourceQuantity(Symbol.ANIMAL));
        assertEquals(7, pb.getResourceQuantity(Symbol.FUNGI));
        assertEquals(0, pb.getResourceQuantity(Symbol.PLANT));
        assertEquals(0, pb.getResourceQuantity(Symbol.MANUSCRIPT));
        assertEquals(0, pb.getResourceQuantity(Symbol.QUILL));
        assertEquals(0, pb.getResourceQuantity(Symbol.INKWELL));

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
    void overlappingTwoLPattern() {
        beforeAll();
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getStarterDeck();
        Deck missionDeck = game.getCommonTable().getMissionDeck();
        Player p1 = players.getFirst();
        game.setCurrentPlayer(p1);
        game.getCurrentPlayer().createPersonalBoard(); //get(12) mission 2 punti per ogni 3FUNGI
        game.getCurrentPlayer().getPersonalBoard().setSecretMission(Optional.of(missionDeck.getCards().get(7)), game.getCurrentPlayer().getID());
        PersonalBoard pb = game.getCurrentPlayer().getPersonalBoard();
        pb.playSide(initialDeck.getCards().get(0).getFront(), game.getCurrentPlayer().getID());
        //missione L pattern tipo 4 con ANIMAL e INSECT e INSECT

        //la carta iniziale ha una risorsa permanente INSECT
        pb.setPosition(1, -1, game.getCurrentPlayer().getID()); //Tipo ANIMAL
        pb.playSide(resourceDeck.getCards().get(10).getBack(), game.getCurrentPlayer().getID());
        pb.setPosition(2, -2, game.getCurrentPlayer().getID());//tipo INSECT
        pb.playSide(resourceDeck.getCards().get(30).getBack(), game.getCurrentPlayer().getID());
        pb.setPosition(3, -3, game.getCurrentPlayer().getID());//tipo INSECT
        pb.playSide(resourceDeck.getCards().get(31).getBack(), game.getCurrentPlayer().getID());
        pb.setPosition(2, -4, game.getCurrentPlayer().getID());//tipo INSECT
        pb.playSide(resourceDeck.getCards().get(32).getBack(), game.getCurrentPlayer().getID());
        pb.setPosition(2, 0, game.getCurrentPlayer().getID());//tipo INSECT
        pb.playSide(resourceDeck.getCards().get(33).getBack(), game.getCurrentPlayer().getID());
        pb.setPosition(1, 1, game.getCurrentPlayer().getID());//tipo ANIMAL
        pb.playSide(resourceDeck.getCards().get(11).getBack(), game.getCurrentPlayer().getID());
        ArrayList<Card> commonMissions = new ArrayList<>();
        commonMissions.add(missionDeck.getCards().get(1));
        commonMissions.add(missionDeck.getCards().get(0));
        pb.endGame(commonMissions);

        assertEquals(6, pb.getResourceQuantity(Symbol.INSECT)); // a causa della carta iniziale
        assertEquals(2, pb.getResourceQuantity(Symbol.ANIMAL));
        assertEquals(0, pb.getResourceQuantity(Symbol.FUNGI));
        assertEquals(0, pb.getResourceQuantity(Symbol.PLANT));
        assertEquals(0, pb.getResourceQuantity(Symbol.MANUSCRIPT));
        assertEquals(0, pb.getResourceQuantity(Symbol.QUILL));
        assertEquals(0, pb.getResourceQuantity(Symbol.INKWELL));

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
    }


    @Test
    void InitialCardBack() {
        //random test to check if the algorithm works with the initial card backwards
        beforeAll();
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getStarterDeck();
        Deck missionDeck = game.getCommonTable().getMissionDeck();
        Player p1 = players.getFirst();
        game.setCurrentPlayer(p1);
        game.getCurrentPlayer().createPersonalBoard();
        game.getCurrentPlayer().getPersonalBoard().setSecretMission(Optional.of(missionDeck.getCards().get(0)), game.getCurrentPlayer().getID());

        PersonalBoard pb = game.getCurrentPlayer().getPersonalBoard();
        pb.playSide(initialDeck.getCards().get(0).getBack(), game.getCurrentPlayer().getID());

        pb.setPosition(1, 1, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(0).getFront(), game.getCurrentPlayer().getID());
        pb.setPosition(-1, 1, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(1).getFront(), game.getCurrentPlayer().getID());
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
        assertEquals(0, pb.getResourceQuantity(Symbol.INKWELL));
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

    @Test
    void fourCorners() {
        beforeAll();
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getStarterDeck();
        Deck missionDeck = game.getCommonTable().getMissionDeck();
        Player p1 = players.getFirst();
        game.setCurrentPlayer(p1);
        game.getCurrentPlayer().createPersonalBoard();
        game.getCurrentPlayer().getPersonalBoard().setSecretMission(Optional.of(missionDeck.getCards().get(0)), game.getCurrentPlayer().getID());

        PersonalBoard pb = game.getCurrentPlayer().getPersonalBoard();
        pb.playSide(initialDeck.getCards().get(0).getBack(), game.getCurrentPlayer().getID());

        pb.setPosition(1, 1, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(8).getFront(), game.getCurrentPlayer().getID());
        pb.setPosition(-1, 1, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(0).getBack(), game.getCurrentPlayer().getID());
        pb.setPosition(2, 2, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(33).getFront(), game.getCurrentPlayer().getID());
        pb.setPosition(-2, 2, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(2).getBack(), game.getCurrentPlayer().getID());
        pb.setPosition(-1, 3, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(9).getFront(), game.getCurrentPlayer().getID());
        pb.setPosition(1, 3, game.getCurrentPlayer().getID());
        pb.playSide(goldDeck.getCards().get(6).getFront(), game.getCurrentPlayer().getID()); //gold card that requires 3 plants, +3 points
        pb.setPosition(0, 2, game.getCurrentPlayer().getID());
        pb.playSide(goldDeck.getCards().get(3).getFront(), game.getCurrentPlayer().getID()); ////gold card that requires 3 plants and 1 insect (corner property)


        assertEquals(13, pb.getScore());
        assertEquals(3, pb.getResourceQuantity(Symbol.INSECT));
        assertEquals(1, pb.getResourceQuantity(Symbol.ANIMAL));
        assertEquals(0, pb.getResourceQuantity(Symbol.FUNGI));
        assertEquals(4, pb.getResourceQuantity(Symbol.PLANT));
        assertEquals(0, pb.getResourceQuantity(Symbol.MANUSCRIPT));
        assertEquals(0, pb.getResourceQuantity(Symbol.QUILL));
        assertEquals(0, pb.getResourceQuantity(Symbol.INKWELL));

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
    }

    @Test
    void cardOnPlayableCornerAndEvilCorner1() {
        beforeAll();
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getStarterDeck();
        Deck missionDeck = game.getCommonTable().getMissionDeck();
        Player p1 = players.getFirst();
        game.setCurrentPlayer(p1);
        game.getCurrentPlayer().createPersonalBoard();
        game.getCurrentPlayer().getPersonalBoard().setSecretMission(Optional.of(missionDeck.getCards().get(0)), game.getCurrentPlayer().getID());

        PersonalBoard pb = game.getCurrentPlayer().getPersonalBoard();
        pb.playSide(initialDeck.getCards().get(0).getBack(), game.getCurrentPlayer().getID());

        pb.setPosition(1, 1, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(8).getFront(), game.getCurrentPlayer().getID());
        pb.setPosition(-1, 1, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(0).getBack(), game.getCurrentPlayer().getID());
        pb.setPosition(2, 2, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(33).getFront(), game.getCurrentPlayer().getID());
        pb.setPosition(-2, 2, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(2).getBack(), game.getCurrentPlayer().getID());
        pb.setPosition(-1, 3, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(9).getFront(), game.getCurrentPlayer().getID());
        pb.setPosition(1, 3, game.getCurrentPlayer().getID());
        pb.playSide(goldDeck.getCards().get(8).getFront(), game.getCurrentPlayer().getID()); //gold card with down left evil
        pb.setPosition(0, 2, game.getCurrentPlayer().getID());
        pb.playSide(goldDeck.getCards().get(3).getFront(), game.getCurrentPlayer().getID()); ////gold card that requires 3 plants and 1 insect (corner property)


        assertEquals(5, pb.getScore());
        assertEquals(3, pb.getResourceQuantity(Symbol.INSECT));
        assertEquals(1, pb.getResourceQuantity(Symbol.ANIMAL));
        assertEquals(0, pb.getResourceQuantity(Symbol.FUNGI));
        assertEquals(4, pb.getResourceQuantity(Symbol.PLANT));
        assertEquals(0, pb.getResourceQuantity(Symbol.MANUSCRIPT));
        assertEquals(0, pb.getResourceQuantity(Symbol.QUILL));
        assertEquals(1, pb.getResourceQuantity(Symbol.INKWELL));

        //initial card
        assertTrue(pb.getOccupiedPositions().get(0).getSide().getUPLEFT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(0).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(0).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(0).getSide().getDOWNRIGHT().isHidden());

        //Card in 1,1
        assertFalse(pb.getOccupiedPositions().get(1).getSide().getUPLEFT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(1).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(1).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(1).getSide().getDOWNRIGHT().isHidden());

        //Card in -1,1
        assertFalse(pb.getOccupiedPositions().get(2).getSide().getUPRIGHT().isHidden());
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
        assertFalse(pb.getOccupiedPositions().get(5).getSide().getDOWNRIGHT().isHidden());

        //Card in 1,3
        assertFalse(pb.getOccupiedPositions().get(6).getSide().getUPLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(6).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(6).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(6).getSide().getDOWNRIGHT().isHidden());
    }


    @Test
    void cardOnPlayableCornerAndEvilCorner2() {
        beforeAll();
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getStarterDeck();
        Deck missionDeck = game.getCommonTable().getMissionDeck();
        Player p1 = players.getFirst();
        game.setCurrentPlayer(p1);
        game.getCurrentPlayer().createPersonalBoard();
        game.getCurrentPlayer().getPersonalBoard().setSecretMission(Optional.of(missionDeck.getCards().get(0)), game.getCurrentPlayer().getID());

        PersonalBoard pb = game.getCurrentPlayer().getPersonalBoard();
        pb.playSide(initialDeck.getCards().get(0).getFront(), game.getCurrentPlayer().getID());

        pb.setPosition(-1, 1, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(0).getBack(), game.getCurrentPlayer().getID());
        pb.setPosition(1, 1, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(39).getFront(), game.getCurrentPlayer().getID()); // upleft corner evil
        pb.setPosition(0, 2, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(1).getBack(), game.getCurrentPlayer().getID());

        assertEquals(1, pb.getScore());
        assertEquals(3, pb.getResourceQuantity(Symbol.INSECT));
        assertEquals(0, pb.getResourceQuantity(Symbol.ANIMAL));
        assertEquals(0, pb.getResourceQuantity(Symbol.FUNGI));
        assertEquals(1, pb.getResourceQuantity(Symbol.PLANT));
        assertEquals(0, pb.getResourceQuantity(Symbol.MANUSCRIPT));
        assertEquals(0, pb.getResourceQuantity(Symbol.QUILL));
        assertEquals(0, pb.getResourceQuantity(Symbol.INKWELL));

        //initial card
        assertTrue(pb.getOccupiedPositions().get(0).getSide().getUPLEFT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(0).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(0).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(0).getSide().getDOWNRIGHT().isHidden());

        //Card in 1,1
        assertFalse(pb.getOccupiedPositions().get(1).getSide().getUPLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(1).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(1).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(1).getSide().getDOWNRIGHT().isHidden());

        //Card in -1,1
        assertFalse(pb.getOccupiedPositions().get(2).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(2).getSide().getUPLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(2).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(2).getSide().getDOWNRIGHT().isHidden());
    }

    @Test
    void notEnoughResources() {
        beforeAll();
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getStarterDeck();
        Deck missionDeck = game.getCommonTable().getMissionDeck();
        Player p1 = players.getFirst();
        game.setCurrentPlayer(p1);
        game.getCurrentPlayer().createPersonalBoard();
        game.getCurrentPlayer().getPersonalBoard().setSecretMission(Optional.of(missionDeck.getCards().get(0)), game.getCurrentPlayer().getID());

        PersonalBoard pb = game.getCurrentPlayer().getPersonalBoard();
        pb.playSide(initialDeck.getCards().get(0).getBack(), game.getCurrentPlayer().getID());

        pb.setPosition(1, 1, game.getCurrentPlayer().getID());
        pb.playSide(goldDeck.getCards().get(6).getFront(), game.getCurrentPlayer().getID());

        assertEquals(0, pb.getScore());
    }


    @Test
    void notEnoughResourcesButPlayedOnBack() {
        beforeAll();
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getStarterDeck();
        Deck missionDeck = game.getCommonTable().getMissionDeck();
        Player p1 = players.getFirst();
        game.setCurrentPlayer(p1);
        game.getCurrentPlayer().createPersonalBoard();
        game.getCurrentPlayer().getPersonalBoard().setSecretMission(Optional.of(missionDeck.getCards().get(0)), game.getCurrentPlayer().getID());

        PersonalBoard pb = game.getCurrentPlayer().getPersonalBoard();
        pb.playSide(initialDeck.getCards().get(0).getBack(), game.getCurrentPlayer().getID());

        pb.setPosition(1, 1, game.getCurrentPlayer().getID());
        pb.playSide(goldDeck.getCards().get(6).getBack(), game.getCurrentPlayer().getID());

        assertEquals(0, pb.getScore());
        assertEquals(1, pb.getResourceQuantity(Symbol.INSECT));
        assertEquals(1, pb.getResourceQuantity(Symbol.ANIMAL));
        assertEquals(1, pb.getResourceQuantity(Symbol.FUNGI));
        assertEquals(1, pb.getResourceQuantity(Symbol.PLANT));
        assertEquals(0, pb.getResourceQuantity(Symbol.MANUSCRIPT));
        assertEquals(0, pb.getResourceQuantity(Symbol.QUILL));
        assertEquals(0, pb.getResourceQuantity(Symbol.INKWELL));
    }

    @Test
    void onePointForEveryManuscriptAndForEveryInqwell() {
        beforeAll();
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getStarterDeck();
        Deck missionDeck = game.getCommonTable().getMissionDeck();
        Player p1 = players.getFirst();
        game.setCurrentPlayer(p1);
        game.getCurrentPlayer().createPersonalBoard();
        game.getCurrentPlayer().getPersonalBoard().setSecretMission(Optional.of(missionDeck.getCards().get(2)), game.getCurrentPlayer().getID());

        ArrayList<Card> commonMissions = new ArrayList<>();
        commonMissions.add(missionDeck.getCards().get(1));
        commonMissions.add(missionDeck.getCards().get(0));

        PersonalBoard pb = game.getCurrentPlayer().getPersonalBoard();
        pb.playSide(initialDeck.getCards().get(4).getFront(), game.getCurrentPlayer().getID());

        pb.setPosition(1, 1, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(10).getBack(), game.getCurrentPlayer().getID());
        pb.setPosition(2, 0, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(11).getBack(), game.getCurrentPlayer().getID());
        pb.setPosition(0, 2, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(12).getBack(), game.getCurrentPlayer().getID());
        pb.setPosition(-1, 1, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(13).getBack(), game.getCurrentPlayer().getID());
        pb.setPosition(1, 3, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(14).getBack(), game.getCurrentPlayer().getID());
        pb.setPosition(2, 4, game.getCurrentPlayer().getID());
        pb.playSide(goldDeck.getCards().get(16).getFront(), game.getCurrentPlayer().getID()); //3 point and 1 manuscript
        pb.setPosition(-2, 0, game.getCurrentPlayer().getID());
        pb.playSide(goldDeck.getCards().get(17).getFront(), game.getCurrentPlayer().getID()); //3 point and 1 inqwell
        pb.setPosition(3, 1, game.getCurrentPlayer().getID());
        pb.playSide(goldDeck.getCards().get(10).getFront(), game.getCurrentPlayer().getID()); //1 point for every INQWELL
        pb.setPosition(4, 2, game.getCurrentPlayer().getID());
        pb.playSide(goldDeck.getCards().get(11).getFront(), game.getCurrentPlayer().getID()); //1 point for every MANUSCRIPT
        pb.endGame(commonMissions);
        assertEquals(14, pb.getScore());

        assertTrue(pb.getOccupiedPositions().get(0).getSide().getUPLEFT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(0).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(0).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(0).getSide().getDOWNRIGHT().isHidden());

        assertTrue(pb.getOccupiedPositions().get(1).getSide().getUPLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(1).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(1).getSide().getDOWNLEFT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(1).getSide().getDOWNRIGHT().isHidden());

        assertFalse(pb.getOccupiedPositions().get(2).getSide().getUPLEFT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(2).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(2).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(2).getSide().getDOWNRIGHT().isHidden());

        assertFalse(pb.getOccupiedPositions().get(3).getSide().getUPLEFT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(3).getSide().getUPRIGHT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(3).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(3).getSide().getDOWNRIGHT().isHidden());

        assertFalse(pb.getOccupiedPositions().get(4).getSide().getUPLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(4).getSide().getUPRIGHT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(4).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(4).getSide().getDOWNRIGHT().isHidden());

        assertFalse(pb.getOccupiedPositions().get(5).getSide().getUPLEFT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(5).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(5).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(5).getSide().getDOWNRIGHT().isHidden());

        assertFalse(pb.getOccupiedPositions().get(6).getSide().getUPLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(6).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(6).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(6).getSide().getDOWNRIGHT().isHidden());

        assertFalse(pb.getOccupiedPositions().get(7).getSide().getUPLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(7).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(7).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(7).getSide().getDOWNRIGHT().isHidden());

        assertFalse(pb.getOccupiedPositions().get(8).getSide().getUPLEFT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(8).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(8).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(8).getSide().getDOWNRIGHT().isHidden());

        assertFalse(pb.getOccupiedPositions().get(9).getSide().getUPLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(9).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(9).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(9).getSide().getDOWNRIGHT().isHidden());
    }

    @Test
    void patternLwithDiagonalPattern() {
        beforeAll();
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getStarterDeck();
        Deck missionDeck = game.getCommonTable().getMissionDeck();
        Player p1 = players.getFirst();
        game.setCurrentPlayer(p1);
        game.getCurrentPlayer().createPersonalBoard();
        game.getCurrentPlayer().getPersonalBoard().setSecretMission(Optional.of(missionDeck.getCards().get(1)), game.getCurrentPlayer().getID());

        ArrayList<Card> commonMissions = new ArrayList<>();
        commonMissions.add(missionDeck.getCards().get(4));
        commonMissions.add(missionDeck.getCards().get(0));

        PersonalBoard pb = game.getCurrentPlayer().getPersonalBoard();
        pb.playSide(initialDeck.getCards().get(0).getBack(), game.getCurrentPlayer().getID());

        pb.setPosition(1, -1, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(20).getBack(), game.getCurrentPlayer().getID());
        pb.setPosition(2, -2, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(21).getBack(), game.getCurrentPlayer().getID());
        pb.setPosition(1, -3, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(22).getBack(), game.getCurrentPlayer().getID());
        pb.setPosition(2, -4, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(0).getBack(), game.getCurrentPlayer().getID());
        pb.setPosition(3, -5, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(1).getBack(), game.getCurrentPlayer().getID());
        pb.setPosition(4, -6, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(2).getBack(), game.getCurrentPlayer().getID());
        pb.setPosition(5, -7, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(3).getBack(), game.getCurrentPlayer().getID());
        pb.endGame(commonMissions);

        assertEquals(5, pb.getScore());
        assertEquals(1, pb.getResourceQuantity(Symbol.INSECT));
        assertEquals(0, pb.getResourceQuantity(Symbol.ANIMAL));
        assertEquals(4, pb.getResourceQuantity(Symbol.FUNGI));
        assertEquals(5, pb.getResourceQuantity(Symbol.PLANT));
        assertEquals(0, pb.getResourceQuantity(Symbol.MANUSCRIPT));
        assertEquals(0, pb.getResourceQuantity(Symbol.QUILL));
        assertEquals(0, pb.getResourceQuantity(Symbol.INKWELL));

        assertFalse(pb.getOccupiedPositions().get(0).getSide().getUPLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(0).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(0).getSide().getDOWNLEFT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(0).getSide().getDOWNRIGHT().isHidden());

        assertFalse(pb.getOccupiedPositions().get(1).getSide().getUPLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(1).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(1).getSide().getDOWNLEFT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(1).getSide().getDOWNRIGHT().isHidden());

        assertFalse(pb.getOccupiedPositions().get(2).getSide().getUPLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(2).getSide().getUPRIGHT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(2).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(2).getSide().getDOWNRIGHT().isHidden());

        assertFalse(pb.getOccupiedPositions().get(3).getSide().getUPLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(3).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(3).getSide().getDOWNLEFT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(3).getSide().getDOWNRIGHT().isHidden());

        assertFalse(pb.getOccupiedPositions().get(4).getSide().getUPLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(4).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(4).getSide().getDOWNLEFT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(4).getSide().getDOWNRIGHT().isHidden());

        assertFalse(pb.getOccupiedPositions().get(5).getSide().getUPLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(5).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(5).getSide().getDOWNLEFT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(5).getSide().getDOWNRIGHT().isHidden());

        assertFalse(pb.getOccupiedPositions().get(6).getSide().getUPLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(6).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(6).getSide().getDOWNLEFT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(6).getSide().getDOWNRIGHT().isHidden());

        assertFalse(pb.getOccupiedPositions().get(7).getSide().getUPLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(7).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(7).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(7).getSide().getDOWNRIGHT().isHidden());
    }

    @Test
    void notPlayablePosition() {
        beforeAll();
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getStarterDeck();
        Deck missionDeck = game.getCommonTable().getMissionDeck();
        Player p1 = players.getFirst();
        game.setCurrentPlayer(p1);
        game.getCurrentPlayer().createPersonalBoard();
        game.getCurrentPlayer().getPersonalBoard().setSecretMission(Optional.of(missionDeck.getCards().get(1)), game.getCurrentPlayer().getID());

        ArrayList<Card> commonMissions = new ArrayList<>();
        commonMissions.add(missionDeck.getCards().get(4));
        commonMissions.add(missionDeck.getCards().get(0));

        PersonalBoard pb = game.getCurrentPlayer().getPersonalBoard();
        pb.playSide(initialDeck.getCards().get(0).getBack(), game.getCurrentPlayer().getID());

        pb.setPosition(1, 0, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(0).getFront(), game.getCurrentPlayer().getID());

        assertEquals(1, pb.getOccupiedPositions().size());
    }

    @Test
    void cardThatGives5PointPlants() {
        beforeAll();
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getStarterDeck();
        Deck missionDeck = game.getCommonTable().getMissionDeck();
        Player p1 = players.getFirst();
        game.setCurrentPlayer(p1);
        game.getCurrentPlayer().createPersonalBoard();
        game.getCurrentPlayer().getPersonalBoard().setSecretMission(Optional.of(missionDeck.getCards().get(2)), game.getCurrentPlayer().getID());

        ArrayList<Card> commonMissions = new ArrayList<>();
        commonMissions.add(missionDeck.getCards().get(4));
        commonMissions.add(missionDeck.getCards().get(0));

        PersonalBoard pb = game.getCurrentPlayer().getPersonalBoard();
        pb.playSide(initialDeck.getCards().get(2).getFront(), game.getCurrentPlayer().getID());

        pb.setPosition(1, 1, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(1).getBack(), game.getCurrentPlayer().getID());
        pb.setPosition(1, -1, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(0).getFront(), game.getCurrentPlayer().getID());
        pb.setPosition(-1, 1, game.getCurrentPlayer().getID());
        pb.playSide(resourceDeck.getCards().get(8).getFront(), game.getCurrentPlayer().getID());
        pb.setPosition(0, -2, game.getCurrentPlayer().getID());
        pb.playSide(goldDeck.getCards().get(9).getFront(), game.getCurrentPlayer().getID());


        assertEquals(6, pb.getScore());
        assertEquals(0, pb.getResourceQuantity(Symbol.INSECT));
        assertEquals(0, pb.getResourceQuantity(Symbol.ANIMAL));
        assertEquals(1, pb.getResourceQuantity(Symbol.FUNGI));
        assertEquals(4, pb.getResourceQuantity(Symbol.PLANT));
        assertEquals(0, pb.getResourceQuantity(Symbol.MANUSCRIPT));
        assertEquals(0, pb.getResourceQuantity(Symbol.QUILL));
        assertEquals(0, pb.getResourceQuantity(Symbol.INKWELL));


        assertTrue(pb.getOccupiedPositions().get(0).getSide().getUPLEFT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(0).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(0).getSide().getDOWNLEFT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(0).getSide().getDOWNRIGHT().isHidden());

        assertFalse(pb.getOccupiedPositions().get(1).getSide().getUPLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(1).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(1).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(1).getSide().getDOWNRIGHT().isHidden());

        assertFalse(pb.getOccupiedPositions().get(2).getSide().getUPLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(2).getSide().getUPRIGHT().isHidden());
        assertTrue(pb.getOccupiedPositions().get(2).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(2).getSide().getDOWNRIGHT().isHidden());

        assertFalse(pb.getOccupiedPositions().get(3).getSide().getUPLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(3).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(3).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(3).getSide().getDOWNRIGHT().isHidden());

        assertFalse(pb.getOccupiedPositions().get(4).getSide().getUPLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(4).getSide().getUPRIGHT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(4).getSide().getDOWNLEFT().isHidden());
        assertFalse(pb.getOccupiedPositions().get(4).getSide().getDOWNRIGHT().isHidden());
    }
}