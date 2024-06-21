package it.polimi.ingsw.gc26.model.card_side.ability;

import it.polimi.ingsw.gc26.model.card_side.Corner;
import it.polimi.ingsw.gc26.model.card_side.GoldCardFront;
import it.polimi.ingsw.gc26.model.card_side.Symbol;
import it.polimi.ingsw.gc26.model.player.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

/**
 * This class represents a card with the ability to give extra points for each manuscript symbol present in the board.
 */
public class QuillCounter extends GoldCardFront implements Serializable {
    /**
     * Creates a new instance of a QuillCounter
     * @param sideSymbol Symbol that represents the card's color.
     * @param requestedResources Resources that must be available in the Player's board to be able to play this card.
     * @param UPLEFT Symbol in the up left corner.
     * @param DOWNLEFT Symbol in the down left corner.
     * @param UPRIGHT Symbol in the up right corner.
     * @param DOWNRIGHT Symbol in the down right corner.
     */
    public QuillCounter(Symbol sideSymbol, Map<Symbol, Integer> requestedResources, Corner UPLEFT, Corner DOWNLEFT, Corner UPRIGHT, Corner DOWNRIGHT, String imagePath) {
        super(sideSymbol, requestedResources, 0, UPLEFT, DOWNLEFT, UPRIGHT, DOWNRIGHT, imagePath);
    }

    /**
     * This method return the extra points that are awarded considering its position in the Player's board.
     * @param resources Personal board's resources
     * @param occupiedPositions this parameter is not used in this case, it is useful for Strategy Pattern
     * @param newOccupiedPoint this parameter is not used in this case, it is useful for Strategy Pattern
     * @return points given by this card
     */
    @Override
    public int useAbility(Map<Symbol, Integer> resources, ArrayList<Point> occupiedPositions, Point newOccupiedPoint) {
        return resources.get(Symbol.QUILL);
    }
}
