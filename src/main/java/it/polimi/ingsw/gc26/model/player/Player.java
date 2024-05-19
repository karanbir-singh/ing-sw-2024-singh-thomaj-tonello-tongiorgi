package it.polimi.ingsw.gc26.model.player;

import it.polimi.ingsw.gc26.client.Printer;
import it.polimi.ingsw.gc26.model.ModelObservable;
import it.polimi.ingsw.gc26.model.hand.Hand;
import it.polimi.ingsw.gc26.model.utils.TextStyle;
import it.polimi.ingsw.gc26.view_model.SimplifiedPlayer;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents the player in the game
 */
public class Player implements Serializable {
    /**
     * This attributes represents the player with a unique id
     */
    private final String ID;
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
     * This attribute represents the player's hand
     */
    private Hand hand;
    /**
     * This attribute represents the player's secret mission hand (used only in the initial game state)
     */
    private Hand secretMissionHand;
    /**
     * This attribute represents the player's personal board
     */
    private PersonalBoard personalBoard;

    /**
     * This attribute represents the player's state
     */
    private PlayerState state;

    /**
     * Observable to notify client
     */
    private ModelObservable observable;

    /**
     * Initializes the player with an id and a name
     *
     * @param id   unique number that represent the player
     * @param name player's nickname
     */
    public Player(String id, String name) {
        this.ID = id;
        this.nickname = name;
        this.pawnColor = null;
        this.hand = null;
        this.personalBoard = null;
        this.state = PlayerState.PLAYING;
    }

    /**
     * Returns the player's id
     *
     * @return player's id
     */
    public String getID() {
        return this.ID;
    }

    /**
     * This method set the observable
     * @param observable
     */
    public void setObservable(ModelObservable observable) {
        this.observable = observable;
    }

    /**
     * Sets the player nickname
     *
     * @param name new nickname
     */
    public void setNickname(String name) {
        this.nickname = name;
    }

    /**
     * Returns the player's nickname
     *
     * @return nickname
     */
    public String getNickname() {
        return this.nickname;
    }

    /**
     * Sets the pawn color to the chosen one
     *
     * @param color new pawn color
     */
    public boolean setPawn(String color, ArrayList<Pawn> availableColors, String clientID) {
        Pawn pawn;
        boolean result = false;
        switch (color) {
            case "BLUE" -> pawn = Pawn.BLUE;
            case "RED" -> pawn = Pawn.RED;
            case "YELLOW" -> pawn = Pawn.YELLOW;
            case "GREEN" -> pawn = Pawn.GREEN;
            default -> pawn = null;
        }

        if (pawn == null) {
            // TODO gestire cosa fare nella view quando l'utente passa un colore non corretto
            this.observable.notifyError("Color not available!", clientID);
        } else if (!availableColors.contains(pawn)) {
            // TODO gestire cosa fare nella view quando l'utente passa un colore non disponibile
            this.observable.notifyError("Color not available!", clientID);
        } else {
            availableColors.remove(pawn);
            this.pawnColor = pawn;
            result = true;
            this.observable.notifyUpdatePlayer(
                    new SimplifiedPlayer(clientID, nickname, pawnColor, amIFirstPlayer, state),
                    "Colore preso!",
                    clientID);
        }
        return result;
    }

    /**
     * Return the pawn color
     *
     * @return pawn color
     */
    public Pawn getPawnColor() {
        return this.pawnColor;
    }

    /**
     * Sets boolean that indicates the player is the first one to true
     */
    public void setFirstPlayer(String clientID) {
        this.amIFirstPlayer = true;

        this.observable.notifyUpdatePlayer(
                new SimplifiedPlayer(clientID, nickname, pawnColor, amIFirstPlayer, state),
                "You are the first player!",
                clientID);
    }

    /**
     * Sets boolean that indicates the player is not the first one to false
     */
    public void setNotFirstPlayer() {
        this.amIFirstPlayer = false;
    }

    /**
     * Returns a boolean that equals true if the player is the first one to play in the round
     *
     * @return amIFirstPlayer
     */
    public boolean isFirstPlayer() {
        return this.amIFirstPlayer;
    }

    /**
     * Creates an empty hand
     */
    public void createHand() {
        this.hand = new Hand(new ArrayList<>(), this.observable);
    }

    /**
     * Creates an empty secret mission hand
     */
    public void createSecretMissionHand() {
        this.secretMissionHand = new Hand(new ArrayList<>(), this.observable);
        ;
    }

    /**
     * Return the player's secret mission hand
     *
     * @return secretMissionHand
     */
    public Hand getSecretMissionHand() {
        return secretMissionHand;
    }

    /**
     * Return the player's hand
     *
     * @return hand
     */
    public Hand getHand() {
        return this.hand;
    }

    /**
     * Creates the player's personal board
     */
    public void createPersonalBoard() {
        this.personalBoard = new PersonalBoard(this.observable);
    }

    /**
     * Returns the player's personal board
     *
     * @return personal board
     */
    public PersonalBoard getPersonalBoard() {
        return personalBoard;
    }

    /**
     * Returns player's state
     *
     * @return state
     */
    public PlayerState getState() {
        return state;
    }

    /**
     * Sets player's state
     *
     * @param state new state
     */
    public void setState(PlayerState state, String clientID) {
        this.state = state;

        this.observable.notifyUpdatePlayer(
                new SimplifiedPlayer(clientID, nickname, pawnColor, amIFirstPlayer, state),
                "Stato cambiato",
                clientID);
    }

    public String printableScore() {
        int score = this.personalBoard.getScore();
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

    public String[][] printableHandAndMission() {
        if(personalBoard.getSecretMission() == null){
            return new String[0][0];
        }
        String[][] hand = this.hand.printableHand();
        String[][] secretMission = this.personalBoard.getSecretMission().getFront().printableSide();
        Printer printer = new Printer();

        int xDim = hand[0].length + secretMission[0].length + 1;
        int yDim = hand.length;
        int xSeparator = hand[0].length;

        String[][] handAndMission = new String[yDim][xDim];


        for(int i=0; i<handAndMission.length; i++){
            for(int j=0; j<handAndMission[0].length; j++){
                handAndMission[i][j] = " ";
            }
        }

        printer.addPrintable(hand, handAndMission, 0,0);
        for(int i=0; i<yDim-1; i++){
            handAndMission[i][xSeparator] = "||      ";
        }
        handAndMission[0][hand[0].length] = "        Your Secret Mission: ";
        printer.addPrintable(secretMission, handAndMission, hand[0].length + 1, 1);

        return handAndMission;
    }
}

