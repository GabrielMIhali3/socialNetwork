package org.example._socialnetwork_.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example._socialnetwork_.Service.Service;
import org.example._socialnetwork_.domain.Prietenie;
import org.example._socialnetwork_.domain.Utilizator;

public class FriendsRequest {
    Service service;
    Utilizator utilizator;
    Utilizator utilizatorFriend;
    Prietenie prietenie;

    @FXML
    private Label name;
    @FXML
    private Label username;


    public void setServiceFriendsRequest(Service service, Utilizator utilizatorAdmin, Utilizator utilizatorFriend) {
        this.service = service;
        this.utilizator = utilizatorAdmin;
        this.utilizatorFriend = utilizatorFriend;
        name.setText(utilizatorFriend.getLastName() + " " + utilizatorFriend.getFirstName());
        username.setText(utilizatorFriend.getUsername());
        prietenie = new OperatiiPrietenie(service, utilizatorAdmin, utilizatorFriend).detPrietenie();
    }

    @FXML
    public void handleDeletePrieten(ActionEvent actionEvent) {
        if (prietenie != null) {
            Prietenie deleted = service.deletePrietenie(prietenie.getId());
        }
    }

    @FXML
    public void handleAddPrieten(ActionEvent actionEvent) {
        OperatiiPrietenie op = new OperatiiPrietenie(service, utilizator, utilizatorFriend);
        op.addFriend();
    }

}
