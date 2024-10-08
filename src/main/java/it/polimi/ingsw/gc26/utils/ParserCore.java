package it.polimi.ingsw.gc26.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc26.model.card.GoldCard;
import it.polimi.ingsw.gc26.model.card.MissionCard;
import it.polimi.ingsw.gc26.model.card.ResourceCard;
import it.polimi.ingsw.gc26.model.card.StarterCard;
import it.polimi.ingsw.gc26.model.card_side.*;
import it.polimi.ingsw.gc26.model.card_side.ability.CornerCounter;
import it.polimi.ingsw.gc26.model.card_side.ability.InkwellCounter;
import it.polimi.ingsw.gc26.model.card_side.ability.ManuscriptCounter;
import it.polimi.ingsw.gc26.model.card_side.ability.QuillCounter;
import it.polimi.ingsw.gc26.model.card_side.mission.MissionDiagonalPattern;
import it.polimi.ingsw.gc26.model.card_side.mission.MissionItemPattern;
import it.polimi.ingsw.gc26.model.card_side.mission.MissionLPattern;
import it.polimi.ingsw.gc26.model.card_side.mission.MissionTripletPattern;
import it.polimi.ingsw.gc26.model.game.Deck;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Parser to create card from json file
 */
public class ParserCore {
    /**
     * File path where the json file is located
     */
    private final String filename;

    /**
     * Initialize the parser. The file to be parsed must be located in the "resources" directory.
     *
     * @param filename file path to be read
     */
    public ParserCore(String filename) {
        this.filename = filename;
    }

