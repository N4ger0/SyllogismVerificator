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

/**
 * Controller for the Main Menu of the app. This is where the user choose if he wants to check the
 * validity of a syllogism or display all the possible syllogism
 */
public class MainMenuController {
    /**
     * The root element of the main menu
     */
    @FXML
    public TitledPane titled_pane;
    /**
     * The button to navigate to the tab containing all the possible syllogism
     */
    @FXML
    public Button ButtonTab;
    /**
     * The button to navigate to the syllogism solver
     */
    @FXML
    public Button buttonSolver;
    /**
     * The stage of the app
     */
    Stage mainStage ;

    /**
     * Instance of the traductor to translate the text of the window
     * @see Traductor
     */
    Traductor traductor = new Traductor() ;

    /**
     * Initializer for the main window
     * @param mainStage the stage of the main window
     */
    @FXML
    public void initialize(Stage mainStage){
        initButtons();

        titled_pane.setText(traductor.get("title"));
        this.mainStage = mainStage ;
    }

    /**
     * Set the text of the buttons in the chossen language and set the events to navigate windows
     */
    private void initButtons() {
        buttonSolver.setText(traductor.get("solve_syllogism"));
        buttonSolver.setPrefSize(titled_pane.getPrefWidth() / 3, titled_pane.getPrefHeight() / 3);
        buttonSolver.setStyle("-fx-font-size: 40");

        ButtonTab.setText(traductor.get("all_syllogism"));
        ButtonTab.setPrefSize(titled_pane.getPrefWidth() / 3, titled_pane.getPrefHeight() / 3);
        ButtonTab.setStyle("-fx-font-size: 30");
    }

    /**
     * Event called when the user click on the solver button, change the content of the main stage
     */
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

    /**
     * Event called when the user click on the all syllogism button, change the content
     * of the main stage
     */
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
