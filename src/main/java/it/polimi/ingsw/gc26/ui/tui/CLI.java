package it.polimi.ingsw.gc26.ui.tui;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.card.GoldCard;
import it.polimi.ingsw.gc26.model.card.ResourceCard;
import it.polimi.ingsw.gc26.model.card.StarterCard;
import it.polimi.ingsw.gc26.model.card_side.Symbol;
import it.polimi.ingsw.gc26.model.card_side.ability.CornerCounter;
import it.polimi.ingsw.gc26.model.card_side.ability.InkwellCounter;
import it.polimi.ingsw.gc26.model.card_side.ability.ManuscriptCounter;
import it.polimi.ingsw.gc26.model.card_side.ability.QuillCounter;
import it.polimi.ingsw.gc26.model.game.GameState;
import it.polimi.ingsw.gc26.model.player.Pawn;
import it.polimi.ingsw.gc26.model.player.Point;
import it.polimi.ingsw.gc26.model.utils.SpecialCharacters;
import it.polimi.ingsw.gc26.model.utils.TextStyle;
import it.polimi.ingsw.gc26.view_model.SimplifiedCommonTable;
import it.polimi.ingsw.gc26.view_model.SimplifiedHand;
import it.polimi.ingsw.gc26.view_model.SimplifiedModel;
import it.polimi.ingsw.gc26.view_model.SimplifiedPersonalBoard;

import java.util.HashMap;

/**
 * The CLI class provides a command-line interface (CLI) for interacting with the simplified game model.
 * It includes methods for displaying game information to the user.
 */
public class CLI {
    /**
     * The simplified game model instance that this CLI interacts with.
     */
    private SimplifiedModel miniModel;

    /**
     * Constructs a new CLI instance with the specified simplified game model.
     *
     * @param miniModel the simplified game model to be used by this CLI.
     */
    public CLI(SimplifiedModel miniModel) {
        this.miniModel = miniModel;
    }

    /**
     * Prints the current state of the game to the command-line interface.
     */
    public void printGame() {
        if (miniModel != null) {
            SimplifiedCommonTable simplifiedCommonTable = miniModel.getSimplifiedCommonTable();
            SimplifiedPersonalBoard personalBoard = miniModel.getPersonalBoard();

            //COMMON TABLE: check if missions are already present
            String[][] commonTablePrint;
            if (simplifiedCommonTable != null) {
                if (simplifiedCommonTable.getCommonMissions().isEmpty()) {
                    commonTablePrint = printableCommonTable();
                } else {
                    commonTablePrint = printableCommonTableAndMissions();
                }
            } else {
                commonTablePrint = new String[1][1];
                commonTablePrint[0][0] = "\t";
            }
            //SCORES
            String[][] scores;
            if (miniModel.getSimplifiedGame() != null &&
                    (miniModel.getSimplifiedGame().getGameState() == GameState.GAME_STARTED ||
                            miniModel.getSimplifiedGame().getGameState() == GameState.END_STAGE)) {
                scores = allPrintableScores();
            } else {
                scores = new String[1][1];
                scores[0][0] = "\t";
            }

            //PERSONAL BOARD
            String[][] personalBoardPrint;
            if (miniModel.getPersonalBoard() != null) {
                personalBoardPrint = printablePersonalBoard(miniModel.getPersonalBoard());
            } else {
                personalBoardPrint = new String[1][1];
                personalBoardPrint[0][0] = "\t";
            }

            //HAND: check if secret mission is already present
            String[][] handPrint;
            if (miniModel.getSimplifiedHand() != null) {
                if (miniModel.getPersonalBoard() != null && miniModel.getPersonalBoard().getSecretMission() != null) {
                    handPrint = printableHandAndMission();
                } else {
                    handPrint = printableHand();
                }
            } else {
                handPrint = new String[1][1];
                handPrint[0][0] = "\t";
            }

            //SECRET HAND
            String[][] secretHand;
            if (miniModel.getSimplifiedGame() != null &&
                    miniModel.getSimplifiedGame().getGameState() == GameState.WAITING_SECRET_MISSION_CHOICE &&
                    miniModel.getSimplifiedSecretHand().getCards().size() == 2) {
                secretHand = printableSecretHand();
            } else {
                secretHand = new String[1][1];
                secretHand[0][0] = "\t";
            }

            //calculate dimensions
            int yDim = commonTablePrint.length + personalBoardPrint.length + handPrint.length + secretHand.length + 3;
            int xDim = Math.max(Math.max(commonTablePrint[0].length + scores[0].length + 1, personalBoardPrint[0].length), Math.max(handPrint[0].length, secretHand[0].length));

            //utils
            int y = 0;

            //initialize empty matrix
            String[][] printableGame = new String[yDim][xDim];
            for (int i = 0; i < yDim; i++) {
                for (int j = 0; j < xDim; j++) {
                    printableGame[i][j] = "\t";
                }
            }

            //DESIGN THE MATRIX
            //show common table
            printableGame[y][0] = "COMMON TABLE:";
            y++;
            addPrintable(commonTablePrint, printableGame, 0, y);

            //show scores

            addPrintable(scores, printableGame, commonTablePrint[0].length + 1, y + 1);
            y += commonTablePrint.length;

            //show personal board
            printableGame[y][0] = "YOUR PERSONAL BOARD:";
            y++;
            addPrintable(personalBoardPrint, printableGame, (xDim - personalBoardPrint[0].length) / 2, y);
            y += personalBoardPrint.length;

            //show player's hand
            addPrintable(handPrint, printableGame, 0, y);
            y += handPrint.length;

            //show secret hand
            addPrintable(secretHand, printableGame, 0, y);

            showPrintable(printableGame);

        }
    }

