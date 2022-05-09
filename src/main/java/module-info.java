module isep.ricochetrobot {
    requires javafx.controls;
    requires javafx.fxml;


    opens isep.ricochetrobot to javafx.fxml;
    exports isep.ricochetrobot;
}