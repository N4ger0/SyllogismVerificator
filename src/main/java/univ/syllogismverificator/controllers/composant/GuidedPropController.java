package univ.syllogismverificator.controllers.composant;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;

/**
 * Cette classe représente le controller d'une des proposition du mode guidé.
 * Elle contient les informations necessaire a la resolution du syllogisme.
 */
public class GuidedPropController {
    @FXML
    private Text text;

    /** Le Menu deroulant permettant de choisir la quantite et qualite de la proposition.*/
    @FXML
    private MenuButton guidedQQL;

    /** Le champ textuel representant le 1er terme de la proposition.*/
    @FXML
    private TextField guidedTerme1;

    /** Le Menu deroulant permettant de choisir le verbe de la proposition.*/
    @FXML
    private MenuButton verbe;

    /** Le champ textuel representant le 1er terme de la proposition.*/
    @FXML
    private TextField guidedTerme2;


    /** Entier servant de compteur de proposition.*/
    private static int TextCounter;

    static {
        TextCounter = 1;
    }

    @FXML
    public void initialize() {
        initText();
        initMenuItems();
        initTextFields();
    }

    private void initText(){
        text.setText("Prémisse " + TextCounter);
        TextCounter++;
    }

    private void initMenuItems() {
        for (MenuItem mi : guidedQQL.getItems()){
            mi.setOnAction(event -> guidedQQL.setText(mi.getText()));
        }
        for (MenuItem mi : verbe.getItems()){
            mi.setOnAction(event -> verbe.setText(mi.getText()));
        }
    }

    private void initTextFields(){
    }

    /**
     * Recupere les differentes parties de la proposition.
     * <br>
     * Les cles sont respectivement "QQL", "terme1", "verbe", "terme2"
     * @return une Map contenant la QQL, le 1er terme, le verbe et le 2eme terme dans cet ordre.
     */
    public Map<String, String> getProposition() {
        Map<String, String> proposition = new HashMap<>();
        proposition.put("QQL", guidedQQL.getText());
        proposition.put("terme1", guidedTerme1.getText());
        proposition.put("verbe", verbe.getText());
        proposition.put("terme2", guidedTerme2.getText());
        return proposition;
    }
}