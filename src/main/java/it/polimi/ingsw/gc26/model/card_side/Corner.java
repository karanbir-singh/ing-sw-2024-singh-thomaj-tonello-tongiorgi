package it.polimi.ingsw.gc26.model.card_side;

import java.util.Optional;

public class Corner {
    private boolean isEvil;
    private boolean isHidden;
    private Optional<Symbol> symbol;

    public Corner(boolean isEvil, Symbol symbol) {
        this.isEvil = isEvil;
        this.isHidden = false;
        this.symbol = Optional.ofNullable(symbol);
    }

    public boolean isEvil() {
        return isEvil;
    }

    public void setEvil(boolean evil) {
        isEvil = evil;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public Optional<Symbol> getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = Optional.ofNullable(symbol);
    }
}
