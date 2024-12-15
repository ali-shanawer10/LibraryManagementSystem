package com.example.librarymanagementsystem.controllers;

import com.example.librarymanagementsystem.models.Book;
import com.example.librarymanagementsystem.models.Member;
import com.example.librarymanagementsystem.utils.SceneNavigator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.librarymanagementsystem.utils.BookHelper.getAllBooks;
import static com.example.librarymanagementsystem.utils.BorrowRecordHelper.insertBorrowRecord;
import static com.example.librarymanagementsystem.utils.UserHelper.getAllUsers;

public class IssueBookController {

    @FXML
    private TextField bookSearch, memberSearch;
    @FXML
    private ListView<Book> bookListView;
    @FXML
    private ListView<Member> memberListView;
    @FXML
    private Label bookTitleLabel, bookAuthorLabel, memberNameLabel, memberEmailLabel;

    private ObservableList<Book> books = FXCollections.observableArrayList();
    private ObservableList<Member> members = FXCollections.observableArrayList();

    private Book selectedBook;
    private Member selectedMember;

    public void initialize() {
        // Load all books and members from DB
        books = getAllBooks();
        members = getAllUsers();

        // Filter only available books
        List<Book> availableBooks = books.stream()
                .filter(Book::isAvailable) // Assuming `isAvailable()` checks if the book is available
                .collect(Collectors.toList());

        // Convert filtered available books to an ObservableList
        ObservableList<Book> availableBooksList = FXCollections.observableArrayList(availableBooks);

        // Set the cell factory for bookListView to display Book Titles
        bookListView.setCellFactory(param -> new ListCell<Book>() {
            @Override
            protected void updateItem(Book book, boolean empty) {
                super.updateItem(book, empty);
                if (empty || book == null) {
                    setText(null);
                } else {
                    setText(book.getTitle());  // Display book title
                }
            }
        });

        // Set the cell factory for memberListView to display Member Names
        memberListView.setCellFactory(param -> new ListCell<Member>() {
            @Override
            protected void updateItem(Member member, boolean empty) {
                super.updateItem(member, empty);
                if (empty || member == null) {
                    setText(null);
                } else {
                    setText(member.getFirstName() + " " + member.getLastName());  // Display member full name
                }
            }
        });

        // Bind the TextField to filter book titles
        bookSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                bookListView.setVisible(false);
            } else {
                ObservableList<Book> filteredBooks = FXCollections.observableArrayList();
                for (Book book : availableBooksList) {  // Use filtered list of available books
                    if (book.getTitle().toLowerCase().contains(newValue.toLowerCase())) {
                        filteredBooks.add(book);
                    }
                }
                bookListView.setItems(filteredBooks);
                bookListView.setVisible(true);  // Show the ListView when there are results
            }
        });

        // Bind the TextField to filter member names
        memberSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                memberListView.setVisible(false);
            } else {
                ObservableList<Member> filteredMembers = FXCollections.observableArrayList();
                for (Member member : members) {
                    if (member.getFirstName().toLowerCase().contains(newValue.toLowerCase()) ||
                            member.getLastName().toLowerCase().contains(newValue.toLowerCase())) {
                        filteredMembers.add(member);
                    }
                }
                memberListView.setItems(filteredMembers);
                memberListView.setVisible(true);  // Show the ListView when there are results
            }
        });

        // Set the initial list of available books
        bookListView.setItems(availableBooksList);

        // Handle book selection from ListView
        bookListView.setOnMouseClicked(event -> {
            selectedBook = bookListView.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                bookSearch.setText(selectedBook.getTitle());  // Set Book Title in the TextField
                bookTitleLabel.setText(selectedBook.getTitle());  // Set Book Title Label
                bookAuthorLabel.setText(selectedBook.getAuthor());  // Set Book Author Label
                bookListView.setVisible(false);  // Hide ListView after selection
            }
        });

        // Handle member selection from ListView
        memberListView.setOnMouseClicked(event -> {
            selectedMember = memberListView.getSelectionModel().getSelectedItem();
            if (selectedMember != null) {
                memberSearch.setText(selectedMember.getFirstName() + " " + selectedMember.getLastName());  // Set Member Full Name in the TextField
                memberNameLabel.setText(selectedMember.getFirstName() + " " + selectedMember.getLastName());  // Set Member Name Label
                memberEmailLabel.setText(selectedMember.getEmail());  // Set Member Email Label
                memberListView.setVisible(false);  // Hide ListView after selection
            }
        });

        // Hide ListView when escape key is pressed
        bookSearch.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode().equals(javafx.scene.input.KeyCode.ESCAPE)) {
                bookListView.setVisible(false);
            }
        });

        memberSearch.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode().equals(javafx.scene.input.KeyCode.ESCAPE)) {
                memberListView.setVisible(false);
            }
        });
    }

    // Method to handle issue button click
    @FXML
    public void handleIssueButton(ActionEvent event) {
        if (selectedBook == null || selectedMember == null) {
            showAlert(AlertType.ERROR, "Error", "Please select both a book and a member before issuing.");
            return;
        }

        // Get the current date
        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        // Call insertBorrowRecord to insert the record into the database
        boolean isInserted = insertBorrowRecord(selectedBook.getId(), selectedMember.getId(), currentDate);

        // Show the appropriate alert based on the result
        if (isInserted) {
            showAlert(AlertType.INFORMATION, "Success", "Book issued successfully.");
            resetScreen();
        } else {
            showAlert(AlertType.ERROR, "Error", "Error issuing the book.");
        }
    }

    // Method to show an alert with the specified type, title, and message
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Method to reset the screen to its initial state
    private void resetScreen() {
        selectedBook = null;
        selectedMember = null;
        bookSearch.clear();
        memberSearch.clear();
        bookTitleLabel.setText("Book Title");
        bookAuthorLabel.setText("Author Name");
        memberNameLabel.setText("Member Name");
        memberEmailLabel.setText("Member Email");
        bookListView.setVisible(false);
        memberListView.setVisible(false);
    }

    // Handle back button click
    @FXML
    public void handleBackButton(ActionEvent event) {
        // Navigate back to the main menu
        SceneNavigator.goToMainMenu();
    }
}
