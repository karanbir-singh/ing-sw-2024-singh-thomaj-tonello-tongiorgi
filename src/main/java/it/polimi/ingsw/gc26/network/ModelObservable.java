package it.polimi.ingsw.gc26.network;

import it.polimi.ingsw.gc26.model.game.Chat;
import it.polimi.ingsw.gc26.model.player.Player;
import it.polimi.ingsw.gc26.view_model.*;
import javafx.util.Pair;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

    /**
 * This class contains methods used to notify the client about an update in the model.
 */
public class ModelObservable implements Serializable {

    /**
     * Map clientView - Client ID to be able to filter the updates using the client's ID.
     */
    private transient ArrayList<Pair<VirtualView, String>> clients;

    /**
     * Constructor that initialized the array of clients
     */
    public ModelObservable() {
        this.clients = new ArrayList<>();
    }

    /**
     * Returns the clients' virtual view and its ID
     * @return for each client, pair virtualView - clientID
     */
    public ArrayList<Pair<VirtualView, String>> getClients() {
        // Check if clients list exists
        if (this.clients == null) {
            return new ArrayList<>();
        }
        return this.clients;
    }

    /**
     * Adds an observer to the observable.
     *
     * @param view client's view
     * @param clientID string client unique identifier
     */
    public void addObserver(VirtualView view, String clientID) {
        // Check if clients list exists
        if (this.clients == null) {
            this.clients = new ArrayList<>();
        }

        synchronized (this.clients){
            for(Pair client : this.clients){
                if(client.getValue().equals(clientID)){
                    this.clients.remove(client);
                }
            }
            this.clients.add(new Pair<>(view, clientID));
        }
    }

    /**
     * Notifies the client about an update in the model's game
     *
     * @param simplifiedGame simplified game containing only the information needed by the client
     * @param message information to show during the update
     */
    public void notifyUpdateGame(SimplifiedGame simplifiedGame, String message) {
        for (Pair client : this.clients) {
            try {
                ((VirtualView) client.getKey()).updateGame(simplifiedGame, message);
            } catch (RemoteException e) {
                System.out.println("network problem");
            }
        }
    }

    /**
     * Notifies the client about an update in the model's game
     *
     * @param simplifiedGame simplified game containing only the information needed by the client
     * @param currentPlayer current player
     */
    public void notifyUpdateGame(SimplifiedGame simplifiedGame, Player currentPlayer) {
        for (Pair client : this.clients) {
            if (client.getValue().equals(currentPlayer.getID())) {
                try {
                    ((VirtualView) client.getKey()).updateGame(simplifiedGame, "It's you turn now!");
                } catch (RemoteException e) {
                    System.out.println("network problem");
                }
            } else {
                try {
                    ((VirtualView) client.getKey()).updateGame(simplifiedGame, "It's " + currentPlayer.getNickname() + " turn now!");
                } catch (RemoteException e) {
                    System.out.println("network problem");
                }
            }
        }
    }

    /**
     * Notifies the client about an update in the model's common table
     *
     * @param simplifiedCommonTable simplified common table containing only the information needed by the client
     * @param message information to show during the update
     */
    public void notifyUpdateCommonTable(SimplifiedCommonTable simplifiedCommonTable, String message) {
        for (Pair client : this.clients) {
            try {
                ((VirtualView) client.getKey()).updateCommonTable(simplifiedCommonTable, message);
            } catch (RemoteException e) {
                System.out.println("network problem");
            }
        }
    }

    /**
     * Notifies the client about an update in the model's common table
     *
     * @param simplifiedCommonTable simplified common table containing only the information needed by the client
     * @param message information to show during the update
     * @param clientID only receiver of update
     */
    public void notifyUpdateCommonTable(SimplifiedCommonTable simplifiedCommonTable, String message, String clientID) {
        for (Pair client : this.clients) {
            if (client.getValue().equals(clientID)) {
                try {
                    ((VirtualView) client.getKey()).updateCommonTable(simplifiedCommonTable, message);
                } catch (RemoteException e) {
                    System.out.println("network problem");
                }
                break;
            }
        }
    }

    /**
     * Notifies the client about an update in the model's hand
     *
     * @param simplifiedHand simplified hand containing only the information needed by the client
     * @param message information to show during the update
     * @param clientID client receiver of the update
     */
    public void notifyUpdateHand(SimplifiedHand simplifiedHand, String message, String clientID) {
        for (Pair client : this.clients) {
            if (client.getValue().equals(clientID)) {
                try {
                    ((VirtualView) client.getKey()).updateHand(simplifiedHand, message);
                } catch (RemoteException e) {
                    System.out.println("network problem");
                }
            }
        }
    }

