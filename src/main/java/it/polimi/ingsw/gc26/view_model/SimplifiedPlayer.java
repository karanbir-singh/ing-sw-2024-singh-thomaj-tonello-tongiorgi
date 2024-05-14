package it.polimi.ingsw.gc26.view_model;

import it.polimi.ingsw.gc26.Printer;
import it.polimi.ingsw.gc26.model.player.Pawn;
import it.polimi.ingsw.gc26.model.player.PersonalBoard;
import it.polimi.ingsw.gc26.model.player.PlayerState;
import it.polimi.ingsw.gc26.model.utils.TextStyle;

import java.io.Serializable;

public class SimplifiedPlayer implements Serializable {
    /**
     * This attributes represents the player with a unique id
     */
    private String ID;
    /**
     * This attribute represents the player's name and will be shown to the other players
     */
    private String nickname;
    /**
     * This attribute represents the pawn, which represent the player in the board
     */
    private Pawn pawnColor;
    /**
     * This attribute represent whether the player is the first one
     */
    private boolean amIFirstPlayer;
    /**
     * This attribute represents the player's state
     */
    private PlayerState state;

    public SimplifiedPlayer(String ID, String nickname, Pawn pawnColor, boolean amIFirstPlayer, PlayerState state) {
        this.ID = ID;
        this.nickname = nickname;
        this.pawnColor = pawnColor;
        this.amIFirstPlayer = amIFirstPlayer;
        this.state = state;
    }

    public String getID() {
        return ID;
    }

    public String getNickname() {
        return nickname;
    }

    public Pawn getPawnColor() {
        return pawnColor;
    }

    public boolean isAmIFirstPlayer() {
        return amIFirstPlayer;
    }

    public PlayerState getState() {
        return state;
    }

    public String printableScore(PersonalBoard personalBoard) {
        int score = personalBoard.getScore();
        int i;
        StringBuilder s = new StringBuilder();
        String background = "▒";
        String fill = "█";

        if(pawnColor != null){
            s.append(pawnColor.getFontColor());
        }

        for (i=0; i<score; i++){
            s.append(fill);
        }
        while (i<29){
            s.append(background);
            i++;
        }

        s.append(TextStyle.STYLE_RESET.getStyleCode()).append(" ").append(score);

        return s.toString();
    }

    public String[][] printableHandAndMission(PersonalBoard personalBoard, SimplifiedHand hand) {
        if(personalBoard.getSecretMission() == null){
            return new String[0][0];
        }
        String[][] printableHand = hand.printableHand();
        String[][] secretMission = personalBoard.getSecretMission().getFront().printableSide();
        Printer printer = new Printer();

        int xDim = printableHand[0].length + secretMission[0].length + 1;
        int yDim = printableHand.length;
        int xSeparator = printableHand[0].length;

        String[][] handAndMission = new String[yDim][xDim];


        for(int i=0; i<handAndMission.length; i++){
            for(int j=0; j<handAndMission[0].length; j++){
                handAndMission[i][j] = " ";
            }
        }

        printer.addPrintable(printableHand, handAndMission, 0,0);
        for(int i=0; i<yDim-1; i++){
            handAndMission[i][xSeparator] = "||      ";
        }
        handAndMission[0][printableHand[0].length] = "        Your Secret Mission: ";
        printer.addPrintable(secretMission, handAndMission, printableHand[0].length + 1, 1);

        return handAndMission;
    }
}
