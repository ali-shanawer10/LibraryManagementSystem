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
}
