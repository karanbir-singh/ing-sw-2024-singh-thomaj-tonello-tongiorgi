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
        return getRootObject().get("ObjectiveCards");
    }

    public JsonNode getResourceCards() {
        return getRootObject().get("ResourceCards");
    }

    private Corner createCorner(JsonNode side, String corner) {
        boolean isEmpty = side.get("Corner" + corner).isNull();
        boolean isEvil = side.get("Corner" + corner).asText().equals("Evil");
        Corner newCorner;
        if(isEmpty || isEvil) {
            newCorner = new Corner(Optional.empty(), isEvil );
        } else {
            newCorner = new Corner(Optional.of(Symbol.valueOf(side.get("Corner" + corner).asText().toUpperCase())), isEvil );
        }
        return newCorner;
    }

    public ArrayList<Card> getGoldCards() {
        JsonNode goldCardsJson = getRootObject().get("GoldCards");
        ArrayList<Card> goldCardDeck = new ArrayList<>();
        for ( JsonNode cardJson : goldCardsJson) {
            JsonNode frontCardJson = cardJson.get("Front");
            JsonNode backCardJson = cardJson.get("Back");

            HashMap<Symbol, Integer> requestedResources = new HashMap<>();

            for (JsonNode resource : frontCardJson.get("Requirements")) {
                requestedResources.put(Symbol.valueOf(resource.asText().toUpperCase()), 1);
            }
            // Corners
            Corner cornerUpLeft = createCorner(frontCardJson, "UpSx");
            Corner cornerUpRight = createCorner(frontCardJson, "UpDx");
            Corner cornerDownLeft = createCorner(frontCardJson, "DownSx");
            Corner cornerDownRight = createCorner(frontCardJson, "DownDx");
            Side front;

            if (frontCardJson.get("Constraint").asText().equals("Corner")) {
                front = new CornerCounter(Optional.of(Symbol.valueOf(backCardJson.get("Resource").asText().toUpperCase())), null, requestedResources, cornerUpLeft, cornerDownLeft, cornerUpRight, cornerDownRight);
            } else if (frontCardJson.get("Constraint").asText().equals("Inkwell")) {
                front = new InkwellCounter(Optional.of(Symbol.valueOf(backCardJson.get("Resource").asText().toUpperCase())), null, requestedResources, cornerUpLeft, cornerDownLeft, cornerUpRight, cornerDownRight);
            } else if (frontCardJson.get("Constraint").asText().equals("Quill")) {
                front = new QuillCounter(Optional.of(Symbol.valueOf(backCardJson.get("Resource").asText().toUpperCase())), null, requestedResources, cornerUpLeft, cornerDownLeft, cornerUpRight, cornerDownRight);
            } else if (frontCardJson.get("Constraint").asText().equals("Manuscript")) {
                front = new ManuscriptCounter(Optional.of(Symbol.valueOf(backCardJson.get("Resource").asText().toUpperCase())), null, requestedResources, cornerUpLeft, cornerDownLeft, cornerUpRight, cornerDownRight);
            } else {
                front = new GoldCardFront(Optional.of(Symbol.valueOf(backCardJson.get("Resource").asText().toUpperCase())), null, requestedResources, cornerUpLeft, cornerDownLeft, cornerUpRight, cornerDownRight);
            }

            Side back = front;
            goldCardDeck.add(new GoldCard(front, back));

        }
        return goldCardDeck;
    }

}


