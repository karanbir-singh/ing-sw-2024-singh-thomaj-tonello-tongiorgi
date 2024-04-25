package it.polimi.ingsw.gc26.model;

import it.polimi.ingsw.gc26.model.game.Message;
import it.polimi.ingsw.gc26.model.player.PersonalBoard;
import it.polimi.ingsw.gc26.model.player.Player;
import it.polimi.ingsw.gc26.network.VirtualView;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class ModelObservable {
    private ArrayList<VirtualView> clients;
    private static ModelObservable instance;

    private ModelObservable() {
        this.clients = new ArrayList<>();
    }

    public static ModelObservable getInstance() {
        if (instance == null) {
            instance = new ModelObservable();
        }
        return instance;
    }

    public void addObserver(VirtualView view) {
        this.clients.add(view);
    }

    public void removeObserver(VirtualView view) {
        this.clients.remove(view);
    }

    public void notifyChat(Message msg, String clientID) {
        for (VirtualView client : this.clients) {
            try {
                client.showMessage(msg.toString(), clientID);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notifyPersonalBoard(Player player, Player personalBoardOwner, PersonalBoard personalBoard) {
        for (VirtualView client : this.clients) {
            try {
                client.showPersonalBoard(player.getID(), personalBoardOwner.getNickname(), personalBoard.toString() );
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notifySelectedCardFromHand( String clientID) throws RemoteException {
        for (VirtualView client : this.clients) {
            client.updateSelectedCardFromHand(clientID);
        }
    }

    public void notifyMessage(String msg, String clientID) throws RemoteException {
        for (VirtualView client : this.clients) {
            client.showMessage(msg, clientID);
        }
    }




}
