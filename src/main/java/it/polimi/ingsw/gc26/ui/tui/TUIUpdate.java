package it.polimi.ingsw.gc26.ui.tui;

import it.polimi.ingsw.gc26.model.game.GameState;
import it.polimi.ingsw.gc26.ui.UpdateInterface;
import it.polimi.ingsw.gc26.utils.ConsoleColors;
import it.polimi.ingsw.gc26.view_model.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The TUIUpdate class implements the UpdateInterface to provide updates to the text-based user interface (TUI).
 * It manages the updates of the simplified game model and interacts with the CLI for displaying information.
 */
public class TUIUpdate implements UpdateInterface {
    /**
     * The simplified game model instance that this class updates.
     */
    private SimplifiedModel miniModel;

    /**
     * Constructor for this class
     *
     * @param miniModel updated mini-model
     */
    public TUIUpdate(SimplifiedModel miniModel) {
        this.miniModel = miniModel;
    }

    /**
     * The CLI instance used for displaying information in the text-based user interface.
     */
    private CLI cli;

    /**
     * Updates the view of the common table.
     *
     * @param simplifiedCommonTable the simplified representation of the common table.
     */
    @Override
    public void updateViewCommonTable(SimplifiedCommonTable simplifiedCommonTable) {
        clearConsole();
        cli = new CLI(miniModel);
        try {
            cli.printGame();
        } catch (Exception e) {
            System.out.println("Common table not available yet!");
        }
        printOptions(getGameState(), miniModel.getSimplifiedGame().getWinners());
    }

    /**
     * Updates the view of a player.
     *
     * @param simplifiedPlayer the simplified representation of a player.
     */
    @Override
    public void updateViewPlayer(SimplifiedPlayer simplifiedPlayer) {
        clearConsole();
        cli = new CLI(miniModel);
        try {
            cli.printGame();
        } catch (Exception e) {
            System.out.println("Player not available yet!");
        }
        printOptions(getGameState(), miniModel.getSimplifiedGame().getWinners());
    }

    /**
     * Updates the view of the hand.
     *
     * @param simplifiedHand the simplified representation of the hand.
     */
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
        printOptions(getGameState(), miniModel.getSimplifiedGame().getWinners());
    }

    /**
     * Updates the view of the secret hand.
     *
     * @param simplifiedSecretHand the simplified representation of the secret hand.
     */
    @Override
    public void updateViewSecretHand(SimplifiedHand simplifiedSecretHand) {
        clearConsole();
        cli = new CLI(miniModel);
        try {
            cli.printGame();
        } catch (Exception e) {
            System.out.println("Secret hand not available yet!");
        }
        printOptions(getGameState(), miniModel.getSimplifiedGame().getWinners());
    }

    /**
     * Updates the view of the personal board.
     *
     * @param personalBoard the simplified representation of the personal board.
     */
    @Override
    public void updateViewPersonalBoard(SimplifiedPersonalBoard personalBoard) {
        clearConsole();
        cli = new CLI(miniModel);
        try {
            cli.printGame();
        } catch (Exception e) {
            System.out.println("Personal not available yet!");
        }
        printOptions(getGameState(), miniModel.getSimplifiedGame().getWinners());
    }

    /**
     * Updates the view of another player's personal board.
     *
     * @param otherPersonalBoard the simplified representation of the other player's personal board.
     */
    @Override
    public void updateViewOtherPersonalBoard(SimplifiedPersonalBoard otherPersonalBoard) {
        return;
        //clearConsole(); i do not want to hide my personal board
//        cli = new CLI(miniModel);
//        try {
//            cli.printOtherGame(otherPersonalBoard); //TODO si puà prendere nickname direttamente da la board senza parametro
//        } catch (Exception e) {
//            System.out.println("Other personal board not available yet!");
//        }
//        printOptions(getGameState());

    }

    /**
     * Updates the view of the chat.
     *
     * @param simplifiedChat the simplified representation of the chat.
     */
    @Override
    public void updateViewSimplifiedChat(SimplifiedChat simplifiedChat) {
        clearConsole();
        try {
            cli.printGame();
        } catch (Exception e) {
            System.out.println("Simplified chat not available yet!");
        }
        printOptions(getGameState(), miniModel.getSimplifiedGame().getWinners());
    }

    /**
     * Updates the game view with the simplified game state.
     *
     * @param simplifiedGame the simplified representation of the game state.
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
        printOptions(getGameState(), miniModel.getSimplifiedGame().getWinners());
    }

    /**
     * Displays a message to the user.
     *
     * @param message the message to be displayed.
     */
    @Override
    public void showMessage(String message) {
        System.out.println("[SERVER]: " + message);
    }

    /**
     * Displays an error message to the user.
     *
     * @param message the error message to be displayed.
     */
    @Override
    public void showError(String message) {
        ConsoleColors.printError("[ERROR]: " + message);
    }

    /**
     * Clears previous print in the interface
     */
    private void clearConsole() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.print("\033[H\033[2J");
            System.out.print("\033\143");
        }
    }

    /**
     * Prints another player's personal board
     *
     * @param nickname other player's nickname
     */
    public void showOtherPersonalBoard(String nickname) {
        cli = new CLI(miniModel);
        try {
            cli.printOtherGame(miniModel.getOthersPersonalBoards().get(nickname));
        } catch (Exception e) {
            System.out.println("Other personal board not available yet!");
        }
        printOptions(getGameState(), miniModel.getSimplifiedGame().getWinners());
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
    public static void printOptions(GameState gameState, ArrayList<String> winners) {
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
            case GAME_STARTED, END_STAGE:
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
            case WINNER:
                System.out.println("Winners are: ");
                int index = 1;
                for (String winner : winners) {
                    System.out.println(index + ") " + winner);
                }
                System.out.println("\n");
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

    /**
     * Print options after the connection is restored
     */
    public void closeErrorPopup() {
        System.out.println("Server is up, you can restart to play");
        TUIUpdate.printOptions(miniModel.getSimplifiedGame().getGameState(), miniModel.getSimplifiedGame().getWinners());
    }
}
