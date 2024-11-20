package univ.syllogismverificator.controllers.composant;

import com.gluonhq.charm.glisten.control.AutoCompleteTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import univ.syllogismverificator.Traductor;
import univ.syllogismverificator.controllers.SolverController;
import javafx.util.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import univ.syllogismverificator.models.Proposition;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Cette classe représente le controller d'une des proposition du mode guidé.
 * Elle contient les informations necessaire a la resolution du syllogisme.
 */
public class GuidedPropController {
    private ArrayList<Pair<String, ArrayList<String>>> QQLList = new ArrayList<>() ;
    private String classe ;

    private Traductor traductor = new Traductor() ;

    public String getClasse() {
        return classe ;
    }
    /** Le texte indiquant le numero de la premisse.*/
    @FXML
    private Text text;

    /** Le Menu deroulant permettant de choisir la quantite et qualite de la proposition.*/
    @FXML
    private MenuButton guidedQQL;

    /** Le champ textuel representant le 1er terme de la proposition.*/
    @FXML
    private AutocompleteTextFieldController guidedTerme1;

    /** Le Menu deroulant permettant de choisir le verbe de la proposition.*/
    @FXML
    private MenuButton verbe;

    /** Le champ textuel representant le 1er terme de la proposition.*/
    @FXML
    private AutocompleteTextFieldController guidedTerme2;

    @FXML
    private MenuItem sont ;
    @FXML
    private MenuItem est ;
    @FXML
    private MenuItem ont ;
    @FXML
    private MenuItem a ;


    /** Entier servant de compteur de proposition.*/
    private SolverController parentController;

    /** Entier servant de compteur de proposition.*/
    public static int TextCounter;

    static {
        TextCounter = 1;
    }

    @FXML
    public void initialize() {
        loadMenuItemsFromJson();
        initText();
        initMenuItems();
        initTextFields();
    }

    private void initText(){
        text.setText(traductor.get("premisse") + TextCounter);
        verbe.setText(traductor.get("verbe"));
        sont.setText(traductor.get("sont"));
        est.setText(traductor.get("est"));
        ont.setText(traductor.get("ont"));
        a.setText(traductor.get("a"));
        TextCounter++;
    }

    private void loadMenuItemsFromJson() {
        try {
            QQLList.clear();
            JSONObject o = (JSONObject) new JSONParser().parse(new FileReader("src/main/resources/data/quanqual.json"));
            JSONArray j = (JSONArray) o.get(Traductor.getLang());
            for (Object object : j) {
                JSONObject myObj = (JSONObject) object;
                String tempClasse = (String) myObj.get("value");
                JSONArray j2 = (JSONArray) myObj.get("array");
                ArrayList<String> tempList = new ArrayList<>() ;
                for(Object object2 : j2) {
                    JSONObject myObj2 = (JSONObject) object2 ;
                    tempList.add((String) myObj2.get("key")) ;
                }
                QQLList.add(new Pair<>((String) myObj.get("value"), tempList));
            }
        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initMenuItems() {
        guidedQQL.getItems().clear();
        for(Pair<String, ArrayList<String>> p : QQLList){
            Menu mySubMenu = new Menu(p.getKey()) ;
            for(String s : p.getValue()) {
                MenuItem tempItem = new MenuItem(s) ;
                mySubMenu.getItems().add(tempItem) ;
                tempItem.setOnAction(event -> {
                    guidedQQL.setText(tempItem.getText()) ;
                    classe = mySubMenu.getText() ;
                });
            }

            guidedQQL.getItems().add(mySubMenu);
        }
        for (MenuItem mi : verbe.getItems()){
            mi.setOnAction(event -> verbe.setText(mi.getText()));
        }
    }

    private void initTutorialText() {
        Text tutorialText = parentController.getTutorialText();

        guidedQQL.setOnMouseClicked(event -> tutorialText.setText(Traductor.get("qql_def")));
        guidedTerme1.setOnMouseClicked(event -> tutorialText.setText(Traductor.get("term_def")));

        verbe.setOnMouseClicked(event -> tutorialText.setText(Traductor.get("verb_def")));

        guidedTerme2.setOnMouseClicked(event -> tutorialText.setText(Traductor.get("term_def")));
    }

    private void initTextFields(){
        guidedTerme1.setPromptText(Traductor.get("terme")+ "1");
        guidedTerme2.setPromptText(Traductor.get("terme")+ "2");
    }

    /**
     * Stocke le controller du parent.
     */
    public void setParentController(SolverController parent) {
        this.parentController = parent;
        initTutorialText();
    }

    public String getTerm1() {
        return guidedTerme1.getText();
    }

    public String getTerm2() {
        return guidedTerme2.getText();
    }

    public AutocompleteTextFieldController getGuidedTerme1() {
        return guidedTerme1 ;
    }

    public AutocompleteTextFieldController getGuidedTerme2() {
        return guidedTerme2 ;
    }

    /**
     * Verifie la validite de la proposition.
     * <br>
     * Il faut verifier que la QQL est selectionne et que les termes sont utilises 2 fois chacun"
     * @return un Boolean exprimant la valisite de la proposition.
     */
    public String isValid(){
        String msg = "";
        if (guidedQQL.getText().equals("QQL")){
            msg += "Il faut selectionner une QQL pour la " + text.getText() + "\n";
        }
        if (guidedTerme1.getText().isEmpty()) {
            msg += "Le 1er terme de la " + text.getText() + " n'est pas definit\n";
        }
        if (guidedTerme2.getText().isEmpty()) {
            msg += "Le 2eme terme de la " + text.getText() + " n'est pas definit\n";
        }
        if (guidedTerme1.getText().equals(guidedTerme2.getText())) {
            msg += "Une proposition ne peux pas avoir 2 fois le même terme\n";
        }
        if (!msg.isEmpty()) {
            msg += "\n";
        }
        return msg;
    }

    /**
     * Recupere les differentes parties de la proposition.
     * <br>
     * Les cles sont respectivement "QQL", "terme1", "terme2"
     * @return une Map contenant la QQL, le 1er terme et le 2eme terme.
     */
    public Proposition getProposition() {
        String QQL = classe;

        boolean qtt = Objects.equals(QQL, "A") || Objects.equals(QQL, "E");
        boolean qual = Objects.equals(QQL, "A") || Objects.equals(QQL, "I");

        return new Proposition(guidedTerme2.getText(), guidedTerme1.getText(), qtt, qual);
    }
}