package univ.syllogismverificator.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.*;

import javafx.util.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.io.IOException;
import java.util.stream.Collectors;

import univ.syllogismverificator.models.Solver;
import univ.syllogismverificator.Traductor;
import univ.syllogismverificator.controllers.composant.*;
import univ.syllogismverificator.models.Polysyllogism;
import univ.syllogismverificator.models.Proposition;
import univ.syllogismverificator.models.SyllogismResult;

public class SolverController {
    public Button language;
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

    private HashMap<String, Integer> counterForGuidedProp = new HashMap<>();

    /** Le champ textuel permettant d'aider l'utilisateur.*/
    @FXML
    private Text tutorialText;

    @FXML
    private Button guidedSolve;
    @FXML
    private CheckBox guidedMt;
    @FXML
    private CheckBox guidedLh;
    @FXML
    private CheckBox guidedNn;
    @FXML
    private CheckBox guidedN;
    @FXML
    private CheckBox guidedAa;
    @FXML
    private CheckBox guidedPp;
    @FXML
    private CheckBox guidedP;
    @FXML
    private CheckBox guidedUu;
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
    private TextField textFieldSujet ;

    @FXML
    private Button addFreeProp;
    @FXML
    private Button freeSolve;
    @FXML
    private CheckBox freeMt;
    @FXML
    private CheckBox freeLh;
    @FXML
    private CheckBox freeNn;
    @FXML
    private CheckBox freeN;
    @FXML
    private CheckBox freeAa;
    @FXML
    private CheckBox freePp;
    @FXML
    private CheckBox freeP;
    @FXML
    private CheckBox freeUu;
    @FXML
    private CheckBox freeHE;
    @FXML
    private Text freeCCL;
    @FXML
    private Text text_predicat ;

    private Solver solver;

    private void clear(){
        guidedPropControllers.clear();
        freePropControllers.clear();
        guidedPropositions.getChildren().clear();
        freePropositions.getChildren().clear();
        FreePropController.TextCounter = 0;
        GuidedPropController.TextCounter = 1;
    }

    @FXML
    public void initialize() {
        clear();
        initTexts();
        System.out.println("INIT");

        initPropositions();
        initButtons();
        solver = new Solver();
        setEventOnTextFieldsFreeMode();
        setEventOnTextFieldsGuidedMode();
        language.setOnAction(event -> {
            traductor.setLang(Traductor.getLang().equals("fr") ? "en" : "fr");
            initialize();
        });
    }

    private void handleChangeOnTextField(SolverController solver,String oldValue, String newValue) {
        if(counterForGuidedProp.containsKey(newValue)) {
            counterForGuidedProp.replace(newValue, counterForGuidedProp.get(newValue) - 1);
        }
        else {
            counterForGuidedProp.put(newValue, 1);
        }
        counterForGuidedProp.remove(oldValue);
        for(GuidedPropController guidedPropController : guidedPropControllers) {
            guidedPropController.getGuidedTerme1().entries = counterForGuidedProp.entrySet().stream().filter(entry -> entry.getValue() != 0).map(Map.Entry::getKey).collect(Collectors.toSet());
            guidedPropController.getGuidedTerme1().entries.remove(guidedPropController.getGuidedTerme2().getText());
            guidedPropController.getGuidedTerme2().entries = counterForGuidedProp.entrySet().stream().filter(entry -> entry.getValue() != 0).map(Map.Entry::getKey).collect(Collectors.toSet());
            guidedPropController.getGuidedTerme2().entries.remove(guidedPropController.getGuidedTerme1().getText());
        }
        System.out.println(counterForGuidedProp);
    }

    private void setEventOnTextFieldsGuidedMode() {
        for(GuidedPropController guidedPropController : guidedPropControllers) {
            guidedPropController.getGuidedTerme1().textProperty().addListener((observable, oldValue, newValue) -> {
                handleChangeOnTextField(this, oldValue, newValue);
            });
            guidedPropController.getGuidedTerme2().textProperty().addListener((observable, oldValue, newValue) -> {
                handleChangeOnTextField(this, oldValue, newValue);
            });
        }
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
        titled_pane.setText(traductor.get("title"));
        tab_guided.setText(traductor.get("guided_mode"));
        tab_free.setText(traductor.get("free_mode"));
        guidedSolve.setText(traductor.get("solve"));
        schemaAdd.setText(traductor.get("add_schema"));
        guidedHE.setText(traductor.get("exist_hypothese"));
        text_sujet.setText(traductor.get("subject"));
        //text_middle.setText(traductor.get("moyen_terme"));
        text_predicat.setText(traductor.get("predicate"));
        freeSolve.setText(traductor.get("solve"));
        //freeHE.setText(traductor.get("exist_hypothese"));
        language.setText(Traductor.getLang().equals("fr") ? "EN" : "FR");
    }