    /**
     * Prints the personal board of another player
     *
     * @param miniPB personal board other player
     */
    public void printOtherGame(SimplifiedPersonalBoard miniPB) {
        System.out.println("\n" + miniPB.getNickname().toUpperCase() + "'S PERSONAL BOARD:");

        showPrintable(printablePersonalBoard(miniPB));
    }

    /**
     * Creates a matrix with scores for each player
     *
     * @return string containing the scores
     */
    public String[][] allPrintableScores() {
        HashMap<String, Integer> scoresMap = miniModel.getSimplifiedGame().getScores();
        HashMap<String, Pawn> pawnMap = miniModel.getSimplifiedGame().getPawnsSelected();

        //dimensions
        int xDim = 2;
        int yDim = scoresMap.size() + 1;
        int yCursor = 0;

        String[][] scores = new String[yDim][xDim];

        //calculate the spaces needed to align the names
        int maxLength = 0;
        StringBuilder spaces;
        for (String nickname : scoresMap.keySet()) {
            maxLength = Math.max(maxLength, nickname.length());
        }
        maxLength++;

        //design the matrix
        scores[yCursor][0] = "CURRENT SCORES:";
        scores[yCursor][1] = "";
        yCursor++;

        for (String nickname : scoresMap.keySet()) {
            int i = 0;
            spaces = new StringBuilder();
            while (i + nickname.length() < maxLength) {
                spaces.append(" ");
                i++;
            }
            scores[yCursor][0] = nickname + spaces;
            scores[yCursor][1] = printableScore(scoresMap.get(nickname), pawnMap.get(nickname));
            yCursor++;
        }

        return scores;
    }

    /**
     * Return a string with the score string for a pawn color
     *
     * @param score     points reached by player
     * @param pawnColor pawn color
     * @return encoded string
     */
    public String printableScore(int score, Pawn pawnColor) {
        System.out.println(score);
        int i;
        StringBuilder s = new StringBuilder();
        String background = "▒";
        String fill = "█";

        if (pawnColor != null) {
            s.append(pawnColor.getFontColor());
        }

        for (i = 0; i < score; i++) {
            s.append(fill);
        }
        while (i < 29) {
            s.append(background);
            i++;
        }

        s.append(TextStyle.STYLE_RESET.getStyleCode()).append(" ").append(score);

        return s.toString();
    }

    //COMMON TABLE

    /**
     * Add styles to deck
     *
     * @param ct
     * @param xDeck
     * @param yDeck
     * @param xCardDim
     * @param yCardDim
     */
    private void decorateDeck(String[][] ct, int xDeck, int yDeck, int xCardDim, int yCardDim) {
        //ct[yDeck + yCardDim][xDeck] = "▔▔▔▔▔▔▔▔▔▔▔▔▔";
        xDeck += xCardDim;
        for (int yOff = 0; yOff < yCardDim; yOff++) {
            ct[yDeck + yOff][xDeck] = "║      ";
        }
        //ct[yResource + yCardDim][0] = "╚═" + whiteSquare + "══" + whiteSquare + "══" + whiteSquare + "═╝";
    }

