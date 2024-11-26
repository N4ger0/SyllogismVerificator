package univ.syllogismverificator.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.*;

import javafx.stage.Stage;
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

/**
 * Controller for the resolution of a syllogism. It's the parent controller for both FreePropController
 * and GuidedPropController
 * @see GuidedPropController
 * @see FreePropController
 */
public class SolverController {
    /**
     * Button to switch between language in GuidedMode
     */
    @FXML
    public Button language;
    /**
     * Button to go back to the main menu in GuidedMode
     * @see MainMenuController
     */
    public Button back;
    /**
     * Button to go back to the main menu in FreeMode
     * @see MainMenuController
     */
    public Button back1;
    /**
     * Button to add a quantifier in FreeMode
     */
    public Button schemaAdd1;
    /**
     * Button to switch between language in FreeMode
     */
    public Button language1;
    Traductor traductor = new Traductor() ;

    /**
     * Title of the Box where the validity of the syllogism is displayed in GuidedMode
     */
    @FXML
    private Text text_regle ;
    /**
     * Title of the Box where the validity of the syllogism is displayed in FreeMode
     */
    @FXML
    private Text text_regle2 ;
    /**
     * Button to add a quantifier in GuidedMode
     */
    @FXML
    public Button schemaAdd;
    /**
     * The TabPane where the user can navigate between the GuidedMode and the FreeMode
     */
    @FXML
    private TabPane tabWindow;

    /**
     * The tab for the GuidedMode
     */
    @FXML
    private Tab tab_guided ;

    /**
     * The tab for the FreeMode
     */
    @FXML
    private Tab tab_free ;

    /**
     * The titledPane who is the root of our application
     */
    @FXML
    private TitledPane titled_pane ;

    /**
     * The VBox at the bottom of the GuidedMode window. Contains the buttons and the Tutorial Text
     */
    @FXML
    private VBox guidedPropositions;
    /**
     * The Text the display subject in the selected language
     */
    @FXML
    private Text text_sujet ;
    /**
     * List of all the guidedPropControllers
     * @see GuidedPropController
     */
    private ArrayList<GuidedPropController> guidedPropControllers = new ArrayList<>();

    /**
     * HashMap containing all the occurences of the inputed terms in GuidedMode
     */
    private HashMap<String, Integer> counterForGuidedProp = new HashMap<>();
    /**
     * HashMap containing all the occurences of the inputed terms in FreeMode
     */
    private HashMap<String, Integer> counterForFreeProp = new HashMap<>();

    /** The Text element where the tutorialText is displayed*/
    @FXML
    private Text tutorialText;

    /**
     * Solve button for the guidedMode
     */
    @FXML
    private Button guidedSolve;
    /**
     * CheckBox for switching on and off the existence hypothesis for given rule in GuidedMode
     */
    @FXML
    private CheckBox guidedMt;
    /**
     * CheckBox for switching on and off the existence hypothesis for given rule in GuidedMode
     */
    @FXML
    private CheckBox guidedLh;
    /**
     * CheckBox for switching on and off the existence hypothesis for given rule in GuidedMode
     */
    @FXML
    private CheckBox guidedNn;
    /**
     * CheckBox for switching on and off the existence hypothesis for given rule in GuidedMode
     */
    @FXML
    private CheckBox guidedN;
    /**
     * CheckBox for switching on and off the existence hypothesis for given rule in GuidedMode
     */
    @FXML
    private CheckBox guidedAa;
    /**
     * CheckBox for switching on and off the existence hypothesis for given rule in GuidedMode
     */
    @FXML
    private CheckBox guidedPp;
    /**
     * CheckBox for switching on and off the existence hypothesis for given rule in GuidedMode
     */
    @FXML
    private CheckBox guidedP;
    /**
     * CheckBox for switching on and off the existence hypothesis for given rule in GuidedMode
     */
    @FXML
    private CheckBox guidedUu;
    /**
     * CheckBox for switching on and off the existence hypothesis for given rule in GuidedMode
     */
    @FXML
    private CheckBox guidedHE;
    /**
     * CheckBox for switching on and off the existence hypothesis for given rule in guidedMode
     */
    @FXML
    private Text guidedCCL;

    /**
     * VBox at the bottom of the FreeMode, contains the buttons
     */
    @FXML
    private VBox freePropositions;
    /**
     * Hashmap to store the occurences of the terms in free mode
     */
    private ArrayList<FreePropController> freePropControllers = new ArrayList<>();

