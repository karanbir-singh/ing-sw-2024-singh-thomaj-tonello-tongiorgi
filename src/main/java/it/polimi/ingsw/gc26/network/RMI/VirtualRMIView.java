package it.polimi.ingsw.gc26.network.RMI;

import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.network.VirtualGameController;
import it.polimi.ingsw.gc26.network.VirtualMainController;
import it.polimi.ingsw.gc26.network.VirtualView;
import javafx.util.Pair;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class VirtualRMIView  implements VirtualView {
    private final VirtualMainController virtualMainController;
    private VirtualGameController virtualGameController;
    private String clientID;
    private String nickName;
    ClientState clientState;

    public VirtualRMIView(VirtualMainController virtualMainController) throws RemoteException {
        this.virtualMainController = virtualMainController;
        clientState = ClientState.CONNECTION;

        UnicastRemoteObject.exportObject(this, 0);
    }

    // These are examples of view updating methods
    public void notifyMessage(String message) throws RemoteException {
        System.out.println(message);
    }

    @Override
    public void setClientID(String clientID) throws RemoteException {

    }

    @Override
    public void setGameController() throws RemoteException {

    }

    /**
     * To notify not the current player but the other that a pawn color has been selected
     *
     * @param pawnColor
     * @param clientID
     * @throws RemoteException
     */
    @Override
    public void updateChosenPawn(String pawnColor, String clientID) throws RemoteException {

    }

    /**
     * To notify the current player the successful selection of its mission
     *
     * @param cardIndex
     * @param clientID
     * @throws RemoteException
     */
    @Override
    public void updateSelectedMission(String cardIndex, String clientID) throws RemoteException {

    }

    /**
     * Notifies the current player the successful selection of its card in the hand
     *
     * @param clientID
     * @throws RemoteException
     */
    @Override
    public void updateSelectedCardFromHand( String clientID) throws RemoteException {
        System.out.println(STR."Card updated to \{clientID}");
    }

    /**
     * Notifies the current player the successful turned of the selected card
     *
     * @param cardIndex
     * @param clientID
     * @throws RemoteException
     */
    @Override
    public void updateSelectedSide(String cardIndex, String clientID) throws RemoteException {

    }

    /**
     * Notifies the current player the successful selection of the position on the board
     *
     * @param selectedX
     * @param selectedY
     * @param playerID
     * @param success   1 | 0 indicating the success of the operation
     * @throws RemoteException
     */
    @Override
    public void updateSelectedPositionOnBoard(String selectedX, String selectedY, String playerID, String success) throws RemoteException {

    }

    /**
     * Notifies the current player the successful play of the card
     *
     * @param clientID
     * @param success
     * @throws RemoteException
     */
    @Override
    public void updatePlayedCardFromHand(String clientID, String success) throws RemoteException {

    }

    /**
     * Notifies all the clients the current player's points
     *
     * @param clientID
     * @param points
     * @throws RemoteException
     */
    @Override
    public void updatePoints(String clientID, String points) throws RemoteException {

    }

    /**
     * Notifies the current player the success selection of the card in the common table
     *
     * @param clientID
     * @param success
     * @throws RemoteException
     */
    @Override
    public void updateSelectedCardFromCommonTable(String clientID, String success) throws RemoteException {

    }

    /**
     * Prints the card in the client's interface
     *
     * @param playerID
     * @param cardSerialization
     * @throws RemoteException
     */
    @Override
    public void showCard(String playerID, String cardSerialization) throws RemoteException {

    }

    /**
     * Notifies the clients about a new message in the chat
     *
     * @param message
     * @throws RemoteException
     */
    @Override
    public void showChat(String message) throws RemoteException {

    }

    /**
     * Prints the personal board
     *
     * @param clientID
     * @param ownerNickname
     * @param personalBoardSerialization
     * @throws RemoteException
     */
    @Override
    public void showPersonalBoard(String clientID, String ownerNickname, String personalBoardSerialization) throws RemoteException {

    }

    /**
     * Notifies all clients who is the first player
     *
     * @param nickname
     * @throws RemoteException
     */
    @Override
    public void updateFirstPlayer(String nickname) throws RemoteException {

    }


    /**
     * Print messages from the server to the client.
     *
     * @param message
     * @param clientID
     * @throws RemoteException
     */
    @Override
    public void showMessage(String message, String clientID) throws RemoteException {
        System.out.println(STR."[SERVER -> \{clientID}] \{message}");
    }

    /**
     * Notifies the clients about an error.
     *
     * @param message
     * @param clientID
     * @throws RemoteException
     */
    @Override
    public void showError(String message, String clientID) throws RemoteException {

    }

    public void updateState(ClientState clientState) {
        this.clientState = clientState;
    }


    // Method for running Terminal UI
    public Pair<VirtualGameController, String> runTUI() throws RemoteException {
        // TODO gestire la Remote Exception
        //Initial state in CONNECTION
        System.out.println("YOU CONNECTED TO THE SERVER");
        Scanner scanner = new Scanner(System.in);

        System.out.println("INSERISCI IL NICKNAME");
        this.nickName = scanner.nextLine();
        this.clientID = this.virtualMainController.connect(this, this.nickName);

        while (clientState == ClientState.INVALID_NICKNAME || clientState == ClientState.CONNECTION) {
            System.out.println("NICKNAME GIA' PRESO\nNickname:");
            this.nickName = scanner.nextLine();
            this.clientID = this.virtualMainController.connect(this, this.nickName);
        }

        if (clientState == ClientState.CREATOR) {
            System.out.println("THERE ARE NO GAME FREE, YOU MUST CREATE A NEW GAME, HOW MANY PLAYER?");
            String decision = scanner.nextLine();
            this.virtualMainController.createWaitingList(this, this.clientID, this.nickName, Integer.parseInt(decision));
        }
        System.out.println("WAITING PLAYERS...");
        while (clientState == ClientState.WAITING){
            System.out.flush();
        }

        virtualGameController = this.virtualMainController.getVirtualGameController();
        System.out.println("GAME BEGIN");
        return new Pair<>(this.virtualGameController, this.clientID);
    }

    // Method for running Graphic UI
    public void runGUI() {

    }
}
