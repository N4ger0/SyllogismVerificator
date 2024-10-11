package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

public class SolverController {

    @FXML
    private MenuButton QQLGM1;
    @FXML
    private MenuButton QQLGM2;
    @FXML
    private MenuButton QQLGM3;

    @FXML
    private MenuButton QQLFM1;
    @FXML
    private MenuButton QQLFM2;
    @FXML
    private MenuButton QQLFM3;

    @FXML
    private MenuButton S11;
    @FXML
    private MenuButton S12;
    @FXML
    private MenuButton S21;
    @FXML
    private MenuButton S22;
    @FXML
    private MenuButton S31;
    @FXML
    private MenuButton S32;


    @FXML
    public void initialize() {
        initMenuItems();
        loadMenuItemsFromJson();
    }

    private void initMenuItems() {
        for (MenuItem mi : QQLGM1.getItems()){
            mi.setOnAction(event -> QQLGM1.setText(mi.getText()));
        }
        for (MenuItem mi : QQLGM2.getItems()){
            mi.setOnAction(event -> QQLGM2.setText(mi.getText()));
        }
        for (MenuItem mi : QQLGM3.getItems()){
            mi.setOnAction(event -> QQLGM3.setText(mi.getText()));
        }
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
