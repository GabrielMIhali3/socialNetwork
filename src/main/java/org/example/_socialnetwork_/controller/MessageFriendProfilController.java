package org.example._socialnetwork_.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.example._socialnetwork_.Service.Service;
import org.example._socialnetwork_.domain.Utilizator;

import java.io.IOException;

public class MessageFriendProfilController {
    private Service service;
    Utilizator utilizator;
    Utilizator utilizatorFriend;
    private AnchorPane anchorPaneMain;

    @FXML
    private Label name;
    @FXML
    private Label username;
    @FXML
    private AnchorPane AnchorPaneProfile;

    @FXML
    void initialize() {
        AnchorPaneProfile.setOnMouseEntered(event -> {
            AnchorPaneProfile.setStyle("-fx-background-color: linear-gradient(to bottom, #e0e0e0, #c8c8c8);");
        });

        AnchorPaneProfile.setOnMouseExited(event -> {
            AnchorPaneProfile.setStyle("-fx-background-color: linear-gradient(to bottom, #f7f7f7, #e9e9e9);");
        });
    }

    public void setServiceMessageFriendsController(Service service, Utilizator utilizatorAdmin, Utilizator utilizatorFriend, AnchorPane anchorPaneMain) {
        this.service = service;
        this.utilizator = utilizatorAdmin;
        this.utilizatorFriend = utilizatorFriend;
        this.anchorPaneMain = anchorPaneMain;
        name.setText(utilizatorFriend.getLastName() + " " + utilizatorFriend.getFirstName());
        username.setText(utilizatorFriend.getUsername());

        AnchorPaneProfile.setOnMouseClicked(event -> {
            System.out.println("merge");
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/_socialnetwork_/views/MessageView.fxml"));
                AnchorPane messagePane = loader.load();

                MessageViewController messageViewController= loader.getController();
                messageViewController.setServiceMessageController(service, utilizatorAdmin, utilizatorFriend, anchorPaneMain, messagePane);
                this.anchorPaneMain.getChildren().add(messagePane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
