package univ.syllogismverificator.controllers.composant;

import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import univ.syllogismverificator.controllers.SolverController;
import javafx.util.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Cette classe représente le controller d'une des proposition du mode guidé.
 * Elle contient les informations necessaire a la resolution du syllogisme.
 */
public class GuidedPropController {
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
    private MenuButton guidedQQL;

    /** Le champ textuel representant le 1er terme de la proposition.*/
    @FXML
    private TextField guidedTerme1;

    /** Le Menu deroulant permettant de choisir le verbe de la proposition.*/
    @FXML
    private MenuButton verbe;

    /** Le champ textuel representant le 1er terme de la proposition.*/
    @FXML
    private TextField guidedTerme2;


    /** Entier servant de compteur de proposition.*/
    private SolverController parentController;

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
        initTextFields();
    }

    private void initText(){
        text.setText("Prémisse " + TextCounter);
        TextCounter++;
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

    private void initMenuItems() {
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

    private void initTutorialText() {
        Text tutorialText = parentController.getTutorialText();

        guidedQQL.setOnMouseClicked(event -> tutorialText.setText("Explication QQL: \n" +
                "\n" +
                "Dans la logique des syllogismes, la quantité et la qualité d'une proposition déterminent son étendue et sa nature.\n" +
                " -  Quantité : Elle indique si la proposition est universelle ou particulière. Une proposition universelle s'applique à tous les membres d'une catégorie, tandis qu'une proposition particulière ne concerne qu'une partie de la catégorie.\n" +
                " -  Qualité : Elle précise si la proposition est affirmative ou négative. Une proposition affirmative établit une relation positive entre les termes, tandis qu'une proposition négative nie cette relation."));

        guidedTerme1.setOnMouseClicked(event -> tutorialText.setText("Explication terme: \n" +
                "Dans un syllogisme, les termes sont les éléments utilisés dans les propositions, ils jouent un rôle central dans le raisonnement logique. Il y a trois types de termes :\n" +
                "\n" +
                " -  Terme majeur : C'est le prédicat de la conclusion, c'est la partie d'une proposition qui affirme ou nie quelque chose à propos du sujet.\n" +
                " -  Terme mineur : C'est le sujet de la conclusion, c'est la partie d'une proposition qui désigne ce dont on parle. Il représente l'élément ou l'entité à propos de laquelle le prédicat fait une affirmation.\n" +
                " -  Terme moyen : C'est le terme qui relie les deux prémisses d'un syllogisme, mais qui ne figure pas dans la conclusion. Il sert de lien entre le terme majeur et le terme mineur.\n" +
                "\n" +
                "Ces trois termes permettent de structurer le raisonnement du syllogisme."));

        verbe.setOnMouseClicked(event -> tutorialText.setText("Explication verbe: \n" +
                "Le verbe est le mot ou le groupe de mots qui exprime l'action, l'état ou le processus dans une proposition. Il est essentiel pour établir une relation entre le sujet et le prédicat."));

        guidedTerme2.setOnMouseClicked(event -> tutorialText.setText("Explication terme: \n" +
                "Dans un syllogisme, les termes sont les éléments utilisés dans les propositions, ils jouent un rôle central dans le raisonnement logique. Il y a trois types de termes :\n" +
                "\n" +
                " -  Terme majeur : C'est le prédicat de la conclusion, c'est la partie d'une proposition qui affirme ou nie quelque chose à propos du sujet.\n" +
                " -  Terme mineur : C'est le sujet de la conclusion, c'est la partie d'une proposition qui désigne ce dont on parle. Il représente l'élément ou l'entité à propos de laquelle le prédicat fait une affirmation.\n" +
                " -  Terme moyen : C'est le terme qui relie les deux prémisses d'un syllogisme, mais qui ne figure pas dans la conclusion. Il sert de lien entre le terme majeur et le terme mineur.\n" +
                "\n" +
                "Ces trois termes permettent de structurer le raisonnement du syllogisme."));
    }

    private void initTextFields(){
        guidedTerme1.setPromptText("terme 1");
        guidedTerme2.setPromptText("terme 2");
    }

    /**
     * Stocke le controller du parent.
     */
    public void setParentController(SolverController parent) {
        this.parentController = parent;
        initTutorialText();
    }

    /**
     * Recupere les differentes parties de la proposition.
     * <br>
     * Les cles sont respectivement "QQL", "terme1", "verbe", "terme2"
     * @return une Map contenant la QQL, le 1er terme, le verbe et le 2eme terme dans cet ordre.
     */
    public Map<String, String> getProposition() {
        Map<String, String> proposition = new HashMap<>();
        proposition.put("QQL", guidedQQL.getText());
        proposition.put("terme1", guidedTerme1.getText());
        proposition.put("verbe", verbe.getText());
        proposition.put("terme2", guidedTerme2.getText());
        return proposition;
    }
}