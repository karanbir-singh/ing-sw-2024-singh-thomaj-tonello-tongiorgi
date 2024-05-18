package it.polimi.ingsw.gc26.model;

import it.polimi.ingsw.gc26.model.player.PersonalBoard;
import it.polimi.ingsw.gc26.network.VirtualView;
import it.polimi.ingsw.gc26.view_model.SimplifiedCommonTable;
import it.polimi.ingsw.gc26.view_model.SimplifiedHand;
import it.polimi.ingsw.gc26.view_model.SimplifiedPersonalBoard;
import it.polimi.ingsw.gc26.view_model.SimplifiedPlayer;
import javafx.util.Pair;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class ModelObservable {
    private ArrayList<Pair<VirtualView, String>> clients;
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

    public void addObserver(VirtualView view, String clientID) {
        this.clients.add(new Pair(view, clientID));
    }

    public void removeObserver(VirtualView view) {
        this.clients.remove(view);
    }

    public void notifyUpdateCommonTable(SimplifiedCommonTable simplifiedCommonTable, String message) {
        for (Pair client : this.clients) {
            try {
                ((VirtualView) client.getKey()).updateCommonTable(simplifiedCommonTable, message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
    }

    public void notifyUpdateHand(SimplifiedHand simplifiedHand, String message, String clientID) {
        for (Pair client : this.clients) {
            if (client.getValue().equals(clientID)) {
                try {
                    ((VirtualView) client.getKey()).updateHand(simplifiedHand, message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void notifyUpdateSecretHand(SimplifiedHand simplifiedSecretHand, String message, String clientID) {
        for (Pair client : this.clients) {
            if (client.getValue().equals(clientID)) {
                try {
                    ((VirtualView) client.getKey()).updateSecretHand(simplifiedSecretHand, message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void notifyUpdatePersonalBoard(SimplifiedPersonalBoard personalBoard, String message, String clientID) {
        for (Pair client : this.clients) {
            if (client.getValue().equals(clientID)) {
                try {
                    ((VirtualView) client.getKey()).updatePersonalBoard(personalBoard, message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void notifyUpdateOtherPersonalBoard(SimplifiedPersonalBoard otherPersonalBoard, String message, String clientID) {
        for (Pair client : this.clients) {
            if (client.getValue().equals(clientID)) {
                try {
                    ((VirtualView) client.getKey()).updateOtherPersonalBoard(otherPersonalBoard, message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void notifyUpdatePlayer(SimplifiedPlayer simplifiedPlayer, String message, String clientID) {
        for (Pair client : this.clients) {
            if (client.getValue().equals(clientID)) {
                try {
                    ((VirtualView) client.getKey()).updatePlayer(simplifiedPlayer, message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    public void notifyUpdateChat(SimplifiedChat simplifiedChat, String message) {
//        for (Pair client : this.clients) {
//            try {
//                ((VirtualView) client.getKey()).updatePlayer(simplifiedPlayer, message);
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
//        }
//    }

//    public void notifyUpdateOptionsMenu(OptionsMenu optionsMenu, String message) {
//        for (Pair client : this.clients) {
//            if (client.getValue().equals(clientID)) {
//                try {
//                    ((VirtualView) client.getKey()).updatePlayer(simplifiedPlayer, message);
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    //    public void notifyChat(Message msg) {
//        for (Pair client : this.clients) {
//            if (!msg.getSender().equals(client.getValue())) {
//                try {
//                    ((VirtualView) client.getKey()).showChat(msg.toJson());
//                } catch (RemoteException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//
//        }
//    }
//
//    public void notifyPersonalBoard(Player player, Player personalBoardOwner, PersonalBoard personalBoard) {
//        for (Pair client : this.clients) {
//            if (client.getValue().equals(player.getID())) {
//                try {
//                    ((VirtualView) client.getKey()).showPersonalBoard(player.getID(), personalBoardOwner.getNickname(), personalBoard.toString());
//                } catch (RemoteException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
//    }
//
//    public void notifySelectedCardFromHand(String clientID) throws RemoteException {
//        for (Pair client : this.clients) {
//            if (client.getValue().equals(clientID)) {
//                try {
//                    ((VirtualView) client.getKey()).updateSelectedCardFromHand(clientID);
//                } catch (RemoteException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
//    }
//
    public void notifyMessage(String msg, String clientID) {
        for (Pair client : this.clients) {
            if (client.getValue().equals(clientID)) {
                try {
                    ((VirtualView) client.getKey()).showMessage(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void notifyMessage(String msg) {
        for (Pair client : this.clients) {
            try {
                ((VirtualView) client.getKey()).showMessage(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
    }

    public void notifyError(String errorMsg, String clientID) {
        for (Pair client : this.clients) {
            if (client.getValue().equals(clientID)) {
                try {
                    ((VirtualView) client.getKey()).showError(errorMsg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void notifyError(String errorMsg) {
        for (Pair client : this.clients) {
            try {
                ((VirtualView) client.getKey()).showError(errorMsg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
