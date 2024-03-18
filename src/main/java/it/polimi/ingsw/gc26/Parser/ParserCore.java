package it.polimi.ingsw.gc26.Parser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.databind.node.ArrayNode;
import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.card.GoldCard;
import it.polimi.ingsw.gc26.model.card_side.*;
import it.polimi.ingsw.gc26.model.card_side.ability.*;

public class ParserCore {
    private final String filePath;
    public ParserCore(String filePath) {
        this.filePath = filePath;
    }
    private JsonNode getRootObject() {
        try {
            ObjectMapper JsonMapper = new ObjectMapper();
            return JsonMapper.readTree(new FileReader(this.filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JsonNode getStarterCards() {
        return this.getRootObject().get("StarterCards");
    }

    public JsonNode getObjectiveCards() {
        JsonNode ObjectiveCardsJson = getRootObject().get("ObjectiveCards");
        ArrayList<Card> ObjectiveCardDeck = new ArrayList<>();
        for ( JsonNode cardJson : ObjectiveCardsJson) {
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

            Side front = new GoldCardFront(Symbol.valueOf(backCardJson.get("Resource").asText().toUpperCase()), requestedResources, frontCardJson.get("Point").asInt(), cornerUpLeft, cornerDownLeft, cornerUpRight, cornerDownRight);
            Side back = front;
            ObjectiveCardDeck.add(new GoldCard(front, back));

        }
        return ObjectiveCardsJson;
    }

    public ArrayList<Card> getResourceCards() {

        JsonNode ResourceCardsJson = getRootObject().get("ResourceCards");
        ArrayList<Card> ResourceCardDeck = new ArrayList<>();
        for ( JsonNode cardJson : ResourceCardsJson) {
            JsonNode frontCardJson = cardJson.get("Front");
            JsonNode backCardJson = cardJson.get("Back");

            // Corners
            Corner cornerUpLeft = createCorner(frontCardJson, "UpSx");
            Corner cornerUpRight = createCorner(frontCardJson, "UpDx");
            Corner cornerDownLeft = createCorner(frontCardJson, "DownSx");
            Corner cornerDownRight = createCorner(frontCardJson, "DownDx");

            Side front = new ResourceCardFront(Symbol.valueOf(backCardJson.get("Resource").asText().toUpperCase()), frontCardJson.get("Points").asInt(), cornerUpLeft, cornerDownLeft, cornerUpRight, cornerDownRight);
            Side back = front;
            ResourceCardDeck.add(new GoldCard(front, back));

        }
        return ResourceCardDeck;

    }
    public ArrayList<Card> getGoldCards() {
        JsonNode goldCardsJson = getRootObject().get("GoldCards");
        ArrayList<Card> goldCardDeck = new ArrayList<>();
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

            if (frontCardJson.get("Constraint").asText().equals("Corner")) {
                front = new CornerCounter(Symbol.valueOf(backCardJson.get("Resource").asText().toUpperCase()),  requestedResources, frontCardJson.get("Points").asInt(), cornerUpLeft, cornerDownLeft, cornerUpRight, cornerDownRight);
            } else if (frontCardJson.get("Constraint").asText().equals("Inkwell")) {
                front = new InkwellCounter(Symbol.valueOf(backCardJson.get("Resource").asText().toUpperCase()), requestedResources, frontCardJson.get("Points").asInt(), cornerUpLeft, cornerDownLeft, cornerUpRight, cornerDownRight);
            } else if (frontCardJson.get("Constraint").asText().equals("Quill")) {
                front = new QuillCounter(Symbol.valueOf(backCardJson.get("Resource").asText().toUpperCase()), requestedResources, frontCardJson.get("Points").asInt(), cornerUpLeft, cornerDownLeft, cornerUpRight, cornerDownRight);
            } else if (frontCardJson.get("Constraint").asText().equals("Manuscript")) {
                front = new ManuscriptCounter(Symbol.valueOf(backCardJson.get("Resource").asText().toUpperCase()), requestedResources, frontCardJson.get("Points").asInt(), cornerUpLeft, cornerDownLeft, cornerUpRight, cornerDownRight);
            } else {
                front = new GoldCardFront(Symbol.valueOf(backCardJson.get("Resource").asText().toUpperCase()), requestedResources, frontCardJson.get("Points").asInt(), cornerUpLeft, cornerDownLeft, cornerUpRight, cornerDownRight);
            }
            front.setPoints(frontCardJson.get("Points").asInt());
            Side back = front;
            goldCardDeck.add(new GoldCard(front, back));

        }
        return goldCardDeck;
    }

    private Corner createCorner(JsonNode side, String corner) {
        boolean isEvil = side.get("Corner" + corner).asText().equals("Evil");
        return new Corner(isEvil, Symbol.valueOf(side.get("Corner" + corner).asText().toUpperCase()));
    }
}