    /**
     * Crates a matrix with the common table elements
     *
     * @return string matrix
     */
    public String[][] printableCommonTable() {
        SimplifiedCommonTable miniCT = miniModel.getSimplifiedCommonTable();

        //dimensions
        int xCardDim = 3;
        int yCardDim = 3;
        int xDim = 3 * (xCardDim + 2) + 1;
        int yDim = 2 * (yCardDim + 3) + 1;
        //components offsets
        int xResource = 1, yResource = 1;
        int xGold = 1, yGold = yResource + yCardDim + 4;

        //utils
        int index = 0; //index to select the drawable element
        String selectedStyle = TextStyle.BACKGROUND_BEIGE.getStyleCode() + TextStyle.BLACK.getStyleCode();
        String styleReset = TextStyle.STYLE_RESET.getStyleCode();
        String blackSquare = SpecialCharacters.SQUARE_BLACK.getCharacter();
        String leftPadding = "    ";
        String rightPadding = "       ";

        //initialize empty matrix
        String[][] ct = new String[yDim][xDim];
        for (int i = 0; i < yDim; i++) {
            if ((i > 0 && i < 4) || (i > 7 && i < 11)) {
                ct[i][0] = "           ";
            } else if (i == 0 || i == 7) {
                ct[i][0] = "Card:      ";
            } else if (i == 4 || i == 11) {
                ct[i][0] = "Points:    " + leftPadding;
            } else if (i == 5 || i == 12) {
                ct[i][0] = "Requires:   " + leftPadding;
            } else {
                ct[i][0] = "";
            }

            if ((i > 0 && i < 4) || (i > 7 && i < 11)) {
                for (int j = 1; j < xDim; j += 5) {
                    ct[i][j] = leftPadding;
                    ct[i][j + 1] = "";
                    ct[i][j + 2] = "";
                    ct[i][j + 3] = "";
                    ct[i][j + 4] = rightPadding;
                }
            } else {
                for (int j = 1; j < xDim; j++) {
                    ct[i][j] = "";
                }
            }
        }

        //RESOURCES

        for (int i = 0; i < 2; i++) {
            int offSet = index * 5 + 1;
            //titles and separators for alignment
            ct[yResource - 1][offSet] = "(" + index + ")";
            ct[yResource - 1][offSet + 1] = " Resource Card";
            if (index == miniCT.getSelectedIndex()) {
                ct[yResource - 1][offSet + 1] = selectedStyle + ct[yResource - 1][offSet + 1] + styleReset;
            }
            ct[yResource - 1][offSet + 2] = blackSquare;
            ct[yResource - 1][offSet + 3] = blackSquare;
            ct[yResource - 1][offSet + 4] = blackSquare;
            xResource++;

            //insert uncovered cards
            if (i < miniCT.getResourceCards().size()) {
                Card r = miniCT.getResourceCards().get(i);
                addPrintable(r.getFront().printableSide(), ct, xResource, yResource);
            } else {
                addPrintable(emptyPrintable(xCardDim, yCardDim), ct, xResource, yResource);
            }
            xResource += xCardDim + 1;

            index++;
        }

        //insert resource deck
        ct[yResource - 1][index * 5 + 1] = "(" + index + ") Resource Deck"; //title
        if (index == miniCT.getSelectedIndex()) {
            ct[yResource - 1][index * 5] = selectedStyle + ct[yResource - 1][index * 5] + styleReset;
        }
        ct[yResource - 1][index * 5 + 4] = blackSquare + blackSquare + blackSquare; //decoration for alignment
        xResource++;

        if (miniCT.getResourceDeck() == null) {
            addPrintable(emptyPrintable(xCardDim, yCardDim), ct, xResource, yResource);
        } else {
            addPrintable(miniCT.getResourceDeck().getBack().printableSide(), ct, xResource, yResource);
            decorateDeck(ct, xResource, yResource, xCardDim, yCardDim);
        }
        index++;

        //insert uncovered gold cards
        for (int i = 0; i < 2; i++) {
            int offSet = (index - 3) * 5 + 1;
            ct[yGold - 1][offSet] = "(" + index + ")";
            ct[yGold - 1][offSet + 1] = " Gold Card    ";
            if (index == miniCT.getSelectedIndex()) {
                ct[yGold - 1][offSet + 1] = selectedStyle + ct[yGold - 1][offSet + 1] + styleReset;
            }
            ct[yGold - 1][offSet + 2] = blackSquare;
            ct[yGold - 1][offSet + 3] = blackSquare;
            ct[yGold - 1][offSet + 4] = blackSquare;

            xGold++;

            if (i < miniCT.getGoldCards().size()) {
                Card g = miniCT.getGoldCards().get(i);
                addPrintable(g.getFront().printableSide(), ct, xGold, yGold);
            } else {
                addPrintable(emptyPrintable(xCardDim, yCardDim), ct, xGold, yGold);
            }
            xGold += xCardDim + 1;

            index++;
        }

        //insert gold deck
        ct[yGold - 1][(index - 3) * 5 + 1] = "(" + index + ") Gold Deck    ";
        if (index == miniCT.getSelectedIndex()) {
            ct[yGold - 1][(index - 3) * 5] = selectedStyle + ct[yGold - 1][(index - 3) * 5] + styleReset;
        }
        ct[yGold - 1][(index - 3) * 5 + 4] = blackSquare + blackSquare + blackSquare;

        xGold++;

        if (miniCT.getGoldDeck() == null) {
            addPrintable(emptyPrintable(xCardDim, yCardDim), ct, xGold, yGold);
        } else {
            addPrintable(miniCT.getGoldDeck().getBack().printableSide(), ct, xGold, yGold);
            decorateDeck(ct, xGold, yGold, xCardDim, yCardDim);
        }

        //insert details
        for (int i = 0; i < 6; i++) {
            Card c;
            int y;
            int x = 2 + xCardDim * (i % 3);
            if (i < 2) {
                c = miniCT.getResourceCards().get(i);
                y = yResource + yCardDim;
            } else if (i == 2) {
                c = miniCT.getResourceDeck();
                y = yResource + yCardDim;
            } else if (i < 5) {
                c = miniCT.getGoldCards().get(i % 3);
                y = yGold + yCardDim;
            } else {
                c = miniCT.getGoldDeck();
                y = yGold + yCardDim;
            }

            //points
            switch (c.getFront()) {
                case CornerCounter cornerCounter -> ct[y][x] = "2 pt " + "x" + Character.toString(0x2B1C);
                case InkwellCounter inkwellCounter -> ct[y][x] = "1 pt " + "x" + Symbol.INKWELL.getAlias();
                case ManuscriptCounter manuscriptCounter -> ct[y][x] = "1 pt " + "x" + Symbol.MANUSCRIPT.getAlias();
                case QuillCounter quillCounter -> ct[y][x] = "1 pt " + "x" + Symbol.QUILL.getAlias();
                case null, default -> ct[y][x] = c.getFront().getPoints() + " pt " + "        ";
            }
            if (c.getFront() instanceof CornerCounter || c.getFront() instanceof InkwellCounter || c.getFront() instanceof ManuscriptCounter || c.getFront() instanceof QuillCounter) {
                ct[y][x + 1] = "       " + blackSquare + blackSquare;
            } else {
                ct[y][x + 1] = blackSquare + blackSquare + blackSquare;
            }
            if (i != 2 && i != 5) {
                ct[y][x + 2] = "    ";
            }
            y++;

            //requirements
            ct[y][x] = "";
            int n;
            int spaces = 5;

            for (Symbol s : c.getFront().getRequestedResources().keySet()) {
                n = c.getFront().getRequestedResources().get(s);
                for (int j = 0; j < n; j++) {
                    ct[y][x] = ct[y][x] + s.getAlias();
                }
                spaces = spaces - n;
            }
            while (spaces > 0) {
                ct[y][x] = ct[y][x] + blackSquare;
                spaces--;
            }
            if (i != 2 && i != 5) {
                ct[y][x + 1] = "      " + blackSquare;
                ct[y][x + 2] = "    ";
            } else {
                ct[y][x + 1] = "        ";
            }
        }

        return ct;
    }

