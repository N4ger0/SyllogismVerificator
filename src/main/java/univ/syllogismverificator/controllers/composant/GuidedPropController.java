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
 * This class is the controller of a proposition in guided mode.
 * It also contains the information necessary to solve the syllogism
 */
public class GuidedPropController {
    /**
     * List containing the loaded JSON of the quantities and qualities
     */
    private ArrayList<Pair<String, ArrayList<String>>> QQLList = new ArrayList<>() ;
    /**
     * Attribute to remember what is the form of the selected quantifier
     */
    private String classe ;
    /**
     * Instance of the Traductor class to translate the text of the proposition
     * @see Traductor
     */
    private Traductor traductor = new Traductor() ;
    /**
     * Getter for the class attribute
     * @return String the letter corresponding to the form of the quantifier A, E, O or I
     */
    public String getClasse() {
        return classe ;
    }
    /** Text containing the number of the premise*/
    @FXML
    private Text text;

    /** MenuButton to select the class of the proposition*/
    @FXML
    private MenuButton guidedQQL;

    /** AutocompleteTextField representing the first term of the proposition.
     * @see AutocompleteTextFieldController*/
    @FXML
    private AutocompleteTextFieldController guidedTerme1;

    /** MenuButton containing some verb to use in a proposition*/
    @FXML
    private MenuButton verbe;

    /** AutocompleteTextField representing the second term of the proposition.
     * @see AutocompleteTextFieldController*/
    @FXML
    private AutocompleteTextFieldController guidedTerme2;

    /**
     * MenuItem contained in the verbe MenuButton
     */
    @FXML
    private MenuItem sont ;
    /**
     * MenuItem contained in the verbe MenuButton
     */
    @FXML
    private MenuItem est ;
    /**
     * MenuItem contained in the verbe MenuButton
     */
    @FXML
    private MenuItem ont ;
    /**
     * MenuItem contained in the verbe MenuButton
     */
    @FXML
    private MenuItem a ;


    /** Instance of the SolverController who contains the GuidedPropController*/
    private SolverController parentController;

    /** Integer used for counting the number of premise.*/
    public static int TextCounter;
    static {
        TextCounter = 1;
    }

    /**
     * Initalizer method that inits the text in the selected language and set all the events
     */
    @FXML
    public void initialize() {
        loadMenuItemsFromJson();
        initText();
        initMenuItems();
        initTextFields();
    }

    /**
     * Set the text in the correct language. Called by initialize()
     */
    private void initText(){
        if (TextCounter == 3) {
            text.setText("Conclusion");
        }
        else {
            text.setText(traductor.get("premisse") + TextCounter);
        }
        verbe.setText(traductor.get("verbe"));
        sont.setText(traductor.get("sont"));
        est.setText(traductor.get("est"));
        ont.setText(traductor.get("ont"));
        a.setText(traductor.get("a"));
        TextCounter++;
    }

    /**
     * Load the quantifier and qualities list from the JSON file
     * @throws RuntimeException if error while parsing the JSON
     */
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

    /**
     * Populate the Menu of the quantifier and qualities MenuButon with the data loaded from
     * the JSON
     */
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

    /**
     * Set the event for the display of the tutorial text during the input of a
     * proposition.
     */
    private void initTutorialText() {
        Text tutorialText = parentController.getTutorialText();

        guidedQQL.setOnMouseClicked(event -> tutorialText.setText(Traductor.get("qql_def")));
        guidedTerme1.setOnMouseClicked(event -> tutorialText.setText(Traductor.get("term_def")));

        verbe.setOnMouseClicked(event -> tutorialText.setText(Traductor.get("verb_def")));

        guidedTerme2.setOnMouseClicked(event -> tutorialText.setText(Traductor.get("term_def")));
    }

    /**
     * Set the prompt text of the TextFields in the language selected by the user
     */
    private void initTextFields(){
        guidedTerme1.setPromptText(Traductor.get("terme")+ "1");
        guidedTerme2.setPromptText(Traductor.get("terme")+ "2");
    }

    /**
     * Method to save the parent controller of the propositionController
     * @param parent the parent SolverController
     * @see SolverController
     */
    public void setParentController(SolverController parent) {
        this.parentController = parent;
        initTutorialText();
    }

    /**
     * Getter for the text of the first term
     * @return String
     */
    public String getTerm1() {
        return guidedTerme1.getText();
    }

    /**
     * Getter for the text of the second term
     * @return String
     */
    public String getTerm2() {
        return guidedTerme2.getText();
    }

    /**
     * Getter for the TextField in which the first term is entered
     * @return AutocompleteTextFieldController
     * @see AutocompleteTextFieldController
     */
    public AutocompleteTextFieldController getGuidedTerme1() {
        return guidedTerme1 ;
    }
    /**
     * Getter for the TextField in which the second term is entered
     * @return AutocompleteTextFieldController
     * @see AutocompleteTextFieldController
     */
    public AutocompleteTextFieldController getGuidedTerme2() {
        return guidedTerme2 ;
    }

    /**
     * Check the validity of the inputed proposition
     * <br>
     * Check if a quantifier had been selected and if the two terms were inputed.
     * @return String containing an error message if the proposition isn't correct
     */
    public String isValid(){
        String msg = "";
        if (Traductor.getLang().equals("fr")) {
            if (guidedQQL.getText().equals("QQL")) {
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
        }
        else {
            if (guidedQQL.getText().equals("QQL")) {
                msg += "You need to select a QQL for the " + text.getText() + "\n";
            }
            if (guidedTerme1.getText().equals("Term 1")) {
                msg += "The first term of the " + text.getText() + " isn't defined\n";
            }
            if (guidedTerme2.getText().equals("Term 2")) {
                msg += "The second term of the " + text.getText() + " isn't defined\n";
            }
            if (guidedTerme1.getText().equals(guidedTerme2.getText())) {
                msg += "A proposition can't have the same term twice\n";
            }
            if (!msg.isEmpty()) {
                msg += "\n";
            }
        }
        return msg;
    }

    /**
     * Build a proposition with the data of the GuidedProposition
     * @return Proposition
     * @see Proposition
     */
    public Proposition getProposition() {
        String QQL = classe;

        boolean qtt = Objects.equals(QQL, "A") || Objects.equals(QQL, "E");
        boolean qual = Objects.equals(QQL, "A") || Objects.equals(QQL, "I");

        return new Proposition(guidedTerme2.getText(), guidedTerme1.getText(), qtt, qual);
    }

    public void reset() {
        TextCounter = 1;
    }
}