package com.example.librarymanagementsystem.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneNavigator {
    private static Stage stage;

    // Initialize the stage when the application starts
    public static void initialize(Stage primaryStage) {
        stage = primaryStage;
    }

    // Method to load a new scene
    public static void loadScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneNavigator.class.getResource("/com/example/librarymanagementsystem/" + fxmlFile));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to go back to the main menu
    public static void goToMainMenu() {
        loadScene("Dashboard.fxml");
    }

    public static void Exit(){
        stage.close();
    }
}
