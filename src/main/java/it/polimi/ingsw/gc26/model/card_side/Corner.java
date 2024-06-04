package it.polimi.ingsw.gc26.model.card_side;

import java.io.Serializable;
import java.util.Optional;

/**
 * This class represent a generic corner in the card
 */
public class Corner implements Serializable {
    /**
     * This attribute equals true if there is no corner in the card
     */
    private final boolean isEvil;
    /**
     * This attribute equals true if the corner is hidden by another corner in the player's board
     */
    private boolean isHidden;
    /**
     * This attribute represents the symbol in the corner. Symbol is empty if there is no symbol in the card.
     */
    private final Symbol symbol;

    /**
     * Constructor to build MissionLPattern
     *
     * @param isEvil boolean that is true if the corner is no playable
     * @param symbol symbol in the corner, empty() if there is no symbol
     */
    public Corner(boolean isEvil, Symbol symbol) {
        this.isEvil = isEvil;
        this.isHidden = false;
        this.symbol = symbol;
    }

    /**
     * Constructor to build corners for socket stream
     *
     * @param isEvil
     * @param symbol
     * @param isHidden
     */
    public Corner(boolean isEvil, Symbol symbol, boolean isHidden) {
        this.isEvil = isEvil;
        this.isHidden = isHidden;
        this.symbol = symbol;
    }

    /**
     * Returns a boolean indicating if the corner exists
     *
     * @return boolean isEvil
     */
    public boolean isEvil() {
        return isEvil;
    }

    /**
     * Returns a boolean indicating is the card is covered by another card in the personal board
     *
     * @return boolean isHidden
     */
    public boolean isHidden() {
        return isHidden;
    }

    /**
     * Sets isHidden = hidden
     *
     * @param hidden True if the corner is hidden by another corner in the personal board
     */
    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    /**
     * Returns an optional containing the corner's symbol if there is one, or an optional.empty() otherwise
     *
     * @return symbol
     */
    public Optional<Symbol> getSymbol() {
        return Optional.ofNullable(symbol);
    }
}
