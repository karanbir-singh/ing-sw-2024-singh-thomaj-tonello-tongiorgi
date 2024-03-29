package it.polimi.ingsw.gc26;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.game.Game;
import it.polimi.ingsw.gc26.model.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainController {
    private Map<String, GameController> gamesControllers;
    //private Map<ID ,  GameController> //TODO controllare qua

    public MainController() {
        this.gamesControllers = new HashMap<>();
    }

    public void createGame(int numPlayers) throws Exception{
        if (numPlayers > 1 && numPlayers <= Game.MAX_NUM_PLAYERS) {
            String gameID = UUID.randomUUID().toString();
            gamesControllers.put(gameID, new GameController(new Game(numPlayers)));
        }
        // TODO gestire cosa fare in cui il numero di giocatori è negativo o maggiori di Game.MAX_NUM_PLAYERS
    }

    public void joinGame(String gameID, Player newPlayer) throws Exception {
        Game game = gamesControllers.get(gameID).getGame();
        if (game.getPlayers().size() < gamesControllers.get(gameID).getGame().getNumberOfPlayers()) {
            game.addPlayer(newPlayer);
        } else {
            // TODO gestire in altro modo questo ramo
        }
    }

    public ArrayList<String> getGamesIDs() {
        return new ArrayList<>(gamesControllers.keySet());
    }

    public GameController getGameController(String gameID) {
        return gamesControllers.get(gameID);
    }

    public void deleteGame(String gameID) {
        gamesControllers.remove(gameID);
    }

    public static void main(String[] args) throws Exception {
        MainController mainController = new MainController();

        // Crea partita, quindi il suo controller e i giocatori
        mainController.createGame(2);
        Player p1 = new Player(0, "guest1");
        Player p2 = new Player(1, "guest2");

        // Aggiungi i giocatori
        mainController.joinGame(mainController.getGamesIDs().getFirst(), p1);
        mainController.joinGame(mainController.getGamesIDs().getFirst(), p2);

        // Ottieni il controller
        GameController gameController = mainController.getGameController(mainController.getGamesIDs().getFirst());

        // imposta il giocatore corrente
        gameController.getGame().setCurrentPlayer(p1);

        // prendo la carta iniziale dal mazzo e la assegno al primo giocatore
//        gameController.getGame().getCommonTable().getInitialDeck().shuffleDeck();
        Card p1Starter = gameController.getGame().getCommonTable().getInitialDeck().removeCard();
//        gameController.getGame().getCurrentPlayer().setHand();
//        gameController.getGame().getCurrentPlayer().getHand().addCard(p1Starter);

        // prendo la carta iniziale dal mazzo e la assegno al seconda giocatore
//        gameController.getGame().getCommonTable().getInitialDeck().shuffleDeck();
        Card p2Starter = gameController.getGame().getCommonTable().getInitialDeck().removeCard();
//        gameController.getGame().getCurrentPlayer().setHand();
//        gameController.getGame().getCurrentPlayer().getHand().addCard(p2Starter);

        // Questa parte ora fa schifo
        // TODO la creazione della personalBoard è da cambiare
        gameController.getGame().getCommonTable().getMissionDeck().shuffleDeck();
        Card p1Secret = gameController.getGame().getCommonTable().getMissionDeck().removeCard();
        gameController.getGame().getCurrentPlayer().createPersonalBoard(p1Starter.getFront());
        gameController.getGame().getCurrentPlayer().getPersonalBoard().setSecretMission(p1Secret);

        gameController.getGame().getCommonTable().getMissionDeck().shuffleDeck();
        Card p2Secret = gameController.getGame().getCommonTable().getMissionDeck().removeCard();
        p2.createPersonalBoard(p2Starter.getBack());
        p2.getPersonalBoard().setSecretMission(p2Secret);

        // Assegna le prime carte ai giocatori
        gameController.getGame().getCommonTable().getResourceDeck().shuffleDeck();
        gameController.getGame().getCommonTable().getGoldDeck().shuffleDeck();
        // Giocatore 1
        p1.createHand();
        p1.getHand().addCard(gameController.getGame().getCommonTable().getResourceDeck().removeCard());
        p1.getHand().addCard(gameController.getGame().getCommonTable().getResourceDeck().removeCard());
        p1.getHand().addCard(gameController.getGame().getCommonTable().getGoldDeck().removeCard());
        // Giocatore 2
        p2.createHand();
        p2.getHand().addCard(gameController.getGame().getCommonTable().getResourceDeck().removeCard());
        p2.getHand().addCard(gameController.getGame().getCommonTable().getResourceDeck().removeCard());
        p2.getHand().addCard(gameController.getGame().getCommonTable().getGoldDeck().removeCard());

        // Qui prepara il common table
        gameController.getGame().getCommonTable().getResourceCards().add(
                gameController.getGame().getCommonTable().getResourceDeck().removeCard()
        );
        gameController.getGame().getCommonTable().getResourceCards().add(
                gameController.getGame().getCommonTable().getResourceDeck().removeCard()
        );
        gameController.getGame().getCommonTable().getGoldCards().add(
                gameController.getGame().getCommonTable().getGoldDeck().removeCard()
        );
        gameController.getGame().getCommonTable().getGoldCards().add(
                gameController.getGame().getCommonTable().getGoldDeck().removeCard()
        );

        // Ora testing
        Card p1FirstCard = gameController.getGame().getCurrentPlayer().getHand().getCards().getFirst();
        gameController.selectCardFromHand(p1FirstCard);
        gameController.turnSelectedCardSide();

        gameController.selectPositionOnBoard(1,1);
        gameController.playCardFromHand();

        // Pescaggio
        Card selected = gameController.getGame().getCommonTable().getResourceCards().getFirst();
        gameController.selectCardFromCommonTable(selected);
        gameController.drawSelectedCard();

        // Controllo se è avvenuto con successo
        System.out.println("SIIIII");
    }
}
