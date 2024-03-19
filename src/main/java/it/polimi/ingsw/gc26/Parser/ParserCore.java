package it.polimi.ingsw.gc26.Parser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.databind.node.ArrayNode;
import it.polimi.ingsw.gc26.model.card.*;
import it.polimi.ingsw.gc26.model.card_side.*;
import it.polimi.ingsw.gc26.model.card_side.ability.*;
import it.polimi.ingsw.gc26.model.card_side.mission.*;
import it.polimi.ingsw.gc26.model.deck.*;

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

    public StarterDeck getStarterCards() {
        JsonNode starterCardsJson = getRootObject().get("StarterCards");
        StarterDeck starterCardDeck = new StarterDeck();
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

            Side front = new StarterCardFront( permanentResources, cornerUpLeft, cornerDownLeft, cornerUpRight, cornerDownRight);
            cornerUpLeft = createCorner(backCardJson, "UpSx");
            cornerUpRight = createCorner(backCardJson, "UpDx");
            cornerDownLeft = createCorner(backCardJson, "DownSx");
            cornerDownRight = createCorner(backCardJson, "DownDx");
            Side back = new CardBack(cornerUpLeft, cornerDownLeft, cornerUpRight, cornerDownRight);

            starterCardDeck.addCard(new StarterCard(front, back));

        }
        return starterCardDeck;
    }

    public MissionDeck getMissionCards() {
        JsonNode ObjectiveCardsJson = getRootObject().get("MissionCards");
        MissionDeck MissionCardDeck = new MissionDeck();
        for ( JsonNode cardJson : ObjectiveCardsJson) {
            JsonNode frontCardJson = cardJson.get("Front");
            JsonNode backCardJson = cardJson.get("Back");

            Side front;
            String subclass = frontCardJson.get("Subclass").asText();
            switch (subclass) {
                case "Diagonal" : {
                    front = new MissionDiagonalPattern(frontCardJson.get("Type").asInt(), frontCardJson.get("Points").asInt());
                    break;
                }
                case "L" : {
                    front = new MissionLPattern(frontCardJson.get("Type").asInt(), frontCardJson.get("Points").asInt());
                    break;
                }
                case "Triplet" : {
                    front = new MissionTripletPattern(frontCardJson.get("Type").asInt(), frontCardJson.get("Points").asInt());
                    break;
                }
                case "Item" : {
                    front = new MissionItemPattern(frontCardJson.get("Type").asInt(), frontCardJson.get("Points").asInt());
                    break;
                }
                default: front = null;
            }

            Side back = new CardBack();

            MissionCardDeck.addCard(new MissionCard(front, back));
        }
        return MissionCardDeck;
    }

    public ResourceDeck getResourceCards() {
        JsonNode ResourceCardsJson = getRootObject().get("ResourceCards");
        ResourceDeck ResourceCardDeck = new ResourceDeck();
        for ( JsonNode cardJson : ResourceCardsJson) {
            JsonNode frontCardJson = cardJson.get("Front");
            JsonNode backCardJson = cardJson.get("Back");

            // Corners
            Corner cornerUpLeft = createCorner(frontCardJson, "UpSx");
            Corner cornerUpRight = createCorner(frontCardJson, "UpDx");
            Corner cornerDownLeft = createCorner(frontCardJson, "DownSx");
            Corner cornerDownRight = createCorner(frontCardJson, "DownDx");

            Side front = new ResourceCardFront(Symbol.valueOf(backCardJson.get("Resource").asText().toUpperCase()), frontCardJson.get("Points").asInt(), cornerUpLeft, cornerDownLeft, cornerUpRight, cornerDownRight);
            Side back = new CardBack(Symbol.valueOf(backCardJson.get("Resource").asText().toUpperCase()));

            ResourceCardDeck.addCard(new ResourceCard(front, back));

        }
        return ResourceCardDeck;

    }
    public GoldDeck getGoldCards() {
        JsonNode goldCardsJson = getRootObject().get("GoldCards");
        GoldDeck goldCardDeck = new GoldDeck();
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
            Side back = new CardBack(Symbol.valueOf(backCardJson.get("Resource").asText().toUpperCase()));

            goldCardDeck.addCard(new GoldCard(front, back));
        }
        return goldCardDeck;
    }

    private Corner createCorner(JsonNode side, String corner) {
        boolean isEmpty = side.get("Corner" + corner).isNull();
        boolean isEvil = side.get("Corner" + corner).asText().equals("Evil");
        if (isEmpty || isEvil) {
            return new Corner(isEvil, null);
        }
        return new Corner( false, Symbol.valueOf(side.get("Corner" + corner).asText().toUpperCase()));

    }
}


