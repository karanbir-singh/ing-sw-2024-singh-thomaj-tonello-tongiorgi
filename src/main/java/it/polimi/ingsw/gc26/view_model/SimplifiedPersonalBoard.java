package it.polimi.ingsw.gc26.view_model;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.card_side.Symbol;
import it.polimi.ingsw.gc26.model.player.PersonalBoard;
import it.polimi.ingsw.gc26.model.player.Point;
import it.polimi.ingsw.gc26.model.utils.SpecialCharacters;
import it.polimi.ingsw.gc26.model.utils.TextStyle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class SimplifiedPersonalBoard implements Serializable {
    private final int xMin, xMax, yMin, yMax;
    private final int score;
    private final ArrayList<Point> occupiedPositions;
    private final ArrayList<Point> playablePositions;
    private final ArrayList<Point> blockedPositions;
    private final Card secretMission;
    private final Map<Symbol, Integer> visibleResources;
    private int selectedX = 0;
    private int selectedY = 0;

    public SimplifiedPersonalBoard(int xMin, int xMax, int yMax, int yMin, int score, ArrayList<Point> occupiedPositions, ArrayList<Point> playablePositions, ArrayList<Point> blockedPositions, Card secretMission, Map<Symbol, Integer> visibleResources, int selectedX, int selectedY) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMax = yMax;
        this.yMin = yMin;
        this.score = score;
        this.occupiedPositions = occupiedPositions;
        this.playablePositions = playablePositions;
        this.blockedPositions = blockedPositions;
        this.secretMission = secretMission;
        this.visibleResources = visibleResources;
        this.selectedX = selectedX;
        this.selectedY = selectedY;
    }

    public SimplifiedPersonalBoard(PersonalBoard personalBoard) {
        this.xMin = personalBoard.getxMin();
        this.xMax = personalBoard.getxMax();
        this.yMax = personalBoard.getyMax();
        this.yMin = personalBoard.getyMin();
        this.score = personalBoard.getScore();
        this.occupiedPositions = personalBoard.getOccupiedPositions();
        this.playablePositions = personalBoard.getPlayablePositions();
        this.blockedPositions = personalBoard.getBlockedPositions();
        this.secretMission = personalBoard.getSecretMission();
        this.visibleResources = personalBoard.getResources();
        this.selectedX = personalBoard.getSelectedX();
        this.selectedY = personalBoard.getSelectedY();
    }

    public int getxMin() {
        return xMin;
    }

    public int getxMax() {
        return xMax;
    }

    public int getyMin() {
        return yMin;
    }

    public int getyMax() {
        return yMax;
    }

    public int getScore() {
        return score;
    }

    public ArrayList<Point> getOccupiedPositions() {
        return occupiedPositions;
    }

    public ArrayList<Point> getPlayablePositions() {
        return playablePositions;
    }

    public ArrayList<Point> getBlockedPositions() {
        return blockedPositions;
    }

    public Card getSecretMission() {
        return secretMission;
    }

    public Map<Symbol, Integer> getVisibleResources() {
        return visibleResources;
    }

    public int getSelectedX() {
        return selectedX;
    }

    public int getSelectedY() {
        return selectedY;
    }

    public String[][] printablePersonalBoard() {
        int xDim = (xMax - xMin)*2 + 3;
        int yDim = (yMax - yMin)*2 + 3;
        int xOff = xMin*2 -1;
        int yOff = yMin*2 -1;
        String[][] board = new String[yDim][xDim];
        String[][] reverseBoard = new String[yDim+1][xDim];

        //utils
        String blackSquare = SpecialCharacters.SQUARE_BLACK.getCharacter();
        String verticalLine = SpecialCharacters.WHITE_VERTICAL_STRING.getCharacter();
        String blocked =  SpecialCharacters.BLOCKED_POSITION.getCharacter();
        String background = SpecialCharacters.BACKGROUND_BLANK_WIDE.getCharacter();
        String playableSeparator = SpecialCharacters.ORANGE_DIAMOND.getCharacter();
        String styleReset = TextStyle.STYLE_RESET.getStyleCode();
        String selectedStyle = TextStyle.BACKGROUND_BEIGE.getStyleCode() + TextStyle.BLACK.getStyleCode();

        //initialize empty board
        for(int j=0; j<yDim; j++) {
            for(int i=0; i<xDim; i+=2){
                board[j][i] = blackSquare;
            }
            for(int i=1; i<xDim; i+=2){
                board[j][i]= background + blackSquare + background;
            }
        }

        //mark playable positions
        for(Point p: playablePositions) {
            int x = p.getX()*2 - xOff;
            int y = p.getY()*2 - yOff;

            String selected = "";

            if(selectedX == p.getX() && selectedY == p.getY()){
                selected = selectedStyle;
            }

            //x dimension with alignment handling
            if(p.getX() <= -10){
                board[y][x] = p.getX() + playableSeparator ;
            } else if (p.getX() < 0 || p.getX() >= 10){
                board[y][x] = " " + p.getX() + playableSeparator ;
            } else {
                board[y][x] = "  " + p.getX() + playableSeparator ;
            }

            //y dimension with alignment handling
            if(p.getY() <= -10){
                board[y][x] = selected + board[y][x] + p.getY() ;
            } else if (p.getY() < 0 || p.getY() >= 10){
                board[y][x] = selected + board[y][x] + p.getY() + " " ;
            } else {
                board[y][x] = selected + board[y][x] + p.getY() + "  " ;
            }

            board[y+1][x] =  "‾‾‾" + blackSquare + "‾‾‾";
            board[y-1][x] =  "___" + blackSquare + "___";
            board[y-1][x-1] =  blackSquare;
            board[y][x-1] = verticalLine;
            board[y+1][x-1] =  blackSquare;
            board[y][x+1] = verticalLine;
            board[y-1][x+1] = blackSquare;
            board[y+1][x+1] = blackSquare;
        }

        //mark blocked positions
        for(Point p: blockedPositions) {
            int x = p.getX()*2 - xOff;
            int y = p.getY()*2 - yOff;
            board[y][x] = background + blocked + background;
        }

        //represent played cards
        for(Point p: occupiedPositions) {
            String[][] s = p.getSide().printableSide();
            int j=0;
            for(int y=(p.getY())*2 -yOff +1; y>=(p.getY())*2 -yOff - 1; y--){
                int i=2;
                for(int x = p.getX()*2 - xOff +1; x >= (p.getX())*2 - xOff -1; x--){
                    board[y][x] = s[j][i];
                    i--;
                }
                j++;
            }
        }

        int y=0;
        //reverse the board
        for(int j=yDim-1; j>=0; j--){
            for(int i=0; i<xDim; i++) {
                reverseBoard[y][i] = board[j][i] + styleReset;
            }
            y++;
        }
        int x = 1;
        while(x<xDim){
            reverseBoard[yDim][x] = " ";
            x++;
        }

        reverseBoard[yDim][0] = "\nYour resources: ";
        for (Symbol s: Symbol.values()) {
            reverseBoard[yDim][1] = reverseBoard[yDim][1] + visibleResources.get(s) + " " + s.name() + "   ";
        }
        return reverseBoard;
    }

}
