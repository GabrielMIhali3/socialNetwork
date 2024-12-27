package org.example._socialnetwork_.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example._socialnetwork_.HelloApplication;
import org.example._socialnetwork_.Service.Service;
import org.example._socialnetwork_.domain.Utilizator;

import java.io.IOException;
import java.util.Objects;


public class LoginController {
    Service service;
    Utilizator utilizator;

    @FXML
    VBox vboxSingUp;
    @FXML
    VBox vboxLogin;
    @FXML
    TextField tfUsername;
    @FXML
    PasswordField tfPassword;
    @FXML
    TextField tfUsernameSingUp;
    @FXML
    PasswordField tfPasswordSingUp;
    @FXML
    TextField tfFirstName;
    @FXML
    TextField tfLastName;

    public void setLoginService(Service service) {
        this.service = service;
    }

    public void showSinngUpPage(){
        vboxLogin.setVisible(!vboxLogin.isVisible());
        vboxSingUp.setVisible(!vboxSingUp.isVisible());
    }

    private void showUserApplication(Utilizator utilizator){
        try{
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("views/UserView.fxml"));
            AnchorPane root =(AnchorPane)loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Utilizator");
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);
            UserController userController = loader.getController();
            userController.setServiceUserController(service, dialogStage, utilizator);
            dialogStage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void login(){
        if (!tfUsername.getText().isEmpty() && !tfPassword.getText().isEmpty()) {
            Iterable<Utilizator> list = service.getAllUtilizatori();
            for (Utilizator u : list) {
                if (Objects.equals(u.getUsername(), tfUsername.getText()) && Objects.equals(u.getPassword(), tfPassword.getText())) {
                    utilizator = u;
                    showUserApplication(utilizator);
                    return;
                }
            }
            MessageAlert.showErrorMessage(null, "Username sau password incorect!");

        } else{
            MessageAlert.showErrorMessage(null, "NU ai completat toate campurile!");
        }
    }

    public void sindUp(){
        if (!tfFirstName.getText().isEmpty() && !tfLastName.getText().isEmpty() && !tfUsernameSingUp.getText().isEmpty() && !tfPasswordSingUp.getText().isEmpty()) {
            Utilizator utilizator = new Utilizator(tfFirstName.getText(), tfLastName.getText());
            utilizator.setUsername(tfUsernameSingUp.getText());
            utilizator.setPassword(tfPasswordSingUp.getText());
            service.addUtilizator(utilizator);

            showSinngUpPage();
        }
        else{
            MessageAlert.showErrorMessage(null, "NU ai completat toate campurile!");
        }
    }
}
