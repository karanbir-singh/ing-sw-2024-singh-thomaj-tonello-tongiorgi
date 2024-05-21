package it.polimi.ingsw.gc26.ui.tui;

import it.polimi.ingsw.gc26.Printer;
import it.polimi.ingsw.gc26.model.game.GameState;
import it.polimi.ingsw.gc26.ui.UpdateInterface;
import it.polimi.ingsw.gc26.model.player.PersonalBoard;
import it.polimi.ingsw.gc26.view_model.*;

import java.io.IOException;

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
        cli.printableCommonTable();
        printOptions();
    }

    @Override
    public void updateViewPlayer(SimplifiedPlayer simplifiedPlayer) {
        clearConsole();
        cli = new CLI(miniModel);
        cli.printableHandAndMission();
        printOptions();
    }

    @Override
    public void updateViewHand(SimplifiedHand simplifiedHand) {
        clearConsole();
        cli = new CLI(miniModel);
        cli.printableHand();
        printOptions();
    }

    @Override
    public void updateViewSecretHand(SimplifiedHand simplifiedSecretHand) {
        clearConsole();
        cli = new CLI(miniModel);
        cli.printableHandAndMission();
        printOptions();
    }

    @Override
    public void updateViewPersonalBoard(SimplifiedPersonalBoard personalBoard) {
        clearConsole();
        cli = new CLI(miniModel);
        cli.printablePersonalBoard(personalBoard);
        printOptions();
    }

    @Override
    public void updateViewOtherPersonalBoard(SimplifiedPersonalBoard otherPersonalBoard) {
        clearConsole();
        cli = new CLI(miniModel);
        cli.printablePersonalBoard(otherPersonalBoard);
        printOptions();

    }

    @Override
    public void updateViewSimplifiedChat(SimplifiedChat simplifiedChat) {
        clearConsole();
        System.out.println(simplifiedChat.getMessages());
        printOptions();
    }

    /**
     * @param simplifiedGame
     */
    @Override
    public void updateGame(SimplifiedGame simplifiedGame) {
        clearConsole();
        cli = new CLI(miniModel);
        cli.printGame();
        printOptions();
    }

    @Override
    public void showMessage(String message) {
        clearConsole();
        System.out.println(message);
        printOptions();
    }

    @Override
    public void showError(String message) {
        clearConsole();
        System.err.println(message);
        printOptions();
    }

    private void clearConsole() {
        System.out.println("Cleaning up...");
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            //System.out.print("\033[H\033[2J");
            // System.out.print("\033\143");
        }
    }

    private void printOptions() {
        GameState gameState;
        try {
            gameState = this.miniModel.getSimplifiedGame().getGameState();
        } catch (NullPointerException e) {
            gameState = GameState.STARTER_CARDS_DISTRIBUTION;
        }
        System.out.println("\nSelect your option:");

        switch (gameState) {
            case STARTER_CARDS_DISTRIBUTION:
                System.out.println("" +
                        "1) Play card from hand.\n" +
                        "2) Open chat.\n" +
                        "3) Exit game.\n");
                break;
            case WAITING_PAWNS_SELECTION:
                System.out.println("" +
                        "1) Choose pawn color.\n" +
                        "2) Open chat.\n" +
                        "3) Exit game.\n");
                break;
            case WAITING_SECRET_MISSION_CHOICE:
                System.out.println("" +
                        "1) Turn selected card side.\n" +
                        "2) Select secret mission.\n" +
                        "3) Set secret mission.\n" +
                        "4) Open chat.\n" +
                        "5) Exit game.\n");
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
                        "9) Exit game.\n");
                break;
            case END_STAGE:
                System.out.println("" +
                        "1) Open chat.\n" +
                        "2) Exit game.\n");
                break;
            case null, default:
                System.out.println("Invalid game state!");
                break;
        }
    }
}
