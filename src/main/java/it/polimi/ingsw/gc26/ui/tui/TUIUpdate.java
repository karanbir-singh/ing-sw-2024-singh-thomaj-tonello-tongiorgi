package it.polimi.ingsw.gc26.ui.tui;

import it.polimi.ingsw.gc26.ui.UpdateInterface;
import it.polimi.ingsw.gc26.model.player.PersonalBoard;
import it.polimi.ingsw.gc26.view_model.*;

import java.io.IOException;

public class TUIUpdate implements UpdateInterface {
    private CLI cli;
    @Override
    public void updateViewCommonTable(SimplifiedModel miniModel) {
        clearConsole();
        cli = new CLI(miniModel);
    }

    @Override
    public void updateViewPlayer(SimplifiedModel miniModel) {
        clearConsole();
        cli = new CLI(miniModel);
    }

    @Override
    public void updateViewHand(SimplifiedModel miniModel) {
        clearConsole();
        cli = new CLI(miniModel);
    }

    @Override
    public void updateViewSecretHand(SimplifiedModel miniModel) {
        clearConsole();
        cli = new CLI(miniModel);
    }
    @Override
    public void updateViewPersonalBoard(SimplifiedModel miniModel) {
        clearConsole();
        cli = new CLI(miniModel);
    }
    @Override
    public void updateViewOtherPersonalBoard(SimplifiedModel miniModel) {
        clearConsole();
        cli = new CLI(miniModel);
    }
    @Override
    public void updateViewSimplifiedChat(SimplifiedModel miniModel) {
        clearConsole();
        cli = new CLI(miniModel);
    }
    /**
     * @param simplifiedGame
     */
    @Override
    public void updateGame(SimplifiedModel miniModel) {
        clearConsole();
        cli = new CLI(miniModel);
    }
    @Override
    public void showMessage(SimplifiedModel miniModel) {
        clearConsole();
        cli = new CLI(miniModel);
    }
    @Override
    public void showError(SimplifiedModel miniModel) {
        clearConsole();
        cli = new CLI(miniModel);
    }

    private void clearConsole() {
        System.out.println("Cleaning up...");
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.print("\033[H\033[2J");
            // System.out.print("\033\143");
        }
    }
}