    /**
     * Creates a matrix with common table and mission for client
     *
     * @return matrix string
     */
    public String[][] printableCommonTableAndMissions() {

        //get printable components
        String[][] commonTable = printableCommonTable();
        String[][] missions = printableCommonMissions();
        //utils
        String blackSquare = SpecialCharacters.SQUARE_BLACK.getCharacter();
        //dimensions
        int xDim = commonTable[0].length + missions[0].length + 1;
        int yDim = Math.max(commonTable.length, missions.length);
        //decorations offsets
        int xSeparator = commonTable[0].length;
        int yLine = commonTable.length;

        //initialize empty matrix
        String[][] commonTableAndMissions = new String[yDim][xDim];
        for (int i = 0; i < yDim; i++) {
            for (int j = 0; j < xDim; j++) {
                commonTableAndMissions[i][j] = "";
            }
        }

        //insert decks and drawable cards
        addPrintable(commonTable, commonTableAndMissions, 0, 0);

        //insert empty lines for alignment
        /*for(int j=0; j<(missions.length - commonTable.length); j++){
            if(j==0){
                for(int i=0; i<(xDim-5); i+=5){
                    commonTableAndMissions[j][i] = "    ";
                    commonTableAndMissions[j][i+1] = blackSquare;
                    commonTableAndMissions[j][i+2] = "   " + blackSquare + "   ";
                    commonTableAndMissions[j][i+3] = blackSquare;
                    commonTableAndMissions[j][i+4] = "       ";
                }
            } else {
                for(int i=0; i<(xDim-5); i+=5){
                    commonTableAndMissions[yLine + j][i] = "    ";
                    commonTableAndMissions[yLine + j][i+1] = blackSquare;
                    commonTableAndMissions[yLine + j][i+2] = "   " + blackSquare + "   ";
                    commonTableAndMissions[yLine + j][i+3] = blackSquare;
                    commonTableAndMissions[yLine + j][i+4] = "       ";
                }
            }
        }*/

        //insert vertical separator between drawable and missions
        for (int i = 0; i < yDim; i++) {
            if (i != 6) {
                commonTableAndMissions[i][xSeparator] = "||      ";
            }
        }

        //insert common mission cards (vertical)
        addPrintable(missions, commonTableAndMissions, commonTable[0].length + 1, 0);

        return commonTableAndMissions;
    }

