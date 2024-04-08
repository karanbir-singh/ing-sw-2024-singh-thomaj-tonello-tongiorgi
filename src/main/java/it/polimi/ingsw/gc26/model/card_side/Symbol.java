package it.polimi.ingsw.gc26.model.card_side;

/**
 * This is an enumeration of the possibles values of a symbol
 */
public enum Symbol {
    FUNGI(Character.toString(0x1F344)), ANIMAL(Character.toString(0x1F43A)), PLANT(Character.toString(0x1F33F)), INSECT(Character.toString(0x1F98B)), INKWELL(Character.toString(0x1FAB6)), QUILL(Character.toString(0x1F3FA)), MANUSCRIPT(Character.toString(0x1F4DC));
    private final String alias;

    Symbol(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }
}
