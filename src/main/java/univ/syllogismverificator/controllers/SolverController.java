package univ.syllogismverificator.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
    private TabPane tabWindow;

    private Hashtable<String, String> QQLList = new Hashtable<>() ;

    @FXML
    private VBox guidedPropositions;
    private ArrayList<GuidedPropController> guidedPropControllers = new ArrayList<>();

    /** Le champ textuel permettant d'aider l'utilisateur.*/
    @FXML
    private Text tutorialText;

    @FXML
    private VBox freePropositions;
    private ArrayList<FreePropController> freePropControllers = new ArrayList<>();


    @FXML
    public void initialize() {
        initTexts();
        initPropositions();
    }

    private void initPropositions() {
        for (int i = 0; i < 3; i++) {  // Ajout des 3 propositions par default
            addGuidedProposition();
            addFreeProposition();
        }
    }

    private void initTexts() {
        String SyllogismeDef = "Un syllogisme est un raisonnement logique formé de trois propositions : deux prémisses et une conclusion. Chaque prémisse relie deux termes, et la conclusion en déduit une relation entre ces deux termes. Par exemple, dans le syllogisme classique :\n" +
                "\n" +
                " 1  - Tous les hommes sont mortels (prémisse majeure),\n" +
                " 2  - Socrate est un homme (prémisse mineure),\n" +
                "ccl - Donc, Socrate est mortel (conclusion).\n" +
                "\n" +
                "Le syllogisme repose sur des règles strictes de logique formelle pour que la conclusion soit valide.";

        tutorialText.setText(SyllogismeDef);

        tabWindow.setOnMouseClicked(event -> tutorialText.setText(SyllogismeDef));
    }

    public Text getTutorialText() {
        return tutorialText;
    }


    /**
     * Ajoute une proposition au mode guide.
     */
    private void addGuidedProposition() {
        // Charger l'HBox depuis le fichier FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/composant/GuidedProp.fxml"));
        HBox GP = null;
        try {
            GP = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Récupérer le contrôleur de l'HBox
        GuidedPropController GPC = loader.getController();

        // Donner au controller fils accès au père pour qu'il puisse changer le texte tutoriel
        GPC.setParentController(this);

        // Ajouter le contrôleur à la liste pour accéder aux méthodes plus tard
        guidedPropControllers.add(GPC);

        // Ajouter l'HBox à la VBox
        guidedPropositions.getChildren().add(GP);
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
        HBox FP = null;
        try {
            FP = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Récupérer le contrôleur de l'HBox
        FreePropController FPC = loader.getController();

        // Ajouter le contrôleur à la liste pour accéder aux méthodes plus tard
        freePropControllers.add(FPC);

        // Ajouter l'HBox à la VBox
        freePropositions.getChildren().add(FP);
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
