package com.example.librarymanagementsystem.utils;

import java.sql.*;

public class BookHelper {

    // Method to get all books
    public static void getAllBooks() {
        String sql = "SELECT * FROM Books";
        try (Connection conn = DBHelper.connect(); Statement statement = conn.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            System.out.println("Books:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("ID") +
                        ", Title: " + rs.getString("Title") +
                        ", Author: " + rs.getString("Author") +
                        ", Availability: " + rs.getString("Availability"));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving books: " + e.getMessage());
        }
    }

    // Method to add a book
    public static boolean insertBook(String title, String author) {
        String sql = "INSERT INTO Books (Title, Author) VALUES (?, ?)";

        try (Connection conn = DBHelper.connect(); PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, author);

            int rowsAffected = preparedStatement.executeUpdate();  // Execute the insert
            if (rowsAffected > 0) {
                System.out.println("Book added successfully.");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
        return false;
    }

    // Method to remove a book
    public static boolean removeBookById(int bookId) {
        String checkSql = "SELECT * FROM Books WHERE ID = ?";
        String deleteSql = "DELETE FROM Books WHERE ID = ?";

        try (Connection conn = DBHelper.connect();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql);
             PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {

            // Check if the book exists
            checkStmt.setInt(1, bookId);
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("Book with ID " + bookId + " does not exist.");
                return false;
            }

            // If exists, delete the book
            deleteStmt.setInt(1, bookId);
            int rowsAffected = deleteStmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book removed successfully.");
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error removing book: " + e.getMessage());
        }
        return false;
    }
}
