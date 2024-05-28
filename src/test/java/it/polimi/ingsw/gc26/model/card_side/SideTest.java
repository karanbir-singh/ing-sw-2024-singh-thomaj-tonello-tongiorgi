package it.polimi.ingsw.gc26.model.card_side;

import it.polimi.ingsw.gc26.model.card_side.mission.MissionItemPattern;
import it.polimi.ingsw.gc26.model.card_side.mission.MissionTripletPattern;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SideTest {
    @Test
    void printStarterCardSide1() {
        ArrayList<Symbol> permanentResources = new ArrayList<>();
        permanentResources.add(Symbol.PLANT);

        Side side = new StarterCardFront(
                permanentResources,
                new Corner(true, Symbol.PLANT),
                new Corner(false, Symbol.ANIMAL),
                new Corner(false, Symbol.INSECT),
                new Corner(false, Symbol.FUNGI)
        );

        String[][] printedSide = side.printableSide();

        assertNotNull(printedSide);
    }

    @Test
    void printStarterCardSide2() {
        ArrayList<Symbol> permanentResources = new ArrayList<>();
        permanentResources.add(Symbol.PLANT);
        permanentResources.add(Symbol.PLANT);

        Side side = new StarterCardFront(
                permanentResources,
                new Corner(true, Symbol.PLANT),
                new Corner(false, Symbol.ANIMAL),
                new Corner(false, Symbol.INSECT),
                new Corner(false, Symbol.FUNGI)
        );

        String[][] printedSide = side.printableSide();

        assertNotNull(printedSide);
    }

    @Test
    void printStarterCardSide3() {
        ArrayList<Symbol> permanentResources = new ArrayList<>();
        permanentResources.add(Symbol.PLANT);
        permanentResources.add(Symbol.PLANT);
        permanentResources.add(Symbol.PLANT);

        Side side = new StarterCardFront(
                permanentResources,
                new Corner(true, Symbol.PLANT),
                new Corner(false, Symbol.ANIMAL),
                new Corner(false, Symbol.INSECT),
                new Corner(false, Symbol.FUNGI)
        );

        String[][] printedSide = side.printableSide();

        assertNotNull(printedSide);
    }

    @Test
    void printResourceCardSide() {
        Side side = new ResourceCardFront(
                Symbol.PLANT,
                0,
                new Corner(false, Symbol.PLANT, false),
                new Corner(true, Symbol.ANIMAL, false),
                new Corner(false, Symbol.INSECT, false),
                new Corner(false, Symbol.FUNGI, true)
        );

        String[][] printedSide = side.printableSide();

        assertNotNull(printedSide);
    }

    @Test
    void printGoldCardSide() {
        Map<Symbol, Integer> requestedResources = new HashMap<>();
        requestedResources.put(Symbol.PLANT, 3);

        Side side = new GoldCardFront(
                Symbol.PLANT,
                requestedResources,
                2,
                new Corner(false, Symbol.PLANT),
                new Corner(false, Symbol.ANIMAL),
                new Corner(false, Symbol.INSECT),
                new Corner(true, Symbol.FUNGI)
        );

        String[][] printedSide = side.printableSide();

        assertNotNull(printedSide);
    }

    @Test
    void printMissionItemPattern1() {
        Side side = new MissionItemPattern(
                1
        );

        String[][] printedSide = side.printableSide();

        assertNotNull(printedSide);

        Map<Symbol, Integer> resources = new HashMap<>();
        resources.put(Symbol.FUNGI, 3);

        assertEquals(2, side.checkPattern(resources, new ArrayList<>()));
    }

    @Test
    void printMissionItemPattern2() {
        Side side = new MissionItemPattern(
                2
        );

        String[][] printedSide = side.printableSide();

        assertNotNull(printedSide);

        Map<Symbol, Integer> resources = new HashMap<>();
        resources.put(Symbol.PLANT, 3);

        assertEquals(2, side.checkPattern(resources, new ArrayList<>()));
    }

    @Test
    void printMissionItemPattern3() {
        Side side = new MissionItemPattern(
                3
        );

        String[][] printedSide = side.printableSide();

        assertNotNull(printedSide);

        Map<Symbol, Integer> resources = new HashMap<>();
        resources.put(Symbol.ANIMAL, 3);

        assertEquals(2, side.checkPattern(resources, new ArrayList<>()));
    }

    @Test
    void printMissionItemPattern4() {
        Side side = new MissionItemPattern(
                4
        );

        String[][] printedSide = side.printableSide();

        assertNotNull(printedSide);

        Map<Symbol, Integer> resources = new HashMap<>();
        resources.put(Symbol.INSECT, 3);

        assertEquals(2, side.checkPattern(resources, new ArrayList<>()));
    }

    @Test
    void printMissionTripletPattern1() {
        Side side = new MissionTripletPattern(
                1
        );

        String[][] printedSide = side.printableSide();

        assertNotNull(printedSide);
        Map<Symbol, Integer> resources = new HashMap<>();
        resources.put(Symbol.MANUSCRIPT, 2);
        resources.put(Symbol.INKWELL, 2);
        resources.put(Symbol.QUILL, 2);

        assertEquals(6, side.checkPattern(resources, new ArrayList<>()));

    }

    @Test
    void printMissionTripletPattern2() {
        Side side = new MissionTripletPattern(
                2
        );

        String[][] printedSide = side.printableSide();

        assertNotNull(printedSide);

        Map<Symbol, Integer> resources = new HashMap<>();
        resources.put(Symbol.MANUSCRIPT, 2);

        assertEquals(2, side.checkPattern(resources, new ArrayList<>()));
    }

    @Test
    void printMissionTripletPattern3() {
        Side side = new MissionTripletPattern(
                3
        );

        String[][] printedSide = side.printableSide();

        assertNotNull(printedSide);

        Map<Symbol, Integer> resources = new HashMap<>();
        resources.put(Symbol.QUILL, 2);

        assertEquals(2, side.checkPattern(resources, new ArrayList<>()));
    }

    @Test
    void printMissionTripletPattern4() {
        Side side = new MissionTripletPattern(
                4
        );

        String[][] printedSide = side.printableSide();

        assertNotNull(printedSide);

        Map<Symbol, Integer> resources = new HashMap<>();
        resources.put(Symbol.INKWELL, 2);

        assertEquals(2, side.checkPattern(resources, new ArrayList<>()));
    }

    @Test
    void printCardBack() {
        // CardBack(Symbol side, Corner UPLEFT, Corner DOWNLEFT, Corner UPRIGHT, Corner DOWNRIGHT, ArrayList<Symbol> permanentResources, Map<Symbol, Integer> requestedResources){
        Side side = new CardBack(
                Symbol.PLANT,
                new Corner(false, Symbol.PLANT),
                new Corner(false, Symbol.ANIMAL),
                new Corner(false, Symbol.INSECT),
                new Corner(false, Symbol.FUNGI),
                new ArrayList<>(),
                new HashMap<>()
        );

        String[][] printedSide = side.printableSide();

        assertNotNull(printedSide);
    }
}