    /**
     * Creates a matrix with common missions
     *
     * @return string matrix
     */
    public String[][] printableCommonMissions() {
        SimplifiedCommonTable miniCT = miniModel.getSimplifiedCommonTable();

        //calculate dimensions
        int yDim = (miniCT.getCommonMissions().get(0).getFront().printableSide().length + 1) * 2 + 1;
        int xDim = miniCT.getCommonMissions().get(0).getFront().printableSide()[0].length;
        int y;
        //initialize empty matrix
        String[][] missions = new String[yDim][xDim];
        for (int i = 0; i < yDim; i++) {
            for (int j = 0; j < xDim; j++) {
                missions[i][j] = "";
            }
        }

        //titles
        missions[0][0] = "Common Mission 0           ";
        missions[yDim / 2 + 1][0] = "Common Mission 1           ";

        //add printable cards vertically
        y = 1;
        for (Card m : miniCT.getCommonMissions()) {
            addPrintable(m.getFront().printableSide(), missions, 0, y);
            y += m.getFront().printableSide().length + 2;
        }

        return missions;
    }

    //PERSONAL BOARD

    /**
     * Creates a matrix of adaptive dimension with cards in the personal board
     *
     * @param miniPB personal board
     * @return string matrix
     */
    public String[][] printablePersonalBoard(SimplifiedPersonalBoard miniPB) {
        int xDim = (miniPB.getxMax() - miniPB.getxMin()) * 2 + 3;
        int yDim = (miniPB.getyMax() - miniPB.getyMin()) * 2 + 3;
        int xOff = miniPB.getxMin() * 2 - 1;
        int yOff = miniPB.getyMin() * 2 - 1;
        String[][] board = new String[yDim][xDim];
        String[][] reverseBoard = new String[yDim + 2][xDim];

        //utils
        String blackSquare = SpecialCharacters.SQUARE_BLACK.getCharacter();
        String verticalLine = SpecialCharacters.WHITE_VERTICAL_STRING.getCharacter();
        String blocked = SpecialCharacters.BLOCKED_POSITION.getCharacter();
        String background = SpecialCharacters.BACKGROUND_BLANK_WIDE.getCharacter();
        String playableSeparator = SpecialCharacters.ORANGE_DIAMOND.getCharacter();
        String styleReset = TextStyle.STYLE_RESET.getStyleCode();
        String selectedStyle = TextStyle.BACKGROUND_BEIGE.getStyleCode() + TextStyle.BLACK.getStyleCode();

        //initialize empty board
        for (int j = 0; j < yDim; j++) {
            for (int i = 0; i < xDim; i += 2) {
                board[j][i] = blackSquare;
            }
            for (int i = 1; i < xDim; i += 2) {
                board[j][i] = background + blackSquare + background;
            }
        }

        //mark playable positions
        for (Point p : miniPB.getPlayablePositions()) {
            int x = p.getX() * 2 - xOff;
            int y = p.getY() * 2 - yOff;

            String selected = "";

            if (miniPB.getSelectedX() == p.getX() && miniPB.getSelectedY() == p.getY()) {
                selected = selectedStyle;
            }

            //x dimension with alignment handling
            if (p.getX() <= -10) {
                board[y][x] = p.getX() + playableSeparator;
            } else if (p.getX() < 0 || p.getX() >= 10) {
                board[y][x] = " " + p.getX() + playableSeparator;
            } else {
                board[y][x] = "  " + p.getX() + playableSeparator;
            }

            //y dimension with alignment handling
            if (p.getY() <= -10) {
                board[y][x] = selected + board[y][x] + p.getY();
            } else if (p.getY() < 0 || p.getY() >= 10) {
                board[y][x] = selected + board[y][x] + p.getY() + " ";
            } else {
                board[y][x] = selected + board[y][x] + p.getY() + "  ";
            }

            board[y + 1][x] = "‾‾‾" + blackSquare + "‾‾‾";
            board[y - 1][x] = "___" + blackSquare + "___";
            board[y - 1][x - 1] = blackSquare;
            board[y][x - 1] = verticalLine;
            board[y + 1][x - 1] = blackSquare;
            board[y][x + 1] = verticalLine;
            board[y - 1][x + 1] = blackSquare;
            board[y + 1][x + 1] = blackSquare;
        }

        //mark blocked positions
        for (Point p : miniPB.getBlockedPositions()) {
            int x = p.getX() * 2 - xOff;
            int y = p.getY() * 2 - yOff;
            board[y][x] = background + blocked + background;
        }

        //represent played cards
        for (Point p : miniPB.getOccupiedPositions()) {
            String[][] s = null;
            try {
                s = p.getSide().printableSide();

            } catch (Exception e) {
                e.printStackTrace();
            }
            int j = 0;
            for (int y = (p.getY()) * 2 - yOff + 1; y >= (p.getY()) * 2 - yOff - 1; y--) {
                int i = 2;
                for (int x = p.getX() * 2 - xOff + 1; x >= (p.getX()) * 2 - xOff - 1; x--) {
                    board[y][x] = s[j][i];
                    i--;
                }
                j++;
            }
        }

        reverseBoard[0][0] = "\nYOUR PERSONAL BOARD:\n";
        for (int i = 0; i < xDim; i++) {
            reverseBoard[0][i] = "\t";
            reverseBoard[yDim + 1][i] = "\t";
        }
        int y = 1;
        //reverse the board
        for (int j = yDim - 1; j >= 0; j--) {
            for (int i = 0; i < xDim; i++) {
                reverseBoard[y][i] = board[j][i] + styleReset;
            }
            y++;
        }

        reverseBoard[yDim+1][0] = "\nResources: ";
        for (Symbol s: Symbol.values()) {
            reverseBoard[yDim+1][1] = reverseBoard[yDim+1][1] + miniPB.getVisibleResources().get(s) + " " + s.name() + "   ";
        }
        return reverseBoard;
    }

