module it.polimi.ingsw.gc26 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;


    exports it.polimi.ingsw.gc26.model.card_side;
    opens it.polimi.ingsw.gc26.model.card_side to javafx.fxml;
    exports it.polimi.ingsw.gc26.model.card;
    opens it.polimi.ingsw.gc26.model.card to javafx.fxml;
    exports it.polimi.ingsw.gc26;
    opens it.polimi.ingsw.gc26 to javafx.fxml;
    exports it.polimi.ingsw.gc26.model.card_side.mission;
    opens it.polimi.ingsw.gc26.model.card_side.mission to javafx.fxml;
    exports it.polimi.ingsw.gc26.model.card_side.ability;
    opens it.polimi.ingsw.gc26.model.card_side.ability to javafx.fxml;
}