package it.polimi.ingsw.gc26.model;

import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.model.game.Game;
import it.polimi.ingsw.gc26.model.game.GameState;
import it.polimi.ingsw.gc26.model.game.Message;
import it.polimi.ingsw.gc26.model.player.Pawn;
import it.polimi.ingsw.gc26.model.player.PersonalBoard;
import it.polimi.ingsw.gc26.model.player.Player;
import it.polimi.ingsw.gc26.network.VirtualView;
import javafx.util.Pair;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ModelObservable implements Serializable{
    private transient ArrayList<Pair<VirtualView, String>> clients;
    //private static ModelObservable instance;

    public ModelObservable() {
        this.clients = new ArrayList<>();
    }
    public ArrayList<Pair<VirtualView,String>> getClients(){
        if(this.clients == null){ //SERVE PERCHé QUANDO IL SERVER TORNA SU DOPO ESSERE
                                    //ANDATO IN DOWN, CLIENTS è NULL
            return new ArrayList<>();
        }
        return this.clients;
    }


    public void addObserver(VirtualView view, String clientID) {
        if(this.clients == null){//SERVE PERCHé QUANDO IL SERVER TORNA SU DOPO ESSERE
                                    //ANDATO IN DOWN, CLIENTS è NULL
            this.clients = new ArrayList<>();
        }
        this.clients.add(new Pair<>(view,clientID));
    }


    public void removeObserver(VirtualView view) {
        this.clients.remove(view);
    }

    public void notifyChat(Message msg) {
        for (Pair client : this.clients) {
            if (!msg.getSender().equals(client.getValue())) {
                try {
                    ((VirtualView) client.getKey()).showChat(msg.toJson());
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

    public void notifyPersonalBoard(Player player, Player personalBoardOwner, PersonalBoard personalBoard) {
        /*for (Pair client : this.clients) {
            if (client.getValue().equals(player.getID())) {
                try {
                    //((VirtualView) client.getKey()).upda;
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }*/
    }

    public void notifySelectedCardFromHand(String clientID) throws RemoteException {
        for (Pair client : this.clients) {
            if (client.getValue().equals(clientID)) {
                try {
                    ((VirtualView) client.getKey()).updateSelectedCardFromHand(clientID);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void notifyMessage(String msg, String clientID) {
        for (Pair client : this.clients) {
            if (client.getValue().equals(clientID)) {
                try {
                    ((VirtualView) client.getKey()).showMessage(msg,clientID);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void notifyError(String errorMsg, String clientID) {
        for (Pair client : this.clients) {
            if (client.getValue().equals(clientID)) {
                try {
                    ((VirtualView) client.getKey()).showMessage(errorMsg,clientID);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void notifyUpdateState(ClientState clientState, String clientID) { //TODO change clientState to string???
        for (Pair client : this.clients) {
            if (client.getValue().equals(clientID)) {
                try {
                    ((VirtualView) client.getKey()).updateState(clientState);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void notifyUpdateGameState(GameState gameState) {
        for (Pair client : this.clients) {
            try {
                ((VirtualView) client.getKey()).updateGameState(gameState.toString());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notifyUpdateChosenPawn(Pawn pawn, String clientID) {
        for (Pair client : this.clients) {
            if (client.getValue().equals(clientID)) {
                try {
                    ((VirtualView) client.getKey()).updateChosenPawn(pawn.getFontColor(),clientID);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void notifyUpdateSelectedMission(String clientID) {
        for (Pair client : this.clients) {
            if (client.getValue().equals(clientID)) {
                try {
                    ((VirtualView) client.getKey()).updateSelectedMission(clientID);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void notifyUpdateSelectedCardFromHand(String clientID) {
        for (Pair client : this.clients) {
            if (client.getValue().equals(clientID)) {
                try {
                    ((VirtualView) client.getKey()).updateSelectedCardFromHand(clientID);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void notifyUpdateSelectedSide(int cardIndex, String clientID) {
        for (Pair client : this.clients) {
            if (client.getValue().equals(clientID)) {
                try {
                    ((VirtualView) client.getKey()).updateSelectedSide(String.valueOf(cardIndex), clientID);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void notifyUpdateSelectedPositionOnBoard(int selectedX, int selectedY, String playerID, int success) {
        for (Pair client : this.clients) {
            if (client.getValue().equals(playerID)) {
                try {
                    ((VirtualView) client.getKey()).updateSelectedPositionOnBoard(String.valueOf(selectedX), String.valueOf(selectedY), playerID, String.valueOf(success));
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    public void notifyUpdatePlayedCardFromHand(String clientID, int success) {
        for (Pair client : this.clients) {
            if (client.getValue().equals(clientID)) {
                try {
                    ((VirtualView) client.getKey()).updatePlayedCardFromHand(clientID, String.valueOf(success));
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void notifyUpdatePoints(String clientID, int points) {
        for (Pair client : this.clients) {
            if (client.getValue().equals(clientID)) {
                try {
                    ((VirtualView) client.getKey()).updatePoints(clientID, String.valueOf(points));
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void notifyUpdateSelectedCardFromCommonTable(String clientID, int success) {
        for (Pair client : this.clients) {
            if (client.getValue().equals(clientID)) {
                try {
                    ((VirtualView) client.getKey()).updateSelectedCardFromCommonTable(clientID, String.valueOf(success));
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void notifyShowCard(String playerID, String cardSerialization) {
        for (Pair client : this.clients) {
            if (client.getValue().equals(playerID)) {
                try {
                    ((VirtualView) client.getKey()).showCard(playerID, cardSerialization);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void notifyShowChat(String message) {
        for (Pair client : this.clients) {
            try {
                ((VirtualView) client.getKey()).showChat(message);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notifyUpdateFirstPlayer(String nickname) {
        for (Pair client : this.clients) {
            try {
                ((VirtualView) client.getKey()).updateFirstPlayer(nickname);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
