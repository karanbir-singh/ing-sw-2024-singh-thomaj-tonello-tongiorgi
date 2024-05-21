package it.polimi.ingsw.gc26.ui.tui;

import it.polimi.ingsw.gc26.ui.UpdateInterface;
import it.polimi.ingsw.gc26.model.player.PersonalBoard;
import it.polimi.ingsw.gc26.view_model.*;

import java.io.IOException;

public class TUIUpdate implements UpdateInterface {
    @Override
    public void updateViewCommonTable(SimplifiedCommonTable simplifiedCommonTable) {
        clearConsole();
    }

    @Override
    public void updateViewPlayer(SimplifiedPlayer simplifiedPlayer) {
        clearConsole();
    }

    @Override
    public void updateViewHand(SimplifiedHand simplifiedHand) {
        clearConsole();
    }

    @Override
    public void updateViewSecretHand(SimplifiedHand simplifiedSecretHand) {
        clearConsole();
    }

    @Override
    public void updateViewPersonalBoard(SimplifiedPersonalBoard personalBoard) {
        clearConsole();
    }

    @Override
    public void updateViewOtherPersonalBoard(SimplifiedPersonalBoard otherPersonalBoard) {
        clearConsole();
    }

    @Override
    public void updateViewSimplifiedChat(SimplifiedChat simplifiedChat) {
        clearConsole();
    }

    /**
     * @param simplifiedGame
     */
    @Override
    public void updateGame(SimplifiedGame simplifiedGame) {
        clearConsole();
    }

    @Override
    public void showMessage(String message) {
        clearConsole();
    }

    @Override
    public void showError(String message) {
        clearConsole();
    }

    private void clearConsole() {
        System.out.println("Cleaning up...");
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.print("\033[H\033[2J");
        }
    }
}