    /**
     * Notifies the client about an update in the model's secret hand
     *
     * @param simplifiedSecretHand simplified secret hand containing only the information needed by the client
     * @param message information to show during the update
     * @param clientID client receiver of the update
     */
    public void notifyUpdateSecretHand(SimplifiedHand simplifiedSecretHand, String message, String clientID) {
        for (Pair client : this.clients) {
            if (client.getValue().equals(clientID)) {
                try {
                    ((VirtualView) client.getKey()).updateSecretHand(simplifiedSecretHand, message);
                } catch (RemoteException e) {
                    System.out.println("network problem");
                }
            }
        }
    }

    /**
     * Notifies the client about an update in the model's personal board
     *
     * @param personalBoard simplified personal board containing only the information needed by the client
     * @param message information to show during the update
     * @param clientID client receiver of the update
     */
    public void notifyUpdatePersonalBoard(SimplifiedPersonalBoard personalBoard, String message, String clientID) {
        for (Pair client : this.clients) {
            if (client.getValue().equals(clientID)) {
                try {
                    ((VirtualView) client.getKey()).updatePersonalBoard(personalBoard, message);
                } catch (RemoteException e) {
                    System.out.println("network problem");
                }
            } else {
                try {
                    ((VirtualView) client.getKey()).updateOtherPersonalBoard(personalBoard, message);
                } catch (RemoteException e) {
                    System.out.println("network problem");
                }
            }
        }
    }

    /**
     * Notifies the client about an update in the model's personal board of another player.
     *
     * @param otherPersonalBoard simplified personal board containing only the information needed by the client
     * @param message information to show during the update
     * @param clientID client receiver of the update
     */
    public void notifyUpdateOtherPersonalBoard(SimplifiedPersonalBoard otherPersonalBoard, String message, String clientID) {
        for (Pair client : this.clients) {
            if (client.getValue().equals(clientID)) {
                try {
                    ((VirtualView) client.getKey()).updateOtherPersonalBoard(otherPersonalBoard, message);
                } catch (RemoteException e) {
                    System.out.println("network problem");
                }
            }
        }
    }

    /**
     * Notifies the client about an update in the model's player.
     *
     * @param simplifiedPlayer simplified player containing only the information needed by the client
     * @param message information to show during the update
     * @param clientID client receiver of the update
     */
    public void notifyUpdatePlayer(SimplifiedPlayer simplifiedPlayer, String message, String clientID) {
        for (Pair client : this.clients) {
            if (client.getValue().equals(clientID)) {
                try {
                    ((VirtualView) client.getKey()).updatePlayer(simplifiedPlayer, message);
                } catch (RemoteException e) {
                    System.out.println("network problem");
                }
            }
        }
    }

    /**
     * Notifies the client about an update in the model's chat
     *
     * @param chat simplified chat containing only the information needed by the client
     * @param message information to show during the update
     */
    public void notifyUpdateChat(Chat chat, String message) {
        for (Pair client : this.clients) {
            if (chat.getMessages().getLast().getSender().getID().equals(client.getValue())) {
                // this is the client who sent the message
                try {
                    ((VirtualView) client.getKey()).updateChat(new SimplifiedChat(chat.filterMessages(client.getValue().toString())), "Message sent!");
                } catch (RemoteException e) {
                    System.out.println("network problem");
                }
            } else if (chat.getMessages().getLast().getReceiver() == null || chat.getMessages().getLast().getSender().getID().equals(client.getValue()) || chat.getMessages().getLast().getReceiver().getID().equals(client.getValue())) {
                try {
                    ((VirtualView) client.getKey()).updateChat(new SimplifiedChat(chat.filterMessages(client.getValue().toString())), message);
                } catch (RemoteException e) {
                    System.out.println("network problem");
                }
            }
        }
    }

    /**
     * Notifies the client about a message to show
     *
     * @param msg information to show during the update
     * @param clientID client receiver of the update
     */
    public void notifyMessage(String msg, String clientID) {
        for (Pair client : this.clients) {
            if (client.getValue().equals(clientID)) {
                try {
                    ((VirtualView) client.getKey()).showMessage(msg);
                } catch (RemoteException e) {
                    System.out.println("network problem");
                }
            }
        }
    }

    /**
     * Notifies the client about an error to show
     *
     * @param errorMsg error to show during the update
     * @param clientID client receiver of the update
     */
    public void notifyError(String errorMsg, String clientID) {
        for (Pair client : this.clients) {
            if (client.getValue().equals(clientID)) {
                try {
                    ((VirtualView) client.getKey()).showError(errorMsg);
                } catch (RemoteException e) {
                    System.out.println("network problem");
                }
            }
        }
    }

    /**
     * Notifies the client that it has to kill the game
     */
    public void notifyGameClosed() {
        if(this.clients == null){//when both clients shout down when server is down,
            // and when server goes up this.clients will remain null since nobody will reconnected
            this.clients = new ArrayList<>();
        }
        synchronized (this.clients){
            for (Pair client : this.clients) {
                try {
                    ((VirtualView) client.getKey()).killProcess();
                } catch (RemoteException e) {
                    System.out.println("network problem");
                }
            }
        }

    }
}
