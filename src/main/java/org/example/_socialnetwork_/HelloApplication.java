package org.example._socialnetwork_;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example._socialnetwork_.Repository.Repository;
import org.example._socialnetwork_.Service.Service;
import org.example._socialnetwork_.controller.LoginController;
import org.example._socialnetwork_.domain.Message;
import org.example._socialnetwork_.domain.Prietenie;
import org.example._socialnetwork_.domain.Utilizator;

import java.io.IOException;
import java.time.LocalDateTime;

public class HelloApplication extends Application {
    Service service;

    @Override
    public void start(Stage primaryStage) throws IOException {
        String username = "root";
        String password = "Changeme3#";
        String url = "jdbc:mysql://localhost:3306/socialNetwork";
        service = new Service(username, password, url);
        initView(primaryStage);
        primaryStage.show();
    }

    private void initView(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/Login.fxml"));

        AnchorPane userLayout = fxmlLoader.load();
        primaryStage.setScene(new Scene(userLayout));

        LoginController loginController = fxmlLoader.getController();
        loginController.setLoginService(service);
    }

    public static void main(String[] args) {
        launch();
    }
}