    /**
     * Initial TextField for the needed terms in FreeMode
     */
    @FXML
    private TextField textFieldPredicat ;
    /**
     * Initial TextField for the needed terms in FreeMode
     */
    @FXML
    private TextField textFieldSujet ;

    /**
     * Button to add a proposition in FreeMode
     * @see FreePropController
     */
    @FXML
    private Button addFreeProp;
    /**
     * Button to solve a syllogism in freeMode
     */
    @FXML
    private Button freeSolve;
    /**
     * CheckBox for switching on and off the existence hypothesis for given rule in freeMode
     */
    @FXML
    private CheckBox freeMt;
    /**
     * CheckBox for switching on and off the existence hypothesis for given rule in freeMode
     */
    @FXML
    private CheckBox freeLh;
    /**
     * CheckBox for switching on and off the existence hypothesis for given rule in freeMode
     */
    @FXML
    private CheckBox freeNn;
    /**
     * CheckBox for switching on and off the existence hypothesis for given rule in freeMode
     */
    @FXML
    private CheckBox freeN;
    /**
     * CheckBox for switching on and off the existence hypothesis for given rule in freeMode
     */
    @FXML
    private CheckBox freeAa;
    /**
     * CheckBox for switching on and off the existence hypothesis for given rule in freeMode
     */
    @FXML
    private CheckBox freePp;
    /**
     * CheckBox for switching on and off the existence hypothesis for given rule in freeMode
     */
    @FXML
    private CheckBox freeP;
    /**
     * CheckBox for switching on and off the existence hypothesis for given rule in freeMode
     */
    @FXML
    private CheckBox freeUu;
    /**
     * CheckBox for switching on and off the existence hypothesis for given rule in freeMode
     */
    @FXML
    private CheckBox freeHE;
    /**
     * CheckBox for switching on and off the existence hypothesis for given rule in freeMode
     */
    @FXML
    private Text freeCCL;
    /**
     * Text that display "predicate" in the selected language
     */
    @FXML
    private Text text_predicat ;

    /**
     * Instance of the Solver
     * @see Solver
     */
    private Solver solver;

    /**
     * clear all the inputed fields in both of the input mode
     */
    private void clear(){
        guidedPropControllers.clear();
        freePropControllers.clear();
        guidedPropositions.getChildren().clear();
        freePropositions.getChildren().clear();
        FreePropController.TextCounter = 0;
        GuidedPropController.TextCounter = 1;
    }

    /**
     * Initializer method for SolverController
     */
    @FXML
    public void initialize() {
        clear();
        initTexts();
        System.out.println("INIT");

        initPropositions();
        initButtons();
        solver = new Solver();
        setEventOnTextFieldsGuidedMode();
        setEventOnTextFieldsFreeMode();
        for(FreePropController controller : freePropControllers) {
            controller.setEvent(counterForFreeProp) ;
        }
    }

