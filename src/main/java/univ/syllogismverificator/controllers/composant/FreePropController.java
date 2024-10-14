package univ.syllogismverificator.controllers.composant;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;

/**
 * Cette classe représente le controller d'une des proposition du mode libre.
 * Elle contient les informations necessaire a la resolution du syllogisme.
 */
public class FreePropController {
    /** Le texte indiquant le numero de la premisse.*/
    @FXML
    private Text text;

    /** Le Menu deroulant permettant de choisir la quantite et qualite de la proposition.*/
    @FXML
    private MenuButton freeQQL;

    /** Le Menu deroulant permettant de choisir le 1er terme de la proposition.*/
    @FXML
    private MenuButton freeTerme1;

    /** Le Menu deroulant permettant de choisir le 2eme terme de la proposition.*/
    @FXML
    private MenuButton freeTerme2;


    /** Entier servant de compteur de proposition.*/
    private static int TextCounter;

    static {
        TextCounter = 1;
    }

    @FXML
    public void initialize() {
        initText();
        initMenuItems();
    }

    private void initText(){
        text.setText("Prémisse " + TextCounter);
        TextCounter++;
    }


    private void initMenuItems() {
        for (MenuItem mi : freeQQL.getItems()){
            mi.setOnAction(event -> freeQQL.setText(mi.getText()));
        }
        for (MenuItem mi : freeTerme1.getItems()){
            mi.setOnAction(event -> freeTerme1.setText(mi.getText()));
        }
        for (MenuItem mi : freeTerme2.getItems()){
            mi.setOnAction(event -> freeTerme2.setText(mi.getText()));
        }
    }

    /**
     * Recupere les differentes parties de la proposition.
     * <br>
     * Les cles sont respectivement "QQL", "terme1", "terme2"
     * @return une Map contenant la QQL, le 1er terme et le 2eme terme dans cet ordre.
     */
    public Map<String, String> getProposition() {
        Map<String, String> proposition = new HashMap<>();
        proposition.put("QQL", freeQQL.getText());
        proposition.put("terme1", freeTerme1.getText());
        proposition.put("terme2", freeTerme2.getText());
        return proposition;
    }
}