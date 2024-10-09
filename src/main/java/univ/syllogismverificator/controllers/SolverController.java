package univ.syllogismverificator.controllers;

import javafx.fxml.FXML;

public class SolverController {
    @FXML
    public void initialize() {
        loadMenuItemsFromJson();
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
