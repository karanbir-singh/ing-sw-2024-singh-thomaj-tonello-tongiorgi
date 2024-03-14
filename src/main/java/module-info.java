module it.polimi.ingsw.gc26 {
    requires javafx.controls;
    requires javafx.fxml;


    opens it.polimi.ingsw.gc26 to javafx.fxml;
    exports it.polimi.ingsw.gc26;
    exports it.polimi.ingsw.gc26.model;
    opens it.polimi.ingsw.gc26.model to javafx.fxml;
}