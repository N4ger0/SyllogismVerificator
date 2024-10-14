package univ.syllogismverificator.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import univ.syllogismverificator.controllers.composant.GuidedPropController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class SolverController {
    private Hashtable<String, String> QQLList = new Hashtable<>() ;
    @FXML
    private VBox propositions;

    private ArrayList<GuidedPropController> guidedPropControllers = new ArrayList<>();

    @FXML
    private MenuButton QQLFM1;
    @FXML
    private MenuButton QQLFM2;
    @FXML
    private MenuButton QQLFM3;


    @FXML
    public void initialize() throws IOException {
        initMenuItems();
        loadMenuItemsFromJson();
        initPropositions();
    }

    private void initMenuItems() {
        for (MenuItem mi : QQLFM1.getItems()){
            mi.setOnAction(event -> QQLFM1.setText(mi.getText()));
        }
        for (MenuItem mi : QQLFM2.getItems()){
            mi.setOnAction(event -> QQLFM2.setText(mi.getText()));
        }
        for (MenuItem mi : QQLFM3.getItems()){
            mi.setOnAction(event -> QQLFM3.setText(mi.getText()));
        }
    }

    private void initPropositions() throws IOException {
        for (int i = 0; i < 3; i++) {  // Ajout des 3 propositions par default
            addGuidedProposition();
        }
    }


    /**
     * Ajoute une proposition au mode guide.
     */
    private void addGuidedProposition() throws IOException {
        // Charger l'HBox depuis le fichier FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/composant/GuidedProp.fxml"));
        HBox hbox = loader.load();

        // Récupérer le contrôleur de l'HBox
        GuidedPropController hboxController = loader.getController();

        // Ajouter le contrôleur à la liste pour accéder aux méthodes plus tard
        guidedPropControllers.add(hboxController);

        // Ajouter l'HBox à la VBox
        propositions.getChildren().add(hbox);
    }

    /**
     * Recupere la liste des proposition.
     *
     * @return Une ArraList de Map representant les propositions du mode guide.
     */
    private ArrayList<Map<String, String>> getPropositions(){
        ArrayList<Map<String, String>> propositionsList = new ArrayList<Map<String, String>>();
        for (GuidedPropController GPP: guidedPropControllers) {
            propositionsList.add(GPP.getProposition());
        }
        return propositionsList;
    }

    private void loadMenuItemsFromJson() {
        try {
            Object o = new JSONParser().parse(new FileReader("src/main/resources/data/quanqual.json"));
            JSONArray j = (JSONArray) o;
            for (Object object : j) {
                JSONObject myObj = (JSONObject) object;
                QQLList.put((String) myObj.get("key"), (String) myObj.get("value"));
            }
        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
