package it.polimi.ingsw.gc26.model.card_side;

/**
 * This is an enumeration of the possibles values of a symbol
 */
public enum Symbol {
    FUNGI(0x1F344), ANIMAL(0x1F43A), PLANT(0x1F33F), INSECT(0x1F98B), INKWELL(0x270F), QUILL(0x1F3FA), MANUSCRIPT(0x1F4DC);
    private final int alias;

    Symbol(int alias) {
        this.alias = alias;
    }

    public int getAlias() {
        return alias;
    }
}
