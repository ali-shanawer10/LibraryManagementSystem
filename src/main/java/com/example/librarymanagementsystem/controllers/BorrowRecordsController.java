package com.example.librarymanagementsystem.controllers;

import com.example.librarymanagementsystem.utils.SceneNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class BorrowRecordsController {
    @FXML
    private Button backButton;

    @FXML
    public void handleBackButton(ActionEvent event) {
        // Navigate back to the main menu
        SceneNavigator.goToMainMenu();
    }
}