    /**
     * Creates a matrix with card in the hand and personal mission
     *
     * @return string matrix
     */
    public String[][] printableHandAndMission() {
        SimplifiedPersonalBoard miniPB = miniModel.getPersonalBoard();

        if (miniPB.getSecretMission() == null) {
            String[][] empty = new String[1][1];
            empty[0][0] = "\t";
            return empty;
        }
        String[][] printableHand = printableHand();
        String[][] secretMission = miniPB.getSecretMission().getFront().printableSide();

        int xDim = printableHand[0].length + secretMission[0].length + 1;
        int yDim = printableHand.length;
        int xSeparator = printableHand[0].length;

        String[][] handAndMission = new String[yDim][xDim];


        for (int i = 0; i < handAndMission.length; i++) {
            for (int j = 0; j < handAndMission[0].length; j++) {
                handAndMission[i][j] = " ";
            }
        }

        addPrintable(printableHand, handAndMission, 0, 0);
        for (int i = 1; i < yDim - 1; i++) {
            handAndMission[i][xSeparator] = "||      ";
        }
        handAndMission[1][printableHand[0].length] = "         Your Secret Mission: ";
        addPrintable(secretMission, handAndMission, printableHand[0].length + 1, 2);

        return handAndMission;
    }

    //HAND

    /**
     * Creates a String matrix with a printable representation of the hand
     */
    public String[][] printableHand() {
        SimplifiedHand miniHand = miniModel.getSimplifiedHand();

        if (miniHand.getCards().isEmpty()) {
            String[][] hand = new String[1][1];
            hand[0][0] = "\t\n";
            return hand;
        } else {
            //utils
            String selectedStyle = TextStyle.BACKGROUND_BEIGE.getStyleCode() + TextStyle.BLACK.getStyleCode();
            String blackSquare = SpecialCharacters.SQUARE_BLACK.getCharacter();

            //calculate dimensions
            int xCardDim = miniHand.getCards().get(0).getFront().printableSide()[0].length;
            int yCardDim = miniHand.getCards().get(0).getFront().printableSide().length;
            int xMax = (xCardDim + 1) * 3 + 1;
            int yMax = yCardDim + 5;

            //index of the select the card
            int index = 0;

            //initialize empty matrix
            String[][] myHand = new String[yMax][xMax];
            for (int j = 0; j < yMax; j++) {
                for (int i = 0; i < xMax; i++) {
                    myHand[j][i] = " ";
                }
            }

            //titles

            myHand[0][0] = "\nYOUR HAND:\n";
            myHand[1][0] = "Card:      ";
            for (int i = 2; i < yCardDim + 2; i++) {
                myHand[i][0] = "            ";
            }
            myHand[5][0] = "Type:     ";
            myHand[6][0] = "Points:   ";
            myHand[7][0] = "Requires: ";

            for (int i = 0; i < 3; i++) {
                int x = 1 + i * (xCardDim + 1);
                int y = 1;
                if (i < miniHand.getCards().size()) {
                    Card c = miniHand.getCards().get(i);

                    //titles
                    if (c == miniHand.getSelectedCard()) {
                        if (miniHand.getSelectedSide() == c.getBack()) {
                            myHand[y][x] = index + ") " + selectedStyle + " Back  \u001B[0m   ";
                        } else {
                            myHand[y][x] = index + ") " + selectedStyle + " Front \u001B[0m   ";
                        }
                    } else {
                        myHand[y][x] = index + ")" + "  Front    ";
                    }
                    myHand[y][x] = myHand[y][x] + blackSquare + blackSquare + blackSquare;
                    y++;

                    //printable side
                    if (c == miniHand.getSelectedCard() && miniHand.getSelectedSide().equals(c.getBack())) {
                        addPrintable(c.getBack().printableSide(), myHand, x, y);
                    } else {
                        addPrintable(c.getFront().printableSide(), myHand, x, y);
                    }
                    x += xCardDim;
                    for (int j = 0; j < yCardDim; j++) {
                        myHand[y + j][x] = "          ";
                    }
                    y += yCardDim;

                    //card type
                    if (c instanceof GoldCard) {
                        myHand[y][x] = "Gold         ";
                    } else if (c instanceof ResourceCard) {
                        myHand[y][x] = "Resource     ";
                    } else if (c instanceof StarterCard) {
                        myHand[y][x] = "Starter      ";
                    }
                    myHand[y][x] = myHand[y][x] + blackSquare + blackSquare + blackSquare;
                    y++;

                    //points
                    if (!(c == miniHand.getSelectedCard() && miniHand.getSelectedSide().equals(c.getBack()))) {
                        switch (c.getFront()) {
                            case CornerCounter cornerCounter ->
                                    myHand[y][x] = "2 pt " + "x" + Character.toString(0x2B1C);
                            case InkwellCounter inkwellCounter ->
                                    myHand[y][x] = "1 pt " + "x" + Symbol.INKWELL.getAlias();
                            case ManuscriptCounter manuscriptCounter ->
                                    myHand[y][x] = "1 pt " + "x" + Symbol.MANUSCRIPT.getAlias();
                            case QuillCounter quillCounter -> myHand[y][x] = "1 pt " + "x" + Symbol.QUILL.getAlias();
                            case null, default -> myHand[y][x] = c.getFront().getPoints() + " pt " + "        ";
                        }
                        if (c.getFront() instanceof CornerCounter || c.getFront() instanceof InkwellCounter || c.getFront() instanceof ManuscriptCounter || c.getFront() instanceof QuillCounter) {
                            myHand[y][x] = myHand[y][x] + "       " + blackSquare + blackSquare;
                        } else {
                            myHand[y][x] = myHand[y][x] + blackSquare + blackSquare + blackSquare;
                        }

                    } else {
                        myHand[y][x] = "0 pt         " + blackSquare + blackSquare + blackSquare;
                    }
                    y++;

                    //requirements
                    myHand[y][x] = "";
                    int n;
                    int spaces = 5;

                    if (!(c == miniHand.getSelectedCard() && miniHand.getSelectedSide().equals(c.getBack()))) {
                        for (Symbol s : c.getFront().getRequestedResources().keySet()) {
                            n = c.getFront().getRequestedResources().get(s);
                            for (int j = 0; j < n; j++) {
                                myHand[y][x] = myHand[y][x] + s.getAlias();
                            }
                            spaces = spaces - n;
                        }
                    }
                    while (spaces > 0) {
                        myHand[y][x] = myHand[y][x] + blackSquare;
                        spaces--;
                    }
                    myHand[y][x] = myHand[y][x] + "      " + blackSquare;

                } else if (!(!miniHand.getCards().isEmpty() && miniHand.getCards().get(0) instanceof StarterCard)) {
                    //EMPTY CARDS
                    myHand[y][x] = "            ";
                    y++;
                    //add empty printable
                    addPrintable(emptyPrintable(xCardDim, yCardDim), myHand, x, y);
                    x += xCardDim;
                    for (int j = 0; j < yCardDim; j++) {
                        myHand[y + j][x] = "        ";
                    }
                    y += yCardDim;

                    //type
                    myHand[y][x] = "           " + blackSquare + blackSquare + blackSquare;
                    y++;

                    //points
                    myHand[y][x] = "           " + blackSquare + blackSquare + blackSquare;
                    y++;

                    //requirements
                    myHand[y][x] = blackSquare + blackSquare + blackSquare + blackSquare + blackSquare + "    " + blackSquare;
                }
                index++;
            }

            //align right border
            for (int i = yCardDim + 2; i < yMax - 1; i++) {
                myHand[i][xMax - 1] = myHand[i][xMax - 1] + "  ";
            }

            return myHand;

        }
    }

