package com.example.librarymanagementsystem.utils;

import com.example.librarymanagementsystem.models.Book;
import com.example.librarymanagementsystem.models.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class UserHelper {

    // Method to get all users
    public static ObservableList<Member> getAllUsers() {
        String sql = "SELECT * FROM Users";
        ObservableList<Member> members = FXCollections.observableArrayList();

        try (Connection conn = DBHelper.connect(); Statement statement = conn.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                Member member = new Member(
                        rs.getInt("ID"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Email"),
                        rs.getString("Address")
                );
                members.add(member);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving users: " + e.getMessage());
        }
        return members;
    }

    // Method to add a user
    public static boolean insertUser(String firstName, String lastName, String email, String address) {
        String sql = "INSERT INTO Users (FirstName, LastName, Email, Address) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBHelper.connect(); PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, address);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User added successfully.");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
        return false;
    }

    // Method to remove a user
    public static boolean removeUserById(int userId) {
        String checkSql = "SELECT * FROM Users WHERE ID = ?";
        String deleteSql = "DELETE FROM Users WHERE ID = ?";

        try (Connection conn = DBHelper.connect();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql);
             PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {

            checkStmt.setInt(1, userId);
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("User with ID " + userId + " does not exist.");
                return false;
            }

            deleteStmt.setInt(1, userId);
            int rowsAffected = deleteStmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User removed successfully.");
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error removing user: " + e.getMessage());
        }
        return false;
    }
}
