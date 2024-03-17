module it.polimi.ingsw.gc26 {
    requires javafx.controls;
    requires javafx.fxml;


    exports it.polimi.ingsw.gc26.model.card_side;
    opens it.polimi.ingsw.gc26.model.card_side to javafx.fxml;
    exports it.polimi.ingsw.gc26.model.side_ability;
    opens it.polimi.ingsw.gc26.model.side_ability to javafx.fxml;
    exports it.polimi.ingsw.gc26.model.card;
    opens it.polimi.ingsw.gc26.model.card to javafx.fxml;
    exports it.polimi.ingsw.gc26;
    opens it.polimi.ingsw.gc26 to javafx.fxml;
}