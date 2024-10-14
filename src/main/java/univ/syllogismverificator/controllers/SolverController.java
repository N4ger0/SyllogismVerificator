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

public class SolverController {

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
        /*
        try (FileReader reader = new FileReader("univ/syllogismverificator/data/quanqual.json")) {
            // Parse the JSON file using Gson
            char[] cbuf = new char[0];
            int temp = 0 ;
            while(temp != -1){
                temp = reader.read(cbuf) ;
                System.out.println(temp) ;
            }
            System.out.println(cbuf);


//            // Get the array of menu items from the JSON
//            JsonArray menuItemsArray = json.getAsJsonArray("menuItems");
//
//            // Loop through each item in the JSON array and create a MenuItem
//            for (int i = 0; i < menuItemsArray.size(); i++) {
//                JsonObject menuItemObj = menuItemsArray.get(i).getAsJsonObject();
//                String label = menuItemObj.get("label").getAsString();
//
//                // Create a new MenuItem and add it to the MenuButton
//                MenuItem menuItem = new MenuItem(label);
//                menuButton.getItems().add(menuItem);
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
