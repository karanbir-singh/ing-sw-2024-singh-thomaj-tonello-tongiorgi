package it.polimi.ingsw.gc26.view_model;

import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.MainClient;
import it.polimi.ingsw.gc26.network.VirtualGameController;
import it.polimi.ingsw.gc26.request.view_request.ViewRequest;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Controller class responsible for managing the client-side view interactions and synchronization with the server.
 */
public class ViewController {
    /**
     * References to the main client of which this object is controller
     */
    private final MainClient mainClient;

    /**
     * Simplified model in the client
     */
    private final SimplifiedModel simplifiedModel;

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

    /**
     * Constructs a ViewController object initialized with a main client instance.
     * Initializes the simplified model, view request queue, synchronization object, and starts the executor.
     *
     * @param mainClient The main client object managing the connection and communication with the server.
     */
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

    /**
     * Returns simplified model
     *
     * @return
     */
    public SimplifiedModel getSimplifiedModel() {
        return simplifiedModel;
    }

    /**
     * Updates the client's state (used during the game's initialization)
     *
     * @param clientState state of the client
     */
    public void updateClientState(ClientState clientState) {
        mainClient.setClientState(clientState);
    }

    /**
     * Used only with socket implementation
     *
     * @param clientID ID of the client
     */
    public void setClientID(String clientID) {
        mainClient.setClientID(clientID);
    }

    /**
     * Sets the game controller used to call methods in the server
     *
     * @param virtualGameController game controller
     */
    public void setGameController(VirtualGameController virtualGameController) {
        this.mainClient.setVirtualGameController(virtualGameController);
    }

    /**
     * Wrapper to update the view of the common table.
     *
     * @param simplifiedCommonTable the simplified representation of the common table.
     * @param message               to be displayed with the update
     */
    public void updateCommonTable(SimplifiedCommonTable simplifiedCommonTable, String message) {
        this.simplifiedModel.setSimplifiedCommonTable(simplifiedCommonTable, message);
    }

    /**
     * Wrapper to update the view of the hand.
     *
     * @param simplifiedHand the simplified representation of the hand.
     * @param message        to be displayed with the update
     */
    public void updateHand(SimplifiedHand simplifiedHand, String message) {
        this.simplifiedModel.setSimplifiedHand(simplifiedHand, message);
    }

    /**
     * Wrapper to update the view of the secret hand.
     *
     * @param simplifiedSecretHand the simplified representation of the secret hand.
     * @param message              to be displayed with the update
     */
    public void updateSecretHand(SimplifiedHand simplifiedSecretHand, String message) {
        this.simplifiedModel.setSimplifiedSecretHand(simplifiedSecretHand, message);
    }

    /**
     * Wrapper to update the view of the personal board.
     *
     * @param personalBoard the simplified representation of the personal board.
     * @param message       to be displayed with the update
     */
    public void updatePersonalBoard(SimplifiedPersonalBoard personalBoard, String message) {
        this.simplifiedModel.setPersonalBoard(personalBoard, message);
    }

    /**
     * Wrapper to update the view of the another personal board.
     *
     * @param otherPersonalBoard the simplified representation of the personal board.
     * @param message            to be displayed with the update
     */
    public void updateOtherPersonalBoard(SimplifiedPersonalBoard otherPersonalBoard, String message) {
        this.simplifiedModel.setOtherPersonalBoard(otherPersonalBoard, message);
    }

    /**
     * Wrapper to update the view of the player.
     *
     * @param simplifiedPlayer the simplified representation of the player.
     * @param message          to be displayed with the update
     */
    public void updatePlayer(SimplifiedPlayer simplifiedPlayer, String message) {
        this.simplifiedModel.setSimplifiedPlayer(simplifiedPlayer, message);
    }

    /**
     * Wrapper to update the view of the chat.
     *
     * @param simplifiedChat the simplified representation of the chat.
     * @param message        to be displayed with the update
     */
    public void updateChat(SimplifiedChat simplifiedChat, String message) {
        this.simplifiedModel.setSimplifiedChat(simplifiedChat, message);
    }

    /**
     * Wrapper to update the view of the game.
     *
     * @param simplifiedGame the simplified representation of the game.
     * @param message        to be displayed with the update
     */
    public void updateGame(SimplifiedGame simplifiedGame, String message) {
        this.simplifiedModel.setSimplifiedGame(simplifiedGame, message);
    }

    /**
     * Wrapper to reset timer
     */
    public void resetTimer() {
        this.mainClient.getPingManager().reset();
    }

    /**
     * Reports a message from the server (for example error reports)
     *
     * @param message message sent by the server
     */

    public void showMessage(String message) {
        this.simplifiedModel.getView().showMessage(message);
    }

    /**
     * Reports an error message from the server
     *
     * @param errorMessage message to print
     */
    public void showError(String errorMessage) {
        this.simplifiedModel.getView().showError(errorMessage);
    }

    /**
     * Set game unique identifier to recreate the game after the server goes down
     *
     * @param gameID unique ID, natural number
     */
    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    /**
     * Return game unique identifier
     *
     * @return natural number
     */
    public int getGameID() {
        return this.gameID;
    }

    /**
     * Ends client's process
     */
    public void killProcess() {
        this.mainClient.killProcesses();
    }

    /**
     * Closes pop up that shows the server is down
     */
    public void closeErrorPopup() {
        this.simplifiedModel.getView().closeErrorPopup();
    }

}