package com.example.librarymanagementsystem.controllers;

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

import static com.example.librarymanagementsystem.utils.UserHelper.getAllUsers;

public class AllMembersController {

    @FXML
    private Button backButton;

    @FXML
    public void handleBackButton(ActionEvent event) {
        SceneNavigator.goToMainMenu();
    }

    @FXML
    private TableView<Member> tableview;

    @FXML
    private TableColumn<Member, String> fname;

    @FXML
    private TableColumn<Member, String> lname;

    @FXML
    private TableColumn<Member, String> email;

    @FXML
    private TableColumn<Member, String> address;

    @FXML
    private TextField searchbar;

    private ObservableList<Member> memberList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        fname.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));

        memberList = getAllUsers();

        searchbar.textProperty().addListener((observable, oldValue, newValue) -> searchMembers(newValue));

        tableview.setItems(memberList);
    }

    private void searchMembers(String query) {
        ObservableList<Member> filteredList = FXCollections.observableArrayList();

        for (Member member : memberList) {
            if (member.getFirstName().toLowerCase().contains(query.toLowerCase()) ||
                    member.getLastName().toLowerCase().contains(query.toLowerCase()) ||
                    member.getEmail().toLowerCase().contains(query.toLowerCase()) ||
                    member.getAddress().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(member);
            }
        }
        tableview.setItems(filteredList);
    }
}
