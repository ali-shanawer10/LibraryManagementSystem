module com.example.librarymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.librarymanagementsystem.models to javafx.base;
    opens com.example.librarymanagementsystem to javafx.fxml;
    exports com.example.librarymanagementsystem;
    exports com.example.librarymanagementsystem.controllers;
    opens com.example.librarymanagementsystem.controllers to javafx.fxml;
}