package com.example.librarymanagementsystem.controllers;
import com.example.librarymanagementsystem.utils.SceneNavigator;

public class DashboardController {
    public void handleManageBooks() {
        SceneNavigator.loadScene("AllBooks.fxml");
    }

    public void handleManageUsers() {
        SceneNavigator.loadScene("AllMembers.fxml");
    }

    public void handleBorrowReturn() {
        SceneNavigator.loadScene("IssueBook.fxml");
    }

    public void handleExit() {
        SceneNavigator.Exit();
    }
}

