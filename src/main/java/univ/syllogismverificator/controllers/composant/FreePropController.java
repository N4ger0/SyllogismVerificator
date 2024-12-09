package univ.syllogismverificator.controllers.composant;

import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import univ.syllogismverificator.Traductor;
import univ.syllogismverificator.controllers.SolverController;
import univ.syllogismverificator.models.Proposition;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * This class is the controller of a proposition in free mode.
 * It also contains the information necessary to solve the syllogism
 */
public class FreePropController {
    public String currentSelected1 ;
    public String currentSelected2 ;
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

    /**
     * Getter for the text containing the number of the premise
     * @return String
     */
    public String getText(){
        return text.getText();
    }

    /**
     * Text to display "medium term" in the language chosen by the user
     */
    @FXML
    private Text freeMediumTermText ;

    /** MenuButton to select the class of the proposition*/
    @FXML
    private MenuButton freeQQL;

    /** MenuButton to select the first term of the premise*/
    @FXML
    private MenuButton freeTerme1;

    /**
     * Getter for the first MenuButton
     * @return MenuButton
     */
    public MenuButton getFreeTerme1() {
        return freeTerme1 ;
    }

    /** MenuButton to select the second term of the premise*/
    @FXML
    private MenuButton freeTerme2;

    /**
     * Getter for the second MenuButton
     * @return MenuButton
     */
    public MenuButton getFreeTerme2() {
        return freeTerme2 ;
    }

    /**
     * TextField where the user input the medium term of the proposition
     */
    @FXML
    private TextField freeTextFieldMedium;
    /**
     * VBox containing the TextFIel where the user input the medium term
     */
    @FXML
    private VBox freeMediumTerm;

    /**
     * Getter for the TextField containing the medium term
     * @return
     */
    public TextField getFreeTextFieldMedium() {
        return freeTextFieldMedium;
    }

    /**
     * Attribute that indicates wether the controller is the last proposition ie the conclusion
     */
    private static FreePropController lastFreeProp;
    static {
        lastFreeProp = null;
    }

    /** Integer used as a counter of proposition*/
    public static int TextCounter;
    static {
        TextCounter = 0;
    }

    /**
     * Initalizer for the FreePropController, set the text dynamically according to the select
     * language, set all the events and increments the counter of proposition
     */
    @FXML
    public void initialize() {
        freeTerme1.setText(traductor.get("terme") + "1");
        freeTerme2.setText(traductor.get("terme") + "2");
        loadMenuItemsFromJson();
        initText();
        updateText();
        initMenuItems();

        freeMediumTermText.setText(Traductor.get("moyen_terme"));

        lastFreeProp = this;
        TextCounter++;
    }

    /**
     * Method to load the JSON contained into the QQL selection MenuButton, automatically
     * called from intialize()
     * @throws RuntimeException if error parsing the JSON file
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
     * If this is the conclusion, hide the Proposition x, and display conclusion instead
     */
    private void initText(){
        freeMediumTerm.setVisible(false);
        text.setText("Conclusion");
    }

    /**
     * Method to set the text containing the number of the premise
     */
    public void updateText() {
        if (lastFreeProp != null){
            lastFreeProp.text.setText(Traductor.get("premisse") + TextCounter);
            if (TextCounter != 1){
                lastFreeProp.freeMediumTerm.setVisible(true);
            }
        }
    }

    /**
     * Build the items of the QQL MenuButton with the data loaded from the JSON
     */
    private void initMenuItems() {
        freeQQL.getItems().clear();
        for(Pair<String, ArrayList<String>> p : QQLList){
            Menu mySubMenu = new Menu(p.getKey()) ;
            for(String s : p.getValue()) {
                MenuItem tempItem = new MenuItem(s) ;
                mySubMenu.getItems().add(tempItem) ;
                tempItem.setOnAction(event -> {
                    freeQQL.setText(tempItem.getText()) ;
                    classe = mySubMenu.getText() ;
                });
            }

            freeQQL.getItems().add(mySubMenu);
        }
        for (MenuItem mi : freeTerme1.getItems()){
            mi.setOnAction(event -> freeTerme1.setText(mi.getText()));
        }
        for (MenuItem mi : freeTerme2.getItems()){
            mi.setOnAction(event -> freeTerme2.setText(mi.getText()));
        }
    }

    /**
     * Getter for the first term of the proposition
     * @return String the inputed term
     */
    public String getTerm1() {
        return freeTerme1.getText();
    }

    /**
     * Getter for the second term of the proposition
     * @return String the inputed term
     */
    public String getTerm2() {
        return freeTerme2.getText();
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
            if (freeQQL.getText().equals("QQL")) {
                msg += "Il faut selectionner une QQL pour la " + text.getText() + "\n";
            }
            if (freeTerme1.getText().equals("Terme 1")) {
                msg += "Le 1er terme de la " + text.getText() + " n'est pas definit\n";
            }
            if (freeTerme2.getText().equals("Terme 2")) {
                msg += "Le 2eme terme de la " + text.getText() + " n'est pas definit\n";
            }
            if (freeTerme1.getText().equals(freeTerme2.getText())) {
                msg += "Une proposition ne peux pas avoir 2 fois le même terme\n";
            }
            if (!msg.isEmpty()) {
                msg += "\n";
            }
        }
        else {
            if (freeQQL.getText().equals("QQL")) {
                msg += "You need to select a QQL for the " + text.getText() + "\n";
            }
            if (freeTerme1.getText().equals("Term 1")) {
                msg += "The first term of the " + text.getText() + " isn't defined\n";
            }
            if (freeTerme2.getText().equals("Term 2")) {
                msg += "The second term of the " + text.getText() + " isn't defined\n";
            }
            if (freeTerme1.getText().equals(freeTerme2.getText())) {
                msg += "A proposition can't have the same term twice\n";
            }
            if (!msg.isEmpty()) {
                msg += "\n";
            }
        }
        return msg;
    }

    /**
     * Build a proposition with the data of the FreeProposition
     * @return Proposition
     * @see Proposition
     */
    public Proposition getProposition() {
        String QQL = classe;

        boolean qtt = Objects.equals(QQL, "A") || Objects.equals(QQL, "E");
        boolean qual = Objects.equals(QQL, "A") || Objects.equals(QQL, "I");

        return new Proposition(freeTerme2.getText(), freeTerme1.getText(), qtt, qual);
    }

    /**
     * Event to put back a use in the hashmap if the label of a MenuButton
     * is changed and they were already one
     * @param counterForFreeProp the hashmap to update
     */
    public void setEvent(HashMap<String, Integer> counterForFreeProp, SolverController solver) {
        freeTerme1.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!Objects.equals(currentSelected1, newValue) && !(currentSelected1 == null)) {
                System.out.println("dans le event freeProp");
                counterForFreeProp.put(currentSelected1, counterForFreeProp.get(currentSelected1) + 1);
                solver.handleChangeFocusFreeMode();
                //counterForFreeProp.put(newValue, counterForFreeProp.get(newValue) - 1);
            }
            currentSelected1 = newValue;
        });

        freeTerme2.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!Objects.equals(currentSelected2, newValue) && !(currentSelected2 == null)) {
                counterForFreeProp.put(currentSelected2, counterForFreeProp.get(currentSelected2) + 1);
                //counterForFreeProp.put(newValue, counterForFreeProp.get(newValue) - 1);
            }
            currentSelected2 = newValue;

        });
    }

    public void reset() {
        lastFreeProp = null;
        TextCounter = 0;
    }
}