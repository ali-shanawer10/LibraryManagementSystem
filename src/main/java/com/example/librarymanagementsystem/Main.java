package com.example.librarymanagementsystem;

import com.example.librarymanagementsystem.utils.BookHelper;
import com.example.librarymanagementsystem.utils.BorrowRecordHelper;
import com.example.librarymanagementsystem.utils.DBHelper;
import com.example.librarymanagementsystem.utils.UserHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

    }
}