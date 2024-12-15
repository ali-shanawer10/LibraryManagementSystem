package com.example.librarymanagementsystem.controllers;

import com.example.librarymanagementsystem.models.Book;
import com.example.librarymanagementsystem.models.Member;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import com.example.librarymanagementsystem.utils.SceneNavigator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import static com.example.librarymanagementsystem.utils.BookHelper.getAllBooks;


public class AllBooksController {

    @FXML
    private Button backButton;

    @FXML
    private TableView<Book> tableview;

    @FXML
    private TableColumn<Book, String> title;

    @FXML
    private TableColumn<Book, String> author;

    @FXML
    private TableColumn<Book, String> avail;

    @FXML
    private TextField searchbar;

    private ObservableList<Book> bookList = FXCollections.observableArrayList();

    @FXML
    public void handleBackButton(ActionEvent event) {
        SceneNavigator.goToMainMenu();
    }

    public void initialize() {
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        author.setCellValueFactory(new PropertyValueFactory<>("author"));
        avail.setCellValueFactory(new PropertyValueFactory<>("availability"));

        bookList = getAllBooks();

        searchbar.textProperty().addListener((observable, oldValue, newValue) -> searchMembers(newValue));

        tableview.setItems(bookList);
    }

    private void searchMembers(String query) {
        ObservableList<Book> filteredList = FXCollections.observableArrayList();

        for (Book book : bookList) {
            if (book.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    book.getAuthor().toLowerCase().contains(query.toLowerCase()) ||
                    book.getAvailability().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(book);
            }
        }
        tableview.setItems(filteredList);
    }
}