    private void initButtons() {
        addFreeProp.setOnAction(event -> addFreeProposition());
        guidedSolve.setOnAction(event -> guidedSolve());
        freeSolve.setOnAction(event -> freeSolve());
        schemaAdd.setOnAction(event -> askSchema());
    }

    private void setEventOnTextFieldsFreeMode() {
        for (FreePropController p: freePropControllers) {
            p.getFreeTerme1().setOnMouseClicked((event -> {
                p.getFreeTerme1().getItems().clear();

                if (!textFieldSujet.getText().isEmpty()) {
                    p.getFreeTerme1().getItems().add(new MenuItem(textFieldSujet.getText()));
                }

                if (!textFieldPredicat.getText().isEmpty()) {
                    p.getFreeTerme1().getItems().add(new MenuItem(textFieldPredicat.getText()));
                }

                for (int i = 1; i < freePropControllers.size() - 1; i++) {
                    if (!freePropControllers.get(i).getFreeTextFieldMedium().getText().isEmpty()){
                        p.getFreeTerme1().getItems().add(new MenuItem(freePropControllers.get(i).getFreeTextFieldMedium().getText()));
                    }
                }
            }));

            p.getFreeTerme2().setOnMouseClicked((event -> {
                p.getFreeTerme2().getItems().clear();

                if (!textFieldSujet.getText().isEmpty()) {
                    p.getFreeTerme2().getItems().add(new MenuItem(textFieldSujet.getText()));
                }

                if (!textFieldPredicat.getText().isEmpty()) {
                    p.getFreeTerme2().getItems().add(new MenuItem(textFieldPredicat.getText()));
                }

                for (int i = 1; i < freePropControllers.size() - 1; i++) {
                    if (!freePropControllers.get(i).getFreeTextFieldMedium().getText().isEmpty()){
                        p.getFreeTerme2().getItems().add(new MenuItem(freePropControllers.get(i).getFreeTextFieldMedium().getText()));
                    }
                }
            }));
        }
    }
    private void setEventOnTextFieldsFreeMode(FreePropController p) {
        p.getFreeTerme1().setOnMouseClicked((event -> {
            p.getFreeTerme1().getItems().clear();

            if (!textFieldSujet.getText().isEmpty()) {
                p.getFreeTerme1().getItems().add(new MenuItem(textFieldSujet.getText()));
            }

            if (!textFieldPredicat.getText().isEmpty()) {
                p.getFreeTerme1().getItems().add(new MenuItem(textFieldPredicat.getText()));
            }

            for (int i = 1; i < freePropControllers.size() - 1; i++) {
                if (!freePropControllers.get(i).getFreeTextFieldMedium().getText().isEmpty()){
                    p.getFreeTerme1().getItems().add(new MenuItem(freePropControllers.get(i).getFreeTextFieldMedium().getText()));
                }
            }
        }));

        p.getFreeTerme2().setOnMouseClicked((event -> {
            p.getFreeTerme2().getItems().clear();

            if (!textFieldSujet.getText().isEmpty()) {
                p.getFreeTerme2().getItems().add(new MenuItem(textFieldSujet.getText()));
            }

            if (!textFieldPredicat.getText().isEmpty()) {
                p.getFreeTerme2().getItems().add(new MenuItem(textFieldPredicat.getText()));
            }

            for (int i = 1; i < freePropControllers.size() - 1; i++) {
                if (!freePropControllers.get(i).getFreeTextFieldMedium().getText().isEmpty()){
                    p.getFreeTerme2().getItems().add(new MenuItem(freePropControllers.get(i).getFreeTextFieldMedium().getText()));
                }
            }
        }));
    }

    public Text getTutorialText() {
        return tutorialText;
    }

    private ArrayList<String> completion = new ArrayList<>();


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

        setEventOnTextFieldsFreeMode(FPC);
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

    /**
     * Creer une map qui compte les occurences de chaque terme du mode guide.
     *
     * @return Une HashMap contenant chaque terme en clé ainsi que leurs occurence en valeur.
     */
    private Map<String, Integer> getGuidedTermsOccurence(){
        Map<String, Integer> map = new HashMap<>();
        String term;
        for (GuidedPropController GPC: guidedPropControllers) {
            term = GPC.getTerm1();
            if (map.containsKey(term)) {
                map.put(term, map.get(term) + 1);
            }
            else if (!term.isEmpty()) {
                map.put(term, 1);
            }

            term = GPC.getTerm2();
            if (map.containsKey(term)){
                map.put(term, map.get(term) + 1);
            }
            else if (!term.isEmpty()) {
                map.put(term, 1);
            }
        }
        return map;
    }

