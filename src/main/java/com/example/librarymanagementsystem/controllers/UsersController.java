package com.example.librarymanagementsystem.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import com.example.librarymanagementsystem.utils.SceneNavigator;

public class UsersController {

    @FXML
    private Button backButton;

    @FXML
    public void handleBackButton(ActionEvent event) {
        // Navigate back to the main menu
        SceneNavigator.goToMainMenu();
    }
}
