package it.polimi.ingsw.gc26.network.RMI;

import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.network.VirtualView;
import it.polimi.ingsw.gc26.network.socket.client.ClientController;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class VirtualRMIView implements VirtualView {
    //private final VirtualMainController virtualMainController;
    //private VirtualGameController virtualGameController;
    //private MainClient mainClient;
    private ClientController clientController;

//    public VirtualRMIView(/*VirtualMainController virtualMainController,*/ MainClient mainClient) throws RemoteException {
//        //this.virtualMainController = virtualMainController;
//        this.mainClient = mainClient;
//        UnicastRemoteObject.exportObject(this, 0);
//    }

    public VirtualRMIView(ClientController clientController) throws RemoteException {
        this.clientController = clientController;
        UnicastRemoteObject.exportObject(this, 0);
    }

    // These are examples of view updating methods
    //public void notifyMessage(String message) throws RemoteException {
    //  System.out.println(message);
    //}

    @Override
    public void setClientID(String clientID) throws RemoteException {
        //this.mainClient.setClientID(clientID);
        this.clientController.setClientID(clientID);
    }

    @Override
    public void setGameController() throws RemoteException {
    }

    @Override
    public ClientState getState() throws RemoteException {
        return this.clientController.getClientState();
        //return this.mainClient.getClientState();
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
//        System.out.println(STR."\{clientID} chose \{pawnColor}\{pawnColor}");
        this.clientController.updateChosenPawn(pawnColor, clientID);
    }

    /**
     * To notify the current player the successful selection of its mission
     *
     * @param clientID
     * @throws RemoteException
     */
    @Override
    public void updateSelectedMission(String clientID) throws RemoteException {
//        if (this.mainClient.getClientID().equals(clientID)) {
//            System.out.println("You have selected the mission");
//        }
        this.clientController.updateSelectedMission(clientID);
    }

    /**
     * Notifies the current player the successful selection of its card in the hand
     *
     * @param clientID
     * @throws RemoteException
     */
    @Override
    public void updateSelectedCardFromHand(String clientID) throws RemoteException {
//        System.out.println("You have selected a card from the hand");
        this.clientController.updateSelectedCardFromHand(clientID);
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
//        if (this.mainClient.getClientID().equals(clientID)) {
//            System.out.println("You have selected the side in" + cardIndex);
//        }
        this.clientController.updateSelectedSide(cardIndex, clientID);
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
//        if (this.mainClient.getClientID().equals(playerID)) {
//            if (Integer.parseInt(success) == 1) {
//                System.out.println(STR."selected x: \{selectedX} y: \{selectedY}");
//
//            } else {
//                System.out.println(STR."failed to selected x: \{selectedX} y: \{selectedY}");
//
//            }
//        }
        this.clientController.updateSelectedPositionOnBoard(selectedX, selectedY, playerID, success);
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
//        if (Integer.parseInt(success) == 1) { // 1 success
//            System.out.println(STR."\{clientID} played its selected card");
//
//        } else {
//            System.out.println(STR."\{clientID} failed to play its card");
//
//        }
        this.clientController.updatePlayedCardFromHand(clientID, success);
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
//        System.out.println(STR."\{clientID} has \{points}points");
        this.clientController.updatePoints(clientID, points);
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
//        if (this.mainClient.getClientID().equals(clientID)) {
//            System.out.println(STR."you have selected a card from the common table");
//        }
        this.clientController.updateSelectedCardFromCommonTable(clientID, success);
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
//        System.out.println(STR."\{playerID} : \{cardSerialization}");
        this.clientController.showCard(playerID, cardSerialization);
    }

    /**
     * Notifies the clients about a new message in the chat
     *
     * @param message
     * @throws RemoteException
     */
    @Override
    public void showChat(String message) throws RemoteException {
//        System.out.println("message: " + message);
        this.clientController.showChat(message);
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
//        System.out.println(STR."""
//            nickname's personal board \{ownerNickname} :\s
//            \{personalBoardSerialization}""");
        this.clientController.showPersonalBoard(clientID, ownerNickname, personalBoardSerialization);
    }

    /**
     * Notifies all clients who is the first player
     *
     * @param nickname
     * @throws RemoteException
     */
    @Override
    public void updateFirstPlayer(String nickname) throws RemoteException {
//        System.out.println(STR."First player is \{nickname}");
        this.clientController.updateFirstPlayer(nickname);
    }

    /**
     * Notifies the clients the new game's state
     *
     * @param gameState
     * @throws RemoteException
     */
    @Override
    public void updateGameState(String gameState) throws RemoteException {
//        System.out.println(STR."New gameState: \{gameState}");
        this.clientController.updateGameState(gameState);
    }

//    /**
//     * @return
//     * @throws RemoteException
//     */
//    @Override
//    public String getClientID() throws RemoteException {
//        return this.mainClient.getClientID();
//    }


    /**
     * Print messages from the server to the client.
     *
     * @param message
     * @param clientID
     * @throws RemoteException
     */
    @Override
    public void showMessage(String message, String clientID) throws RemoteException {
//        System.out.println(STR."[SERVER] \{message}");
        this.clientController.showMessage(message, clientID);
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
//        System.out.println(STR."Error: \{message}");
        this.clientController.showError(message, clientID);
    }

    @Override
    public void updateState(ClientState clientState) throws RemoteException {
//        this.mainClient.setClientState(clientState);
        this.clientController.updateClientState(clientState);
    }


    // Method for running Graphic UI
    public void runGUI() {

    }
}