    /**
     * Event fonction for change on TextField in GuidedMode,
     * @param oldValue oldValue of the TextField, will be deleted of the HashMap
     * @param newValue newValue of the TextField, will be added to the HashMap
     */
    private void handleChangeOnTextField(SolverController solver,String oldValue, String newValue) {
        if(counterForGuidedProp.containsKey(newValue)) {
            counterForGuidedProp.replace(newValue, counterForGuidedProp.get(newValue) - 1);
        }
        else {
            if(!Objects.equals(newValue, "")) {
                counterForGuidedProp.put(newValue, 1);
            }
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

    private void setEventOnTextFieldsFreeMode() {
        textFieldSujet.textProperty().addListener((observable, oldValue, newValue) -> {
            handleChangeOnTextFieldsFreeMode(oldValue, newValue);
        });
        textFieldSujet.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue) {
                handleChangeFocusFreeMode();
            }
        });
        textFieldPredicat.textProperty().addListener((observable, oldValue, newValue) -> {
            handleChangeOnTextFieldsFreeMode(oldValue, newValue);
        });
        textFieldPredicat.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue) {
                handleChangeFocusFreeMode();
            }
        });
        for(FreePropController freePropController : freePropControllers) {
            freePropController.getFreeTextFieldMedium().textProperty().addListener((observable, oldValue, newValue) -> {
                handleChangeOnTextFieldsFreeMode(oldValue, newValue);
            });
            freePropController.getFreeTextFieldMedium().focusedProperty().addListener((observable, oldValue, newValue) -> {
                if(!newValue) {
                    handleChangeFocusFreeMode();
                }
            });
        }
    }

    private void removeFromAll(String key){
        for(FreePropController freePropController : freePropControllers) {
            freePropController.getFreeTerme1().getItems().removeIf( im -> (Objects.equals(im.getText(), key)));
            freePropController.getFreeTerme2().getItems().removeIf( im -> (Objects.equals(im.getText(), key)));
        }
    }

    private void handleChangeFocusFreeMode() {
        for(FreePropController controller : freePropControllers) {
            controller.getFreeTerme1().getItems().clear();
            controller.getFreeTerme2().getItems().clear();
            for (String key : counterForFreeProp.entrySet().stream().filter(entry -> entry.getValue() != 0).map(Map.Entry::getKey).collect(Collectors.toSet())) {
                MenuItem mi1 = new MenuItem(key);
                mi1.setOnAction((event) -> {
                    controller.getFreeTerme1().setText(key);
                    counterForFreeProp.replace(key, counterForFreeProp.get(key) - 1);
                    handleChangeFocusFreeMode();
                    controller.getFreeTerme2().getItems().removeIf(im -> im.getText().equals(key));
//                    if(counterForFreeProp.get(key) == 0) {
//                        removeFromAll(key);
//                    }
                });
                MenuItem mi2 = new MenuItem(key);
                mi2.setOnAction((event) -> {

                    controller.getFreeTerme2().setText(key);
                    counterForFreeProp.replace(key, counterForFreeProp.get(key) - 1);
                    handleChangeFocusFreeMode();
                    controller.getFreeTerme1().getItems().removeIf(im -> im.getText().equals(key));
//                    if(counterForFreeProp.get(key) == 0) {
//                        removeFromAll(key);
//                    }
                });
                if(!Objects.equals(controller.getFreeTerme1().getText(), key)) {
                    controller.getFreeTerme1().getItems().add(mi1);
                }
                if(!Objects.equals(controller.getFreeTerme2().getText(), key)) {
                    controller.getFreeTerme2().getItems().add(mi2);
                }
                controller.getFreeTerme1().layout();
                controller.getFreeTerme2().layout();
            }
            System.out.println(counterForFreeProp);
        }
    }

    private void handleChangeOnTextFieldsFreeMode(String oldValue, String newValue){
        for(FreePropController controller : freePropControllers) {
            controller.currentSelected1 = null ;
//            if(controller.getFreeTerme1().getText() != traductor.get("terme") + "1") {
//                counterForFreeProp.put(controller.getFreeTerme1().getText(), counterForFreeProp.get(controller.getFreeTerme1().getText()) + 1);
//            }
            controller.getFreeTerme1().setText(traductor.get("terme") + "1");
            controller.getFreeTerme1().getItems().clear();
            controller.currentSelected2 = null ;
//            if(controller.getFreeTerme2().getText() != traductor.get("terme") + "2") {
//                counterForFreeProp.put(controller.getFreeTerme2().getText(), counterForFreeProp.get(controller.getFreeTerme2().getText()) + 1);
//            }
            controller.getFreeTerme2().setText(traductor.get("terme") + "2");
            controller.getFreeTerme2().getItems().clear();
        }
        counterForFreeProp.put(newValue, 2);
        counterForFreeProp.remove(oldValue);
        System.out.println(counterForFreeProp);
    }


    private void initPropositions() {
        for (int i = 0; i < 3; i++) {  // Ajout des 3 propositions par default
            addGuidedProposition();
            addFreeProposition();
        }
    }

    private void initTexts() {
        tutorialText.setText(Traductor.get("syllogism_def"));

        back.setText(Traductor.get("back"));
        back1.setText(Traductor.get("back"));
        text_regle.setText(Traductor.get("rules"));
        text_regle2.setText(Traductor.get("rules"));

        tabWindow.setOnMouseClicked(event -> tutorialText.setText(traductor.get("syllogism_def")));
        titled_pane.setText(traductor.get("title"));
        tab_guided.setText(traductor.get("guided_mode"));
        tab_free.setText(traductor.get("free_mode"));
        guidedSolve.setText(traductor.get("solve"));
        guidedHE.setText(traductor.get("exist_hypothese"));
        text_sujet.setText(traductor.get("subject"));
        //text_middle.setText(traductor.get("moyen_terme"));
        text_predicat.setText(traductor.get("predicate"));
        freeSolve.setText(traductor.get("solve"));
        //freeHE.setText(traductor.get("exist_hypothese"));
        addFreeProp.setText(Traductor.get("add_prop"));

        language.setText(Traductor.getLang().equals("fr") ? "EN" : "FR");
        language1.setText(Traductor.getLang().equals("fr") ? "EN" : "FR");

        schemaAdd.setText(traductor.get("add_schema"));
        schemaAdd1.setText(traductor.get("add_schema"));
    }

    private void initButtons() {
        addFreeProp.setOnAction(event -> addFreeProposition());
        guidedSolve.setOnAction(event -> guidedSolve());
        freeSolve.setOnAction(event -> freeSolve());
        schemaAdd.setOnAction(event -> askSchema());
        language.setOnAction(event -> {
            traductor.setLang(Traductor.getLang().equals("fr") ? "en" : "fr");
            initialize();
        });
        back.setOnAction(event -> goToMenu(back));

        back1.setOnAction(back.getOnAction());
        schemaAdd1.setOnAction(schemaAdd.getOnAction());
        language1.setOnAction(language.getOnAction());
    }

    public static void goToMenu(Button back) {
        FXMLLoader loader = new FXMLLoader(SolverController.class.getResource("/views/main_menu-view.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage)back.getScene().getWindow();
            MainMenuController controller = loader.getController() ;
            controller.initialize(stage);
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void setEventOnMenuButtonFreeMode(FreePropController p) {
        /*p.getFreeTerme1().setOnMouseClicked((event -> {
            MenuButton button = (MenuButton) event.getSource();
            button.getItems().clear();
            for (String key : counterForFreeProp.keySet()) {
                MenuItem mi = new MenuItem(key);
                button.getItems().add(mi);
                button.layout();
            }
        }));

        p.getFreeTerme2().setOnMouseClicked((event -> {
            MenuButton button = (MenuButton) event.getSource();
            button.getItems().clear();
            for (String key : counterForFreeProp.keySet()) {
                MenuItem mi = new MenuItem(key);
                button.getItems().add(mi);
                button.layout();
            }
        }));*/
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

        setEventOnMenuButtonFreeMode(FPC);
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
            else if (!term.equals("Terme     1")) {
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
                setProposition(ps, true);
                SyllogismResult res = solver.solve(ps, guidedMt.isSelected(), guidedLh.isSelected(), guidedNn.isSelected(), guidedN.isSelected(), guidedAa.isSelected(), guidedPp.isSelected(), guidedP.isSelected(), guidedUu.isSelected(), guidedHE.isSelected());

                if (res.isValid()) {
                    if (guidedHE.isSelected()) {
                        guidedCCL.setText("Syllogisme valide!\n" + res.getResults().getLast().toString());

                    } else {
                        guidedCCL.setText("Syllogisme valide!");
                    }
                }
                else {
                    guidedCCL.setText("Syllogisme invalide!\n" + res);
                }
            }
        }
    }

    private void setProposition(Polysyllogism ps, boolean guided) {
            List<Node> ordered = new ArrayList<>();
            List<Object> orderedPropControllers = new ArrayList<>();
            ArrayList<?> objects = guided ? guidedPropControllers : freePropControllers;
            VBox vbox = guided ? guidedPropositions : freePropositions;

            ps.stream().forEach(p -> {
                for (int i = 0; i < objects.size(); i++) {
                    if(objects.getFirst() instanceof GuidedPropController){
                        if (!((GuidedPropController) objects.get(i)).getProposition().equals(p)) continue;
                    } else {
                        if (!((FreePropController) objects.get(i)).getProposition().equals(p)) continue;
                    }
                    System.out.println(p);
                    ordered.add(vbox.getChildren().get(i));
                    orderedPropControllers.add(objects.get(i));
                    break;
                }
            });
            ordered.add(vbox.getChildren().getLast());
            vbox.getChildren().clear();
            objects.clear();
            if(guided) orderedPropControllers.stream().map(GuidedPropController.class::cast).forEach(guidedPropControllers::add);
            else orderedPropControllers.stream().map(FreePropController.class::cast).forEach(freePropControllers::add);
            ordered.forEach(vbox.getChildren()::add);

    }

    /**
     * Lance la resolution du syllogisme dans le mode libre.
     */
    private void freeSolve() {
        if (isFreePSValid()) {
            Polysyllogism ps = getFreePropositions();

            if (ps.sort()) {
                setProposition(ps, false);
                SyllogismResult res = solver.solve(ps, freeMt.isSelected(), freeLh.isSelected(), freeNn.isSelected(), freeN.isSelected(), freeAa.isSelected(), freePp.isSelected(), freeP.isSelected(), freeUu.isSelected(), freeHE.isSelected());

                if (res.isValid()) {
                    if (freeHE.isSelected()) {
                        freeCCL.setText("Syllogisme valide!\n" + res.getResults().getLast().toString());
                    } else {
                        freeCCL.setText("Syllogisme valide!");
                    }
                }
                else {
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
