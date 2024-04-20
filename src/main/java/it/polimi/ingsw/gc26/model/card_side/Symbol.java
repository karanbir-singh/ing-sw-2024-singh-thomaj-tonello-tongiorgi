package it.polimi.ingsw.gc26.model.card_side;

/**
 * This is an enumeration of the possibles values of a symbol
 */
public enum Symbol {
    FUNGI(Character.toString(0x1F344),"❗\uFE0F","\u001B[31m", "\u001B[48;2;248;49;47m"),
    //"\u001B[48;2;248;49;47m"
    //"[48;5;{ID}m");
            //
    //"\u001B[31m"
    ANIMAL(Character.toString(0x1F43A),Character.toString(0x1F535),"\u001B[34m","\u001B[48;2;0;116;186m"),
    PLANT(Character.toString(0x1F33F),Character.toString(0x1F7E2),"\u001B[32m","\u001B[48;2;0;210;106m"),
    INSECT(Character.toString(0x1F98B),Character.toString(0x1F7E3),"\u001B[35m","\u001B[48;2;141;101;197m"),
    INKWELL(Character.toString(0x1FAB6),"", "","\u001B[48;2;242;233;175m"),
    QUILL(Character.toString(0x1F3FA),"", "","\u001B[48;2;242;233;175m"),
    MANUSCRIPT(Character.toString(0x1F4DC),"", "","\u001B[48;2;242;233;175m");
    private final String alias;
    private final String filler;
    private final String fontColor;
    private final String background;

    Symbol(String alias, String filler, String fontColor, String background) {
        this.alias = alias;
        this.filler = filler;
        this.fontColor = fontColor;
        this.background = background;
    }

    public String getAlias() {
        return alias;
    }

    public String getFiller() {return filler;}

    public String getFontColor() {return fontColor;}

    public String getBackground(){
        return background;
    }
}
