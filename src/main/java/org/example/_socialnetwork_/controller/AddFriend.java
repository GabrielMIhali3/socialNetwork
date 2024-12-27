package org.example._socialnetwork_.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example._socialnetwork_.Service.Service;
import org.example._socialnetwork_.domain.Utilizator;


public class AddFriend {
    Service service;
    Utilizator utilizatorAdmin;
    Utilizator utilizatorFriend;

    @FXML
    private Label name;
    @FXML
    private Label username;

    public void setServiceFriendsRequest(Service service, Utilizator utilizator, Utilizator utilizatorFriend) {
        this.service = service;
        this.utilizatorAdmin = utilizator;
        this.utilizatorFriend = utilizatorFriend;
        name.setText(utilizatorFriend.getLastName() + " " + utilizatorFriend.getFirstName());
        username.setText(utilizatorFriend.getUsername());

    }

    @FXML
    public void handleAddFriend(ActionEvent event) {
        OperatiiPrietenie op = new OperatiiPrietenie(service, utilizatorAdmin, utilizatorFriend);
        op.addFriend();
    }
}
