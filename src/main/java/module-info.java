module univ.syllogismverificator {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    requires json.simple;
    requires com.gluonhq.charm.glisten;

    opens univ.syllogismverificator to javafx.fxml;
    exports univ.syllogismverificator;
    exports univ.syllogismverificator.controllers;
    exports univ.syllogismverificator.controllers.composant;
    exports univ.syllogismverificator.models;

    opens univ.syllogismverificator.controllers;
    opens univ.syllogismverificator.controllers.composant;
    exports univ.syllogismverificator.models.rules;
    opens univ.syllogismverificator.models to javafx.fxml;
}