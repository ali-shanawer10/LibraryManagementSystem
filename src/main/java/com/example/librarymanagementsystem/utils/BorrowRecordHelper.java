package com.example.librarymanagementsystem.utils;

import java.sql.*;

public class BorrowRecordHelper {

    // Method to get all borrow records
    public static void getAllBorrowRecords() {
        String sql = "SELECT * FROM BorrowRecords";
        try (Connection conn = DBHelper.connect(); Statement statement = conn.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            System.out.println("Borrow Records:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("ID") +
                        ", Book ID: " + rs.getInt("BookID") +
                        ", User ID: " + rs.getInt("UserID") +
                        ", Issue Date: " + rs.getString("IssueDate") +
                        ", Return Date: " + rs.getString("ReturnDate"));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving borrow records: " + e.getMessage());
        }
    }

    public static boolean insertBorrowRecord(int bookID, int userID, String issueDate) {
        String insertSQL = "INSERT INTO BorrowRecords (BookID, UserID, IssueDate) VALUES (?, ?, ?)";
        String updateSQL = "UPDATE Books SET Availability = 'Unavailable' WHERE id = ?";

        try (Connection conn = DBHelper.connect();
             PreparedStatement insertStatement = conn.prepareStatement(insertSQL);
             PreparedStatement updateStatement = conn.prepareStatement(updateSQL)) {

            insertStatement.setInt(1, bookID);
            insertStatement.setInt(2, userID);
            insertStatement.setString(3, issueDate);

            int rowsAffected = insertStatement.executeUpdate();
            if (rowsAffected > 0) {
                updateStatement.setInt(1, bookID);
                int rowsUpdated = updateStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error inserting borrow record or updating book availability: " + e.getMessage());
        }
        return false;
    }


    // Method to remove a borrow record
    public static boolean removeBorrowRecordById(int recordId) {
        String checkSql = "SELECT * FROM BorrowRecords WHERE ID = ?";
        String deleteSql = "DELETE FROM BorrowRecords WHERE ID = ?";

        try (Connection conn = DBHelper.connect();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql);
             PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {

            checkStmt.setInt(1, recordId);
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("Borrow record with ID " + recordId + " does not exist.");
                return false;
            }

            deleteStmt.setInt(1, recordId);
            int rowsAffected = deleteStmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Borrow record removed successfully.");
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error removing borrow record: " + e.getMessage());
        }
        return false;
    }
}
