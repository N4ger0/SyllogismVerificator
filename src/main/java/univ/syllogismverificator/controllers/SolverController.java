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
    /**
     * Instance of the traductor to handle language changes
     */
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
     * Reset button for the guidedMode
     */
    @FXML
    private Button guidedReset;
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

    /**
     * Button to delete a proposition in FreeMode
     * @see FreePropController
     */
    @FXML
    private Button delFreeProp;
    /**
     * Button to solve a syllogism in freeMode
     */
    @FXML
    private Button freeSolve;
    /**
     * Reset button for the freeMode
     */
    @FXML
    private Button freeReset;
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
            controller.setEvent(counterForFreeProp, this) ;
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

    /**
     * Method to set events on the textFields in guided mode
     */

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

    /**
     * Method to set events on the textFields in free mode
     */
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
        /*for(FreePropController freePropController : freePropControllers) {
            freePropController.getFreeTextFieldMedium().textProperty().addListener((observable, oldValue, newValue) -> {
                handleChangeOnTextFieldsFreeMode(oldValue, newValue);
            });
            freePropController.getFreeTextFieldMedium().focusedProperty().addListener((observable, oldValue, newValue) -> {
                if(!newValue) {
                    handleChangeFocusFreeMode();
                }
            });
        }*/
    }

    private void setEventOnTextFieldsFreeMode(FreePropController FPC) {
        FPC.getFreeTextFieldMedium().textProperty().addListener((observable, oldValue, newValue) -> {
            handleChangeOnTextFieldsFreeMode(oldValue, newValue);
        });
        FPC.getFreeTextFieldMedium().focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue) {
                handleChangeFocusFreeMode();
            }
        });
    }

    /**
     * Method to call to remove an item from all the menuButton items
     * @deprecated not called by any of the methods, instead filters the key entry directly
     * @param key the text of the item to remove
     */
    private void removeFromAll(String key){
        for(FreePropController freePropController : freePropControllers) {
            freePropController.getFreeTerme1().getItems().removeIf( im -> (Objects.equals(im.getText(), key)));
            freePropController.getFreeTerme2().getItems().removeIf( im -> (Objects.equals(im.getText(), key)));
        }
    }

    /**
     * Build the list of menuItems to put in the menu buttons when the focus is
     * put out of a textFields
     */
    public void handleChangeFocusFreeMode() {
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
                });
                MenuItem mi2 = new MenuItem(key);
                mi2.setOnAction((event) -> {

                    controller.getFreeTerme2().setText(key);
                    counterForFreeProp.replace(key, counterForFreeProp.get(key) - 1);
                    handleChangeFocusFreeMode();
                    controller.getFreeTerme1().getItems().removeIf(im -> im.getText().equals(key));
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

    /**
     * Method called on change of a TextField on a free mode, update the hashmap of an instance
     * @param oldValue the value entered before on the TextFields
     * @param newValue the value entered now on the TextFields
     */
    private void handleChangeOnTextFieldsFreeMode(String oldValue, String newValue){
        for(FreePropController controller : freePropControllers) {
            controller.currentSelected1 = null ;
            controller.getFreeTerme1().setText(traductor.get("terme") + "1");
            controller.getFreeTerme1().getItems().clear();

            controller.currentSelected2 = null ;
            controller.getFreeTerme2().setText(traductor.get("terme") + "2");
            controller.getFreeTerme2().getItems().clear();
        }
        if(!Objects.equals(newValue, "")) {
            counterForFreeProp.put(newValue, 2);
        }
        counterForFreeProp.remove(oldValue);
        counterForFreeProp.replaceAll((k, v) -> 2);
        System.out.println(counterForFreeProp);
    }

    /**
     * Init the number of propositions to 2 and a conclusion
     */
    private void initPropositions() {
        for (int i = 0; i < 3; i++) {  // Ajout des 3 propositions par default
            addGuidedProposition();
            addFreeProposition();
        }
    }

    /**
     * Method to init the text of the interface in the correct language
     */
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
        guidedReset.setText(traductor.get("reset"));
        freeReset.setText(traductor.get("reset"));
        guidedHE.setText(traductor.get("exist_hypothese"));
        text_sujet.setText(traductor.get("terme") + "1");
        //text_middle.setText(traductor.get("moyen_terme"));
        text_predicat.setText(traductor.get("terme") + "2");
        freeSolve.setText(traductor.get("solve"));
        //freeHE.setText(traductor.get("exist_hypothese"));
        addFreeProp.setText(Traductor.get("add_prop"));
        delFreeProp.setText(Traductor.get("del_prop"));

        language.setText(Traductor.getLang().equals("fr") ? "EN" : "FR");
        language1.setText(Traductor.getLang().equals("fr") ? "EN" : "FR");

        schemaAdd.setText(traductor.get("add_schema"));
        schemaAdd1.setText(traductor.get("add_schema"));
    }

    /**
     * Set the events of the buttons of the interface such as solve, addProp, askSchema
     */
    private void initButtons() {
        addFreeProp.setOnAction(event -> addFreeProposition());
        delFreeProp.setOnAction(event -> delFreeProposition());
        guidedSolve.setOnAction(event -> guidedSolve());
        guidedReset.setOnAction(event -> guidedReset());
        freeReset.setOnAction(event -> freeReset());
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

    /**
     * Method to got back to the main menu
     * @param back Button
     */
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

    public Text getTutorialText() {
        return tutorialText;
    }

    /**
     * Add a propositon in GuidedMode
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
     * Get the propositions list and make it a polysyllogism
     * @return a polysyllogism
     */
    private Polysyllogism getGuidedPropositions(){
        ArrayList<Proposition> propositionsList = new ArrayList<>();
        for (GuidedPropController GPP: guidedPropControllers) {
            propositionsList.add(GPP.getProposition());
        }
        return new Polysyllogism(propositionsList);
    }

    /**
     * Add proposition in free mode
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
     * Ajoute une proposition au mode libre.
     */
    private void delFreeProposition() {
        if (FreePropController.TextCounter > 3) {
            FreePropController.TextCounter--;
            freeReset();
        }
    }

    /**
     * Get a polysyllogism from the interface of the free mode
     * @return a polysyllogism
     */
    private Polysyllogism getFreePropositions(){
        ArrayList<Proposition> propositionsList = new ArrayList<>();
        for (FreePropController FPP: freePropControllers) {
            propositionsList.add(FPP.getProposition());
        }
        return new Polysyllogism(propositionsList);
    }

    /**
     * Create a map continin the number of occurences of each term in guided mode
     * @return a HashMap with the term as the key and the number of occurences
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
     * Create a map continin the number of occurences of each term in free mode
     * @return a HashMap with the term as the key and the number of occurences
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
     * Buid a message error if the inputed syllogism in guided mode isn't in the correct form
     * If the input is correct, return an empty string
     * @return String the error message
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
     * Buid a message error if the inputed syllogism in free mode isn't in the correct form
     * If the input is correct, return an empty string
     * @return String the error message
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
     * Start the resolution of the syllogism in guided mode
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

    /**
     * order a polysyllogism and display the correct order in the interface
     * @param ps the polysyllogism
     * @param guided boolean
     */
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
     * Start the resolution of the syllogism in free mode
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

    public void guidedReset() {
        guidedPropControllers.get(0).reset();
        guidedPropControllers.clear();
        guidedPropositions.getChildren().clear();
        counterForGuidedProp.clear();
        setEventOnTextFieldsGuidedMode();

        addGuidedProposition();
        addGuidedProposition();
        addGuidedProposition();

        guidedCCL.setText("");
    }

    public void freeReset() {
        textFieldSujet.setText("");
        textFieldPredicat.setText("");
        int nFPC = FreePropController.TextCounter;
        freePropControllers.get(0).reset();
        freePropControllers.clear();
        freePropositions.getChildren().clear();
        counterForFreeProp.clear();

        for (int i = 0; i < nFPC; i++) {
            addFreeProposition();
        }

        freeCCL.setText("");
    }

    /**
     * Récupère la liste des QQL dans le fichier json.
     */
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

    /**
     * Save the inputed quantifier and quality in the json
     * @param name String
     * @param say String
     */
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
