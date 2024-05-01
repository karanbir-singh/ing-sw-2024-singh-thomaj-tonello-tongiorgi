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
    private boolean isEvil;
    /**
     * This attribute equals true if the corner is hidden by another corner in the player's board
     */
    private boolean isHidden;
    /**
     * This attribute represents the symbol in the corner. Symbol is empty if there is no symbol in the card.
     */
    private Symbol symbol;

    /**
     * Creates a new instance of MissionLPattern
     * @param isEvil boolean that is true if the corner is no playable
     * @param symbol symbol in the corner, empty() if there is no symbol
     */
    public Corner(boolean isEvil, Symbol symbol) {
        this.isEvil = isEvil;
        this.isHidden = false;
        this.symbol = symbol;
    }

    /**
     * Returns a boolean indicating if the card has no playable corner
     * @return boolean isEvil
     */
    public boolean isEvil() {
        return isEvil;
    }

    /**
     * Sets isEvil = evil
     * @param evil True if the corner is not playable, false otherwise
     */
    public void setEvil(boolean evil) {
        isEvil = evil;
    }

    /**
     * Returns a boolean indicating is the card is covered by another card in the Player's board
     * @return boolean isHidden
     */
    public boolean isHidden() {
        return isHidden;
    }

    /**
     * Sets isHidden = hidden
     * @param hidden True if the corner is hidden by another corner in the Player's board
     */
    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    /**
     * Returns an optional containing the corner's symbol if there is one, or an optional.empty() otherwise
     * @return symbol
     */
    public Optional<Symbol> getSymbol() {
        return Optional.ofNullable(symbol);
    }

    /**
     * Sets symbol in the corner
     * @param symbol Symbol in the corner
     */
    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }
}
