package it.polimi.ingsw.gc26.model.card_side.ability;

import it.polimi.ingsw.gc26.model.card_side.Corner;
import it.polimi.ingsw.gc26.model.card_side.GoldCardFront;
import it.polimi.ingsw.gc26.model.card_side.Symbol;
import it.polimi.ingsw.gc26.model.player.Point;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

/**
 * This class represents a card with the ability to give extra points for each corner that it covers once placed.
 */
public class CornerCounter extends GoldCardFront implements Serializable {
    /**
     * Creates a new instance of a CornerCounter
     *
     * @param sideSymbol         Symbol that represents the card's color.
     * @param requestedResources Resources that must be available in the Player's board to be able to play this card.
     * @param UPLEFT             Symbol in the up left corner.
     * @param DOWNLEFT           Symbol in the down left corner.
     * @param UPRIGHT            Symbol in the up right corner.
     * @param DOWNRIGHT          Symbol in the down right corner.
     * @param imagePath path to corresponding image
     */
    public CornerCounter(Symbol sideSymbol, Map<Symbol, Integer> requestedResources, Corner UPLEFT, Corner DOWNLEFT, Corner UPRIGHT, Corner DOWNRIGHT, String imagePath) {
        super(sideSymbol, requestedResources, 0, UPLEFT, DOWNLEFT, UPRIGHT, DOWNRIGHT, imagePath);
    }

    /**
     * This method return the extra points that are awarded considering its position in the Player's board.
     *
     * @param resources         this parameter is not used in this case, it is useful for Strategy Pattern
     * @param occupiedPositions list of the position occupied in the Player's board
     * @param newOccupiedPoint  point where the new card is being placed
     * @return points given by this card
     */
    @Override
    public int useAbility(Map<Symbol, Integer> resources, ArrayList<Point> occupiedPositions, Point newOccupiedPoint) {
        int corners = 0;
        for (Point p1 : occupiedPositions) {
            if (p1.getX() == newOccupiedPoint.getX() + 1 && p1.getY() == newOccupiedPoint.getY() + 1) {
                corners++;
            } else if (p1.getX() == newOccupiedPoint.getX() - 1 && p1.getY() == newOccupiedPoint.getY() + 1) {
                corners++;
            } else if (p1.getX() == newOccupiedPoint.getX() - 1 && p1.getY() == newOccupiedPoint.getY() - 1) {
                corners++;
            } else if (p1.getX() == newOccupiedPoint.getX() + 1 && p1.getY() == newOccupiedPoint.getY() - 1) {
                corners++;
            }
        }
        return corners * 2;
    }
}
