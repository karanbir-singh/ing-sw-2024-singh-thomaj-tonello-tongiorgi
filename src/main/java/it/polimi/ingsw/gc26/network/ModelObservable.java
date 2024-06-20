package it.polimi.ingsw.gc26.network;

import it.polimi.ingsw.gc26.model.game.Chat;
import it.polimi.ingsw.gc26.view_model.*;
import javafx.util.Pair;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ModelObservable implements Serializable {
    private transient ArrayList<Pair<VirtualView, String>> clients;

    public ModelObservable() {
        this.clients = new ArrayList<>();
    }

    public ArrayList<Pair<VirtualView, String>> getClients() {
        // Check if clients list exists
        if (this.clients == null) {
            return new ArrayList<>();
        }
        return this.clients;
    }


    public void addObserver(VirtualView view, String clientID) {
        // Check if clients list exists
        if (this.clients == null) {
            this.clients = new ArrayList<>();
        }
        this.clients.add(new Pair<>(view, clientID));
    }


    public void removeObserver(VirtualView view) {
        this.clients.remove(view);
    }

    public void notifyUpdateGame(SimplifiedGame simplifiedGame, String message) {
        for (Pair client : this.clients) {
            try {
                ((VirtualView) client.getKey()).updateGame(simplifiedGame, message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
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
            } else {
                try {
                    ((VirtualView) client.getKey()).updateOtherPersonalBoard(personalBoard, message);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
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

    public void notifyUpdateChat(Chat chat, String message) {
        for (Pair client : this.clients) {
            if (chat.getMessages().getLast().getSender().getID().equals(client.getValue())) {
                // this is the client who sent the message
                try {
                    ((VirtualView) client.getKey()).updateChat(new SimplifiedChat(chat.filterMessages(client.getValue().toString())), "Message sent!");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else if (chat.getMessages().getLast().getReceiver() == null || chat.getMessages().getLast().getSender().getID().equals(client.getValue()) || chat.getMessages().getLast().getReceiver().getID().equals(client.getValue())) {
                try {
                    ((VirtualView) client.getKey()).updateChat(new SimplifiedChat(chat.filterMessages(client.getValue().toString())), message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }



        }
    }

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


    public void notifyGameClosed(String nickname) {
        for (Pair client : this.clients) {
            try {
                ((VirtualView) client.getKey()).killProcess(nickname);
            } catch (RemoteException e) {
                //client is down, no need to notify
            }
        }
    }
}
