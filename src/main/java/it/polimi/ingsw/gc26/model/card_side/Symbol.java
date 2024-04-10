package it.polimi.ingsw.gc26.model.card_side;

/**
 * This is an enumeration of the possibles values of a symbol
 */
public enum Symbol {
    FUNGI(Character.toString(0x1F344),Character.toString(0x1F534),"\u001B[31m"),
    ANIMAL(Character.toString(0x1F43A),Character.toString(0x1F535),"\u001B[34m"),
    PLANT(Character.toString(0x1F33F),Character.toString(0x1F7E2),"\u001B[32m"),
    INSECT(Character.toString(0x1F98B),Character.toString(0x1F7E3),"\u001B[35m"),
    INKWELL(Character.toString(0x1FAB6),"", ""),
    QUILL(Character.toString(0x1F3FA),"", ""),
    MANUSCRIPT(Character.toString(0x1F4DC),"", "");
    private final String alias;
    private final String filler;
    private final String fontColor;

    Symbol(String alias, String filler, String fontColor) {
        this.alias = alias;
        this.filler = filler;
        this.fontColor = fontColor;
    }

    public String getAlias() {
        return alias;
    }

    public String getFiller() {return filler;}

    public String getFontColor() {return fontColor;}
}
