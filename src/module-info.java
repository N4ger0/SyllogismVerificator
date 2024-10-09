module univ.syllogismverificator {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens univ.syllogismverificator to javafx.fxml;
    exports univ.syllogismverificator;
    exports univ.syllogismverificator.controllers;
}