package it.polimi.ingsw.gc26.model.card_side;

import it.polimi.ingsw.gc26.model.card_side.mission.MissionDiagonalPattern;
import it.polimi.ingsw.gc26.model.card_side.mission.MissionItemPattern;
import it.polimi.ingsw.gc26.model.card_side.mission.MissionLPattern;
import it.polimi.ingsw.gc26.model.card_side.mission.MissionTripletPattern;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SideTest {

    private static String imagePath = "src/main/resources/images/";

    @Test
    void printStarterCardSide1() {
        ArrayList<Symbol> permanentResources = new ArrayList<>();
        permanentResources.add(Symbol.PLANT);

        Side side = new StarterCardFront(
                permanentResources,
                new Corner(true, Symbol.PLANT),
                new Corner(false, Symbol.ANIMAL),
                new Corner(false, Symbol.INSECT),
                new Corner(false, Symbol.FUNGI),
                imagePath + "frontSide/img_81.jpeg"
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
                new Corner(false, Symbol.FUNGI),
                imagePath + "frontSide/img_81.jpeg"
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
                new Corner(false, Symbol.FUNGI),
                imagePath + "frontSide/img_81.jpeg"
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
                new Corner(false, Symbol.FUNGI, true),
                imagePath + "frontSide/img_1.jpeg"
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
                new Corner(true, Symbol.FUNGI),
                imagePath + "frontSide/img_41.jpeg"
        );

        String[][] printedSide = side.printableSide();

        assertNotNull(printedSide);
    }

    @Test
    void printMissionItemPattern1() {
        Side side = new MissionItemPattern(
                1,
                imagePath + "frontSide/img_98.jpeg"
        );

        String[][] printedSide = side.printableSide();

        assertNotNull(printedSide);

        Map<Symbol, Integer> resources = new HashMap<>();
        resources.put(Symbol.FUNGI, 3);
        side.printableSide();

        assertEquals(2, side.checkPattern(resources, new ArrayList<>()));
    }

    @Test
    void printMissionItemPattern2() {
        Side side = new MissionItemPattern(
                2,
                imagePath + "frontSide/img_98.jpeg"
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
                3,
                imagePath + "frontSide/img_98.jpeg"
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
                4,
                imagePath + "frontSide/img_98.jpeg"
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
                1,
                imagePath + "frontSide/img_98.jpeg"
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
                2,
                imagePath + "frontSide/img_98.jpeg"
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
                3,
                imagePath + "frontSide/img_98.jpeg"
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
                4,
                imagePath + "frontSide/img_98.jpeg"
        );

        String[][] printedSide = side.printableSide();

        assertNotNull(printedSide);

        Map<Symbol, Integer> resources = new HashMap<>();
        resources.put(Symbol.INKWELL, 2);

        assertEquals(2, side.checkPattern(resources, new ArrayList<>()));
    }

    @Test
    void printMissionDiagonalPattern1() {
        Side side = new MissionDiagonalPattern(
                1,
                imagePath + "frontSide/img_98.jpeg"
        );

        String[][] printedSide = side.printableSide();

        assertNotNull(printedSide);

        Map<Symbol, Integer> resources = new HashMap<>();
        resources.put(Symbol.INKWELL, 2);

        assertEquals(0, side.checkPattern(resources, new ArrayList<>()));
    }

    @Test
    void printMissionDiagonalPattern2() {
        Side side = new MissionDiagonalPattern(
                2,
                imagePath + "frontSide/img_98.jpeg"
        );

        String[][] printedSide = side.printableSide();

        assertNotNull(printedSide);

        Map<Symbol, Integer> resources = new HashMap<>();
        resources.put(Symbol.INKWELL, 2);

        assertEquals(0, side.checkPattern(resources, new ArrayList<>()));
    }

    @Test
    void printMissionDiagonalPattern3() {
        Side side = new MissionDiagonalPattern(
                3,
                imagePath + "frontSide/img_98.jpeg"
        );

        String[][] printedSide = side.printableSide();

        assertNotNull(printedSide);

        Map<Symbol, Integer> resources = new HashMap<>();
        resources.put(Symbol.INKWELL, 2);

        assertEquals(0, side.checkPattern(resources, new ArrayList<>()));
    }

    @Test
    void printMissionDiagonalPattern4() {
        Side side = new MissionDiagonalPattern(
                4,
                imagePath + "frontSide/img_98.jpeg"
        );

        String[][] printedSide = side.printableSide();

        assertNotNull(printedSide);

        Map<Symbol, Integer> resources = new HashMap<>();
        resources.put(Symbol.INKWELL, 2);

        assertEquals(0, side.checkPattern(resources, new ArrayList<>()));
    }

    @Test
    void printMissionLPattern1() {
        Side side = new MissionLPattern(
                1,
                imagePath + "frontSide/img_98.jpeg"
        );

        String[][] printedSide = side.printableSide();

        assertNotNull(printedSide);

        Map<Symbol, Integer> resources = new HashMap<>();
        resources.put(Symbol.INKWELL, 2);

        assertEquals(0, side.checkPattern(resources, new ArrayList<>()));
    }

    @Test
    void printMissionLPattern2() {
        Side side = new MissionLPattern(
                2,
                imagePath + "frontSide/img_98.jpeg"
        );

        String[][] printedSide = side.printableSide();

        assertNotNull(printedSide);

        Map<Symbol, Integer> resources = new HashMap<>();
        resources.put(Symbol.INKWELL, 2);

        assertEquals(0, side.checkPattern(resources, new ArrayList<>()));
    }

    @Test
    void printMissionLPattern3() {
        Side side = new MissionLPattern(
                3,
                imagePath + "frontSide/img_98.jpeg"
        );

        String[][] printedSide = side.printableSide();

        assertNotNull(printedSide);

        Map<Symbol, Integer> resources = new HashMap<>();
        resources.put(Symbol.INKWELL, 2);

        assertEquals(0, side.checkPattern(resources, new ArrayList<>()));
    }

    @Test
    void printMissionLPattern4() {
        Side side = new MissionLPattern(
                4,
                imagePath + "frontSide/img_98.jpeg"
        );

        String[][] printedSide = side.printableSide();

        assertNotNull(printedSide);

        Map<Symbol, Integer> resources = new HashMap<>();
        resources.put(Symbol.INKWELL, 2);

        assertEquals(0, side.checkPattern(resources, new ArrayList<>()));
    }

    @Test
    void printCardBack() {
        Side side = new CardBack(
                Symbol.PLANT,
                new Corner(false, Symbol.PLANT),
                new Corner(false, Symbol.ANIMAL),
                new Corner(false, Symbol.INSECT),
                new Corner(false, Symbol.FUNGI),
                new ArrayList<>(),
                new HashMap<>(),
                imagePath + "frontSide/img_98.jpeg"
        );

        String[][] printedSide = side.printableSide();

        assertNotNull(printedSide);
    }

    @Test
    void getImagePath() {
        Side side = new CardBack(
                Symbol.PLANT,
                new Corner(false, Symbol.PLANT),
                new Corner(false, Symbol.ANIMAL),
                new Corner(false, Symbol.INSECT),
                new Corner(false, Symbol.FUNGI),
                new ArrayList<>(),
                new HashMap<>(),
                imagePath + "frontSide/img_98.jpeg"
        );

        assertEquals(imagePath + "frontSide/img_98.jpeg", side.getImagePath());
    }
}