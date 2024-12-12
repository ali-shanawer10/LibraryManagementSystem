package com.example.librarymanagementsystem.controllers;

import com.example.librarymanagementsystem.utils.SceneNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import static com.example.librarymanagementsystem.utils.UserHelper.insertUser;

public class AddMemberController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField addressField;

    // Method called when the "Add" button is clicked
    @FXML
    private void handleAddButton(ActionEvent event) {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String email = emailField.getText().trim();
        String address = addressField.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || address.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "All fields are required!");
            return;
        }

        try {
            insertUser(firstName, lastName, email, address);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Member added successfully!");

            // Clear the fields after successful insertion
            firstNameField.clear();
            lastNameField.clear();
            emailField.clear();
            addressField.clear();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to add the member: " + e.getMessage());
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
