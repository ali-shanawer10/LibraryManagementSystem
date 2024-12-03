package com.example.librarymanagementsystem.controllers;
import com.example.librarymanagementsystem.utils.SceneNavigator;

public class MainMenuController {
    public void handleManageBooks() {
        SceneNavigator.loadScene("Books.fxml");
    }

    public void handleManageUsers() {
        SceneNavigator.loadScene("Users.fxml");
    }

    public void handleBorrowReturn() {
        SceneNavigator.loadScene("BorrowRecords.fxml");
    }

    public void handleExit() {
        SceneNavigator.Exit();
    }
}