    /**
     * Returns the root object contained in the Json file, that is to say, all the file's content.
     * Is useful to navigate the file and get its children.
     *
     * @return JsonNode file's root object
     */
    private JsonNode getRootObject() {

        try {
            ObjectMapper JsonMapper = new ObjectMapper();
            return JsonMapper.readTree(getClass().getResourceAsStream(filename));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns a deck containing only the Starter Cards. All the cards in the returned deck are completed initialized.
     * Note the deck is not shuffled (useful for testing purposes).
     *
     * @return starterCardDeck a deck containing the starter cards from the Json file.
     */
    public Deck getStarterCards() {
        JsonNode starterCardsJson = Objects.requireNonNull(getRootObject()).get("StarterCards");
        Deck starterCardDeck = new Deck();
        for ( JsonNode cardJson : starterCardsJson) {
            JsonNode frontCardJson = cardJson.get("Front");
            JsonNode backCardJson = cardJson.get("Back");

            ArrayList<Symbol> permanentResources = new ArrayList<>();

            for (JsonNode resource : frontCardJson.get("Resource")) {
                permanentResources.add(Symbol.valueOf(resource.asText().toUpperCase()));
            }

            // Corners
            Corner cornerUpLeft = createCorner(frontCardJson, "UpSx");
            Corner cornerUpRight = createCorner(frontCardJson, "UpDx");
            Corner cornerDownLeft = createCorner(frontCardJson, "DownSx");
            Corner cornerDownRight = createCorner(frontCardJson, "DownDx");

            String imagePathFront = frontCardJson.get("ImgFile").asText();
            Side front = new StarterCardFront( permanentResources, cornerUpLeft, cornerDownLeft, cornerUpRight, cornerDownRight, imagePathFront);
            cornerUpLeft = createCorner(backCardJson, "UpSx");
            cornerUpRight = createCorner(backCardJson, "UpDx");
            cornerDownLeft = createCorner(backCardJson, "DownSx");
            cornerDownRight = createCorner(backCardJson, "DownDx");
            String imagePath = backCardJson.get("ImgFile").asText();
            Side back = new CardBack(cornerUpLeft, cornerDownLeft, cornerUpRight, cornerDownRight, imagePath);

            starterCardDeck.addCard(new StarterCard(front, back));

        }
        return starterCardDeck;
    }

    /**
     * Returns a deck containing only the Mission Cards. All the cards in the returned deck are completed initialized.
     * Note the deck is not shuffled (useful for testing purposes).
     *
     * @return MissionCardDeck a deck containing the mission cards from the Json file.
     */
    public Deck getMissionCards() {
        JsonNode ObjectiveCardsJson = Objects.requireNonNull(getRootObject()).get("MissionCards");
        Deck MissionCardDeck = new Deck();
        for ( JsonNode cardJson : ObjectiveCardsJson) {
            JsonNode frontCardJson = cardJson.get("Front");

            Side front;
            String subclass = frontCardJson.get("Subclass").asText();
            String imagePathFront = frontCardJson.get("ImgFile").asText();
            switch (subclass) {
                case "Diagonal" : {
                    front = new MissionDiagonalPattern(frontCardJson.get("Type").asInt(), imagePathFront);
                    break;
                }
                case "L" : {
                    front = new MissionLPattern(frontCardJson.get("Type").asInt(), imagePathFront);
                    break;
                }
                case "Triplet" : {
                    front = new MissionTripletPattern(frontCardJson.get("Type").asInt(), imagePathFront);
                    break;
                }
                case "Item" : {
                    front = new MissionItemPattern(frontCardJson.get("Type").asInt(), imagePathFront);
                    break;
                }
                default: front = null;
            }

            String imagePath = cardJson.get("Back").get("ImgFile").asText();
            Side back = new CardBack(imagePath);

            MissionCardDeck.addCard(new MissionCard(front, back));
        }
        return MissionCardDeck;
    }

    /**
     * Returns a deck containing only the Resources Cards. All the cards in the returned deck are completed initialized.
     * Note the deck is not shuffled (useful for testing purposes).
     *
     * @return ResourceCardDeck a deck containing the resources cards from the Json file.
     */
    public Deck getResourceCards() {
        JsonNode ResourceCardsJson = Objects.requireNonNull(getRootObject()).get("ResourceCards");
        Deck ResourceCardDeck = new Deck();
        for ( JsonNode cardJson : ResourceCardsJson) {
            JsonNode frontCardJson = cardJson.get("Front");
            JsonNode backCardJson = cardJson.get("Back");

            // Corners
            Corner cornerUpLeft = createCorner(frontCardJson, "UpSx");
            Corner cornerUpRight = createCorner(frontCardJson, "UpDx");
            Corner cornerDownLeft = createCorner(frontCardJson, "DownSx");
            Corner cornerDownRight = createCorner(frontCardJson, "DownDx");
            String imagePathFront = frontCardJson.get("ImgFile").asText();
            String imagePath = backCardJson.get("ImgFile").asText();
            Side front = new ResourceCardFront(Symbol.valueOf(backCardJson.get("Resource").asText().toUpperCase()), frontCardJson.get("Points").asInt(), cornerUpLeft, cornerDownLeft, cornerUpRight, cornerDownRight, imagePathFront);
            Side back = new CardBack(Symbol.valueOf(backCardJson.get("Resource").asText().toUpperCase()), imagePath);

            ResourceCardDeck.addCard(new ResourceCard(front, back));

        }
        return ResourceCardDeck;

    }

    /**
     * Returns a deck containing only the Gold Cards. All the cards in the returned deck are completed initialized.
     * Note the deck is not shuffled (useful for testing purposes).
     *
     * @return goldCardDeck a deck containing the gold cards from the Json file.
     */
    public Deck getGoldCards() {
        JsonNode goldCardsJson = Objects.requireNonNull(getRootObject()).get("GoldCards");
        Deck goldCardDeck = new Deck();
        for ( JsonNode cardJson : goldCardsJson) {
            JsonNode frontCardJson = cardJson.get("Front");
            JsonNode backCardJson = cardJson.get("Back");

            HashMap<Symbol, Integer> requestedResources = new HashMap<>();

            for (JsonNode resource : frontCardJson.get("Requirements")) {
                if(requestedResources.containsKey(Symbol.valueOf(resource.asText().toUpperCase()))) {
                    requestedResources.put(Symbol.valueOf(resource.asText().toUpperCase()), requestedResources.get(Symbol.valueOf(resource.asText().toUpperCase())) + 1);
                } else {
                    requestedResources.put(Symbol.valueOf(resource.asText().toUpperCase()), 1);
                }
            }
            // Corners
            Corner cornerUpLeft = createCorner(frontCardJson, "UpSx");
            Corner cornerUpRight = createCorner(frontCardJson, "UpDx");
            Corner cornerDownLeft = createCorner(frontCardJson, "DownSx");
            Corner cornerDownRight = createCorner(frontCardJson, "DownDx");
            Side front;
            String imagePathFront = frontCardJson.get("ImgFile").asText();

            if (frontCardJson.get("Constraint").asText().equals("Corner")) {
                front = new CornerCounter(Symbol.valueOf(backCardJson.get("Resource").asText().toUpperCase()),  requestedResources, cornerUpLeft, cornerDownLeft, cornerUpRight, cornerDownRight, imagePathFront);
            } else if (frontCardJson.get("Constraint").asText().equals("Inkwell")) {
                front = new InkwellCounter(Symbol.valueOf(backCardJson.get("Resource").asText().toUpperCase()), requestedResources, cornerUpLeft, cornerDownLeft, cornerUpRight, cornerDownRight, imagePathFront);
            } else if (frontCardJson.get("Constraint").asText().equals("Quill")) {
                front = new QuillCounter(Symbol.valueOf(backCardJson.get("Resource").asText().toUpperCase()), requestedResources, cornerUpLeft, cornerDownLeft, cornerUpRight, cornerDownRight, imagePathFront);
            } else if (frontCardJson.get("Constraint").asText().equals("Manuscript")) {
                front = new ManuscriptCounter(Symbol.valueOf(backCardJson.get("Resource").asText().toUpperCase()), requestedResources, cornerUpLeft, cornerDownLeft, cornerUpRight, cornerDownRight, imagePathFront);
            } else {
                front = new GoldCardFront(Symbol.valueOf(backCardJson.get("Resource").asText().toUpperCase()), requestedResources, frontCardJson.get("Points").asInt(), cornerUpLeft, cornerDownLeft, cornerUpRight, cornerDownRight, imagePathFront);
            }
            String imagePath = backCardJson.get("ImgFile").asText();
            Side back = new CardBack(Symbol.valueOf(backCardJson.get("Resource").asText().toUpperCase()), imagePath);

            goldCardDeck.addCard(new GoldCard(front, back));
        }
        return goldCardDeck;
    }

    /**
     * Private method to help creating card's corners.
     * Returns a new corner with its value initialized.
     *
     * @param side card's side
     * @param corner corner to be analyzed, its value can be: UpSx, UpDx, DownSx, DownDx
     */
    private Corner createCorner(JsonNode side, String corner) {
        boolean isEmpty = side.get("Corner" + corner).isNull();
        boolean isEvil = side.get("Corner" + corner).asText().equals("Evil");
        if (isEmpty || isEvil) {
            return new Corner(isEvil, null);
        }
        return new Corner( false, Symbol.valueOf(side.get("Corner" + corner).asText().toUpperCase()));

    }
}


