package it.polimi.ingsw.gc26.view_model;

import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.MainClient;
import it.polimi.ingsw.gc26.ui.UpdateInterface;
import it.polimi.ingsw.gc26.network.VirtualGameController;
import it.polimi.ingsw.gc26.request.view_request.ViewRequest;
import java.util.ArrayDeque;
import java.util.Queue;

public class ViewController {
    /**
     * References to the main client of which this object is controller
     */
    private MainClient mainClient;

    private SimplifiedModel simplifiedModel;

    /**
     * This attribute represents the list of requests sent from server
     */
    private final Queue<ViewRequest> viewRequests;

    /**
     * Attribute used for synchronize actions between server and client
     */
    public final Object lock;

    /**
     * ID of the game where the client participates
     */
    private int gameID;

    public ViewController(MainClient mainClient) {
        this.mainClient = mainClient;
        this.simplifiedModel = new SimplifiedModel();
        this.viewRequests = new ArrayDeque<>();
        this.lock = new Object();
        this.launchExecutor();
    }


    /**
     * Launch a thread for managing server requests
     */
    private void launchExecutor() {
        new Thread(() -> {
            while (true) {
                synchronized (viewRequests) {
                    while (viewRequests.isEmpty()) {
                        try {
                            viewRequests.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    viewRequests.remove().execute(this);
                }
            }
        }).start();
    }

    /**
     * Add a new request to the queue
     *
     * @param viewRequest server's request
     */
    public void addRequest(ViewRequest viewRequest) {
        synchronized (this.viewRequests) {
            viewRequests.add(viewRequest);
            viewRequests.notifyAll();
        }
    }

    public SimplifiedModel getSimplifiedModel() {
        return simplifiedModel;
    }

    /**
     * Updates the client's state (used during the game's initialization)
     *
     * @param clientState
     */
    public void updateClientState(ClientState clientState) {
        mainClient.setClientState(clientState);
    }

    /**
     * Used only with socket implementation
     *
     * @param clientID
     */
    public void setClientID(String clientID) {
        mainClient.setClientID(clientID);
    }

    public void setGameController(VirtualGameController virtualGameController) {
        this.mainClient.setVirtualGameController(virtualGameController);
    }

    public void updateCommonTable(SimplifiedCommonTable simplifiedCommonTable, String message) {
        this.simplifiedModel.setSimplifiedCommonTable(simplifiedCommonTable, message);
    }

    public void updateHand(SimplifiedHand simplifiedHand, String message) {
        this.simplifiedModel.setSimplifiedHand(simplifiedHand, message);
    }

    public void updateSecretHand(SimplifiedHand simplifiedSecretHand, String message) {
        this.simplifiedModel.setSimplifiedSecretHand(simplifiedSecretHand, message);
    }

    public void updatePersonalBoard(SimplifiedPersonalBoard personalBoard, String message) {
        this.simplifiedModel.setPersonalBoard(personalBoard, message);
    }

    public void updateOtherPersonalBoard(SimplifiedPersonalBoard otherPersonalBoard, String message) {
        this.simplifiedModel.setOtherPersonalBoard(otherPersonalBoard, message);
    }

    public void updatePlayer(SimplifiedPlayer simplifiedPlayer, String message) {
        this.simplifiedModel.setSimplifiedPlayer(simplifiedPlayer, message);
    }

    public void updateChat(SimplifiedChat simplifiedChat, String message) {
        this.simplifiedModel.setSimplifiedChat(simplifiedChat, message);
    }

    public void updateGame(SimplifiedGame simplifiedGame, String message) {
        this.simplifiedModel.setSimplifiedGame(simplifiedGame, message);
    }

    /**
     * Reports a message from the server (for example error reports)
     *
     * @param message
     */

    public void showMessage(String message) {
        this.simplifiedModel.getView().showMessage(message);
    }

    /**
     * Reports an error message from the server
     *
     * @param errorMessage
     */
    public void showError(String errorMessage) {
        this.simplifiedModel.getView().showError(errorMessage);
    }

    public void setGameID(int gameID){
        this.gameID = gameID;
    }

    public int getGameID(){
        return this.gameID;
    }

    public void killProcess() {
        this.mainClient.killProcesses();
    }


}