    //SECRET MISSION HAND

    /**
     * Creates a matrix with secret mission cards
     *
     * @return string matrix
     */
    public String[][] printableSecretHand() {
        SimplifiedHand miniHand = miniModel.getSimplifiedSecretHand();

        if (miniHand.getCards().isEmpty()) {
            String[][] hand = new String[1][1];
            hand[0][0] = "\t\n";
            return hand;
        } else {
            //utils
            String selectedStyle = TextStyle.BACKGROUND_BEIGE.getStyleCode() + TextStyle.BLACK.getStyleCode();
            String blackSquare = SpecialCharacters.SQUARE_BLACK.getCharacter();

            //calculate dimensions
            int xCardDim = miniHand.getCards().get(0).getFront().printableSide()[0].length;
            int yCardDim = miniHand.getCards().get(0).getFront().printableSide().length;
            int xMax = (xCardDim + 2) * 2;
            int yMax = yCardDim + 2;
            //cursors
            int y = 0, x = 0;
            //initialize matrix
            String[][] myHand = new String[yMax][xMax];

            for (int j = 0; j < yMax; j++) {
                for (int i = 0; i < xMax; i++) {
                    myHand[j][i] = " ";
                }
            }
            //titles

            myHand[0][0] = "\nCHOOSE YOUR SECRET MISSION:\nIf you complete the mission you will receive extra points " + SpecialCharacters.CUP.getCharacter() + " at the end of the game\n";
            y++;
            for (int i = 0; i < 2; i++) {
                if (miniHand.getSelectedCard() == miniHand.getCards().get(i)) {
                    myHand[y][x] = "    " + selectedStyle + " Mission  " + i + " \u001B[0m             ";
                } else {
                    myHand[y][x] = "     Mission " + i + "               ";
                }
                x++;
            }
            x = 0;
            y++;
            //printable cards
            for (Card c : miniHand.getCards()) {
                for (int j = 0; j < yCardDim; j++) {
                    myHand[y + j][x] = "   ";
                }
                x++;
                addPrintable(c.getFront().printableSide(), myHand, x, y);
                x += xCardDim;
                for (int j = 0; j < yCardDim; j++) {
                    myHand[y + j][x] = "     ";
                }
                x++;
            }

            return myHand;
        }
    }

