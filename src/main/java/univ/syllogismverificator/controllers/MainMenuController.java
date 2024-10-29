package univ.syllogismverificator.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import univ.syllogismverificator.Traductor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class MainMenuController {
    @FXML
    public Button ButtonTab;
    @FXML
    public Button buttonSolver;
    @FXML
    public TitledPane titled_pane;
    Stage mainStage ;

    Traductor traductor = new Traductor() ;

    @FXML
    public void initialize(Stage mainStage){
        ButtonTab.setText(traductor.get("all_syllogism"));
        buttonSolver.setText(traductor.get("solve_syllogism"));
        titled_pane.setText(traductor.get("title"));
        this.mainStage = mainStage ;
    }

    @FXML
    public void handleClickButtonSolver() {
        try {
            //Charger solve_syllogism_view.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/solve_syllogism_view.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            mainStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleClickButtonTable() {
        try {
            //Charger solve_syllogism_view.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/TableAll.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            mainStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
