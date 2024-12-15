package com.example.librarymanagementsystem.utils;

import com.example.librarymanagementsystem.models.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class BookHelper {

    // Method to get all books
    public static ObservableList<Book> getAllBooks() {
        String sql = "SELECT * FROM Books";
        ObservableList<Book> books = FXCollections.observableArrayList();

        try (Connection conn = DBHelper.connect();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                Book book = new Book(
                        rs.getInt("ID"),
                        rs.getString("Title"),
                        rs.getString("Author"),
                        rs.getString("Availability")
                );
                books.add(book);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving books: " + e.getMessage());
        }
        return books;
    }

    // Method to add a book
    public static boolean insertBook(String title, String author) {
        String sql = "INSERT INTO Books (Title, Author) VALUES (?, ?)";

        try (Connection conn = DBHelper.connect(); PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, author);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book added successfully.");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
        return false;
    }
}
