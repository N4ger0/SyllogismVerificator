package univ.syllogismverificator.controllers;

import javafx.collections.FXCollections;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Hashtable;
import java.util.*;

import javafx.util.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.io.IOException;
import java.util.ArrayList;

import univ.syllogismverificator.Solver;
import univ.syllogismverificator.Traductor;
import univ.syllogismverificator.controllers.composant.*;
import univ.syllogismverificator.models.Polysyllogism;
import univ.syllogismverificator.models.Proposition;
import univ.syllogismverificator.models.SyllogismResult;
import univ.syllogismverificator.models.rules.RuleResult;

public class SolverController {
    Traductor traductor = new Traductor() ;
    public Button schemaAdd;
    @FXML
    private TabPane tabWindow;

    @FXML
    private Tab tab_guided ;

    @FXML
    private Tab tab_free ;

    @FXML
    private TitledPane titled_pane ;

    @FXML
    private VBox guidedPropositions;
    @FXML
    private Text text_sujet ;

    @FXML
    private Text text_middle ;
    private ArrayList<GuidedPropController> guidedPropControllers = new ArrayList<>();

    /** Le champ textuel permettant d'aider l'utilisateur.*/
    @FXML
    private Text tutorialText;

    @FXML
    private Button guidedSolve;
    @FXML
    private CheckBox guidedHE;
    @FXML
    private Text guidedCCL;

    @FXML
    private VBox freePropositions;
    private ArrayList<FreePropController> freePropControllers = new ArrayList<>();

    @FXML
    private TextField textFieldPredicat ;

    @FXML
    private TextField textFieldMoyen ;

    @FXML
    private TextField textFieldSujet ;

    private void setEventOnTextFields() {
        textFieldPredicat.setOnKeyTyped((event) -> {
            for(FreePropController control : freePropControllers) {
                control.getFreeTerme1().getItems().get(0).setText(textFieldPredicat.getText());
                control.getFreeTerme1().getItems().get(0).setVisible(!(textFieldPredicat.getText().equals("")));
                control.getFreeTerme2().getItems().get(0).setText(textFieldPredicat.getText());
                control.getFreeTerme2().getItems().get(0).setVisible(!(textFieldPredicat.getText().equals("")));
            }
        });
        textFieldMoyen.setOnKeyTyped((event) -> {
            for(FreePropController control : freePropControllers) {
                control.getFreeTerme1().getItems().get(1).setText(textFieldMoyen.getText());
                control.getFreeTerme1().getItems().get(1).setVisible(!(textFieldMoyen.getText().equals("")));
                control.getFreeTerme2().getItems().get(1).setText(textFieldMoyen.getText());
                control.getFreeTerme2().getItems().get(1).setVisible(!(textFieldMoyen.getText().equals("")));
            }
        });
        textFieldSujet.setOnKeyTyped((event) -> {
            for(FreePropController control : freePropControllers) {
                control.getFreeTerme1().getItems().get(2).setText(textFieldSujet.getText());
                control.getFreeTerme1().getItems().get(2).setVisible(!(textFieldSujet.getText().equals("")));
                control.getFreeTerme2().getItems().get(2).setText(textFieldSujet.getText());
                control.getFreeTerme2().getItems().get(2).setVisible(!(textFieldSujet.getText().equals("")));
            }
        });
    }
    @FXML
    private Button freeSolve;
    @FXML
    private CheckBox freeHE;
    @FXML
    private Text freeCCL;

    private Solver solver;


    @FXML
    public void initialize() {
        initTexts();
        System.out.println("INIT");
        titled_pane.setText(traductor.get("title"));
        tab_guided.setText(traductor.get("guided_mode"));
        tab_free.setText(traductor.get("free_mode"));
        guidedSolve.setText(traductor.get("solve"));
        guidedHE.setText(traductor.get("exist_hypothese"));
        text_sujet.setText(traductor.get("subject"));
        text_middle.setText(traductor.get("moyen_terme"));

        initPropositions();
        initButtons();
        solver = new Solver();
        setEventOnTextFields();
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

        tutorialText.setText(traductor.get("syllogism_def"));

        tabWindow.setOnMouseClicked(event -> tutorialText.setText(traductor.get("syllogism_def")));
    }

    private void initButtons() {
        guidedSolve.setOnAction(event -> guidedSolve());
        freeSolve.setOnAction(event -> freeSolve());
        schemaAdd.setOnAction(event -> askSchema());
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
    private Polysyllogism getGuidedPropositions(){
        ArrayList<Proposition> propositionsList = new ArrayList<>();
        for (GuidedPropController GPP: guidedPropControllers) {
            propositionsList.add(GPP.getProposition());
        }
        return new Polysyllogism(propositionsList);
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
    private Polysyllogism getFreePropositions(){
        ArrayList<Proposition> propositionsList = new ArrayList<>();
        for (FreePropController FPP: freePropControllers) {
            propositionsList.add(FPP.getProposition());
        }
        return new Polysyllogism(propositionsList);
    }

    private void guidedSolve() {
        Polysyllogism ps = getGuidedPropositions();
        SyllogismResult res = solver.solve(ps);

        if (res.isValid()){
            guidedCCL.setText("Syllogisme valide!");
        }
        else {
            String ccl = "";
            for (RuleResult rr: res.getResults()){
                ccl += rr.toString() + "\n";
            }
            guidedCCL.setText(ccl);
        }
    }

    private void freeSolve(){
        Polysyllogism ps = getFreePropositions();
        SyllogismResult res = solver.solve(ps);

        if (res.isValid()){
            freeCCL.setText("Syllogisme valide!");
        }
        else {
            String ccl = "";
            for (RuleResult rr: res.getResults()){
                ccl += rr.toString() + "\n";
            }
            freeCCL.setText(ccl);
        }
    }

    public void askSchema() {
        // open a dialogue to ask
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        ComboBox<String> comboBox = new ComboBox<>(FXCollections.observableArrayList("A", "E", "I", "O"));
        TextField text = new TextField();
        VBox content = new VBox(10, comboBox, text);
        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.setResultConverter(btn -> btn == ButtonType.OK ? new Pair<>(comboBox.getValue(), text.getText()) : null);
        dialog.showAndWait().ifPresent(p -> saveOnJson(p.getKey(), p.getValue()));
    }

    private void saveOnJson(String name, String say){
        try {
            Object o = new JSONParser().parse(new FileReader("src/main/resources/data/quanqual.json"));
            JSONArray j = (JSONArray) o;
            for (Object object : j) {
                JSONObject myObj = (JSONObject) object;
                if (myObj.get("value").equals(name)){
                    JSONArray j2 = (JSONArray) myObj.get("array");
                    JSONObject newObject = new JSONObject();
                    newObject.put("key", say);
                    j2.add(newObject);
                    break;
                }
            }

            // Write the new json
            try (FileWriter file = new FileWriter("src/main/resources/data/quanqual.json")) {
                j.writeJSONString(file);
                file.flush();
            }

            freePropControllers.forEach(FreePropController::initialize);
            guidedPropControllers.forEach(GuidedPropController::initialize);
        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
