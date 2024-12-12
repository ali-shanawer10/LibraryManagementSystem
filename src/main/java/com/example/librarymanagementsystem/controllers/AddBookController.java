package com.example.librarymanagementsystem.controllers;

import com.example.librarymanagementsystem.utils.SceneNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import static com.example.librarymanagementsystem.utils.BookHelper.insertBook;

public class AddBookController {

    @FXML
    private TextField titleField;

    @FXML
    private TextField authorField;

    // Method called when the "Add" button is clicked
    @FXML
    private void handleAddButton(ActionEvent event) {
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();

        if (title.isEmpty() || author.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "All fields are required!");
            return;
        }

        try {
            insertBook(title, author);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Book added successfully!");

            // Clear the fields after successful insertion
            titleField.clear();
            authorField.clear();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to add the book: " + e.getMessage());
        }
    }

    @FXML
    public void handleBackButton(ActionEvent event) {
        // Navigate back to the main menu
        SceneNavigator.goToMainMenu();
    }

    // Utility method to show alert dialogs
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
