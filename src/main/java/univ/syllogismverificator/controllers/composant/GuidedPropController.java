package univ.syllogismverificator.controllers.composant;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;

public class guidedPropController {
    @FXML
    private Text Text;
    @FXML
    private MenuButton QQL;
    @FXML
    private MenuButton verbe;

    private static int TextCounter;

    static {
        TextCounter = 1;
    }

    @FXML
    public void initialize() {
        Text.setText("Prémisse " + TextCounter);
        TextCounter++;

        initMenuItems();
    }

    private void initMenuItems() {
        for (MenuItem mi : QQL.getItems()){
            mi.setOnAction(event -> QQL.setText(mi.getText()));
        }
        for (MenuItem mi : verbe.getItems()){
            mi.setOnAction(event -> verbe.setText(mi.getText()));
        }
    }
}