    /**
     * Creer une map qui compte les occurences de chaque terme du mode libre.
     *
     * @return Une HashMap contenant chaque terme en clé ainsi que leurs occurence en valeur.
     */
    private Map<String, Integer> getFreeTermsOccurence(){
        Map<String, Integer> map = new HashMap<>();
        String term;
        for (FreePropController FPC: freePropControllers) {
            term = FPC.getTerm1();
            if (map.containsKey(term)){
                map.put(term, map.get(term) + 1);
            }
            else if (!term.equals("Terme 1")) {
                map.put(term, 1);
            }

            term = FPC.getTerm2();
            if (map.containsKey(term)){
                map.put(term, map.get(term) + 1);
            }
            else if (!term.equals("Terme 2")) {
                map.put(term, 1);
            }
        }
        return map;
    }

    /**
     * Construit un message d'erreur si les entrées du mode guide sont non coherentes.
     *<br>
     * Si toutes les entrees sont cherentes, le message est une chaine vide
     * @return Le message d'erreur.
     */
    private boolean isGuidedPSValid() {
        String msg = "";
        for (GuidedPropController GPC: guidedPropControllers) {
            msg += GPC.isValid();
        }
        Map<String, Integer> occ = getGuidedTermsOccurence();
        for (Map.Entry<String, Integer> entry : occ.entrySet()) {
            if (entry.getValue() != 2){
                msg += "Le terme " + entry.getKey() + " n'apparait pas 2 fois.\n";
            }
        }
        guidedCCL.setText(msg);

        return msg.isEmpty();
    }

    /**
     * Construit un message d'erreur si les entrées du mode libre sont non coherentes.
     *<br>
     * Si toutes les entrees sont cherentes, le message est une chaine vide
     * @return Le message d'erreur.
     */
    private boolean isFreePSValid() {
        String msg = "";
        for (FreePropController FPC: freePropControllers) {
            msg += FPC.isValid();
        }
        Map<String, Integer> occ = getFreeTermsOccurence();
        for (Map.Entry<String, Integer> entry : occ.entrySet()) {
            if (entry.getValue() != 2){
                msg += "Le terme " + entry.getKey() + " n'apparait pas 2 fois.\n";
            }
        }
        freeCCL.setText(msg);

        return msg.isEmpty();
    }

    /**
     * Lance la resolution du syllogisme dans le mode guide.
     */
    private void guidedSolve() {
        if (isGuidedPSValid()){
            Polysyllogism ps = getGuidedPropositions();

            if (ps.sort()){
                SyllogismResult res = solver.solve(ps, guidedMt.isSelected(), guidedLh.isSelected(), guidedNn.isSelected(), guidedN.isSelected(), guidedAa.isSelected(), guidedPp.isSelected(), guidedP.isSelected(), guidedUu.isSelected(), guidedHE.isSelected());

                if (res.isValid()){
                    guidedCCL.setText("Syllogisme valide!");
                }
                else {
                    guidedCCL.setText("Syllogisme invalide!\n" + res);
                }
            }
        }
    }

    /**
     * Lance la resolution du syllogisme dans le mode libre.
     */
    private void freeSolve() {
        if (isFreePSValid()) {
            Polysyllogism ps = getFreePropositions();

            if (ps.sort()) {
                SyllogismResult res = solver.solve(ps, freeMt.isSelected(), freeLh.isSelected(), freeNn.isSelected(), freeN.isSelected(), freeAa.isSelected(), freePp.isSelected(), freeP.isSelected(), freeUu.isSelected(), freeHE.isSelected());

                if (res.isValid()) {
                    freeCCL.setText("Syllogisme valide!");
                } else {
                    freeCCL.setText("Syllogisme invalide!\n" + res);
                }
            }
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
            JSONObject o = (JSONObject) new JSONParser().parse(new FileReader("src/main/resources/data/quanqual.json"));
            JSONArray j = (JSONArray) o.get(Traductor.getLang());
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
                o.writeJSONString(file);
                file.flush();
            }

            freePropControllers.forEach(FreePropController::initialize);
            guidedPropControllers.forEach(GuidedPropController::initialize);
        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
