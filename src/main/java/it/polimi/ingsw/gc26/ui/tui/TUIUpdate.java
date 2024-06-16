package it.polimi.ingsw.gc26.ui.tui;

import it.polimi.ingsw.gc26.model.player.PersonalBoard;
import it.polimi.ingsw.gc26.ui.UpdateInterface;
import it.polimi.ingsw.gc26.model.game.GameState;
import it.polimi.ingsw.gc26.ui.UpdateInterface;
import it.polimi.ingsw.gc26.view_model.*;

public class TUIUpdate implements UpdateInterface {

    private SimplifiedModel miniModel;

    public TUIUpdate(SimplifiedModel miniModel) {
        this.miniModel = miniModel;
    }

    private CLI cli;

    @Override
    public void updateViewCommonTable(SimplifiedCommonTable simplifiedCommonTable) {
        clearConsole();
        cli = new CLI(miniModel);
        try {
            cli.printGame();
        } catch (Exception e) {
            System.out.println("Common table not available yet!");
        }
        printOptions(getGameState());
    }

    @Override
    public void updateViewPlayer(SimplifiedPlayer simplifiedPlayer) {
        clearConsole();
        cli = new CLI(miniModel);
        try {
            cli.printGame();
        } catch (Exception e) {
            System.out.println("Player not available yet!");
        }
        printOptions(getGameState());
    }

    @Override
    public void updateViewHand(SimplifiedHand simplifiedHand) {
        clearConsole();
        cli = new CLI(miniModel);
        try {
            cli.printGame();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Hand not available yet!");
        }
        printOptions(getGameState());
    }

    @Override
    public void updateViewSecretHand(SimplifiedHand simplifiedSecretHand) {
        clearConsole();
        cli = new CLI(miniModel);
        try {
            cli.printGame();
        } catch (Exception e) {
            System.out.println("Secret hand not available yet!");
        }
        printOptions(getGameState());
    }

    @Override
    public void updateViewPersonalBoard(SimplifiedPersonalBoard personalBoard) {
        clearConsole();
        cli = new CLI(miniModel);
        try {
            cli.printGame();
        } catch (Exception e) {
            System.out.println("Personal not available yet!");
        }
        printOptions(getGameState());
    }

    @Override
    public void updateViewOtherPersonalBoard(SimplifiedPersonalBoard otherPersonalBoard) {
        //clearConsole(); i do not want to hide my personal board
        cli = new CLI(miniModel);
        try {
            cli.printOtherGame(otherPersonalBoard.getNickname()); //TODO si puà prendere nickname direttamente da la board senza parametro
        } catch (Exception e) {
            System.out.println("Other personal board not available yet!");
        }
        printOptions(getGameState());

    }

    @Override
    public void updateViewSimplifiedChat(SimplifiedChat simplifiedChat) {
        clearConsole();
        try {
            cli.printGame();
        } catch (Exception e) {
            System.out.println("Simplified chat not available yet!");
        }
        printOptions(getGameState());
    }

    /**
     * @param simplifiedGame
     */
    @Override
    public void updateGame(SimplifiedGame simplifiedGame) {
        clearConsole();
        cli = new CLI(miniModel);
        try {
            cli.printGame();
        } catch (Exception e) {
            System.out.println("Game not available yet!");
        }
        printOptions(getGameState());
    }

    @Override
    public void showMessage(String message) {
        //clearConsole();
        System.out.println("[SERVER]: " + message);
        //printOptions();
    }

    @Override
    public void showError(String message) {
        System.err.println("[ERROR]: " + message);
        printOptions(getGameState());
    }

    private void clearConsole() {
        System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

//        try {
//            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
        //System.out.print("\033[H\033[2J");
        // System.out.print("\033\143");
        //}
    }

    /**
     * auxiliary function to get the current game state
     *
     * @return
     */
    private GameState getGameState() {
        GameState gameState;
        try {
            gameState = this.miniModel.getSimplifiedGame().getGameState();
        } catch (NullPointerException e) {
            gameState = GameState.WAITING_STARTER_CARD_PLACEMENT;
        }
        return gameState;
    }

    /**
     * Print's the options depending on the current game state
     *
     * @param gameState
     */
    public static void printOptions(GameState gameState) {
        System.out.println("\nSelect your option:");

        switch (gameState) {
            case WAITING_STARTER_CARD_PLACEMENT:
                System.out.println("" +
                        "1) Turn selected card side.\n" +
                        "2) Play card from hand.\n" +
                        "3) Open chat.\n" +
                        "4) Exit game.\n" +
                        "5) Open rulebook.");
                break;
            case WAITING_PAWNS_SELECTION:
                System.out.println("" +
                        "1) Choose pawn color.\n" +
                        "2) Open chat.\n" +
                        "3) Exit game.\n" +
                        "4) Open rulebook.");
                break;
            case WAITING_SECRET_MISSION_CHOICE:
                System.out.println("" +
                        "1) Select secret mission.\n" +
                        "2) Set secret mission.\n" +
                        "3) Open chat.\n" +
                        "4) Exit game.\n" +
                        "5) Open rulebook.");
                break;
            case GAME_STARTED:
                System.out.println("" +
                        "1) Select a card.\n" +
                        "2) Turn selected card side.\n" +
                        "3) Play card from hand.\n" +
                        "4) Select position on board.\n" +
                        "5) Select card from common table.\n" +
                        "6) Draw selected card.\n" +
                        "7) Print player's personal board.\n" +
                        "8) Open chat.\n" +
                        "9) Exit game.\n" +
                        "10) Open rulebook.");
                break;
            case END_STAGE:
                System.out.println("" +
                        "1) Open chat.\n" +
                        "2) Exit game.\n" +
                        "3) Open rulebook.");
                break;
            case null, default:
                //System.out.println("Invalid game state! -> " + gameState);
                break;
        }
    }


    public void closeErrorPopup(){

    }
}
