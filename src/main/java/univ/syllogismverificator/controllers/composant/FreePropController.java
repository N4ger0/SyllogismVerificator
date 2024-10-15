package univ.syllogismverificator.controllers.composant;

import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Cette classe représente le controller d'une des proposition du mode libre.
 * Elle contient les informations necessaire a la resolution du syllogisme.
 */
public class FreePropController {
    private ArrayList<Pair<String, ArrayList<String>>> QQLList = new ArrayList<>() ;
    private String classe ;

    public String getClasse() {
        return classe ;
    }
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
        loadMenuItemsFromJson();
        initText();
        initMenuItems();
    }

    private void loadMenuItemsFromJson() {
        try {
            Object o = new JSONParser().parse(new FileReader("src/main/resources/data/quanqual.json"));
            JSONArray j = (JSONArray) o;
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

    private void initText(){
        text.setText("Prémisse " + TextCounter);
        TextCounter++;
    }


    private void initMenuItems() {
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