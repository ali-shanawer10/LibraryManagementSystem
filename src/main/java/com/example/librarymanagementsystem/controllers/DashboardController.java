package com.example.librarymanagementsystem.controllers;
import com.example.librarymanagementsystem.utils.SceneNavigator;

public class DashboardController {
    public void handleAllBooksClick() {
        SceneNavigator.loadScene("AllBooks.fxml");
    }

    public void handleAddBookClick() {
        SceneNavigator.loadScene("AddBook.fxml");
    }

    public void handleAllMembersClick() {
        SceneNavigator.loadScene("AllMembers.fxml");
    }

    public void handleAddMemberClick() {
        SceneNavigator.loadScene("AddMember.fxml");
    }

    public void handleIssueBookClick() {
        SceneNavigator.loadScene("IssueBook.fxml");
    }

    public void handleExit() {
        SceneNavigator.Exit();
    }
}

