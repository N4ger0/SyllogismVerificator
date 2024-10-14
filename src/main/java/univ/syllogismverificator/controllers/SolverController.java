package univ.syllogismverificator.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import univ.syllogismverificator.controllers.composant.*;

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
    @FXML
    private VBox guidedPropositions;
    private ArrayList<GuidedPropController> guidedPropControllers = new ArrayList<>();

    @FXML
    private VBox freePropositions;
    private ArrayList<FreePropController> freePropControllers = new ArrayList<>();


    @FXML
    public void initialize() {
        initPropositions();
    }

    private void initPropositions() {
        for (int i = 0; i < 3; i++) {  // Ajout des 3 propositions par default
            addGuidedProposition();
            addFreeProposition();
        }
    }


    /**
     * Ajoute une proposition au mode guide.
     */
    private void addGuidedProposition() {
        // Charger l'HBox depuis le fichier FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/composant/GuidedProp.fxml"));
        HBox hbox = null;
        try {
            hbox = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Récupérer le contrôleur de l'HBox
        GuidedPropController hboxController = loader.getController();

        // Ajouter le contrôleur à la liste pour accéder aux méthodes plus tard
        guidedPropControllers.add(hboxController);

        // Ajouter l'HBox à la VBox
        guidedPropositions.getChildren().add(hbox);
    }

    /**
     * Recupere la liste des proposition.
     *
     * @return Une ArraList de Map representant les propositions du mode guide.
     */
    private ArrayList<Map<String, String>> getGuidedPropositions(){
        ArrayList<Map<String, String>> propositionsList = new ArrayList<>();
        for (GuidedPropController GPP: guidedPropControllers) {
            propositionsList.add(GPP.getProposition());
        }
        return propositionsList;
    }

    /**
     * Ajoute une proposition au mode libre.
     */
    private void addFreeProposition() {
        // Charger l'HBox depuis le fichier FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/composant/FreeProp.fxml"));
        HBox hbox = null;
        try {
            hbox = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Récupérer le contrôleur de l'HBox
        FreePropController hboxController = loader.getController();

        // Ajouter le contrôleur à la liste pour accéder aux méthodes plus tard
        freePropControllers.add(hboxController);

        // Ajouter l'HBox à la VBox
        freePropositions.getChildren().add(hbox);
    }

    /**
     * Recupere la liste des proposition.
     *
     * @return Une ArraList de Map representant les propositions du mode libre.
     */
    private ArrayList<Map<String, String>> getFreePropositions(){
        ArrayList<Map<String, String>> propositionsList = new ArrayList<>();
        for (FreePropController FPP: freePropControllers) {
            propositionsList.add(FPP.getProposition());
        }
        return propositionsList;
    }

}
