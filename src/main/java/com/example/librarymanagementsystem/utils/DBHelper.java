package com.example.librarymanagementsystem.utils;

import java.sql.*;

public class DBHelper {
    private static final String DB_URL = "jdbc:sqlite:database/library.db";

    // Method to connect to the database
    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
        return conn;
    }

    // Method to initialize the database with tables
    public static void initializeDatabase() {
        String booksTable = "CREATE TABLE IF NOT EXISTS Books (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Title TEXT NOT NULL," +
                "Author TEXT NOT NULL," +
                "Availability TEXT NOT NULL DEFAULT 'Available'" +
                ");";

        String usersTable = "CREATE TABLE IF NOT EXISTS Users (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "FirstName TEXT NOT NULL," +
                "LastName TEXT NOT NULL," +
                "Email TEXT NOT NULL UNIQUE," +
                "Address TEXT NOT NULL" +
                ");";

        String borrowRecordsTable = "CREATE TABLE IF NOT EXISTS BorrowRecords (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "BookID INTEGER NOT NULL," +
                "UserID TEXT NOT NULL," +
                "IssueDate TEXT NOT NULL," +
                "ReturnDate TEXT," +
                "FOREIGN KEY (BookID) REFERENCES Books(ID)," +
                "FOREIGN KEY (UserID) REFERENCES Users(ID)" +
                ");";

        try (Connection conn = connect(); Statement statement = conn.createStatement()) {
            statement.execute(booksTable);
            statement.execute(usersTable);
            statement.execute(borrowRecordsTable);
            System.out.println("Database initialized successfully.");
        } catch (SQLException e) {
            System.out.println("Error initializing database: " + e.getMessage());
        }
    }

    // Method to insert a user into the Users table
    public static boolean insertUser(String firstName, String lastName, String email, String address) {
        String sql = "INSERT INTO Users (FirstName, LastName, Email, Address) VALUES (?, ?, ?, ?)";

        try (Connection conn = connect(); PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, address);

            int rowsAffected = preparedStatement.executeUpdate();  // Execute the insert
            if (rowsAffected > 0) {
                System.out.println("User added successfully.");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
        return false;
    }

    // Method to insert a book into the Books table
    public static boolean insertBook(String title, String author) {
        String sql = "INSERT INTO Books (Title, Author) VALUES (?, ?)";

        try (Connection conn = connect(); PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
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

    // Method to insert a borrowRecord into the BorrowRecords table
    public static boolean insertBorrowRecord(int bookID, int userID, String issueDate) {
        String sql = "INSERT INTO BorrowRecords (BookID, UserID, IssueDate) VALUES (?, ?, ?)";

        try (Connection conn = connect(); PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, bookID);  // Set BookID
            preparedStatement.setInt(2, userID);  // Set UserID
            preparedStatement.setString(3, issueDate);  // Set IssueDate

            int rowsAffected = preparedStatement.executeUpdate();  // Execute the insert
            if (rowsAffected > 0) {
                System.out.println("Borrow record inserted successfully.");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error inserting borrow record: " + e.getMessage());
        }
        return false;
    }

}