    /**
     * Puts together different component of the game to print
     *
     * @param printable
     * @param context
     * @param xBase
     * @param yBase
     */
    private void addPrintable(String[][] printable, String[][] context, int xBase, int yBase) {
        int y = 0, x;

        for (String[] row : printable) {
            x = 0;
            try {
                for (String col : row) {
                    context[yBase + y][xBase + x] = col;
                    x++;
                }
            } catch (Exception e) {
                String a = "";
            }
            y++;
        }
    }

    /**
     * Prints the current game state with the available printable components
     *
     * @param printable
     */
    private void showPrintable(String[][] printable) {
        for (String[] row : printable) {
            for (String col : row) {
                System.out.print(col);
            }
            System.out.print("\n");
        }
    }

    /**
     * Add spaced to avoid problems in format
     * @param xCardDim
     * @param yCardDim
     * @return
     */
    private String[][] emptyPrintable(int xCardDim, int yCardDim) {
        String[][] s = new String[yCardDim][xCardDim];

        String decoration = SpecialCharacters.SQUARE_BLACK.getCharacter();
        String backgroundSymbol = SpecialCharacters.SQUARE_BLACK.getCharacter();
        String blank = SpecialCharacters.BACKGROUND_BLANK_WIDE.getCharacter();
        String backgroundColor = TextStyle.BACKGROUND_BLACK.getStyleCode();
        String reset = TextStyle.STYLE_RESET.getStyleCode();

        //corners
        s[0][0] = backgroundSymbol;
        s[0][xCardDim - 1] = backgroundSymbol;
        s[yCardDim - 1][0] = backgroundSymbol;
        s[yCardDim - 1][xCardDim - 1] = backgroundSymbol;

        //decoration
        s[0][xCardDim / 2] = blank + decoration + blank;
        s[yCardDim / 2][xCardDim / 2] = decoration + decoration + decoration;
        s[yCardDim - 1][xCardDim / 2] = blank + decoration + blank;

        //rest of the card
        for (int i = 0; i < yCardDim; i++) {
            for (int j = 0; j < xCardDim; j++) {
                if (s[i][j] == null) {
                    s[i][j] = blank;
                }
                s[i][j] = backgroundColor + s[i][j] + reset;
            }
        }
        return s;
    }
}
