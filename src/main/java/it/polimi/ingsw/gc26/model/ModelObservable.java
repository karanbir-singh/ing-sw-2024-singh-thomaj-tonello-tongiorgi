package it.polimi.ingsw.gc26.model;

import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.model.game.Game;
import it.polimi.ingsw.gc26.model.game.GameState;
import it.polimi.ingsw.gc26.model.game.Message;
import it.polimi.ingsw.gc26.model.player.Pawn;
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

    public void notifyChat(Message msg) {
        for (VirtualView client : this.clients) {
            try {
                client.showChat(msg.toString());
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

    public void notifyError(String errorMsg, String clientID) {
        for (VirtualView client : this.clients) {
            try {
                client.showError(errorMsg, clientID);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notifyUpdateState(ClientState clientState, String clientID) { //TODO change clientState to string???
        for (VirtualView client : this.clients) {
            try {
                client.updateState(clientState); //TODO add clientID?
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notifyUpdateGameState(GameState gameState) {
        for(VirtualView client : this.clients) {
            try {
                client.updateGameState(gameState.toString());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notifyUpdateChosenPawn(Pawn pawn, String clientID) {
        for (VirtualView client : this.clients) {
            try {
                client.updateChosenPawn(pawn.toString(), clientID);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notifyUpdateSelectedMission(String clientID) {
        for (VirtualView client : this.clients) {
            try {
                client.updateSelectedMission(clientID);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notifyUpdateSelectedCardFromHand(String clientID) {
        for (VirtualView client : this.clients) {
            try {
                client.updateSelectedCardFromHand(clientID);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notifyUpdateSelectedSide(int cardIndex, String clientID) {
        for (VirtualView client : this.clients) {
            try {
                client.updateSelectedSide(String.valueOf(cardIndex), clientID);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notifyUpdateSelectedPositionOnBoard(int selectedX, int selectedY, String playerID, int success) {
        for (VirtualView client : this.clients) {
            try {
                client.updateSelectedPositionOnBoard(String.valueOf(selectedX),String.valueOf(selectedY) , playerID, String.valueOf(success));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notifyUpdatePlayedCardFromHand(String clientID, int success) {
        for (VirtualView client : this.clients) {
            try {
                client.updatePlayedCardFromHand(clientID, String.valueOf(success));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notifyUpdatePoints(String clientID, int points) {
        for (VirtualView client : this.clients) {
            try {
                client.updatePoints(clientID, String.valueOf(points));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notifyUpdateSelectedCardFromCommonTable(String clientID, int success) {
        for (VirtualView client : this.clients) {
            try {
                client.updateSelectedCardFromCommonTable(clientID, String.valueOf(success));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notifyShowCard(String playerID, String cardSerialization) {
        for (VirtualView client : this.clients) {
            try {
                client.showCard(playerID, cardSerialization);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notifyShowChat(String message) {
        for (VirtualView client : this.clients) {
            try {
                client.showChat(message);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notifyUpdateFirstPlayer(String nickname) {
        for (VirtualView client : this.clients) {
            try {
                client.updateFirstPlayer(nickname);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
