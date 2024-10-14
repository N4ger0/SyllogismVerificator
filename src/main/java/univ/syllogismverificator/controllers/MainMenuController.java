package univ.syllogismverificator.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {
    @FXML
    public Button ButtonTab;
    @FXML
    public Button buttonSolver;
    Stage mainStage ;
    @FXML
    public void initialize(Stage mainStage){
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
}
