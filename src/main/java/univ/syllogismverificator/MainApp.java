package univ.syllogismverificator;

import univ.syllogismverificator.controllers.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import univ.syllogismverificator.controllers.MainMenuController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        Traductor traductor = new Traductor() ;
        try {
            // Load FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/main_menu-view.fxml"));
            System.out.println(loader) ;
            Parent root = loader.load();

            MainMenuController controller = loader.getController() ;
            controller.initialize(primaryStage);

            // Create the scene and set it on the stage
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle(traductor.get("title"));
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
