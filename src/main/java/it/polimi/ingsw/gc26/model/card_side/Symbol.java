package it.polimi.ingsw.gc26.model.card_side;

/**
 * This is an enumeration of the possibles values of a symbol
 */
public enum Symbol {
    FUNGI("F"), ANIMAL("A"), PLANT("P"), INSECT("I"), INKWELL("INK"), QUILL("QUILL"), MANUSCRIPT("SCRIPT");
    private final String alias;

    Symbol(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }
}
