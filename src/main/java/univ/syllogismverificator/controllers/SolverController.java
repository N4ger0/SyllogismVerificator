package univ.syllogismverificator.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
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
    private Hashtable<String, String> QQLList = new Hashtable<>() ;
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
        loadMenuItemsFromJson();
        initMenuItems();
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

    private void loadMenuItemsFromJson() {
        try {
            Object o = new JSONParser().parse(new FileReader("src/main/resources/data/quanqual.json"));
            JSONArray j = (JSONArray) o;
            for (Object object : j) {
                JSONObject myObj = (JSONObject) object;
                QQLList.put((String) myObj.get("key"), (String) myObj.get("value"));
            }
        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
